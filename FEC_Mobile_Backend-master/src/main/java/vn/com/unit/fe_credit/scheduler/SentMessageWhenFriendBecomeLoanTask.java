package vn.com.unit.fe_credit.scheduler;

import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import vn.com.unit.fe_credit.config.SystemConfig.MessageStatus;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.MgmSuggestedContactsService;
import vn.com.unit.webservice.PushNotification;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SentMessageWhenFriendBecomeLoanTask extends QuartzJobBean implements ScheduleTask {

	@Autowired
	MgmSuggestedContactsService mgmSuggestedContactsService;

	@Autowired
	CustomerService customerService;

	@Autowired
	MessageService messageService;

	@Autowired
	MasterdataDetailService masterdataDetailService;

	@Override
	public void doTask() {

		try {
			List<Map<String, Object>> datas = mgmSuggestedContactsService.getListPushMessageWhenFriendBecomeLoan();
			NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("en"));
			MasterdataDetal masterdataDetalVi = masterdataDetailService.getMasterData("MGM_MOBILE_VI", "MGM_FRIEND_BECOME_LOAN");
			MasterdataDetal masterdataDetalEn = masterdataDetailService.getMasterData("MGM_MOBILE_EN", "MGM_FRIEND_BECOME_LOAN");
			
			if (CollectionUtils.isNotEmpty(datas)) {
				for (Map<String, Object> item : datas) {
					try {
						// String RELATION_ID = (String) item.get("RELATION_ID");
						String CUSTOMER_ID = (String) item.get("CUSTOMER_ID");
						
						String NUMBER_FRIEND = numberFormat.format(item.get("NUMBER_FRIEND"));
						String RECEIVED_POINT = numberFormat.format(item.get("RECEIVED_POINT"));

						Customer customer = customerService.customerIDtoAccountID(CUSTOMER_ID);

						MessageDashBoard messageDashBoardL = new MessageDashBoard();
						messageDashBoardL.setCustomer(customer);
						messageDashBoardL.setFullName(customer.getFullName());
						messageDashBoardL.setCellphone(customer.getCellPhone());
						
						if (StringUtils.equalsIgnoreCase(customer.getDefaultLanguage(), "vi")) {
							String msgTempVi = masterdataDetalVi.getDescription();
							messageDashBoardL.setSubject("THÔNG BÁO");
							messageDashBoardL.setContent(MessageFormat.format(msgTempVi, NUMBER_FRIEND, RECEIVED_POINT));
						} else if (StringUtils.equalsIgnoreCase(customer.getDefaultLanguage(), "en")) {
							String msgTempEn = masterdataDetalEn.getDescription();
							messageDashBoardL.setSubject("NOTICE");
							messageDashBoardL.setContent(MessageFormat.format(msgTempEn, NUMBER_FRIEND, RECEIVED_POINT));
						}
						
						messageDashBoardL.setStatus(MessageStatus.SENT.getIntValue());
						messageDashBoardL.setType(9);
						messageDashBoardL.setIsRead(0);
						messageDashBoardL.setIsMsgUser(0);
						messageDashBoardL.setCreatedDate(new Date());
						messageService.save(messageDashBoardL);
						
						String content = messageDashBoardL.getContent();
						content = Jsoup.parse(String.valueOf(messageDashBoardL.getContent())).text();
						
						try {
							PushNotification.pushNotification(URLEncoder.encode(String.valueOf(customer.getCustomerId()), "UTF-8"),
									content,
									messageDashBoardL.getMessageID().toString());
						} catch (Exception e) {
							e.printStackTrace();
						}

						messageDashBoardL.setParentMsgId(messageDashBoardL.getMessageID());
						messageService.save(messageDashBoardL);

					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		doTask();
	}
}
