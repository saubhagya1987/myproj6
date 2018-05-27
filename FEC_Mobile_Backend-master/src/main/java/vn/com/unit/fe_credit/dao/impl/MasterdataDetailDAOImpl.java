package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.MasterdataDetailDAO;
import vn.com.unit.fe_credit.entity.MasterdataDetal;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class MasterdataDetailDAOImpl extends GenericDAOImpl<MasterdataDetal, Long>
		implements MasterdataDetailDAO {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public void deleteMasterdataID(Long masterdataid) {
		String hql = " " + "delete from MASTERDATADETAIL where MASTERDATAID  =:masterdataid" + "  ";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("masterdataid", masterdataid);
		query.executeUpdate();
	}

}
