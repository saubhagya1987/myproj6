package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.CMSActivityLog;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CMSActivityLogDao extends GenericDAO<CMSActivityLog, Long> {
	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
	List<CMSActivityLog> cmsActivityLogs(long type, long customerId);
}
