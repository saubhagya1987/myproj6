package vn.com.unit.fe_credit.service;

import vn.com.unit.fe_credit.entity.CustomerAddress;

public interface CustomerAddressService {

	CustomerAddress findById(Long id);

	void deleteCustomerAddress(Long id);

	void saveCustomerAddress(CustomerAddress customerAddress);

	CustomerAddress findByCustomerId(Long customerId);

}
