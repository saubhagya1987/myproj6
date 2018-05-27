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
import vn.com.unit.fe_credit.dao.MessageActivityLogDao;
import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.MessageActivityLog;
import vn.com.unit.fe_credit.service.MessageActivityLogService;

import com.googlecode.genericdao.search.Search;

@Service("MessageActivityLogService")
@Transactional(readOnly = true)
public class MessageActivityLogServiceImpl implements MessageActivityLogService {
	@Autowired
	MessageActivityLogDao messageActivityLogDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	UserProfile userProfile;
	@Autowired
	MessageSource msgSrc;

	public MessageActivityLogServiceImpl() {
		// TODO Auto-generated constructor stub
		super();
	}

	@Transactional
	@Override
	public void saveMessageActivityLog(MessageActivityLog entity) {
		messageActivityLogDao.save(entity);
	}

	@Override
	public List<MessageActivityLog> messageActivityLogs(long type) {
		Search search = new Search(ActivityLog.class);
		search.addFilterEqual("type", type);
		search.addFilterEqual("userId", userProfile.getAccount().getId());
		search.addSortDesc("activityId");
		List<MessageActivityLog> activityLogs = messageActivityLogDao.search(search);
		return activityLogs;
	}

	@Override
	public List<MessageActivityLog> messageActivityLogsCustomer(long type, long Id) {
		return messageActivityLogDao.messageActivityLogsCustomer(type, Id);
	}

	@Transactional
	@Override
	public void saveMessageActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId) {
		MessageActivityLog activityLog = new MessageActivityLog();
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
		messageActivityLogDao.save(activityLog);
	}
	
	
	@Transactional
	@Override
	public void saveMessageImportActivityLog(Integer logCode, Integer type, Locale locale,
			Integer userType, String customerId, String accountId,String descritption) {
		MessageActivityLog activityLog = new MessageActivityLog();
		activityLog.setLogCode(systemConfig.getActivityLog().get(logCode));
		activityLog.setLogDate(new Date());
		activityLog.setDescritption(descritption);
		activityLog.setRefeId(Long.parseLong(logCode.toString()));
		activityLog.setType(Long.parseLong(type.toString()));
		activityLog.setUserType(Long.parseLong(userType.toString()));
		if (accountId != null)
			activityLog.setUserId(Long.parseLong(accountId.toString()));
		if (customerId != null)
			activityLog.setId(Long.parseLong(customerId));
		messageActivityLogDao.save(activityLog);
	}
	
	
	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		return messageActivityLogDao.sumChart(list, FromDay, ToDay, userId);
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		return messageActivityLogDao.countReport(refeId, FromDay, ToDay, userId);
	}
}
