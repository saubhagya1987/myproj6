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
import vn.com.unit.fe_credit.dao.HobbyActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.HobbyActivityLog;
import vn.com.unit.fe_credit.service.HobbyActivityLogService;

@Service("HobbyActivityLogService")
@Transactional(readOnly = true)
public class HobbyActivityLogServiceImpl implements HobbyActivityLogService {
	@Autowired
	HobbyActivityLogDao hobbyActivityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public HobbyActivityLogServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Transactional
	@Override
	public void saveHobbyActivityLog(HobbyActivityLog entity) {
		// TODO Auto-generated method stub
		hobbyActivityLogDao.save(entity);
	}

	@Override
	public List<HobbyActivityLog> hobbyActivityLogs(long type) {
		// TODO Auto-generated method stub
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<HobbyActivityLog> activityLogs = hobbyActivityLogDao.search(search);
		return activityLogs;
	}

	@Override
	public List<HobbyActivityLog> hobbyActivityLogsCustomer(long type, long customerId) {
		return hobbyActivityLogDao.hobbyActivityLogsCustomer(type, customerId);
	}

	@Transactional
	@Override
	public void saveHobbyActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		// TODO Auto-generated method stub
		HobbyActivityLog hobbyActivityLog = new HobbyActivityLog();
		hobbyActivityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
		hobbyActivityLog.setLogDate(new Date());
		hobbyActivityLog.setDescritption(msgSrc.getMessage(systemConfig
				.getActivityLog().get(logCode), null, locale));
		hobbyActivityLog.setRefeId(Long.parseLong(logCode.toString()));
		hobbyActivityLog.setType(Long.parseLong(type.toString()));
		hobbyActivityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null)
			hobbyActivityLog.setUserId(Long.parseLong(accountId.toString()));
		if (customerId != null)
			hobbyActivityLog.setCustomerId(Long.parseLong(customerId));
		hobbyActivityLogDao.save(hobbyActivityLog);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		return hobbyActivityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		return hobbyActivityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
