package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.dao.ExportFileDataToVTigerDao;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;

@Repository
public class ExportFileDataToVTigerDaoImpl extends GenericDAOImpl<MgmExportFileDataToVTiger, String>
		implements ExportFileDataToVTigerDao {
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

	}

	@Override
	public Integer searchMaxNoByDateVTiger(String date) {
		// search numbering file
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT MAX(MGM_EXPORT_FILE_VTIGER.NO_IN_DATE) from MGM_EXPORT_FILE_VTIGER ");
		hql.append(" WHERE 1=1 ");
		hql.append(" AND to_char(MGM_EXPORT_FILE_VTIGER.EXPORT_FILE_DATE,'MM-dd-yyyy') = :date");

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("date", date);
		Integer a = null;
		if (query.uniqueResult() == null) {
			a = 0;
		} else {
		}

		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;
	}

	@Override
	public void deleteExportVTiger(Long exportVTigerId) {
		// delete v-tiger
		String hql = " " + "delete from mgm_export_file_vtiger where vtiger_id  =:vtigerId" + "  ";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("vtigerId", exportVTigerId);
		query.executeUpdate();
	}

}
