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
import vn.com.unit.fe_credit.dao.v1.RepossessionApproverDao;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;

@Repository
public class RepossessionApproverDaoImpl extends HibernateBaseDAO implements RepossessionApproverDao {
	private static final Logger LOG = LoggerFactory.getLogger(RepossessionApproverDaoImpl.class);

	@Autowired
	UserProfile userProfile;

	@Override
	@Autowired
	public void setSessionFactory(@Qualifier("sessionFactoryCollections") SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionApprover> findAll() {
		LOG.info("fetching data from Repossession Approver");
		String hql = "from RepossessionApprover";
		Query query = getSession().createQuery(hql);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionApprover> findByApproverEmail(String approverEmailId) {
		LOG.info("fetching data from Repossession Approver by approver mail id");
		List<RepossessionApprover> repossessionApprovers = new ArrayList<RepossessionApprover>();

		String hql = "from RepossessionApprover a where a.approverEmailId = :approverEmailId";
		Query query = getSession().createQuery(hql);

		try {
			query.setParameter("approverEmailId", approverEmailId);
			repossessionApprovers = query.list();
		} catch (Exception exp) {
			LOG.error("Error fetching data from Repossession Approver" + ExceptionUtils.getStackTrace(exp));
			exp.printStackTrace();
		}
		return repossessionApprovers;
	}

	@Override
	@Transactional("txnManagerCollections")
	public Integer deleteByRepossessionId(Integer repossesionId) {
		LOG.debug("Delete by Repossession id");
		try {
			String hqlQuery = "DELETE FROM RepossessionApprover rap WHERE rap.repossesionId =:repossesionId";
			Query hqlQueryObject = getSession().createQuery(hqlQuery);
			hqlQueryObject.setParameter("repossesionId", repossesionId.longValue());
			/*List objs = hqlQueryObject.list();
			for (Object obj : objs) {
				getSession().delete(obj);
			}*/
			return hqlQueryObject.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error deleting repossession from approver table "+  ExceptionUtils.getStackTrace(e));
			
			return 0;
		}
	}

	@Override
	@Transactional("txnManagerCollections")
	public void updateApproverEmails(Integer repossessionId, String contractId, List<String> approverEmailIds,
			RepossessionApprover approver, Role role) {
		try {
		int batch = 20;
		int count = 1;
		// int step =Integer.parseInt(approver.getStep())+1;
		for (String approverEmail : approverEmailIds) {
			System.out.println("update approval mail id's"+approverEmail);
			RepossessionApprover repossessionApprover = new RepossessionApprover();
			repossessionApprover.setRepossesionId( repossessionId.longValue());
			repossessionApprover.setContractId(contractId);
			repossessionApprover.setAssignedDate(new Date());
			repossessionApprover.setAssignedBy(userProfile.getAccount().getEmail());
			repossessionApprover.setApproverEmailId(approverEmail);
			repossessionApprover.setStep("");
			repossessionApprover.setRole(role.getCode());
			LOG.debug("update approval mail id's");
			
				getSession().saveOrUpdate(repossessionApprover);

			

			if (count % batch == 0) {
				getSession().flush();
				getSession().clear();
			}
			count++;
		}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("error updating approval mail id's "+  ExceptionUtils.getStackTrace(e));
		}
	}

	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionApprover> findByRepossessionId(Integer repossesionId) {
		List<RepossessionApprover> repossessionApprovers = new ArrayList<RepossessionApprover>();

		String hql = "from RepossessionApprover a where a.repossesionId = :repossesionId";
		Query query = getSession().createQuery(hql);
		LOG.debug("fetching repossession list ");
		try {
			query.setParameter("repossesionId", repossesionId);
			repossessionApprovers = query.list();
		} catch (Exception exp) {
			LOG.error("error while fetching repossession list "+  ExceptionUtils.getStackTrace(exp));
		}
		return repossessionApprovers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see vn.com.unit.fe_credit.dao.v1.RepossessionApproverDao#
	 * findByRepossessionAndBidderId(java.lang.Long, java.lang.Integer)
	 */
	@Override
	public RepossessionApprover findByRepossessionAndBidderId(Long repossesionId, String bidderId, String email) {
		String hql = "from RepossessionApprover a where a.repossesionId = :repossesionId and a.approverEmailId =:email and a.bidderId ";
		if(bidderId != null)
			hql = hql +"=:bidderId" ;
		else
			hql = hql + " is null";
		
		Query query = getSession().createQuery(hql);
		LOG.debug("fetching data based on repossession and bidder id ");
		try {
			query.setParameter("repossesionId", repossesionId);
			if(bidderId != null)
				query.setParameter("bidderId", bidderId);
			query.setParameter("email", email);
			return (RepossessionApprover) query.uniqueResult();
		} catch (Exception exp) {
			LOG.error("error while fetching data based on repossession and bidder id  "+  ExceptionUtils.getStackTrace(exp));
			throw new RuntimeException(exp);
		}
	}
	@Transactional("txnManagerCollections")
	public int deleteByRepossessionAndBidderId(Long repossesionId, String bidderId) {
		String hql = "DELETE FROM RepossessionApprover a WHERE a.repossesionId = :repossesionId and a.bidderId " ;
				if(bidderId == null)
					hql = hql + "is null";
				else
					hql = hql +"= :bidderId";
		Query query = getSession().createQuery(hql);
		LOG.debug("deleting data based on repossession and bidder id ");
		try {
			query.setParameter("repossesionId", repossesionId);
			
			if(bidderId != null)
				query.setParameter("bidderId", bidderId);
			
			return query.executeUpdate();
		} catch (Exception exp) {
			LOG.error("error while deleting data based on repossession and bidder id  "+  ExceptionUtils.getStackTrace(exp));
			throw new RuntimeException(exp);
		}
	}
	public void save(List<RepossessionApprover> approvers) {
		LOG.debug("saving data in approver table ");
		try {
			for (RepossessionApprover app : approvers) {
				getSession().save(app);
			}
		} catch (Exception exp) {
			LOG.error("error while saving data in approver table  "+  ExceptionUtils.getStackTrace(exp));
			throw new RuntimeException(exp);
		}
	}
}
