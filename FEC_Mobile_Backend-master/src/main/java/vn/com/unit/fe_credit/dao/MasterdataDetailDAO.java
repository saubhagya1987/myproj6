package vn.com.unit.fe_credit.dao;

import vn.com.unit.fe_credit.entity.MasterdataDetal;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface MasterdataDetailDAO extends GenericDAO<MasterdataDetal, Long> {
	void deleteMasterdataID(Long masterdataId);

}
