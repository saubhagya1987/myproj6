package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.POSBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.POSDao;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.service.POSService;
import vn.com.unit.webservice.BranchCities;

@Service("POSService")
@Transactional(readOnly = true)
public class POSServiceImpl implements POSService {

	@Autowired
	private POSDao posDao;
	@Autowired
	private UserProfile userProfile;
	public POSServiceImpl() {
		super();
	}

	@Override
	public List<PosEmtity> findAllex() {
		return posDao.findAll();
	}

	@Override
	public POSBean search(POSBean bean) {
		return posDao.search(bean);
	}

	@Override
	@Transactional
	public void savePOS(PosEmtity entity) {
		if(entity.getPosid() == null){
			entity.setCreated_date(new Date());
			if(userProfile != null && userProfile.getAccount() != null)
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			if(userProfile != null && userProfile.getAccount() != null)
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		posDao.save(entity);

	}
	
	
	@Override
	@Transactional
	public void savePOSAuto(PosEmtity entity) {
		posDao.save(entity);

	}
	
	
	@Override
	public PosEmtity findById(Long CMSId) {
		return posDao.find(CMSId);
	}

	@Override
	public void updateLocation(String province, String longitude,
			String latitude, String district, Long branchId) {
		posDao.updateLocation(province, longitude, latitude, district, branchId);
	}

	@Override
	public List<PosEmtity> findDistrict(String district) {
		return posDao.findDistrict(district);
	}

	@Override
	public List<BranchCities> findByBranchID(Long cityID) {
		return posDao.findByBranchID(cityID);
	}

	@Override
	public List<PosEmtity> findByLongAndLat(String longitude, String latitude,
			String distance, String buyorpay,int page) {
		// TODO Auto-generated method stub
		return posDao.findByLongAndLat(longitude, latitude, distance, buyorpay,page);
	}
	
	@Override
	public List<PosEmtity> findByLongAndLatTmp(String longitude, String latitude,
			String distance, String buyorpay,int page) {
		// TODO Auto-generated method stub
		return posDao.findByLongAndLatTmp(longitude, latitude, distance, buyorpay,page);
	}

	@Override
	public List<PosEmtity> findByBranchListByCity(Long pos_provinceID,
			int page, String buyOrPay) {
		// TODO Auto-generated method stub
		return posDao.findByBranchListByCity(pos_provinceID, page, buyOrPay);
	}
	
	@Override
	public List<PosEmtity> GetPOSListBySearch(PosEmtity pos) {
		// TODO Auto-generated method stub
		return posDao.GetPOSListBySearch(pos);
	}

	@Override
	@Transactional
	public void deletePosLocation(Long branchId) {
		posDao.deletePosLocation(branchId);
	}

	@Override
	@Transactional
	public void deletePosIdLocation(Long branchId) {
		posDao.removeById(branchId);

	}

	@Override
	public List<PosEmtity> findProvinceDistrict(Long pos_provinceID,
			String district) {
		return posDao.findProvinceDistrict(pos_provinceID, district);
	}

	@Override
	public List<PosEmtity> maxId() {
		return posDao.maxId();
	}

	@Override
	public List<PosEmtity> findCodePosBranch(String codePosBranch) {
		return posDao.findCodePosBranch(codePosBranch);
	}

	@Override
	public List<PosEmtity> getListDAONullValue() {
		return posDao.getListDAONullValue();
	}

}
