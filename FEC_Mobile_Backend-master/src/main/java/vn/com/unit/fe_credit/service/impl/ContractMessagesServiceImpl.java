package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.ContractMessagesDao;
import vn.com.unit.fe_credit.entity.ContractMessages;
import vn.com.unit.fe_credit.service.ContractMessagesService;

@Service("contractMessagesService")
@Transactional(readOnly = true)
public class ContractMessagesServiceImpl implements ContractMessagesService{
	@Autowired
	ContractMessagesDao contractMessagesDao;
	
	@Override
	@Transactional
	public void save(ContractMessages contractMessages) {
		contractMessagesDao.save(contractMessages);

	}
	
	@Override
	public List<ContractMessages> findByCustomerId(Long customerId) {
		return contractMessagesDao.findByCustomerId(customerId);
	}
}
