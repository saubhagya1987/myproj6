package vn.com.unit.fe_credit.dao;

import java.util.List;
import java.util.Map;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;

public interface MgmRedeemTransDao extends GenericDAO<MgmRedeemTrans, String> {

	RedeemTrasPointBean getDataForExportRedeemTransactionPoint(RedeemTrasPointBean bean) throws Exception;
	void updateStatus(RedeemTrasPointBean bean);
	
	List<Object[]> searchValueExportRedeem(RedeemTrasPointBean bean);

	RedeemTrasPointBean searchValueExportMomo(RedeemTrasPointBean bean);

	void updateStatusRedeem(List<Object[]> lst);
	List<Map<String, Object>> getListPushMessageNotice() throws Exception;
	
}
