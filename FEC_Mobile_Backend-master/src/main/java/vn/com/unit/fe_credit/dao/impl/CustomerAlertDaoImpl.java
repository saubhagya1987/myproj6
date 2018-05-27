package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.CustomerAlertDao;
import vn.com.unit.fe_credit.entity.CustomerAlert;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CustomerAlertDaoImpl extends GenericDAOImpl<CustomerAlert, Long> implements CustomerAlertDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
