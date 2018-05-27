package vn.com.unit.fe_credit.dao.impl;

import static vn.com.unit.fe_credit.config.SystemConfig.DRIVERCLASS;
import static vn.com.unit.fe_credit.config.SystemConfig.JDBCURL;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_KH;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.bean.MgmSuggestedContactsBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MgmSuggestedContactsDao;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.entity.MgmSuggestedContactsPK;

@Repository
public class MgmSuggestedContactsDaoImpl extends GenericDAOImpl<MgmSuggestedContacts, MgmSuggestedContactsPK> implements MgmSuggestedContactsDao {

	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<MgmSuggestedContacts> findByCustomerId(Long id) {
		//
		Search search = new Search(MgmSuggestedContacts.class);
		if (id != null) {
			search.addFilterEqual("customer.customerId", id);
		}
		return this.search(search);
	}

	public MgmSuggestedContactsBean search(MgmSuggestedContactsBean bean, Long customerId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT sc.VALIDATED_DATE as validatedDate, sc.CONTACT_NAME as contactName, sc.CONTACT_PHONE as contactPhone, "
				+ "sc.NATIONAL_ID as nationalId, sc.SUBMITED_DATE as submitedDate, sc.STATUS as status, sc.INVITE_METHOD as inviteMethod"
				+ " FROM MGM_SUGGESTED_CONTACTS sc");
		sql.append(" WHERE 1=1 ");

		if (!String.valueOf(customerId).isEmpty()) {
			sql.append(" AND sc.CUSTOMER_ID = :customerId");
		}

		sql.append(" ORDER BY SC.SUBMITED_DATE DESC");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("contactName", new StringType())
				.addScalar("contactPhone", new StringType()).addScalar("nationalId", new StringType()).addScalar("submitedDate", new TimestampType())
				.addScalar("validatedDate", new TimestampType()).addScalar("status", new IntegerType()).addScalar("inviteMethod", new IntegerType());
		query.setResultTransformer(Transformers.aliasToBean(MgmSuggestedContacts.class));

