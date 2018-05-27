package vn.com.unit.fe_credit.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.MessageDashBoardBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.config.SystemConfig.MessageStatus;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.DocumentInfo;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.DocumentInfoService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MasterdataService;
import vn.com.unit.fe_credit.service.MessageActivityLogService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.PushNotificationService;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.fe_credit.utils.excel.ExcelHelper;
import vn.com.unit.fe_credit.utils.excel.ExcelUtils;
import vn.com.unit.webservice.Hotline;

@Controller
@RequestMapping(value = "/message/")
public class MessagesController {

	@Autowired
	MessageService messageService;

	@Autowired
	MasterdataDetailService masterdataDetailService;

	@Autowired
	private MessageService messagesService;

	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private UserProfile userProfile;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DocumentInfoService documentInfoService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageActivityLogService messageactivityLogService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	MasterdataDetailService masterdatalDetailService;

	@Autowired
	MasterdataService MasterdataService;

	@Autowired
	PushNotificationService pushNotificationService;

	private static final Logger logger = LoggerFactory.getLogger(MessagesController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate") + "HH:mm");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
	}

	@ModelAttribute(value = "listMess")
	public Map<Integer, String> allContractCCMTypes() {
		List<Hotline> getValueDAO = masterdatalDetailService.findDetail("MessageType");
		Map<Integer, String> allContractCCMTypes = new HashMap<Integer, String>();
		if (getValueDAO != null) {
			for (Hotline iteam : getValueDAO) {
				allContractCCMTypes.put(Integer.parseInt(iteam.getCode()), iteam.getName());
			}
		}
		return allContractCCMTypes;
	}

	@RequestMapping(value = "dashboard/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listMessageDashBoard(@ModelAttribute(value = "bean") MessageDashBoardBean bean, Model model, Locale locale,
			HttpServletRequest request) throws Exception {
		bean.clearMessages();
		MessageDashBoardBean result = messagesService.search(bean);
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		List<Hotline> messageTypeDAO = masterdatalDetailService.findDetail("MessageType");
		model.addAttribute("messageTypeDAO", messageTypeDAO);
		model.addAttribute("bean", result);
		return "messages.list";
	}

