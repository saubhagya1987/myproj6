package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.UserAttemptsDao;
import vn.com.unit.fe_credit.entity.UserAttempts;

@Repository
public class UserAttemptsDaoImpl extends GenericDAOImpl<UserAttempts, Long> implements UserAttemptsDao {

	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Integer countAttempts(String ipAddress) throws Exception {

		StringBuilder hql = new StringBuilder();
		hql.append(" select ATTEMPTS" + "	from USER_ATTEMPTS" + "	where IP_ADDRESS = :ipAddress");

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("ipAddress", ipAddress);
		Integer count = (Integer) query.uniqueResult();
		return count;

	}

}
