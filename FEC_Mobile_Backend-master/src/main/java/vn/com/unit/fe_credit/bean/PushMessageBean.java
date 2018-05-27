package vn.com.unit.fe_credit.bean;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;
import vn.com.unit.webservice.Hotline;

@Getter
@Setter
public class PushMessageBean extends AbstractBean<PushMessageTemplate> {

	public static final int TYPE_SEND_NOW = 1;

	public static final int TYPE_SET_CALENDAR = 0;

	String templateSubject;

	Integer messageType;

	String pathFile;

	Integer optionType;

	Date formSendDate;

	Date toSendDate;

	private List<MessageDashBoard> messageImportLst;

	private CommonsMultipartFile fileUpload;

	private List<Hotline> messageTypeDao;

	String dateSchedule;

	String timeSchedule;

	Date schedule;

	Integer isSendNow;

	String ipAddress;

	Boolean statusPush;

	PushMessageTemplate pushMessageTemplate;

}
