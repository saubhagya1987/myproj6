package vn.com.unit.fe_credit.service.impl.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.bean.v1.BidderBean;
import vn.com.unit.fe_credit.bean.v1.BidderDto;
import vn.com.unit.fe_credit.dao.AccountTeamDao;
import vn.com.unit.fe_credit.dao.NotificationDao;
import vn.com.unit.fe_credit.dao.v1.BidderDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionApprovalDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionApproverDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionDao;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;
import vn.com.unit.fe_credit.entity.collection.Bidder;
import vn.com.unit.fe_credit.entity.collection.Notification;
import vn.com.unit.fe_credit.entity.collection.Repossession;
import vn.com.unit.fe_credit.entity.collection.RepossessionApproval;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;
import vn.com.unit.fe_credit.service.v1.BidderService;
import vn.com.unit.fe_credit.utils.MailUtil;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.utils.Description;
import vn.com.unit.utils.NotificationMessages;
import vn.com.unit.utils.NotificationUtil;
import vn.com.unit.utils.OperationType;
import vn.com.unit.utils.RepossessionAndBidderStatus;

import com.google.gson.Gson;

@Service
public class BidderServiceImpl implements BidderService {

	@Autowired
	private UserProfile userProfile;
	
	@Autowired
	private BidderDao bidderDao;
@Autowired
MailUtil util;
	@Autowired
	private Gson gson;
	@Autowired
	private AccountTeamDao accountTeamDao;

	@Autowired
	private RepossessionDao repossessionDao;
	@Autowired
	private RepossessionApproverDao repossessionApproverDao;

	@Autowired
	private RepossessionApprovalDao approvalDao;

@Autowired
private MessageSource msgSrc;	
	
	
	@Autowired
	private NotificationDao notificationDao;
	
	private static final Logger LOG = LoggerFactory.getLogger(BidderServiceImpl.class);
/*	private static final String ADMIN_ROLE = "R001";
	private static final String FS_COLLECTOR_ADMIN_ROLE = "FS";
	private static final String CA_ADMIN_ROLE = "CA";*/

