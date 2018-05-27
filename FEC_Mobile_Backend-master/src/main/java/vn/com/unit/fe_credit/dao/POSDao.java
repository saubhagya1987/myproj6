package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.bean.POSBean;
import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.webservice.BranchCities;
import vn.com.unit.webservice.BranchList;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface POSDao extends GenericDAO<PosEmtity, Long>{
	void updateLocation(String province, String longitude,String latitude,String district,Long branchId);
	List<PosEmtity> findDistrict(String district);
	List<PosEmtity> findByLongAndLat(String longitude, String latitude,String distance, String buyorpay,int page);
	List<PosEmtity> findByBranchListByCity(Long pos_provinceID, int page, String buyOrPay);
	void deletePosLocation(Long posId);
	List<PosEmtity> findProvinceDistrict(Long pos_provinceID, String district);
	List<PosEmtity> maxId();
	List<BranchCities> findByBranchID(Long cityID);
	List<PosEmtity> GetPOSListBySearch(PosEmtity pos);	
	List<PosEmtity> findCodePosBranch(String codePosBranch);
	List<PosEmtity> getListDAONullValue();
	List<PosEmtity> findByLongAndLatTmp(String longitude, String latitude, String distance, String buyorpay, int page);
	POSBean search(POSBean bean);
}
