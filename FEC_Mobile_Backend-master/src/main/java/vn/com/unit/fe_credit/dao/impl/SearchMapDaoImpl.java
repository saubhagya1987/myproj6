package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.SearchMapDao;
import vn.com.unit.fe_credit.entity.SearchMap;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class SearchMapDaoImpl extends GenericDAOImpl<SearchMap, Long> implements SearchMapDao {


	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