	@Override
	public BidderBean getBidderList(String contractId) {
		LOG.info("Getting Bidder List");
		// Check for User Role (from team)
		/*boolean isAdmin = false;
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

		List<BidderDto> bidderDtos = null;

		// Check if user is Admin, return all bidders available
		if (isAdmin) {
			bidderDtos = bidderDao.findAll();
			
			 * List<BidderDto> bidderList =
			 * bidderDao.getBidderList(userProfile.getAccount().getEmail());
			 * if(bidderList.size()<1){ bidderDtos =
			 * bidderDao.getBidderResult(userProfile.getAccount().getEmail(),
			 * isAdmin); }
			 

		} else {*/
			// Getting Bidder list based on EmailId from
			// Bidder_Approver and Bidder tables
//		List<BidderDto>  bidderDtos = null;
			List<BidderDto> bidderList = bidderDao.getBidderList(userProfile.getAccount().getEmail(), contractId);
			/*if (bidderList == null || bidderList.size() < 1) {
				bidderDtos = bidderDao.getBidderResult(userProfile.getAccount().getEmail(), isAdmin);
			}*/
//		}

		BidderBean bidderBean = new BidderBean();
		bidderBean.setBidderList(bidderList);

		return bidderBean;

	}
	/*
	 * public void dummy(Long repossessionId, String actionCode, String remarks,
	 * String role, bidderID) { //
	 * 
	 * //
	 * 
	 * 
	 * 
	 * 
	 * 
	 * repossession.setStep(rule.getId()+""); //find next level approver email
	 * from account table // List<Bidder> bidder =
	 * bidderDao.getBidderByContractId(contractId, repossessionId); //
	 * List<String> emailList=(List<String>)(List<?>) emails;
	 * mailUtil.sendEmail(emailList,locale); //if next role is empty then send
	 * push notification NotificationMessages notificationMessages = new
	 * 
	 * 
	 * 
	 * 
	 * }
	 */
	/*
	 * @Override public void updateContractStatus(BidderBean bidderBean, Locale
	 * locale) { Long id = bidderBean.getId().longValue(); Bidder bidder =
	 * bidderDao.findByBidderId(id); BidderApproval approvalStatus =
	 * bidderDao.updateApproval(bidder,bidderBean);
	 * //repossessionDao.updateStatus(repossessionBean,repossession);
	 * 
	 * }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * vn.com.unit.fe_credit.service.v1.BidderService#updateContractStatus(vn.
	 * com.unit.fe_credit.bean.v1.BidderBean, java.util.Locale)
	 */
	@Override
	@Transactional("txnManagerCollections")
	public void updateBidderStatus(BidderBean bidderBean, Locale locale) {
		LOG.info("Update Bidder Status");
		Repossession repossession = repossessionDao.findByRepossessionId(bidderBean.getId().intValue());
		List<Bidder> bidder = bidderDao.getBidderByContractId(repossession.getAPPL_ID(), repossession.getId().longValue());
		RepossessionApprover  approver =  repossessionApproverDao.findByRepossessionAndBidderId(bidderBean.getId(), bidderBean.getBidderId().toString(), userProfile.getAccount().getEmail());
		String sender = approver.getApproverEmailId();
		Bidder approvedBidder = null;
		for(Bidder bid: bidder){
			if( !bid.getId().equals(bidderBean.getBidderId().longValue()) && RepossessionAndBidderStatus.APPROVED.name().equalsIgnoreCase(bidderBean.getStatus())){
				bid.setStatus(RepossessionAndBidderStatus.REJECT);
			
			bid.setApprovedDate(new Date());
			bid.setUserApprove(sender);
			continue;
			}
			approvedBidder  = bid;
			if( bidderBean.getBidderId().equals(bid.getId().toString()) && RepossessionAndBidderStatus.REJECT.name().equalsIgnoreCase(bidderBean.getStatus())){
				approvedBidder.setStatus(RepossessionAndBidderStatus.REJECT);
				break;
			}
		}
		
	
		
	 	repossessionApproverDao.deleteByRepossessionAndBidderId(bidderBean.getId(), bidderBean.getBidderId()+"");
		
		ApprovalRule rule=repossessionDao.getApprovalRuleByRole(approver.getRole());


		Map<String, List<Object>> emails =new HashMap<>();
		    
		  String []  nextRoles=rule.getMailTo().split("[/|,]");
		  for (String nextRole : nextRoles) {
		   emails.put(nextRole, accountTeamDao.getEmailIdByMail(nextRole)); 
		  }
		 
		  List<String> emptyMailList = new ArrayList<>();
		 
		 
		   
		  repossession.setStep(rule.getId()+"");
		   
		  if(rule.getRoleToApprove()!=null && RepossessionAndBidderStatus.APPROVED.name().equalsIgnoreCase(bidderBean.getStatus())){
			  
			  List<RepossessionApprover> approvers = 	  saveBidderApprover(emails, (long) repossession.getId(), repossession.getAPPL_ID(),
					  repossession.getUpdatedBy() ,   bidderBean.getBidderId().longValue(),rule.getId()+"", emptyMailList); 
			  repossessionApproverDao.save(approvers);
			  String messageString = "<table style='width:50%'>"
				       +"<tr><td>Contract ID</td><td>"+repossession.getAPPL_ID()+"</td></tr>"
				          +"<tr><td>Status</td><td>"+RepossessionAndBidderStatus.PENDING+"</td></tr>" 
				          +"<tr><td>Type</td><td>"+OperationType.BIDDER.name()+"</td></tr>"
				          +"<tr><td>Login</td><td colspan=2>"+msgSrc.getMessage("contract.termination.email", null, locale)+"</td></tr></table>";
			  util.sendEmail(emptyMailList, locale,messageString,"Bidder request for contract ID "+repossession.getAPPL_ID());  
		  }else if(rule.getRoleToApprove() == null){
			  if(RepossessionAndBidderStatus.APPROVED.name().equalsIgnoreCase(bidderBean.getStatus()))
				  approvedBidder.setStatus(RepossessionAndBidderStatus.APPROVED);
			  
			  approvedBidder.setApprovedDate(new Date());
			  approvedBidder.setUserApprove(sender);

			  saveAndSendNotification(repossession, bidderBean.getRemark(), approver.getAssignedBy(), bidderBean.getBidderId(),  sender, locale, approvedBidder.getStatus().name()) ;
		  }else{
			  saveAndSendNotification(repossession, bidderBean.getRemark(), approver.getAssignedBy(), bidderBean.getBidderId(),  sender, locale, RepossessionAndBidderStatus.REJECT.name()) ;
			  
		  }
		  
		  
		  saveApproval( repossession, bidderBean.getStatus(), bidderBean.getRemark(), rule, approvedBidder.getId().toString()); 
	}