		if (!String.valueOf(customerId).isEmpty()) {
			query.setParameter("customerId", customerId);
		}
		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = query.list().size();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}
		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}

	@Override
	public MgmSuggestedContactsBean searchListFriendsByCustomerId(MgmSuggestedContactsBean bean, long customerId) throws Exception {
		Session sessionLocal = getSession();
		Session session = null;
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
			// Set connection timeout. Make sure you set this correctly as per your need
			DriverManager.setLoginTimeout(5);

			// get Connection
			Connection conn = DriverManager.getConnection(systemConfig.getConfig(JDBCURL), systemConfig.getConfig(USER_KH),
					systemConfig.getConfig(PASSWORD_KH));
			if (conn == null) {
				throw new Exception("Error connection!");
			}

			// get Session
			SessionBuilder sb = getSessionFactory().withOptions();
			session = sb.connection(conn).openSession();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// t1 - local - get id_card_number
		StringBuilder sqlLocal = new StringBuilder();

		sqlLocal.append("SELECT IDCARDNUMBER as idCardNumber FROM CUSTOMER");
		sqlLocal.append(" WHERE 1=1");
		sqlLocal.append(" AND CUSTOMERID = :customerId");

		Query queryLocal = sessionLocal.createSQLQuery(sqlLocal.toString()).addScalar("idCardNumber", new StringType());
		queryLocal.setParameter("customerId", customerId);
		String idCardNumberLocal = (String) queryLocal.uniqueResult();
		if (StringUtils.isBlank(idCardNumberLocal)) {
			throw new Exception("ID card number is blank");
		}

		// t2 - mobile
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT msc.CONTACT_NAME as contactName, msc.CONTACT_PHONE as contactPhone, msc.NATIONAL_ID as nationalId, msc.SUBMITED_DATE as submitedDate, "
						+ "msc.VALIDATED_DATE as validatedDate, msc.STATUS as status, msc.INVITE_METHOD as inviteMethod"
						+ " FROM MOBILE_CUSTOMER mc JOIN MGM_SUGGESTED_CONTACTS msc ON mc.CUSTOMER_ID = msc.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		sql.append(" AND mc.ID_CARD_NUMBER = :idCardNumberLocal");
		sql.append(String.format(" AND msc.STATUS not in (%d,%d)", SystemConfig.MGM_SUGGEST_CONTACT_STATUS_DUPLICATED,
				SystemConfig.MGM_SUGGEST_CONTACT_STATUS_EXIST_CUSTOMER));
		sql.append(" ORDER BY msc.SUBMITED_DATE DESC, rownum ");

		SQLQuery query = session.createSQLQuery(sql.toString());
		query.addScalar("contactName", new StringType());
		query.addScalar("contactPhone", new StringType());
		query.addScalar("nationalId", new StringType());
		query.addScalar("submitedDate", new TimestampType());
		query.addScalar("validatedDate", new TimestampType());
		query.addScalar("status", new IntegerType());
		query.addScalar("inviteMethod", new IntegerType());
		query.setResultTransformer(Transformers.aliasToBean(MgmSuggestedContacts.class));

		query.setParameter("idCardNumberLocal", idCardNumberLocal);

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = query.list().size();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}

	@Override
	public List<Map<String, Object>> getListPushMessageWhenFriendBecomeLoan() throws Exception {

		Session session = null;

		// Returns the Class object associated with the class
		Class.forName(systemConfig.getConfig(DRIVERCLASS));
		// Set connection timeout. Make sure you set this correctly as per your need
		DriverManager.setLoginTimeout(5);
		// get Connection
		Connection conn = DriverManager.getConnection(systemConfig.getConfig(JDBCURL), systemConfig.getConfig(USER_KH),
				systemConfig.getConfig(PASSWORD_KH));
		if (conn == null) {
			throw new Exception("Error connection!");
		}

		// get Session
		SessionBuilder sb = getSessionFactory().withOptions();
		session = sb.connection(conn).openSession();

		StringBuilder sql = new StringBuilder();
		sql.append(" WITH t1 AS ( ");
		sql.append(" 	SELECT ");
		sql.append(" 		RELATION_ID, CUSTOMER_ID, ");
		sql.append(" 		(COUNT (*)) as number_friend ");
		sql.append(" 	FROM ");
		sql.append(" 		MGM_SUGGESTED_CONTACTS ");
		sql.append(" 	WHERE ");
		sql.append(" 		STATUS = 8 and IS_SENT_MESSAGE_WHEN_LOAN != 1 ");
		sql.append(" 	GROUP BY ");
		sql.append(" 		RELATION_ID, CUSTOMER_ID ");
		sql.append(" )  ");
		sql.append(" SELECT RELATION_ID, CUSTOMER_ID,	NUMBER_FRIEND, (NUMBER_FRIEND * 200) RECEIVED_POINT   ");
		sql.append(" FROM t1 ");
		sql.append("  ");
		sql.append("  ");

		SQLQuery query = session.createSQLQuery(sql.toString());
		query.addScalar("RELATION_ID", new StringType());
		query.addScalar("CUSTOMER_ID", new StringType());
		query.addScalar("NUMBER_FRIEND", new IntegerType());
		query.addScalar("RECEIVED_POINT", new IntegerType());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> results = query.list();

		if (CollectionUtils.isNotEmpty(results)) {
			sql = new StringBuilder();
			sql.append(" UPDATE MGM_SUGGESTED_CONTACTS ");
			sql.append(" SET IS_SENT_MESSAGE_WHEN_LOAN = 1 ");
			sql.append(" WHERE ");
			sql.append(" 	STATUS = 8 ");
			sql.append(" AND IS_SENT_MESSAGE_WHEN_LOAN != 1 ");
			SQLQuery query2 = session.createSQLQuery(sql.toString());
			int countRS = query2.executeUpdate();
		}

		return results;

	}

}
