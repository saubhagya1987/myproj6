package vn.com.unit.fe_credit.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.bean.MgmExportVtigerBean;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;

public interface MgmExportVtigerDao extends GenericDAO<MgmSuggestedContacts, String> {

	MgmExportVtigerBean searchValueExport(MgmExportVtigerBean bean);

	void updateStatus(MgmExportVtigerBean bean);
}