	@RequestMapping(value = "dashboard/view", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewCustomer(@ModelAttribute(value = "bean") MessageDashBoardBean customerBean,
			@RequestParam(value = "messageId", required = false) Long messageId, HttpServletRequest request, Model model,
			HttpServletResponse responses, Locale locale) throws Exception {

		MessageDashBoard mess = messagesService.findMessByMessageId(messageId);

		String messType = "";
		List<Hotline> getValueDAO = masterdatalDetailService.findDetail("MessageType");
		if (getValueDAO != null) {
			for (Hotline iteam : getValueDAO) {
				if (String.valueOf(mess.getType()).equals(iteam.getCode())) {
					messType = iteam.getName();
				}
			}
		}

		String accountId = "";
		if (mess.getACCOUNT() != null) {
			accountId = mess.getACCOUNT().getId().toString();
		}
		model.addAttribute("accountId", accountId);
		model.addAttribute("messType", messType);
		model.addAttribute("mess", mess);
		return "message.dashboard.view";
	}

	@RequestMapping(value = "follow_up", method = RequestMethod.GET)
	public String viewFollowUp(Model model, Locale locale, HttpServletRequest request) {

		model.addAttribute("accountId", userProfile.getAccount().getId());
		model.addAttribute("lstAccount", accountService.findAllAccount());
		List<Hotline> getValueDAO = masterdatalDetailService.findDetail("MessageType");
		Map<String, String> allContractCCMTypes = new HashMap<String, String>();
		if (getValueDAO != null) {
			for (Hotline iteam : getValueDAO) {
				allContractCCMTypes.put(iteam.getCode(), iteam.getName());
			}

		}
		model.addAttribute("allContractCCMTypes", allContractCCMTypes);
		return "messages.follow.up";

	}

	@RequestMapping(value = "getListMessageCustomer", method = RequestMethod.GET)
	@ResponseBody
	public List<Customer> getListMessageCustomer(@RequestParam(value = "typeId", required = false) Long typeId) {
		List<Customer> customers = messagesService.findAll(typeId);
		if (CollectionUtils.isNotEmpty(customers)) {

		}
		return customers;
	}

	@RequestMapping(value = "getOneMessageType", method = RequestMethod.GET)
	public void getOneMessageType(@RequestParam(value = "val", required = false) String val, SecurityContextHolderAwareRequestWrapper request,
			HttpServletResponse response) throws JSONException, IOException {
		List<Hotline> getValueDAO = masterdatalDetailService.findDetail("MessageType");
		Map<String, String> allContractCCMTypes = new HashMap<String, String>();
		if (getValueDAO != null) {
			for (Hotline iteam : getValueDAO) {
				allContractCCMTypes.put(iteam.getCode(), iteam.getName());
			}
		}
		request.setCharacterEncoding("utf8");
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		out.print(mapper.writeValueAsString(allContractCCMTypes.get(val)));
	}

	@RequestMapping(value = "getLstMessageType", method = RequestMethod.GET)
	@ResponseBody
	public ArrayList<Integer> getLstMessageType() {
		/*
		 * List<Hotline> hotline = null; ArrayList<Integer> getValueDAO= new ArrayList<Integer>();
		 * hotline=masterdataDetailService.findDetail("MessageType"); for (Hotline iteam : hotline) {
		 * getValueDAO.add(Integer.valueOf(iteam.getCode())); }
		 */
		ArrayList<Integer> getValueDAO = new ArrayList<Integer>();
		List<MessageDashBoard> dashBoards = messagesService.findbyTypeOfUser();
		for (MessageDashBoard item : dashBoards) {
			getValueDAO.add(item.getType());
		}

		return getValueDAO;
	}

	@RequestMapping(value = "getLstMessageByCustomerId", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageDashBoard> getLstMessageByCustomerId(@RequestParam(value = "customerId") Long customerId,
			@RequestParam(value = "messageId", required = false) Long messageId, @RequestParam(value = "typeId") Long typeId,
			@RequestParam(value = "parentMsgId") Long parentMsgId, @RequestParam(value = "accountId", required = false) Long accountId,
			@RequestParam(value = "dashboard", required = false) Boolean dashboard) {

		List<MessageDashBoard> lst = null;
		try {
			lst = messagesService.getLstMessageByCustomerId(customerId, typeId, parentMsgId, accountId, dashboard, messageId);
			for (MessageDashBoard messageDashBoard : lst) {
				messageDashBoard.setContent(StringEscapeUtils.escapeHtml(messageDashBoard.getContent()));
				messageDashBoard.setCreDate(SystemConfig.formatDateToString(messageDashBoard.getCreatedDate()));
			}
		} catch (Exception e) {
			// logger
		}
		return lst;

	}

	@RequestMapping(value = "changeStatusIsRead", method = RequestMethod.GET)
	@ResponseBody
	public int changeStatusIsRead(@RequestParam(value = "parentMsgId") Long parentMsgId, Locale locale) {
		int rs = messagesService.changeStatusIsRead(parentMsgId);

		return rs;
	}

	@RequestMapping(value = "saveMsg", headers = "Content-Type=application/json", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveMsg(@RequestBody MessageDashBoard msg, Locale locale) {
		String fileName = "";
		boolean isFile = false;

		msg.setIsMsgUser(0);
		Account ac = new Account();
		ac.setId(userProfile.getAccount().getId());
		msg.setACCOUNT(ac);
		msg.setCreatedDate(new Date());
		msg.setStatus(0);
		Customer cs = new Customer();
		cs.setCustomerId(msg.getCustomerId());
		msg.setCustomer(cs);
		msg.setIsRead(0);

		if (StringUtils.contains(msg.getContent(), "@@@")) {
			isFile = true;
			fileName = Utils.moveTempToUploadFolder(msg.getContent(), systemConfig);
			msg.setContent("");
		} else {
			String content = msg.getContent();
			content = StringUtils.trimToEmpty(content);
			if (StringUtils.isNotBlank(content)) {
				// content = StringUtils.replace(content, "<", "");
				// content = StringUtils.replace(content, ">", "");
				// content = Jsoup.parse(content).text();
			}
			if (StringUtils.isBlank(content)) {
				return false;
			}
			msg.setContent(content);
		}

		boolean rs = messagesService.saveMsg(msg);
		if (rs) {
			MessageDashBoard dashBoard = messagesService.findById(msg.getParentMsgId());
			if (dashBoard != null) {
				dashBoard.setIsRead(0);
				messagesService.saveMsg(dashBoard);
			}
			String pushContent = "";
			try {

				pushContent = msg.getContent();
				if (StringUtils.isNotEmpty(pushContent) && pushContent.length() > 100) {
					pushContent = pushContent.substring(0, 100) + "...";
				}

				pushNotificationService.pushNotification(msg.getCustomerId().toString(), pushContent,
						msg.getParentMsgId() != null ? msg.getParentMsgId().toString() : msg.getMessageID().toString(), false);

			} catch (Exception e) {
				msg.setPushErrorMessages(StringUtils.left(e.getMessage(), 1500));
				messagesService.saveMsg(msg);
			}

			try {
				messageactivityLogService.saveMessageActivityLog(SystemConfig.MESSAGE_BE_REPLY, SystemConfig.MESSAGE, locale, SystemConfig.SYSTEM,
						String.valueOf(msg.getParentMsgId()), userProfile.getAccount().getId().toString());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (isFile) {
			DocumentInfo di = new DocumentInfo();
			di.setFileName(fileName);
			di.setFileType(fileName.substring(fileName.indexOf(".") + 1, fileName.length()));
			di.setDocumentSource(new Long(1));
			di.setReferenceId(msg.getMessageID());
			documentInfoService.saveDocumentInfo(di);
		}

		return rs;
	}

	@RequestMapping(value = "assign_accountId", method = RequestMethod.POST)
	@ResponseBody
	public String assignAccountId(@RequestParam(value = "parentMsgId", required = false) Long parentMsgId,
			@RequestParam(value = "contractMsgId", required = false) String contractMsgId, @RequestParam(value = "messageId") Long messageId,
			Locale locale) {

		if (parentMsgId == 0) {
			MessageDashBoard mes = messagesService.findById(messageId);
			if (MessageStatus.ASSIGNED.getIntValue() == mes.getStatus()) {
				return "false";
			}

			mes.setParentMsgId(messageId);
			messagesService.save(mes);
			parentMsgId = mes.getParentMsgId();

			/*
			 * if (StringUtils.isNotEmpty(contractMsgId)) { messagesService.assignParentToMessageContract(contractMsgId,
			 * parentMsgId); }
			 */
		} else {
			MessageDashBoard mes = messagesService.findById(messageId);
			if (mes == null) {
				return "false";
			}
			if ((mes.getMessageID().equals(mes.getParentMsgId()) || BooleanUtils.isTrue(mes.getIsParent()))) {
				if (mes.getACCOUNT() != null && BooleanUtils.isNotTrue(userProfile.getAccount().getId().equals(mes.getACCOUNT().getId()))) {
					return "false";
				}
			}
		}

		int rs = messagesService.assignAccountId(parentMsgId);

		if (rs > 0) {
			try {
				messageactivityLogService.saveMessageActivityLog(SystemConfig.MESSAGE_BE_ASSIGN, SystemConfig.MESSAGE, locale, SystemConfig.SYSTEM,
						String.valueOf(parentMsgId), userProfile.getAccount().getId().toString());
				return "true";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "false";

	}

	@RequestMapping(value = "checkMessageNew", method = RequestMethod.GET)
	@ResponseBody
	public List<MessageDashBoard> checkMessageNew() {
		return messagesService.checkMessageNew();
	}

	@RequestMapping(value = "changeStatusMsg", method = RequestMethod.POST)
	@ResponseBody
	public String changeStatusMsg(@RequestParam(value = "status") int status, @RequestParam(value = "customerId") Long customerId,
			@RequestParam(value = "reason") String reason, @RequestParam(value = "typeId") Long typeId,
			@RequestParam(value = "accountId", required = false) Long accountId,
			@RequestParam(value = "parentMsgId", required = false) Long parentMsgId, Locale locale) {

		int rs = messagesService.changeStatusMsg(status, customerId, reason, accountId, typeId, parentMsgId);

		try {

			if (status == 1) {
				messageactivityLogService.saveMessageActivityLog(SystemConfig.MESSAGE_BE_MOVE_TO_CANCEL_QUEUE, SystemConfig.MESSAGE, locale,
						SystemConfig.SYSTEM, String.valueOf(parentMsgId), userProfile.getAccount().getId().toString());
			} else if (status == 2) {
				messageactivityLogService.saveMessageActivityLog(SystemConfig.MESSAGE_BE_REASSIGN, SystemConfig.MESSAGE, locale, SystemConfig.SYSTEM,
						String.valueOf(parentMsgId), userProfile.getAccount().getId().toString());
			} else if (status == 3) {
				messageactivityLogService.saveMessageActivityLog(SystemConfig.MESSAGE_BE_MOVE_TO_CLOSE_QUEUE, SystemConfig.MESSAGE, locale,
						SystemConfig.SYSTEM, String.valueOf(parentMsgId), userProfile.getAccount().getId().toString());
			}

			return "true";
		} catch (Exception e) {
			return "false";
		}

	}

	@RequestMapping(value = "getInfoCustomer", method = RequestMethod.GET)
	@ResponseBody
	public Customer getInfoCustomer(@RequestParam(value = "customerId", required = false) Long customerId) {
		return customerService.findById(customerId);
	}

	@RequestMapping(value = "/init", method = { RequestMethod.GET, RequestMethod.POST })
	public String init(@ModelAttribute(value = "bean") MessageDashBoardBean messageDashBoardBean, HttpServletRequest request, Model model) {
		// pendingPaymentBean = new PendingPaymentBean();
		model.addAttribute("systemConfig", systemConfig);
		List<Hotline> messageTypeDAO = masterdatalDetailService.findDetail("MessageType");
		messageDashBoardBean.setMessageTypeDAO(messageTypeDAO);
		model.addAttribute("bean", messageDashBoardBean);
		return "messages.import";
	}

	// down load template file import customer
	@RequestMapping(value = "/downloadImportMessage", method = RequestMethod.POST)
	public void downloadImportMessage(HttpServletResponse response) throws FileNotFoundException, IOException {
		repairFileDownload(response, "trading_vsd");
	}

	private void repairFileDownload(HttpServletResponse response, String type) throws FileNotFoundException, IOException {
		String template = "ImportMessage.xlsx";
		String fileName = null;
		template = "/WEB-INF/exportlist/" + template;
		fileName = template.substring(template.lastIndexOf("/") + 1);
		template = servletContext.getRealPath(template);
		ExcelUtils excelUtils = new ExcelUtils();
		Workbook workbook = excelUtils.getWorkbook(template);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
			response.getOutputStream().write(bytes);
		} finally {
			bos.close();
		}
	}

	@RequestMapping(value = "/import", headers = "content-type=multipart/*", method = { RequestMethod.POST })
	public String importCustomer(MultipartHttpServletRequest multipartHttpServletRequest,
			@ModelAttribute(value = "bean") MessageDashBoardBean messageDashBoardBean, Model model, Locale locale, HttpServletRequest req,
			HttpServletResponse resp, RedirectAttributes redirectAttributes) throws Exception {
		List<Hotline> messageTypeDAO = masterdatalDetailService.findDetail("MessageType");
		messageDashBoardBean.setMessageTypeDAO(messageTypeDAO);
		model.addAttribute("bean", messageDashBoardBean);
		if (StringUtils.isNotEmpty(messageDashBoardBean.getMessageType())) {

			Workbook workbook = null;
			Sheet sheetBuy = null;
			MultipartFile multipartFile = null;
			Row row = null;
			try {
				multipartFile = multipartHttpServletRequest.getFile("fileUpload");

				if (multipartFile.equals(null)) {
					return "messages.import";
				}

				String contentType = multipartFile.getContentType();
				try {
					if (StringUtils.equals(contentType, "application/vnd.ms-excel")) {
						workbook = new HSSFWorkbook(multipartFile.getInputStream());
					} else {
						workbook = new XSSFWorkbook(multipartFile.getInputStream());
					}
				} catch (Exception e) {

					logger.error(e.getMessage());
					messageDashBoardBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.import.onlyExcelAllow", null, locale));
					return "messages.import";

				}
				sheetBuy = workbook.getSheetAt(0);
				// kiem tra loai file import vao

				if (sheetBuy == null) {
					messageDashBoardBean.addMessage(Message.ERROR, msgSrc.getMessage("pendingPayment.file.not.equal.template", null, locale));
					return "messages.import";
				}

				CellStyle cellStyleError = workbook.createCellStyle();
				// cellStyleError.set màu
				Font fontRed = workbook.createFont();
				fontRed.setColor(HSSFColor.RED.index);
				cellStyleError.setFont(fontRed);

				ExcelHelper excelHelper = new ExcelHelper();

				List<MessageDashBoard> messageDashBoardImport = new ArrayList<MessageDashBoard>();
				String err = "";
				boolean isHasError = false;
				for (int i = 1; i < sheetBuy.getPhysicalNumberOfRows(); i++) {

					MessageDashBoard messageDashBoard = new MessageDashBoard();
					row = (Row) sheetBuy.getRow(i);
					if (row.getCell(0) != null) {
						// lay idcarnumber ra tìm customerid
						Customer customer = customerService.findByIdCardNumber(row.getCell(0).getStringCellValue());

						if (customer == null) {
							row.createCell(4).setCellStyle(cellStyleError);
							excelHelper.fillCell(sheetBuy, i, 4,
									err += " " + msgSrc.getMessage("customer.field.idcarnumber.notexist", null, locale) + " ");
							isHasError = true;
						} else {
							messageDashBoard.setCustomerId(customer.getCustomerId());
							messageDashBoard.setCustomer(customer);
						}

					} else {
						row.createCell(4).setCellStyle(cellStyleError);
						excelHelper.fillCell(sheetBuy, i, 4, err += " " + msgSrc.getMessage("customer.field.idcarnumber.nul", null, locale) + " ");
						isHasError = true;
					}
					if (row.getCell(1) != null) {
						String cellPhone = String.valueOf(row.getCell(1));
						;
						if (StringUtils.isNotEmpty(cellPhone)) {
							Customer customerSearchCellPhone = customerService.findByIdCellPhone(cellPhone.trim());
							if (customerSearchCellPhone == null) {
								// Bôi đỏ
								// Ghi log
								// Set flag có lỗi
								row.createCell(4).setCellStyle(cellStyleError);
								excelHelper.fillCell(sheetBuy, i, 4,
										err += " " + msgSrc.getMessage("customer.field.cellphone.notexist", null, locale) + " ");
								isHasError = true;
							} else
								messageDashBoard.setCellphone(cellPhone.trim());
						} else {
							row.createCell(4).setCellStyle(cellStyleError);
							excelHelper.fillCell(sheetBuy, i, 4, err += " " + msgSrc.getMessage("customer.field.cellphone.null", null, locale) + " ");
							isHasError = true;
						}
					} else {
						row.createCell(4).setCellStyle(cellStyleError);
						excelHelper.fillCell(sheetBuy, i, 4, err += " " + msgSrc.getMessage("customer.field.cellphone.null", null, locale) + " ");
						isHasError = true;
					}
					if (row.getCell(2) != null) {
						String subject = String.valueOf(row.getCell(2).getRichStringCellValue());
						if (StringUtils.isNotEmpty(subject)) {
							messageDashBoard.setSubject(subject);
						} else {
							messageDashBoard.setSubject(null);
						}
					} else {
						messageDashBoard.setSubject(null);
					}
					if (row.getCell(3) != null) {
						String content = String.valueOf(row.getCell(3).getRichStringCellValue());
						if (StringUtils.isNotEmpty(content)) {
							messageDashBoard.setContent(content);
						} else {
							messageDashBoard.setContent(null);
						}
					} else {
						messageDashBoard.setContent(null);
					}
					messageDashBoard.setType(Integer.parseInt(messageDashBoardBean.getMessageType()));
					messageDashBoard.setDATEIMPORT(new Date());
					messageDashBoard.setIsRead(0);
					messageDashBoard.setStatus(1);
					messageDashBoard.setIsMsgUser(0);
					messageDashBoard.setDeviceID("auto");
					messageDashBoard.setCreatedDate(new Date());
					messageDashBoardImport.add(messageDashBoard);

				}
				if (isHasError == false) {

					try {

						messageDashBoardBean.setMessageImportLst(messageDashBoardImport);
						messageService.saveMessageDashBoard(messageDashBoardImport);

						if (messageDashBoardImport == null || messageDashBoardImport.size() <= 0) {
							messageDashBoardBean.addMessage(Message.ERROR, msgSrc.getMessage("pendingPayment.import.fail", null, locale));
						} else {
							List<MessageDashBoard> getDaoImport = messageService.findImportAuto("auto");

							for (MessageDashBoard iteammessageDashBoard : getDaoImport) {
								iteammessageDashBoard.setParentMsgId(iteammessageDashBoard.getMessageID());
								iteammessageDashBoard.setDeviceID(null);
								// messageService.save(iteammessageDashBoard);
								String restUrl = "";
								try {
									restUrl = iteammessageDashBoard.getContent();
									if (StringUtils.isNotEmpty(restUrl) && restUrl.length() > 100) {
										restUrl = restUrl.substring(0, 100) + "...";
									}
									pushNotificationService.pushNotification(iteammessageDashBoard.getCustomer().getCustomerId().toString(), restUrl,
											iteammessageDashBoard.getParentMsgId() != null ? iteammessageDashBoard.getParentMsgId().toString()
													: iteammessageDashBoard.getMessageID().toString(),
											false);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							messageService.saveMessageDashBoard(getDaoImport);
							messageDashBoardBean.addMessage(Message.SUCCESS, msgSrc.getMessage("pendingPayment.import.done", null, locale));
							// log mess import
							try {
								if (messageDashBoardImport == null || messageDashBoardImport.size() >= 0) {
									messageactivityLogService.saveMessageImportActivityLog(SystemConfig.SAVE_MESSAGE_IMPORT, SystemConfig.MESSAGE,
											locale, SystemConfig.SYSTEM, null, userProfile.getAccount().getId().toString(),
											"Import " + messageDashBoardImport.size() + "message");
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					} catch (Exception e) {
						logger.debug("##saveCustomers##", e);
					}

				} else {
					resp.setContentType(contentType);
					resp.setHeader("Content-Disposition", "attachment; filename=\"OUT_" + multipartFile.getOriginalFilename() + "\"");
					ServletOutputStream servletOutputStream = resp.getOutputStream();
					workbook.write(servletOutputStream);
					resp.flushBuffer();
					return "redirect:messages/init";
				}

			} catch (IOException e) {
				System.out.println("Format file is incorrect!");
			}

			return "messages.import";
		} else {
			messageDashBoardBean.addMessage(Message.ERROR, msgSrc.getMessage("customer.messages.field.type.notnull", null, locale));
			return "messages.import";
		}
	}

	@RequestMapping(value = "dashboard/importlist", method = { RequestMethod.GET, RequestMethod.POST })
	public String listMessageImportDashBoard(@ModelAttribute(value = "bean") MessageDashBoardBean bean, Model model, Locale locale,
			HttpServletRequest request) throws Exception {
		bean.clearMessages();
		MessageDashBoardBean result = messagesService.searchImport(bean);
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		List<Hotline> messageTypeDAO = masterdatalDetailService.findDetail("MessageType");
		model.addAttribute("messageTypeDAO", messageTypeDAO);
		model.addAttribute("bean", result);
		return "messages.import.list";
	}
}