package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.BannerActivityLog;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface BannerActivityLogDao extends GenericDAO<BannerActivityLog, Long> {
	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
	List<BannerActivityLog> bannerActivityLogs(long type, long customerId);
}
