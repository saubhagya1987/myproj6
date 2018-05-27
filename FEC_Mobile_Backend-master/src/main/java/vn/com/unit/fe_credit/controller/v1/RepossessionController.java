package vn.com.unit.fe_credit.controller.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.PathParam;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.bean.v1.BidderBean;
import vn.com.unit.fe_credit.bean.v1.BidderDto;
import vn.com.unit.fe_credit.bean.v1.RepossessionAttachmentDto;
import vn.com.unit.fe_credit.bean.v1.RepossessionBean;
import vn.com.unit.fe_credit.bean.v1.RepossessionDto;
import vn.com.unit.fe_credit.dao.v1.BidderDao;
import vn.com.unit.fe_credit.dao.v1.RepossessionDao;
import vn.com.unit.fe_credit.entity.collection.Bidder;
import vn.com.unit.fe_credit.entity.collection.Repossession;
import vn.com.unit.fe_credit.service.v1.BidderService;
import vn.com.unit.fe_credit.service.v1.RepossessionService;

import com.sun.org.apache.xml.internal.security.utils.Base64;


@SessionAttributes({"bean","bidderBean"})
@Controller
@RequestMapping("/contract/repossession/")
public class RepossessionController {

	private static final Logger LOG = LoggerFactory.getLogger(RepossessionController.class);
	
	private static final String MIME_TYPE = "application/octet-stream";
	private static final String RESPONSE_HEADER = "Content-Disposition";

	@Autowired
	MessageSource msgSrc;
	
	@Autowired
	UserProfile userProfile;
	
