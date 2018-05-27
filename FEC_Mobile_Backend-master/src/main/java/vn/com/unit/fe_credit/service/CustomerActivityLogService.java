package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.CustomerActivityLog;

public interface CustomerActivityLogService {

	void saveCustomerActivityLog(CustomerActivityLog entity);

	void saveCustomerActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId);

	List<CustomerActivityLog> CustomerActivityLogs(long type);

	List<CustomerActivityLog> customerActivityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay, String userId);

	long countReport(Long refeId, String FromDay, String ToDay, String userId);

	void saveCustomerImportActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId,
			String descritption);

	void saveCustomerActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId,
			Customer customer);
}
