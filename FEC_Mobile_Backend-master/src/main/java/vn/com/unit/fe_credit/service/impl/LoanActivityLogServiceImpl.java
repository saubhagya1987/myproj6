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
import vn.com.unit.fe_credit.dao.LoanActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.LoanActivityLog;
import vn.com.unit.fe_credit.service.LoanActivityLogService;

@Service("LoanActivityLogService")
@Transactional(readOnly = true)
public class LoanActivityLogServiceImpl implements LoanActivityLogService {
	@Autowired
	LoanActivityLogDao loanActivityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public LoanActivityLogServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Transactional
	@Override
	public void saveLoanActivityLog(LoanActivityLog entity) {
		// TODO Auto-generated method stub
		loanActivityLogDao.save(entity);
	}

	@Override
	public List<LoanActivityLog> loanActivityLogs(long type) {
		// TODO Auto-generated method stub
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<LoanActivityLog> activityLogs = loanActivityLogDao.search(search);
		return activityLogs;
	}

	@Override
	public List<LoanActivityLog> loanActivityLogsCustomer(long type, long customerId) {
		// TODO Auto-generated method stub
		return loanActivityLogDao.loanActivityLogs(type, customerId);
	}

	@Transactional
	@Override
	public void saveLoanActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		// TODO Auto-generated method stub
		LoanActivityLog activityLog = new LoanActivityLog();
		activityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
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
		loanActivityLogDao.save(activityLog);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return loanActivityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		return loanActivityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
