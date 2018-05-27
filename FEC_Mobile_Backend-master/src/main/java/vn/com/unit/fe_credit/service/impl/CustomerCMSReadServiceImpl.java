package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.CustomerCMSReadDAO;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.CustomerCMSRead;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.fe_credit.service.CMSService;
import vn.com.unit.fe_credit.service.CustomerCMSReadService;
import vn.com.unit.fe_credit.service.CustomerService;

@Service("CustomerCMSReadService")
@Transactional(readOnly = true)
public class CustomerCMSReadServiceImpl implements CustomerCMSReadService {
	@Autowired
	private CustomerCMSReadDAO customerCMSReadDao;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	CMSService cMSService;
	
	@Autowired
	private UserProfile userProfile;
	
	public CustomerCMSReadServiceImpl() {
		super();
	}

	@Override
	public List<CustomerCMSRead> findAllex() {

		return customerCMSReadDao.findAll();
	}


	@Override
	@Transactional
	public void saveCustomerCMSRead(CustomerCMSRead entity) {
		
		CMSEmtity addEntity=new CMSEmtity();
		addEntity=cMSService.findById(entity.getCmsId());
		if(addEntity!=null){
			entity.setCmsEmtity(addEntity);
		}
		Customer addCustomer = new Customer();
		addCustomer=customerService.findById(entity.getCustomerId());
		if(addCustomer!=null){
			entity.setCustomer(addCustomer);
		}
		if(entity.getCmsEmtity()!=null && entity.getCmsEmtity()!=null){
			customerCMSReadDao.save(entity);
		}
		

	}

	@Override
	public CustomerCMSRead findCustomerIdAndCMSId(Long customerId, Long cmsId) {
		Search search = new Search(CustomerCMSRead.class);
		search.addFilterEqual("cmsEmtity.cmsId", cmsId);
		search.addFilterEqual("customer.customerId", customerId);
		List<CustomerCMSRead> searchResult = customerCMSReadDao.search(search);
		if(searchResult != null && searchResult.size() > 0){
			return searchResult.get(0);
		}
		return null;
	}

}
