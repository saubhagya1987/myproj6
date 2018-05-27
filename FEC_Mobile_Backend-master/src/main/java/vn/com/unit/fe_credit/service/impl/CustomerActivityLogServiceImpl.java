package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.CustomerActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.CustomerActivityLog;
import vn.com.unit.fe_credit.service.CustomerActivityLogService;

@Service("CustomerActivityLogService")
@Transactional(readOnly = true)
public class CustomerActivityLogServiceImpl implements CustomerActivityLogService {
	@Autowired
	CustomerActivityLogDao customerActivityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public CustomerActivityLogServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Transactional
	@Override
	public void saveCustomerActivityLog(CustomerActivityLog entity) {
		// TODO Auto-generated method stub
		customerActivityLogDao.save(entity);
	}

	@Override
	public List<CustomerActivityLog> CustomerActivityLogs(long type) {
		// TODO Auto-generated method stub
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<CustomerActivityLog> activityLogs = customerActivityLogDao.search(search);
		return activityLogs;
	}

	@Override
	public List<CustomerActivityLog> customerActivityLogsCustomer(long type, long customerId) {
		// TODO Auto-generated method stub
		return customerActivityLogDao.CustomerActivityLogs(type, customerId);
	}

	@Transactional
	@Override
	public void saveCustomerActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId,
			Customer customer) {

		CustomerActivityLog customerActivityLog = new CustomerActivityLog();
		customerActivityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
		customerActivityLog.setLogDate(new Date());
		customerActivityLog.setDescritption(msgSrc.getMessage(systemConfig.getActivityLog().get(logCode), null, locale));
		customerActivityLog.setRefeId(Long.parseLong(logCode.toString()));
		customerActivityLog.setType(Long.parseLong(type.toString()));
		customerActivityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null) {
			customerActivityLog.setUserId(Long.parseLong(accountId.toString()));
		}
		if (customerId != null) {
			customerActivityLog.setCustomerId(Long.parseLong(customerId));
		}
		customerActivityLog.setCodeMachine(customer.getCodeMachine());
		customerActivityLogDao.save(customerActivityLog);

	}

	@Transactional
	@Override
	public void saveCustomerActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId) {
		CustomerActivityLog customerActivityLog = new CustomerActivityLog();
		customerActivityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
		customerActivityLog.setLogDate(new Date());
		customerActivityLog.setDescritption(msgSrc.getMessage(systemConfig.getActivityLog().get(logCode), null, locale));
		customerActivityLog.setRefeId(Long.parseLong(logCode.toString()));
		customerActivityLog.setType(Long.parseLong(type.toString()));
		customerActivityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null) {
			customerActivityLog.setUserId(Long.parseLong(accountId.toString()));
		}
		if (customerId != null) {
			customerActivityLog.setCustomerId(Long.parseLong(customerId));
		}
		customerActivityLogDao.save(customerActivityLog);
	}
	
	
	@Transactional
	@Override
	public void saveCustomerImportActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId,String descritption) {
		// TODO Auto-generated method stub
		CustomerActivityLog customerActivityLog = new CustomerActivityLog();
		customerActivityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
		customerActivityLog.setLogDate(new Date());
		customerActivityLog.setDescritption(descritption);
		customerActivityLog.setRefeId(Long.parseLong(logCode.toString()));
		customerActivityLog.setType(Long.parseLong(type.toString()));
		customerActivityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null)
			customerActivityLog.setUserId(Long.parseLong(accountId.toString()));
		if (customerId != null)
			customerActivityLog.setCustomerId(Long.parseLong(customerId));
		customerActivityLogDao.save(customerActivityLog);
	}
	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return customerActivityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return customerActivityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
