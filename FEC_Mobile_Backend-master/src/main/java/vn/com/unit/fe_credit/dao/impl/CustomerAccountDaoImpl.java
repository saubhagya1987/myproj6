package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.CustomerAccountDao;
import vn.com.unit.fe_credit.entity.CustomerAccount;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CustomerAccountDaoImpl extends GenericDAOImpl<CustomerAccount, Long> implements CustomerAccountDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
