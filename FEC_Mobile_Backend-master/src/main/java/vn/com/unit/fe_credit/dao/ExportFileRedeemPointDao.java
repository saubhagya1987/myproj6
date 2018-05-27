package vn.com.unit.fe_credit.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;

public interface ExportFileRedeemPointDao extends GenericDAO<MgmExportFileRedeemPoint, String> {

	Integer searchMaxNoByDate(String date);

	void deleteExportRedeem(Long exportRedeemId);

}
