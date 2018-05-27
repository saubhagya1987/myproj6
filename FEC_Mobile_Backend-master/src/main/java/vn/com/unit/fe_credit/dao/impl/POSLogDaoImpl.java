package vn.com.unit.fe_credit.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.POSLogDao;
import vn.com.unit.fe_credit.entity.POSLog;
import vn.com.unit.fe_credit.utils.Utils;

@Repository
public class POSLogDaoImpl extends GenericDAOImpl<POSLog, Long>
		implements POSLogDao {
	@Autowired
	UserProfile userProfile;
	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public long sumChart(List<String> list, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null, date2 = null;
		try {
			date1 = dateFormat.parse(FromDay);
			date2 = dateFormat.parse(ToDay);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from POSLog ac ");
		String id = null;
		hql.append(" where ac.logDate>=:FromDay");
		hql.append(" and ac.logDate<=:ToDay");
		if (userId != null)
			hql.append(" and ac.userId=" + userId + " or ac.userId is null");
		for (String string : list) {
			if (id == null)
				hql.append(" and ac.refeId=" + string);
			else
				hql.append(" or ac.refeId=" + string);
			id = string;
		}

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("FromDay", date1);
		query.setParameter("ToDay", date2);
		query.uniqueResult().toString();

		Integer a = 0;
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}

		return a;
	}

	@Override
	public long countReport(Long refeId, String FromDay, String ToDay,
			String userId) {
		// TODO Auto-generated method stub

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date1 = null, date2 = null;
		try {
			date1 = dateFormat.parse(FromDay);
			date2 = dateFormat.parse(ToDay);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuilder hql = new StringBuilder();
		hql.append("select count(*) from POSLog ac");
		hql.append(" where ac.refeId=" + refeId.toString());
		if (systemConfig.MOBILE_LOGIN.toString().equals(String.valueOf(refeId))
				|| systemConfig.SEND_MESS_CUSTOMER.toString().equals(
						String.valueOf(refeId))) {
		} else {
			if (userId != null)
				hql.append(" and ac.userId=" + userId);
		}
		hql.append(" and ac.logDate>=:FromDay");
		hql.append(" and ac.logDate<=:ToDay");
		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("FromDay", Utils.setTimeToZero(date1));
		query.setParameter("ToDay", Utils.setTimeToMax(date2));
		query.uniqueResult().toString();
		Integer a = 0;
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}

		return a;
	}

}
