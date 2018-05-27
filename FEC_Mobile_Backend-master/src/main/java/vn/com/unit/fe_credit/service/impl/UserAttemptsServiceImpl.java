package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.dao.UserAttemptsDao;
import vn.com.unit.fe_credit.entity.UserAttempts;
import vn.com.unit.fe_credit.service.UserAttemptsService;

@Service("UserAttemptsService")
@Transactional(readOnly = true)
public class UserAttemptsServiceImpl implements UserAttemptsService {

	@Autowired
	private UserAttemptsDao userAttemptsDao;

	@Override
	@Transactional
	public void save(UserAttempts user) {
		userAttemptsDao.save(user);
	}

	@Override
	public Integer countAttempts(String ipAddress) throws Exception {
		return userAttemptsDao.countAttempts(ipAddress);
	}

	@Override
	public UserAttempts findByIpAddress(String ipAddress) {
		Search search = new Search(UserAttempts.class);
		search.addFilterEqual("ipAddress", ipAddress);
		List<UserAttempts> result = userAttemptsDao.search(search);
		if (CollectionUtils.isNotEmpty(result)) {
			return result.get(0);
		} else {
			return null;
		}
	}

}
