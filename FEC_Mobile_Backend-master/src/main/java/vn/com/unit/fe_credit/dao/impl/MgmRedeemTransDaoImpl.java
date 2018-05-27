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
import org.hibernate.type.DateType;
import org.hibernate.type.FloatType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MgmRedeemTransDao;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;

@Repository
public class MgmRedeemTransDaoImpl extends GenericDAOImpl<MgmRedeemTrans, String> implements MgmRedeemTransDao {

	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public RedeemTrasPointBean getDataForExportRedeemTransactionPoint(RedeemTrasPointBean bean) throws Exception {
		Session session = null;
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
			// Set connection timeout. Make sure you set this correctly as per
			// your need
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

		// mobile
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT mc.TRANS_ID as refNum, msc.FIRST_NAME as firstName, msc.LAST_NAME as lastName, msc.MIDDLE_NAME as middleName, ");
		sql.append(" msc.ID_CARD_NUMBER as iDCardNumber, mc.CUSTOMER_ID as customerId, mc.TRANS_ID as transId, ");
		sql.append(" mc.VALUE_DESCRIPTION as valueDescription, mc.EXCHANGE_VALUE as exchangeValue, mc.SUBMISSION_DATE as submissionDate ");
		sql.append(" FROM MGM_REDEEM_TRANS mc JOIN MOBILE_CUSTOMER msc ON mc.CUSTOMER_ID = msc.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		sql.append(" AND mc.STATUS = :status  AND mc.TRANX_TYPE = :tranxType");
		sql.append(" ORDER BY mc.SUBMISSION_DATE DESC, mc.TRANS_ID DESC");

		SQLQuery query = session.createSQLQuery(sql.toString());
		query.addScalar("refNum", new StringType());
		query.addScalar("firstName", new StringType());
		query.addScalar("lastName", new StringType());
		query.addScalar("middleName", new StringType());
		query.addScalar("iDCardNumber", new StringType());
		query.addScalar("exchangeValue", new BigDecimalType());
		query.addScalar("customerId", new StringType());
		query.addScalar("transId", new StringType());
		query.addScalar("valueDescription", new StringType());
		query.addScalar("submissionDate", new DateType());
		query.setResultTransformer(Transformers.aliasToBean(MgmRedeemTrans.class));

		query.setParameter("tranxType", bean.getEntity().getTranxType());
		query.setParameter("status", SystemConfig.MGM_REDEEM_STATUS_SUBMITED);

		bean.setListResult(query.list());
		return bean;
	}

	@Override
	public void updateStatus(RedeemTrasPointBean bean) {
		Session session = null;
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
			// Set connection timeout. Make sure you set this correctly as per
			// your need
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

		// mobile
		for (MgmRedeemTrans mgmRedeemTrans : bean.getListResult()) {
			StringBuilder sql = new StringBuilder();
			sql.append(" update MGM_REDEEM_TRANS set STATUS  = :status ");
			sql.append(" where CUSTOMER_ID = :customerId and TRANS_ID = :transId  ");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("status", SystemConfig.MGM_REDEEM_STATUS_IN_PROCESS);
			query.setParameter("customerId", mgmRedeemTrans.getCustomerId());
			query.setParameter("transId", mgmRedeemTrans.getTransId());
			query.executeUpdate();
		}

	}

	@Override
	public void updateStatusRedeem(List<Object[]> lst) {
		// TODO Auto-generated method stub

	}

	@Override
	public RedeemTrasPointBean searchValueExportMomo(RedeemTrasPointBean bean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object[]> searchValueExportRedeem(RedeemTrasPointBean bean) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<MgmExportFileRedeemPoint> getMgmExportRedeem(Long departmentId) {

		String sql = " SELECT *"
				+ " FROM MGM_EXPORT_FILE_REDEEM_POINT"
				+ " WHERE IS_EXPORTING = '1';";

		Query query = getSession().createQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(MgmExportFileRedeemPoint.class));
		List<MgmExportFileRedeemPoint> list = query.list();

		return list;
	}
	
	@Override
	public List<Map<String, Object>> getListPushMessageNotice() throws Exception {
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
		sql.append(" SELECT ");
		sql.append(" 	mgm.CUSTOMER_ID, ");
		sql.append(" 	MB.ID_CARD_NUMBER, ");
		sql.append(" 	mgm.STATUS, ");
		sql.append(" 	mgm.TRANX_TYPE, ");
		sql.append(" 	NVL(mgm.REDEEM_POINT,0 ) AS REDEEM_POINT , ");
		sql.append(" 	NVL(mgm.EXCHANGE_VALUE,0 ) AS EXCHANGE_VALUE, ");
		sql.append(" 	NVL(mgm.REMAINING_POINT,0 ) AS REMAINING_POINT, ");
		sql.append(" 	mgm.SUBMISSION_DATE, ");
		sql.append(" 	mgm.VALUE_DESCRIPTION, ");
		sql.append(" 	mgm.TRANS_ID, ");
		sql.append(" 	mgm.NOTE, ");
		sql.append(" 	mgm.TRANS_ID, ");
		sql.append(" 	mgm.PARTNER_TRANS_REF_ID, ");
		sql.append(" 	mgm.DATE_NEXT_PAYMENT ");
		sql.append(" FROM ");
		sql.append(" 	MGM_REDEEM_TRANS mgm ");
		sql.append(" INNER JOIN MOBILE_CUSTOMER MB ON mgm.CUSTOMER_ID = MB.CUSTOMER_ID ");
		sql.append(" WHERE ");
		sql.append(" 	mgm.STATUS IN ('3', '4') ");
		sql.append(" AND mgm.TRANX_TYPE IN ('1', '2') ");
		sql.append(" AND ( ");
		sql.append(" 	mgm.IS_SENT_MESSAGE_WHEN_DONE != 1 ");
		sql.append(" 	OR mgm.IS_SENT_MESSAGE_WHEN_DONE IS NULL ");
		sql.append(" ) ");


		SQLQuery query = session.createSQLQuery(sql.toString());
		query.addScalar("CUSTOMER_ID", new StringType());
		query.addScalar("ID_CARD_NUMBER", new StringType());
		query.addScalar("STATUS", new IntegerType());
		query.addScalar("TRANX_TYPE", new IntegerType());
		query.addScalar("REDEEM_POINT", new BigDecimalType());
		query.addScalar("EXCHANGE_VALUE", new BigDecimalType());
		query.addScalar("REMAINING_POINT", new BigDecimalType());
		query.addScalar("SUBMISSION_DATE", new TimestampType());
		query.addScalar("VALUE_DESCRIPTION", new StringType());
		query.addScalar("TRANS_ID", new StringType());
		query.addScalar("NOTE", new StringType());
		query.addScalar("PARTNER_TRANS_REF_ID", new StringType());
		query.addScalar("DATE_NEXT_PAYMENT", new DateType());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> results = query.list();

		if (CollectionUtils.isNotEmpty(results)) {
			sql = new StringBuilder();
			sql.append(" UPDATE MGM_REDEEM_TRANS"
					+ " SET IS_SENT_MESSAGE_WHEN_DONE = '1'"
					+ " WHERE STATUS IN ('3', '4') AND TRANX_TYPE IN ('1', '2') "
					+ " AND (IS_SENT_MESSAGE_WHEN_DONE != 1 OR IS_SENT_MESSAGE_WHEN_DONE IS NULL) ");
			SQLQuery query2 = session.createSQLQuery(sql.toString());
			int countRS = query2.executeUpdate();
		}

		return results;
	}
}
