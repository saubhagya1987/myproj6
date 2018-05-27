package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.ExportRedeemTrasPointBean;
import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;

public interface ExportFileRedeemPointService {

	List<MgmExportFileRedeemPoint> findAllex();

	ExportRedeemTrasPointBean search(ExportRedeemTrasPointBean bean);

	RedeemTrasPointBean getDataForExportRedeemTransactionPoint(RedeemTrasPointBean bean) throws Exception;

	void save(MgmExportFileRedeemPoint exportRedeemPoint, Boolean flagExport);

//	void saveExportFileRedeem(MgmExportFileRedeemPoint exportRedeemPoint);

	Integer getNoFile(String date);

	void deleteExportRedeem(Long exportRedeemId);

	void updateStatusMobile(RedeemTrasPointBean redeemTrasPointBean);

	List<Object[]> exportRedeem(RedeemTrasPointBean bean);

	RedeemTrasPointBean exportMomo(RedeemTrasPointBean bean);

	void updateStatusRedeem(List<Object[]> lst);

	boolean isHasExportingProcess(String valueType) throws Exception;
	
//	RedeemTrasPointBean exportFileRedeem(RedeemTrasPointBean redeembBean) throws Exception;
}
