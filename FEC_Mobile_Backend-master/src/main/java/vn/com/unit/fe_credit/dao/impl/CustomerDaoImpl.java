package vn.com.unit.fe_credit.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.bean.CustomerBean;
import vn.com.unit.fe_credit.dao.CustomerDao;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.utils.Utils;

@Repository
public class CustomerDaoImpl extends GenericDAOImpl<Customer, Long> implements CustomerDao {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public CustomerBean search(CustomerBean bean) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT customerId as customerId, fullName AS fullName, oldUserId AS oldUserId, ");
		sql.append(" idCardNumber AS idCardNumber, cellPhone AS cellPhone, ");
		sql.append(" status AS status, birthday AS birthday, imagePath AS imagePath, ");
		sql.append(
				"  addressNo as addressNo, street as street, ward as ward, district as district, province as province, addressS as addressS, address122 as address122 ");
		sql.append(" FROM ( ");

		sql.append(" SELECT c.customerId as customerId, c.fullName AS fullName, c.oldUserId AS oldUserId, ");
		sql.append(
				" c.idCardNumber AS idCardNumber, c.cellPhone AS cellPhone, c.status AS status, c.birthday AS birthday, c.imagePath AS imagePath, ");
		sql.append(
				" c.ADDRESSNO as addressNo, c.street as street, c.ward as ward, c.district as district, c.province as province, c.ADDRESS as addressS ,");
		sql.append(
				"CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(CONCAT(c.ADDRESSNO, ' '), c.STREET), ' '), c.WARD), ' '), c.DISTRICT), ' '), c.PROVINCE) AS address122");
		sql.append(" FROM Customer c ");
		sql.append(" ) ");
		sql.append(" WHERE 1=1 ");

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			sql.append(" AND lower(fullName) like lower(:fullName)");
		}

		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			sql.append(" AND cellPhone like :cellPhone");
		}

		if (StringUtils.isNotEmpty(bean.getCardNo())) {
			sql.append(" AND idCardNumber like :idCardNumber");
		}

		if (StringUtils.isNotEmpty(bean.getShearAddress())) {
			sql.append(" AND lower(address122) like lower(:cutAddress)");
		}
		if (bean.getStatus() != null) {
			sql.append(" AND status = :status");
		}
		if (bean.getShearLoan() != null) {
			if (bean.getShearLoan().equalsIgnoreCase("1")) {
				sql.append(" AND oldUserId is not null");
			}
			if (bean.getShearLoan().equalsIgnoreCase("-1"))
				sql.append(" AND oldUserId is  null");
		}

		// sql.append(" AND c.firstName is not null");
		if (StringUtils.isNotEmpty(bean.getDir())) {
			if (bean.getDir().equals("idCardNumber")) {
				sql.append(" ORDER BY idCardNumber " + bean.getSort());
			}
			if (bean.getDir().equals("addressNo")) {
				sql.append(" ORDER BY CASE WHEN addressNo is null THEN street ELSE addressNo END " + bean.getSort());
			}
		} else {
			sql.append(" ORDER BY customerId  desc");
		}

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("customerId", new LongType()).addScalar("fullName", new StringType())
				.addScalar("idCardNumber", new StringType()).addScalar("cellPhone", new StringType()).addScalar("status", new IntegerType())
				.addScalar("birthday", new TimestampType()).addScalar("imagePath", new StringType()).addScalar("addressNo", new StringType())
				.addScalar("street", new StringType()).addScalar("ward", new StringType()).addScalar("district", new StringType())
				.addScalar("province", new StringType()).addScalar("addressS", new StringType()).addScalar("oldUserId", new StringType());
		// .addScalar("address",new StringType());
		query.setResultTransformer(Transformers.aliasToBean(Customer.class));
		Query queryCount = getSession().createSQLQuery(Utils.getCountQuery(sql.toString()));

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			query.setParameter("fullName", "%" + bean.getCustomerName().trim() + "%");
			queryCount.setParameter("fullName", "%" + bean.getCustomerName().trim() + "%");
		}

		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			query.setParameter("cellPhone", "%" + bean.getCellPhone().trim() + "%");
			queryCount.setParameter("cellPhone", "%" + bean.getCellPhone().trim() + "%");
		}

		if (StringUtils.isNotEmpty(bean.getCardNo())) {
			query.setParameter("idCardNumber", "%" + bean.getCardNo().trim() + "%");
			queryCount.setParameter("idCardNumber", "%" + bean.getCardNo().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getShearAddress())) {
			String cutAddress = bean.getShearAddress().replaceAll(",", "");
			query.setParameter("cutAddress", "%" + cutAddress + "%");
			queryCount.setParameter("cutAddress", "%" + cutAddress + "%");
		}
		if (bean.getStatus() != null) {
			query.setParameter("status", bean.getStatus());
			queryCount.setParameter("status", bean.getStatus());
		}
		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = ((BigDecimal) queryCount.list().get(0)).intValue();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}
		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}

	@Override
	public Customer findbyCustomerId(String customerId) {
		// TODO Auto-generated method stub
		List<Customer> results = search(new Search().addFilterEqual("customerId", customerId));
		if (results.size() > 0)
			return results.get(0);
		return null;
	}

	@Override
	public void deleteCustomerImport(Date date) {
		StringBuilder sql = new StringBuilder();
		sql.append(" DELETE FROM CUSTOMER  ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND DATEIMPORT <= :DATEIMPORT");
		sql.append(" AND ISLOGGED is not null");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("DATEIMPORT", date);
		query.executeUpdate();
	}

	@Override
	public List<Customer> cuontDeleteCustomerImport(Date date) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT CUSTOMERID FROM CUSTOMER  ");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND DATEIMPORT <= :DATEIMPORT");
		sql.append(" AND ISLOGGED is not null");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("DATEIMPORT", date);
		return query.list();
	}

	@Override
	public int updateOTPResetPasswordAttempts(long customerId, int otpAttempts, Date bannedDueDate) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CUSTOMER ");
		sql.append(" SET WRG_OTP_RESET_PWD_ATTEMPTS = :otpAttempts ,");
		sql.append(" 	 WRG_OTP_RESET_PWD_BAN_DUE = :bannedDueDate ");
		sql.append(" WHERE CUSTOMERID = :customerId");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("customerId", customerId);
		query.setParameter("otpAttempts", otpAttempts);
		query.setParameter("bannedDueDate", bannedDueDate, new TimestampType());
		return query.executeUpdate();

	}

	@Override
	public int resetRequestOTPAttempts(long customerId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" UPDATE CUSTOMER ");
		sql.append(" SET NUMBERGETCODE = 0 , SMSCODE = null,  REQUEST_OTP_BLOCKED_DUE = null, OTP_LIVE_DUE = null ");
		sql.append(" WHERE CUSTOMERID = :customerId");

		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("customerId", customerId);
		return query.executeUpdate();

	}
}
