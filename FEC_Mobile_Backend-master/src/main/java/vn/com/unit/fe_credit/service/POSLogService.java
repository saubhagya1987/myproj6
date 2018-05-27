package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.POSLog;

public interface POSLogService {
	void savePOSLog(POSLog entity);

	void savePOSLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId);

	List<POSLog> posLog(long type);


	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);

}
