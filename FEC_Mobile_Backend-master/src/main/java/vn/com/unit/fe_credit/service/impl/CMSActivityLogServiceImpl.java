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
import vn.com.unit.fe_credit.dao.CMSActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.CMSActivityLog;
import vn.com.unit.fe_credit.service.CMSActivityLogService;

import com.googlecode.genericdao.search.Search;

@Service("CMSActivityLogService")
@Transactional(readOnly = true)
public class CMSActivityLogServiceImpl implements CMSActivityLogService {
	@Autowired
	CMSActivityLogDao cmsActivityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public CMSActivityLogServiceImpl() {
		super();
	}

	@Transactional
	@Override
	public void saveCMSActivityLog(CMSActivityLog entity) {
		// TODO Auto-generated method stub
		cmsActivityLogDao.save(entity);
	}

	@Override
	public List<CMSActivityLog> cmsActivityLogs(long type) {
		// TODO Auto-generated method stub
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<CMSActivityLog> cmsactivityLogs = cmsActivityLogDao.search(search);
		return cmsactivityLogs;
	}

	@Override
	public List<CMSActivityLog> cmsActivityLogsCustomer(long type, long customerId) {
		// TODO Auto-generated method stub
		return cmsActivityLogDao.cmsActivityLogs(type, customerId);
	}

	@Transactional
	@Override
	public void saveCMSActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		// TODO Auto-generated method stub
		CMSActivityLog cmsActivityLog = new CMSActivityLog();
		cmsActivityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
		cmsActivityLog.setLogDate(new Date());
		cmsActivityLog.setDescritption(msgSrc.getMessage(systemConfig
				.getActivityLog().get(logCode), null, locale));
		cmsActivityLog.setRefeId(Long.parseLong(logCode.toString()));
		cmsActivityLog.setType(Long.parseLong(type.toString()));
		cmsActivityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null)
			cmsActivityLog.setUserId(Long.parseLong(accountId.toString()));
		if (customerId != null)
			cmsActivityLog.setCustomerId(Long.parseLong(customerId));
		cmsActivityLogDao.save(cmsActivityLog);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return cmsActivityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return cmsActivityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
