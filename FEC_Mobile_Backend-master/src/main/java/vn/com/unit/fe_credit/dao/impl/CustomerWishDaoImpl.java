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

import vn.com.unit.fe_credit.dao.CustomerWishDao;
import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.Wish;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

@Repository
public class CustomerWishDaoImpl extends GenericDAOImpl<CustomerWish, Long>
		implements CustomerWishDao {
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<CustomerWish> findByCustomerId(Long customerId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ch.wishId as wishId,ch.customerWishId as customerWishId, w.TITLE as wishes, ch.addDate as addDate, w.CMS_CATEGORYID as category");
		sql.append(" FROM CustomerWish ch ");
		sql.append(" JOIN CMS w on w.CMSID=ch.wishId");
		sql.append(" WHERE ch.customerId = :customerId");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("customerWishId", new LongType())
				.addScalar("wishId", new LongType())
				.addScalar("wishes", new StringType())
				.addScalar("addDate", new TimestampType())
				.addScalar("category", new StringType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(CustomerWish.class));

		return query.list();
	}

	@Override
	public List<Wish> findWishByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT w.wishId as wishId,w.wishes as wishes,w.category as category");
		sql.append(" FROM CustomerWish ch ");
		sql.append(" JOIN Wish w on w.wishId=ch.wishId");
		sql.append(" WHERE ch.customerId = :customerId");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("wishId", new LongType())
				.addScalar("wishes", new StringType())
				.addScalar("category", new StringType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(Wish.class));

		return query.list();
	}

	@Override
	public void deleteCustomerWish(Long customerId, Long wishId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("delete from CUSTOMERWISH cu where cu.CUSTOMERID='"
				+ customerId + "' and cu.WISHID='" + wishId + "'");
		Query query = getSession().createSQLQuery(sql.toString());
		query.executeUpdate();
	}

}