	private void saveAndSendNotification(Repossession repossession, String remark, String email, Integer bidderId, String sender,Locale locale, String bidderStatus) {
		  LOG.info("saving notification");		 
		  NotificationMessages notificationMessages = new NotificationMessages();
		  Description description = new Description();
		  description.setId(bidderId);
		  description.setType(OperationType.BIDDER.getValue());
		  description.setContractId(repossession.getAPPL_ID());
		  description.setStatus(repossession.getStatus());
		  notificationMessages.setEn_message("Bidder " + bidderStatus+ " for contract Id : "+repossession.getAPPL_ID());
		  notificationMessages.setVi_message("Bidder " + bidderStatus + " for contract Id : "+repossession.getAPPL_ID());
		  description.setNotificationMessages(notificationMessages);
		  description.setOther("repossession", repossession.getId());
		  String descriptionString = gson.toJson(description).toString();
		  LOG.info("Notification  :  "+descriptionString);		  
		  Notification notification = new NotificationUtil()._createNotification(descriptionString, email, OperationType.CONTRACT_REPOSSESSION.getValue(),sender);
		  try{
		     notificationDao.save(notification);
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
		 /*    
		     Object notificationMesage  =jsonO.get("notificationMessages");
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
		  }
		  catch(Exception ex)
		  {
			 LOG.error("Error while saving notification - " + ExceptionUtils.getStackTrace(ex));
			 
		  }
		
	}

	
	private List<RepossessionApprover> saveBidderApprover(Map<String, List<Object>> emails, Long repossessionId, String contractId,
			String assignedBy, Long bidderId, String stepNumber, List<String> emptyMailList ) {
		LOG.info("SAVING BIDDER APPROVER ..........");
		RepossessionApprover approver = null;
		List<RepossessionApprover> approvers = new ArrayList<>();
		Set<Entry<String, List<Object>>> entrySet = emails.entrySet();
		for (Entry<String, List<Object>> role : entrySet) {
			for(Object email : role.getValue()){
				emptyMailList.add((String) email);
				
				approver = new RepossessionApprover();
				approver.setApproverEmailId((String) email);
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
		}
	/**
	 * 
	 */
	private void saveApproval(Repossession repossession, String actionCode, String remarks, ApprovalRule step, String bidderId) {
		LOG.info("SAVING NEW APPROVAL ..........");
		RepossessionApproval approval = new RepossessionApproval();
		approval.setContractId(repossession.getAPPL_ID());
		approval.setCustomerName(repossession.getCustFullName());
		approval.setCustomerId(repossession.getCustomerId());
		approval.setProduct(repossession.getProduct());
		approval.setDpd(repossession.getDpd().toString());
		if(RepossessionAndBidderStatus.APPROVED.name().equalsIgnoreCase(actionCode))
			approval.setActionCode(RepossessionAndBidderStatus.APPROVED.name());
		else
			approval.setActionCode(RepossessionAndBidderStatus.REJECT.name());
		approval.setBidderId(bidderId);
		approval.setRepossessionId(repossession.getId());
		approval.setUserRequest(repossession.getUpdatedBy());
		approval.setRequestDate(new Date());
		approval.setUserApprove(userProfile.getAccount().getEmail());
		approval.setApproveDate(new Date()); 
		approval.setRemarks(remarks);
		approval.setEntryDate(new Date());
		approval.setRole(step.getRole());
		approval.setStep(step.getId()+"");
		approvalDao.save(approval);
		LOG.info("SAVE NEW APPROVAL ..........");
	}
}
