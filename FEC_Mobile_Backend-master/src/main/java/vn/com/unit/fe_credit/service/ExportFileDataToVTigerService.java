package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.ExportDataToVTigerBean;
import vn.com.unit.fe_credit.bean.MgmExportVtigerBean;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;

public interface ExportFileDataToVTigerService {

	List<MgmExportFileDataToVTiger> findAllex();

	ExportDataToVTigerBean search(ExportDataToVTigerBean bean);

	MgmExportVtigerBean export(MgmExportVtigerBean bean);

	void save(MgmExportFileDataToVTiger exportVTiger, Boolean flagExport);
	// void saveExportFileVTiger(MgmExportFileDataToVTiger exportVTiger);

	void updateStatusMobile(MgmExportVtigerBean dataToVTigerBean);

	Integer getNoFileVTiger(String date);

	void deleteVTiger(Long exportVTigerId);

	boolean isHasExportingProcess() throws Exception;
}
