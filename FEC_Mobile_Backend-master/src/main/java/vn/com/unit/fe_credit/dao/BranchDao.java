package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.entity.BranchEmtity;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface BranchDao extends GenericDAO<BranchEmtity, Long>{
	void updateLocation(String province, String longitude,String latitude);
	List<BranchEmtity> findProvince(String province);
	void deleteBranchLocation(Long branchId);
}
