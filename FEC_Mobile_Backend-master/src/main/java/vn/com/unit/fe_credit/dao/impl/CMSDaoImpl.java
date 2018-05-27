package vn.com.unit.fe_credit.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.dao.CMSDao;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.utils.Utils;

@Repository
public class CMSDaoImpl extends GenericDAOImpl<CMSEmtity, Long> implements CMSDao {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<CMSEmtity> getTitlebyCategory(String category) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT c.cmsId as cmsId, c.title as title, c.image as image");
		sql.append(" FROM CMS c");
		sql.append(" JOIN CMS_Category cc ON cc.cms_categoryId = c.cms_categoryId");
		sql.append(" WHERE 1=1 AND cc.code = :category");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("cmsId", new LongType()).addScalar("title", new StringType())
				.addScalar("image", new StringType());

		query.setParameter("category", category.toUpperCase());
		query.setResultTransformer(Transformers.aliasToBean(CMSEmtity.class));

		return query.list();
	}

	@Override
	public Map<String, String> getMobileAppTerm(long categoryId) throws Exception {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT c.title as title, c.content as content");
		sql.append(" FROM CMS c");
		sql.append(" WHERE c.cms_categoryId like '%;" + categoryId + ";%' ");

		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.addScalar("title", new StringType());
		query.addScalar("content", new StringType());

		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List datas = query.list();
		if (CollectionUtils.isNotEmpty(datas)) {
			return (Map<String, String>) datas.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<CMSEmtity> getTitleEXCategory(String category, String searchName, Integer page, Integer limit) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT c.cmsId     AS cmsId, ");
		sql.append("   c.title          AS title, ");
		sql.append("   c.image          AS image, ");
		sql.append("   c.CMS_CATEGORYID AS cmsCategory, ");
		sql.append("   c.imageLong      AS imageLong, ");
		sql.append("   c.shareComment   AS shareComment, ");
		sql.append("   c.url            AS url, ");
		sql.append("   c.CREATEDDATE    AS createDate, ");
		sql.append("   c.START_DAY      AS startDate, ");
		sql.append("   c.END_DAY        AS endDate, ");
		sql.append("   c.CMS_HOBBYYID   AS cmsHobby, ");
		sql.append("   c.TAG            AS tag, ");
		sql.append("   c.IMAGE_LONG_EN  AS image_long_en, ");
		sql.append("   c.IMAGE_EN       AS image_en ");
		sql.append(" FROM CMS c ");
		sql.append(" WHERE c.STATUSID         = 1 ");
		sql.append(" AND (c.START_DAY        IS NULL ");
		sql.append(" OR TRUNC( c.START_DAY ) <= CURRENT_DATE ) ");
		sql.append(" AND (c.END_DAY          IS NULL ");
		sql.append(" OR TRUNC( c.END_DAY )   >= CURRENT_DATE ) ");
		sql.append(" AND CMS_CATEGORYID like '%;" + category + ";%' ");

		if (StringUtils.isNotBlank(searchName)) {
			sql.append("  AND ( CONVERTTOUNSIGN(lower(c.title)) like CONVERTTOUNSIGN(lower(:searchName)) ");
			// sql.append(" OR CONVERTTOUNSIGN(lower(DBMS_LOB.SUBSTR(c.content,3000,1))) like
			// CONVERTTOUNSIGN(lower(:searchName)) ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHORT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHARECOMMENT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append(" ) ");
		}

		sql.append("  ORDER BY ORDERCMS ASC ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("cmsId", new LongType()).addScalar("title", new StringType())
				.addScalar("cmsCategory", new StringType()).addScalar("image", new StringType()).addScalar("imageLong", new StringType())
				.addScalar("image_long_en", new StringType()).addScalar("image_en", new StringType()).addScalar("shareComment", new StringType())
				.addScalar("url", new StringType()).addScalar("createDate", new TimestampType()).addScalar("startDate", new TimestampType())
				.addScalar("endDate", new TimestampType()).addScalar("cmsHobby", new StringType()).addScalar("tag", new StringType());

		if (StringUtils.isNotBlank(searchName)) {
			query.setParameter("searchName", "%" + searchName.trim() + "%");
		}

		if (page != null && limit != null) {
			query.setMaxResults(limit);
			query.setFirstResult((page - 1) * limit);
		}

		query.setResultTransformer(Transformers.aliasToBean(CMSEmtity.class));
		return query.list();
	}

	@Override
	public List<CMSEmtity> getTitleEXCategoryOffer(String category, List<Long> list, String searchName, Integer page, Integer limit) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT c.cmsId     AS cmsId, ");
		sql.append("   c.title          AS title, ");
		sql.append("   c.image          AS image, ");
		sql.append("   c.CMS_CATEGORYID AS cmsCategory, ");
		sql.append("   c.imageLong      AS imageLong, ");
		sql.append("   c.shareComment   AS shareComment, ");
		sql.append("   c.url            AS url, ");
		sql.append("   c.CREATEDDATE    AS createDate, ");
		sql.append("   c.START_DAY      AS startDate, ");
		sql.append("   c.END_DAY        AS endDate, ");
		sql.append("   c.CMS_HOBBYYID   AS cmsHobby, ");
		sql.append("   c.TAG            AS tag, ");
		sql.append("   c.IMAGE_LONG_EN  AS image_long_en, ");
		sql.append("   c.IMAGE_EN       AS image_en ");
		sql.append(" FROM CMS c ");
		sql.append(" WHERE c.STATUSID         = 1 ");
		sql.append(" AND (c.START_DAY        IS NULL ");
		sql.append(" OR TRUNC( c.START_DAY ) <= CURRENT_DATE ) ");
		sql.append(" AND (c.END_DAY          IS NULL ");
		sql.append(" OR TRUNC( c.END_DAY )   >= CURRENT_DATE ) ");
		sql.append(" AND CMS_CATEGORYID like '%;" + category + ";%' ");

		if (StringUtils.isNotBlank(searchName)) {
			sql.append("  AND ( CONVERTTOUNSIGN(lower(c.title)) like CONVERTTOUNSIGN(lower(:searchName)) ");
			// sql.append(" OR CONVERTTOUNSIGN(lower(DBMS_LOB.SUBSTR(c.content,3000,1))) like
			// CONVERTTOUNSIGN(lower(:searchName)) ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHORT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHARECOMMENT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append(" ) ");
		}

		if (list != null && list.size() > 0) {
			sql.append("  AND ( ");
			Integer count = 0;
			for (Long long1 : list) {
				if (count == 0) {
					sql.append(" CMS_HOBBYYID like '%;" + long1 + ";%' ");
				} else {
					sql.append(" OR CMS_HOBBYYID like '%;" + long1 + ";%' ");
				}
				count++;
			}
			sql.append("  ) ");
		}

		sql.append("  ORDER BY ORDERCMS ASC ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("cmsId", new LongType()).addScalar("title", new StringType())
				.addScalar("cmsCategory", new StringType()).addScalar("image", new StringType()).addScalar("imageLong", new StringType())
				.addScalar("shareComment", new StringType()).addScalar("url", new StringType()).addScalar("createDate", new TimestampType())
				.addScalar("startDate", new TimestampType()).addScalar("endDate", new TimestampType()).addScalar("cmsHobby", new StringType())
				.addScalar("tag", new StringType());

		if (StringUtils.isNotBlank(searchName)) {
			query.setParameter("searchName", "%" + StringUtils.trim(searchName) + "%");
		}
		if (page != null && limit != null) {
			query.setMaxResults(limit);
			query.setFirstResult((page - 1) * limit);
		}

		query.setResultTransformer(Transformers.aliasToBean(CMSEmtity.class));
		return query.list();
	}

	@Override
	public List<CMSEmtity> getTitleEXCategoryByOne(String category, String searchName, Long cmsId, String preornext) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.cmsId as cmsId, c.title as title, c.image as image,c.CMS_CATEGORYID as cmsCategory, "
				+ "	c.imageLong as imageLong, c.shareComment as shareComment, c.url as url,c.CREATEDDATE as createDate, "
				+ " c.START_DAY as startDate, c.END_DAY as endDate, c.CMS_HOBBYYID as cmsHobby, c.TAG as tag " + "  FROM CMS c "
				+ " WHERE c.STATUSID = 1 and ((c.START_DAY is  null or c.START_DAY <= :date) AND (c.END_DAY is null or c.END_DAY >= :date)) AND CMS_CATEGORYID like '%;"
				+ category + ";%' ");

		if (StringUtils.isNotBlank(searchName)) {
			sql.append("  AND ( CONVERTTOUNSIGN(lower(c.title)) like CONVERTTOUNSIGN(lower(:searchName)) ");
			// sql.append(" OR CONVERTTOUNSIGN(lower(DBMS_LOB.SUBSTR(c.content,3000,1))) like
			// CONVERTTOUNSIGN(lower(:searchName)) ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHORT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHARECOMMENT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append(" ) ");
		}

		sql.append("  ORDER BY ORDERCMS ASC ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("cmsId", new LongType()).addScalar("title", new StringType())
				.addScalar("cmsCategory", new StringType()).addScalar("image", new StringType()).addScalar("imageLong", new StringType())
				.addScalar("shareComment", new StringType()).addScalar("url", new StringType()).addScalar("createDate", new TimestampType())
				.addScalar("startDate", new TimestampType()).addScalar("endDate", new TimestampType()).addScalar("cmsHobby", new StringType())
				.addScalar("tag", new StringType());

		query.setParameter("date", Utils.setTimeToMax(new Date()));
		if (StringUtils.isNotBlank(searchName)) {
			query.setParameter("searchName", "%" + searchName.trim() + "%");
		}

		query.setResultTransformer(Transformers.aliasToBean(CMSEmtity.class));
		List<CMSEmtity> listcms = query.list();
		CMSEmtity tmp = new CMSEmtity();
		List<CMSEmtity> listTmp = new ArrayList<CMSEmtity>();
		if (query.list().size() > 0) {
			if (StringUtils.isNotBlank(preornext) && preornext.equalsIgnoreCase("pre")) {
				for (CMSEmtity item : listcms) {
					if (item.getCmsId().equals(cmsId)) {
						break;
					} else {
						tmp = item;
					}
				}
			}

			if (StringUtils.isNotBlank(preornext) && preornext.equalsIgnoreCase("next")) {
				boolean flag = false;
				for (CMSEmtity item : listcms) {
					if (item.getCmsId().equals(cmsId)) {
						flag = true;
						continue;
					}
					if (flag) {
						tmp = item;
						break;
					}

				}
			}
		}
		listTmp.add(tmp);

		return listTmp;
	}

	@Override
	public List<CMSEmtity> getTitleEXCategoryOfferByOne(String category, List<Long> list, String searchName, Long cmsId, String preornext) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.cmsId as cmsId, c.title as title, c.image as image,c.CMS_CATEGORYID as cmsCategory, "
				+ "	c.imageLong as imageLong, c.shareComment as shareComment, c.url as url,c.CREATEDDATE as createDate, "
				+ " c.START_DAY as startDate, c.END_DAY as endDate, c.CMS_HOBBYYID as cmsHobby, c.TAG as tag " + "  FROM CMS c "
				+ " WHERE c.STATUSID = 1 and ((c.START_DAY is  null or c.START_DAY <= :date) AND (c.END_DAY is null or c.END_DAY >= :date)) AND CMS_CATEGORYID like '%;"
				+ category + ";%' ");

		if (StringUtils.isNotBlank(searchName)) {
			sql.append("  AND ( CONVERTTOUNSIGN(lower(c.title)) like CONVERTTOUNSIGN(lower(:searchName)) ");
			// sql.append(" OR CONVERTTOUNSIGN(lower(DBMS_LOB.SUBSTR(c.content,3000,1))) like
			// CONVERTTOUNSIGN(lower(:searchName)) ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHORT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHARECOMMENT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append(" ) ");
		}

		if (list != null && list.size() > 0) {
			sql.append("  AND ( ");
			Integer count = 0;
			for (Long long1 : list) {
				if (count == 0) {
					sql.append(" CMS_HOBBYYID like '%;" + long1 + ";%' ");
				} else {
					sql.append(" OR CMS_HOBBYYID like '%;" + long1 + ";%' ");
				}
				count++;
			}
			sql.append("  ) ");
		}

		sql.append("  ORDER BY ORDERCMS ASC ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("cmsId", new LongType()).addScalar("title", new StringType())
				.addScalar("cmsCategory", new StringType()).addScalar("image", new StringType()).addScalar("imageLong", new StringType())
				.addScalar("shareComment", new StringType()).addScalar("url", new StringType()).addScalar("createDate", new TimestampType())
				.addScalar("startDate", new TimestampType()).addScalar("endDate", new TimestampType()).addScalar("cmsHobby", new StringType())
				.addScalar("tag", new StringType());

		query.setParameter("date", Utils.setTimeToMax(new Date()));
		if (StringUtils.isNotBlank(searchName)) {
			query.setParameter("searchName", "%" + searchName.trim() + "%");
		}

		query.setResultTransformer(Transformers.aliasToBean(CMSEmtity.class));

		List<CMSEmtity> listcms = query.list();
		CMSEmtity tmp = new CMSEmtity();
		List<CMSEmtity> listTmp = new ArrayList<CMSEmtity>();
		if (listcms.size() > 0) {
			if (StringUtils.isNotBlank(preornext) && preornext.equalsIgnoreCase("pre")) {
				for (CMSEmtity item : listcms) {
					if (item.getCmsId().equals(cmsId)) {
						break;
					} else {
						tmp = item;
					}
				}
			}

			if (StringUtils.isNotBlank(preornext) && preornext.equalsIgnoreCase("next")) {
				boolean flag = false;
				for (CMSEmtity item : listcms) {
					if (item.getCmsId().equals(cmsId)) {
						flag = true;
						continue;
					}
					if (flag) {
						tmp = item;
						break;
					}

				}
			}
		}
		listTmp.add(tmp);

		return listTmp;
	}

	@Override
	public Integer getStatistic(String category, Long customerId, List<Long> list, String searchName, Integer page, Integer limit) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.cmsId as cmsId, c.title as title, c.image as image,c.CMS_CATEGORYID as cmsCategory, "
				+ "	c.imageLong as imageLong, c.shareComment as shareComment, c.url as url,c.CREATEDDATE as createDate, "
				+ " c.START_DAY as startDate, c.END_DAY as endDate, c.CMS_HOBBYYID as cmsHobby, c.TAG as tag " + "  FROM CMS c "
				+ " WHERE c.STATUSID = 1 and ((c.START_DAY is  null or c.START_DAY <= :date) AND (c.END_DAY is null or c.END_DAY >= :date)) AND CMS_CATEGORYID like '%;"
				+ category + ";%' ");

		if (StringUtils.isNotBlank(searchName)) {
			sql.append("  AND ( CONVERTTOUNSIGN(lower(c.title)) like CONVERTTOUNSIGN(lower(:searchName)) ");
			// sql.append(" OR CONVERTTOUNSIGN(lower(DBMS_LOB.SUBSTR(c.content,3000,1))) like
			// CONVERTTOUNSIGN(lower(:searchName)) ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHORT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append("  OR  CONVERTTOUNSIGN(lower(c.SHARECOMMENT)) like CONVERTTOUNSIGN(lower(:searchName))  ");
			sql.append(" ) ");
		}

		if (list != null && list.size() > 0) {
			sql.append("  AND ( ");
			Integer count = 0;
			for (Long long1 : list) {
				if (count == 0) {
					sql.append(" CMS_HOBBYYID like '%;" + long1 + ";%' ");
				} else {
					sql.append(" OR CMS_HOBBYYID like '%;" + long1 + ";%' ");
				}
				count++;
			}
			sql.append("  ) ");
		}

		if (customerId != null) {
			sql.append("  AND c.cmsId not in (select CMSID from CUSTOMERCMSREAD where CUSTOMERID =:customerId ) ");
		}

		String sqlCount = "select count(*) from (" + sql.toString() + ")";
		Query queryCount = getSession().createSQLQuery(sqlCount.toString());

		queryCount.setParameter("date", Utils.setTimeToZero(new Date()));
		if (StringUtils.isNotBlank(searchName)) {
			queryCount.setParameter("searchName", "%" + searchName.trim() + "%");
		}
		if (customerId != null) {
			queryCount.setParameter("customerId", customerId);
		}

		return Integer.valueOf(queryCount.uniqueResult().toString());

	}

	@Override
	@Transactional
	public void updateStatus(Long cmsId, Boolean check) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE CMS ");
		sql.append("SET STATUSID = :STATUSID ");
		sql.append("WHERE  CMSID = :CMSID ");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("CMSID", cmsId);
		if (check == false) {
			query.setParameter("STATUSID", 2);
		} else {
			query.setParameter("STATUSID", 1);
		}
		query.executeUpdate();

	}
}
