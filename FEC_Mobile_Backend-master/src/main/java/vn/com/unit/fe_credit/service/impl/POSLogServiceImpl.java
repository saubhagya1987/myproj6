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
import vn.com.unit.fe_credit.dao.POSLogDao;
import vn.com.unit.fe_credit.entity.POSLog;
import vn.com.unit.fe_credit.service.POSLogService;

import com.googlecode.genericdao.search.Search;

@Service("POSLogService")
@Transactional(readOnly = true)
public class POSLogServiceImpl implements POSLogService {
	@Autowired
	POSLogDao posLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public POSLogServiceImpl() {
		super();
	}

	@Transactional
	@Override
	public void savePOSLog(POSLog entity) {
		// TODO Auto-generated method stub
		posLogDao.save(entity);
	}

	@Override
	public List<POSLog> posLog(long type) {
		Search search = new Search(POSLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<POSLog> activityLogs = posLogDao.search(search);
		return activityLogs;
	}


	@Transactional
	@Override
	public void savePOSLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		POSLog activityLog = new POSLog();
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
			activityLog.setId(Long.parseLong(customerId));
		posLogDao.save(activityLog);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		return posLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		return posLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
