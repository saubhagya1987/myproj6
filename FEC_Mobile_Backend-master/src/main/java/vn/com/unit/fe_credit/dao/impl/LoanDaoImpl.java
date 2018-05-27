package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.dao.LoanDao;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Repository
public class LoanDaoImpl extends GenericDAOImpl<Loan, Long> implements LoanDao {
	// @Autowired
	// UserProfile userProfile;
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public SearchResult<Loan> search(LoanBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT distinct loan");
		hql.append(" FROM Loan loan ");

		// init filter
		hql.append(" WHERE 1=1 ");

		// build query get list
		Query query = getSession().createQuery(hql.toString());
		// build query get count
		Query countQuery = getSession().createQuery(
				Utils.getCountQuery(hql.toString()));

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = ((Long) countQuery.list().get(0)).intValue();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		List<Loan> lst = query.list();
		SearchResult<Loan> searchResult = new SearchResult<Loan>();
		searchResult.setResult(lst);

		if (bean.getLimit() > 0) {
			searchResult.setTotalCount(totalCount);
		}
		return searchResult;
	}

	@Override
	public List<Loan> RemoveConditionWhenCreate(String category,
			Long loanDetailID) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" select loan.LOANID as loanID , loan.CONDITIONNAME as condition_name  From LOAN loan where  loan.LOANID not in (");
		sql.append(" select ld.LOANID from LOANDETAIL ld ");
		if (loanDetailID != null) {
			sql.append(" where ld.loanDetailID <> :loanDetailID )");
		}else{
			sql.append(" )");
		}
		sql.append(" AND loan.CONDITIONCATEGORY=:category  ");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("loanID", new LongType())
				.addScalar("condition_name", new StringType());

		query.setParameter("category", category.toUpperCase());
		if (loanDetailID != null) {
			query.setParameter("loanDetailID", loanDetailID);
		}
		query.setResultTransformer(Transformers.aliasToBean(Loan.class));

		return query.list();
	}

}
