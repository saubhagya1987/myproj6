package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.dao.CustomerAddressDao;
import vn.com.unit.fe_credit.entity.CustomerAddress;
import vn.com.unit.fe_credit.service.CustomerAddressService;

@Service("customerAddressService")
@Transactional(readOnly = true)
public class CustomerAddressServiceImpl implements CustomerAddressService{
	@Autowired
	CustomerAddressDao customerAddressDao;
	
	public CustomerAddressServiceImpl() {
		super();
	}

	@Override
	public CustomerAddress findById(Long id) {
		return customerAddressDao.find(id);
	}

	@Transactional
	@Override
	public void deleteCustomerAddress(Long id) {
		CustomerAddress customerAddress=findById(id);
		if(customerAddress!=null) 
			customerAddressDao.remove(customerAddress);
	}
	
	@Transactional
	@Override
	public void saveCustomerAddress(CustomerAddress customerAddress) {
		customerAddressDao.save(customerAddress);
	}
	
	@Override
	@Transactional
	public CustomerAddress findByCustomerId(Long customerId) {
		Search search=new Search();
		search.addFilterEqual("customerId", customerId);
		List<CustomerAddress> result =customerAddressDao.search(search);
		if(result.size()>0) return result.get(0);
		return null;
	}
}
