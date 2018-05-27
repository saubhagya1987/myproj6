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
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.CustomerHobbyDao;
import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.NewCustomerHobby;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CustomerHobbyDaoImpl extends GenericDAOImpl<CustomerHobby, Long>
		implements CustomerHobbyDao {
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<CustomerHobby> findByCustomerId(Long customerId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ch.customerHobbyId as customerHobbyId, h.name as name, ch.checkedDate as checkedDate, ch.status as status, ch.hobbyId as hobbyId");
		sql.append(" FROM CustomerHobby ch ");
		sql.append(" JOIN Hobby h on h.hobbyId=ch.hobbyId");
		sql.append(" WHERE ch.customerId = :Id");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("customerHobbyId", new LongType())
				.addScalar("name", new StringType())
				.addScalar("checkedDate", new TimestampType())
				.addScalar("status", new IntegerType())
				.addScalar("hobbyId", new LongType());
		query.setParameter("Id", customerId);
		query.setResultTransformer(Transformers
				.aliasToBean(CustomerHobby.class));

		return query.list();
	}

	@Override
	public List<Hobby> findHobbyByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT h.hobbyId as hobbyId,h.code as code, h.name as name,h.description as description,h.status as status");
		sql.append(" FROM CustomerHobby ch ");
		sql.append(" JOIN Hobby h on h.hobbyId=ch.hobbyId");
		sql.append(" WHERE ch.customerId = :customerId");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("hobbyId", new LongType())
				.addScalar("code", new StringType())
				.addScalar("name", new StringType())
				.addScalar("description", new StringType())
				.addScalar("status", new StringType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(Hobby.class));
		return query.list();
	}

	@Override
	public void deleteByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		if (findByCustomerId(customerId).size() > 0) {
			sql.append("delete from customerHobby cu where cu.customerId="
					+ customerId);
			Query query = getSession().createSQLQuery(sql.toString());
			query.executeUpdate();
		}
	}

}
