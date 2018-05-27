package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.NewCustomerHobby;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CustomerHobbyDao extends GenericDAO<CustomerHobby, Long> {

	List<CustomerHobby> findByCustomerId(Long customerId);

	List<Hobby> findHobbyByCustomerId(Long customerId);

	void deleteByCustomer(Long customerId);

}
