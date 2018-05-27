package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.dao.CustomerHobbyDao;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.NewCustomerHobby;
import vn.com.unit.fe_credit.service.CustomerHobbyService;

@Service("customerHobbyService")
@Transactional(readOnly = true)
public class CustomerHobbyServiceImpl implements CustomerHobbyService {
	@Autowired
	CustomerHobbyDao customerHobbyDao;

	public CustomerHobbyServiceImpl() {
		super();
	}

	@Override
	public CustomerHobby findById(Long id) {
		return customerHobbyDao.find(id);
	}

	@Transactional
	@Override
	public void deleteCustomerHobby(Long id) {
		CustomerHobby customerHobby = findById(id);
		if (customerHobby != null)
			customerHobbyDao.remove(customerHobby);
	}

	@Transactional
	@Override
	public void saveCustomerHobby(CustomerHobby customerHobby) {
		customerHobbyDao.save(customerHobby);

	}

	@Override
	@Transactional
	public List<CustomerHobby> findByCustomerId(Long customerId) {
		return customerHobbyDao.findByCustomerId(customerId);
	}

	@Override
	public List<Hobby> findHobbyByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return customerHobbyDao.findHobbyByCustomerId(customerId);
	}

	@Override
	@Transactional
	public void deleteByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		customerHobbyDao.deleteByCustomer(customerId);

	}
}
