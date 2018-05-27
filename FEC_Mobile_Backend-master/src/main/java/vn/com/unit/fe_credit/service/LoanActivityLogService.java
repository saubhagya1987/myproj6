package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.LoanActivityLog;

public interface LoanActivityLogService {
	void saveLoanActivityLog(LoanActivityLog entity);

	void saveLoanActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId);

	List<LoanActivityLog> loanActivityLogs(long type);

	List<LoanActivityLog> loanActivityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
}
