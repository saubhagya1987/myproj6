package vn.com.unit.fe_credit.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.CheckInDao;
import vn.com.unit.fe_credit.entity.collection.CheckIn;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

@Repository
public class CheckInDaoImpl extends HibernateBaseDAO implements CheckInDao {
	
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<CheckIn> findAll() {
		// TODO Auto-generated method stub
		List<CheckIn> records = new ArrayList<CheckIn>();
		Session session = null;
		String hql = "from CheckIn ci where ci.status != 'Yes' or ci.status is null";
		try{
			session = getSession();
			Query query = session.createQuery(hql);		
			records = query.list(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return records;			
	}
	
	@Override
	@Transactional("txnManagerCollections")
	public void updateStatus(String tablename, String ids) {
		// TODO Auto-generated method stub
		String hql = "from ";		
		Query query = getSession().createSQLQuery("update " + tablename +" set status='Yes' where ID IN("+ids+ ")")	;	
		 query.executeUpdate();
	}
	
	@Override
	@Transactional("txnManagerCollections")
	public boolean save(CheckIn record){
		getSession().saveOrUpdate(record);
		return false;
	}

	@Override
	@Transactional("txnManagerCollections")
	public List<Object[]> findByAppIDAndCreationDate() {
		// TODO Auto-generated method stub
		List<Object[]> obj = new ArrayList<Object[]>();
		Session session = null;
		String hql = "SELECT APP_ID, TRUNC(CREATION_DATE),COUNT(*) count FROM CHECK_IN GROUP BY APP_ID, TRUNC(CREATION_DATE)";		
		try{
			session = getSession();
			Query query = session.createSQLQuery(hql);		
			obj = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	@Transactional("txnManagerCollections")
	public Object findFcCheckInDaily(String date) {
		// TODO Auto-generated method stub	
		Object obj = new Object();
		Session session = null;
		String hql = "SELECT COUNT(*) count FROM (select distinct FC_ID from CHECK_IN where CHECKIN_TIME >=  TO_DATE(?, 'DD-MM-YYYY')  and CHECKIN_TIME < TO_DATE(?, 'DD-MM-YYYY') + 1)";		
		try{
			session = getSession();
			Query query = session.createSQLQuery(hql).addScalar("count", LongType.INSTANCE);
			query.setParameter(0, date);
			query.setParameter(1, date);
			obj = query.uniqueResult();
			System.out.println("findFcCheckInDaily  :::  ");
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	@Transactional("txnManagerCollections")
	public List<Object[]> findAppCheckInDaily(String date) {
		// TODO Auto-generated method stub
		List<Object[]> obj = new ArrayList<Object[]>();
		Session session = null;
		String hql = "SELECT APP_ID appid,COUNT(*) count FROM CHECK_IN where CHECKIN_TIME >= TO_DATE(?, 'DD-MM-YYYY')  and CHECKIN_TIME < TO_DATE(?, 'DD-MM-YYYY') + 1 GROUP BY APP_ID";		
		try{
			session = getSession();
			Query query = session.createSQLQuery(hql).addScalar("appid", StringType.INSTANCE).addScalar("count", LongType.INSTANCE);
			query.setParameter(0, date);
			query.setParameter(1, date);
			obj = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}
	
	@Override
	@Transactional("txnManagerCollections")
	public Object findFcCheckInMonthly() {
		// TODO Auto-generated method stub
		Object obj = new Object();
		Session session = null;
		String hql = "SELECT COUNT(*) count FROM (select distinct FC_ID from CHECK_IN where CHECKIN_TIME BETWEEN trunc(sysdate, 'mm') AND SYSDATE)";		
		try{
			session = getSession();
			Query query = session.createSQLQuery(hql).addScalar("count", LongType.INSTANCE);		
			obj = query.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}

	@Override
	@Transactional("txnManagerCollections")
	public List<Object[]> findAppCheckInMonthly() {
		// TODO Auto-generated method stub
		List<Object[]> obj = new ArrayList<Object[]>();
		Session session = null;
		String hql = "SELECT APP_ID appid,COUNT(*) count FROM CHECK_IN where CHECKIN_TIME BETWEEN trunc(sysdate, 'mm') AND SYSDATE GROUP BY APP_ID";		
		try{
			session = getSession();
			Query query = session.createSQLQuery(hql).addScalar("appid", StringType.INSTANCE).addScalar("count", LongType.INSTANCE);		
			obj = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return obj;
	}	

}
