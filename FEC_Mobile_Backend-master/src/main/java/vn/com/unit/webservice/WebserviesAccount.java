package vn.com.unit.webservice;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.json.JSONException;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import vn.com.unit.fe_credit.bean.MessageInboxBean;
import vn.com.unit.fe_credit.config.Convert;
import vn.com.unit.fe_credit.config.CrunchifyMySQLJDBCConnection;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.config.SystemConfig.MessageType;
import vn.com.unit.fe_credit.entity.ApplyNow;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.Contact;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.CustomerCMSRead;
import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.CustomerHobbyHistory;
import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.DataImg;
import vn.com.unit.fe_credit.entity.DataJson;
import vn.com.unit.fe_credit.entity.DocumentInfo;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.Masterdata;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.entity.PostCustomerHobby;
import vn.com.unit.fe_credit.entity.PostCustomerWish;
import vn.com.unit.fe_credit.entity.SaveMess;
import vn.com.unit.fe_credit.entity.ViewCms;
import vn.com.unit.fe_credit.entity.ViewCustomer;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ApplyNowLogService;
import vn.com.unit.fe_credit.service.ApplyNowService;
import vn.com.unit.fe_credit.service.BannerService;
import vn.com.unit.fe_credit.service.BranchService;
import vn.com.unit.fe_credit.service.CMSCategoryService;
import vn.com.unit.fe_credit.service.CMSService;
import vn.com.unit.fe_credit.service.ContactService;
import vn.com.unit.fe_credit.service.CustomerActivityLogService;
import vn.com.unit.fe_credit.service.CustomerCMSReadService;
import vn.com.unit.fe_credit.service.CustomerHobbyHistoryService;
import vn.com.unit.fe_credit.service.CustomerHobbyService;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.CustomerWishService;
import vn.com.unit.fe_credit.service.DocumentInfoService;
import vn.com.unit.fe_credit.service.HobbyService;
import vn.com.unit.fe_credit.service.LoanDetailService;
import vn.com.unit.fe_credit.service.LoanService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MasterdataService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.POSLogService;
import vn.com.unit.fe_credit.service.POSService;
import vn.com.unit.fe_credit.service.PushNotificationService;
import vn.com.unit.fe_credit.service.WishService;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.json.JsonReturn;

/**
 * @author CongDT
 * @since Jun 28, 2016
 */
@Path("/api")
public class WebserviesAccount extends SpringBeanAutowiringSupport {

	Logger logger = LoggerFactory.getLogger("WebserviesAccount");

	@Autowired
	AccountService accountService;

	@Autowired
	POSLogService posLogService;

	@Autowired
	CustomerCMSReadService customerCMSReadService;

	@Autowired
	ContactService contactService;

	@Autowired
	MasterdataService masterdataService;

	@Autowired
	MasterdataDetailService masterdataDetailService;

	@Autowired
	CustomerService customerService;

	@Autowired
	MessageService messageService;

	@Autowired
	BannerService bannerService;

	@Autowired
	LoanDetailService loanDetailService;

	@Autowired
	BranchService branchService;

	@Autowired
	POSService posService;

	@Autowired
	LoanService loanService;

	@Autowired
	ApplyNowService applyNowService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	DocumentInfoService documentInfoService;

	@Autowired
	CMSService cMSService;

	@Autowired
	CMSCategoryService cmsCategoryService;

	@Autowired
	ActivityLogService activityLogService;

	@Autowired
	CustomerHobbyService customerHobbyService;

	@Autowired
	CustomerHobbyHistoryService customerHobbyHistoryService;

	@Autowired
	HobbyService hobbyService;

	@Autowired
	CustomerWishService customerWishService;

	@Autowired
	ApplyNowLogService applyNowLogService;

	@Autowired
	WishService wishService;

	@Autowired
	CustomerActivityLogService customerActivityLogService;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private PushNotificationService pushNotificationService;

	Locale locale = new Locale("EN");

	/**
	 * <pre>
	 * Register 
	 * Đăng ký tài khoản mới
	 * yêu cầu mã OTP
	 * 
	 * </pre>
	 * 
	 * @param customer
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getSMSCode")
	public Response getSMSCode(@RequestBody Customer customer) {

		JsonReturn jsonReturn = new JsonReturn();
		Map<String, Object> jsonData = new LinkedHashMap<>();

		try {

			Customer customerFind = customerService.findByIdCardNum(customer.getIdCardNumber(), null, null, customer.getCellPhone());

			if (customerFind == null) {
				// Thông tin khách hàng chưa tồn tại
				// Kiểm tra số điện thoại
				Customer customerCellPhone = customerService.findByIdCellPhone(customer.getCellPhone());
				if (customerCellPhone != null) {
					throw new Exception(msgSrc.getMessage("web.register.cellphone", null, locale));
				}

				// Kiểm tra CMND
				Customer customerByIdCardNum = customerService.findByIdCardNum(customer.getIdCardNumber(), null, null, null);
				if (customerByIdCardNum != null && SystemConfig.STATUS_ACTIVE.equals(customerByIdCardNum.getStatus())) {
					throw new Exception("IdCardNumber is exists.");
				}

				customerFind = new Customer();

				if (customerByIdCardNum != null) {
					customerFind.setCustomerId(customerByIdCardNum.getCustomerId());
				}

				customerFind.setIdCardNumber(customer.getIdCardNumber());
				customerFind.setCellPhone(customer.getCellPhone());
				customerFind.setFullName(customer.getFullName());
				customerFind.setFirstName(customer.getFirstName());
				customerFind.setLastName(customer.getLastName());

			} else {

				// Khách hàng đã tồn tại, kiểm tra status
				Integer status = customerFind.getStatus();
				if (SystemConfig.STATUS_ACTIVE.equals(status)) {
					throw new Exception("IdCardNumber is exists.");
				}

			}

			checkToSendOTP(customerFind, jsonReturn, jsonData, false);
			customerService.saveCustomerAccount(customerFind);

			jsonData.put("customerId", customerFind.getCustomerId());
			jsonData.put("refID", customerFind.getRefId());
			jsonData.put("respDate", new Date());
			jsonReturn.setSuccess(true);

			SendMessSoapServies.sendMess("+84" + customerFind.getCellPhone().substring(1, customerFind.getCellPhone().length()),
					msgSrc.getMessage("web.register.sms", null, locale) + customerFind.getSmsCode() + ".", "Status");

		} catch (Exception e) {
			jsonReturn.setMsg(e.getMessage());
			jsonReturn.setSuccess(false);
		}

		jsonReturn.setData(jsonData);
		return Response.status(200).entity(jsonReturn).build();

	}

	/**
	 * 
	 * Kiểm tra khi lấy mã OTP
	 * 
	 * @param customer
	 * @param jsonReturn
	 * @param jsonData
	 * @throws Exception
	 */
	private void checkToSendOTP(Customer customer, JsonReturn jsonReturn, Map<String, Object> jsonData, boolean isResend) throws Exception {

		final int MAX_OTP = 5;
		final int OTP_LIVE = 5; // minute
		final int OTP_BLOCK = 1; // DAY

		// Thời gian khóa OTP
		Date request_OTP_BLOCKED_DUE = customer.getRequest_OTP_BLOCKED_DUE();
		if (request_OTP_BLOCKED_DUE == null) {
			request_OTP_BLOCKED_DUE = new Date();
		}

		Integer numberGetCode = customer.getNumberGetCode() == null ? 0 : customer.getNumberGetCode();
		if (request_OTP_BLOCKED_DUE.after(new Date())) {
			jsonData.put("otpBlockedDue", customer.getRequest_OTP_BLOCKED_DUE());
			jsonReturn.setCodeError("ExceedTheNumberOfTimes");
			throw new Exception(msgSrc.getMessage("web.register.exceedTheNumberOfTimes", null, locale));
		} else {
			// Sau 24h bi khoa se reset lai so lan lay OTP
			if (numberGetCode >= MAX_OTP) {
				numberGetCode = 0;
			}
		}

		// Kiểm tra số lần đã lấy mã OTP
		// Nếu số lần lấy OTP quá 5 lần thì khóa trong 24h
		numberGetCode = numberGetCode + 1;
		customer.setNumberGetCode(numberGetCode);
		if (numberGetCode >= MAX_OTP) {
			// Khóa lại trong vòng 24h
			customer.setRequest_OTP_BLOCKED_DUE(DateUtils.addDays(new Date(), OTP_BLOCK));
			jsonData.put("otpBlockedDue", customer.getRequest_OTP_BLOCKED_DUE());
		}

		// OTP random trong khoảng 1000-9999, thời gian sống là 5 phút
		Integer rand = (int) (Math.random() * 9000) + 1000;
		customer.setSmsCode(rand.toString());
		customer.setOtp_LIVE_DUE(DateUtils.addMinutes(new Date(), OTP_LIVE));
		customer.setDateGetCode(new Date());

		customer.setRefId(UUID.randomUUID().toString());

		if (BooleanUtils.isNotTrue(isResend)) {
			// Reset lại số lần nhập sai OTP khi thực hiện một quy trình mới
			customer.setWrg_OTP_RESET_PWD_ATTEMPTS(0);
			customer.setWrg_OTP_RESET_PWD_BAN_DUE(null);
		}

	}

