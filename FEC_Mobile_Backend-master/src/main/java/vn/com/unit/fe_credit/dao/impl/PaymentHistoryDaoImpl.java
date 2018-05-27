package vn.com.unit.fe_credit.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.PaymentHistoryBean;
import vn.com.unit.fe_credit.dao.PaymentHistoryDao;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PaymentHistory;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.SearchResult;

@Repository
public class PaymentHistoryDaoImpl extends GenericDAOImpl<PaymentHistory, Long> implements PaymentHistoryDao {
	// @Autowired
	// UserProfile userProfile;
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public SearchResult<PaymentHistory> search(PaymentHistoryBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
        hql.append(" SELECT distinct payment");
        hql.append(" FROM PaymentHistory payment ");

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
        
        List<PaymentHistory> lst = query.list();
        SearchResult<PaymentHistory> searchResult = new SearchResult<PaymentHistory>();
        searchResult.setResult(lst);

        if(bean.getLimit() > 0){
            searchResult.setTotalCount(totalCount);
        }
        return searchResult;
	}



	@Override
	public List<PaymentHistory> findByContractDetailID(Long contractdetailID) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT pay.paymenthistoryID as paymenthistoryID, pay.transactionCode as transactionCode, pay.contractNumber as contractNumber,pay.amount as amount, pay.paymentDate as paymentDate, pay.note as note");
		sql.append(" FROM PaymentHistory pay ");
		sql.append(" JOIN ContractDetail cd on cd.contractdetailID=pay.contractdetailID");
		sql.append(" WHERE cd.contractdetailID = :contractdetailID");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("paymenthistoryID", new LongType())
				.addScalar("transactionCode", new StringType())
				.addScalar("contractNumber", new StringType())
				.addScalar("amount", new DoubleType())
				.addScalar("paymentDate", new TimestampType())
				.addScalar("note", new StringType());
		query.setParameter("contractdetailID", contractdetailID);
		query.setResultTransformer(Transformers
				.aliasToBean(PaymentHistory.class));

		return query.list();
	}
	
	

}
