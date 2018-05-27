package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.POSBean;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.webservice.BranchCities;

public interface POSService {
	List<PosEmtity> findAllex();

	POSBean search(POSBean bean);
	
	List<PosEmtity> findByBranchListByCity(Long pos_provinceID, int page, String buyOrPay);
	
	List<BranchCities> findByBranchID(Long cityID);
	List<PosEmtity> findByLongAndLat(String longitude, String latitude, String distance, String buyorpay,int page);

	PosEmtity findById(Long province);
	List<PosEmtity> findDistrict(String district);
	
	List<PosEmtity> findCodePosBranch(String codePosBranch);
	
	List<PosEmtity> findProvinceDistrict(Long pos_provinceID,String district);

	void savePOS(PosEmtity entity);
	void savePOSAuto(PosEmtity entity);
	void updateLocation(String province, String longitude,String latitude,String district,Long branchId);
	
	void deletePosLocation(Long branchId);
	void deletePosIdLocation(Long branchId);
	
	List<PosEmtity> maxId();
	List<PosEmtity> getListDAONullValue();
	List<PosEmtity> GetPOSListBySearch(PosEmtity pos);

	List<PosEmtity> findByLongAndLatTmp(String longitude, String latitude, String distance, String buyOrPay, int parseInt);

}
