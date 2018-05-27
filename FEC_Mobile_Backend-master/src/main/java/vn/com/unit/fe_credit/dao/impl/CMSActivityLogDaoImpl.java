package vn.com.unit.fe_credit.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.CMSActivityLogDao;
import vn.com.unit.fe_credit.entity.CMSActivityLog;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CMSActivityLogDaoImpl extends GenericDAOImpl<CMSActivityLog, Long>
		implements CMSActivityLogDao {
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
		hql.append("select count(*) from CMSActivityLog ac ");
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
		hql.append("select count(*) from CMSActivityLog ac");
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

	@Override
	public List<CMSActivityLog> cmsActivityLogs(long type, long customerId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("select ac.activityId as activityId ,ac.logCode as logCode ,ac.logDate as logDate,ac.descritption as descritption,ac.userType as userType,ac.type as type,ac.refeId as refeId,ac.userId as userId,ac.customerId  as customerId from CMSActivityLog ac where ac.customerId="
				+ customerId
				+ " and ac.userId="
				+ userProfile.getAccount().getId()
				+ " or ac.userId is null and ac.customerId="
				+ customerId
				+ " order by ac.activityId desc ");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("activityId", new LongType())
				.addScalar("logCode", new StringType())
				.addScalar("logDate", new TimestampType())
				.addScalar("descritption", new StringType())
				.addScalar("userType", new LongType())
				.addScalar("type", new LongType())
				.addScalar("refeId", new LongType())
				.addScalar("userId", new LongType())
				.addScalar("customerId", new LongType());
		query.setResultTransformer(Transformers.aliasToBean(CMSActivityLog.class));
		return query.list();
	}
}
