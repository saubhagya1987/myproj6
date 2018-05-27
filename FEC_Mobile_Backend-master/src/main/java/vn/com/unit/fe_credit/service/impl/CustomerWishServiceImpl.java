package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.dao.CustomerWishDao;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.CustomerAddress;
import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.Wish;
import vn.com.unit.fe_credit.service.CMSCategoryService;
import vn.com.unit.fe_credit.service.CustomerWishService;

@Service("customerWishService")
@Transactional(readOnly = true)
public class CustomerWishServiceImpl implements CustomerWishService {
	@Autowired
	CustomerWishDao customerWishDao;
	
	@Autowired
	CMSCategoryService cMSCategoryService;

	public CustomerWishServiceImpl() {
		super();
	}

	@Override
	public CustomerWish findById(Long id) {
		return customerWishDao.find(id);
	}

	@Transactional
	@Override
	public void deleteCustomerWish(Long id) {
		CustomerWish customerWish = findById(id);
		if (customerWish != null)
			customerWishDao.remove(customerWish);
	}

	@Transactional
	@Override
	public void saveCustomerWish(CustomerWish customerWish) {
		customerWishDao.save(customerWish);
	}

	@Override
	public List<CustomerWish> findByCustomerId(Long customerId) {
		List<CustomerWish> list = customerWishDao.findByCustomerId(customerId);
		
		for (CustomerWish customerWish : list) {
			String[] listCategory = customerWish.getCategory().split(";");
			List<CMSCategory> lstCMSCategory = cMSCategoryService
					.findById(listCategory);
			String ortherCat = "";
			for (CMSCategory cat : lstCMSCategory) {				
				ortherCat+= cat.getCode() +"; ";
			}
			
			if(StringUtils.isNotEmpty(ortherCat)){
				customerWish.setCategory(ortherCat.substring(0, ortherCat.length() -2));
			}			
		}		
		
		return list;
		
	}

	@Override
	public List<Wish> findWishByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return customerWishDao.findWishByCustomerId(customerId);
	}

	@Override
	public List<CustomerWish> findByCustomerWish(Long customerId, Long wishId) {
		// TODO Auto-generated method stub
		Search search = new Search(CustomerWish.class);
		search.addFilterEqual("customerId", customerId);
		search.addFilterEqual("wishId", wishId);
		List<CustomerWish> result = customerWishDao.search(search);
		return result;
	}
	
	@Override
	public List<CustomerWish> findByWishId(Long wishId) {
		// TODO Auto-generated method stub
		Search search = new Search(CustomerWish.class);		
		search.addFilterEqual("wishId", wishId);
		List<CustomerWish> result = customerWishDao.search(search);
		return result;
	}

	@Transactional
	@Override
	public void deleteCustomerWish(Long customerId, Long wishId) {
		// TODO Auto-generated method stub
		customerWishDao.deleteCustomerWish(customerId, wishId);
	}

	@Override
	public List<CustomerWish> findByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		Search search = new Search(CustomerWish.class);
		search.addFilterEqual("customerId", customerId);
		List<CustomerWish> result = customerWishDao.search(search);
		return result;
	}
}
