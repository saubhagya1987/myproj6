package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.PaymentHistoryBean;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PaymentHistory;

public interface PaymentHistoryService {
	List<PaymentHistory> findAll();

	PaymentHistoryBean search(PaymentHistoryBean bean);

	void savePaymentHistory(PaymentHistory entity);

	PaymentHistory findById(Long paymenthistoryId);
	
	List<PaymentHistory> findByContractDetailID(Long contractdetailID);
	
	
	
	
}
