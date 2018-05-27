package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.bean.v1.RepossessionBean;
import vn.com.unit.fe_credit.dao.v1.RepossessionApprovalDao;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;
import vn.com.unit.fe_credit.entity.collection.Repossession;
import vn.com.unit.fe_credit.entity.collection.RepossessionApproval;
import vn.com.unit.fe_credit.service.v1.ContractAttachmentService;
import vn.com.unit.utils.RepossessionAndBidderStatus;
@Repository
public class RepossessionApprovalDaoImpl extends HibernateBaseDAO implements RepossessionApprovalDao{
	private static final Logger LOG = LoggerFactory.getLogger(RepossessionApprovalDaoImpl.class);

	@Autowired
	UserProfile userProfile;
	
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}

	@Override
	@Transactional("txnManagerCollections")
	public RepossessionApproval updateApproval(Repossession repossession ,RepossessionBean repossessionBean, String stepNo, String role) {
		LOG.debug("update approval status ");
		RepossessionApproval approval =new RepossessionApproval();
//		}
		approval.setContractId(repossession.getAPPL_ID());
		approval.setCustomerName(repossession.getCustFullName());
		
		  approval.setCustomerId(repossession.getCustomerId());
		
		approval.setProduct(repossession.getProduct());
		approval.setDpd(repossession.getDpd().toString());
		
		if(RepossessionAndBidderStatus.APPROVED.name().equalsIgnoreCase(repossessionBean.getStatus())){
			approval.setActionCode(RepossessionAndBidderStatus.APPROVED.name());
			
		}
		else
			approval.setActionCode(RepossessionAndBidderStatus.REJECT.name());
		
		approval.setRepossessionId(repossessionBean.getId());
		approval.setUserRequest(repossession.getUpdatedBy());
		approval.setRequestDate(new Date());
		approval.setUserApprove(userProfile.getAccount().getEmail());
		approval.setApproveDate(new Date());
		approval.setRemarks(repossessionBean.getRemark());
		approval.setEntryDate(new Date());
		approval.setStep(repossession.getStep());
		
		approval.setStep(stepNo);
		approval.setRole(role);
		getSession().saveOrUpdate(approval);
		
		
		return approval;
		
		
	}
	@Transactional("txnManagerCollections")
	@Override
	public RepossessionApproval findByContractId(String contractId) {
		LOG.debug("Getting reposssession approval by contract id");
		String hqlQuery = "FROM RepossessionApproval where contractId=:contractId";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("contractId", contractId);
		return (RepossessionApproval)hqlQueryObject.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.dao.v1.RepossessionApprovalDao#save(vn.com.unit.fe_credit.entity.collection.RepossessionApproval)
	 */
	@Override
	@Transactional("txnManagerCollections")
	public void save(RepossessionApproval approval) {
		LOG.debug("Saving reposssession approval");
		getSession().saveOrUpdate(approval);
		
	}

}
