package vn.com.unit.fe_credit.dao;

import java.util.Date;
import java.util.List;

import vn.com.unit.fe_credit.bean.CustomerBean;
import vn.com.unit.fe_credit.entity.Customer;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CustomerDao extends GenericDAO<Customer, Long> {

	CustomerBean search(CustomerBean bean);

	Customer findbyCustomerId(String customerId);

	void deleteCustomerImport(Date date);

	List<Customer> cuontDeleteCustomerImport(Date date);

	int updateOTPResetPasswordAttempts(long customerId, int otpAttempts, Date bannedDueDate);

	int resetRequestOTPAttempts(long customerId);

}
