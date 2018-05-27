package vn.com.unit.fe_credit.dao.impl;

import static vn.com.unit.fe_credit.config.SystemConfig.DRIVERCLASS;
import static vn.com.unit.fe_credit.config.SystemConfig.JDBCURL;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_KH;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import oracle.jdbc.pool.OracleDataSource;
import vn.com.unit.fe_credit.bean.MgmExportVtigerBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MgmExportVtigerDao;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.storeprocedure.MgmExportVtigerSP;

@Repository
public class MgmExportVtigerDaoImpl extends GenericDAOImpl<MgmSuggestedContacts, String> implements MgmExportVtigerDao {
	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public MgmExportVtigerBean searchValueExport(MgmExportVtigerBean bean) {
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

		// mobile
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT mc.CONTACT_NAME as contactName, mc.CONTACT_PHONE as contactPhone, mc.NATIONAL_ID as nationalId,mc.CUSTOMER_ID as customerId, mc.AGE_RANGE as ageRange ");
		sql.append(
				" FROM MGM_SUGGESTED_CONTACTS mc JOIN MGM_CUSTOMER msc ON mc.CUSTOMER_ID = msc.CUSTOMER_ID");
		sql.append(" WHERE 1=1 AND mc.STATUS = 3");
		sql.append(" ORDER BY mc.SUBMITED_DATE DESC");

		Query query = session.createSQLQuery(sql.toString()).addScalar("contactName", new StringType())
				.addScalar("nationalId", new StringType()).addScalar("contactPhone", new StringType())
				.addScalar("customerId", new StringType()).addScalar("ageRange", new StringType());
		query.setResultTransformer(Transformers.aliasToBean(MgmSuggestedContacts.class));

		bean.setListResult(query.list());
		return bean;
	}
	
	@Override
	public void updateStatus(MgmExportVtigerBean dataToVTigerBean) {
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

		// mobile
		for (MgmSuggestedContacts mgmexportVTiger : dataToVTigerBean.getListResult()) {
			StringBuilder sql = new StringBuilder();
			sql.append(" update MGM_SUGGESTED_CONTACTS set STATUS  = '6' ");
			sql.append(" where CUSTOMER_ID = :customerId and CONTACT_PHONE = :contactPhone");

			Query query = session.createSQLQuery(sql.toString());
			query.setParameter("customerId", mgmexportVTiger.getCustomerId());
			query.setParameter("contactPhone", mgmexportVTiger.getContactPhone());
			query.executeUpdate();
		}

	}
	
	public List<MgmExportFileDataToVTiger> getMgmExportVtiger(Long departmentId) {
		
		String sql = " SELECT * "
				+ " FROM MGM_EXPORT_FILE_VTIGER "
				+ " WHERE IS_EXPORTING = '1' ";

		Query query = getSession().createQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(MgmExportFileDataToVTiger.class));
		List<MgmExportFileDataToVTiger> list = query.list();

		return list;
	}

}
