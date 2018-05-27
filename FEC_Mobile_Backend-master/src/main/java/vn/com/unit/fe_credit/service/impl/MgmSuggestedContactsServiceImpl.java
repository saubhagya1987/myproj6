package vn.com.unit.fe_credit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.MgmSuggestedContactsBean;
import vn.com.unit.fe_credit.dao.MgmSuggestedContactsDao;
import vn.com.unit.fe_credit.service.MgmSuggestedContactsService;

@Service("MgmSuggestedContactsService")
@Transactional(readOnly = true)
public class MgmSuggestedContactsServiceImpl implements MgmSuggestedContactsService {

	@Autowired
	MgmSuggestedContactsDao mgmSuggestedContactsDao;

	@Override
	@Transactional
	public MgmSuggestedContactsBean searchListFriendsByCustomerId(MgmSuggestedContactsBean bean, long customerId) throws Exception {
		return mgmSuggestedContactsDao.searchListFriendsByCustomerId(bean, customerId);
	}

	@Transactional
	@Override
	public List<Map<String, Object>> getListPushMessageWhenFriendBecomeLoan() throws Exception {
		return mgmSuggestedContactsDao.getListPushMessageWhenFriendBecomeLoan();
	}

}
