package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.CustomerHobbyHistoryDao;
import vn.com.unit.fe_credit.entity.CustomerHobbyHistory;
import vn.com.unit.fe_credit.service.CustomerHobbyHistoryService;

@Service("customerHobbyHistoryService")
@Transactional(readOnly = true)
public class CustomerHobbyHistoryServiceImpl implements CustomerHobbyHistoryService {
	@Autowired
	CustomerHobbyHistoryDao historyDao;
	public CustomerHobbyHistoryServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public void save(CustomerHobbyHistory customerHobbyHistory) {
		historyDao.save(customerHobbyHistory);
		
	}

}
