package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.CMSCategoryBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.CMSCategoryDao;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.service.CMSCategoryService;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("CMS_CategoryService")
@Transactional(readOnly = true)
public class CMSCategoryServiceImpl implements CMSCategoryService{

	@Autowired
	private UserProfile userProfile;
	@Autowired
	private CMSCategoryDao cms_CategoryDao;

	public CMSCategoryServiceImpl() {
		super();
	}
	
	
	
	@Override
	public List<CMSCategory> findAllex() {
		// TODO Auto-generated method stub
		return cms_CategoryDao.findAll();
	}

	@Override
	public CMSCategoryBean search(CMSCategoryBean bean) {
		Search search = new Search(CMSCategory.class);
		 if (StringUtils.isNotEmpty(bean.getSearchField())) {
		 search.addFilterOr(
		 Filter.ilike("code", "%" + bean.getSearchField().trim() + "%"),
		 Filter.ilike("name", "%" + bean.getSearchField().trim() + "%"));
		
		 }

		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		search.addSort("cms_categoryId", true);
		SearchResult<CMSCategory> searchResult = cms_CategoryDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void saveCMS_Category(CMSCategory entity) {
		if(entity.getCms_categoryId() == null){
			entity.setCreated_date(new Date());
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		cms_CategoryDao.save(entity);

		
	}

	@Override
	public CMSCategory findById(Long CMS_CategoryId) {
		return cms_CategoryDao.find(CMS_CategoryId);
	}



	@Override
	public void saveCMSCategory(CMSCategory entity) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public CMSCategoryBean findByCode(CMSCategoryBean bean) {
		Search search = new Search(CMSCategory.class);
		search.addFilterEqual("code", bean.getEntity().getCode());

		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<CMSCategory> searchResult = cms_CategoryDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}
	
	@Override
	public List<CMSCategory> findByNotInCode(String code){
		Search search = new Search(CMSCategory.class);
		List<String> codes = new ArrayList<String>();
		codes.add(code.toUpperCase());
		search.addFilterNotIn("code", codes);
		List<CMSCategory> result = cms_CategoryDao.search(search);

		return result;
	}



	@Override
	public List<CMSCategory> findInStatic() {
		return cms_CategoryDao.findInStatic();
	}



	@Override
	public List<CMSCategory> searchInStatic() {
		Search search = new Search(CMSCategory.class);
		 search.addFilterOr(
		 Filter.ilike("statusTable.status_text","Activated"));
		 List<CMSCategory> searchResult = cms_CategoryDao.search(search);
		return searchResult;
	}



	@Override
	public List<CMSCategory> findBycmsIdLst(List<Long> cmsIdLst) {
		  Search search = new Search(CMSCategory.class);
	        search.addField("stockId");
	        search.addField("code");
	        search.addField("name");
//	        if(cmsIdLst != null && !cmsIdLst.isEmpty()) {
//	            search.addFilterIn("region.regionId", cmsIdLst);
//	        }
	        List<Object[]> list = cms_CategoryDao.search(search);
	        List<CMSCategory> listStock = new ArrayList<CMSCategory>();
	        for (Object[] objects : list) {
	        	CMSCategory cmsCategory = new CMSCategory();
	        	cmsCategory.setCms_categoryId(Long.parseLong(objects[0].toString()));
	        	cmsCategory.setCode((String) objects[1]);
	        	cmsCategory.setName((String) objects[2]);
	            listStock.add(cmsCategory);
	        }
	        return listStock;
	}



	@Override
	public CMSCategory findByCode(String category) {
		// TODO Auto-generated method stub
		Search search = new Search(CMSCategory.class);
		search.addFilterEqual("code", category.toUpperCase());
		List<CMSCategory> searchResult = cms_CategoryDao.search(search);
		if (searchResult.size() > 0)
			return searchResult.get(0);
		return null;
	}



	@Override
	public List<CMSCategory> findById(String[] list) {
		// TODO Auto-generated method stub
		return cms_CategoryDao.findById(list);
	}



	@Override
	public CMSCategory findCMSCategoryCode(String code) {
		Search search = new Search(CMSCategory.class);
		if (StringUtils.isNotEmpty(code)) {
			search.addFilterEqual("code", code);
		}
		return cms_CategoryDao.searchUnique(search);
	}


/*	@Override
	public List<String> searchFollowId(List<Long> ids) {
		// TODO Auto-generated method stub
		Search search = new Search(CMSCategory.class);
		search.addField("code");
		search.addFilterIn("cms_categoryId", ids);
		List<String> searchResult = cms_CategoryDao.search(search);
		return searchResult;
		 
	}*/



	

}
