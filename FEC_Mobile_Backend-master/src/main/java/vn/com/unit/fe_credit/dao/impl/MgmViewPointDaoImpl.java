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
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MgmViewPointDao;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.entity.MgmSuggestedContactsPK;

@Repository
public class MgmViewPointDaoImpl extends GenericDAOImpl<MgmSuggestedContacts, MgmSuggestedContactsPK> implements MgmViewPointDao {

	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Integer getTotalFriends(Long customerId) {
		Integer count = null;
		String idCardNumberLocal = idCardNumberLocal(customerId);
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c.SUBMITED_CONTACT_COUNT as count" + " FROM MOBILE_CUSTOMER mc JOIN MGM_CUSTOMER c ON mc.CUSTOMER_ID=c.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			sql.append(" AND mc.ID_CARD_NUMBER = :idCardNumber");
		}

		Query query = getSessionKH().createSQLQuery(sql.toString()).addScalar("count", new StringType());
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			query.setParameter("idCardNumber", idCardNumberLocal);
		}

		if (query.uniqueResult() == null) {
			return 0;
		}
		count = Integer.parseInt(query.uniqueResult().toString());

		return count;
	}

	@Override
	public Integer getFriendsValid(Long customerId) {
		Integer count = null;
		String idCardNumberLocal = idCardNumberLocal(customerId);
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c.ACTIVE_CONTACT_COUNT as count" + " FROM MOBILE_CUSTOMER mc JOIN MGM_CUSTOMER c ON mc.CUSTOMER_ID=c.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			sql.append(" AND mc.ID_CARD_NUMBER = :idCardNumber");
		}

		Query query = getSessionKH().createSQLQuery(sql.toString()).addScalar("count", new StringType());
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			query.setParameter("idCardNumber", idCardNumberLocal);
		}

		if (query.uniqueResult() == null) {
			return 0;
		}
		count = Integer.parseInt(query.uniqueResult().toString());

		return count;
	}

	@Override
	public Integer getFriendsBecomeLoans(Long customerId) {
		Integer count = null;
		String idCardNumberLocal = idCardNumberLocal(customerId);
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c.LOAN_CONTACT_COUNT as count" + " FROM MOBILE_CUSTOMER mc JOIN MGM_CUSTOMER c ON mc.CUSTOMER_ID=c.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			sql.append(" AND mc.ID_CARD_NUMBER = :idCardNumber");
		}

		Query query = getSessionKH().createSQLQuery(sql.toString()).addScalar("count", new StringType());
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			query.setParameter("idCardNumber", idCardNumberLocal);
		}

		if (query.uniqueResult() == null) {
			return 0;
		}
		count = Integer.parseInt(query.uniqueResult().toString());

		return count;
	}

	@Override
	public Integer getRemainingPointsPreviousTerm(Long customerId) {
		return 0;
	}

	@Override
	public Integer getTotalPoints(Long customerId) {
		Integer redeemPoints = getPointsHasConverted(customerId);
		Integer remainingPoints = getRemainingPoints(customerId);
		return remainingPoints + redeemPoints;
	}

	@Override
	public Integer getPointsHasConverted(Long customerId) {
		Integer count = null;
		String idCardNumberLocal = idCardNumberLocal(customerId);
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT SUM(r.REDEEM_POINT) as count" + " FROM MOBILE_CUSTOMER mc JOIN MGM_REDEEM_TRANS r ON mc.CUSTOMER_ID=r.CUSTOMER_ID");
		sql.append(" WHERE 1=1 AND r.STATUS='3'"); // STATUS=3: success
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			sql.append(" AND mc.ID_CARD_NUMBER = :idCardNumber");
		}

		Query query = getSessionKH().createSQLQuery(sql.toString()).addScalar("count", new StringType());
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			query.setParameter("idCardNumber", idCardNumberLocal);
		}

		if (query.uniqueResult() == null) {
			return 0;
		}
		count = Integer.parseInt(query.uniqueResult().toString());

		return count;
	}

	@Override
	public Integer getRemainingPoints(Long customerId) {
		Integer count = null;
		String idCardNumberLocal = idCardNumberLocal(customerId);
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT c.MGM_POINT as count" + " FROM MOBILE_CUSTOMER mc JOIN MGM_CUSTOMER c ON mc.CUSTOMER_ID=c.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			sql.append(" AND mc.ID_CARD_NUMBER = :idCardNumber");
		}

		Query query = getSessionKH().createSQLQuery(sql.toString()).addScalar("count", new StringType());
		if (!String.valueOf(idCardNumberLocal).isEmpty()) {
			query.setParameter("idCardNumber", idCardNumberLocal);
		}

		if (query.uniqueResult() == null) {
			return 0;
		}
		count = Integer.parseInt(query.uniqueResult().toString());

		return count;
	}

	// get connection from KH
	private Session getSessionKH() {
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
		return session;
	}

	// get id_card_number local
	private String idCardNumberLocal(Long customerId) {
		Session session = getSession();
		String idCardNumber = null;
		StringBuilder sql = new StringBuilder();

		sql.append("SELECT IDCARDNUMBER as idCardNumber" + " FROM CUSTOMER");
		sql.append(" WHERE 1=1");
		if (!String.valueOf(customerId).isEmpty()) {
			sql.append(" AND CUSTOMERID = :customerId");
		}

		Query query = session.createSQLQuery(sql.toString()).addScalar("idCardNumber", new StringType());
		if (!String.valueOf(customerId).isEmpty()) {
			query.setParameter("customerId", customerId);
		}

		if (query.uniqueResult() == null) {
			return "";
		}
		idCardNumber = query.uniqueResult().toString();

		return idCardNumber;
	}

	@Override
	public Map<String, Object> getCustomerPointSummary(long customerId) throws Exception {

		String idCardNumber = idCardNumberLocal(customerId);

		StringBuilder sql = new StringBuilder();
		sql.append("  WITH T1 AS (   ");
		sql.append("  SELECT   ");
		sql.append("   C.CUSTOMER_ID,   ");
		sql.append("   MC.ID_CARD_NUMBER,   ");
		sql.append("   C.SUBMITED_CONTACT_COUNT AS TOTAL_FRIENDS,   ");
		sql.append("   C.ACTIVE_CONTACT_COUNT AS FRIENDS_VALID,   ");
		sql.append("   C.LOAN_CONTACT_COUNT AS FRIENDS_BECAME_LOANS,   ");
		sql.append("   0 AS POINTS_OF_PREVIOUS_TERM,   ");
		sql.append("   MGM_POINT AS TOTAL_POINTS,   ");
		sql.append("   C.REMAINING_POINT AS THE_REMAINING_POINTS,   ");
		sql.append("   (   ");
		sql.append("    SELECT  SUM ( CASE WHEN MRT.STATUS = 5 THEN (REDEEM_POINT * (-1)) "
				+ " 		WHEN MRT.TRANX_TYPE = 3 THEN 0 "
				+ "			ELSE REDEEM_POINT END) ");
		sql.append("    FROM  MGM_REDEEM_TRANS MRT   ");
		sql.append("    WHERE  MRT.CUSTOMER_ID = C.CUSTOMER_ID   ");
		sql.append("   ) AS TOTAL_POINTS_HAS_CONVERTED   ");
		sql.append("  FROM   ");
		sql.append("   MGM_CUSTOMER C   ");
		sql.append("  JOIN MOBILE_CUSTOMER MC ON MC.CUSTOMER_ID = C.CUSTOMER_ID   ");
		sql.append("  WHERE   ");
		sql.append("   MC.ID_CARD_NUMBER = :ID_CARD_NUMBER   ");
		sql.append("  ) SELECT   ");
		sql.append("  CUSTOMER_ID,   ");
		sql.append("  ID_CARD_NUMBER,   ");
		sql.append("  TOTAL_FRIENDS,   ");
		sql.append("  FRIENDS_VALID,   ");
		sql.append("  FRIENDS_BECAME_LOANS,   ");
		sql.append("  POINTS_OF_PREVIOUS_TERM,   ");
		sql.append("  TOTAL_POINTS,   ");
		sql.append("  TOTAL_POINTS_HAS_CONVERTED,   ");
		sql.append("  THE_REMAINING_POINTS   ");
		sql.append("  FROM T1   ");
		
		SQLQuery query = getSessionKH().createSQLQuery(sql.toString());
		query.addScalar("CUSTOMER_ID", new LongType());
		query.addScalar("ID_CARD_NUMBER", new StringType());
		query.addScalar("TOTAL_FRIENDS", new BigDecimalType());
		query.addScalar("FRIENDS_VALID", new BigDecimalType());
		query.addScalar("FRIENDS_BECAME_LOANS", new BigDecimalType());
		query.addScalar("POINTS_OF_PREVIOUS_TERM", new BigDecimalType());
		query.addScalar("TOTAL_POINTS", new BigDecimalType());
		query.addScalar("TOTAL_POINTS_HAS_CONVERTED", new BigDecimalType());
		query.addScalar("THE_REMAINING_POINTS", new BigDecimalType());

		query.setParameter("ID_CARD_NUMBER", idCardNumber);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> data = query.list();
		if (CollectionUtils.isNotEmpty(data)) {
			return data.get(0);
		} else {
			return null;
		}
	}

}
