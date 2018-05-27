package vn.com.unit.fe_credit.service;

import vn.com.unit.fe_credit.entity.CustomerAccount;

public interface CustomerAccountService {

	CustomerAccount findById(Long id);

	void deleteCustomerAccount(Long id);

	void saveCustomerAccount(CustomerAccount customerAccount);

}
