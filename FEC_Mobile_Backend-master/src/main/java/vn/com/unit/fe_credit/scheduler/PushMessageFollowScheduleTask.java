package vn.com.unit.fe_credit.scheduler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.config.SystemConfig.MessageStatus;
import vn.com.unit.fe_credit.controller.PushMessageController;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MessageActivityLogService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.PushMessageTemplateService;
import vn.com.unit.fe_credit.service.PushNotificationService;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class PushMessageFollowScheduleTask extends QuartzJobBean implements ScheduleTask {

	@Autowired
	PushMessageTemplateService pushMessageTemplateService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageService messageService;

	@Autowired
	private MessageActivityLogService messageactivityLogService;

	@Autowired
	PushNotificationService pushNotificationService;

	@Autowired
	AccountService accountService;

	private static final Logger logger = LoggerFactory.getLogger(PushMessageController.class);

	@Override
	public void doTask() {
		try {

			// read file up, send message follow currentDate

			Account account = accountService.findByAccountName("admin");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String currentDate = sdf.format(new Date());
			List<PushMessageTemplate> lst = pushMessageTemplateService.getPushMessageTemplateByDate(currentDate);
			if (lst.size() > 0) {
				for (PushMessageTemplate item : lst) {

					Workbook workbook = null;
					Sheet sheetBuy = null;
					Row row = null;

					String fileName = item.getFileName();
					String domain = "";
					String path = "";
					File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE) + fileName);
					if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
						path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
					} else {
						path = systemConfig.getConfig(SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE) + fileName;
					}

					workbook = WorkbookFactory.create(new File(domain + path));

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
									throw new Exception(msgSrc.getMessage("customer.field.idcarnumber.notexist", null, null));
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
							String subject = String.valueOf(cell.getRichStringCellValue());
							messageDashBoard.setSubject(subject);
						}
						if (row.getCell(3) != null) {
							cell = row.getCell(3);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							String content = String.valueOf(cell.getRichStringCellValue());
							messageDashBoard.setContent(content);
						}

						messageDashBoard.setPushMessageId(item.getTemplateId());
						messageDashBoard.setPushMessageSubject(item.getTemplateSubject());

						if (customer != null) {
							messageDashBoard.setFullName(customer.getFullName());
						}
						messageDashBoard.setType(item.getMessageType());
						messageDashBoard.setDATEIMPORT(new Date());
						messageDashBoard.setIsRead(0);
						messageDashBoard.setStatus(MessageStatus.SENT.getIntValue());
						messageDashBoard.setIsMsgUser(0);
						messageDashBoard.setCreatedDate(new Date());
						messageDashBoard.setDeviceID(null);
						messageDashBoard.setIsParent(true);
						messageDashBoard.setACCOUNT(account);
						messageDashBoardImport.add(messageDashBoard);

					}

					try {

						for (List<MessageDashBoard> partition : Lists.partition(messageDashBoardImport, 500)) {
							messageService.saveMessageDashBoard(partition);
						}

						// Pushmessage đến thiết bị
						pushMessageNow(messageDashBoardImport);

						// Count pushmessage
						// item.setPushCountSuccess(pushMessageTemplateService.pushCountSuccess(item.getTemplateId()));
						// item.setPushCountFail(pushMessageTemplateService.pushCountFail(item.getTemplateId()));

						try {
							if (messageDashBoardImport == null || messageDashBoardImport.size() >= 0) {
								messageactivityLogService.saveMessageImportActivityLog(SystemConfig.SAVE_MESSAGE_IMPORT, SystemConfig.MESSAGE, null,
										SystemConfig.SYSTEM, null, null, "Import " + messageDashBoardImport.size() + "message");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						// UPDATE template pushmessage
						item.setStatus(true);
						pushMessageTemplateService.save(item);

					} catch (Exception e) {
						logger.debug("##Push message##", e);
						if (messageDashBoardImport != null && messageDashBoardImport.size() > 0) {
							messageService.deleteMessageDashBoard(messageDashBoardImport);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		doTask();

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

}
