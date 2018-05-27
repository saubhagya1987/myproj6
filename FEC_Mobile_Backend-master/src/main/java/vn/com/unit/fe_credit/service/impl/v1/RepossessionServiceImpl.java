package vn.com.unit.fe_credit.service.impl.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.itextpdf.text.log.SysoCounter;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.bean.v1.RepossessionAttachmentDto;
import vn.com.unit.fe_credit.bean.v1.RepossessionBean;
import vn.com.unit.fe_credit.bean.v1.RepossessionDto;
import vn.com.unit.fe_credit.dao.AccountTeamDao;
import vn.com.unit.fe_credit.dao.NotificationDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionApprovalDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionApproverDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;
import vn.com.unit.fe_credit.entity.collection.Notification;
import vn.com.unit.fe_credit.entity.collection.Repossession;
import vn.com.unit.fe_credit.entity.collection.RepossessionApproval;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;
import vn.com.unit.fe_credit.service.RoleService;
import vn.com.unit.fe_credit.service.v1.RepossessionApproverService;
import vn.com.unit.fe_credit.service.v1.RepossessionAttachmentService;
import vn.com.unit.fe_credit.service.v1.RepossessionService;
import vn.com.unit.fe_credit.utils.MailUtil;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.utils.Description;
import vn.com.unit.utils.NotificationMessages;
import vn.com.unit.utils.NotificationUtil;
import vn.com.unit.utils.OperationType;
import vn.com.unit.utils.RepossessionAndBidderStatus;

@Service
public class RepossessionServiceImpl implements RepossessionService {
	@Autowired
	UserProfile userProfile;
	@Autowired
	MailUtil mailUtil;

	@Autowired
	RoleService roleService;

	@Autowired
	RepossessionApproverService repossessionApproverService;

	@Autowired
	RepossessionApprovalDao repossessionApprovalDao;

	@Autowired
	RepossessionDao repossessionDao;

	@Autowired
	NotificationDao notificationDao;

	@Autowired
	AccountTeamDao accountTeamDao;

	@Autowired
	private Gson gson;

	@Autowired
	RepossessionApproverDao repossessionApproverDao;

	@Autowired
	private RepossessionAttachmentService attachmentService;

@Autowired
private MessageSource msgSrc;
	private static final Logger LOG = LoggerFactory
			.getLogger(ContractTerminationServiceImpl.class);
	private static final String ADMIN_ROLE = "R001";
	private static final String FS_COLLECTOR_ADMIN_ROLE = "FS";
	private static final String CA_ADMIN_ROLE = "CA";

