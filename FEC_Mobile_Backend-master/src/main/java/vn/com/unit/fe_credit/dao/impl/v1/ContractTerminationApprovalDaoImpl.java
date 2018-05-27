package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.Date;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;
import vn.com.unit.fe_credit.dao.v1.ContractTerminationApprovalDao;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;
import vn.com.unit.fe_credit.entity.collection.ContractTerminationApproval;
import vn.com.unit.fe_credit.entity.collection.ContractTerminationApprover;

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;
@Repository
public class ContractTerminationApprovalDaoImpl extends HibernateBaseDAO implements ContractTerminationApprovalDao{
	private static final Logger LOG = LoggerFactory.getLogger(ContractTerminationApprovalDaoImpl.class);
	@Autowired
	UserProfile userProfile;
	
	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
		
	}

	@Override
	@Transactional("txnManagerCollections")
	public ContractTerminationApproval updateApproval(ContractTermination contractTermination, ContractTerminationBean contractTerminationBean) {
		LOG.info("Updating Contract Termination Approval");
		ContractTerminationApproval terminationApproval = findByContractId(contractTerminationBean.getContractId());
		if(terminationApproval==null){
			terminationApproval=new ContractTerminationApproval();
		}
		terminationApproval.setContractId(contractTermination.getContractId());
		terminationApproval.setCustomerName(contractTermination.getCustomerName());
		terminationApproval.setCustomerId(Integer.parseInt(contractTermination.getCustomerId()));
		terminationApproval.setProduct(contractTermination.getProduct());
		terminationApproval.setDpd(contractTermination.getDpd().toString());
		terminationApproval.setActionCode(contractTerminationBean.getStatus().toUpperCase());
		terminationApproval.setTerminationId(contractTermination.getContractTerminationId());
		terminationApproval.setUserRequest(contractTermination.getUpdatedBy());
		terminationApproval.setRequestDate(new Date());
		terminationApproval.setUserApprove(userProfile.getAccount().getEmail());
		terminationApproval.setApproveDate(new Date());
		terminationApproval.setRemarks(contractTerminationBean.getRemark());
		terminationApproval.setEntryDate(new Date());
		terminationApproval.setStep(contractTermination.getStep());
		getSession().saveOrUpdate(terminationApproval);
		return terminationApproval;
		
		
		
		
	}
	@Transactional("txnManagerCollections")
	@Override
	public ContractTerminationApproval findByContractId(String contractId) {		
		String hqlQuery = "FROM ContractTerminationApproval where contractId=:contractId";
		Query hqlQueryObject = getSession().createQuery(hqlQuery);
		hqlQueryObject.setParameter("contractId", contractId);
		return (ContractTerminationApproval)hqlQueryObject.uniqueResult();
	}

}
