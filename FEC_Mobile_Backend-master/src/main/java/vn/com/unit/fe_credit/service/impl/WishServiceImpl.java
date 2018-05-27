package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.CustomerDao;
import vn.com.unit.fe_credit.dao.WishDao;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.Wish;
import vn.com.unit.fe_credit.service.WishService;

@Service("wishService")
@Transactional(readOnly = true)
public class WishServiceImpl implements WishService{
	@Autowired
	WishDao wishDao;
	
	public WishServiceImpl() {
		super();
	}

	@Override
	public Wish findById(Long id) {
		return wishDao.find(id);
	}

	@Transactional
	@Override
	public void deleteWish(Long id) {
		Wish wish=findById(id);
		if(wish!=null) 
			wishDao.remove(wish);
	}
	
	@Transactional
	@Override
	public void saveWish(Wish wish) {
		wishDao.save(wish);
		
	}
}
