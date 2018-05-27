package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.v1.RepossessionAttachmentDao;
import vn.com.unit.fe_credit.entity.collection.RepossessionAttachment;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

@Repository
public class RepossessionAttachmentDaoImpl extends HibernateBaseDAO implements RepossessionAttachmentDao {
	private static final Logger LOG = LoggerFactory.getLogger(RepossessionAttachmentDaoImpl.class);
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepossessionAttachment> getAttachmentsByContractId(String contractId) {
		LOG.info("Getting Attachment List by contract id");
		String hqlQuery = "FROM RepossessionAttachment where contractId=:contractId";
		Query hqlQueryObj = getSession().createQuery(hqlQuery);
		hqlQueryObj.setParameter("contractId", contractId);
		return hqlQueryObj.list();
	}

	@Override
	public RepossessionAttachment getAttachmentById(Integer attachmentId) {
		LOG.info("Getting Attachment by attachment id");
		String hqlQuery = "FROM RepossessionAttachment where id=:attachmentId";
		Query hqlQueryObj = getSession().createQuery(hqlQuery);
		hqlQueryObj.setParameter("attachmentId", attachmentId);
		
		return (RepossessionAttachment)hqlQueryObj.uniqueResult();
	}
	
	
	@Override
	@Transactional("txnManagerCollections")
	public void saveAttachment(RepossessionAttachment repossessionAttachment) {
		LOG.info("saving Repossession Attachment");
		getSession().save(repossessionAttachment);
	}

	@Override
	@Transactional("txnManagerCollections")
	public void deleteAttachments() {
		LOG.info("deleting Repossession Attachment");
		getSession().createQuery("delete from RepossessionAttachment").executeUpdate();
	}


}
