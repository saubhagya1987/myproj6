package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.BannerImageDao;
import vn.com.unit.fe_credit.entity.BannerImages;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class BannerImageDaoImpl extends GenericDAOImpl<BannerImages, Long> implements BannerImageDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public void deleteBannerImagebyLst(List<BannerImages> lstBannerImage) {
		String sql = "delete from BannerImages"
				+ " where 1=2";
		if (lstBannerImage!=null) {
			for (int i = 0; i < lstBannerImage.size(); i++) {
				sql+= " or bannerImagesId="+lstBannerImage.get(i).getBannerImagesId();
			}
		}
		Query query = getSession().createSQLQuery(sql);
		query.executeUpdate();
	}
	
	@Override
	public List<BannerImages> findByBannerId(Long bannerId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT bi.bannerImagesId as bannerImagesId, bi.imageFileName as imageFileName, bi.imagePath as imagePath, bi.status as status");
		sql.append(" FROM BannerImages bi ");
		sql.append(" WHERE 1=1 AND bi.bannerId = :bannerId");
		
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("bannerImagesId",new LongType())
				.addScalar("imageFileName",new StringType())
				.addScalar("imagePath",new StringType())
				.addScalar("status",new IntegerType());
		query.setParameter("bannerId", bannerId);
		query.setResultTransformer(Transformers.aliasToBean(BannerImages.class));			
		
		return query.list();
	}
}
