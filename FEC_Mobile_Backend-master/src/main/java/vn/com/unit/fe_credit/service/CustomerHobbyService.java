package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.NewCustomerHobby;

public interface CustomerHobbyService {

	CustomerHobby findById(Long id);

	void deleteCustomerHobby(Long id);

	void saveCustomerHobby(CustomerHobby customerHobby);

	List<CustomerHobby> findByCustomerId(Long customerId);
	List<Hobby> findHobbyByCustomerId(Long customerId);
	void deleteByCustomer(Long customerId);
}
