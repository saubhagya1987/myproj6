package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.entity.BannerActivityLog;

public interface BannerActivityLogService {

	void saveBannerActivityLog(BannerActivityLog entity);

	void saveBannerActivityLog(Integer logCode, Integer type, Locale locale, Integer userType, String customerId, String accountId);

	List<BannerActivityLog> bannerActivityLogs(long type);

	List<BannerActivityLog> bannerActivityLogsCustomer(long type, long customerId);

	long sumChart(List<String> list, String FromDay, String ToDay, String userId);

	long countReport(Long refeId, String FromDay, String ToDay, String userId);
}
