package vn.com.unit.fe_credit.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.ContractDetailBean;
import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.dao.ContractDetailDao;
import vn.com.unit.fe_credit.dao.LoanDao;
import vn.com.unit.fe_credit.entity.ContractDetail;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Repository
public class ContractDetailDaoImpl extends GenericDAOImpl<ContractDetail, Long> implements ContractDetailDao {
	// @Autowired
	// UserProfile userProfile;
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public SearchResult<ContractDetail> search(ContractDetailBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
        hql.append(" SELECT distinct contract");
        hql.append(" FROM Contract contract ");

        // init filter
        hql.append(" WHERE 1=1 ");
  
        // build query get list
        Query query = getSession().createQuery(hql.toString());
        // build query get count
        Query countQuery = getSession().createQuery(Utils.getCountQuery(hql.toString()));

      
        int totalCount = 0;
        if(bean.getLimit() > 0){
            totalCount = ((Long) countQuery.list().get(0)).intValue();
            query.setMaxResults(bean.getLimit());
            query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
        }
        
        List<ContractDetail> lst = query.list();
        SearchResult<ContractDetail> searchResult = new SearchResult<ContractDetail>();
        searchResult.setResult(lst);

        if(bean.getLimit() > 0){
            searchResult.setTotalCount(totalCount);
        }
        return searchResult;
	}

}
