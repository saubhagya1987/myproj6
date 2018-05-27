package vn.com.unit.fe_credit.dao.impl.v1;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationDto;
import vn.com.unit.fe_credit.controller.v1.TerminationController;
import vn.com.unit.fe_credit.dao.v1.ContractTerminationDao;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;
import vn.com.unit.fe_credit.entity.collection.ContractTerminationApproval;

@Repository
public class ContractTerminationDaoImpl extends HibernateBaseDAO implements ContractTerminationDao {
	private static final Logger LOG = LoggerFactory.getLogger(ContractTerminationDaoImpl.class);
	private static final String ADMIN_ROLE = "R001";
	private static final String FS_COLLECTOR_ADMIN_ROLE = "FS";

	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/*@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationDto> findAll() {

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ct.CONTRACT_TERMINATION_ID as contractTerminationId, "
				+ "ct.CONTRACT_ID as contractId, "
				+ "CONCAT(ct.FIRST_NAME, CONCAT(' ', CONCAT(ct.MIDDLE_NAME, CONCAT(' ', ct.LAST_NAME)))) as customerName, "
				+ "ct.REASON as reason, "
				+ "ct.PAYER as payer, "
				+ "ct.APPROVER_LEVEL as approverLevel, "
				+ "ct.NEXT_APPROVER_LEVEL as nextApproverLevel, "
				+ "ct.TOTAL_AMT_PAID_BY_CUSTOMER as totalAmtPaid, "
				+ "ct.PRINICIPLE_OUTSTANDING as principleOutstanding, "
				+ "ctapl.ACTION_CODE as approvalStatus,"
				+ "ctapl.USER_APPROVE as approvedUserEmailId,"
				+ "ctapl.APPROVE_DATE as approvedDate "
				+ "FROM CONTRACT_TERMINATION ct "
				+ "LEFT JOIN CONTRACT_TERMINATION_APPROVAL ctapl ON ct.CONTRACT_TERMINATION_ID = ctapl.TERMINATION_ID");
		

		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("contractTerminationId", IntegerType.INSTANCE)
				.addScalar("contractId", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)
				.addScalar("payer", StringType.INSTANCE)
				.addScalar("reason", StringType.INSTANCE)
				.addScalar("approverLevel", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				.addScalar("totalAmtPaid", FloatType.INSTANCE)
				.addScalar("principleOutstanding", FloatType.INSTANCE)
				.addScalar("approvalStatus", StringType.INSTANCE)
				.addScalar("approvedUserEmailId", StringType.INSTANCE)
				.addScalar("approvedDate", TimestampType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(ContractTerminationDto.class));

		return sqlQueryObject.list();
	}*/
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationDto> findAll() {

		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT DISTINCT ct.CONTRACT_TERMINATION_ID , ct.CONTRACT_TERMINATION_ID as contractTerminationId, ct.LOAN_ACCOUNT_NUM as contractId, "
				+ "ct.CUSTOMER_ID as customerId, "
				+ "ct.FULL_NAME as customerName, "			
				+ "ct.APPROVER_LEVEL as approverLevel, "
				/*+ "ct.UPDATION_DATE as updationDate, "*/
				+ "ct.PENALTY_FEE_REQUESTED as penaltyFeeRequested, "
				+ "ct.EARLY_TERMINATION_FEE as earlyTerminationFeeRequested, "
				+ "ct.DPD as dpd, "
				+ "ct.BUCKET as bucket, "
				+ "ct.UNIT_CODE as unitCode, "
				+ "ct.REASON as reason, "
				+ "ct.CHARGE_OFF_FLAG as chargeOffFlag, "
				+ "ct.PRINICIPLE_OUTSTANDING as principleOutstanding, "
				+ "ct.OVERDUE_CHARGES as overdueCharges, "
				+ "ct.INTEREST_OUTSTANDING as interestOutstanding, "
				+ "ct.PREPAYMENT_PENALTY as prepaymentPenalty, "
				+ "ct.REFUNDS as refunds, "
				+ "ct.INTER_FUND as interFund, "
				+ "ct.TOTAL_AMT_PAID_BY_CUSTOMER as totalAmontPaidByCustomer, "
				+ "ct.BILLED_INST as billedInst, "
				+ "ct.PHONE_NUMBER as phoneNumber, "
				+ "ct.PAYER as payer, "
				+ "ct.NEXT_APPROVER_LEVEL as nextApproverLevel, "
				+ "ct.STATUS as approvalStatus,"
				+ "ct.UPDATED_BY as approvedUserEmailId,"
				+ "ct.UPDATION_DATE as approvedDate "
				+ "FROM CONTRACT_TERMINATION ct "
				+ "INNER JOIN CONTRACT_TERMINATION_APPROVER ctapr ON ct.CONTRACT_TERMINATION_ID = ctapr.CONTRACT_TERMINATION_ID where ct.CONTRACT_TERMINATION_ID = ctapr.CONTRACT_TERMINATION_ID");
		

		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("contractTerminationId", IntegerType.INSTANCE)
				.addScalar("contractId", StringType.INSTANCE)
				.addScalar("customerId", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)				
				.addScalar("approverLevel", StringType.INSTANCE)
				/*.addScalar("updationDate", TimestampType.INSTANCE)*/
				.addScalar("penaltyFeeRequested", BigDecimalType.INSTANCE)
				.addScalar("earlyTerminationFeeRequested", BigDecimalType.INSTANCE)
				.addScalar("dpd", BigDecimalType.INSTANCE)
				.addScalar("bucket", BigDecimalType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("reason", StringType.INSTANCE)
				.addScalar("chargeOffFlag", StringType.INSTANCE)
				.addScalar("principleOutstanding", BigDecimalType.INSTANCE)
				.addScalar("overdueCharges", DoubleType.INSTANCE)
				.addScalar("interestOutstanding", StringType.INSTANCE)
				.addScalar("prepaymentPenalty", DoubleType.INSTANCE)
				.addScalar("refunds", DoubleType.INSTANCE)
				.addScalar("interFund", DoubleType.INSTANCE)
				.addScalar("totalAmontPaidByCustomer", BigDecimalType.INSTANCE)
				.addScalar("billedInst", DoubleType.INSTANCE)
				.addScalar("phoneNumber", StringType.INSTANCE)
				.addScalar("payer", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				.addScalar("approvalStatus", StringType.INSTANCE)
				.addScalar("approvedUserEmailId", StringType.INSTANCE)
				.addScalar("approvedDate", TimestampType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(ContractTerminationDto.class));

		return sqlQueryObject.list();
	}

	
	/**
	 * @param approverEmailId
	 *//*
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationDto> getTerminatedContractsAssigned(String approverEmailId){
		
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ctapr.CONTRACT_TERMINATION_ID as contractTerminationId, ctapr.CONTRACT_ID as contractId, "
				+ "CONCAT(ct.FIRST_NAME, CONCAT(' ', CONCAT(ct.MIDDLE_NAME, CONCAT(' ', ct.LAST_NAME)))) as customerName, "
				+ "ct.REASON as reason, "
				+ "ct.PAYER as payer, "
				+ "ct.APPROVER_LEVEL as approverLevel, "
				+ "ct.NEXT_APPROVER_LEVEL as nextApproverLevel, "
				+ "ct.TOTAL_AMT_PAID_BY_CUSTOMER as totalAmtPaid, "
				+ "ct.PRINICIPLE_OUTSTANDING as principleOutstanding, "
				+ "ctapl.ACTION_CODE as approvalStatus "
				+ "FROM CONTRACT_TERMINATION_APPROVER ctapr "
				+ "INNER JOIN CONTRACT_TERMINATION ct ON ctapr.CONTRACT_TERMINATION_ID = ct.CONTRACT_TERMINATION_ID  "
				+ "LEFT JOIN CONTRACT_TERMINATION_APPROVAL ctapl ON ctapl.TERMINATION_ID = ctapr.CONTRACT_TERMINATION_ID WHERE APPROVER_EMAIL_ID = '" + approverEmailId + "'");
		
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("contractTerminationId", IntegerType.INSTANCE)
				.addScalar("contractId", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)
				.addScalar("payer", StringType.INSTANCE)
				.addScalar("reason", StringType.INSTANCE)
				.addScalar("approverLevel", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				.addScalar("totalAmtPaid", FloatType.INSTANCE)
				.addScalar("principleOutstanding", FloatType.INSTANCE)
				.addScalar("approvalStatus", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(ContractTerminationDto.class));

		return sqlQueryObject.list();
	}*/
	
	/**
	 * @param approverEmailId
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationDto> getTerminatedContractsAssigned(String approverEmailId,boolean isAdmin){
		String approvalLevel=ADMIN_ROLE;
		String approvalLevelAdminRole=FS_COLLECTOR_ADMIN_ROLE;
		/*if(isAdmin && (approvalLevel.equalsIgnoreCase(ADMIN_ROLE))){
			approvalLevel=ADMIN_ROLE;
		}
		else if(isAdmin && (approvalLevelAdminRole.equalsIgnoreCase(FS_COLLECTOR_ADMIN_ROLE))){
			approvalLevelAdminRole=FS_COLLECTOR_ADMIN_ROLE;
		}*/
		StringBuilder sqlQuery = new StringBuilder();
		
		sqlQuery.append("SELECT ct.CONTRACT_TERMINATION_ID as contractTerminationId, ct.LOAN_ACCOUNT_NUM as contractId, "
				+ "ct.CUSTOMER_ID as customerId, "
				+ "ct.FULL_NAME as customerName, "			
				+ "ct.APPROVER_LEVEL as approverLevel, "
				/*+ "ct.UPDATION_DATE as updationDate, "*/
				+ "ct.PENALTY_FEE_REQUESTED as penaltyFeeRequested, "
				+ "ct.EARLY_TERMINATION_FEE as earlyTerminationFeeRequested, "
				+ "ct.DPD as dpd, "
				+ "ct.BUCKET as bucket, "
				+ "ct.UNIT_CODE as unitCode, "
				+ "ct.REASON as reason, "
				+ "ct.CHARGE_OFF_FLAG as chargeOffFlag, "
				+ "ct.PRINICIPLE_OUTSTANDING as principleOutstanding, "
				+ "ct.OVERDUE_CHARGES as overdueCharges, "
				+ "ct.INTEREST_OUTSTANDING as interestOutstanding, "
				+ "ct.PREPAYMENT_PENALTY as prepaymentPenalty, "
				+ "ct.REFUNDS as refunds, "
				+ "ct.INTER_FUND as interFund, "
				+ "ct.TOTAL_AMT_PAID_BY_CUSTOMER as totalAmontPaidByCustomer, "
				+ "ct.BILLED_INST as billedInst, "
				+ "ct.PHONE_NUMBER as phoneNumber, "
				+ "ct.PAYER as payer, "
				+ "ct.NEXT_APPROVER_LEVEL as nextApproverLevel, "
				+ "ctapl.ACTION_CODE as approvalStatus "
				+ "FROM CONTRACT_TERMINATION ct "
				/*+ "FROM CONTRACT_TERMINATION_APPROVER ctapr "*/
				/*+ "INNER JOIN CONTRACT_TERMINATION ct ON ctapr.CONTRACT_TERMINATION_ID = ct.CONTRACT_TERMINATION_ID  "*/
				+ "LEFT JOIN CONTRACT_TERMINATION_APPROVAL ctapl ON ctapl.TERMINATION_ID = ct.CONTRACT_TERMINATION_ID WHERE USER_APPROVE = '" + approverEmailId + "'"
		        + "OR APPROVER_LEVEL = '" + approvalLevel + "'"
		        + "OR APPROVER_LEVEL = '" + approvalLevelAdminRole + "'");
		
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("contractTerminationId", IntegerType.INSTANCE)
				.addScalar("contractId", StringType.INSTANCE)
				.addScalar("customerId", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)				
				.addScalar("approverLevel", StringType.INSTANCE)
				/*.addScalar("updationDate", TimestampType.INSTANCE)*/
				.addScalar("penaltyFeeRequested", BigDecimalType.INSTANCE)
				.addScalar("earlyTerminationFeeRequested", BigDecimalType.INSTANCE)
				.addScalar("dpd", BigDecimalType.INSTANCE)
				.addScalar("bucket", BigDecimalType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("reason", StringType.INSTANCE)
				.addScalar("chargeOffFlag", StringType.INSTANCE)
				.addScalar("principleOutstanding", BigDecimalType.INSTANCE)
				.addScalar("overdueCharges", DoubleType.INSTANCE)
				.addScalar("interestOutstanding", StringType.INSTANCE)
				.addScalar("prepaymentPenalty", DoubleType.INSTANCE)
				.addScalar("refunds", DoubleType.INSTANCE)
				.addScalar("interFund", DoubleType.INSTANCE)
				.addScalar("totalAmontPaidByCustomer", BigDecimalType.INSTANCE)
				.addScalar("billedInst", DoubleType.INSTANCE)
				.addScalar("phoneNumber", StringType.INSTANCE)
				.addScalar("payer", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				.addScalar("approvalStatus", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(ContractTerminationDto.class));

		return sqlQueryObject.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationDto> getTerminatedContracts(String approverEmailId){
		
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT ctapr.CONTRACT_TERMINATION_ID as contractTerminationId, ct.LOAN_ACCOUNT_NUM as contractId, "
				+ "ct.CUSTOMER_ID as customerId, "
				+ "ct.FULL_NAME as customerName, "			
				+ "ct.APPROVER_LEVEL as approverLevel, "
				/*+ "ct.UPDATION_DATE as updationDate, "*/
				+ "ct.PENALTY_FEE_REQUESTED as penaltyFeeRequested, "
				+ "ct.EARLY_TERMINATION_FEE as earlyTerminationFeeRequested, "
				+ "ct.DPD as dpd, "
				+ "ct.BUCKET as bucket, "
				+ "ct.UNIT_CODE as unitCode, "
				+ "ct.REASON as reason, "
				+ "ct.CHARGE_OFF_FLAG as chargeOffFlag, "
				+ "ct.PRINICIPLE_OUTSTANDING as principleOutstanding, "
				+ "ct.OVERDUE_CHARGES as overdueCharges, "
				+ "ct.INTEREST_OUTSTANDING as interestOutstanding, "
				+ "ct.PREPAYMENT_PENALTY as prepaymentPenalty, "
				+ "ct.REFUNDS as refunds, "
				+ "ct.INTER_FUND as interFund, "
				+ "ct.TOTAL_AMT_PAID_BY_CUSTOMER as totalAmontPaidByCustomer, "
				+ "ct.BILLED_INST as billedInst, "
				+ "ct.PHONE_NUMBER as phoneNumber, "
				+ "ct.PAYER as payer, "
				+ "ct.NEXT_APPROVER_LEVEL as nextApproverLevel "
				/*+ "ctapl.ACTION_CODE as approvalStatus "*/
				+ "FROM CONTRACT_TERMINATION_APPROVER ctapr "
				+ "INNER JOIN CONTRACT_TERMINATION ct ON ctapr.CONTRACT_TERMINATION_ID = ct.CONTRACT_TERMINATION_ID  "
				/*+ "LEFT JOIN CONTRACT_TERMINATION_APPROVAL ctapl ON ctapl.TERMINATION_ID = ctapr.CONTRACT_TERMINATION_ID WHERE APPROVER_EMAIL_ID = '" + approverEmailId + "'");*/
				+ " WHERE APPROVER_EMAIL_ID = '" + approverEmailId + "'");
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("contractTerminationId", IntegerType.INSTANCE)
				.addScalar("contractId", StringType.INSTANCE)
				.addScalar("customerId", StringType.INSTANCE)
				.addScalar("customerName", StringType.INSTANCE)				
				.addScalar("approverLevel", StringType.INSTANCE)
				/*.addScalar("updationDate", TimestampType.INSTANCE)*/
				.addScalar("penaltyFeeRequested", BigDecimalType.INSTANCE)
				.addScalar("earlyTerminationFeeRequested", BigDecimalType.INSTANCE)
				.addScalar("dpd", BigDecimalType.INSTANCE)
				.addScalar("bucket", BigDecimalType.INSTANCE)
				.addScalar("unitCode", StringType.INSTANCE)
				.addScalar("reason", StringType.INSTANCE)
				.addScalar("chargeOffFlag", StringType.INSTANCE)
				.addScalar("principleOutstanding", BigDecimalType.INSTANCE)
				.addScalar("overdueCharges", DoubleType.INSTANCE)
				.addScalar("interestOutstanding", StringType.INSTANCE)
				.addScalar("prepaymentPenalty", DoubleType.INSTANCE)
				.addScalar("refunds", DoubleType.INSTANCE)
				.addScalar("interFund", DoubleType.INSTANCE)
				.addScalar("totalAmontPaidByCustomer", BigDecimalType.INSTANCE)
				.addScalar("billedInst", DoubleType.INSTANCE)
				.addScalar("phoneNumber", StringType.INSTANCE)
				.addScalar("payer", StringType.INSTANCE)
				.addScalar("nextApproverLevel", StringType.INSTANCE)
				/*.addScalar("approvalStatus", StringType.INSTANCE)*/
				.setResultTransformer(Transformers.aliasToBean(ContractTerminationDto.class));

		return sqlQueryObject.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public ContractTermination findByTerminationId(Integer contractTerminationId) {
		LOG.debug("fetch Terminated Contract Details");
		String hqlQuery = "FROM ContractTermination where contractTerminationId=:contractTerminationId";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("contractTerminationId", contractTerminationId);
		return (ContractTermination)hqlQueryObject.uniqueResult();
	}
	
	
	@Override
	public void updateTerminatedContractApprover() {
		// TODO Auto-generated method stub

	}

	@Transactional("txnManagerCollections")
	public int updateApproverLevelById(Integer contractTerminationId, String approverLevel){
		try{
		String hqlQuery = "UPDATE ContractTermination SET approverLevel=:approverLevel WHERE contractTerminationId=:contractTerminationId";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("contractTerminationId", contractTerminationId);
		hqlQueryObject.setParameter("approverLevel", approverLevel);
		return hqlQueryObject.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 1;
		}
	}

	@Override
	@Transactional("txnManagerCollections")
	public ContractTermination findByContractTerminationId(Integer contractTerminationId) {
		ContractTermination contractTermination = null;
		String hqlQuery = "FROM ContractTermination where contractTerminationId=:contractTerminationId";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		try{			
			hqlQueryObject.setParameter("contractTerminationId", contractTerminationId);
			contractTermination = (ContractTermination)hqlQueryObject.uniqueResult();
		}catch(Exception e){
			e.printStackTrace();
		}
		return contractTermination;
		
	}

	@Override
	@Transactional("txnManagerCollections")
	public void updateStatus(ContractTerminationBean contractTerminationBean,ContractTermination contractTermination) {
		
		contractTermination.setStatus(contractTerminationBean.getStatus().toUpperCase());				
		getSession().saveOrUpdate(contractTermination);
		
	}
	
	

}