	@Override
	public RepossessionBean getRepossessionContracts() {

		LOG.debug("Getting Repossession Contract list based on Email - "
				+ userProfile.getAccount().getEmail());

		// Check for User Role (from team)
		boolean isAdmin = false;
		for (Team team : userProfile.getAccount().getTeams()) {

			for (Role accountRole : team.getRoles()) {
				if (accountRole.getCode().equalsIgnoreCase(ADMIN_ROLE)
						|| accountRole.getCode().equalsIgnoreCase(
								FS_COLLECTOR_ADMIN_ROLE)
						|| accountRole.getCode()
								.equalsIgnoreCase(CA_ADMIN_ROLE)) {
					isAdmin = true;
					break;
				}
			}
		}

		List<RepossessionDto> repossessionDtos = null;

		// Check if user is Admin, return all terminated contracts available
		if (isAdmin) {
			repossessionDtos = repossessionDao.findAll();
			
		} else {
			// Getting ContractTermination list based on EmailId from
			// Contract_Termination_Approver and Contract_Termination tables

			repossessionDtos = repossessionDao
					.getRepossessionContracts(userProfile.getAccount()
							.getEmail());
			/*
			 * if(repossessionDtos == null || repossessionDtos.size()<1){
			 * repossessionDtos =
			 * repossessionDao.getRepossessionContractsAssigned
			 * (userProfile.getAccount().getEmail(),isAdmin); }
			 */
		}

		RepossessionBean repossessionBean = new RepossessionBean();
		repossessionBean.setTerminatedContracts(repossessionDtos);

		return repossessionBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.fe_credit.service.ContractTerminationService#
	 * reassignTerminatedContract
	 * (vn.com.unit.fe_credit.bean.ContractTerminationBean)
	 */
	@Override
	@Transactional
	public List<String> getNextApproverLevelEmails(Integer id) {
		LOG.info("Reassigning  Repossession to other level approver");
		List<String> approverEmailIds = new ArrayList<String>();
		Repossession repossession = repossessionDao.findByRepossessionId(id);
		String nextApproverLevelResult = repossession.getNextApproverLevel();
		String[] nextApproverLevelResultList = nextApproverLevelResult
				.split("[/|,]");
		for (String nextApproverLevel : nextApproverLevelResultList) {

			// Getting EmailIds of all user accounts having next level approver
			// role
			Role role = roleService.findByCode(nextApproverLevel);
			if (role != null) {
				// approverEmailIds = new ArrayList<String>();
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
			}
		}
		return approverEmailIds;
	}

	/**
	 * @param terminationBean
	 * @param approverEmailIds
	 */
	@Transactional(rollbackFor = { Throwable.class }, value = "txnManagerCollections")
	public RepossessionBean assignContract(RepossessionBean repossessionBean,
			List<String> approverEmailIds, Locale locale) {
try{
		Integer repossessionId = repossessionBean.getRepossessionId();
		System.out.println("repossession----"+repossessionId);
		String nextApproverLevel = repossessionBean.getNextApproverLevel();
		System.out.println("nextApproverLevel----"+nextApproverLevel);
		LOG.debug("Reassigning repossession contract with repossession Id - "
				+ repossessionId + " to next approver level - "
				+ nextApproverLevel);
		List<RepossessionApprover> repossessionApproverList = repossessionApproverDao
				.findByRepossessionId(repossessionId);
		// delete old entries mapped with previous approverEmailId from
		// 'contract_termination_approver' table based on contractTerminationId
		Integer isDeleted = repossessionApproverService
				.deleteRepossessionMappingById(repossessionId);
		System.out.println("approver delete----"+isDeleted);
		Role role = roleService.findByCode(nextApproverLevel);

		// persisting mapping of next level approvers EmailIds against
		// contractTerminationId in 'contract_termination_approver' table
//		for (String email : approverEmailIds) {
//			System.out.println("approver delete----"+isDeleted);
			/*repossessionApproverService.updateApproverEmails(repossessionId,
					repossessionBean.getContractId(), approverEmailIds,
					null, role);*/
		
		Map<String, List<Object>> emails =new HashMap<>();
	    
		  String []  nextRoles=nextApproverLevel.split("[/|,]");
		  for (String nextRole : nextRoles) {
		   emails.put(nextRole, accountTeamDao.getEmailIdByMail(nextRole)); 
		  }
		 
		  List<String> emptyMailList = new ArrayList<>();
		  List<RepossessionApprover> approvers = saveBidderApprover(emails, repossessionId.longValue(), repossessionBean.getContractId(), null, null, "", emptyMailList);
		
		
		repossessionApproverDao.save(approvers);
//		}

		// update "nextApproverLevel" in 'contract_termination' table
		//repossessionDao.updateApproverLevelById(repossessionId,
			//	nextApproverLevel);

		// push notification
		Repossession status = repossessionDao
				.findByRepossessionId(repossessionId);
		NotificationMessages notificationMessages = new NotificationMessages();
		Description description = new Description();
		description.setId(repossessionId);
		description.setType(OperationType.CONTRACT_REPOSSESSION.getValue());
		description.setContractId(repossessionBean.getContractId());
		description.setStatus(status.getStatus().toUpperCase());
		notificationMessages
				.setEn_message("Reassigning repossession contract id : "
						+ repossessionBean.getContractId() + " to next approver level " );
		notificationMessages
				.setVi_message("Reassigning repossession contract id : "
						+ repossessionBean.getContractId() + " to next approver level ");
		description.setNotificationMessages(notificationMessages);
		String descriptionString = gson.toJson(description).toString();
		System.out.println("Notification  :  " + descriptionString);
		// logger.info("Notification  :  "+descriptionString);
		String email = status.getUpdatedBy();
		String sender = userProfile.getAccount().getEmail();
		Notification notification = new NotificationUtil()._createNotification(
				descriptionString, email,
				OperationType.CONTRACT_REPOSSESSION.getValue(), sender);
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
		     
//		     Object vnMessage  = null;
		     try{
		     JSONObject  jsonO = new JSONObject(notification.getDescription());
		 //    Gson gson = new Gson();
//		     Object notificationMesage  =jsonO.get("notificationMessages");
//		     LOG.info("notificationMesage "+jsonO.toString());
	
		     /*JSONObject  messageObj = new JSONObject(notificationMesage.toString());
		      vnMessage  = messageObj.get("vi_message");
		     LOG.info("parsed vnMessage "+vnMessage);*/
		     //  vnMessage = notification.getDescription();
//		     System.out.println(" notification message "+vnMessage);
		     Utils.sendPushNotification(jsonO.toString(),email,adapterurl);
		     }catch(Exception e){
		    	 LOG.info("ERROR WHILE PARSING NOTIFICATION DESCRPITON FROM DB "+ ExceptionUtils.getStackTrace(e));
		    	// vnMessage = notification.getDescription();
		     }
		
		  }
		  catch(Exception ex)
		  {
			 ex.printStackTrace();
			 LOG.info("ERROR WHILE SAVING NOTIFICATION DESCRPITON FROM DB "+ ExceptionUtils.getStackTrace(ex));
		  }

		LOG.debug("Sending mail to all Users having next level approver role - "
				+ nextApproverLevel);
		// TODO: Send mail to all emailIds sendMail(List<String>
		// approverEmailIds)

		LOG.debug("Sending mail to all Users having next level approver role - " + nextApproverLevel);
		// TODO: Send mail to all emailIds sendMail(List<String> approverEmailIds)	
		
		mailUtil.sendEmail(emptyMailList,locale,getMessage(status.getAPPL_ID(), RepossessionAndBidderStatus.PENDING.name(), OperationType.CONTRACT_REPOSSESSION.getValue(),  locale),"Repossession request for contract ID "+status.getAPPL_ID());
}catch(Exception ex){
	System.out.println("ROLL BACKED TRANSACTION");
	ex.printStackTrace();
}
		return repossessionBean;
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional("txnManagerCollections")
	public void updateRepossessionStatus(RepossessionBean repossessionBean,
			Locale locale) {
		LOG.debug("Updating Repossession status");
		Integer id = repossessionBean.getId();

		// Make entry in approval table
		// ContractTerminationApproval contractTerminationApproval = new
		// ContractTerminationApproval();
		Repossession repossession = repossessionDao.findByRepossessionId(id);
		
String approverEmailId = userProfile.getAccount().getEmail();
System.out.println("approverEmailId : " + approverEmailId);
		RepossessionApprover approver = repossessionApproverDao
				.findByRepossessionAndBidderId(
						repossession.getId().longValue(), null,approverEmailId);
		System.out.println("Find approval rule");		
		ApprovalRule approvalRule = repossessionDao
				.getApprovalRuleByRole(approver.getRole());
		System.out.println("Update approvalStatus status");
		RepossessionApproval approvalStatus = repossessionApprovalDao
				.updateApproval(repossession, repossessionBean, approvalRule
						.getId().toString(), approvalRule.getRole());
		// repossessionDao.updateStatus(repossessionBean,repossession);

		Integer isDeleted = repossessionApproverService.deleteApprover(
				repossession.getId().longValue(), null);
		System.out.println("isDeleted : " + isDeleted);
		if (RepossessionAndBidderStatus.APPROVED.name().equalsIgnoreCase(
				approvalStatus.getActionCode())) {
			List<Object> emails = new ArrayList<Object>();
			repossession.setStatus(RepossessionAndBidderStatus.APPROVED.name());
			repossession.setStep(approvalRule.getId().toString());
			String[] nextRoles = approvalRule.getMailTo().split("[/|,]");
			for (String nextRole : nextRoles) {
				emails.addAll(accountTeamDao.getEmailIdByMail(nextRole));
			}

			List<String> emailList = (List<String>) (List<?>) emails;
			mailUtil.sendEmail(emailList,locale,getMessage(repossession.getAPPL_ID(), RepossessionAndBidderStatus.APPROVED.name(), OperationType.CONTRACT_REPOSSESSION.getValue(),  locale),"Repossession approve for contract ID "+repossession.getAPPL_ID());

		}else{
			repossession.setStatus(RepossessionAndBidderStatus.REJECT.name());
		}

		NotificationMessages notificationMessages = new NotificationMessages();
		Description description = new Description();
		description.setId(id);
		description.setType(OperationType.CONTRACT_REPOSSESSION.getValue());
		description.setContractId(repossessionBean.getContractId());
		description.setStatus(approvalStatus.getActionCode().toUpperCase());
		notificationMessages
				.setEn_message("Repossession : " + repossession.getStatus()+ " for contract Id : "+repossession.getAPPL_ID());
		notificationMessages
				.setVi_message("Repossession : " + repossession.getStatus()+ " for contract Id : "+repossession.getAPPL_ID());
		description.setNotificationMessages(notificationMessages);
		String descriptionString = gson.toJson(description).toString();
		System.out.println("Notification  :  " + descriptionString);
		String email = approvalStatus.getUserRequest();
		String sender = userProfile.getAccount().getEmail();
		Notification notification = new NotificationUtil()._createNotification(
				descriptionString, email,
				OperationType.CONTRACT_REPOSSESSION.getValue(), sender);
		
		try {
			LOG.debug("Saving data in notification table");
			
			LOG.info("calling push notification for user ");
			 //    Locale locale = null ;
			     String localRelease = msgSrc.getMessage("isLocalRelease", null, locale);
			     String adapterurl = msgSrc.getMessage("notificationUrl", null, locale);
			     LOG.info("isLocalRelease & URL "+localRelease+" "+adapterurl);
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
			    /*   Object notificationMesage  =jsonO.get("notificationMessages");
			     LOG.info("notificationMesage "+notificationMesage);
		
			     JSONObject  messageObj = new JSONObject(notificationMesage.toString());
			      vnMessage  = messageObj.get("vi_message");
			     LOG.info("parsed vnMessage "+vnMessage);*/
			     LOG.info(" notification message "+vnMessage);
			     Utils.sendPushNotification(jsonO.toString(),email,adapterurl);
			     
			     }catch(Exception e){
			    	 LOG.error("ERROR WHILE PARSING NOTIFICATION DESCRPITON FROM DB "+ ExceptionUtils.getStackTrace(e));
			    	 vnMessage = notification.getDescription();
			     }
			   //  vnMessage = notification.getDescription();
			     
			notificationDao.save(notification);
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			LOG.error("error occurred while saving data in notification table" + ExceptionUtils.getStackTrace(ex));
		}

		

	}
	
