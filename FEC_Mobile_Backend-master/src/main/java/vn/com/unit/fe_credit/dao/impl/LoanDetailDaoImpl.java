package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.LoanDetailBean;
import vn.com.unit.fe_credit.dao.LoanDetailDao;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.SearchResult;

@Repository
public class LoanDetailDaoImpl extends GenericDAOImpl<LoanDetail, Long>
		implements LoanDetailDao {
	// @Autowired
	// UserProfile userProfile;
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void updateCategoryAndNameByLoanID(Long loanID, String category,
			String name) {
		// TODO Auto-generated method stub
		String sql = "UPDATE Loan "
				+ " set CONDITIONCATEGORY=:category, CONDITIONNAME=:name "
				+ " WHERE loanID=:loanID";
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("loanID", loanID);
		query.setParameter("category", category);
		query.setParameter("name", name);
		query.executeUpdate();
	}

	@Override
	public SearchResult<LoanDetail> search(LoanDetailBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT distinct loanDetail");
		hql.append(" FROM LoanDetail loanDetail ");

		// init filter
		hql.append(" WHERE 1=1 ");

		// build query get list
		Query query = getSession().createQuery(hql.toString());

		List<LoanDetail> lst = query.list();
		SearchResult<LoanDetail> searchResult = new SearchResult<LoanDetail>();
		searchResult.setResult(lst);

		return searchResult;
	}

}