	/**
	 * <pre>
	 * Yêu cầu gửi lại Mã OTP để reset password, các bước:
	 * - Kiểm tra khách hàng đã tồn tại hãy không
	 * </pre>
	 * 
	 * @param customer
	 * @return
	 * @author CongDT
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getResendSMSCode")
	public Response getResendSMSCode(@RequestBody Customer customer) {

		Map<String, Object> jsonData = new LinkedHashMap<>();
		JsonReturn jsonReturn = new JsonReturn();

		try {

			// Kiểm tra thông tin khách hàng
			Customer existsCustomer = customerService.findByIdCardNum(customer.getIdCardNumber(), null, null, customer.getCellPhone());
			if (existsCustomer == null) {
				throw new Exception(msgSrc.getMessage("web.register.cardphone.correct", null, locale));
			}

			// Kiểm tra để gửi mã OTP
			checkToSendOTP(existsCustomer, jsonReturn, jsonData, true);

			customerService.saveCustomerAccount(existsCustomer);

			jsonData.put("customerId", existsCustomer.getCustomerId());
			jsonData.put("refID", existsCustomer.getRefId());
			jsonData.put("respDate", new Date());

			SendMessSoapServies.sendMess("+84" + existsCustomer.getCellPhone().substring(1, existsCustomer.getCellPhone().length()),
					msgSrc.getMessage("web.register.sms", null, locale) + existsCustomer.getSmsCode() + ".", "Status");

			jsonReturn.setSuccess(true);

		} catch (Exception e) {
			jsonReturn.setMsg(e.getMessage());
			jsonReturn.setSuccess(false);
		}

		jsonReturn.setData(jsonData);
		return Response.status(200).entity(jsonReturn).build();

	}

	@POST
	@Path("/smsActivate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response smsActivate(@RequestBody Customer customerParam) {

		JsonReturn jsonReturn = new JsonReturn();
		Map<String, Object> jsonData = new LinkedHashMap<>();

		try {

			Customer customerFind = customerService.findByIdCardNum(customerParam.getIdCardNumber(), null, null, null);
			if (customerFind == null) {
				throw new Exception(msgSrc.getMessage("web.register.cardsms.correct", null, locale));
			}

			checkOTPInput(customerFind, jsonData, customerParam.getSmsCode());

			customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_REGISTER_ACTIVATE, SystemConfig.LOGIN_MOBILE, locale,
					SystemConfig.MOBILE, customerFind.getCustomerId().toString(), null);

			jsonReturn.setSuccess(true);

		} catch (Exception e) {
			jsonReturn.setMsg(e.getMessage());
			jsonReturn.setSuccess(false);
		}

		jsonReturn.setData(jsonData);
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/register")
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(@RequestBody Customer customerParam) {
		Map<String, Object> hashMap = new LinkedHashMap<>();
		Customer customerFind = customerService.findByIdCardNum(customerParam.getIdCardNumber(), null, null, null);
		JsonReturn jsonReturn = new JsonReturn();
		if (customerFind != null) {

			customerFind.setPassword(Utils.encryptMD5(customerParam.getPassword()));
			customerFind.setUserName(customerParam.getUserName());
			customerFind.setStatus(SystemConfig.STATUS_ACTIVE);
			customerFind.setSmsCode(null);
			customerFind.setNumberGetCode(0);
			customerFind.setDateGetCode(null);
			customerFind.setCodeMachine(customerParam.getCodeMachine());

			if (StringUtils.isBlank(customerParam.getDefaultLanguage())) {
				customerFind.setDefaultLanguage("vi");
			} else {
				customerFind.setDefaultLanguage(customerParam.getDefaultLanguage());
			}

			try {
				customerService.saveCustomerAccount(customerFind);
				String idCardNumber = customerParam.getIdCardNumber();
				String cellphone = customerParam.getCellPhone();
				customerService.markAsCustomerHasBeenInstalledTheMobileApp(idCardNumber, cellphone);

				try {
					customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_REGISTER_SUBMIT, SystemConfig.LOGIN_MOBILE, locale,
							SystemConfig.MOBILE, customerFind.getCustomerId().toString(), null, customerFind);
				} catch (Exception e) {
					e.printStackTrace();
				}

				hashMap.put("customerId", customerFind.getCustomerId());
				hashMap.put("cellPhone", customerFind.getCellPhone());
				hashMap.put("customerContractId", customerFind.getOldUserId());
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);

				MessageDashBoard messageDashBoard = new MessageDashBoard();
				Customer cus = customerService.findById(customerFind.getCustomerId());
				if (StringUtils.equals(cus.getDefaultLanguage(), "vi")) {
					List<Hotline> content = masterdataService.findDetail("TemplateWellcome_VI");
					List<Hotline> subject = masterdataService.findDetail("TemplateWellcomeTitle_VI");
					messageDashBoard.setSubject(subject.get(0).getName());
					messageDashBoard.setContent(content.get(0).getName());
				} else if (StringUtils.equals(cus.getDefaultLanguage(), "en")) {
					List<Hotline> content = masterdataService.findDetail("TemplateWellcome_EN");
					List<Hotline> subject = masterdataService.findDetail("TemplateWellcomeTitle_EN");
					messageDashBoard.setSubject(subject.get(0).getName());
					messageDashBoard.setContent(content.get(0).getName());
				}

				messageDashBoard.setCustomer(customerFind);
				messageDashBoard.setType(7);
				messageDashBoard.setStatus(1);
				messageDashBoard.setIsRead(0);
				messageDashBoard.setIsMsgUser(0);
				messageDashBoard.setCreatedDate(new Date());
				messageDashBoard.setIsParent(true);
				messageService.save(messageDashBoard);

				// messageDashBoard.setParentMsgId(messageDashBoard.getMessageID());
				// messageService.save(messageDashBoard);

				Locale locale = new Locale("en");
				activityLogService.saveActivityLog(SystemConfig.SEND_MESS_CUSTOMER, SystemConfig.MESSAGE, locale, SystemConfig.MOBILE,
						customerFind.getCustomerId().toString(), null);
				try {
					CrunchifyMySQLJDBCConnection crunchifyMySQLJDBCConnection = new CrunchifyMySQLJDBCConnection();
					crunchifyMySQLJDBCConnection.saveCustomer(customerFind.getIdCardNumber(), customerFind.getCustomerId());
				} catch (Exception e) {
					logger.debug("##CrunchifyMySQLJDBCConnection##", e);
				}

			} catch (Exception e) {

				jsonReturn.setData(null);
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(e.getMessage());
			}

			return Response.status(200).entity(jsonReturn).build();
		} else {
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(msgSrc.getMessage("web.register.card", null, locale));
			return Response.status(200).entity(jsonReturn).build();
		}
	}

	// Login
	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@RequestBody Customer customer) {
		Map<String, Object> hashMap = new LinkedHashMap<>();
		JsonReturn jsonReturn = new JsonReturn();
		Locale locale = new Locale("en");
		try {
			Customer customerFind = customerService.findByCustomerLogin(customer.getIdCardNumber(), Utils.encryptMD5(customer.getPassword()));
			if (customerFind != null) {
				customerFind.setCodeMachine(customer.getCodeMachine());
				hashMap.put("customerId", customerFind.getCustomerId());
				hashMap.put("customerContractId", customerFind.getOldUserId());
				hashMap.put("fullName", customerFind.getFullName());
				hashMap.put("cellPhone", customerFind.getCellPhone());
				hashMap.put("firstLogin", customerFind.getIsLogged());

				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				customerFind.setIsLogged(null);
				try {
					customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_LOGIN, SystemConfig.LOGIN_MOBILE, locale,
							SystemConfig.MOBILE, customerFind.getCustomerId().toString(), null, customer);
				} catch (Exception e) {
					e.printStackTrace();
				}

				customerService.saveCustomerAccount(customerFind);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.register.cardpass.correct", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	// Inbox
	@POST
	@Path("/getInboxList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getInboxList(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {

			Integer limit = message.getLimit();
			if (limit == null) {
				limit = 20;
			}

			Integer page = message.getPage();
			if (page == null) {
				page = 1;
			}

			Map<String, Object> dataMap = messageService.getInboxList(message.getCustomerId(), message.getCategory(), message.getTypeInbox(),
					SystemConfig.SEARCH_TYPE_LIST_ONLY, page, limit);
			@SuppressWarnings("unchecked")
			List<MessageInboxBean> messageLst = (List<MessageInboxBean>) dataMap.get("LIST");
			if (CollectionUtils.isNotEmpty(messageLst)) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(messageLst);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("No data.");
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();

	}

	@POST
	@Path("/markRead")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markRead(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			MessageDashBoard messageDashBoard = messageService.findMessagesWebService(message.getMessageID(), message.getCustomerId());
			if (messageDashBoard != null) {
				jsonReturn.setSuccess(true);
				messageDashBoard.setIsRead(1);
				messageDashBoard.setReadDate(new Date());
				messageService.save(messageDashBoard);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.mesidcusid.correct", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();

	}

	@POST
	@Path("/markUnRead")
	@Produces(MediaType.APPLICATION_JSON)
	public Response markUnRead(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			MessageDashBoard messageDashBoard = messageService.findMessagesWebService(message.getMessageID(), message.getCustomerId());
			if (messageDashBoard != null) {
				jsonReturn.setSuccess(true);
				messageDashBoard.setIsRead(0);
				messageService.save(messageDashBoard);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.mesidcusid.correct", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();

	}

	@POST
	@Path("/deleteMsg")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteMsg(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			MessageDashBoard messageDashBoard = messageService.findMessagesWebService(message.getMessageID(), message.getCustomerId());
			if (messageDashBoard != null) {
				jsonReturn.setSuccess(true);
				messageService.delete(messageDashBoard);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.mesidcusid.correct", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// messageDetail
	@POST
	@Path("/getMessageDetail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessageDetail(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<MessageInboxBean> messageLst = messageService.getMessageDetail(message.getMessageID(), message.getCustomerId());

			convertArrayToJSONArray(messageLst);
			if (messageLst != null) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(messageLst);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.mesidcusid.correct", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// message contract list
	@POST
	@Path("/getListMessageContract")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListMessageContract(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<MessageInboxBean> messageLst = messageService.getListMessageContract(message.getCustomerId(), message.getContractMsgId());
			if (messageLst != null && messageLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(messageLst);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// getMessageDetailContract
	@POST
	@Path("/getMessageDetailContract")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMessageDetailContract(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<MessageInboxBean> messageLst = messageService.getMessageDetailContract(message.getMessageID(), message.getCustomerId());

			convertArrayToJSONArray(messageLst);
			if (messageLst != null) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(messageLst);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.mescontractidcusid.correct", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// listmessage
	@POST
	@Path("/getListMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getListMessage(@RequestBody MessageDashBoard message) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<MessageInboxBean> messageLst = messageService.getListMessage(message.getCustomerId());
			if (messageLst != null && messageLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(messageLst);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	private void convertArrayToJSONArray(List<MessageInboxBean> messageLst) throws JSONException {

		for (MessageInboxBean messageInboxBean : messageLst) {
			String str = StringEscapeUtils.escapeXml(messageInboxBean.getSummary());
			messageInboxBean.setSummary(StringUtils.replace(str, "\n", "<br/>"));
			// replaceAll("(\r\n|\n)", "<br/>");
			ArrayList<DataJson> dataJsons = documentInfoService.getAttachment(messageInboxBean.getMessageID());
			messageInboxBean.setAttachment(dataJsons);
		}

	}

	// composeMessage
	@POST
	@Path("/sendMsg")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMsg(@RequestBody MessageMsg msg) {
		JsonReturn jsonReturn = new JsonReturn();

		try {

			checkFileSize(msg.getMsg().getAttachment());

			String subject = msg.getMsg().getSubject();
			String content = msg.getMsg().getContent();

			// if (StringUtils.isNotBlank(subject)) {
			// subject = Jsoup.parse(subject).text();
			// }
			// if (StringUtils.isNotBlank(content)) {
			// content = Jsoup.parse(content).text();
			// }

			MessageDashBoard messageDashBoard = new MessageDashBoard();
			messageDashBoard.setSubject(subject);
			messageDashBoard.setContent(content);
			Customer customer = customerService.findById(msg.getCustomerId());
			messageDashBoard.setCustomer(customer);
			messageDashBoard.setType(msg.getType());
			messageDashBoard.setStatus(1);
			messageDashBoard.setIsRead(1);
			messageDashBoard.setIsMsgUser(1);
			messageDashBoard.setCreatedDate(new Date());
			messageDashBoard.setCategory(msg.getCategory());
			messageDashBoard.setSubcategory(msg.getSubcategory());
			messageDashBoard.setCellphone(msg.getCellPhone());
			messageDashBoard.setFullName(msg.getCustomerName());
			messageService.save(messageDashBoard);

			messageDashBoard.setParentMsgId(messageDashBoard.getMessageID());
			messageService.save(messageDashBoard);

			saveImg(msg.getMsg().getAttachment(), messageDashBoard);

			Locale locale = new Locale("en");
			try {
				activityLogService.saveActivityLog(SystemConfig.SEND_MESS_CUSTOMER, SystemConfig.MESSAGE, locale, SystemConfig.MOBILE,
						customer.getCustomerId().toString(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		jsonReturn.setSuccess(true);
		return Response.status(200).entity(jsonReturn).build();

	}

	private void checkFileSize(List<DataImg> arrayList) throws Exception {
		// if (CollectionUtils.isNotEmpty(arrayList)) {
		// for (DataImg dataImg : arrayList) {
		// String base64String = dataImg.getImgData().substring(dataImg.getImgData().lastIndexOf(",") + 1,
		// dataImg.getImgData().length());
		// byte[] bytes = Base64.decodeBase64(base64String);
		// if (bytes.length > 2097152) {
		// throw new Exception("exceeded file size limit");
		// }
		// }
		// }
	}

	private void saveImg(ArrayList<DataImg> arrayList, MessageDashBoard messageDashBoard) {
		if (arrayList != null) {
			for (DataImg dataImg : arrayList) {

				try {
					String stringBase64 = dataImg.getImgData().substring(dataImg.getImgData().lastIndexOf(",") + 1, dataImg.getImgData().length());
					byte[] bytes = Convert.getByteImage(stringBase64);

					// Create the file on server
					File serverFile = new File(systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + System.getProperty("file.separator")
							+ dataImg.getFileName());
					BufferedOutputStream stream;
					stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(bytes);
					stream.close();
					DocumentInfo documentInfo = new DocumentInfo();
					documentInfo.setReferenceId(messageDashBoard.getMessageID());
					documentInfo.setFileName(dataImg.getFileName());
					documentInfo.setDocumentSource(SystemConfig.MESSAGE_SOURCES);
					documentInfo
							.setFileType(dataImg.getImgData().substring(dataImg.getImgData().indexOf("/") + 1, dataImg.getImgData().indexOf(";")));
					documentInfoService.saveDocumentInfo(documentInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	// replyMessage
	@POST
	@Path("/replyMsg")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replyMsg(@RequestBody MessageMsg msg) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {

			checkFileSize(msg.getMsg().getAttachment());

			String subject = msg.getMsg().getSubject();
			String content = msg.getMsg().getContent();

			// if (StringUtils.isNotBlank(subject)) {
			// subject = Jsoup.parse(subject).text();
			// }
			// if (StringUtils.isNotBlank(content)) {
			// content = Jsoup.parse(content).text();
			// }

			// System.out.println("##subject##" + subject);
			// System.out.println("##content##" + content);

			MessageDashBoard messageDashBoard = new MessageDashBoard();
			messageDashBoard.setSubject(subject);
			messageDashBoard.setContent(content);
			messageDashBoard.setParentMsgId(msg.getParentMsgId());
			Customer customer = customerService.findById(msg.getCustomerId());
			messageDashBoard.setCustomer(customer);

			MessageDashBoard dashBoard = messageService.findById(msg.getParentMsgId());
			messageDashBoard.setACCOUNT(dashBoard.getACCOUNT());
			messageDashBoard.setIsRead(0);
			messageDashBoard.setIsMsgUser(1);
			messageDashBoard.setStatus(1);
			messageDashBoard.setCreatedDate(new Date());
			messageDashBoard.setType(dashBoard.getType());
			messageDashBoard.setCategory(dashBoard.getCategory());
			messageDashBoard.setSubcategory(dashBoard.getSubcategory());
			messageDashBoard.setCellphone(msg.getCellPhone());
			messageDashBoard.setFullName(msg.getCustomerName());
			messageService.save(messageDashBoard);
			saveImg(msg.getMsg().getAttachment(), messageDashBoard);
			Locale locale = new Locale("en");
			try {
				activityLogService.saveActivityLog(SystemConfig.REPLY_MESS_CUSTOMER, SystemConfig.MESSAGE, locale, SystemConfig.MOBILE,
						customer.getCustomerId().toString(), dashBoard.getACCOUNT() != null ? dashBoard.getACCOUNT().getId().toString() : null);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// sendMsgContract
	@POST
	@Path("/sendMsgContract")
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendMsgContract(@RequestBody MessageMsg msg) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {

			checkFileSize(msg.getMsg().getAttachment());

			String subject = msg.getMsg().getSubject();
			String content = msg.getMsg().getContent();

			// if (StringUtils.isNotBlank(subject)) {
			// subject = Jsoup.parse(subject).text();
			// }
			// if (StringUtils.isNotBlank(content)) {
			// content = Jsoup.parse(content).text();
			// }

			MessageDashBoard messageDashBoard = new MessageDashBoard();
			messageDashBoard.setSubject(subject);
			messageDashBoard.setContent(content);
			messageDashBoard.setContractMsgId(msg.getContractMsgId());
			Customer customer = customerService.findById(msg.getCustomerId());
			messageDashBoard.setCustomer(customer);

			messageDashBoard.setIsRead(1);
			messageDashBoard.setIsMsgUser(1);
			messageDashBoard.setStatus(1);
			messageDashBoard.setType(3);
			messageDashBoard.setCreatedDate(new Date());
			messageDashBoard.setCellphone(msg.getCellPhone());
			messageDashBoard.setFullName(msg.getCustomerName());
			messageService.save(messageDashBoard);

			messageDashBoard.setParentMsgId(messageDashBoard.getMessageID());
			messageService.save(messageDashBoard);

			saveImg(msg.getMsg().getAttachment(), messageDashBoard);

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// replyMsgContract
	@POST
	@Path("/replyMsgContract")
	@Produces(MediaType.APPLICATION_JSON)
	public Response replyMsgContract(@RequestBody MessageMsg msg) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {

			checkFileSize(msg.getMsg().getAttachment());

			String subject = msg.getMsg().getSubject();
			String content = msg.getMsg().getContent();

			// if (StringUtils.isNotBlank(subject)) {
			// subject = Jsoup.parse(subject).text();
			// }
			// if (StringUtils.isNotBlank(content)) {
			// content = Jsoup.parse(content).text();
			// }

			MessageDashBoard messageDashBoard = new MessageDashBoard();
			messageDashBoard.setSubject(subject);
			messageDashBoard.setContent(content);
			messageDashBoard.setParentMsgId(msg.getParentMsgId());
			messageDashBoard.setContractMsgId(msg.getContractMsgId());
			Customer customer = customerService.findById(msg.getCustomerId());
			messageDashBoard.setCustomer(customer);
			MessageDashBoard dashBoard = messageService.findById(msg.getParentMsgId());
			messageDashBoard.setACCOUNT(dashBoard.getACCOUNT());
			messageDashBoard.setIsRead(0);
			messageDashBoard.setIsMsgUser(1);
			messageDashBoard.setStatus(1);
			messageDashBoard.setType(3);
			messageDashBoard.setCreatedDate(new Date());
			messageDashBoard.setCellphone(msg.getCellPhone());
			messageDashBoard.setFullName(msg.getCustomerName());
			messageService.save(messageDashBoard);

			saveImg(msg.getMsg().getAttachment(), messageDashBoard);

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	@POST
	@Path("/saveMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveMessage(@RequestBody SaveMess saveMess) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			if (saveMess.getData() != null) {
				for (SaveMessageContract messageDashBoard : saveMess.getData()) {
					Customer customer = customerService.customerIDtoAccountID(messageDashBoard.getCustomerId().toString());

					if (customer != null) {
						MessageDashBoard messageDashBoardL = new MessageDashBoard();
						messageDashBoardL.setCustomer(customer);
						messageDashBoardL.setContractMsgId(messageDashBoard.getContractID());

						if (messageDashBoard.getFullName() != null) {
							messageDashBoardL.setFullName(messageDashBoard.getFullName());
						}
						if (messageDashBoard.getCellphone() != null) {
							messageDashBoardL.setCellphone(messageDashBoard.getCellphone());
						}

						String subject = messageDashBoard.getSubject();
						String content = messageDashBoard.getContent();

						// if (StringUtils.isNotBlank(subject)) {
						// subject = Jsoup.parse(subject).text();
						// }
						// if (StringUtils.isNotBlank(content)) {
						// content = Jsoup.parse(content).text();
						// }

						if (messageDashBoard.getSubject() != null) {
							messageDashBoardL.setSubject(subject);
						}
						if (messageDashBoard.getContent() != null) {
							messageDashBoardL.setContent(content);
						}

						messageDashBoardL.setStatus(messageDashBoard.getStatus());
						messageDashBoardL.setType(3);
						messageDashBoardL.setIsRead(0);
						messageDashBoardL.setIsMsgUser(0);

						messageDashBoardL.setCreatedDate(new Date());
						messageService.save(messageDashBoardL);

						messageDashBoardL.setParentMsgId(messageDashBoardL.getMessageID());
						messageService.save(messageDashBoardL);

						jsonReturn.setSuccess(true);
						jsonReturn.setMsg("Success");

					} else {
						jsonReturn.setSuccess(false);
						jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
					}
				}
			}

		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/saveMessageAndPushMGM")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveMessageAndPushMGM(@RequestBody SaveMess saveMess) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			if (saveMess.getData() != null) {
				for (SaveMessageContract messageDashBoard : saveMess.getData()) {
					Customer customer = customerService.customerIDtoAccountID(messageDashBoard.getCustomerId().toString());

					if (customer != null) {
						MessageDashBoard messageDashBoardL = new MessageDashBoard();
						messageDashBoardL.setCustomer(customer);
						messageDashBoardL.setContractMsgId(messageDashBoard.getContractID());
						if (messageDashBoard.getFullName() != null) {
							messageDashBoardL.setFullName(messageDashBoard.getFullName());
						}

						if (messageDashBoard.getCellphone() != null) {
							messageDashBoardL.setCellphone(messageDashBoard.getCellphone());
						}

						String subject = messageDashBoard.getSubject();
						String content = messageDashBoard.getContent();

						// if (StringUtils.isNotBlank(subject)) {
						// subject = Jsoup.parse(subject).text();
						// }
						// if (StringUtils.isNotBlank(content)) {
						// content = Jsoup.parse(content).text();
						// }

						if (messageDashBoard.getSubject() != null) {
							messageDashBoardL.setSubject(subject);
						}
						if (messageDashBoard.getContent() != null) {
							messageDashBoardL.setContent(content);
						}

						String language = messageDashBoard.getLanguage();

						if (StringUtils.equalsIgnoreCase(language, "vi")) {
							messageDashBoardL.setContent(messageDashBoard.getContent_vi());
						} else if (StringUtils.equalsIgnoreCase(language, "en")) {
							messageDashBoardL.setContent(messageDashBoard.getContent_en());
						}

						messageDashBoardL.setStatus(messageDashBoard.getStatus());
						messageDashBoardL.setType(9);
						messageDashBoardL.setIsRead(0);
						messageDashBoardL.setIsMsgUser(0);

						messageDashBoardL.setCreatedDate(new Date());
						messageService.save(messageDashBoardL);

						jsonReturn.setSuccess(true);
						jsonReturn.setMsg("Success");

						try {

							int respCode = pushNotificationService.pushNotification(String.valueOf(customer.getCustomerId()),
									String.valueOf(messageDashBoardL.getContent()), String.valueOf(messageDashBoardL.getMessageID()), true);

							System.out.println("##respCode##" + respCode);

						} catch (Exception e) {
							e.printStackTrace();
						}

						messageDashBoardL.setParentMsgId(messageDashBoardL.getMessageID());
						messageService.save(messageDashBoardL);

					} else {
						jsonReturn.setSuccess(false);
						jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
					}
				}
			}

		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	/**
	 * CongDT: yêu cầu lại mật khẩu OTP
	 * 
	 * @param customer
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getForgetSMSCode")
	public Response getForgetSMSCode(@RequestBody Customer customer) {

		JsonReturn jsonReturn = new JsonReturn();
		Map<String, Object> jsonData = new LinkedHashMap<>();

		try {

			Customer customerFind = customerService.findByIdCardNum(customer.getIdCardNumber(), null, null, customer.getCellPhone());

			if (customerFind == null) {
				throw new Exception(msgSrc.getMessage("web.register.cardphone.correct", null, locale));
			}

			Integer status = customerFind.getStatus();
			if (BooleanUtils.isNotTrue(SystemConfig.STATUS_ACTIVE.equals(status))) {
				throw new Exception("Your account is disabled");
			}

			checkToSendOTP(customerFind, jsonReturn, jsonData, false);

			customerService.saveCustomerAccount(customerFind);

			jsonData.put("customerId", customerFind.getCustomerId());
			jsonData.put("refID", customerFind.getRefId());
			jsonData.put("respDate", new Date());

			SendMessSoapServies.sendMess("+84" + customerFind.getCellPhone().substring(1, customerFind.getCellPhone().length()),
					msgSrc.getMessage("web.register.smsforget", null, locale) + customerFind.getSmsCode() + ".", "Status");

			jsonReturn.setSuccess(true);

		} catch (Exception e) {
			jsonReturn.setMsg(e.getMessage());
			jsonReturn.setSuccess(false);
		}

		jsonReturn.setData(jsonData);
		return Response.status(200).entity(jsonReturn).build();

	}

	/**
	 * <pre>
	 * 
	 * Nhập OTP để reset password
	 * 
	 * </pre>
	 * 
	 * @param customerParam
	 * @return
	 */
	@POST
	@Path("/smsForgetActivate")
	@Produces(MediaType.APPLICATION_JSON)
	public Response smsForgetActivate(@RequestBody Customer customerParam) {

		JsonReturn jsonReturn = new JsonReturn();
		Map<String, Object> jsonData = new LinkedHashMap<>();

		try {

			String otpInput = customerParam.getSmsCode();

			if (StringUtils.isEmpty(otpInput)) {
				throw new Exception("Please input OTP");
			}

			// Kiểm tra customer
			Customer customerFind = customerService.findByIdCardNum(customerParam.getIdCardNumber(), null, null, customerParam.getCellPhone());
			if (customerFind == null) {
				throw new Exception(msgSrc.getMessage("web.register.cellphonesms", null, locale));
			}

			checkOTPInput(customerFind, jsonData, otpInput);

			customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_RESET_PASSWORD, SystemConfig.LOGIN_MOBILE, locale,
					SystemConfig.MOBILE, customerFind.getCustomerId().toString(), null);

			jsonReturn.setSuccess(true);

		} catch (Exception e) {
			jsonReturn.setMsg(e.getMessage());
			jsonReturn.setSuccess(false);
		}

