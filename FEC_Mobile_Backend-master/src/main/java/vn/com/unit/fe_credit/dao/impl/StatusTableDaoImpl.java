package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.StatusTableDao;
import vn.com.unit.fe_credit.entity.StatusTable;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;


@Repository
public class StatusTableDaoImpl extends GenericDAOImpl<StatusTable, Long> implements StatusTableDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
