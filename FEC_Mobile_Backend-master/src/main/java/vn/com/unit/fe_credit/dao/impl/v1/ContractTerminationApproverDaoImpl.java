package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
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
import vn.com.unit.fe_credit.dao.v1.ContractTerminationApproverDao;
import vn.com.unit.fe_credit.entity.collection.ContractTerminationApprover;

@Repository
public class ContractTerminationApproverDaoImpl extends HibernateBaseDAO
		implements ContractTerminationApproverDao {
	private static final Logger LOG = LoggerFactory.getLogger(ContractTerminationApproverDaoImpl.class);
	@Autowired
	UserProfile userProfile;

	@Override
	@Autowired
	public void setSessionFactory(
			@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationApprover> findAll() {

		String hql = "from ContractTerminationApprover";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<ContractTerminationApprover> findByApproverEmail(
			String approverEmailId) {

		List<ContractTerminationApprover> contractTerminationApprovers = new ArrayList<>();

		String hql = "from ContractTerminationApprover a where a.approverEmailId = :approverEmailId";
		Query query = getSession().createQuery(hql);
		LOG.debug("fetching data from contract termination approver table");
		try {
			query.setParameter("approverEmailId", approverEmailId);
			contractTerminationApprovers = query.list();
		} catch (Exception exp) {
			LOG.error("Error occured while fetching data from contract termination approver - " + ExceptionUtils.getStackTrace(exp));
		}
		return contractTerminationApprovers;
	}

	@Override
	 @Transactional("txnManagerCollections")
	public Integer deleteByTerminationId(Integer contractTerminationId) {
		LOG.debug("Deleting data from contract termination approver table");
		try {
			String hqlQuery = "DELETE FROM ContractTerminationApprover ctap WHERE ctap.contractTerminationId =:contractTerminationId";
			Query hqlQueryObject = getSession().createQuery(hqlQuery);
			hqlQueryObject.setParameter("contractTerminationId",
					contractTerminationId);
			return hqlQueryObject.executeUpdate();
		} catch (Exception e) {
			LOG.error("Error occured  during Contract termination deletion - " + ExceptionUtils.getStackTrace(e));			
			return 0;
		}
	}

	@Override
	@Transactional("txnManagerCollections")
	public void updateApproverEmails(Integer contractTerminationId,
			String contractId, List<String> approverEmailIds) {
   try{
		int batch = 20;
		int count = 1;

		for (String approverEmail : approverEmailIds) {

			ContractTerminationApprover terminationApprover = new ContractTerminationApprover();
			terminationApprover.setContractTerminationId(contractTerminationId);
			terminationApprover.setContractId(contractId);
			terminationApprover.setAssignedDate(new Date());
			terminationApprover.setAssignedBy(userProfile.getAccount()
					.getEmail());
			terminationApprover.setApproverEmailId(approverEmail);
			getSession().saveOrUpdate(terminationApprover);

			if (count % batch == 0) {
				getSession().flush();
				getSession().clear();
			}
			count++;
		}
	
	}
	catch(Exception e){
		e.printStackTrace();
	}
	}
}
