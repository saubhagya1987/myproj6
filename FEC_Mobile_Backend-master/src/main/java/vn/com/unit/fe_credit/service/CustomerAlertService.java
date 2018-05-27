package vn.com.unit.fe_credit.service;

import vn.com.unit.fe_credit.entity.CustomerAlert;

public interface CustomerAlertService {

	CustomerAlert findById(Long id);

	void deleteCustomerAlert(Long id);

	void saveCustomerAlert(CustomerAlert customerAlert);

}