		jsonReturn.setData(jsonData);
		return Response.status(200).entity(jsonReturn).build();
	}

	/**
	 * Kiểm tra OTP nhập vào
	 * 
	 * @param customerFind
	 * @param otpInput
	 * @throws Exception
	 */
	private void checkOTPInput(Customer customerFind, Map<String, Object> jsonData, String otpInput) throws Exception {

		String defaultLanguage = customerFind.getDefaultLanguage();
		defaultLanguage = StringUtils.defaultIfBlank(defaultLanguage, "vi");

		/**
		 * <pre>
		 * ===========================================
		 * kiểm tra OTP
		 * - Kiểm tra xem OTP còn hiệu lực hay không
		 * - Kiểm tra xem chức năng nhập OTP có đang bị tạm khóa hay không
		 * - Kiểm tra mã OTP có đúng hay không
		 * ============================================
		 * </pre>
		 */

		// Kiểm tra xem OTP còn hiệu lực hay không
		Date otpLive = customerFind.getOtp_LIVE_DUE();
		if (otpLive == null || otpLive.before(new Date())) {
			throw new Exception(msgSrc.getMessage("OTP.has.been.expired", null, new Locale(defaultLanguage)));
		}

		// Kiểm tra xem chức năng nhập OTP có đang bị tạm khóa hay không
		Date bannedDueDate = customerFind.getWrg_OTP_RESET_PWD_BAN_DUE();
		if (bannedDueDate != null && bannedDueDate.after(new Date())) {
			// Quý khách đã nhập quá số lần cho phép. Vui lòng thử lại sau
			throw new Exception(msgSrc.getMessage("OTP.The.entered.authentication.code.has.exceeded.the.limit", null, new Locale(defaultLanguage)));
		}

		// Kiểm tra mã OTP có đúng hay không
		if (BooleanUtils.isNotTrue(StringUtils.equals(otpInput, customerFind.getSmsCode()))) {
			Integer otpAttempts = customerFind.getWrg_OTP_RESET_PWD_ATTEMPTS() == null ? 0 : customerFind.getWrg_OTP_RESET_PWD_ATTEMPTS();
			otpAttempts = otpAttempts + 1;
			if (otpAttempts >= 3) {
				bannedDueDate = DateUtils.addHours(new Date(), 1);
				jsonData.put("OTPInputDisabledDue", bannedDueDate);
			}
			customerService.updateOTPResetPasswordAttempts(customerFind.getCustomerId(), otpAttempts, bannedDueDate);
			throw new Exception(msgSrc.getMessage("OTP.Incorrect.authentication.code", null, new Locale(defaultLanguage)));
		}

		customerService.resetRequestOTPAttempts(customerFind.getCustomerId());

	}

	@POST
	@Path("/registerForget")
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerForget(@RequestBody Customer customer) {
		Map<String, Object> hashMap = new LinkedHashMap<>();
		Customer customerFind = customerService.findByIdCardNum(customer.getIdCardNumber(), null, null, null);
		JsonReturn jsonReturn = new JsonReturn();
		if (customerFind != null) {
			try {
				customerFind.setPasswordOld(Utils.encryptMD5(customerFind.getPassword()));
				customerFind.setPassword(Utils.encryptMD5(customer.getPassword()));
				customerFind.setUserName(customer.getUserName());
				customerFind.setNumberForgetGetCode(null);
				customerFind.setDateForgetGetCode(null);
				customerFind.setActivateCodeDate(null);
				customerFind.setActivateCodeExpiredDate(null);
				customerFind.setResetPasswordExpiredDate(null);
				customerFind.setSmsCode(null);
				customerService.saveCustomerAccount(customerFind);
				hashMap.put("customerId", customerFind.getCustomerId());
				hashMap.put("customerContractId", customerFind.getOldUserId());
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				Locale locale = new Locale("en");
				try {
					activityLogService.saveActivityLog(SystemConfig.MOBILE_REGISTER_FOGET, SystemConfig.REGISTER_FORGET_MOBILE, locale,
							SystemConfig.MOBILE, customerFind.getCustomerId().toString(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {

				jsonReturn.setData(null);
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(e.getMessage());
			}

			return Response.status(200).entity(jsonReturn).build();
		} else {
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(msgSrc.getMessage("web.register.card", null, locale));
			return Response.status(200).entity(jsonReturn).build();
		}
	}

	// getBannerByCategory
	@POST
	@Path("/getBannerByCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBannerByCategory(@RequestBody Banner banner) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {
			List<Banner> bannerLst = bannerService.findByCategory(banner.getCategory());
			if (bannerLst != null && bannerLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(bannerLst);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// getTitlebyCategory
	@POST
	@Path("/getTitlebyCategory")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTitlebyCategory(@RequestBody CMSCategory category) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {
			List<ViewCms> cmsLst = null;
			if ("offers".equalsIgnoreCase(category.getCategory())) {
				cmsLst = cMSService.getOfferByCustomer(category.getCategory(), category.getCustomerId(), category.getSearchName(), category.getPage(),
						category.getLimit());
			} else {
				cmsLst = cMSService.getTitlebyCategory(category.getCategory(), category.getCustomerId(), category.getSearchName(), category.getPage(),
						category.getLimit());
			}
			if (cmsLst != null && cmsLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(cmsLst);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {
			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// getTitlebyCategory
	@POST
	@Path("/getTitlebyCategoryByOne")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTitlebyCategoryByOne(@RequestBody CMSCategory category) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {
			List<ViewCms> cmsLst = null;
			if ("offers".equalsIgnoreCase(category.getCategory())) {
				cmsLst = cMSService.getOfferByCustomerByOne(category.getCategory(), category.getCustomerId(), category.getSearchName(),
						category.getCmsId(), category.getPreornext());
			} else {
				cmsLst = cMSService.getTitlebyCategoryByOne(category.getCategory(), category.getCustomerId(), category.getSearchName(),
						category.getCmsId(), category.getPreornext());
			}
			if (cmsLst != null && cmsLst.size() > 0) {
				CMSEmtity result = cMSService.getContentByID(Long.parseLong(cmsLst.get(0).getCmsId()));
				if (result != null) {
					if (StringUtils.isNotEmpty(result.getImage())) {
						result.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + result.getImage());
					}
					if (StringUtils.isNotEmpty(result.getImageLong())) {
						result.setImagesquare(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + result.getImageLong());
					}

					List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(category.getCustomerId(),
							Long.parseLong(cmsLst.get(0).getCmsId()));
					if (category.getCustomerId() != null && customerWishs != null && customerWishs.size() > 0) {
						result.setWishlist(true);
					}
					jsonReturn.setSuccess(true);
					jsonReturn.setData(result);
				} else {
					jsonReturn.setSuccess(true);
					jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
				}
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	// getContentByID
	@POST
	@Path("/getContentByID")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getContentByID(@RequestBody CMSEmtity cms) {
		JsonReturn jsonReturn = new JsonReturn();
		jsonReturn.setSuccess(true);
		try {
			CMSEmtity result = cMSService.getContentByID(cms.getCmsId());
			if (result != null) {
				if (StringUtils.isNotEmpty(result.getImage())) {
					result.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + result.getImage());
				}
				if (StringUtils.isNotEmpty(result.getImageLong())) {
					result.setImagesquare(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + result.getImageLong());
				}

				List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(cms.getCustomerId(), cms.getCmsId());
				if (cms.getCustomerId() != null && customerWishs != null && customerWishs.size() > 0) {
					result.setWishlist(true);
				}
				jsonReturn.setSuccess(true);
				jsonReturn.setData(result);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();

	}

	@POST
	@Path("/getLoanCalculator")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoanCalculator(@RequestBody Loan loan) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<LoanCalculator> loandetail = loanDetailService.findLoanDetailByLoanType(loan.getCondition_category());
			if (loandetail != null) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(loandetail);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getBranchList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBranchCities() {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<BranchCities> branchLst = branchService.findAll();
			if (branchLst != null && branchLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(branchLst);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/GetDistrictByBranch")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetBranchDistrict(@RequestBody BranchEmtity branch) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<BranchCities> branchLst = posService.findByBranchID(branch.getBranchid());
			if (branchLst != null && branchLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(branchLst);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/GetPOSListTmp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetPOSListTmp(@RequestBody PosEmtity pos) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<PosEmtity> posLst = posService.findByLongAndLatTmp(pos.getLongitude(), pos.getLatitude(), pos.getDistance(), pos.getBuyOrPay(),
					Integer.parseInt(pos.getPage()));
			if (posLst != null && posLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(posLst);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/GetPOSList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetBranchList(@RequestBody PosEmtity pos) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<PosEmtity> posLst = posService.findByLongAndLat(pos.getLongitude(), pos.getLatitude(), pos.getDistance(), pos.getBuyOrPay(),
					Integer.parseInt(pos.getPage()));
			if (posLst != null && posLst.size() > 0) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(posLst);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/GetPOSListByDistrict")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetBranchListByCity(@RequestBody PosEmtity pos) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			List<PosEmtity> posEmtity = posService.findByBranchListByCity(pos.getIddistrict(), Integer.parseInt(pos.getPage()), pos.getBuyOrPay());
			if (posEmtity != null && posEmtity.size() > 0) {

				jsonReturn.setSuccess(true);
				jsonReturn.setData(posEmtity);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/GetPOSListBySearch")
	@Produces(MediaType.APPLICATION_JSON)
	public Response GetPOSListBySearch(@RequestBody PosEmtity pos) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			List<PosEmtity> posEmtity = posService.GetPOSListBySearch(pos);
			if (posEmtity != null && posEmtity.size() > 0) {
				try {
					posLogService.savePOSLog(SystemConfig.POS_READ, SystemConfig.POS_READ, locale, SystemConfig.MOBILE, null,
							pos.getPosid().toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				jsonReturn.setSuccess(true);
				jsonReturn.setData(posEmtity);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/saveApplyNow")
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveApplyNow(@RequestBody ApplyNow applyNow) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			Customer customer = null;
			PosEmtity posEmtity = null;
			Map<String, Object> hashMap = new LinkedHashMap<>();
			Loan loan = null;

			final Long customerId = applyNow.getCustomerId();
			final Long loanId = applyNow.getLoanId();

			if (customerId != null) {
				customer = new Customer();
				customer.setCustomerId(customerId);
			}

			if (applyNow.getPosEmtityId() != null) {
				// posEmtity = new PosEmtity();
				// posEmtity.setPosid(applyNow.getPosEmtityId());
			}

			if (loanId != null) {
				loan = new Loan();
				loan.setLoanID(loanId);
			}

			Customer cus = customerService.findById(customerId);
			long status = 1l;
			List<ApplyNow> existApplyNowList = applyNowService.searchApplyNow(customerId, loanId, applyNow.getProduct(), status);
			if (CollectionUtils.isNotEmpty(existApplyNowList)) {
				if (StringUtils.equalsIgnoreCase(cus.getDefaultLanguage(), "vi")) {
					throw new Exception("Khoản vay đã tồn tại");
				} else {
					throw new Exception("Your loan application has been registered");
				}
			}

			applyNow.setCustomer(customer);
			applyNow.setPosEmtity(posEmtity);
			applyNow.setLoan(loan);
			applyNow.setStatus(status);
			applyNow.setSubmittedDate(new Date());
			// applyNow.setExportedDate(new Date());
			applyNowService.saveApplyNow(applyNow);
			try {
				applyNowLogService.saveApplyNowLog(SystemConfig.APPLY_NOW_SUBMIT, SystemConfig.LOGIN_MOBILE, locale, SystemConfig.MOBILE,
						applyNow.getCustomerId().toString(), null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			hashMap.put("applyNowID", applyNow.getApplyNowID());
			jsonReturn.setSuccess(true);
			jsonReturn.setData(hashMap);
			MessageDashBoard messageDashBoard = new MessageDashBoard();

			if (StringUtils.equals(cus.getDefaultLanguage(), "vi")) {
				List<Hotline> hotlineTitleVi = masterdataService.findDetail("TemplateApplyNowTitle_VI");
				messageDashBoard.setSubject(hotlineTitleVi.get(0).getName());
				messageDashBoard.setContent(
						"Cám ơn bạn đã đăng ký! Thông tin đăng ký của bạn đã được ghi nhận. Chúng tôi sẽ liên lạc với bạn trong thời gian sớm nhất."
								+ " Khi cần liên hệ, bạn có thể dùng mã số đăng ký " + applyNow.getApplyNowID()
								+ " để FE CREDIT có thể hỗ trợ bạn nhanh hơn. ");
			} else if (StringUtils.equals(cus.getDefaultLanguage(), "en")) {
				List<Hotline> hotlineTitleEn = masterdataService.findDetail("TemplateApplyNowTitle_EN");
				messageDashBoard.setSubject(hotlineTitleEn.get(0).getName());
				messageDashBoard.setContent("Thank you for your registration. Your information has been recorded. We will contact you shortly. "
						+ "If you require any assistance, please use your application code " + applyNow.getApplyNowID() + " for faster support.");
			}

			messageDashBoard.setCustomer(customer);
			messageDashBoard.setType(MessageType.APPLY_NOW.getIntValue());
			messageDashBoard.setStatus(1);
			messageDashBoard.setIsRead(0);
			messageDashBoard.setIsMsgUser(0);
			messageDashBoard.setCreatedDate(new Date());
			messageService.save(messageDashBoard);
			messageDashBoard.setParentMsgId(messageDashBoard.getMessageID());
			messageService.save(messageDashBoard);

		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	private void jsonReturnFail(JsonReturn jsonReturn, Exception e) {
		jsonReturn.setData(null);
		jsonReturn.setSuccess(false);
		jsonReturn.setMsg(e.getMessage());
	}

	@POST
	@Path("/getMyHobbyList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMyHobbyList(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<Hobby> customerHobbies = hobbyService.listHobbyByCustomer(customer.getCustomerId());
			if (customerHobbies != null) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(customerHobbies);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/submitMyHobbyList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response submitMyHobbyList(@RequestBody PostCustomerHobby postCustomerHobby) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			Customer customer = customerService.findById(Long.parseLong(postCustomerHobby.getCustomerId()));
			if (customer != null) {
				if (postCustomerHobby.getList() != null) {
					List<CustomerHobby> customerHobbies = customerHobbyService.findByCustomerId(customer.getCustomerId());

					List<Long> list = new ArrayList<Long>();
					List<Long> listCurrent = new ArrayList<Long>();
					for (CustomerHobby item : customerHobbies) {
						list.add(item.getHobbyId());
					}
					customerHobbyService.deleteByCustomer(Long.parseLong(postCustomerHobby.getCustomerId()));
					for (Hobby hobby : postCustomerHobby.getList()) {
						CustomerHobby customerHobby = new CustomerHobby();
						customerHobby.setCheckedDate(new Date());
						customerHobby.setStatus(1);
						customerHobby.setCustomerId(Long.parseLong(postCustomerHobby.getCustomerId()));
						customerHobby.setHobbyId(hobby.getHobbyId());

						CustomerHobbyHistory customerHobbyHistory = new CustomerHobbyHistory();
						customerHobbyHistory.setCheckedDate(new Date());
						customerHobbyHistory.setStatus(1);
						customerHobbyHistory.setCustomerId(Long.parseLong(postCustomerHobby.getCustomerId()));
						customerHobbyHistory.setHobbyId(hobby.getHobbyId());

						customerHobbyService.saveCustomerHobby(customerHobby);
						listCurrent.add(customerHobby.getHobbyId());
						if (list != null && list.size() > 0) {
							if (!list.contains(customerHobby.getHobbyId())) {
								customerHobbyHistoryService.save(customerHobbyHistory);
							}
						} else {
							customerHobbyHistoryService.save(customerHobbyHistory);
						}
					}
					for (Long item : list) {
						if (!listCurrent.contains(item)) {
							CustomerHobbyHistory customerHobbyHistory = new CustomerHobbyHistory();
							customerHobbyHistory.setCheckedDate(new Date());
							customerHobbyHistory.setStatus(0);
							customerHobbyHistory.setCustomerId(Long.parseLong(postCustomerHobby.getCustomerId()));
							customerHobbyHistory.setHobbyId(item);
							customerHobbyHistoryService.save(customerHobbyHistory);
						}
					}

					jsonReturn.setSuccess(true);
					jsonReturn.setMsg("Success");

				} else
					jsonReturn.setSuccess(false);
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}
		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/changeAvatar")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changeAvatar(@RequestBody PostCustomerHobby postCustomerHobby) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			Customer customer = customerService.findById(Long.parseLong(postCustomerHobby.getCustomerId()));
			if (customer != null) {
				DataImg dataImg = postCustomerHobby.getAvartar();
				String stringBase64 = dataImg.getImgData().substring(dataImg.getImgData().lastIndexOf(",") + 1, dataImg.getImgData().length());
				byte[] bytes = Convert.getByteImage(stringBase64);
				// Create the file on server
				File serverFile = new File(
						systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + System.getProperty("file.separator") + dataImg.getFileName());
				BufferedOutputStream stream;
				stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				customer.setImagePath(dataImg.getFileName());
				customerService.update(customer);
				try {
					customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_CHANGE_AVATAR, SystemConfig.LOGIN_MOBILE, locale,
							SystemConfig.MOBILE, customer.getCustomerId().toString(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}

				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}

		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getWishList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWishList(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			List<ViewCms> wishs = cMSService.getListCMSByCustomer(customer.getCustomerId());
			if (wishs != null) {
				jsonReturn.setSuccess(true);
				jsonReturn.setData(wishs);
			} else {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/setWishList")
	@Produces(MediaType.APPLICATION_JSON)
	public Response setWishList(@RequestBody PostCustomerWish postCustomerWish) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			Customer customer = customerService.findById(Long.parseLong(postCustomerWish.getCustomerId()));
			if (customer != null) {
				List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(Long.parseLong(postCustomerWish.getCustomerId()),
						Long.parseLong(postCustomerWish.getCmsId()));
				if (customerWishs.size() == 0) {
					CustomerWish customerWish = new CustomerWish();
					customerWish.setAddDate(new Date());
					customerWish.setCustomerId(Long.parseLong(postCustomerWish.getCustomerId()));
					customerWish.setWishId(Long.parseLong(postCustomerWish.getCmsId()));
					customerWishService.saveCustomerWish(customerWish);
					jsonReturn.setSuccess(true);
					jsonReturn.setMsg("Success");
				} else {
					jsonReturn.setSuccess(false);
					jsonReturn.setMsg(msgSrc.getMessage("web.wish.exits", null, locale));
				}

			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}
		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/editCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response editCustomer(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			Customer customerN = customerService.findById(customer.getCustomerId());
			if (customerN != null) {
				customerN.setUserName(customer.getUserName() == null ? customerN.getUserName() : customer.getUserName());

				customerN.setFullName(customer.getFullName() == null ? customerN.getFullName() : customer.getFullName());

				customerN.setAddressNo(customer.getAddressNo() == null ? customerN.getAddressNo() : customer.getAddressNo());
				customerN.setStreet(customer.getStreet() == null ? customerN.getStreet() : customer.getStreet());
				customerN.setAddressS(customer.getAddress() == null ? customerN.getAddressS() : customer.getAddress());
				customerN.setWard(customer.getWard() == null ? customerN.getWard() : customer.getWard());
				customerN.setDistrict(customer.getDistrict() == null ? customerN.getDistrict() : customer.getDistrict());
				customerN.setProvince(customer.getProvince() == null ? customerN.getProvince() : customer.getProvince());

				customerN.setEmailAddress(customer.getEmailAddress() == null ? customerN.getEmailAddress() : customer.getEmailAddress());

				customerService.saveCustomerAccount(customerN);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
				// Locale locale = new Locale("en");

			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}
		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/changePassword")
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePassword(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			Customer customerN = customerService.findById(customer.getCustomerId());
			if (customerN != null) {
				String pass = Utils.encryptMD5(customer.getNewPass());
				customerN.setPassword(customer.getNewPass() == null ? customerN.getPassword() : pass);
				customerService.saveCustomerAccount(customerN);
				try {
					customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_CHANGE_PASSWORD, SystemConfig.LOGIN_MOBILE, locale,
							SystemConfig.MOBILE, customerN.getCustomerId().toString(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}

				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
				Locale locale = new Locale("en");
				try {
					activityLogService.saveActivityLog(SystemConfig.MOBILE_CHANGER_PASS, SystemConfig.CHANGER_PASS_MOBILE, locale,
							SystemConfig.MOBILE, customerN.getCustomerId().toString(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}
		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/viewCustomer")
	@Produces(MediaType.APPLICATION_JSON)
	public Response viewCustomer(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			ViewCustomer customerN = customerService.findViewById(customer.getCustomerId());
			if (customerN != null) {
				customerN.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + customerN.getImagePath());
				jsonReturn.setData(customerN);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}
		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/removeCustomerWish")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeCustomerWish(@RequestBody CustomerWish customerWish) {
		JsonReturn jsonReturn = new JsonReturn();

		try {
			List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(customerWish.getCustomerId(), customerWish.getWishId());
			if (customerWishs != null) {
				customerWishService.deleteCustomerWish(customerWish.getCustomerId(), customerWish.getWishId());
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.wishcus.exits", null, locale));
			}
		} catch (Exception e) {

			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/refeshCustomerId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response refeshCustomerId(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			Map<String, Object> hashMap = new LinkedHashMap<>();
			String userId = SoapServies.getValueCustomer(customer.getIdCardNumber(), customer.getCellPhone(), "customerID");
			if (userId != null) {
				hashMap.put("customerContractId", userId);
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
				Customer customerFind = customerService.findByIdCardNum(customer.getIdCardNumber(), null, null, customer.getCellPhone());
				if (customerFind != null) {
					customerFind.setOldUserId(userId.toString());
					customerService.saveCustomerAccount(customerFind);
				}
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/customerIDtoAccountID")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerIDtoAccountID(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			Map<String, Object> hashMap = new LinkedHashMap<>();
			Customer customer2 = customerService.customerIDtoAccountID(customer.getCustomerContractId());
			if (customer2 != null) {
				hashMap.put("customerId", customer2.getCustomerId());
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getStatistic")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStatistic(@RequestBody Customer customer) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			Map<String, Object> hashMap = new LinkedHashMap<>();

			if (customer.getCustomerId() != null) {
				Map<String, Object> count = messageService.getInboxList(customer.getCustomerId(), 1, "unread", SystemConfig.SEARCH_TYPE_COUNT_ONLY, 0,
						0);
				Integer cmsoffers = cMSService.getStatistic("OFFERS", customer.getCustomerId(), null, null, null);
				Integer cmspromotions = cMSService.getStatistic("PROMOTIONS", customer.getCustomerId(), null, null, null);
				hashMap.put("inbox", count.get("COUNT"));
				hashMap.put("offers", cmsoffers);
				hashMap.put("promotions", cmspromotions);
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			} else {
				Integer cmspromotions = cMSService.getStatistic("PROMOTIONS", customer.getCustomerId(), null, null, null);
				hashMap.put("promotions", cmspromotions);
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getAbout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAbout() {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			Map<String, String> hashMap = new LinkedHashMap<>();

			Contact aboutDAO = contactService.findByAll();
			if (aboutDAO == null) {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			} else {
				hashMap.put("about", aboutDAO.getContent());
				jsonReturn.setData(hashMap);
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg("Success");
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getMobileAppTerm")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMobileAppTerm() {
		JsonReturn jsonReturn = new JsonReturn();
		try {

			CMSCategory cmsCategory = cmsCategoryService.findByCode("TERM");
			if (cmsCategory == null) {
				throw new Exception(msgSrc.getMessage("web.message.nodata", null, locale));
			}

			Map<String, String> term = cMSService.getMobileAppTerm(cmsCategory.getCms_categoryId());
			if (term == null) {
				throw new Exception(msgSrc.getMessage("web.message.nodata", null, locale));
			}

			Map<String, Object> hashMap = new HashMap<>();
			hashMap.put("term", term);
			jsonReturn.setData(hashMap);
			jsonReturn.setSuccess(true);
			jsonReturn.setMsg("Success");

		} catch (Exception e) {
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getMasterdataDetail")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMasterdataDetail(@RequestBody Masterdata masterdata) {
		JsonReturn jsonReturn = new JsonReturn();
		try {
			// Map<String, Object> hashMap = new LinkedHashMap<>();

			if (StringUtils.isNotEmpty(masterdata.getName())) {
				List<Hotline> getListDAO = masterdataDetailService.findDetail(masterdata.getName());
				if (getListDAO == null) {
					jsonReturn.setSuccess(true);
					jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
				} else {
					jsonReturn.setSuccess(true);
					jsonReturn.setData(getListDAO);
				}

			} else {
				jsonReturn.setSuccess(false);
				jsonReturn.setMsg(msgSrc.getMessage("web.hobby.cus", null, locale));
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/getMasterdataChild")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMasterdataChild(@RequestBody MasterdataDetal masterdata) {
		JsonReturn jsonReturn = new JsonReturn();
		try {

			if (StringUtils.isEmpty(masterdata.getParentName()) || StringUtils.isEmpty(masterdata.getChildName())) {
				throw new Exception("Data not found");
			}

			MasterdataDetal masterdataDetal = masterdataDetailService.getMasterData(masterdata.getParentName(), masterdata.getChildName());
			if (masterdataDetal == null) {
				jsonReturn.setSuccess(true);
				jsonReturn.setMsg(msgSrc.getMessage("web.message.nodata", null, locale));
			} else {
				Map<String, Object> hashMap = new HashMap<>();
				hashMap.put("parentName", masterdata.getParentName());
				hashMap.put("childName", masterdata.getChildName());
				hashMap.put("content", masterdataDetal.getDescription());
				jsonReturn.setSuccess(true);
				jsonReturn.setData(hashMap);
			}

		} catch (Exception e) {
			jsonReturnFail(jsonReturn, e);
		}
		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	public Response logout(@RequestBody Customer customer) {
		Customer customerFind = customerService.findByIdCardNum(customer.getIdCardNumber(), customer.getSmsCode(), null, null);
		JsonReturn jsonReturn = new JsonReturn();
		try {
			if (customerFind != null) {
				if (customerFind.getCodeMachine() != null) {
					customerFind.setCodeMachine(null);
					customerService.saveCustomerAccount(customerFind);
					try {
						customerActivityLogService.saveCustomerActivityLog(SystemConfig.MOBILE_LOGOUT, SystemConfig.LOGIN_MOBILE, locale,
								SystemConfig.MOBILE, customerFind.getCustomerId().toString(), null);
					} catch (Exception e) {
						e.printStackTrace();
					}

					jsonReturn.setSuccess(true);
				} else {
					jsonReturn.setMsg(msgSrc.getMessage("web.register.cardsms.logoutFail", null, locale));
					jsonReturn.setSuccess(false);
					return Response.status(200).entity(jsonReturn).build();
				}

			} else {
				jsonReturn.setMsg(msgSrc.getMessage("web.register.cardsms.correct", null, locale));
				jsonReturn.setSuccess(false);
				return Response.status(200).entity(jsonReturn).build();
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/updateLanguage")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLanguage(@RequestBody Customer customer) {
		Customer customerFind = customerService.findById(customer.getCustomerId());

		JsonReturn jsonReturn = new JsonReturn();
		try {
			if (customerFind != null) {

				customerFind.setDefaultLanguage(customer.getDefaultLanguage());
				customerService.update(customerFind);

				jsonReturn.setSuccess(true);
				return Response.status(200).entity(jsonReturn).build();
			} else {
				jsonReturn.setMsg(msgSrc.getMessage("web.customer.null", null, locale));
				jsonReturn.setSuccess(false);
				return Response.status(200).entity(jsonReturn).build();
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/checkLogin")
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkLogin(@RequestBody Customer customer) {
		Customer customerFind = customerService.findByIdCardNum(customer.getIdCardNumber(), customer.getSmsCode(), null, null);
		JsonReturn jsonReturn = new JsonReturn();
		try {
			if (customerFind != null) {
				if (customerFind.getCodeMachine() != null) {
					if (!customerFind.getCodeMachine().equalsIgnoreCase(customer.getCodeMachine())) {
						jsonReturn.setMsg(msgSrc.getMessage("web.register.cardsms.checkLogin", null, locale));
						jsonReturn.setSuccess(false);
					} else {
						jsonReturn.setSuccess(true);
						return Response.status(200).entity(jsonReturn).build();
					}
				} else {
					jsonReturn.setMsg(msgSrc.getMessage("web.register.cardsms.logoutFail", null, locale));
					jsonReturn.setSuccess(false);
					return Response.status(200).entity(jsonReturn).build();
				}

				if (customerFind.getStatus() == null
						|| (customerFind.getStatus() != null && !customerFind.getStatus().equals(SystemConfig.STATUS_ACTIVE))) {
					jsonReturn.setMsg(msgSrc.getMessage("web.register.cardsms.checkLogin", null, locale));
					jsonReturn.setSuccess(false);
					return Response.status(200).entity(jsonReturn).build();
				}

			} else {
				jsonReturn.setMsg(msgSrc.getMessage("web.register.cardsms.correct", null, locale));
				jsonReturn.setSuccess(false);
				return Response.status(200).entity(jsonReturn).build();
			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();
	}

	@POST
	@Path("/customerCMSRead")
	@Produces(MediaType.APPLICATION_JSON)
	public Response customerCMSRead(@RequestBody CustomerCMSRead customerCMSRead) {
		CustomerCMSRead customerFind = customerCMSReadService.findCustomerIdAndCMSId(customerCMSRead.getCustomerId(), customerCMSRead.getCmsId());
		JsonReturn jsonReturn = new JsonReturn();
		try {
			if (customerFind != null) {
				jsonReturn.setMsg(msgSrc.getMessage("web.customerCMSRead.exits", null, locale));
			} else {
				try {
					customerCMSReadService.saveCustomerCMSRead(customerCMSRead);
					jsonReturn.setSuccess(true);
					return Response.status(200).entity(jsonReturn).build();

				} catch (Exception e) {
					jsonReturn.setMsg(msgSrc.getMessage("msg.save.fail", null, locale));
					jsonReturn.setData(null);
					jsonReturn.setSuccess(false);

				}

			}
		} catch (Exception e) {

			jsonReturn.setData(null);
			jsonReturn.setSuccess(false);
			jsonReturn.setMsg(e.getMessage());
		}

		return Response.status(200).entity(jsonReturn).build();
	}

	public static void main(String[] args) {
		long time = new Date().getTime();
		System.out.println(time);
	}

	@Autowired
	@Qualifier("schedulerFactoryBeanCluster")
	private SchedulerFactoryBean schedulerFactory;

	// @Autowired
	// private ApplicationContext appContext;

	@POST
	@Path("/testJobs")
	@Produces(MediaType.APPLICATION_JSON)
	public Response testJobs() {

		// SentMessageWhenFriendBecomeLoanTask task = new SentMessageWhenFriendBecomeLoanTask();
		// appContext.getAutowireCapableBeanFactory().autowireBean(task);
		// task.doTask();
		// String url = "smb://10.30.1.1/DeltaShare/Public Share/MGM-test/abc.txt";
		// NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("deltavn.vn", "servicemobileapp",
		// "7F>y~pC8Qk=;");
		// SmbFile dir = new SmbFile(url, auth);
		// dir.createNewFile();
		//
		// url = "smb://10.30.1.1/DeltaShare/Public Share/MGM-test/";
		// dir = new SmbFile(url, auth);
		// for (SmbFile f : dir.listFiles()) {
		// System.out.println(f.getName());
		// }

		// SentMessageNoticeRedeemResultTask task = new SentMessageNoticeRedeemResultTask();
		// appContext.getAutowireCapableBeanFactory().autowireBean(task);
		// task.doTask();

		try {

			Scheduler scheduler = this.schedulerFactory.getScheduler();

			System.out.println("##getSchedulerName##" + scheduler.getSchedulerName());
			System.out.println("##isStarted##" + scheduler.isStarted());

			scheduler.start();
			System.out.println("##isStarted##" + scheduler.isStarted());

			System.out.println("##getJobGroupNames##" + Utils.writeValueAsString(scheduler.getJobGroupNames()));
			System.out.println("##getTriggerGroupNames##" + Utils.writeValueAsString(scheduler.getTriggerGroupNames()));

			for (String groupName : scheduler.getJobGroupNames()) {

				for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {

					String JOB_NAME = jobKey.getName();
					System.out.println("##JOB_NAME##" + JOB_NAME);

					@SuppressWarnings("unchecked")
					List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);

					for (Trigger trigger : triggers) {
						String TRIGGER_NAME = trigger.getKey().getName();
						Date nextFireTime = trigger.getNextFireTime();

						// Trigger.STATE_NORMAL, Trigger.STATE_PAUSED, Trigger.STATE_COMPLETE, Trigger.STATE_ERROR,
						// Trigger.STATE_BLOCKED, Trigger.STATE_NONE
						System.out.println("[JOB_NAME] : " + JOB_NAME + " [TRIGGER_NAME] : " + TRIGGER_NAME + " [TRIGGER_STATE] : "
								+ scheduler.getTriggerState(trigger.getKey()).name() + " [NEXT_FIRE_TIME] " + nextFireTime);
					}
				}
			}

			// scheduler.pauseTrigger(TriggerKey.triggerKey("firstTrigger", "DEFAULT"));
			// scheduler.resumeTrigger(TriggerKey.triggerKey("firstTrigger", "DEFAULT"));
			TriggerKey triggerKey = TriggerKey.triggerKey("firstTrigger", "DEFAULT");
			scheduler.getTrigger(triggerKey);
			CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
			trigger.setCronExpression("");
			scheduler.rescheduleJob(triggerKey, trigger);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Response.status(200).entity("OK").build();

	}

}
