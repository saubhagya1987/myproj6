package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.DocumentInfoDao;
import vn.com.unit.fe_credit.entity.DocumentInfo;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class DocumentInfoDaoImpl extends GenericDAOImpl<DocumentInfo, Long>
		implements DocumentInfoDao {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
}
