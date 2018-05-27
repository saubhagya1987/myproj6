package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.dao.ApplyNowDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.ApplyNow;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class ApplyNowDaoImpl extends GenericDAOImpl<ApplyNow, Long> implements
		ApplyNowDao {
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Object[]> search(ApplyNowBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT acc.applyNowID AS  Id,acc.fullName AS fullName,acc.cellPhone AS cellPhone,acc.exportedBy AS exportedBy,acc.exportedDate AS exportedDate,acc.submittedDate AS submittedDate,acc.status AS status ");
		hql.append(" FROM APPLYNOW acc ");

		hql.append(" WHERE 1=1 ");
		if (bean.getSubmitedFrom() != null && bean.getSubmitedTo() != null) {
			hql.append(" AND acc.submittedDate >= :SubmitedFrom AND acc.submittedDate <= :SubmitedTo");
		} else {
			if (bean.getSubmitedFrom() != null) {
				hql.append(" AND acc.submittedDate >= :SubmitedFrom");
			}
			if (bean.getSubmitedTo() != null) {
				hql.append(" AND acc.submittedDate <= :SubmitedTo");
			}
		}
		if (bean.getApplyNowID() != null) {
			hql.append(" AND acc.applyNowID= :applyNowID");
		}
		if (bean.getStatus() != 0) {
			hql.append(" AND acc.status= :status");
		}
		if (StringUtils.isNotEmpty(bean.getDir())) {
			hql.append(" ORDER BY acc." + bean.getDir().toString() + " "
					+ bean.getSort().toString());

		} else {
			hql.append(" ORDER BY acc.applyNowID desc");
		}
		//System.out.println("Apply Now search: " + hql);
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("Id", new LongType())
				.addScalar("fullName", new StringType())
				.addScalar("cellPhone", new StringType())
				.addScalar("exportedBy", new StringType())
				.addScalar("exportedDate", new TimestampType())
				.addScalar("submittedDate", new TimestampType())
				.addScalar("status", new LongType());
		
		if (bean.getApplyNowID() != null) {
			query.setParameter("applyNowID", bean.getApplyNowID());
		}
		if (bean.getStatus() != 0) {
			query.setParameter("status", bean.getStatus().toString());
		}
		if (bean.getSubmitedFrom() != null) {
			query.setParameter("SubmitedFrom", Utils.setTimeToZero(bean.getSubmitedFrom()));
		}
		if (bean.getSubmitedTo() != null) {
			query.setParameter("SubmitedTo", Utils.setTimeToMax(bean.getSubmitedTo()));
		}
		if (bean.getLimit() > 0) {
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		@SuppressWarnings("unchecked")
		List<Object[]> lst = query.list();

		return lst;
	}

	@Override
	public Integer countSearch(ApplyNowBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*) FROM (");
		hql.append(" SELECT acc.applyNowID AS  Id,acc.fullName AS fullName,acc.cellPhone AS cellPhone,acc.exportedBy AS exportedBy,acc.exportedDate AS exportedDate,acc.submittedDate AS submittedDate,acc.status AS status ");
		hql.append(" FROM APPLYNOW acc ");

		hql.append(" WHERE 1=1 ");
		if (bean.getSubmitedFrom() != null && bean.getSubmitedTo() != null) {
			hql.append(" AND acc.submittedDate >= :SubmitedFrom AND acc.submittedDate <= :SubmitedTo");
		} else {
			if (bean.getSubmitedFrom() != null) {
				hql.append(" AND acc.submittedDate >= :SubmitedFrom");
			}
			if (bean.getSubmitedTo() != null) {
				hql.append(" AND acc.submittedDate <= :SubmitedTo");
			}
		}
		if (bean.getApplyNowID() != null) {
			hql.append(" AND acc.applyNowID= :applyNowID");
		}
		if (bean.getStatus() != 0) {
			hql.append(" AND acc.status= :status");
		}
		hql.append(" ) ");
		Query query = getSession().createSQLQuery(hql.toString());
		if (bean.getApplyNowID() != null) {
			query.setParameter("applyNowID", bean.getApplyNowID());
		}
		if (bean.getStatus() != 0) {
			query.setParameter("status", bean.getStatus().toString());
		}
		if (bean.getSubmitedFrom() != null) {
			query.setParameter("SubmitedFrom", Utils.setTimeToZero(bean.getSubmitedFrom()));
		}
		if (bean.getSubmitedTo() != null) {
			query.setParameter("SubmitedTo", Utils.setTimeToMax(bean.getSubmitedTo()));
		}

		query.uniqueResult().toString();
		Integer a = 0;
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;

	}

}
