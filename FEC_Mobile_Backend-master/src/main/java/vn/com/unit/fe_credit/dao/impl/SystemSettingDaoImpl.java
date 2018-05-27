package vn.com.unit.fe_credit.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.SystemSettingDao;
import vn.com.unit.fe_credit.entity.SystemSetting;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
@Repository
public class SystemSettingDaoImpl extends GenericDAOImpl<SystemSetting, Long> implements SystemSettingDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}
