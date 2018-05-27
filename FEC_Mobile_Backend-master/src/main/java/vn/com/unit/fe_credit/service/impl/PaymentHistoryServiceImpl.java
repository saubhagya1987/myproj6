package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.bean.PaymentHistoryBean;
import vn.com.unit.fe_credit.dao.PaymentHistoryDao;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.PaymentHistory;
import vn.com.unit.fe_credit.service.PaymentHistoryService;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("paymentHistoryService")
@Transactional(readOnly = true)
public class PaymentHistoryServiceImpl implements PaymentHistoryService {
	@Autowired
	private PaymentHistoryDao paymentHistoryDao;

	public PaymentHistoryServiceImpl() {
		super();
	}

	@Override
	public List<PaymentHistory> findAll() {
		// TODO Auto-generated method stub
		return paymentHistoryDao.findAll();
	}

	@Override
	public PaymentHistoryBean search(PaymentHistoryBean bean) {
		// TODO Auto-generated method stub
		Search search = new Search(PaymentHistory.class);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<PaymentHistory> searchResult = paymentHistoryDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void savePaymentHistory(PaymentHistory entity) {
		paymentHistoryDao.save(entity);
	}

	@Override
	public PaymentHistory findById(Long loanId) {
		// TODO Auto-generated method stub
		return paymentHistoryDao.find(loanId);
	}

	@Override
	public List<PaymentHistory> findByContractDetailID(Long contractdetailID) {
		// TODO Auto-generated method stub
		return paymentHistoryDao.findByContractDetailID(contractdetailID);
	}

}
