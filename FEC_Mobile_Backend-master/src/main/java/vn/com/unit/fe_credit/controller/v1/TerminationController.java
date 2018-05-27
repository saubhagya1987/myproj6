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
import vn.com.unit.fe_credit.bean.v1.ContractAttachmentDto;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationDto;
import vn.com.unit.fe_credit.dao.v1.ContractTerminationDao;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;
import vn.com.unit.fe_credit.service.v1.ContractTerminationService;

import com.sun.org.apache.xml.internal.security.utils.Base64;

@SessionAttributes("bean")
@Controller
@RequestMapping("/contract/termination/")
public class TerminationController {

	private static final Logger LOG = LoggerFactory.getLogger(TerminationController.class);
	
	private static final String MIME_TYPE = "application/octet-stream";
	private static final String RESPONSE_HEADER = "Content-Disposition";

	@Autowired
	MessageSource msgSrc;
	
	@Autowired
	UserProfile userProfile;
	
	@Autowired
	ContractTerminationService contractTerminationService;	
	@Autowired
	ContractTerminationDao contractTerminationDao;

/*@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String showTerminatedContracts(Model model,Locale locale)throws Exception{
		
        ContractTerminationBean result = null;
		
		try{
			//result = contractTerminationService.getTerminatedContracts();
			result = getContractTerminationBean();
			
			
			System.out.println("ContractController.getTerminatedContracts() = " + result);
			
		}catch(Exception exp){
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
		}
		model.addAttribute("bean", result);
		return "contract.termination.view";
	}*/
	

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String showTerminatedContractDetails(@PathParam( value = "contractTerminationId") Integer contractTerminationId,@ModelAttribute(value = "bean") 
	ContractTerminationBean bean, Model model,Locale locale)throws Exception{
		LOG.info("Show Terminated Contract Details");		
		try{
			
		    bean.setId(contractTerminationId);	
		    ContractTermination termination= contractTerminationDao.findByTerminationId(contractTerminationId);
		  
		    ContractTerminationDto contractTermination=new ContractTerminationDto();
			contractTermination.setCustomerId(termination.getCustomerId());			
			contractTermination.setCustomerName(termination.getCustomerName());
			contractTermination.setUnitCode(termination.getUnitCode());
			contractTermination.setChargeOffFlag(termination.getChargeOffFlag());
			contractTermination.setOverdueCharges(termination.getOverdueCharges());
			contractTermination.setInterestOutstanding(termination.getInterestOutstanding());
			contractTermination.setPrepaymentPenalty(termination.getPrepaymentPenalty());
			contractTermination.setRefunds(termination.getRefunds());
			contractTermination.setInterFund(termination.getInterFund());
			contractTermination.setTotalAmontPaidByCustomer(termination.getTotalAmontPaidByCustomer());
			contractTermination.setPenaltyFeeRequested(termination.getPenaltyFeeRequested());
			contractTermination.setEarlyTerminationFeeRequested(termination.getEarlyTerminationFeeRequested());
			contractTermination.setBilledInst(termination.getBilledInst());
			contractTermination.setPhoneNumber(termination.getPhoneNumber());
			contractTermination.setPayer(termination.getPayer());
			contractTermination.setReason(termination.getReason());
			contractTermination.setApproverLevel(termination.getApproverLevel());			
			contractTermination.setContractTerminationId(termination.getContractTerminationId());
			contractTermination.setContractId(termination.getContractId());
			contractTermination.setDpd(termination.getDpd());
			contractTermination.setBucket(termination.getBucket());
			contractTermination.setPrincipleOutstanding(termination.getPrincipleOutstanding());
			List<ContractTerminationDto> contractTerminationDtoList=new ArrayList<ContractTerminationDto>();			
			contractTerminationDtoList.add(contractTermination);
			bean.setContractTerminationDtoList(contractTerminationDtoList);
		  
		   
		   
		}
		catch(Exception exp){
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occured  during Terminated Contract Details - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bean.getListResult())) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		model.addAttribute("beans", bean);
		//model.addAttribute("bean", bean);
		return "contract.termination.detail.view";
	}

	@RequestMapping(value = "/attachments", method = RequestMethod.GET)
	public String showContractAttachments(Model model)throws Exception{
		
		return "contract.termination.attachments";
	}
	
	
	@RequestMapping(value = "/status", method = RequestMethod.GET)
	public String showContractStatus(@PathParam(value = "contractTerminationId") Integer contractTerminationId,@ModelAttribute(value = "bean") ContractTerminationBean bean,
		Locale locale,Model model)throws Exception{
		LOG.info("show Contract Status");
		try{
			bean.setId(contractTerminationId);		
			model.addAttribute("beans", bean);
		}
		catch(Exception exp){
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occured  during Contract Status - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(bean.getListResult())) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("bean", bean);
		return "contract.termination.status.update";
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
	@RequestMapping(value = "/viewAttachments", method = { RequestMethod.GET })
	public String getContractAttachments(@ModelAttribute(value = "bean") ContractTerminationBean bean, Model model, Locale locale)throws Exception{
		
		// for testing purpose
		/*bean = new ContractTerminationBean();
		bean.setContractId("1234");*/
		
		LOG.info("Request received for retrieving attachments . . . . .");
		ContractTerminationBean result = null;
		
		boolean isFail = false;
		try{
			result = contractTerminationService.getContractAttachments(bean.getContractId());
			
		}catch(Exception exp){
			isFail = true;
			result.addMessage(Message.ERROR, msgSrc.getMessage("contract.attachment.view.fail", null, locale));
			LOG.error("Error occured  during retrieving attachments - " + ExceptionUtils.getStackTrace(exp));
		}

		if(CollectionUtils.isEmpty(result.getAttachments())){
			isFail = true;
			result.addMessage(Message.INFO, msgSrc.getMessage("contract.attachment.view.notpresent", null, locale));
		}

		model.addAttribute("bean", result);
		
		if(isFail){
			//return "contract.termination.detail.view";
			return "contract.termination.attachments";
		}
		
		return "contract.termination.attachments";
	}
		
	
	@RequestMapping(value = "/downloadAttachment/{id}", method = RequestMethod.GET)
	public void downloadAttachment(@PathVariable(value = "id") String attachmentId, HttpServletRequest httpServletRequest, HttpServletResponse response){
		
		LOG.info("Request received for downloading attachment . . . . .");
		
		try{
			ContractTerminationBean terminationBean = contractTerminationService.getContractAttachment(Integer.parseInt(attachmentId));
			ContractAttachmentDto attachmentDto = terminationBean.getAttachments().get(0);
			
			response.setContentType(MIME_TYPE);
			response.setHeader(RESPONSE_HEADER, String.format("attachment; filename=\"%s\"",
					attachmentDto.getAttachmentName()));
			
			IOUtils.write(Base64.decode(attachmentDto.getAttachmentData()), response.getOutputStream());
			
		}catch(Exception exp){
			LOG.error("Error occurred while downloading attachment - " + ExceptionUtils.getStackTrace(exp));
			
		}
	}
	
	
	@RequestMapping(value = "/reassign", method = {RequestMethod.POST, RequestMethod.GET})
	public String reassignContract(@PathParam("contractId") String contractId,@PathParam("contractTerminationId") Integer contractTerminationId,
			@PathParam("nextApproverLevel") String nextApproverLevel,
			@ModelAttribute(value = "bean") ContractTerminationBean bean, Model model,
			Locale locale,HttpServletRequest httpServletRequest) throws Exception{
		
		ContractTerminationBean terminationBean=null;		
		LOG.debug("Reassigning terminated contract to other level approver");
		try{
			terminationBean = new ContractTerminationBean();
			terminationBean.setContractTerminationId(contractTerminationId);
			terminationBean.setContractId(contractId);
			terminationBean.setNextApproverLevel(nextApproverLevel);
		    List<String> approverEmailIds = contractTerminationService.getNextApproverLevelEmails(terminationBean.getContractTerminationId());		
		    contractTerminationService.assignContract(terminationBean, approverEmailIds,locale);
		}
		catch(Exception exp){
			terminationBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occurred while Reassigning terminated contract - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(terminationBean.getListResult())) {
			terminationBean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		
		model.addAttribute("bean", terminationBean);
		return "contract.termination.view";
	}
	@RequestMapping(value = "/updateStatus", method = {RequestMethod.POST})
	public @ResponseBody Map<String, Object> updateContractStatus(
			@PathParam("contractId") String contractId,@ModelAttribute(value = "bean") ContractTerminationBean bean, Model model, 
			Locale locale,HttpServletRequest httpServletRequest) throws Exception{
		LOG.debug("Updating the status of terminated contract");
			
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
		bean = contractTerminationService.updateContractStatus(bean,contractId,locale);
		}
		catch(Exception exp){
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			map.put(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			map.put(Message.INFO, "");
			LOG.error("Error occurred while Updating the status of terminated contract - " + ExceptionUtils.getStackTrace(exp));
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
	public String getTerminatedContracts(/*@ModelAttribute(value = "bean") ContractTerminationBean bean,*/ Model model, HttpServletRequest request, Locale locale,
			HttpServletResponse response)throws Exception{
		
		LOG.debug("Getting list of terminated contracts based on Approver's Email Id : ");
		ContractTerminationBean result = null;
		
		try{
			result = contractTerminationService.getTerminatedContracts();
			
			
		}catch(Exception exp){
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
			LOG.error("Error occurred while getting terminated contracts List - " + ExceptionUtils.getStackTrace(exp));
		}
		
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
			
		}
		
		model.addAttribute("bean", result);
		return "contract.termination.view";
	}
	
	
	/*@ModelAttribute("terminationObject")
	private ContractTerminationBean getContractTerminationBean(){
		ContractTerminationDto contractTerminationDto=new ContractTerminationDto();
		contractTerminationDto.setCustomerId(1);
		//contractTerminationDto.setId(1);
		contractTerminationDto.setCustomerName("yong");
		contractTerminationDto.setUnitCode("qwe");
		contractTerminationDto.setChargeOffFlag("asd");
		contractTerminationDto.setOverdueCharges(12.4);
		contractTerminationDto.setInterestOutstanding("14");
		contractTerminationDto.setPrepaymentPenalty(14.7);
		contractTerminationDto.setRefunds(16.3);
		contractTerminationDto.setInterFund(19.7);
		contractTerminationDto.setTotalAmontPaidByCustomer(200);
		contractTerminationDto.setPenaltyFeeRequested(355);
		contractTerminationDto.setEarlyTerminationFeeRequested(433);
		contractTerminationDto.setBilledInst(333);
		contractTerminationDto.setPhoneNumber("1234567890");
		contractTerminationDto.setPayer("1234");
		contractTerminationDto.setReason("rrr");
		contractTerminationDto.setApproverLevel("ddsd");
		contractTerminationDto.setAssetsCondition("dfdff");
		contractTerminationDto.setFinancialConditionAssessment("dssd");
		contractTerminationDto.setUpdationDate("dds");
		contractTerminationDto.setProposedSalesPrice(123344);
		contractTerminationDto.setUpdatedBy("sgss");
		contractTerminationDto.setRoleCode("ffd");
		//contractTerminationDto.setDpd(BigDecimal.valueOf(323.55));
		contractTerminationDto.setBucket(BigDecimal.valueOf(53433.55));
		contractTerminationDto.setPrincipleOutstanding(BigDecimal.valueOf(43443.55));
		contractTerminationDto.setContractTerminationId(11);
		contractTerminationDto.setContractId("123");
		
		
		ContractTerminationDto contractTermination=new ContractTerminationDto();
		contractTermination.setCustomerId(2);
		//contractTermination.setId(2);
		contractTermination.setCustomerName("lee");
		contractTermination.setUnitCode("fsdf");
		contractTermination.setChargeOffFlag("vcxvfcvdf");
		contractTermination.setOverdueCharges(13.4);
		contractTermination.setInterestOutstanding("15");
		contractTermination.setPrepaymentPenalty(18.7);
		contractTermination.setRefunds(18.3);
		contractTermination.setInterFund(23.7);
		contractTermination.setTotalAmontPaidByCustomer(230);
		contractTermination.setPenaltyFeeRequested(665);
		contractTermination.setEarlyTerminationFeeRequested(773);
		contractTermination.setBilledInst(333);
		contractTermination.setPhoneNumber("1234567891");
		contractTermination.setPayer("123224");
		contractTermination.setReason("qweee");
		contractTermination.setApproverLevel("vsvsv");
		contractTermination.setAssetsCondition("vvsds");
		contractTermination.setFinancialConditionAssessment("jfghf");
		contractTermination.setUpdationDate("ttr");
		contractTermination.setProposedSalesPrice(1263637);
		contractTermination.setUpdatedBy("jnkjdb");
		contractTermination.setRoleCode("dvdv");
		//contractTermination.setDpd(BigDecimal.valueOf(123.55));
		contractTermination.setBucket(BigDecimal.valueOf(3333.55));
		contractTermination.setPrincipleOutstanding(BigDecimal.valueOf(3334443.55));
		contractTermination.setContractTerminationId(11);
		contractTermination.setContractId("123");
		
		List<ContractTerminationDto> contractTerminationDtoList=new ArrayList<ContractTerminationDto>();
		contractTerminationDtoList.add(contractTerminationDto);
		contractTerminationDtoList.add(contractTermination);
		
		ContractTerminationBean bean = new ContractTerminationBean();
		bean.setContractTerminationId(11);
		
		bean.setNextApproverLevel("abcd");		
		bean.setContractTerminationDtoList(contractTerminationDtoList);
		
		
		
		
		
		
		return bean;
	}
	
	private ContractTerminationDto getContractTerminationList(){
		ContractTerminationDto contractTerminationDto=new ContractTerminationDto();
		contractTerminationDto.setContractTerminationId(11);
		contractTerminationDto.setContractId("123");
		contractTerminationDto.setCustomerId(1);
		contractTerminationDto.setCustomerName("yong");
		contractTerminationDto.setUnitCode("qwe");
		contractTerminationDto.setChargeOffFlag("asd");
		contractTerminationDto.setOverdueCharges(12.4);
		contractTerminationDto.setInterestOutstanding("14");
		contractTerminationDto.setPrepaymentPenalty(14.7);
		contractTerminationDto.setRefunds(16.3);
		contractTerminationDto.setInterFund(19.7);
		contractTerminationDto.setTotalAmontPaidByCustomer(200);
		contractTerminationDto.setPenaltyFeeRequested(355);
		contractTerminationDto.setEarlyTerminationFeeRequested(433);
		contractTerminationDto.setBilledInst(333);
		contractTerminationDto.setPhoneNumber("1234567890");
		contractTerminationDto.setPayer("1234");
		contractTerminationDto.setReason("rrr");
		contractTerminationDto.setApproverLevel("ddsd");
		contractTerminationDto.setAssetsCondition("dfdff");
		contractTerminationDto.setFinancialConditionAssessment("dssd");
		contractTerminationDto.setUpdationDate("dds");
		contractTerminationDto.setProposedSalesPrice(123344);
		contractTerminationDto.setUpdatedBy("sgss");
		contractTerminationDto.setRoleCode("ffd");	
		//contractTerminationDto.setDpd(BigDecimal.valueOf(123.55));
		contractTerminationDto.setBucket(BigDecimal.valueOf(3333.55));
		contractTerminationDto.setPrincipleOutstanding(BigDecimal.valueOf(3334443.55));
		
		
		return contractTerminationDto;
	}
*/	
}
