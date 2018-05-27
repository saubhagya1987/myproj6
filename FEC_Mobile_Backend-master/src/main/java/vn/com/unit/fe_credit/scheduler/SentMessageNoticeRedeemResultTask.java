package vn.com.unit.fe_credit.scheduler;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.commons.lang3.StringUtils;
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
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.MgmRedeemTransService;
import vn.com.unit.fe_credit.service.PushNotificationService;
import vn.com.unit.webservice.PushNotification;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SentMessageNoticeRedeemResultTask extends QuartzJobBean implements ScheduleTask {

	@Autowired
	MgmRedeemTransService mgmRedeemTransService;

	@Autowired
	CustomerService customerService;

	@Autowired
	MessageService messageService;

	@Autowired
	PushNotificationService pushNotificationService;

	@Override
	public void doTask() {

		try {
			List<Map<String, Object>> datas = mgmRedeemTransService.getListPushMessageNotice();
			if (CollectionUtils.isNotEmpty(datas)) {
				for (Map<String, Object> item : datas) {
					try {

						SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm dd/MM/yyyy");
						SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");

						Date SUBMISSION_DATE = (Date) item.get("SUBMISSION_DATE");
						Date DATE_NEXT_PAYMENT = (Date) item.get("DATE_NEXT_PAYMENT");
						String CUSTOMER_ID = (String) item.get("CUSTOMER_ID");
						String ID_CARD_NUMBER = (String) item.get("ID_CARD_NUMBER");
						String TRANS_ID = (String) item.get("TRANS_ID"); // Mã số giao dịch
						String VALUE_DESCRIPTION = (String) item.get("VALUE_DESCRIPTION"); // Mã hợp đồng
						String NOTE = (String) item.get("NOTE"); // Ghi chú
						String PARTNER_TRANS_REF_ID = (String) item.get("PARTNER_TRANS_REF_ID"); // momo acc no
						int STATUS = (int) item.get("STATUS"); // Trạng thái
						int TRANX_TYPE = (int) item.get("TRANX_TYPE"); // 1. Momo, 2.Payment
						BigDecimal REDEEM_POINT = (BigDecimal) item.get("REDEEM_POINT");
						BigDecimal EXCHANGE_VALUE = (BigDecimal) item.get("EXCHANGE_VALUE");
						BigDecimal REMAINING_POINT = (BigDecimal) item.get("REMAINING_POINT");

						NumberFormat numberFormat = NumberFormat.getNumberInstance(new Locale("en"));

						Map<String, Object> mapVelocity = new HashMap<>();
						mapVelocity.put("SUBMISSION_DATE", SUBMISSION_DATE != null ? sdf1.format(SUBMISSION_DATE) : null);
						mapVelocity.put("DATE_NEXT_PAYMENT", DATE_NEXT_PAYMENT != null ? sdf2.format(DATE_NEXT_PAYMENT) : null);
						mapVelocity.put("CUSTOMER_ID", CUSTOMER_ID);
						mapVelocity.put("ID_CARD_NUMBER", ID_CARD_NUMBER);
						mapVelocity.put("TRANS_ID", TRANS_ID);
						mapVelocity.put("VALUE_DESCRIPTION", VALUE_DESCRIPTION);
						mapVelocity.put("NOTE", NOTE);
						mapVelocity.put("PARTNER_TRANS_REF_ID", PARTNER_TRANS_REF_ID);
						mapVelocity.put("STATUS", STATUS);
						mapVelocity.put("TRANX_TYPE", TRANX_TYPE);
						mapVelocity.put("REDEEM_POINT", numberFormat.format(REDEEM_POINT));
						mapVelocity.put("EXCHANGE_VALUE", numberFormat.format(EXCHANGE_VALUE));
						mapVelocity.put("REMAINING_POINT", numberFormat.format(REMAINING_POINT));
						StrSubstitutor strsub = new StrSubstitutor(mapVelocity);

						Customer customer = customerService.findByIdCardNumber(ID_CARD_NUMBER);

						String subject = null;
						StringBuffer content = new StringBuffer("");

						if (String.valueOf(STATUS).equals("3")) {
							// SUCCESS
							if (StringUtils.equals(customer.getDefaultLanguage(), "vi")) {
								if (TRANX_TYPE == 1) {
									subject = "Đổi điểm thành công qua Momo";
									content.append(
											"Bạn đã đổi ${REDEEM_POINT} điểm thành ${EXCHANGE_VALUE} VND thành công vào lúc ${SUBMISSION_DATE} qua tài khoản MoMo số ${PARTNER_TRANS_REF_ID}!");
									content.append(" Hiện tại bạn còn ${REMAINING_POINT} điểm khả dụng");
								} else if (TRANX_TYPE == 2) {
									subject = "Đổi điểm thành công cho kỳ thanh toán tiếp theo";
									content.append(
											"Bạn đã đổi ${REDEEM_POINT} điểm thành ${EXCHANGE_VALUE} VNĐ cho hợp đồng số ${VALUE_DESCRIPTION} vào kỳ thanh toán ${DATE_NEXT_PAYMENT} thành công.");
									content.append(" Thời gian quy đổi ${SUBMISSION_DATE}.");
									content.append(" Mã số giao dịch ${TRANS_ID}. Hiện tại bạn còn ${REMAINING_POINT} điểm khả dụng");
								}
							} else if (StringUtils.equals(customer.getDefaultLanguage(), "en")) {
								if (TRANX_TYPE == 1) {
									subject = "Successfully redeemed  through MoMo";
									content.append(
											"Successfully converted ${REDEEM_POINT} points into ${EXCHANGE_VALUE} VND for the payment on ${SUBMISSION_DATE}");
									content.append(" through MoMo account, no ${PARTNER_TRANS_REF_ID}");
									content.append(" Transaction date: ${SUBMISSION_DATE} Transaction ID${TRANS_ID}.");
									content.append(" Valid points: ${REMAINING_POINT}");

								} else if (TRANX_TYPE == 2) {
									subject = "Successfully redeemed for the next payment";
									content.append(
											" Successfully converted ${REDEEM_POINT} points into ${EXCHANGE_VALUE} VND for the payment on ${DATE_NEXT_PAYMENT}");
									content.append(
											" of the contract No. ${VALUE_DESCRIPTION} Transaction date: ${SUBMISSION_DATE} Transaction ID ${TRANS_ID}.");
									content.append(" Valid points: ${REMAINING_POINT}");
								}
							}
						} else if (String.valueOf(STATUS).equals("4")) {
							// FAIL
							if (StringUtils.equals(customer.getDefaultLanguage(), "vi")) {
								if (TRANX_TYPE == 1) {

									subject = "Đổi điểm thất bại qua Momo";
									content.append("Bạn đã đổi ${REDEEM_POINT} điểm thành ${EXCHANGE_VALUE} VNĐ thất bại");
									content.append(" vào lúc ${SUBMISSION_DATE} qua tài khoản Momo số ${PARTNER_TRANS_REF_ID}!");
									if (StringUtils.isNotBlank(NOTE)) {
										content.append(" Lý do: ${NOTE}");
									}
									content.append(" Mã số giao dịch ${TRANS_ID}.");
									content.append(
											" Bạn có thể dùng mã số giao dịch này để <a href='#new-message'>liên hệ với nhân viên tư vấn</a> để được hỗ trợ thêm.");
									content.append(" Hiện tại bạn còn ${REMAINING_POINT} điểm khả dụng");

								} else if (TRANX_TYPE == 2) {

									subject = "Đổi điểm thất bại cho kỳ thanh toán tiếp theo";
									content.append(
											"Bạn đã đổi ${REDEEM_POINT} điểm thành ${EXCHANGE_VALUE} VNĐ cho hợp đồng số ${VALUE_DESCRIPTION}");
									content.append(" vào kỳ thanh toán ${DATE_NEXT_PAYMENT} thất bại.");
									content.append(" Thời gian quy đổi ${SUBMISSION_DATE}.");
									if (StringUtils.isNotBlank(NOTE)) {
										content.append(" Lý do: ${NOTE} ");
									}
									content.append(" Mã số giao dịch ${TRANS_ID}.");
									content.append(
											" Bạn có thể dùng mã số giao dịch này để <a href='#new-message'>liên hệ với nhân viên tư vấn</a> để được hỗ trợ thêm.");
									content.append(" Hiện tại bạn còn ${REMAINING_POINT} điểm khả dụng");

								}
							} else if (StringUtils.equals(customer.getDefaultLanguage(), "en")) {
								if (TRANX_TYPE == 1) {
									subject = "Point conversion through MoMo failed.";

									content.append("Failed to convert ${REDEEM_POINT} points into ${EXCHANGE_VALUE} VND on ${SUBMISSION_DATE}");
									content.append(" through MoMo account no ${PARTNER_TRANS_REF_ID}.");
									if (StringUtils.isNotBlank(NOTE)) {
										content.append(" Reason: ${NOTE}.");
									}
									content.append(
											" Transaction ID ${TRANS_ID} You can use this transaction ID to contact our consultant for further support.");
									content.append(" Valid points: ${REMAINING_POINT} points.");
									content.append(" Please do it again or <a href='#new-message'>send us a message</a> for support.");

								} else if (TRANX_TYPE == 2) {
									subject = "Point conversion for the next payment failed.";
									content.append(
											"Failed to convert ${REDEEM_POINT} points into ${EXCHANGE_VALUE} VND for the payment on ${DATE_NEXT_PAYMENT}");
									content.append(" of the contract no ${VALUE_DESCRIPTION} Transaction date is ${SUBMISSION_DATE}.");
									if (StringUtils.isNotBlank(NOTE)) {
										content.append(" Reason: ${NOTE}.");
									}
									content.append(
											" Transaction ID ${TRANS_ID} You can use this transaction ID to contact our consultant for further support.");
									content.append(" Valid points: ${REMAINING_POINT} points.");
									content.append(" Please do it again or <a href='#new-message'>send us a message</a> for support.");
								}
							}
						}

						MessageDashBoard messageDashBoardL = new MessageDashBoard();
						messageDashBoardL.setCustomer(customer);
						messageDashBoardL.setFullName(customer.getFullName());
						messageDashBoardL.setCellphone(customer.getCellPhone());
						messageDashBoardL.setSubject(subject);
						messageDashBoardL.setContent(strsub.replace(content.toString()));
						messageDashBoardL.setStatus(MessageStatus.SENT.getIntValue());
						messageDashBoardL.setType(9);
						messageDashBoardL.setIsRead(0);
						messageDashBoardL.setIsMsgUser(0);
						messageDashBoardL.setCreatedDate(new Date());
						messageService.save(messageDashBoardL);

						String contentPush = messageDashBoardL.getContent();
						contentPush = Jsoup.parse(String.valueOf(contentPush)).text();

						try {
							pushNotificationService.pushNotification(String.valueOf(customer.getCustomerId()), contentPush,
									messageDashBoardL.getMessageID().toString(), true);
						} catch (Exception e) {
							e.printStackTrace();
						}

						messageDashBoardL.setParentMsgId(messageDashBoardL.getMessageID());
						messageService.save(messageDashBoardL);

					} catch (Exception e) {
						e.printStackTrace();
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
