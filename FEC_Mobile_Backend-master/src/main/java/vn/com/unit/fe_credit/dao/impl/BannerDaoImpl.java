package vn.com.unit.fe_credit.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.BannerBean;
import vn.com.unit.fe_credit.dao.BannerDao;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class BannerDaoImpl extends GenericDAOImpl<Banner, Long> implements BannerDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public BannerBean search(BannerBean bean) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT b.bannerId as bannerId, b.category as category, b.bannerCode as bannerCode, b.status as status, b.activeFromDate as activeFromDate, b.activeToDate as activeToDate");
		sql.append(" FROM Banner b ");

		sql.append(" WHERE 1=1 ");
		
		if (StringUtils.isNotEmpty(bean.getCategory())) {
			sql.append(" AND b.category= :category");
		}
		
		if (StringUtils.isNotEmpty(bean.getBannerCode())) {
			sql.append(" AND LOWER(b.bannerCode) like LOWER(:bannerCode)");
		}
		
		if (bean.getStatus() != null) {
			sql.append(" AND b.status = :status");
		}
		
		if(StringUtils.isNotEmpty(bean.getDir())){
			if(bean.getDir().equals("category")){
				sql.append(" ORDER BY b.category " + bean.getSort());
			}
			if(bean.getDir().equals("bannerCode")){
				sql.append(" ORDER BY b.bannerCode " + bean.getSort());
			}
			if(bean.getDir().equals("activeFromDate")){
				sql.append(" ORDER BY b.activeFromDate " + bean.getSort());
			}
			if(bean.getDir().equals("activeToDate")){
				sql.append(" ORDER BY b.activeToDate " + bean.getSort());
			}
		}else{
			sql.append("  ORDER BY orderbanner ASC ");
		}
		
		Query query = getSession().createSQLQuery(sql.toString())
						.addScalar("bannerId",new LongType())
						.addScalar("category",new StringType())
						.addScalar("bannerCode",new StringType())
						.addScalar("status",new IntegerType())
						.addScalar("activeFromDate",new TimestampType())
						.addScalar("activeToDate",new TimestampType());
		query.setResultTransformer(Transformers.aliasToBean(Banner.class));
		Query queryCount = getSession().createSQLQuery(vn.com.unit.fe_credit.utils.Utils.getCountQuery(sql.toString()));		
		
		if (StringUtils.isNotEmpty(bean.getCategory())) {
			query.setParameter("category", bean.getCategory());
			queryCount.setParameter("category", bean.getCategory());
		}
		
		if (StringUtils.isNotEmpty(bean.getBannerCode())) {
			query.setParameter("bannerCode", "%" + bean.getBannerCode() + "%");
			queryCount.setParameter("bannerCode", "%" + bean.getBannerCode() + "%");
		}
		
		if (bean.getStatus() != null) {
			query.setParameter("status", bean.getStatus());
			queryCount.setParameter("status", bean.getStatus());
		}
		
		int totalCount = 0;
		if(bean.getLimit() > 0){
			totalCount = ((BigDecimal) queryCount.list().get(0)).intValue();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}		
		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}
	
	@Override
	public List<Banner> findByCategory(String category) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT b.bannerId as bannerId, b.link as link, b.activeFromDate as activeFromDate, b.title as title, bi.imageFileName as imageFileName, bi.imagePath as imagePath");
		sql.append(" FROM Banner b ");
		sql.append(" JOIN BannerImages bi ON bi.bannerId = b.bannerId");
		sql.append(" WHERE 1=1 and b.status =1 and ((b.ActiveFromDate is  null or b.ActiveFromDate <= :date) AND (b.ActiveToDate is null or b.ActiveToDate >= :date)) AND b.category = :category  ORDER BY orderbanner ASC ");
		
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("bannerId",new LongType())
				.addScalar("link",new StringType())
				.addScalar("activeFromDate",new TimestampType())
				.addScalar("title",new StringType())
				.addScalar("imageFileName",new StringType())
				.addScalar("imagePath",new StringType());
		query.setParameter("category", category);
		
		query.setParameter("date",Utils.setTimeToMax(new Date()));
		query.setResultTransformer(Transformers.aliasToBean(Banner.class));			
		
		return query.list();
	}
	
	@Override
	public List<Banner> findByCategory_WS_getTitlebyCategory(String category) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT b.bannerId as bannerId, b.link as link, b.activeFromDate as activeFromDate, b.title as title, bi.imageFileName as imageFileName, bi.imagePath as imagePath");
		sql.append(" FROM Banner b ");
		sql.append(" JOIN BannerImages bi ON bi.bannerId = b.bannerId");
		sql.append(" WHERE 1=1 AND b.category = :category");
		
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("bannerId",new LongType())
				.addScalar("link",new StringType())
				.addScalar("activeFromDate",new TimestampType())
				.addScalar("title",new StringType())
				.addScalar("imageFileName",new StringType())
				.addScalar("imagePath",new StringType());
		
		query.setParameter("category", category);
		query.setResultTransformer(Transformers.aliasToBean(Banner.class));			
		
		return query.list();
	}
	
	@Override
	public Banner getContentByID(Long bannerId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT bi.link as link, bi.activeFromDate as activeFromDate, bi.title as title");//, bi.category as category
		sql.append(" FROM Banner bi ");
		sql.append(" WHERE 1=1 AND bi.bannerId = :bannerId");
		
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("link",new StringType())
				.addScalar("activeFromDate",new TimestampType())
				.addScalar("title",new StringType());
		query.setParameter("bannerId", bannerId);
		query.setResultTransformer(Transformers.aliasToBean(Banner.class));	
		List<Banner> result = query.list();
		if(result != null && result.size() > 0){
			return result.get(0);
		}
		return null;
	}


	@Override
	public List<Banner> searchInStatus() {
		StringBuilder sql = new StringBuilder();
		sql.append( "Select * FROM Banner banner WHERE 1=1 AND banner.status = :status");
		Query query = getSession().createSQLQuery(sql.toString()).addEntity(Banner.class);
		query.setParameter("status",1);
		return query.list();
	}
	
}
