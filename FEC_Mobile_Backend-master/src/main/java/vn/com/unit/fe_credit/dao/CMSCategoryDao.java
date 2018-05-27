package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.CMSCategory;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CMSCategoryDao extends GenericDAO<CMSCategory, Long> {
	List<CMSCategory> findInStatic();

	List<CMSCategory> findById(String[] list);
}
