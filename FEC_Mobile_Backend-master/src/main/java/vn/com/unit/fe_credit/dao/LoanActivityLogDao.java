package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.LoanActivityLog;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface LoanActivityLogDao extends GenericDAO<LoanActivityLog, Long> {
	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
	List<LoanActivityLog> loanActivityLogs(long type, long customerId);
}
