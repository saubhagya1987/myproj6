package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.HobbyActivityLog;

public interface HobbyActivityLogService {
	void saveHobbyActivityLog(HobbyActivityLog entity);

	void saveHobbyActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId);

	List<HobbyActivityLog> hobbyActivityLogs(long type);

	List<HobbyActivityLog> hobbyActivityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
}
