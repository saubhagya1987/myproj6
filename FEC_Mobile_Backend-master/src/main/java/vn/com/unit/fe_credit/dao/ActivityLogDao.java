package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface ActivityLogDao extends GenericDAO<ActivityLog, Long> {
	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
	List<ActivityLog> activityLogsCustomer(long type, long customerId);
}
