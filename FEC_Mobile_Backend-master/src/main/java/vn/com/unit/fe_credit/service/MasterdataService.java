package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.HobbyBean;
import vn.com.unit.fe_credit.bean.MasterdataBean;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Masterdata;
import vn.com.unit.webservice.Hotline;

public interface MasterdataService {
	List<Masterdata> findAllex();

	MasterdataBean search(MasterdataBean masterdataBean);

	void saveMasterdata(Masterdata entity);

	Masterdata findById(Long masterdataId);
	
	MasterdataBean findByMasterdataName(MasterdataBean bean);
	
	void deleteMasterdata(Long masterdataId);
	
	Masterdata findByName(Masterdata masterdata);
	
	List<Hotline> findDetail(String masterType);
}
