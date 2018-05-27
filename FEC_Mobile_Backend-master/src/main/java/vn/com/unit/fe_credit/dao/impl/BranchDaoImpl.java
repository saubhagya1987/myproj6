package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.BranchDao;
import vn.com.unit.fe_credit.entity.BranchEmtity;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class BranchDaoImpl extends GenericDAOImpl<BranchEmtity, Long> implements BranchDao {


	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public void updateLocation(String province, String longitude,String latitude) {
		String hql = " " + "update branch set longitude  =:longitude  , latitude   =:latitude " + "  "
				+ "where province =:province " + "  ";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);
		query.setParameter("province", province);
		query.executeUpdate();
		
	}




	@Override
	public List<BranchEmtity> findProvince(String province) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select br.branchid from BRANCH  br where br.province=:province");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("branchid", new LongType());
		query.setParameter("province", province);
		query.setResultTransformer(Transformers.aliasToBean(BranchEmtity.class));
		return query.list();
	}
	@Override
	public void deleteBranchLocation(Long branchId) {
		// delete branch
		String hql = " " + "delete from branch where branchid  =:branchid" + "  ";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("branchid", branchId);
		query.executeUpdate();
	}





		
}
