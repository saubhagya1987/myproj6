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
import vn.com.unit.fe_credit.dao.BannerActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.BannerActivityLog;
import vn.com.unit.fe_credit.service.BannerActivityLogService;

@Service("BannerActivityLogService")
@Transactional(readOnly = true)
public class BannerActivityLogServiceImpl implements BannerActivityLogService {
	@Autowired
	BannerActivityLogDao bannerActivityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public BannerActivityLogServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Transactional
	@Override
	public void saveBannerActivityLog(BannerActivityLog entity) {
		// TODO Auto-generated method stub
		bannerActivityLogDao.save(entity);
	}

	@Override
	public List<BannerActivityLog> bannerActivityLogs(long type) {
		// TODO Auto-generated method stub
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<BannerActivityLog> bannerActivityLogs = bannerActivityLogDao.search(search);
		return bannerActivityLogs;
	}

	@Override
	public List<BannerActivityLog> bannerActivityLogsCustomer(long type, long customerId) {
		// TODO Auto-generated method stub
		return bannerActivityLogDao.bannerActivityLogs(type, customerId);
	}

	@Override
	@Transactional
	public void saveBannerActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId) {

		BannerActivityLog banneractivityLog = new BannerActivityLog();
		banneractivityLog.setLogCode(systemConfig.getActivityLog().get(logCode).toUpperCase());
		banneractivityLog.setLogDate(new Date());
		banneractivityLog.setDescritption(msgSrc.getMessage(systemConfig.getActivityLog().get(logCode), null, locale));
		banneractivityLog.setRefeId(Long.parseLong(logCode.toString()));
		banneractivityLog.setType(Long.parseLong(type.toString()));
		banneractivityLog.setUserType(Long.parseLong(userType.toString()));

		if (accountId != null) {
			banneractivityLog.setUserId(Long.parseLong(accountId.toString()));
		}

		if (customerId != null) {
			banneractivityLog.setCustomerId(Long.parseLong(customerId));
		}

		bannerActivityLogDao.save(banneractivityLog);

	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		return bannerActivityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		return bannerActivityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
