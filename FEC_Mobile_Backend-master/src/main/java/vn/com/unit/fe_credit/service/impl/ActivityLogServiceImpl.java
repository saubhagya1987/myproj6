package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.ActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.service.ActivityLogService;

import com.googlecode.genericdao.search.Search;

@Service("ActivityLogService")
@Transactional(readOnly = true)
public class ActivityLogServiceImpl implements ActivityLogService {
	@Autowired
	ActivityLogDao activityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public ActivityLogServiceImpl() {
		super();
	}

	@Transactional
	@Override
	public void saveActivityLog(ActivityLog entity) {
		// TODO Auto-generated method stub
		activityLogDao.save(entity);
	}

	@Override
	public List<ActivityLog> activityLogs(long type) {
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<ActivityLog> activityLogs = activityLogDao.search(search);
		return activityLogs;
	}

	@Override
	public List<ActivityLog> activityLogsCustomer(long type, long customerId) {
		return activityLogDao.activityLogsCustomer(type, customerId);
	}

	@Transactional
	@Override
	public void saveActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		ActivityLog activityLog = new ActivityLog();
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
		return activityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		return activityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
