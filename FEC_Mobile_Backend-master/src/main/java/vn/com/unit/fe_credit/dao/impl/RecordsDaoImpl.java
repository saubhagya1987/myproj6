package vn.com.unit.fe_credit.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.dao.RecordsDao;
import vn.com.unit.fe_credit.entity.collection.Record;
import vn.com.unit.fe_credit.entity.collection.RecordAttachment;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

@Repository
public class RecordsDaoImpl extends HibernateBaseDAO implements RecordsDao {
	
	@Override
	@Autowired
	
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	
	@Transactional("txnManagerCollections")	
	public List<Record> findAllRecord() {
		// TODO Auto-generated method stub
		List<Record> records = new ArrayList<Record>();
		Session session = null;
		String hql = "from Record";
		try{
			session = getSession();			
			Query query = session.createQuery(hql);	
			records = query.list(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return records;		
	}
	@Transactional("txnManagerCollections")
	public List<Object[]> findAll() {
		// TODO Auto-generated method stub
		List<Object[]> records = new ArrayList<Object[]>();
		Session session = null;		
		String hql = "select record.contractId,record.personContacted,record.contactMode,record.placeContacted,record.actionDate,record.personContacted,recordAttachment.attachment_type,recordAttachment.attachment_data,recordAttachment.id,record.checkIn from Record record  left join "
				+ "record.recordAttachment recordAttachment";				
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
		Session session = null;	
		try{
			session = getSession();	
			Query query = session.createSQLQuery("update " + tablename +" set status='Yes' where RECORD_ID IN("+ids+ ")")	;	
			query.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<Record> findAllByStatus() {
		// TODO Auto-generated method stub
		List<Record> records = new ArrayList<Record>();
		Session session = null;
		String hql = "from Record rec where rec.status = 'No'";		
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
	public boolean save(Record record){
		//getSession().beginTransaction();
		getSession().saveOrUpdate(record);
		//getSession().c
		return false;
	}

	@Override
	@Transactional("txnManagerCollections")
	public List<Record> findAllByStatusAndFC() {
		// TODO Auto-generated method stub
		List<Record> records = new ArrayList<Record>();
		Session session = null;
		String hql = "from Record rec where (rec.status != 'Yes' or rec.status is null) and (rec.isskiptracer != 'y' or rec.isskiptracer is null)";		
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
	public List<Record> findAllByStatusAndSF() {
		// TODO Auto-generated method stub
		List<Record> records = new ArrayList<Record>();
		Session session = null;
		String hql = "from Record rec where (rec.status != 'Yes' or rec.status is null) and rec.isskiptracer = 'y'";		
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
	public RecordDto getAttachmentId(int id) {
		String hql = "from RecordAttachment where id=:id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", id);
		RecordAttachment recordAttachment = (RecordAttachment) query.uniqueResult();
		RecordDto recordDto=new RecordDto();
		recordDto.setAttachmentData(recordAttachment.getAttachment_data());
		recordDto.setAttachmentType(recordAttachment.getAttachment_type());
		
		return recordDto;
	}
}
