package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.ContractMessagesDao;
import vn.com.unit.fe_credit.entity.ContractMessages;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class ContractMessagesDaoImpl extends GenericDAOImpl<ContractMessages, Long> implements ContractMessagesDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public List<ContractMessages> findByCustomerId(Long customerId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT m.contractMessagesId as contractMessagesId, m.subject as subject, m.datetime as datetime, m.contractNumber as contractNumber, m.status as status");
		sql.append(" FROM ContractMessages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId");
		
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("contractMessagesId",new LongType())
				.addScalar("subject",new StringType())
				.addScalar("datetime",new TimestampType())
				.addScalar("contractNumber",new TimestampType())
				.addScalar("status",new IntegerType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(ContractMessages.class));			
	
		return query.list();
	}
}
