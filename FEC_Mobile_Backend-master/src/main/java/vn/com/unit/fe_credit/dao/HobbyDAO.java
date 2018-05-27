package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.Hobby;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface HobbyDAO extends GenericDAO<Hobby, Long> {
	Hobby findByHobbyCode(String HobbyCode);

	List<Hobby> listHobbyByCustomer(Long customerId);

}
