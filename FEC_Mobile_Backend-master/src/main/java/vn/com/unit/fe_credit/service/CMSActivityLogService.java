package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.CMSActivityLog;

public interface CMSActivityLogService {
	void saveCMSActivityLog(CMSActivityLog entity);

	void saveCMSActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId);

	List<CMSActivityLog> cmsActivityLogs(long type);

	List<CMSActivityLog> cmsActivityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
}
