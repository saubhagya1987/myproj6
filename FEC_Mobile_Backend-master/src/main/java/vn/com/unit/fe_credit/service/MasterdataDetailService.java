package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.MasterdataDetailBean;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.webservice.Hotline;

public interface MasterdataDetailService {
	List<MasterdataDetal> findAllex();

	MasterdataDetailBean search(MasterdataDetailBean masterdataDetailBean);

	void saveMasterdataDetal(MasterdataDetal entity);

	MasterdataDetal findById(Long masterdataDetalId);
	
	MasterdataDetailBean findByMasterdataDetailBeanName(MasterdataDetailBean bean);
	
	void deleteMasterdataDetail(Long masterdataDetailId);

	MasterdataDetal findNameAndMasterDuplicate(String name, Long masterId, Long masterdetailId);

	List<Hotline> findDetail(String masterType);

	MasterdataDetal getMasterData(String parentName, String childName) throws Exception;

}
