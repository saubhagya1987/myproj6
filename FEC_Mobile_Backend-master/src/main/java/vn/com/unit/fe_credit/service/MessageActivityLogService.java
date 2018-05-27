package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.MessageActivityLog;

public interface MessageActivityLogService {
	void saveMessageActivityLog(MessageActivityLog entity);

	void saveMessageActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId);

	List<MessageActivityLog> messageActivityLogs(long type);

	List<MessageActivityLog> messageActivityLogsCustomer(long type, long Id);

	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);

	void saveMessageImportActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId,
			String descritption);
}
