package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.entity.CustomerCMSRead;
import vn.com.unit.fe_credit.entity.MasterdataDetal;

public interface CustomerCMSReadService {
	List<CustomerCMSRead> findAllex();

	CustomerCMSRead findCustomerIdAndCMSId(Long customerId, Long cmsId);
	void saveCustomerCMSRead(CustomerCMSRead entity);

}
