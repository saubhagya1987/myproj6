package vn.com.unit.fe_credit.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;

public interface ExportFileDataToVTigerDao extends GenericDAO<MgmExportFileDataToVTiger, String> {

	Integer searchMaxNoByDateVTiger(String date);

	void deleteExportVTiger(Long exportVTigerId);

}
