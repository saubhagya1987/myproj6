package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.MgmPointConversionHistoryBean;
import vn.com.unit.fe_credit.dao.MgmPointConversionHistoryDao;
import vn.com.unit.fe_credit.service.MgmPointConversionHistoryService;


@Service("MgmPointConversionHistoryService")
@Transactional(readOnly = true)
public class MgmPointConversionHistoryServiceImpl implements MgmPointConversionHistoryService {

	@Autowired
	MgmPointConversionHistoryDao mgmPointConversionHistoryDao;
	
	
	@Override
	@Transactional
	public MgmPointConversionHistoryBean search(MgmPointConversionHistoryBean bean, Long id) {
		return mgmPointConversionHistoryDao.searchListPEPointByCustomerId(bean, id);
	}

}
