package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.MasterdataDAO;
import vn.com.unit.fe_credit.entity.Masterdata;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class MasterdataDAOImpl extends GenericDAOImpl<Masterdata, Long>
		implements MasterdataDAO {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
