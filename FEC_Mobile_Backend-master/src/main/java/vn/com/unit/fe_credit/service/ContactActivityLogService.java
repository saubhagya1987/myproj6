package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.ContactActivityLog;

public interface ContactActivityLogService {
	void saveContactActivityLog(ContactActivityLog entity);

	void saveContactActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId);

	List<ContactActivityLog> contactActivityLogs(long type);

	List<ContactActivityLog> contactActivityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
}
