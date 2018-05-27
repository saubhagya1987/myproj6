package vn.com.unit.fe_credit.dao.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.IntegerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.dao.ExportFileRedeemPointDao;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;

@Repository
public class ExportFileRedeemPointDaoImpl extends GenericDAOImpl<MgmExportFileRedeemPoint, String>
		implements ExportFileRedeemPointDao {
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);

	}

	@Override
	public Integer searchMaxNoByDate(String date) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT NVL(MAX(MGM_EXPORT_FILE_REDEEM_POINT.NO_IN_DATE),0) AS NO_IN_DATE from MGM_EXPORT_FILE_REDEEM_POINT  ");
		hql.append(" WHERE 1=1 ");
		hql.append(" AND to_char(MGM_EXPORT_FILE_REDEEM_POINT.EXPORT_FILE_DATE,'MM-dd-yyyy') = :date");

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("date", date);
		return Integer.valueOf(String.valueOf(query.uniqueResult()));
	}

	@Override
	public void deleteExportRedeem(Long exportRedeemId) {
		// delete redeem
		String hql = " " + "delete from mgm_export_file_redeem_point where export_redeem_id  =:exportRedeemId" + "  ";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("exportRedeemId", exportRedeemId);
		query.executeUpdate();
	}


}
