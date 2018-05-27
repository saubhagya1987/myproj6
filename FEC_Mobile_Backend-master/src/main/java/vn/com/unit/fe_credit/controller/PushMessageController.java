package vn.com.unit.fe_credit.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.PushMessageBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.config.SystemConfig.MessageStatus;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MessageActivityLogService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.PushMessageTemplateService;
import vn.com.unit.fe_credit.service.PushNotificationService;
import vn.com.unit.fe_credit.utils.ajax.ReturnObject;
import vn.com.unit.fe_credit.utils.excel.ExcelHelper;
import vn.com.unit.fe_credit.utils.excel.ExcelUtils;
import vn.com.unit.webservice.Hotline;

@Controller
@RequestMapping(value = "/push_message")
public class PushMessageController {

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	MasterdataDetailService masterdatalDetailService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageActivityLogService messageactivityLogService;

	@Autowired
	private UserProfile userProfile;

	@Autowired
	private PushMessageTemplateService pushMessageTemplateService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	PushNotificationService pushNotificationService;

	private static final Logger logger = LoggerFactory.getLogger(PushMessageController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates

		if (request.getSession() == null) {
			return;
		}

		if (request.getSession().getAttribute("formatDate") != null) {

			SimpleDateFormat dateFormat2 = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
			CustomDateEditor editor2 = new CustomDateEditor(dateFormat2, true);
			binder.registerCustomEditor(Date.class, editor2);

			SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate") + "HH:mm");
			// Create a new CustomDateEditor
			CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
			// Register it as custom editor for the Date type
			binder.registerCustomEditor(Date.class, "entity.schedule", editor);

		}

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

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String pushMessageInit(@ModelAttribute(value = "bean") PushMessageBean pushMessageBean, Model model, HttpServletRequest request,
			Locale locale, HttpServletResponse response) throws Exception {
		List<Hotline> messageTypeDao = masterdatalDetailService.findDetail("MessageType");
		pushMessageBean.setMessageTypeDao(messageTypeDao);
		if (pushMessageBean.getOptionType() != null) {
			if (pushMessageBean.getOptionType() == PushMessageBean.TYPE_SEND_NOW) {
				pushMessageBean.setOptionType(PushMessageBean.TYPE_SEND_NOW);
			}

			if (pushMessageBean.getOptionType() == PushMessageBean.TYPE_SET_CALENDAR) {
				pushMessageBean.setOptionType(PushMessageBean.TYPE_SET_CALENDAR);
				// pushMessageBean.setDateSchedule(pushMessageBean.getDateSchedule());
				// pushMessageBean.setTimeSchedule(pushMessageBean.getTimeSchedule());
			}
		}

		Integer countTime = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_TIME_PUSHMESSAGE));

