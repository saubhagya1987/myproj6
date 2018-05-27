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
import vn.com.unit.fe_credit.dao.ApplyNowLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.ApplyNowLog;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ApplyNowLogService;

import com.googlecode.genericdao.search.Search;

@Service("ApplyNowLogService")
@Transactional(readOnly = true)
public class ApplyNowLogServiceImpl implements ApplyNowLogService {
	@Autowired
	ApplyNowLogDao applyNowLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public ApplyNowLogServiceImpl() {
		super();
	}

	@Transactional
	@Override
	public void saveApplyNowLog(ApplyNowLog entity) {
		// TODO Auto-generated method stub
		applyNowLogDao.save(entity);
	}

	@Override
	public List<ApplyNowLog> applyNowLog(long type) {
		Search search = new Search(ApplyNowLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<ApplyNowLog> applyNowLog = applyNowLogDao.search(search);
		return applyNowLog;
	}


	@Transactional
	@Override
	public void saveApplyNowLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId) {
		ApplyNowLog activityLog = new ApplyNowLog();
		activityLog.setLogCode(systemConfig.getActivityLog().get(logCode));
		activityLog.setLogDate(new Date());
		activityLog.setDescritption(msgSrc.getMessage(systemConfig.getActivityLog().get(logCode), null, locale));
		activityLog.setRefeId(Long.parseLong(logCode.toString()));
		activityLog.setType(Long.parseLong(type.toString()));
		activityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null)
			activityLog.setUserId(Long.parseLong(accountId.toString()));
		if (customerId != null)
			activityLog.setId(Long.parseLong(customerId));
		applyNowLogDao.save(activityLog);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		return applyNowLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		return applyNowLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
