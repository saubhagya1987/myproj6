package vn.com.unit.fe_credit.service.impl.v1;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.bean.v1.ContractAttachmentDto;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationDto;
import vn.com.unit.fe_credit.dao.AccountTeamDao;
import vn.com.unit.fe_credit.dao.NotificationDao;
import vn.com.unit.fe_credit.dao.v1.ContractTerminationApprovalDao;
import vn.com.unit.fe_credit.dao.v1.ContractTerminationDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;
import vn.com.unit.fe_credit.entity.collection.ContractTerminationApproval;
import vn.com.unit.fe_credit.entity.collection.Notification;
import vn.com.unit.fe_credit.service.RoleService;
import vn.com.unit.fe_credit.service.v1.ContractAttachmentService;
import vn.com.unit.fe_credit.service.v1.ContractTerminationApproverService;
import vn.com.unit.fe_credit.service.v1.ContractTerminationService;
import vn.com.unit.fe_credit.utils.MailUtil;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.utils.Description;
import vn.com.unit.utils.NotificationMessages;
import vn.com.unit.utils.NotificationUtil;
import vn.com.unit.utils.OperationType;

import com.google.gson.Gson;

@Service
public class ContractTerminationServiceImpl implements ContractTerminationService {

	@Autowired
	UserProfile userProfile;
	@Autowired
	MailUtil mailUtil;

	@Autowired
	RoleService roleService;

	@Autowired
	ContractTerminationApproverService contractTerminationApproverService;
	
	@Autowired
	ContractTerminationApprovalDao contractTerminationApprovalDao;

	@Autowired
	ContractTerminationDao contractTerminationDao;
	
	@Autowired
	NotificationDao notificationDao;
	
	@Autowired
	AccountTeamDao accountTeamDao;
	
	@Autowired
	private Gson gson;
	
	@Autowired
	private MessageSource msgSrc;	
	
	@Autowired
	private ContractAttachmentService attachmentService;

	private static final Logger LOG = LoggerFactory.getLogger(ContractTerminationServiceImpl.class);
	private static final String ADMIN_ROLE = "R001";
	private static final String FS_COLLECTOR_ADMIN_ROLE = "FS";
	private static final String CA_ADMIN_ROLE = "CA";

