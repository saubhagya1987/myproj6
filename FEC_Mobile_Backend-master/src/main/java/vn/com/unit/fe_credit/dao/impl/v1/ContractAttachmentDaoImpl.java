package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

import vn.com.unit.fe_credit.dao.v1.ContractAttachmentDao;
import vn.com.unit.fe_credit.entity.collection.ContractAttachment;

@Repository
public class ContractAttachmentDaoImpl extends HibernateBaseDAO implements ContractAttachmentDao {
	
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractAttachment> getAttachmentsByContractId(String contractId) {
		
		String hqlQuery = "FROM ContractAttachment where contractId=:contractId";
		Query hqlQueryObj = getSession().createQuery(hqlQuery);
		hqlQueryObj.setParameter("contractId", contractId);
		return hqlQueryObj.list();
	}

	@Override
	public ContractAttachment getAttachmentById(Integer attachmentId) {
		
		String hqlQuery = "FROM ContractAttachment where id=:attachmentId";
		Query hqlQueryObj = getSession().createQuery(hqlQuery);
		hqlQueryObj.setParameter("attachmentId", attachmentId);
		
		return (ContractAttachment)hqlQueryObj.uniqueResult();
	}
	
	
	@Override
	@Transactional("txnManagerCollections")
	public void saveAttachment(ContractAttachment contractAttachment) {
		
		getSession().save(contractAttachment);
	}

	@Override
	@Transactional("txnManagerCollections")
	public void deleteAttachments() {
		
		getSession().createQuery("delete from ContractAttachment").executeUpdate();
	}
}
