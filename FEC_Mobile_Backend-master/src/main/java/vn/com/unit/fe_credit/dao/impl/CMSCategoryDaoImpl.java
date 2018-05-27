package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.CMSCategoryDao;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.CMSEmtity;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class CMSCategoryDaoImpl extends GenericDAOImpl<CMSCategory, Long>
		implements CMSCategoryDao {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<CMSCategory> findInStatic() {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT cms.CMS_CATEGORYID as cms_categoryId, cms.NAME  as name , cms.CODE as code ");
		sql.append(" FROM CMS_CATEGORY  cms");
		sql.append(" JOIN STATUS_TABLE stt ON stt.STATUSID = cms.STATUSID");
		sql.append(" WHERE  stt.STATUS_TEXT =:status_text");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("cms_categoryId", new LongType())
				.addScalar("name", new StringType())
				.addScalar("code", new StringType());
		query.setParameter("status_text", "Activated");
		query.setResultTransformer(Transformers.aliasToBean(CMSCategory.class));
		return query.list();

	}

	@Override
	public List<CMSCategory> findById(String[] list) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT cms.CMS_CATEGORYID as cms_categoryId, cms.NAME  as name , cms.CODE as code FROM CMS_CATEGORY cms ");
		for (int i = 0; i < list.length; i++) {
			if (i == 0)
				sql.append(" WHERE cms.CMS_CATEGORYID='" + list[i] + "'");
			else
				sql.append(" OR cms.CMS_CATEGORYID='" + list[i] + "'");
		}

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("cms_categoryId", new LongType())
				.addScalar("name", new StringType())
				.addScalar("code", new StringType());
		query.setResultTransformer(Transformers.aliasToBean(CMSCategory.class));
		return query.list();
	}

}
