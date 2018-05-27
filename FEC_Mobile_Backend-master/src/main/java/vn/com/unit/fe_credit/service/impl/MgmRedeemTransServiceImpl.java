package vn.com.unit.fe_credit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.MgmRedeemTransDao;
import vn.com.unit.fe_credit.service.MgmRedeemTransService;

@Service("MgmRedeemTransService")
@Transactional(readOnly = true)
public class MgmRedeemTransServiceImpl implements MgmRedeemTransService {
	
	@Autowired
	MgmRedeemTransDao mgmRedeemTransDao;

	@Override
	@Transactional
	public List<Map<String, Object>> getListPushMessageNotice() throws Exception {
		return mgmRedeemTransDao.getListPushMessageNotice();
	}

}
