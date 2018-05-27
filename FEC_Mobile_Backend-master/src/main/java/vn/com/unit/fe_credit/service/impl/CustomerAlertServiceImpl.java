package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.CustomerAlertDao;
import vn.com.unit.fe_credit.entity.CustomerAlert;
import vn.com.unit.fe_credit.service.CustomerAlertService;

@Service("customerAlertService")
@Transactional(readOnly = true)
public class CustomerAlertServiceImpl implements CustomerAlertService{
	@Autowired
	CustomerAlertDao customerAlertDao;
	
	public CustomerAlertServiceImpl() {
		super();
	}

	@Override
	public CustomerAlert findById(Long id) {
		return customerAlertDao.find(id);
	}

	@Transactional
	@Override
	public void deleteCustomerAlert(Long id) {
		CustomerAlert customerAlert=findById(id);
		if(customerAlert!=null) 
			customerAlertDao.remove(customerAlert);
	}
	
	@Transactional
	@Override
	public void saveCustomerAlert(CustomerAlert customerAlert) {
		customerAlertDao.save(customerAlert);
		
	}
}
