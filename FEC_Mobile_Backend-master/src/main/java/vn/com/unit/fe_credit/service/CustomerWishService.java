package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.Wish;

public interface CustomerWishService {

	CustomerWish findById(Long id);

	void deleteCustomerWish(Long id);

	void saveCustomerWish(CustomerWish customerWish);

	List<CustomerWish> findByCustomerId(Long customerId);
	
	List<CustomerWish> findByCustomer(Long customerId);
	List<CustomerWish> findByCustomerWish(Long customerId, Long wishId);

	List<Wish> findWishByCustomerId(Long customerId);
	void deleteCustomerWish(Long customerId, Long wishId);

	List<CustomerWish> findByWishId(Long wishId);
}