	@Autowired
	RepossessionService repossessionService;	
	@Autowired
	RepossessionDao repossessionDao;
	@Autowired
	BidderDao bidderDao;
	@Autowired
	BidderService bidderService;

	
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String showRepossessionDetails(@PathParam( value = "repossessionId") Integer repossessionId,@ModelAttribute(value = "bean") 
	RepossessionBean bean, Model model,Locale locale)throws Exception{
		LOG.info("Request received for Show Repossession Details . . . . .");
		try{
			
		    bean.setId(repossessionId);	
		    Repossession repossession= repossessionDao.findByRepossessionId(repossessionId);		  
		    RepossessionDto repossessionDto=new RepossessionDto();		    
		    repossessionDto.setApproverLevel(repossession.getApproverLevel());		
		    repossessionDto.setId(repossession.getId());
		    repossessionDto.setLoanAccountNumber(repossession.getLoanAcctNum());
		    repossessionDto.setAppleId(repossession.getAPPL_ID());
		    repossessionDto.setCustomerId(repossession.getCustomerId());
		    repossessionDto.setLoanAmount(repossession.getLoanAmount());
		    repossessionDto.setCustomerName(repossession.getCustFullName());
		    repossessionDto.setPrincipalOutstanding(repossession.getPrincipleOutstanding());
		    repossessionDto.setTotalAmountPaid(repossession.getTotalAmountPaid());
		    repossessionDto.setDpd(repossession.getDpd());
		    repossessionDto.setRepossessionAddress(repossession.getReposessionAddress());
		    repossessionDto.setUnitCode(repossession.getUnitCode());
		    repossessionDto.setUnitDesc(repossession.getUnitDesc());
		    repossessionDto.setSuggestions(repossession.getSuggestions());
		    repossessionDto.setCustomersPhone(repossession.getCustomersPhone());
		    repossessionDto.setApproverLevel(repossession.getApproverLevel());
		    repossessionDto.setNextApproverLevel(repossession.getNextApproverLevel());
		    repossessionDto.setAssetCondition(repossession.getAssetCondition());
		    repossessionDto.setBrand(repossession.getBrand());
		    repossessionDto.setFinancialConditionAssessment(repossession.getFinancialConditionAssessment());		    
		    
		   	List<RepossessionDto> repossessionDtoList=new ArrayList<RepossessionDto>();			
		   	repossessionDtoList.add(repossessionDto);
			bean.setTerminatedContracts(repossessionDtoList);
		  
		   
		  
		}
		catch(Exception exp){
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occurred while Showing Repossession Details - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bean.getListResult())) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("beans", bean);
		return "contract.repossession.detail.view";
	}

	@RequestMapping(value = "/attachments", method = RequestMethod.GET)
	public String showRepossessionAttachments(Model model)throws Exception{
		LOG.info("Request received for Showing Repossession Attachments . . . . .");
		return "contract.repossession.attachments";
	}
	
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public String showRepossessionStatus(@PathParam("repossessionId") Integer repossessionId,@ModelAttribute(value = "bean") RepossessionBean bean,
		Locale locale,Model model)throws Exception{
		LOG.info("Request received for Showing Repossession Status . . . . .");
		try{
			bean.setId(repossessionId);		
			model.addAttribute("beans", bean);
		}
		catch(Exception exp){
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occurred while Showing Repossession Status - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bean.getListResult())) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("bean", bean);
		return "contract.repossession.status.update";
	}
	

	
	
	/**
	 * Method returns the contract attachments info based on contract Id 
	 * 
	 * @param bean
	 * @param model
	 * @param locale
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/viewAttachments", method = {RequestMethod.GET })
	public String getRepossessionAttachments(@ModelAttribute(value = "bean") RepossessionBean bean, Model model, Locale locale)throws Exception{
		
		LOG.info("Request received for retrieving repossession attachments . . . . .");
		RepossessionBean result = null;
		
		boolean isFail = false;
		try{
			result = repossessionService.getRepossessionAttachments(bean.getContractId());
			
		}catch(Exception exp){
			isFail = true;
			result.addMessage(Message.ERROR, msgSrc.getMessage("contract.attachment.view.fail", null, locale));
			LOG.error("Error occurred while retrieving repossession attachments - " + ExceptionUtils.getStackTrace(exp));
		}

		if(CollectionUtils.isEmpty(result.getAttachments())){
			isFail = true;
			result.addMessage(Message.INFO, msgSrc.getMessage("contract.attachment.view.notpresent", null, locale));
		}

		model.addAttribute("bean", result);
		
		if(isFail){
			return "contract.repossession.detail.view";
		}
		
		return "contract.repossession.attachments";
	}
		
	
	@RequestMapping(value = "/downloadAttachment/{id}", method = RequestMethod.GET)
	public void downloadRepossessionAttachment(@PathVariable(value = "id") String attachmentId, HttpServletRequest httpServletRequest, HttpServletResponse response){
		
		LOG.info("Request received for Download Repossession Attachment . . . . .");
		
		try{
			RepossessionBean repossessionBean = repossessionService.getRepossessionAttachment(Integer.parseInt(attachmentId));
			RepossessionAttachmentDto attachmentDto = repossessionBean.getAttachments().get(0);
			
			response.setContentType(MIME_TYPE);
			response.setHeader(RESPONSE_HEADER, String.format("attachment; filename=\"%s\"",
					attachmentDto.getAttachmentName()));
			
			IOUtils.write(Base64.decode(attachmentDto.getAttachmentData()), response.getOutputStream());
			
		}catch(Exception exp){
			LOG.error("Error occurred while Downloading Repossession Attachment - " + ExceptionUtils.getStackTrace(exp));
			
		}
	}
	
	
	@RequestMapping(value = "/reassigns", method = {RequestMethod.GET})
	public String reassignRepossession(@PathParam("contractId") String contractId,@PathParam("repossessionId") Integer repossessionId,
			@PathParam("nextApproverLevel") String nextApproverLevel,
			@ModelAttribute(value = "bean") RepossessionBean bean, Model model,
			Locale locale,HttpServletRequest httpServletRequest) throws Exception{
		
		RepossessionBean repossessionBean=null;		
		LOG.info("Reassigning  Repossession to other level approver");
		try{
			repossessionBean = new RepossessionBean();
			repossessionBean.setRepossessionId(repossessionId);
			repossessionBean.setContractId(contractId);
			repossessionBean.setNextApproverLevel(nextApproverLevel);
		   // List<String> approverEmailIds = repossessionService.getNextApproverLevelEmails(repossessionBean.getRepossessionId());		
		    repossessionService.assignContract(repossessionBean, null,locale);
		}
		catch(Exception exp){
			repossessionBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occurred while  Reassign Repossession - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(repossessionBean.getListResult())) {
			repossessionBean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("bean", repossessionBean);
		return "contract.repossession.view";
	}
	@RequestMapping(value = "/updateStatus", method = {RequestMethod.POST})
	public @ResponseBody Map<String, Object> updateRepossessionStatus(			
			@ModelAttribute(value = "bean") RepossessionBean bean, Model model, 
			Locale locale,HttpServletRequest httpServletRequest) throws Exception{
		LOG.debug("Updating  Repossession status");
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			//bean.setId(repossessionId);
			repossessionService.updateRepossessionStatus(bean,locale);
		}
		catch(Exception exp){
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			map.put(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			map.put(Message.INFO, "");
			LOG.error("Updating the status of Repossession "+ ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bean.getListResult())) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
			map.put(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
			map.put(Message.ERROR, "");
		}		
		model.addAttribute("bean", bean);		
		//return "contract.termination.view";
		
		
		
		return map;
	}
	
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String getRepossessionContracts(/*@ModelAttribute(value = "bean") ContractTerminationBean bean,*/ Model model, HttpServletRequest request, Locale locale,
			HttpServletResponse response)throws Exception{
		
		LOG.info("Getting list of Repossession contracts based on Approver's Email Id");
		RepossessionBean result = null;
		boolean isFail = false;
		try{
			result = repossessionService.getRepossessionContracts();
			
			
		}catch(Exception exp){
			isFail = true;
			
			if(isFail){
				return "contract.repossession.view";
			}
			LOG.error("Error during Repossession contracts view"+ ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(result.getListResult())) {
			isFail = true;
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
			
		}
		
		model.addAttribute("bean", result);
		return "contract.repossession.view";
	}
	
	
	
	
	@RequestMapping(value = "/viewBidder", method = RequestMethod.GET)
	public String viewBidder(/*@ModelAttribute(value = "bean") ContractTerminationBean bean,*/@PathParam( value = "contractId") String contractId, @PathParam( value = "repossessionId") Long repossessionId, Model model, HttpServletRequest request, Locale locale,
			HttpServletResponse response)throws Exception{
		
		LOG.info("Getting list of bidders");
		BidderBean result = null;
		
		try{
			result = bidderService.getBidderList(contractId);
			
			
		}catch(Exception exp){
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error during bidders view" + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("bean", result);
		return "bidder.view";
	}
	
	
	
	@RequestMapping(value = "/bidderDetails", method = RequestMethod.GET)
	public String bidderDetails(@PathParam( value = "id") Long id,
			/*@ModelAttribute(value = "bidderBean")*/ BidderBean bidderBean, Model model,Locale locale)throws Exception{
		LOG.info("showing list of bidders");
		try{
			
			bidderBean.setId(id);	
		    Bidder bidder= bidderDao.findByBidderId(id);		  
		    BidderDto bidderDto=new BidderDto();		    
		    bidderDto.setAmount(bidder.getAmount());	
		    bidderDto.setApplId(bidder.getApplId());
		    bidderDto.setApprovedDate(bidder.getApprovedDate());
		    bidderDto.setBidderName(bidder.getBidderName());
		    bidderDto.setEntryDate(bidder.getEntryDate());
		    bidderDto.setIdNumber(bidder.getIdNumber());
		    bidderDto.setPhoneNumber(bidder.getPhoneNumber());
		    bidderDto.setRepossessionId(Integer.valueOf(bidder.getRepossessionId().intValue()));
		    bidderDto.setRequestDate(bidder.getRequestDate());
		    bidderDto.setUserApprove(bidder.getUserApprove());	  
		    bidderDto.setUserRequest(bidder.getUserRequest());
		    bidderDto.setStatus(bidder.getStatus().toString());
		    bidderDto.setId(bidder.getId());
		    
		   	List<BidderDto> bidderDtoList=new ArrayList<BidderDto>();			
		   	bidderDtoList.add(bidderDto);
			bidderBean.setBidderList(bidderDtoList);
		  
		   
		   
		}
		catch(Exception exp){
			bidderBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("error during showing list of bidders" + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bidderBean.getListResult())) {
			bidderBean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("beans", bidderBean);
		return "bidder.detail.view";
	}
	
	@RequestMapping(value = "/updateBidderStatus", method = {RequestMethod.POST})
	public @ResponseBody Map<String, Object> updateBidderStatus(			
			@ModelAttribute(value = "bidderBean")BidderBean bidderBean, Model model, 
			Locale locale,HttpServletRequest httpServletRequest) throws Exception{
		LOG.info("Updating the  bidder status ");
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			//bean.setId(repossessionId);
			bidderService.updateBidderStatus(bidderBean,locale);
		}
		catch(Exception exp){
			bidderBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			map.put(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			map.put(Message.INFO, "");
			LOG.error("Error during the  bidder status "+ ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bidderBean.getListResult())) {
			bidderBean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
			map.put(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
			map.put(Message.ERROR, "");
		}		
		model.addAttribute("bean", bidderBean);		
		//return "contract.termination.view";
		
		
		
		return map;
	}
	
	@RequestMapping(value = "/updateStatusBidder", method = RequestMethod.GET)
	public String updateStatusBidder(@PathParam("repossessionId")Long repossessionId,@PathParam("contractId") String contractId, @PathParam("bidder") Integer bidder,	
			/*@ModelAttribute(value = "bidderBean")*/BidderBean bidderBean,
		Locale locale,Model model)throws Exception{
		LOG.info("Updating the  status bidder page");
		try{
			bidderBean.setId(repossessionId);	
			bidderBean.setBidderId(bidder);
			bidderBean.setContractId(contractId);	
			model.addAttribute("beans", bidderBean);
		}
		catch(Exception exp){
			bidderBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Updating the  status bidder page" + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bidderBean.getListResult())) {
			bidderBean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("bean", bidderBean);
		return "bidder.status.update";
	}
	
	
	/*@RequestMapping(value = "/searchForm", method = RequestMethod.GET)
	public String searchForm(@ModelAttribute(value = "bean") ContractTerminationBean bean, 
			@PathParam("appleId")String appleId,
			@PathParam("customerName")String customerName,
			@PathParam("approvalStatus")String approvalStatus,			
			Model model, HttpServletRequest request, Locale locale,
			HttpServletResponse response)throws Exception{
		
		LOG.info("Getting list of Repossession contracts ");
		RepossessionBean result = null;
		boolean isFail = false;
		try{
			//result = repossessionService.getRepossessionFormData(appleId,customerName,approvalStatus);			
			
		}catch(Exception exp){
			isFail = true;
			
			if(isFail){
				return "contract.repossession.view";
			}
			LOG.error("Error during Repossession contracts view"+ ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(result.getListResult())) {
			isFail = true;
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));			
		}		
		model.addAttribute("bean", result);
		return "contract.repossession.view";
	}*/
	
}









