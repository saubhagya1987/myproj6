package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.persistence.NoResultException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.v1.RepossessionBean;
import vn.com.unit.fe_credit.bean.v1.RepossessionDto;
import vn.com.unit.fe_credit.dao.v1.RepossessionDao;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;
import vn.com.unit.fe_credit.entity.collection.Repossession;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;
import vn.com.unit.fe_credit.service.impl.v1.ContractTerminationServiceImpl;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;
@Repository
public class RepossessionDaoImpl extends HibernateBaseDAO implements RepossessionDao{
	private static final String ADMIN_ROLE = "R001";
	private static final String FS_COLLECTOR_ADMIN_ROLE = "FS";
	private static final Logger LOG = LoggerFactory.getLogger(RepossessionDaoImpl.class);

	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}	
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionDto> findAll() {
		LOG.debug("Getting Repossession Contract list based on Email - ");
try{
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT DISTINCT rs.ID, rs.ID as id, rs.FC_CODE as fcCode, "				
				+ "rs.FC_NAME as fcName, "			
				+ "rs.ASSET_CONDITION as assetCondition, "				
				+ "rs.BRAND as brand, "
				+ "rs.CALLEDWHOM as calledWhom, "
				+ "rs.CONTACTED_DATE as ContactedDate, "
				+ "rs.APPL_ID as appleId, "
				+ "rs.CUSTOMERS_PHONE as customersPhone, "
				+ "rs.DPD as dpd, "
				+ "rs.FINANCIAL_CONDITION_ASSESSMENT as financialConditionAssessment, "
				+ "rs.CUST_FULL_NAME as customerName, "
				+ "rs.LAA_ASSET_MAKE_C as laaAssetMakeC, "
				+ "rs.LOAN_ACCT_NUM as loanAccountNumber, "
				+ "rs.LOAN_AMOUNT as loanAmount, "
				+ "rs.PRINCIPLE_OUTSTANDING as principalOutstanding, "
				+ "rs.REPOSESSION_ADDRESS as repossessionAddress, "
				+ "rs.STATUS as status, "
				+ "rs.SUGGESTIONS as suggestions, "
				+ "rs.TOTAL_AMOUNT_PAID as totalAmountPaid, "
				+ "rs.UNIT_CODE as unitCode, "
				+ "rs.UNIT_DESC as unitDesc, "
				+ "rs.STEP as step, "
				+ "rs.APPROVER_LEVEL as approverLevel, "
				+ "rs.CUSTOMER_ID as customerId, "
				+ "rs.NEXT_APPROVER_LEVEL as nextApproverLevel "
			/*	+ "raapl.ACTION_CODE as approvalStatus,"
				+ "raapl.USER_APPROVE as userApprove,"
				+ "raapl.APPROVE_DATE as approvedDate "*/
				+ "FROM REPOSSESSION rs "
				+ "INNER JOIN REPOSSESSION_APPROVER raapl on  rs.ID = raapl.REPOSSESSION_ID where rs.ID = raapl.REPOSSESSION_ID");
		

		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", IntegerType.INSTANCE)
				.addScalar("fcCode", StringType.INSTANCE)
				.addScalar("fcName", StringType.INSTANCE)
				.addScalar("assetCondition", StringType.INSTANCE)
				.addScalar("brand", StringType.INSTANCE)				
				.addScalar("calledWhom", StringType.INSTANCE)
				.addScalar("ContactedDate", StringType.INSTANCE)
				.addScalar("appleId", StringType.INSTANCE)
				.addScalar("customersPhone", StringType.INSTANCE)
				.addScalar("dpd", IntegerType.INSTANCE)
				.addScalar("financialConditionAssessment", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)
				.addScalar("laaAssetMakeC", StringType.INSTANCE)
				.addScalar("loanAccountNumber", IntegerType.INSTANCE)
				.addScalar("loanAmount", IntegerType.INSTANCE)
				.addScalar("principalOutstanding", IntegerType.INSTANCE)
				.addScalar("repossessionAddress", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("suggestions", StringType.INSTANCE)
				.addScalar("totalAmountPaid", IntegerType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("unitDesc", StringType.INSTANCE)
				.addScalar("step", StringType.INSTANCE)
				.addScalar("approverLevel", StringType.INSTANCE)
				.addScalar("customerId", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				/*.addScalar("approvalStatus", StringType.INSTANCE)
				.addScalar("userApprove", StringType.INSTANCE)
				.addScalar("approvedDate", TimestampType.INSTANCE)*/
				.setResultTransformer(Transformers.aliasToBean(RepossessionDto.class));

		return sqlQueryObject.list();
	}catch(Exception ex){
		LOG.error("error during Repossession Contract list "+ ExceptionUtils.getStackTrace(ex));
		throw ex;
	}
	}

	
	
	/**
	 * @param approverEmailId
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionDto> getRepossessionContractsAssigned(String approverEmailId,boolean isAdmin){
		String approvalLevel=ADMIN_ROLE;
		String approvalLevelAdminRole=FS_COLLECTOR_ADMIN_ROLE;
		
		StringBuilder sqlQuery = new StringBuilder();
		
		sqlQuery.append("SELECT rs.ID as id, rs.FC_CODE as fcCode, "				
				+ "rs.FC_NAME as fcName, "			
				+ "rs.ASSET_CONDITION as assetCondition, "				
				+ "rs.BRAND as brand, "
				+ "rs.CALLEDWHOM as calledWhom, "
				+ "rs.CONTACTED_DATE as ContactedDate, "
				+ "rs.APPL_ID as appleId, "
				+ "rs.CUSTOMERS_PHONE as customersPhone, "
				+ "rs.DPD as dpd, "
				+ "rs.FINANCIAL_CONDITION_ASSESSMENT as financialConditionAssessment, "
				+ "rs.CUST_FULL_NAME as customerName, "
				+ "rs.LAA_ASSET_MAKE_C as laaAssetMakeC, "
				+ "rs.LOAN_ACCT_NUM as loanAccountNumber, "
				+ "rs.LOAN_AMOUNT as loanAmount, "
				+ "rs.PRINCIPLE_OUTSTANDING as principalOutstanding, "
				+ "rs.REPOSESSION_ADDRESS as repossessionAddress, "
				+ "rs.STATUS as status, "
				+ "rs.SUGGESTIONS as suggestions, "
				+ "rs.TOTAL_AMOUNT_PAID as totalAmountPaid, "
				+ "rs.UNIT_CODE as unitCode, "
				+ "rs.UNIT_DESC as unitDesc, "
				+ "rs.STEP as step, "
				+ "rs.APPROVER_LEVEL as approverLevel, "
				+ "rs.NEXT_APPROVER_LEVEL as nextApproverLevel, "
				+ "raapl.ACTION_CODE as approvalStatus "
				+ "FROM REPOSSESSION rs "				
				+ "LEFT JOIN REPOSSESSION_APPROVAL raapl ON rs.ID = raapl.REPOSSESSION_ID WHERE USER_APPROVE = '" + approverEmailId + "'"
		        + "OR APPROVER_LEVEL = '" + approvalLevel + "'"
		        + "OR APPROVER_LEVEL = '" + approvalLevelAdminRole + "'");
		
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", IntegerType.INSTANCE)
				.addScalar("fcCode", StringType.INSTANCE)
				.addScalar("fcName", StringType.INSTANCE)
				.addScalar("assetCondition", StringType.INSTANCE)
				.addScalar("brand", StringType.INSTANCE)				
				.addScalar("calledWhom", StringType.INSTANCE)
				.addScalar("ContactedDate", StringType.INSTANCE)
				.addScalar("appleId", StringType.INSTANCE)
				.addScalar("customersPhone", StringType.INSTANCE)
				.addScalar("dpd", IntegerType.INSTANCE)
				.addScalar("financialConditionAssessment", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)
				.addScalar("laaAssetMakeC", StringType.INSTANCE)
				.addScalar("loanAccountNumber", IntegerType.INSTANCE)
				.addScalar("loanAmount", IntegerType.INSTANCE)
				.addScalar("principalOutstanding", IntegerType.INSTANCE)
				.addScalar("repossessionAddress", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("suggestions", StringType.INSTANCE)
				.addScalar("totalAmountPaid", IntegerType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("unitDesc", StringType.INSTANCE)
				.addScalar("step", StringType.INSTANCE)
				.addScalar("approverLevel", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				.addScalar("approvalStatus", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(RepossessionDto.class));

		return sqlQueryObject.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionDto> getRepossessionContracts(String approverEmailId){
		LOG.debug("Getting Repossession Contract list based on Email other than admin role - ");
		try{
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT rs.ID as id, rs.FC_CODE as fcCode, "				
				+ "rs.FC_NAME as fcName, "			
				+ "rs.ASSET_CONDITION as assetCondition, "				
				+ "rs.BRAND as brand, "
				+ "rs.CALLEDWHOM as calledWhom, "
				+ "rs.CONTACTED_DATE as ContactedDate, "
				+ "rs.APPL_ID as appleId, "
				+ "rs.CUSTOMERS_PHONE as customersPhone, "
				+ "rs.DPD as dpd, "
				+ "rs.FINANCIAL_CONDITION_ASSESSMENT as financialConditionAssessment, "
				+ "rs.CUST_FULL_NAME as customerName, "
				+ "rs.LAA_ASSET_MAKE_C as laaAssetMakeC, "
				+ "rs.LOAN_ACCT_NUM as loanAccountNumber, "
				+ "rs.LOAN_AMOUNT as loanAmount, "
				+ "rs.PRINCIPLE_OUTSTANDING as principalOutstanding, "
				+ "rs.REPOSESSION_ADDRESS as repossessionAddress, "
				+ "rs.STATUS as status, "
				+ "rs.SUGGESTIONS as suggestions, "
				+ "rs.TOTAL_AMOUNT_PAID as totalAmountPaid, "
				+ "rs.UNIT_CODE as unitCode, "
				+ "rs.UNIT_DESC as unitDesc, "
				+ "rs.STEP as step, "
				+ "rs.APPROVER_LEVEL as approverLevel, "
				+ "rs.CUSTOMER_ID as customerId, "
				+ "rs.NEXT_APPROVER_LEVEL as nextApproverLevel "				
				+ "FROM REPOSSESSION_APPROVER rapr "
				+ "INNER JOIN REPOSSESSION rs ON rapr.REPOSSESSION_ID = rs.ID "				
				+ " WHERE rapr.APPROVER_EMAIL_ID = '" + approverEmailId + "'");
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", IntegerType.INSTANCE)
				.addScalar("fcCode", StringType.INSTANCE)
				.addScalar("fcName", StringType.INSTANCE)
				.addScalar("assetCondition", StringType.INSTANCE)
				.addScalar("brand", StringType.INSTANCE)				
				.addScalar("calledWhom", StringType.INSTANCE)
				.addScalar("ContactedDate", StringType.INSTANCE)
				.addScalar("appleId", StringType.INSTANCE)
				.addScalar("customersPhone", StringType.INSTANCE)
				.addScalar("dpd", IntegerType.INSTANCE)
				.addScalar("financialConditionAssessment", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)
				.addScalar("laaAssetMakeC", StringType.INSTANCE)
				.addScalar("loanAccountNumber", IntegerType.INSTANCE)
				.addScalar("loanAmount", IntegerType.INSTANCE)
				.addScalar("principalOutstanding", IntegerType.INSTANCE)
				.addScalar("repossessionAddress", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("suggestions", StringType.INSTANCE)
				.addScalar("totalAmountPaid", IntegerType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("unitDesc", StringType.INSTANCE)
				.addScalar("step", StringType.INSTANCE)
				.addScalar("approverLevel", StringType.INSTANCE)
				.addScalar("customerId", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				
				.setResultTransformer(Transformers.aliasToBean(RepossessionDto.class));

		return sqlQueryObject.list();
		}catch(Exception ex){
			LOG.error("error during Repossession Contract list other than admin role  "+ ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public Repossession findByRepossessionId(Integer id) {
		LOG.debug("Request received for find by repossession id . . . . .");
		String hqlQuery = "FROM Repossession where id=:id";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("id", id);
		return (Repossession)hqlQueryObject.uniqueResult();
	}
	
	
	@Override
	public void updateTerminatedContractApprover() {
		// TODO Auto-generated method stub

	}

	@Transactional("txnManagerCollections")
	public int updateApproverLevelById(Integer id, String approverLevel){
		LOG.debug("Request received for update approval level by Id . . . . .");
		String hqlQuery = "UPDATE Repossession SET approverLevel=:approverLevel WHERE id=:id";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("id", id);
		hqlQueryObject.setParameter("approverLevel", approverLevel);
		return hqlQueryObject.executeUpdate();	
	}

	@Override
	@Transactional("txnManagerCollections")
	public void updateStatus(RepossessionBean repossessionBean,Repossession repossession) {
		LOG.debug("Request received for update status. . . . .");
		try{
		repossession.setStatus(repossessionBean.getStatus().toUpperCase());	
		repossession.setStep("1");
		getSession().saveOrUpdate(repossession);
		}catch(Exception ex){
			LOG.error("error during update status  "+ ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
		
	}

	
	
	@Transactional("txnManagerCollections")
	@Override
	public ApprovalRule getApprovalRuleByRole(String role) {		  
		LOG.debug("Getting Approval Rule By Role. . . . .");  
		try {		   
		   String queryString = "FROM ApprovalRule ar WHERE ar.role=:role";
		   Query query = getSession().createQuery(queryString);
		   query.setParameter("role", role);		   
		   return  (ApprovalRule) query.uniqueResult();   
		  } catch (NoResultException ex) {
		   return null;
		  } catch (Exception e) {
		   LOG.error("error during approval rule by role "+ ExceptionUtils.getStackTrace(e));
		   throw new RuntimeException(e);
		  }
		 
	}

	/*@Override
	@Transactional("txnManagerCollections")
	public Repossession findByContractTerminationId(Integer id) {
		String hqlQuery = "FROM ContractTermination where id=:id";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("id", id);
		return (Repossession)hqlQueryObject.uniqueResult();
	}*/
	
	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionDto> getRepossessionFormData(String appleId,String customerName, String approvalStatus){
		LOG.debug("Getting Repossession Contract list based on Email other than admin role - ");
		try{
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT rs.ID as id, rs.FC_CODE as fcCode, "				
				+ "rs.FC_NAME as fcName, "			
				+ "rs.ASSET_CONDITION as assetCondition, "				
				+ "rs.BRAND as brand, "
				+ "rs.CALLEDWHOM as calledWhom, "
				+ "rs.CONTACTED_DATE as ContactedDate, "
				+ "rs.APPL_ID as appleId, "
				+ "rs.CUSTOMERS_PHONE as customersPhone, "
				+ "rs.DPD as dpd, "
				+ "rs.FINANCIAL_CONDITION_ASSESSMENT as financialConditionAssessment, "
				+ "rs.CUST_FULL_NAME as customerName, "
				+ "rs.LAA_ASSET_MAKE_C as laaAssetMakeC, "
				+ "rs.LOAN_ACCT_NUM as loanAccountNumber, "
				+ "rs.LOAN_AMOUNT as loanAmount, "
				+ "rs.PRINCIPLE_OUTSTANDING as principalOutstanding, "
				+ "rs.REPOSESSION_ADDRESS as repossessionAddress, "
				+ "rs.STATUS as status, "
				+ "rs.SUGGESTIONS as suggestions, "
				+ "rs.TOTAL_AMOUNT_PAID as totalAmountPaid, "
				+ "rs.UNIT_CODE as unitCode, "
				+ "rs.UNIT_DESC as unitDesc, "
				+ "rs.STEP as step, "
				+ "rs.APPROVER_LEVEL as approverLevel, "
				+ "rs.NEXT_APPROVER_LEVEL as nextApproverLevel "				
				+ "FROM REPOSSESSION_APPROVER rapr "
				+ "INNER JOIN REPOSSESSION rs ON rapr.REPOSSESSION_ID = rs.ID "				
				+ " WHERE rs.APPL_ID = '" + appleId + "'")
		  		+ "OR APPROVER_LEVEL = '" + approvalLevel + "'"
		  		+ "OR APPROVER_LEVEL = '" + approvalLevelAdminRole + "'");
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", IntegerType.INSTANCE)
				.addScalar("fcCode", StringType.INSTANCE)
				.addScalar("fcName", StringType.INSTANCE)
				.addScalar("assetCondition", StringType.INSTANCE)
				.addScalar("brand", StringType.INSTANCE)				
				.addScalar("calledWhom", StringType.INSTANCE)
				.addScalar("ContactedDate", StringType.INSTANCE)
				.addScalar("appleId", StringType.INSTANCE)
				.addScalar("customersPhone", StringType.INSTANCE)
				.addScalar("dpd", IntegerType.INSTANCE)
				.addScalar("financialConditionAssessment", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)
				.addScalar("laaAssetMakeC", StringType.INSTANCE)
				.addScalar("loanAccountNumber", IntegerType.INSTANCE)
				.addScalar("loanAmount", IntegerType.INSTANCE)
				.addScalar("principalOutstanding", IntegerType.INSTANCE)
				.addScalar("repossessionAddress", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("suggestions", StringType.INSTANCE)
				.addScalar("totalAmountPaid", IntegerType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("unitDesc", StringType.INSTANCE)
				.addScalar("step", StringType.INSTANCE)
				.addScalar("approverLevel", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				
				.setResultTransformer(Transformers.aliasToBean(RepossessionDto.class));

		return sqlQueryObject.list();
		}catch(Exception ex){
			LOG.error("error during Repossession Contract list other than admin role  "+ ExceptionUtils.getStackTrace(ex));
			throw ex;
		}
	}*/

}