	@Override
	public ContractTerminationBean getTerminatedContracts() {

		LOG.debug("Getting Terminated Contract list based on Email - " + userProfile.getAccount().getEmail());

		// Check for User Role (from team)
		boolean isAdmin = false;
		for (Team team : userProfile.getAccount().getTeams()) {

			for (Role accountRole : team.getRoles()) {
				if (accountRole.getCode().equalsIgnoreCase(ADMIN_ROLE)
						|| accountRole.getCode().equalsIgnoreCase(FS_COLLECTOR_ADMIN_ROLE)
						|| accountRole.getCode().equalsIgnoreCase(CA_ADMIN_ROLE)) {
					isAdmin = true;
					break;
				}
			}
		}

		List<ContractTerminationDto> contractTerminationDtos = null;

		// Check if user is Admin, return all terminated contracts available
		if (isAdmin) {
			contractTerminationDtos = contractTerminationDao.findAll();
			/*List<ContractTerminationDto> contractTerminationList = contractTerminationDao.getTerminatedContracts(userProfile.getAccount().getEmail());
			if(contractTerminationList == null || contractTerminationList.size()<1){
			     contractTerminationDtos = contractTerminationDao.getTerminatedContractsAssigned(userProfile.getAccount().getEmail(),isAdmin);
			}*/
		} else {
			// Getting ContractTermination list based on EmailId from
			// Contract_Termination_Approver and Contract_Termination tables
			
			contractTerminationDtos = contractTerminationDao.getTerminatedContracts(userProfile.getAccount().getEmail());
			/*if(contractTerminationDtos.size()<1){
			     contractTerminationDtos = contractTerminationDao.getTerminatedContractsAssigned(userProfile.getAccount().getEmail(),isAdmin);
			}*/
		}

		ContractTerminationBean contractTerminationBean = new ContractTerminationBean();
		contractTerminationBean.setContractTerminationDtoList(contractTerminationDtos);

		return contractTerminationBean;
	}

	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.service.ContractTerminationService#reassignTerminatedContract(vn.com.unit.fe_credit.bean.ContractTerminationBean)
	 */
	@Override
	@Transactional
	public List<String> getNextApproverLevelEmails(Integer contractTerminationId) {

		ContractTermination contractTermination = contractTerminationDao.findByTerminationId(contractTerminationId);
		String nextApproverLevel = contractTermination.getNextApproverLevel();
		
		// Getting EmailIds of all user accounts having next level approver role
		Role role = roleService.findByCode(nextApproverLevel);

		List<String> approverEmailIds = new ArrayList<String>();
		List<Account> accounts = new ArrayList<Account>();

		for (Team team : role.getTeeprocurement()) {
			for (Account account : team.getAccounts()) {
				String emailId = account.getEmail();

				if (!approverEmailIds.contains(emailId)) {
					approverEmailIds.add(account.getEmail());
					accounts.add(account);
				}
			}
		}
		return approverEmailIds;
	}

	
	/**
	 * @param terminationBean
	 * @param approverEmailIds
	 */
	@Transactional(rollbackFor = {Throwable.class}, value = "txnManagerCollections")
	public ContractTerminationBean assignContract(ContractTerminationBean terminationBean, List<String> approverEmailIds,Locale locale) {
		
		Integer contractTerminationId = terminationBean.getContractTerminationId();
		String nextApproverLevel = terminationBean.getNextApproverLevel();
		
		LOG.debug("Reassigning terminated contract with Termination Id - " + contractTerminationId
				+ " to next approver level - " + nextApproverLevel);	
		
		// delete old entries mapped with previous approverEmailId from
		// 'contract_termination_approver' table based on contractTerminationId
		Integer isDeleted = contractTerminationApproverService.deleteContractMappingById(contractTerminationId);

		// persisting mapping of next level approvers EmailIds against
		// contractTerminationId in 'contract_termination_approver' table
		contractTerminationApproverService.updateApproverEmails(contractTerminationId, terminationBean.getContractId(), approverEmailIds);
		
		// update "nextApproverLevel" in 'contract_termination' table
		contractTerminationDao.updateApproverLevelById(contractTerminationId, nextApproverLevel);
		
		//push notification
		
		  ContractTermination approvalStatus=contractTerminationDao.findByContractTerminationId(contractTerminationId);
		  NotificationMessages notificationMessages = new NotificationMessages();
		  Description description = new Description();
		  description.setId(contractTerminationId);
		  description.setType(OperationType.CONTRACT_TERMINATION.getValue());
		  description.setContractId(terminationBean.getContractId());
		  description.setStatus(approvalStatus.getStatus().toUpperCase());
		  notificationMessages.setEn_message("CONTRACT TERMINATION "+ approvalStatus.getStatus().toUpperCase() + " for contract Id : "+ terminationBean.getContractId());
		  notificationMessages.setVi_message("CONTRACT TERMINATION " + approvalStatus.getStatus().toUpperCase() + " for contract Id : "+ terminationBean.getContractId());
		  description.setNotificationMessages(notificationMessages);
		  String descriptionString = gson.toJson(description).toString();
		  System.out.println("Notification  :  "+descriptionString);
		  //logger.info("Notification  :  "+descriptionString);
		  String email = approvalStatus.getUpdatedBy();
		  String sender= userProfile.getAccount().getEmail();
		  Notification notification = new NotificationUtil()._createNotification(descriptionString, email, OperationType.CONTRACT_TERMINATION.getValue(),sender);
		  try{
		     notificationDao.save(notification);
		     LOG.info("calling push notification for user ");
		     String localRelease = msgSrc.getMessage("isLocalRelease", null, locale);
		     String adapterurl = msgSrc.getMessage("notificationUrl", null, locale);
		     System.out.println("isLocalRelease & URL "+localRelease+" "+adapterurl);
		     LOG.info("isLocalRelease & URL "+localRelease+" "+adapterurl);
		    		     
		     if(localRelease.equals("true")){
					try{
					email = email.substring(0, email.indexOf("@"));
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
		    System.out.println("email "+email);
		     LOG.info("email "+email);	   
		     
		     Object vnMessage  = null;
		     try{
		     JSONObject  jsonO = new JSONObject(notification.getDescription());
		     System.out.println(" notification message "+jsonO.toString());
		     Utils.sendPushNotification(jsonO.toString(),email,adapterurl);
		   /*  Object notificationMesage  =jsonO.get("notificationMessages");
		     LOG.info("notificationMesage "+notificationMesage);
	
		     JSONObject  messageObj = new JSONObject(notificationMesage.toString());
		      vnMessage  = messageObj.get("vi_message");
		     LOG.info("parsed vnMessage "+vnMessage);*/
		     
		     }catch(Exception e){
		    	 LOG.error("ERROR WHILE PARSING NOTIFICATION DESCRPITON FROM DB "+ ExceptionUtils.getStackTrace(e));
		     }
		   //  vnMessage = notification.getDescription();
		   
		  }
		  catch(Exception ex)
		  {
			 ex.printStackTrace();
		  }
		  String mailMessage = "<table style='width:50%'>"
					+"<tr><td>Contract ID</td><td>"+approvalStatus.getContractId()+"</td></tr>"
					+"<tr><td>Status</td><td>"+approvalStatus.getStatus()+"</td></tr>" 
					+"<tr><td>Type</td><td>"+OperationType.CONTRACT_TERMINATION+"</td></tr>"
					+"<tr><td colspan=2>"+msgSrc.getMessage("contract.termination.email", null, locale)+"</td></tr></table>";
		 String subject = "Contract Termination request for contract ID: "+approvalStatus.getContractId();
		System.out.println("Sending mail to all Users having next level approver role - " + nextApproverLevel);
		// TODO: Send mail to all emailIds sendMail(List<String> approverEmailIds)	
		try{
			mailUtil.sendEmail(approverEmailIds,locale,mailMessage,subject);
		}
		catch(Exception e){
			System.out.println("exception occured while sending mail");
		    e.printStackTrace();	
		}
		
		
		return terminationBean;
	}
	

	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.service.ContractTerminationService#updateContractStatus(vn.com.unit.fe_credit.bean.ContractTerminationBean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public ContractTerminationBean updateContractStatus(ContractTerminationBean contractTerminationBean, String contractId,Locale locale) {
		
		Integer contractTerminationId = contractTerminationBean.getId();
		
		// Make entry in approval table
		//ContractTerminationApproval contractTerminationApproval = new ContractTerminationApproval();
		ContractTermination contractTermination = contractTerminationDao.findByContractTerminationId(contractTerminationId);
		ContractTerminationApproval approvalStatus=contractTerminationApprovalDao.updateApproval(contractTermination, contractTerminationBean);		
		contractTerminationDao.updateStatus(contractTerminationBean,contractTermination);	
		 
		Integer isDeleted = contractTerminationApproverService.deleteContractMappingById(contractTerminationId);
		List<ContractTerminationDto> newTerList = new ArrayList<ContractTerminationDto>();
		/*if(null != contractTerminationBean.getContractTerminationDtoList()){
			List<ContractTerminationDto> newList =contractTerminationBean.getContractTerminationDtoList();
			  for(ContractTerminationDto dto : newList){
				  if(!dto.getContractId().equals(contractTerminationBean.getId())){
					  System.out.println(dto.getContractId() + "  :  "+contractTerminationBean.getId());
					  newTerList.add(dto);
				  }
			  }
		  }
		contractTerminationBean.setContractTerminationDtoList(newTerList);*/
		//sending mail to FC,FS,CA
		List<Object> emails=accountTeamDao.getFSCAEmails();
		emails.add(contractTermination.getUpdatedBy());
		String mailMessage = "<table style='width:50%'>"
				+"<tr><td>Contract ID</td><td>"+contractTermination.getContractId()+"</td></tr>"
				+"<tr><td>Status</td><td>"+contractTermination.getStatus()+"</td></tr>" 
				+"<tr><td>Type</td><td>"+OperationType.CONTRACT_TERMINATION+"</td></tr>"
				+"<tr><td colspan=2>"+msgSrc.getMessage("contract.termination.email", null, locale)+"</td></tr></table>";
		String subject = "Contract Termination request for contract ID: "+contractTermination.getContractId();
		List<String> emailList=(List<String>)(List<?>) emails;		
	    mailUtil.sendEmail(emailList,locale,mailMessage,subject);
	   		
	    //push notification
		  //ContractTerminationApproval approvalStatus=contractTerminationApprovalDao.findByContractId(contractId);
		  NotificationMessages notificationMessages = new NotificationMessages();
		  Description description = new Description();
		  description.setId(contractTerminationId);
		  description.setType(OperationType.CONTRACT_TERMINATION.getValue());
		  description.setContractId(contractTermination.getContractId());
		  description.setStatus(approvalStatus.getActionCode().toUpperCase());
		  notificationMessages.setEn_message("CONTRACT TERMINATION "+ approvalStatus.getActionCode().toUpperCase() + " for contract Id : "+ contractTermination.getContractId());
		  notificationMessages.setVi_message("CONTRACT TERMINATION " + approvalStatus.getActionCode().toUpperCase() + " for contract Id : "+ contractTermination.getContractId());
		
		  description.setNotificationMessages(notificationMessages);
		  String descriptionString = gson.toJson(description).toString();
		  System.out.println("Notification  :  "+descriptionString);		  
		  String email = approvalStatus.getUserRequest();
		  String sender= userProfile.getAccount().getEmail();
		  Notification notification = new NotificationUtil()._createNotification(descriptionString, email, OperationType.CONTRACT_TERMINATION.getValue(),sender);
		  try{
		     notificationDao.save(notification);
		     System.out.println("calling push notification for user ");
		     String localRelease = msgSrc.getMessage("isLocalRelease", null, locale);
		     String adapterurl = msgSrc.getMessage("notificationUrl", null, locale);
		     System.out.println("isLocalRelease & URL "+localRelease+" "+adapterurl);
		     LOG.info("isLocalRelease & URL "+localRelease+" "+adapterurl);
		    		     
		     if(localRelease.equals("true")){
					try{
					email = email.substring(0, email.indexOf("@"));
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
		    System.out.println("email "+email);
		     LOG.info("email "+email);	  
		  Object vnMessage  = null;
		     try{
		     JSONObject  jsonO = new JSONObject(notification.getDescription());
		     /*  Object notificationMesage  =jsonO.get("notificationMessages");
		     LOG.info("notificationMesage "+notificationMesage);
	
		     JSONObject  messageObj = new JSONObject(notificationMesage.toString());
		      vnMessage  = messageObj.get("vi_message");
		     LOG.info("parsed vnMessage "+vnMessage);*/
		     
		     Utils.sendPushNotification(jsonO.toString(),email,adapterurl);
		     }catch(Exception e){
		    	 LOG.error("ERROR WHILE PARSING NOTIFICATION DESCRPITON FROM DB "+ ExceptionUtils.getStackTrace(e));
		     }
		   //  vnMessage = notification.getDescription();
		  } catch(Exception ex) {
			  LOG.info("ERROR WHILE SAVING NOTIFICATION DESCRPITON FROM DB "+ ExceptionUtils.getStackTrace(ex));
		  } 
		
		return contractTerminationBean;
	}

	
	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.service.v1.ContractTerminationService#getContractAttachments(java.lang.String)
	 */
	@Override
	public ContractTerminationBean getContractAttachments(String contractId) {

		LOG.debug("Getting Contract Attachments to be displayed on based on contract Id - " + contractId);
		
		List<ContractAttachmentDto> attachments = attachmentService.getContractAttachments(contractId);
		
		ContractTerminationBean contractTerminationBean = new ContractTerminationBean();
		contractTerminationBean.setAttachments(attachments);
		
		return contractTerminationBean;
	}

	
	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.service.v1.ContractTerminationService#getContractAttachment(java.lang.Integer)
	 */
	@Override
	public ContractTerminationBean getContractAttachment(Integer attachmentId) {
		
		LOG.debug("Getting Contract Attachment to be downloaded on based on attachment Id - " + attachmentId);
		
		ContractAttachmentDto attachmentDto = attachmentService.getContractAttachment(attachmentId);
		
		ContractTerminationBean terminationBean = new ContractTerminationBean();
		terminationBean.setAttachments(Arrays.asList(attachmentDto));
		
		return terminationBean;
	}
	
	
	
	
}


