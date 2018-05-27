package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.ActivityLog;

public interface ActivityLogService {

	void saveActivityLog(ActivityLog entity);

	void saveActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId);

	List<ActivityLog> activityLogs(long type);

	List<ActivityLog> activityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay, String userId);

	long countReport(Long refeId, String FromDay, String ToDay, String userId);
}
