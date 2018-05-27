package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.CustomerActivityLog;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CustomerActivityLogDao extends GenericDAO<CustomerActivityLog, Long> {
	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
	List<CustomerActivityLog> CustomerActivityLogs(long type, long customerId);
}
