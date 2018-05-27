package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.CustomerCMSReadDAO;
import vn.com.unit.fe_credit.entity.CustomerCMSRead;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CustomerCMSReadDAOImpl extends GenericDAOImpl<CustomerCMSRead, Long>
		implements CustomerCMSReadDAO {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
