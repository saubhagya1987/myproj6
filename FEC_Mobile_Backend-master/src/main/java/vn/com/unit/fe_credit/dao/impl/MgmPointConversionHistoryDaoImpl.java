package vn.com.unit.fe_credit.dao.impl;

import static vn.com.unit.fe_credit.config.SystemConfig.DRIVERCLASS;
import static vn.com.unit.fe_credit.config.SystemConfig.JDBCURL;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_KH;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.MgmPointConversionHistoryBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MgmPointConversionHistoryDao;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;

@Repository
public class MgmPointConversionHistoryDaoImpl extends GenericDAOImpl<MgmRedeemTrans, String>
		implements MgmPointConversionHistoryDao {
	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public MgmPointConversionHistoryBean searchListPEPointByCustomerId(MgmPointConversionHistoryBean bean,
			Long customerId) {
		Session sessionLocal = getSession();
		Session session = null;
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
			// Set connection timeout. Make sure you set this correctly as per
			// your need
			DriverManager.setLoginTimeout(5);

			// get Connection
			Connection conn = DriverManager.getConnection(systemConfig.getConfig(JDBCURL),
					systemConfig.getConfig(USER_KH), systemConfig.getConfig(PASSWORD_KH));
			if (conn == null) {
				throw new Exception("Error connection!");
			}

			// get Session
			SessionBuilder sb = getSessionFactory().withOptions();
			session = sb.connection(conn).openSession();

		} catch (Exception e) {
			e.printStackTrace();
		}
		//
		String idCardNumberLocal = null;
		StringBuilder sqlLocal = new StringBuilder();

		sqlLocal.append("SELECT IDCARDNUMBER as idCardNumber" + " FROM CUSTOMER");
		sqlLocal.append(" WHERE 1=1");
		sqlLocal.append(" AND CUSTOMERID = :customerId");

		Query queryLocal = sessionLocal.createSQLQuery(sqlLocal.toString()).addScalar("idCardNumber", new StringType());
		if (!String.valueOf(customerId).isEmpty()) {
			queryLocal.setParameter("customerId", customerId);
		}
		if (queryLocal.uniqueResult() == null) {
			idCardNumberLocal = "";
		} else {
			idCardNumberLocal = queryLocal.uniqueResult().toString();
			// t2 - mobile
			String cusId = null;
			StringBuilder sqlmobile = new StringBuilder();

			sqlmobile.append("SELECT CUSTOMER_ID as customerId" + " FROM MOBILE_CUSTOMER");
			sqlmobile.append(" WHERE 1=1");
			sqlmobile.append(" AND ID_CARD_NUMBER = :idCardNumber");

			Query sqlMobile = session.createSQLQuery(sqlmobile.toString()).addScalar("customerId", new StringType());
			if (!String.valueOf(idCardNumberLocal).isEmpty()) {
				sqlMobile.setParameter("idCardNumber", idCardNumberLocal);
			}
			if (sqlMobile.uniqueResult() == null) {
				cusId = "";
			} else {
				cusId = sqlMobile.uniqueResult().toString();
				StringBuilder sql = new StringBuilder();
				sql.append("SELECT sc.SUBMISSION_DATE as submissionDate, sc.EXCHANGE_RATE as exchangeRate, sc.REDEEM_POINT as redeemPoint, "
						+ "sc.REMAINING_POINT as remainingPoint, sc.TRANX_TYPE as tranxType, sc.EXCHANGE_VALUE as exchangeValue,"
						+ "sc.COMPLETION_DATE as completionDate, sc.VALUE_DESCRIPTION as valueDescription,"
						+ "sc.DATE_NEXT_PAYMENT as dateNextPayment, sc.PARTNER_TRANS_REF_ID as partnerTransRefId,sc.STATUS as status,sc.NOTE as remark"
						+ " FROM MGM_REDEEM_TRANS sc JOIN MOBILE_CUSTOMER msc ON sc.CUSTOMER_ID = msc.CUSTOMER_ID");
				sql.append(" WHERE 1=1 ");

				if (!String.valueOf(idCardNumberLocal).isEmpty()) {
					sql.append(" AND msc.ID_CARD_NUMBER = :idCardNumber");
				}

				sql.append(" ORDER BY SC.SUBMISSION_DATE DESC, sc.TRANS_ID DESC");
				Query query = session.createSQLQuery(sql.toString()).addScalar("redeemPoint", new LongType()).addScalar("exchangeRate", new LongType())
						.addScalar("remainingPoint", new LongType()).addScalar("tranxType", new StringType())
						.addScalar("exchangeValue", new BigDecimalType()).addScalar("valueDescription", new StringType())
						.addScalar("partnerTransRefId", new StringType()).addScalar("status", new IntegerType())
						.addScalar("remark", new StringType()).addScalar("submissionDate", new TimestampType())
						.addScalar("completionDate", new TimestampType())
						.addScalar("dateNextPayment", new TimestampType());
				query.setResultTransformer(Transformers.aliasToBean(MgmRedeemTrans.class));

				if (!String.valueOf(idCardNumberLocal).isEmpty()) {
					query.setParameter("idCardNumber", idCardNumberLocal);
				}
				int totalCount = 0;
				if (bean.getLimit() > 0) {
					totalCount = query.list().size();
					query.setMaxResults(bean.getLimit());
					query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
				}
				bean.setTotal(totalCount);
				bean.setListResult(query.list());
			}
		}

		return bean;
	}

}
