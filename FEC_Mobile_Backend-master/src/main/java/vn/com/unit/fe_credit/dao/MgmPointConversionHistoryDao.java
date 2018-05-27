package vn.com.unit.fe_credit.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.bean.MgmPointConversionHistoryBean;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;

public interface MgmPointConversionHistoryDao extends GenericDAO<MgmRedeemTrans, String> {

	MgmPointConversionHistoryBean searchListPEPointByCustomerId(MgmPointConversionHistoryBean bean, Long customerId);

}
