package vn.com.unit.fe_credit.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.fe_credit.bean.CustomerBean;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.ViewCustomer;

public interface CustomerService {

	Customer findById(Long id);

	ViewCustomer findViewById(Long id);

	void deleteCustomer(Long id);

	void deleteCustomerImport(Date date);

	void saveCustomerAccount(Customer customer);

	CustomerBean search(CustomerBean bean);

	void save(CustomerBean bean);

	void saveCustomers(List<Customer> customer) throws Exception;

	void update(Customer customer);

	Customer findByIdCardNumber(String idCardNumber);

	Customer findbyCustomerId(String customerId);

	Customer findByIdCellPhone(String cellPhone);

	Customer findByIdCardNum(String idCardNum, String smsCode, String password, String cellPhone);

	Customer findByCustomerLogin(String idCardNum, String password);

	Customer customerIDtoAccountID(String customerContractId);

	void markAsCustomerHasBeenInstalledTheMobileApp(String idCardNumber, String cellphone);

	int updateOTPResetPasswordAttempts(long customerId, int otpAttempts, Date bannedDueDate) throws Exception;

	int resetRequestOTPAttempts(long customerId);

}
