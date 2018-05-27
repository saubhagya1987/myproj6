package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.CMSCategoryBean;
import vn.com.unit.fe_credit.entity.CMSCategory;

public interface CMSCategoryService {
	List<CMSCategory> findAllex();
	List<CMSCategory> findInStatic();
	List<CMSCategory> findBycmsIdLst(List<Long> cmsIdLst);
	CMSCategoryBean search(CMSCategoryBean bean);
	List<CMSCategory> searchInStatic();
	CMSCategoryBean findByCode(CMSCategoryBean bean);
	CMSCategory findByCode(String category);
	void saveCMS_Category(CMSCategory entity);
	CMSCategory findById(Long CMS_CategoryId);
	void saveCMSCategory(CMSCategory entity);
	List<CMSCategory> findByNotInCode(String code);
	List<CMSCategory> findById(String[] list);
	CMSCategory findCMSCategoryCode(String code);

}
