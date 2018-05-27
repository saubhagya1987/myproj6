package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.bean.PaymentHistoryBean;
import vn.com.unit.fe_credit.entity.PaymentHistory;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;

public interface PaymentHistoryDao extends GenericDAO<PaymentHistory, Long> {

	SearchResult<PaymentHistory> search(PaymentHistoryBean bean);
	
	List<PaymentHistory> findByContractDetailID(Long contractdetailID);
	
}
