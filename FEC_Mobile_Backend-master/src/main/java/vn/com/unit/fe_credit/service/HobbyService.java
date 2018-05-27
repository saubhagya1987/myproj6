package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.HobbyBean;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.Hobby;

public interface HobbyService {
	List<Hobby> findAllex();

	HobbyBean search(HobbyBean bean);

	void saveHobby(Hobby entity);

	Hobby findById(Long hobbyId);

	HobbyBean findByHobbyCode(HobbyBean bean);

	List<Hobby> listHobbyByCustomer(Long customerId);
	
	List<Hobby> searchInStatic();
}
