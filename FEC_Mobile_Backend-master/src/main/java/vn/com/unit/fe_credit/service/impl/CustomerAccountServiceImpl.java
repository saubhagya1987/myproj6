package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.CustomerAccountDao;
import vn.com.unit.fe_credit.entity.CustomerAccount;
import vn.com.unit.fe_credit.service.CustomerAccountService;

@Service("customerAccountService")
@Transactional(readOnly = true)
public class CustomerAccountServiceImpl implements CustomerAccountService{
	@Autowired
	CustomerAccountDao customerAccountDao;
	
	public CustomerAccountServiceImpl() {
		super();
	}

	@Override
	public CustomerAccount findById(Long id) {
		return customerAccountDao.find(id);
	}

	@Transactional
	@Override
	public void deleteCustomerAccount(Long id) {
		CustomerAccount customerAccount=findById(id);
		if(customerAccount!=null) 
			customerAccountDao.remove(customerAccount);
	}
	
	@Transactional
	@Override
	public void saveCustomerAccount(CustomerAccount customerAccount) {
		customerAccountDao.save(customerAccount);
		
	}
}
