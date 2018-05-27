package vn.com.unit.fe_credit.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.MgmViewPointDao;
import vn.com.unit.fe_credit.service.MgmViewPointService;

@Service("MgmViewPointService")
public class MgmViewPointServiceImpl implements MgmViewPointService {

	@Autowired
	MgmViewPointDao mgmViewPointDao;

	@Override
	@Transactional
	public Integer getFriendsBecomeLoans(Long id) {
		return mgmViewPointDao.getFriendsBecomeLoans(id);
	}

	@Override
	@Transactional
	public Integer getRemainingPointsPreviousTerm(Long id) {
		return mgmViewPointDao.getRemainingPointsPreviousTerm(id);
	}

	@Override
	@Transactional
	public Integer getRemainingPoints(Long id) {
		return mgmViewPointDao.getRemainingPoints(id);
	}

	@Override
	@Transactional
	public Integer getPointsHasConverted(Long id) {
		return mgmViewPointDao.getPointsHasConverted(id);
	}

	@Override
	@Transactional
	public Integer getTotalPoints(Long id) {
		return mgmViewPointDao.getTotalPoints(id);
	}

	@Override
	@Transactional
	public Integer getTotalFriends(Long id) {
		return mgmViewPointDao.getTotalFriends(id);
	}

	@Override
	@Transactional
	public Integer getFriendsValid(Long id) {
		return mgmViewPointDao.getFriendsValid(id);
	}

	@Override
	@Transactional
	public Map<String, Object> getCustomerPointSummary(long customerId) throws Exception {
		return mgmViewPointDao.getCustomerPointSummary(customerId);
	}

}
