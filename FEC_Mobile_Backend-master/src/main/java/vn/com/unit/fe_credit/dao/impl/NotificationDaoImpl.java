package vn.com.unit.fe_credit.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.NotificationDao;
import vn.com.unit.fe_credit.entity.collection.Notification;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

@Repository
public class NotificationDaoImpl extends HibernateBaseDAO implements NotificationDao  {
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	@Transactional("txnManagerCollections")
	public void save(Notification notification) {
		try{
			System.out.println("Save Notification...................");
		    getSession().save(notification);
		    System.out.println("Save Notification................... DONE....");
		}catch(Exception e){
			System.out.println("Exception Occured.............");
			getSession().flush();
			getSession().close();
			e.printStackTrace();
		}
		
	}

}
