package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.Wish;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CustomerWishDao extends GenericDAO<CustomerWish, Long> {

	List<CustomerWish> findByCustomerId(Long customerId);

	List<Wish> findWishByCustomerId(Long customerId);

	void deleteCustomerWish(Long customerId, Long wishId);
}