	private List<RepossessionApprover> saveBidderApprover(Map<String, List<Object>> emails, Long repossessionId, String contractId,
			String assignedBy, Long bidderId, String stepNumber, List<String> emptyMailList ) {
		try{
		System.out.println("SAVING REPOSSESSION APPROVER ..........");
		RepossessionApprover approver = null;
		List<RepossessionApprover> approvers = new ArrayList<>();
		Set<Entry<String, List<Object>>> entrySet = emails.entrySet();
		for (Entry<String, List<Object>> role : entrySet) {
			for(Object email : role.getValue()){
				emptyMailList.add((String) email);
				System.out.println("SAVING EMAIL : " + email + " assignedBy " + assignedBy + "contractId" + contractId +  " bidderId "+bidderId + "role " + role.getKey());		
				approver = new RepossessionApprover();
				approver.setApproverEmailId((String) email);
				System.out.println("SAVING EMAIL : " + email);
				approver.setAssignedBy(assignedBy);
				approver.setAssignedDate(new Date());
				approver.setContractId(contractId);
				approver.setRepossesionId(repossessionId);
				if(bidderId != null)
				approver.setBidderId(bidderId+"");
				approver.setStep(stepNumber);
				approver.setRole(role.getKey());
				approvers.add(approver);
				
			}
		}
			return approvers;
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
		}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.fe_credit.service.v1.ContractTerminationService#
	 * getContractAttachments(java.lang.String)
	 */
	@Override
	public RepossessionBean getRepossessionAttachments(String contractId) {
		
		LOG.debug("Getting Repossession Attachments to be displayed on based on contract Id - "
				+ contractId);

		List<RepossessionAttachmentDto> attachments = attachmentService
				.getContractAttachments(contractId);

		RepossessionBean repossessionBean = new RepossessionBean();
		repossessionBean.setAttachments(attachments);

		return repossessionBean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.fe_credit.service.v1.ContractTerminationService#
	 * getContractAttachment(java.lang.Integer)
	 */
	@Override
	public RepossessionBean getRepossessionAttachment(Integer attachmentId) {

		LOG.debug("Getting Repossession Attachment to be downloaded on based on attachment Id - "
				+ attachmentId);

		RepossessionAttachmentDto attachmentDto = attachmentService
				.getContractAttachment(attachmentId);

		RepossessionBean repossessionBean = new RepossessionBean();
		repossessionBean.setAttachments(Arrays.asList(attachmentDto));

		return repossessionBean;
	}
	
	private String getMessage(String contractId, String status, String type, Locale locale){
		String messageString = "<table style='width:50%'>"
			       +"<tr><td>Contract ID</td><td>"+contractId+"</td></tr>"
			          +"<tr><td>Status</td><td>"+status+"</td></tr>" 
			          +"<tr><td>Type</td><td>"+type+"</td></tr>"
			          +"<tr><td>Login</td><td colspan=2>"+msgSrc.getMessage("contract.termination.email", null, locale)+"</td></tr></table>";
		return messageString;
	}
	
	
	/*@Override
	public RepossessionBean getRepossessionFormData(String appleId,String customerName,String approvalStatus) {
		LOG.debug("Getting Repossession Contract list based on Email - "+ userProfile.getAccount().getEmail());		
		List<RepossessionDto> repossessionDtos = repossessionDao.getRepossessionFormData(appleId,customerName,approvalStatus);
		RepossessionBean repossessionBean = new RepossessionBean();
		repossessionBean.setTerminatedContracts(repossessionDtos);

		return repossessionBean;
	}*/

}