		model.addAttribute("countTime", countTime);
		model.addAttribute("bean", pushMessageBean);
		return "push.message.import";
	}

	@RequestMapping(value = "/checkDateScheduleText", method = RequestMethod.POST)
	@ResponseBody
	public Object checkDateScheduleText(@ModelAttribute(value = "bean") PushMessageBean pushMessageBean, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse resp, RedirectAttributes redirectAttributes, HttpServletResponse response)
			throws Exception {

		ReturnObject returnObject = new ReturnObject();
		try {
			Date dateSchedule = pushMessageBean.getEntity().getSchedule();

			if (dateSchedule == null) {
				throw new Exception(msgSrc.getMessage("push.message.time.empty", null, locale));
			}

			if (dateSchedule.before(new Date())) {
				throw new Exception(msgSrc.getMessage("push.message.date.error", null, locale));
			}

			Integer countTime = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_TIME_PUSHMESSAGE));
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.HOUR, countTime);
			Date currentDateAfterConfig = calendar.getTime();
			if (dateSchedule.before(currentDateAfterConfig)) {
				throw new Exception(
						msgSrc.getMessage("push.message.time.error" + " " + countTime + " hours from when you create them.", null, locale));
			}

			returnObject.setStatus(ReturnObject.SUCCESS);
		} catch (Exception e) {
			returnObject.setStatus(ReturnObject.ERROR);
			returnObject.setMessage(e.getMessage());
		}

		return returnObject;

	}

	@RequestMapping(value = "/init", method = RequestMethod.POST)
	public String pushMessageInitPost(MultipartHttpServletRequest multipartHttpServletRequest,
			@ModelAttribute(value = "bean") PushMessageBean pushMessageBean, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse resp, RedirectAttributes redirectAttributes, HttpServletResponse response) throws Exception {

		List<Hotline> messageTypeDao = masterdatalDetailService.findDetail("MessageType");
		pushMessageBean.setMessageTypeDao(messageTypeDao);

		if (String.valueOf(pushMessageBean.getTemplateSubject()).isEmpty()) {
			pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.template.subject.null", null, locale));
			redirectAttributes.addFlashAttribute("bean", pushMessageBean);
			return "redirect:/push_message/init";
		}

		if (pushMessageBean.getMessageType() == null) {
			pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("customer.messages.field.type.notnull", null, locale));
			redirectAttributes.addFlashAttribute("bean", pushMessageBean);
			return "redirect:/push_message/init";
		}

		Workbook workbook = null;
		PushMessageTemplate pushMessageTemplate = new PushMessageTemplate();

		try {

			MultipartFile multipartFile = multipartHttpServletRequest.getFile("fileUpload");

			if (multipartFile.equals(null)) {
				throw new Exception("File not found");
			}

			// Check excel file
			String contentType = multipartFile.getContentType();
			System.out.println("##start read workbook##");
			try {
				if (StringUtils.equals(contentType, "application/vnd.ms-excel")) {
					workbook = new HSSFWorkbook(multipartFile.getInputStream());
				} else {
					workbook = new XSSFWorkbook(multipartFile.getInputStream());
				}
			} catch (Exception e) {
				throw new Exception(msgSrc.getMessage("msg.import.onlyExcelAllow", null, locale));
			}
			System.out.println("##end read workbook##");

			// Check excel file contain image
			List<? extends PictureData> lstImage = workbook.getAllPictures();
			if (CollectionUtils.isNotEmpty(lstImage)) {
				throw new Exception(msgSrc.getMessage("push.message.excel.contain.image.error", null, locale));
			}

			// get Sheet
			Sheet sheetData = workbook.getSheetAt(0);
			if (sheetData == null) {
				throw new Exception(msgSrc.getMessage("pendingPayment.file.not.equal.template", null, locale));
			}

			Row rowTitle = (Row) sheetData.getRow(0);

			if (rowTitle == null) {
				throw new Exception(msgSrc.getMessage("push.message.excel.template.error", null, locale));
			}
			if (!rowTitle.getCell(0).toString().equals("IDCARDNUMBER") || !rowTitle.getCell(1).toString().equals("CELLPHONE")
					|| !rowTitle.getCell(2).toString().equals("SUBJECT") || !rowTitle.getCell(3).toString().equals("CONTENT")) {
				throw new Exception(msgSrc.getMessage("push.message.excel.template.error", null, locale));
			}

			CellStyle cellStyleError = workbook.createCellStyle();
			Font fontRed = workbook.createFont();
			fontRed.setColor(HSSFColor.RED.index);
			cellStyleError.setFont(fontRed);

			ExcelHelper excelHelper = new ExcelHelper();
			List<MessageDashBoard> messageDashBoardList = new ArrayList<MessageDashBoard>();

			boolean isHasError = false;

			// Tạo mới PushMessageTemplate đẻ lấy PushMessageTemplateId
			pushMessageTemplateService.save(pushMessageTemplate);

			Map<String, Customer> existsCustomerMap = new HashMap<>();

			System.out.println("##getLastRowNum##" + sheetData.getLastRowNum());
			System.out.println("##StartReadAt##" + new Date());

			Account account = userProfile.getAccount();

			for (int i = 1; i <= sheetData.getLastRowNum(); i++) {

				System.out.println("##row##" + i);

				MessageDashBoard messageDashBoard = new MessageDashBoard();
				Row row = (Row) sheetData.getRow(i);

				StringBuilder err = new StringBuilder();

				if (row == null) {
					row = sheetData.createRow(i);
					row.createCell(4).setCellStyle(cellStyleError);
					err.append(" " + msgSrc.getMessage("customer.field.idcarnumber.nul", null, locale));
					err.append(" " + msgSrc.getMessage("customer.field.cellphone.null", null, locale));
					err.append(" " + msgSrc.getMessage("customer.field.subject.null", null, locale));
					err.append(" " + msgSrc.getMessage("customer.field.content.null", null, locale));
					excelHelper.fillCell(sheetData, i, 4, err.toString());
					isHasError = true;
					continue;
				}

				if (row.getCell(0) == null && row.getCell(1) == null && String.valueOf(row.getCell(2)).isEmpty()
						&& String.valueOf(row.getCell(3)).isEmpty()) {
					continue;
				}

				Customer customer = null;

				// Cell 0
				try {

					Cell cell = row.getCell(0);
					if (cell == null) {
						throw new Exception(msgSrc.getMessage("customer.field.idcarnumber.nul", null, locale));
					}

					cell.setCellType(Cell.CELL_TYPE_STRING);
					String idCardNumber = String.valueOf(cell.getStringCellValue());

					if (StringUtils.isBlank(idCardNumber)) {
						throw new Exception(msgSrc.getMessage("customer.field.idcarnumber.nul", null, locale));
					}

					customer = existsCustomerMap.get(idCardNumber);
					if (customer == null) {
						customer = customerService.findByIdCardNumber(idCardNumber);
						if (customer == null) {
							throw new Exception(msgSrc.getMessage("customer.field.idcarnumber.notexist", null, locale));
						}
						existsCustomerMap.put(idCardNumber, customer);
					}

					messageDashBoard.setCustomerId(customer.getCustomerId());
					messageDashBoard.setCustomer(customer);

				} catch (Exception e) {
					row.createCell(4).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetData, i, 4, err.append(" " + e.getMessage()));
					isHasError = true;
				}

				// Cell 1
				try {

					Cell cell = row.getCell(1);
					if (cell == null) {
						throw new Exception(msgSrc.getMessage("customer.field.cellphone.null", null, locale));
					}

					cell.setCellType(Cell.CELL_TYPE_STRING);
					String cellPhone = String.valueOf(cell.getStringCellValue());

					if (StringUtils.isBlank(cellPhone)) {
						throw new Exception(msgSrc.getMessage("customer.field.cellphone.null", null, locale));
					}

					if (customer != null) {
						if (!String.valueOf(cellPhone.trim()).equals(customer.getCellPhone().trim())) {
							throw new Exception(msgSrc.getMessage("customer.field.cellphone.notexist", null, locale));
						} else {
							messageDashBoard.setCellphone(cellPhone.trim());
						}
					}

				} catch (Exception e) {
					row.createCell(4).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetData, i, 4, err.append(" " + e.getMessage()));
					isHasError = true;
				}

				// Cell 2
				try {

					Cell cell = row.getCell(2);
					if (cell == null) {
						throw new Exception(msgSrc.getMessage("customer.field.subject.null", null, locale));
					}

					cell.setCellType(Cell.CELL_TYPE_STRING);
					String subject = String.valueOf(cell.getStringCellValue());

					if (StringUtils.isBlank(subject)) {
						throw new Exception(msgSrc.getMessage("customer.field.subject.null", null, locale));
					}

					if (subject.length() > 255) {
						throw new Exception(msgSrc.getMessage("push.message.subject.maxlength.error", null, locale));
					}

					messageDashBoard.setSubject(subject);

				} catch (Exception e) {
					row.createCell(4).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetData, i, 4, err.append(" " + e.getMessage()));
					isHasError = true;
				}

				// Cell 3
				try {

					Cell cell = row.getCell(3);
					if (cell == null) {
						throw new Exception(msgSrc.getMessage("customer.field.content.null", null, locale));
					}

					cell.setCellType(Cell.CELL_TYPE_STRING);
					String content = String.valueOf(cell.getStringCellValue());

					if (StringUtils.isBlank(content)) {
						throw new Exception(msgSrc.getMessage("customer.field.content.null", null, locale));
					}

					if (content.length() > 2000) {
						throw new Exception(msgSrc.getMessage("push.message.content.maxlength.error", null, locale));
					}

					messageDashBoard.setContent(content);

				} catch (Exception e) {
					row.createCell(4).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetData, i, 4, err.append(" " + e.getMessage()));
					isHasError = true;
				}

				messageDashBoard.setPushMessageId(pushMessageTemplate.getTemplateId());
				messageDashBoard.setPushMessageSubject(pushMessageBean.getTemplateSubject());

				if (customer != null) {
					messageDashBoard.setFullName(customer.getFullName());
				}

				messageDashBoard.setType(pushMessageBean.getMessageType());
				messageDashBoard.setDATEIMPORT(new Date());
				messageDashBoard.setIsRead(0);
				messageDashBoard.setStatus(MessageStatus.SENT.getIntValue());
				messageDashBoard.setIsMsgUser(0);
				messageDashBoard.setCreatedDate(new Date());
				messageDashBoard.setDeviceID(null);
				messageDashBoard.setIsParent(true);
				messageDashBoard.setACCOUNT(account);
				messageDashBoardList.add(messageDashBoard);

			}

			System.out.println("##ReadDoneAt##" + new Date());

			if (isHasError == false) {

				if (CollectionUtils.isEmpty(messageDashBoardList)) {
					throw new Exception(msgSrc.getMessage("pendingPayment.import.fail", null, locale));
				}

				// Save file import
				SimpleDateFormat dateExport = new SimpleDateFormat("MM-dd-yyyy");
				Date today = Calendar.getInstance().getTime();
				String reportDate = dateExport.format(today);

				Integer nofile = pushMessageTemplateService.searchMaxNoByDate(reportDate);
				Integer noFileExport = (nofile == 0) ? 1 : nofile + 1;

				SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
				String outFileName = f.format(new Date()) + "_" + String.format("%03d", noFileExport) + "_" + multipartFile.getOriginalFilename();
				String destFolder = systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE);
				File fileTmp = new File(destFolder + outFileName);
				workbook.write(new FileOutputStream(fileTmp));

				if (pushMessageBean.getOptionType() == PushMessageBean.TYPE_SEND_NOW) {

					// Lưu danh sách message
					System.out.println("##messageDashBoardList##" + messageDashBoardList.size());
					System.out.println("##StartSaveMessage##" + new Date());
					int stt = 0;
					// Lưu 50k dòng hết khoảng 11 phút
					for (List<MessageDashBoard> partition : Lists.partition(messageDashBoardList, 500)) {
						// System.out.println("##STT##" + (stt++));
						messageService.saveMessageDashBoard(partition);
					}
					System.out.println("##SaveMessageDone##" + new Date());

					// Set list
					pushMessageBean.setMessageImportLst(messageDashBoardList);

					// Pushmessage đến thiết bị
					System.out.println("##StartPUSHMessage##" + new Date());
					pushMessageNow(messageDashBoardList);
					System.out.println("##EndPUSHMessage##" + new Date());

					// Count pushmessage
					// pushMessageTemplate.setPushCountSuccess(pushMessageTemplateService.pushCountSuccess(pushMessageTemplate.getTemplateId()));
					// pushMessageTemplate.setPushCountFail(pushMessageTemplateService.pushCountFail(pushMessageTemplate.getTemplateId()));

					messageactivityLogService.saveMessageImportActivityLog(SystemConfig.SAVE_MESSAGE_IMPORT, SystemConfig.MESSAGE, locale,
							SystemConfig.SYSTEM, null, userProfile.getAccount().getId().toString(),
							"Import " + messageDashBoardList.size() + "message");

					pushMessageBean.setIsSendNow(PushMessageBean.TYPE_SEND_NOW);
					pushMessageBean.setSchedule(new Date());
					pushMessageBean.setStatusPush(true);

					pushMessageBean.addMessage(Message.SUCCESS, msgSrc.getMessage("pendingPayment.import.done", null, locale));

				} else if (pushMessageBean.getOptionType() == PushMessageBean.TYPE_SET_CALENDAR) {

					// validate time
					// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

					// String dateSchedule = pushMessageBean.getDateSchedule();
					// String timeSchedule = pushMessageBean.getTimeSchedule();
					// if (String.valueOf(timeSchedule).isEmpty() || String.valueOf(dateSchedule).isEmpty()) {
					// throw new Exception(msgSrc.getMessage("push.message.time.empty", null, locale));
					// }

					// Date inputTimeSchedule = sdf.parse(dateSchedule + " " + timeSchedule);

					Date currentDate = new Date();
					Date dateSchedule = pushMessageBean.getEntity().getSchedule();

					if (dateSchedule == null) {
						throw new Exception(msgSrc.getMessage("push.message.time.empty", null, locale));
					}

					if (dateSchedule.before(currentDate)) {
						throw new Exception(msgSrc.getMessage("push.message.date.error", null, locale));
					}

					Integer countTime = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_TIME_PUSHMESSAGE));
					Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.HOUR, countTime);
					Date currentDateAfterConfig = calendar.getTime();
					if (dateSchedule.before(currentDateAfterConfig)) {
						throw new Exception(
								msgSrc.getMessage("push.message.time.error" + " " + countTime + " hours from when you create them.", null, locale));
					}

					pushMessageBean.setIsSendNow(PushMessageBean.TYPE_SET_CALENDAR);
					pushMessageBean.setSchedule(dateSchedule);
					pushMessageBean.setStatusPush(false);

					pushMessageBean.addMessage(Message.SUCCESS, msgSrc.getMessage("mgm.redeem.status.Successful", null, locale));

				}

				pushMessageTemplate.setIpAddress(InetAddress.getLocalHost().toString());
				pushMessageTemplate.setTemplateSubject(pushMessageBean.getTemplateSubject());
				pushMessageTemplate.setMessageType(pushMessageBean.getMessageType());
				pushMessageTemplate.setFileName(outFileName);
				pushMessageTemplate.setNoInDate(noFileExport);

				pushMessageTemplate.setIsSendNow(pushMessageBean.getIsSendNow());
				pushMessageTemplate.setSchedule(pushMessageBean.getSchedule());
				pushMessageTemplate.setStatus(pushMessageBean.getStatusPush());

				pushMessageTemplateService.save(pushMessageTemplate);

			} else {

				if (pushMessageTemplate != null) {
					pushMessageTemplateService.delete(pushMessageTemplate);
				}

				resp.setContentType(contentType);
				resp.setHeader("Content-Disposition", "attachment; filename=\"OUT_" + multipartFile.getOriginalFilename() + "\"");
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				workbook.write(servletOutputStream);
				resp.flushBuffer();
				return "redirect:/push_message/init";

			}

		} catch (Exception e) {
			if (pushMessageTemplate != null) {
				pushMessageTemplateService.delete(pushMessageTemplate);
			}
			pushMessageBean.addMessage(Message.ERROR, e.getMessage());
			logger.debug("##SAVE_MESSAGE##", e);
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		logger.debug("##FINISH##");
		System.out.println("##FINISH##");
		return "push.message.import";

	}

	private void pushMessageNow(List<MessageDashBoard> messageDashBoardList) {
		if (CollectionUtils.isNotEmpty(messageDashBoardList)) {
			for (MessageDashBoard iteammessageDashBoard : messageDashBoardList) {
				try {

					String customerId = String.valueOf(iteammessageDashBoard.getCustomer().getCustomerId());
					String message = iteammessageDashBoard.getContent();
					String messId = String.valueOf(iteammessageDashBoard.getMessageID());

					iteammessageDashBoard.setPushDate(new Date());
					Integer responseCode = pushNotificationService.pushNotification(customerId, message, messId, false);
					iteammessageDashBoard.setPushNotificationStatus(responseCode);

				} catch (Exception e) {
					iteammessageDashBoard.setPushErrorMessages(StringUtils.left(e.getMessage(), 1500));
				} finally {
					// messageService.save(iteammessageDashBoard);
				}
			}
		}
	}

	@RequestMapping(value = "/downloadImportMessage", method = RequestMethod.GET)
	public String downloadImportMessage(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, IOException {
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
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bos.close();
		}
		return "redirect:/push_message/init";
	}

	@RequestMapping(value = "/list_template", method = RequestMethod.GET)
	public String pushMessageListTemplate(@ModelAttribute(value = "bean") PushMessageBean bean, Model model, Locale locale,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {

		Integer countTime = -1;
		Date currentDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.MONTH, countTime);
		Date formSendDate = calendar.getTime();
		Date toSendDate = currentDate;

		bean.setFormSendDate(formSendDate);
		bean.setToSendDate(toSendDate);

		PushMessageBean result = pushMessageTemplateService.searchPushMessageListTemplate(bean);
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		List<Hotline> messageType = masterdatalDetailService.findDetail("MessageType");

		model.addAttribute("currentDate", new Date());
		model.addAttribute("messageType", messageType);
		model.addAttribute("bean", result);
		return "push.message.list.template";
	}

	@RequestMapping(value = "/list_template", method = RequestMethod.POST)
	public String pushMessageListTemplatePost(@ModelAttribute(value = "bean") PushMessageBean bean, Model model, Locale locale,
			HttpServletRequest request, RedirectAttributes redirectAttributes) {
		bean.clearMessages();

		if (bean.getFormSendDate() != null && bean.getToSendDate() != null) {
			if (bean.getFormSendDate().after(bean.getToSendDate())) {
				bean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.create.fromto.date.error", null, locale));
				redirectAttributes.addFlashAttribute("bean", bean);
				return "push.message.list.template";
			}
		}

		PushMessageBean result = pushMessageTemplateService.searchPushMessageListTemplate(bean);
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		List<Hotline> messageType = masterdatalDetailService.findDetail("MessageType");

		model.addAttribute("currentDate", new Date());
		model.addAttribute("messageType", messageType);
		model.addAttribute("bean", result);
		return "push.message.list.template";
	}

	@RequestMapping(value = "/list_template/download", method = RequestMethod.POST)
	public void downloadFileUploaded(@ModelAttribute(value = "bean") PushMessageBean pushMessageBean, Model model, Locale locale,
			RedirectAttributes redirectAttributes, HttpServletResponse response) {

		try {
			PushMessageTemplate pushMessageTemplate = pushMessageTemplateService.findById(pushMessageBean.getEntity().getTemplateId());
			//
			//
			// Read file in folder contain template

			Workbook workbook = null;

			String fileName = pushMessageTemplate.getFileName();
			String domain = "";
			String path = "";
			File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE) + fileName);
			if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
				path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
			} else {
				path = systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE) + fileName;
			}

			workbook = WorkbookFactory.create(new File(domain + path));

			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				workbook.write(bos);
				byte[] bytes = bos.toByteArray();
				response.getOutputStream().write(bytes);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				bos.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/list_template/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean") PushMessageBean bean, Model model, Locale locale, RedirectAttributes redirectAttributes,
			BindingResult bindingResult) {

		PushMessageTemplate PushMessageTemplate = pushMessageTemplateService.findTemplateNoSentById(bean.getEntity().getTemplateId());
		if (PushMessageTemplate != null) {
			pushMessageTemplateService.delete(PushMessageTemplate.getTemplateId());
			bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
		} else {
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
		}
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/push_message/list_template";

	}

	@RequestMapping(value = "/list_template/edit", method = { RequestMethod.GET })
	public String edit(@ModelAttribute(value = "bean") @Valid PushMessageBean pushMessageBean,
			@RequestParam(value = "templateId", required = false) Long templateId, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) {
		pushMessageBean.setOptionType(pushMessageBean.TYPE_SET_CALENDAR);
		pushMessageBean.setEntity(pushMessageTemplateService.findById(templateId));
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", pushMessageBean);
		return "push.message.list.template.edit";
	}

	@RequestMapping(value = "/list_template/edit", method = RequestMethod.POST)
	public String edit_Post(@ModelAttribute(value = "bean") @Valid PushMessageBean pushMessageBean, BindingResult bindingResult, Model model,
			Locale locale, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			if (pushMessageBean.getOptionType() == pushMessageBean.TYPE_SEND_NOW) {
				PushMessageTemplate pushMessageTemplate = pushMessageTemplateService
						.findTemplateNoSentById(pushMessageBean.getEntity().getTemplateId());

				if (pushMessageTemplate != null) {

					// Check currentTime >= scheduleTime: Error
					Date currentDate = new Date();
					if (pushMessageTemplate.getSchedule().before(currentDate)) {
						pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.edit.time.sendnow.error", null, locale));
						redirectAttributes.addFlashAttribute("bean", pushMessageBean);
						return "push.message.list.template.edit";
					}

					// read file up, Send message now
					//
					//
					// Read file in folder contain template

					Workbook workbook = null;
					Sheet sheetBuy = null;
					Row row = null;

					String fileName = pushMessageTemplate.getFileName();
					String domain = "";
					String path = "";
					File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE) + fileName);
					if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
						path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
					} else {
						path = systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE) + fileName;
					}

					workbook = WorkbookFactory.create(new File(domain + path));

					//
					//
					// Send now data

					sheetBuy = workbook.getSheetAt(0);
					// kiem tra loai file import vao

					if (sheetBuy == null) {
						throw new Exception(msgSrc.getMessage("pendingPayment.file.not.equal.template", null, null));
					}

					CellStyle cellStyleError = workbook.createCellStyle();
					// cellStyleError.set màu
					Font fontRed = workbook.createFont();
					fontRed.setColor(HSSFColor.RED.index);
					cellStyleError.setFont(fontRed);

					// ExcelHelper excelHelper = new ExcelHelper();

					Map<String, Customer> existsCustomerMap = new HashMap<>();

					List<MessageDashBoard> messageDashBoardImport = new ArrayList<MessageDashBoard>();
					for (int i = 1; i < sheetBuy.getPhysicalNumberOfRows(); i++) {

						MessageDashBoard messageDashBoard = new MessageDashBoard();
						row = (Row) sheetBuy.getRow(i);

						if (row.getCell(0) == null && row.getCell(1) == null && String.valueOf(row.getCell(2)).isEmpty()
								&& String.valueOf(row.getCell(3)).isEmpty()) {
							continue;
						}

						Customer customer = null;
						Cell cell = null;

						if (row.getCell(0) != null) {
							cell = row.getCell(0);
							cell.setCellType(Cell.CELL_TYPE_STRING);

							String idCardNumber = String.valueOf(cell.getStringCellValue());
							customer = existsCustomerMap.get(idCardNumber);

							if (customer == null) {
								customer = customerService.findByIdCardNumber(idCardNumber);
								if (customer == null) {
									throw new Exception(msgSrc.getMessage("customer.field.idcarnumber.notexist", null, locale));
								}
								existsCustomerMap.put(idCardNumber, customer);
							}

							messageDashBoard.setCustomerId(customer.getCustomerId());
							messageDashBoard.setCustomer(customer);

						}

						if (row.getCell(1) != null) {
							String cellPhone = String.valueOf(row.getCell(1));
							messageDashBoard.setCellphone(cellPhone.trim());
						}

						if (row.getCell(2) != null) {
							cell = row.getCell(2);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String subject = String.valueOf(cell.getStringCellValue());
							messageDashBoard.setSubject(subject);
						}
						if (row.getCell(3) != null) {
							cell = row.getCell(3);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String content = String.valueOf(cell.getStringCellValue());
							messageDashBoard.setContent(content);
						}

						messageDashBoard.setPushMessageId(pushMessageTemplate.getTemplateId());
						messageDashBoard.setPushMessageSubject(pushMessageTemplate.getTemplateSubject());

						if (customer != null) {
							messageDashBoard.setFullName(customer.getFullName());
						}

						messageDashBoard.setType(pushMessageTemplate.getMessageType());
						messageDashBoard.setDATEIMPORT(new Date());
						messageDashBoard.setIsRead(0);
						messageDashBoard.setStatus(MessageStatus.SENT.getIntValue());
						messageDashBoard.setIsMsgUser(0);
						messageDashBoard.setCreatedDate(new Date());
						messageDashBoard.setDeviceID(null);
						messageDashBoard.setIsParent(true);
						messageDashBoardImport.add(messageDashBoard);

					}

					try {

						for (List<MessageDashBoard> partition : Lists.partition(messageDashBoardImport, 500)) {
							messageService.saveMessageDashBoard(partition);
						}

						// Pushmessage đến thiết bị
						pushMessageNow(messageDashBoardImport);

						// Count pushmessage
						// pushMessageTemplate.setPushCountSuccess(pushMessageTemplateService.pushCountSuccess(pushMessageTemplate.getTemplateId()));
						// pushMessageTemplate.setPushCountFail(pushMessageTemplateService.pushCountFail(pushMessageTemplate.getTemplateId()));

						messageactivityLogService.saveMessageImportActivityLog(SystemConfig.SAVE_MESSAGE_IMPORT, SystemConfig.MESSAGE, locale,
								SystemConfig.SYSTEM, null, userProfile.getAccount().getId().toString(),
								"Import " + messageDashBoardImport.size() + "message");

						pushMessageBean.setIsSendNow(PushMessageBean.TYPE_SEND_NOW);
						pushMessageBean.setSchedule(new Date());
						pushMessageBean.setStatusPush(true);

						// UPDATE template pushmessage
						pushMessageTemplate.setIsSendNow(1);
						pushMessageTemplate.setIpAddress(InetAddress.getLocalHost().toString());
						pushMessageTemplate.setSchedule(currentDate);
						pushMessageTemplate.setStatus(true);
						pushMessageTemplateService.save(pushMessageTemplate);

						pushMessageBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
						model.addAttribute("success", true);

					} catch (Exception e) {
						logger.debug("##Push Message##", e);
						if (messageDashBoardImport != null && messageDashBoardImport.size() > 0) {
							messageService.deleteMessageDashBoard(messageDashBoardImport);
						}
					}
				} else {
					pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.template.data.no.correct", null, locale));
				}
			}

			// set calendar: UPdate schedule
			if (pushMessageBean.getOptionType() == pushMessageBean.TYPE_SET_CALENDAR) {

				Date currentDate = new Date();
				Date dateSchedule = pushMessageBean.getEntity().getSchedule();

				if (dateSchedule == null) {
					pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.time.empty", null, locale));
					redirectAttributes.addFlashAttribute("bean", pushMessageBean);
					return "push.message.list.template.edit";
				}

				if (dateSchedule.before(currentDate)) {
					pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.date.error", null, locale));
					redirectAttributes.addFlashAttribute("bean", pushMessageBean);
					return "push.message.list.template.edit";
				}

				Integer countTime = Integer.parseInt(systemConfig.getConfig(SystemConfig.MIN_TIME_PUSHMESSAGE));
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR, countTime);
				Date currentDateAfterConfig = calendar.getTime();
				if (dateSchedule.before(currentDateAfterConfig)) {
					pushMessageBean.addMessage(Message.ERROR,
							msgSrc.getMessage("push.message.time.error" + " " + countTime + " hours from when you create them.", null, locale)
									+ " less " + countTime + " hour");
					redirectAttributes.addFlashAttribute("bean", pushMessageBean);
					return "push.message.list.template.edit";
				}
				// end validate time

				// Save entity
				// UPDATE template pushmessage
				PushMessageTemplate pushMessageTemplate = pushMessageTemplateService
						.findTemplateNoSentById(pushMessageBean.getEntity().getTemplateId());
				if (pushMessageTemplate != null) {
					pushMessageTemplate.setIsSendNow(0);
					pushMessageTemplate.setIpAddress(InetAddress.getLocalHost().toString());
					pushMessageTemplate.setSchedule(dateSchedule);
					pushMessageTemplate.setStatus(false);
					pushMessageTemplateService.save(pushMessageTemplate);
					pushMessageBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
					model.addAttribute("success", true);
				} else {
					pushMessageBean.addMessage(Message.ERROR, msgSrc.getMessage("push.message.setcalendar.error", null, locale));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "push.message.list.template.edit";
	}

}
