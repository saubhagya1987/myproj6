package vn.com.unit.fe_credit.service.impl;

import java.io.File;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.bean.PushMessageBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.PushMessageTemplateDao;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MessageActivityLogService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.PushMessageTemplateService;
import vn.com.unit.fe_credit.service.PushNotificationService;

@Service("PushMessageTemplateService")
@Transactional(readOnly = true)
public class PushMessageTemplateServiceImpl implements PushMessageTemplateService {

	@Autowired
	private PushMessageTemplateDao pushMessageTemplateDao;

	@Autowired
	private UserProfile userProfile;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private MessageService messageService;

	@Autowired
	PushNotificationService pushNotificationService;

	@Autowired
	private MessageActivityLogService messageactivityLogService;

	@Transactional
	public void save(PushMessageTemplate pushMessageTemplate) {
		if (RequestContextHolder.getRequestAttributes() != null) {
			if (pushMessageTemplate.getTemplateId() == null) {
				pushMessageTemplate.setCreated_date(new Date());
				if (userProfile.getAccount() != null) {
					pushMessageTemplate.setCreated_by(userProfile.getAccount().getUsername());
				}
			} else {
				pushMessageTemplate.setUpdated_date(new Date());
				if (userProfile.getAccount() != null) {
					pushMessageTemplate.setUpdated_by(userProfile.getAccount().getUsername());
				}
			}
		}

		pushMessageTemplateDao.save(pushMessageTemplate);
	}

	@Override
	@Transactional
	public void delete(PushMessageTemplate pushMessageTemplate) {
		pushMessageTemplateDao.remove(pushMessageTemplate);
	}

	@Override
	public Integer searchMaxNoByDate(String date) {
		return pushMessageTemplateDao.searchMaxNoByDate(date);
	}

	@Override
	public List<PushMessageTemplate> getPushMessageTemplateByDate(String date) {
		return pushMessageTemplateDao.getPushMessageTemplateByDate(date);
	}

	@Override
	public PushMessageTemplate findById(Long templateId) {
		// TODO Auto-generated method stub
		return pushMessageTemplateDao.find(templateId);
	}

	@Override
	public PushMessageTemplate findTemplateNoSentById(Long templateId) {
		Search search = new Search(PushMessageTemplate.class);
		search.addFilterEqual("templateId", templateId);
		search.addFilterEqual("status", false);
		List<PushMessageTemplate> result = pushMessageTemplateDao.search(search);
		if (result.size() > 0)
			return result.get(0);
		return null;
	}

	@Override
	public PushMessageBean searchPushMessageListTemplate(PushMessageBean bean) {
		return pushMessageTemplateDao.searchPushMessageListTemplate(bean);
	}

	@Override
	@Transactional
	public void delete(Long messageId) {
		pushMessageTemplateDao.removeById(messageId);
	}

	@Override
	public List<MessageDashBoard> getDataFileImported(Workbook workbook) {
		return null;
	}

	@Override
	@Transactional
	public void pushMessageFromTemplate(PushMessageTemplate pushMessageTemplate) throws Exception {

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

		sheetBuy = workbook.getSheetAt(0);
		if (sheetBuy == null) {
			throw new Exception(msgSrc.getMessage("pendingPayment.file.not.equal.template", null, null));
		}

		List<MessageDashBoard> messageDashBoardList = new ArrayList<MessageDashBoard>();
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
				customer = customerService.findByIdCardNumber(cell.getStringCellValue());
				messageDashBoard.setCustomerId(customer.getCustomerId());
				messageDashBoard.setCustomer(customer);
			}

			if (row.getCell(1) != null) {
				cell = row.getCell(1);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellPhone = String.valueOf(cell.getStringCellValue());
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

			messageDashBoard.setType(pushMessageTemplate.getMessageType());
			messageDashBoard.setDATEIMPORT(new Date());
			messageDashBoard.setIsRead(0);
			messageDashBoard.setStatus(1);
			messageDashBoard.setIsMsgUser(0);
			messageDashBoard.setDeviceID("auto");
			messageDashBoard.setCreatedDate(new Date());
			messageDashBoardList.add(messageDashBoard);

		}

		// push message
		messageService.saveMessageDashBoard(messageDashBoardList);

		if (messageDashBoardList != null && messageDashBoardList.size() > 0) {

			for (MessageDashBoard iteammessageDashBoard : messageDashBoardList) {
				iteammessageDashBoard.setParentMsgId(iteammessageDashBoard.getMessageID());
				iteammessageDashBoard.setDeviceID(null);
				String content = "";
				try {
					content = iteammessageDashBoard.getContent();
					pushNotificationService.pushNotification(iteammessageDashBoard.getCustomer().getCustomerId().toString(), content,
							iteammessageDashBoard.getParentMsgId() != null ? iteammessageDashBoard.getParentMsgId().toString()
									: iteammessageDashBoard.getMessageID().toString(),
							false);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			// Save message to db
			messageService.saveMessageDashBoard(messageDashBoardList);

			// UPDATE template pushmessage
			pushMessageTemplate.setIpAddress(InetAddress.getLocalHost().toString());
			if (pushMessageTemplate.getTemplateId() == null) {
				pushMessageTemplate.setCreated_date(new Date());
				if (userProfile.getAccount() != null)
					pushMessageTemplate.setCreated_by(userProfile.getAccount().getUsername());
			} else {
				pushMessageTemplate.setUpdated_date(new Date());
				if (userProfile.getAccount() != null)
					pushMessageTemplate.setUpdated_by(userProfile.getAccount().getUsername());
			}
			pushMessageTemplate.setIpAddress(InetAddress.getLocalHost().toString());
			pushMessageTemplate.setSchedule(new Date());
			pushMessageTemplate.setStatus(true);
			pushMessageTemplateDao.save(pushMessageTemplate);

			// log mess import
			try {
				if (messageDashBoardList == null || messageDashBoardList.size() >= 0) {
					messageactivityLogService.saveMessageImportActivityLog(SystemConfig.SAVE_MESSAGE_IMPORT, SystemConfig.MESSAGE, null,
							SystemConfig.SYSTEM, null, null, "Import " + messageDashBoardList.size() + "message");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Integer pushCountSuccess(Long templateId) {
		return pushMessageTemplateDao.pushCountSuccess(templateId);
	}

	@Override
	public Integer pushCountFail(Long templateId) {
		return pushMessageTemplateDao.pushCountFail(templateId);
	}

}
