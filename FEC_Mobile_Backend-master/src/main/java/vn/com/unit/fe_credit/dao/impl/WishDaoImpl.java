package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.WishDao;
import vn.com.unit.fe_credit.entity.Wish;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class WishDaoImpl extends GenericDAOImpl<Wish, Long> implements WishDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
