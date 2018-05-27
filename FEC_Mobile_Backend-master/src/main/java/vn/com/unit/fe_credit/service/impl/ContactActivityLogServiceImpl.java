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
import vn.com.unit.fe_credit.dao.ContactActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.ContactActivityLog;
import vn.com.unit.fe_credit.service.ContactActivityLogService;

@Service("ContactActivityLogService")
@Transactional(readOnly = true)
public class ContactActivityLogServiceImpl implements ContactActivityLogService {
	@Autowired
	ContactActivityLogDao activityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public ContactActivityLogServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Transactional
	@Override
	public void saveContactActivityLog(ContactActivityLog entity) {
		activityLogDao.save(entity);
	}

	@Override
	public List<ContactActivityLog> contactActivityLogs(long type) {
		// TODO Auto-generated method stub
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<ContactActivityLog> activityLogs = activityLogDao.search(search);
		return activityLogs;
	}

	@Override
	public List<ContactActivityLog> contactActivityLogsCustomer(long type, long customerId) {
		// TODO Auto-generated method stub
		return activityLogDao.contactActivityLogs(type, customerId);
	}

	@Transactional
	@Override
	public void saveContactActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		// TODO Auto-generated method stub
		ContactActivityLog activityLog = new ContactActivityLog();
		activityLog.setLogCode(systemConfig.getActivityLog().get(logCode));
		activityLog.setLogDate(new Date());
		activityLog.setDescritption(msgSrc.getMessage(systemConfig
				.getActivityLog().get(logCode), null, locale));
		activityLog.setRefeId(Long.parseLong(logCode.toString()));
		activityLog.setType(Long.parseLong(type.toString()));
		activityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null)
			activityLog.setUserId(Long.parseLong(accountId.toString()));
		if (customerId != null)
			activityLog.setCustomerId(Long.parseLong(customerId));
		activityLogDao.save(activityLog);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return activityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return activityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
