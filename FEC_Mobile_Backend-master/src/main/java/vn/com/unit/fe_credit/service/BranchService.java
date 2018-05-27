package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.BranchBean;
import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.webservice.BranchCities;
import vn.com.unit.webservice.LoanCalculator;

public interface BranchService {
	List<BranchEmtity> findAllex();

	BranchBean search(BranchBean bean);
	
	List<BranchCities> findAll();
	
//	List<BranchCities> findByBranchListByCity(Long cityID, Integer offset);
	
	BranchEmtity findById(Long province);
	
	List<BranchEmtity> findProvince(String province);

	void saveBranch(BranchEmtity entity);
	void updateLocation(String province,String longitude,String latitude);
	void deleteBranchLocation(Long branchId);
}
