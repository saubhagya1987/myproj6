package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.CustomerAddressDao;
import vn.com.unit.fe_credit.entity.CustomerAddress;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CustomerAddressDaoImpl extends GenericDAOImpl<CustomerAddress, Long> implements CustomerAddressDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
