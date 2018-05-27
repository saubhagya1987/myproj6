package vn.com.unit.fe_credit.service;

import vn.com.unit.fe_credit.entity.UserAttempts;

public interface UserAttemptsService {

	void save(UserAttempts userAttempts);

	Integer countAttempts(String ipAddress) throws Exception;

	UserAttempts findByIpAddress(String ipAddress);

}
