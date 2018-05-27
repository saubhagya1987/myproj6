package vn.com.unit.fe_credit.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.entity.UserAttempts;

public interface UserAttemptsDao extends GenericDAO<UserAttempts, Long> {

	Integer countAttempts(String username) throws Exception;

}
