package vn.com.unit.fe_credit.service;

public interface PushNotificationService {

	int pushNotification(String accountID, String message, String messRootID, boolean isParseTextHtml) throws Exception;

	/**
	 * @author CongDT
	 */
	int sendBroadcastNotification(String msg, long cmsId, String... deviceIds) throws Exception;

}
