package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.v1.BidderDto;
import vn.com.unit.fe_credit.dao.v1.BidderDao;
import vn.com.unit.fe_credit.entity.collection.Bidder;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

@Repository
public class BidderDaoImpl extends HibernateBaseDAO implements BidderDao{
	private static final Logger LOG = LoggerFactory.getLogger(BidderDaoImpl.class);
//	private static final String ADMIN_ROLE = "R001";
//	private static final String FS_COLLECTOR_ADMIN_ROLE = "FS";
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<BidderDto> findAll() {
		StringBuilder sqlQuery = new StringBuilder();
		/*sqlQuery.append("SELECT bd.ID as id, bd.AMOUNT as amount, "				
				+ "bd.APPROVED_DATE as approvedDate, "			
				+ "bd.BIDDER_NAME as bidderName, "				
				+ "bd.ENTRY_DATE as entryDate, "
				+ "bd.ID_NUMBER as idNumber, "
				+ "bd.PHONE_NUMBER as phoneNumber, "
				+ "bd.REPOSSESSION_ID as  repossessionId, "
				+ "bd.REQUEST_DATE as requestDate, "
				+ "bd.USER_APPROVE as userApprove, "
				+ "bd.USER_REQUEST as userRequest, "
				+ "bd.STATUS as status, "
				+ "bd.APPL_ID as applId, "				
				+ "rapl.ACTION_CODE as approvalStatus "					
				+ "FROM BIDDER bd "
				+ "LEFT JOIN REPOSSESSION_APPROVAL rapl ON bd.ID = rapl.REPOSSESSION_ID");*/
		sqlQuery.append("SELECT rapr.REPOSSESSION_ID as id, bd.AMOUNT as amount, "				
				+ "bd.APPROVED_DATE as approvedDate, "			
				+ "bd.BIDDER_NAME as bidderName, "				
				+ "bd.ENTRY_DATE as entryDate, "
				+ "bd.ID_NUMBER as idNumber, "
				+ "bd.PHONE_NUMBER as phoneNumber, "
				+ "bd.REPOSSESSION_ID as  repossessionId, "
				+ "bd.REQUEST_DATE as requestDate, "
				+ "bd.USER_APPROVE as userApprove, "
				+ "bd.USER_REQUEST as userRequest, "
				+ "bd.STATUS as status, "
				+ "bd.APPL_ID as applId "							
				+ "FROM REPOSSESSION_APPROVER rapr "
				+ "INNER JOIN BIDDER bd ON rapr.REPOSSESSION_ID = bd.ID "	);

		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", LongType.INSTANCE)
				.addScalar("amount", BigDecimalType.INSTANCE)
				.addScalar("approvedDate", TimestampType.INSTANCE)
				.addScalar("bidderName", StringType.INSTANCE)
				.addScalar("entryDate", TimestampType.INSTANCE)				
				.addScalar("idNumber", StringType.INSTANCE)
				.addScalar("phoneNumber", StringType.INSTANCE)
				.addScalar("repossessionId", IntegerType.INSTANCE)
				.addScalar("requestDate", TimestampType.INSTANCE)
				.addScalar("userApprove", StringType.INSTANCE)
				.addScalar("userRequest", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("applId", StringType.INSTANCE)
//				.addScalar("approvalStatus", StringType.INSTANCE)							
				.setResultTransformer(Transformers.aliasToBean(BidderDto.class));
		return sqlQueryObject.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<BidderDto> getBidderList(String email, String contractId) {
		LOG.info("Getting Bidder List");
		try{
		StringBuilder sqlQuery = new StringBuilder();
		sqlQuery.append("SELECT DISTINCT rapr.BIDDER_ID, rapr.BIDDER_ID as id, bd.AMOUNT as amount, "				
				+ "bd.APPROVED_DATE as approvedDate, "			
				+ "bd.BIDDER_NAME as bidderName, "				
				+ "bd.ENTRY_DATE as entryDate, "
				+ "bd.ID_NUMBER as idNumber, "
				+ "bd.PHONE_NUMBER as phoneNumber, "
				+ "bd.REPOSSESSION_ID as  repossessionId, "
				+ "bd.REQUEST_DATE as requestDate, "
				+ "bd.USER_APPROVE as userApprove, "
				+ "bd.USER_REQUEST as userRequest, "
				+ "bd.STATUS as status, "
				+ "bd.APPL_ID as applId "							
				+ "FROM REPOSSESSION_APPROVER rapr "
				+ "INNER JOIN BIDDER bd ON rapr.REPOSSESSION_ID = bd.REPOSSESSION_ID "				
				+ " WHERE APPROVER_EMAIL_ID = '" + email + "'  AND bd.APPL_ID = '"+ contractId+"'");
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", LongType.INSTANCE)
				.addScalar("amount", BigDecimalType.INSTANCE)
				.addScalar("approvedDate", TimestampType.INSTANCE)
				.addScalar("bidderName", StringType.INSTANCE)
				.addScalar("entryDate", TimestampType.INSTANCE)				
				.addScalar("idNumber", StringType.INSTANCE)
				.addScalar("phoneNumber", StringType.INSTANCE)
				.addScalar("repossessionId", IntegerType.INSTANCE)
				.addScalar("requestDate", TimestampType.INSTANCE)
				.addScalar("userApprove", StringType.INSTANCE)
				.addScalar("userRequest", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("applId", StringType.INSTANCE)
				.setResultTransformer(Transformers.aliasToBean(BidderDto.class));
		return sqlQueryObject.list();
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<BidderDto> getBidderResult(String email/*, boolean isAdmin*/) {
		/*	String approvalLevel=ADMIN_ROLE;
		String approvalLevelAdminRole=FS_COLLECTOR_ADMIN_ROLE;
		if(isAdmin && (approvalLevel.equalsIgnoreCase(ADMIN_ROLE))){
			approvalLevel=ADMIN_ROLE;
		}
		else if(isAdmin && (approvalLevelAdminRole.equalsIgnoreCase(FS_COLLECTOR_ADMIN_ROLE))){
			approvalLevelAdminRole=FS_COLLECTOR_ADMIN_ROLE;
		}*/
		try{
			StringBuilder sqlQuery = new StringBuilder();
		
		sqlQuery.append("SELECT bd.ID as id, bd.AMOUNT as amount, "				
				+ "bd.APPROVED_DATE as approvedDate, "			
				+ "bd.BIDDER_NAME as bidderName, "				
				+ "bd.ENTRY_DATE as entryDate, "
				+ "bd.ID_NUMBER as idNumber, "
				+ "bd.PHONE_NUMBER as phoneNumber, "
				+ "bd.REPOSSESSION_ID as  repossessionId, "
				+ "bd.REQUEST_DATE as requestDate, "
				+ "bd.USER_APPROVE as userApprove, "
				+ "bd.USER_REQUEST as userRequest, "
				+ "bd.STATUS as status, "
				+ "bd.APPL_ID as applId, "				
				+ "rapl.ACTION_CODE as approvalStatus "
				+ "FROM BIDDER bd "				
				+ "LEFT JOIN REPOSSESSION_APPROVAL rapl ON bd.ID = rapl.REPOSSESSION_ID WHERE bd.USER_APPROVE = '" + email + "'");
		       /* + " OR APPROVER_LEVEL = '" + approvalLevel + "'"
		        + " OR APPROVER_LEVEL = '" + approvalLevelAdminRole + "'");*/
		
		Query sqlQueryObject = getSession().createSQLQuery(sqlQuery.toString())
				.addScalar("id", LongType.INSTANCE)
				.addScalar("amount", BigDecimalType.INSTANCE)
				.addScalar("approvedDate", TimestampType.INSTANCE)
				.addScalar("bidderName", StringType.INSTANCE)
				.addScalar("entryDate", TimestampType.INSTANCE)				
				.addScalar("idNumber", StringType.INSTANCE)
				.addScalar("phoneNumber", StringType.INSTANCE)
				.addScalar("repossessionId", IntegerType.INSTANCE)
				.addScalar("requestDate", TimestampType.INSTANCE)
				.addScalar("userApprove", StringType.INSTANCE)
				.addScalar("userRequest", StringType.INSTANCE)
				.addScalar("status", StringType.INSTANCE)
				.addScalar("applId", StringType.INSTANCE)
				.addScalar("approvalStatus", StringType.INSTANCE)	
				.setResultTransformer(Transformers.aliasToBean(BidderDto.class));

		return sqlQueryObject.list();}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	
	
	@Override
	@Transactional("txnManagerCollections")
	public Bidder findByBidderId(Long id) {
		LOG.info("Getting Bidder by Bidder Id");
		String hqlQuery = "FROM Bidder where id=:id";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("id", id);
		return (Bidder)hqlQueryObject.uniqueResult();
	}
	/*@Override
	public BidderApproval updateApproval(Bidder bidder, BidderBean bidderBean) {
		// TODO Auto-generated method stub
		return null;
	}*/
	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.dao.v1.BidderDao#getBidderByContractId(java.lang.String, java.lang.Long)
	 */
	@Override
	@Transactional("txnManagerCollections")
	public List<Bidder> getBidderByContractId(String contractId, Long repossessionId) {
		LOG.info("Getting Bidder by contract Id");
		String hqlQuery = "FROM Bidder bid WHERE repossessionId=:id AND applId = :contractId";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("id", repossessionId);
		hqlQueryObject.setParameter("contractId", contractId);
		return hqlQueryObject.list();
	}
	
	

}
