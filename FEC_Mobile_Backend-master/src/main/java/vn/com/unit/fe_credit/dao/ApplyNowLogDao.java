package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.ApplyNowLog;
import vn.com.unit.fe_credit.entity.BannerActivityLog;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface ApplyNowLogDao extends GenericDAO<ApplyNowLog, Long> {
	long sumChart(List<String> list, String FromDay, String ToDay,String userId);

	long countReport(Long refeId, String FromDay, String ToDay,String userId);
}
