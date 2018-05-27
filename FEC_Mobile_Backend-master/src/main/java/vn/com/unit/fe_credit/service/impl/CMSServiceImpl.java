package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.CMSBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.config.SystemConfig.MessageStatus;
import vn.com.unit.fe_credit.dao.CMSDao;
import vn.com.unit.fe_credit.dao.MessageDao;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.ViewCms;
import vn.com.unit.fe_credit.service.CMSCategoryService;
import vn.com.unit.fe_credit.service.CMSService;
import vn.com.unit.fe_credit.service.CustomerHobbyService;
import vn.com.unit.fe_credit.service.CustomerWishService;
import vn.com.unit.fe_credit.utils.Utils;

@Service("CMSService")
@Transactional(readOnly = true)
public class CMSServiceImpl implements CMSService {

	@Autowired
	private CMSDao cmsDao;

	@Autowired
	private UserProfile userProfile;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	CMSCategoryService cMSCategoryService;

	@Autowired
	CustomerWishService customerWishService;

	@Autowired
	CustomerHobbyService customerHobbyService;

	@Autowired
	MessageDao messagesDao;

	public CMSServiceImpl() {
		super();
	}

	@Override
	public List<CMSEmtity> findAllex() {
		return cmsDao.findAll();
	}

	@Override
	public CMSBean search(CMSBean bean) {
		Search search = new Search(CMSEmtity.class);
		String CMSsearch = ";";
		CMSsearch += bean.getCategory();
		CMSsearch += ";";
		if (bean.getCreateDayfrom() != null) {
			search.addFilterGreaterOrEqual("createDate", Utils.setTimeToZero(bean.getCreateDayfrom()));// lom hon
		}
		if (bean.getCreateDayto() != null) {
			search.addFilterLessOrEqual("createDate", Utils.setTimeToMax(bean.getCreateDayto()));// nhỏ hon
		}
		if (bean.getStatus() == -1) {

		} else {
			search.addFilterEqual("statusTable.status_tableId", bean.getStatus());
		}
		if (bean.getCategory() == -1) {

		} else {
			search.addFilterOr(Filter.ilike("cmsCategory", "%" + CMSsearch + "%"));
		}
		if (StringUtils.isNotEmpty(bean.getTag())) {
			search.addFilterOr(Filter.ilike("tag", "%" + bean.getTag().trim() + "%"));
		}

		// search.addFilterEqual("statusTable.status_tableId", value);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		search.addSort("ordercms", false);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(), "desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<CMSEmtity> searchResult = cmsDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		if (searchResult != null && searchResult.getResult().size() > 0) {
			for (CMSEmtity item : searchResult.getResult()) {
				String cmsCategoryList = "";
				for (String str : item.getCmsCategory().split(";")) {
					if (StringUtils.isNotEmpty(str)) {
						CMSCategory category = cMSCategoryService.findById(Long.parseLong(str));
						if (category != null) {
							cmsCategoryList += category.getCode() + " ;";
						}
					}
				}
				if (StringUtils.isNotEmpty(cmsCategoryList)) {
					cmsCategoryList = cmsCategoryList.substring(0, cmsCategoryList.length() - 2);
				}
				item.setCmsCategoryList(cmsCategoryList);
			}
		}
		return bean;
	}

	@Override
	@Transactional
	public boolean saveCMS(CMSEmtity entity) {

		if (entity.getCmsId() == null) {
			entity.setCreated_date(new Date());
			if (userProfile.getAccount() != null) {
				entity.setCreated_by(userProfile.getAccount().getUsername());
			}
		} else {
			entity.setUpdate_date(new Date());
			if (userProfile.getAccount() != null) {
				entity.setUpdate_by(userProfile.getAccount().getUsername());
			}
		}
		return cmsDao.save(entity);

	}

	@Override
	public CMSEmtity findById(Long CMSId) {
		return cmsDao.find(CMSId);
	}

	@Override
	public CMSBean searchAll(CMSBean bean) {
		Search search = new Search(CMSEmtity.class);
		if (bean.getStatus() == -1) {
			search.addFilterEqual("statusTable.status_tableId", 1);

		} else {
			search.addFilterEqual("statusTable.status_tableId", bean.getStatus());
		}

		// search.addFilterEqual("statusTable.status_tableId", value);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		search.addSort("ordercms", false);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(), "desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<CMSEmtity> searchResult = cmsDao.searchAndCount(search);

		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());

		if (searchResult != null && searchResult.getResult().size() > 0) {
			for (CMSEmtity item : searchResult.getResult()) {
				String cmsCategoryList = "";
				for (String str : item.getCmsCategory().split(";")) {
					if (StringUtils.isNotEmpty(str)) {
						CMSCategory category = cMSCategoryService.findById(Long.parseLong(str));
						if (category != null) {
							cmsCategoryList += category.getCode() + " ;";
						}
					}
				}
				if (StringUtils.isNotEmpty(cmsCategoryList)) {
					cmsCategoryList = cmsCategoryList.substring(0, cmsCategoryList.length() - 2);
				}
				item.setCmsCategoryList(cmsCategoryList);
			}
		}

		return bean;
	}

	@Override
	public List<ViewCms> getTitlebyCategory(String category, Long customerId, String searchName, Integer page, Integer limit) {

		CMSCategory cmsCategory = cMSCategoryService.findByCode(category);
		List<CMSEmtity> lstCMS = cmsDao.getTitleEXCategory(cmsCategory.getCms_categoryId().toString(), searchName, page, limit);

		List<ViewCms> viewCmsS = new ArrayList<ViewCms>();
		for (CMSEmtity cms : lstCMS) {
			ViewCms viewCms = new ViewCms();
			String[] listCategory = cms.getCmsCategory().split(";");
			List<CMSCategory> lstCMSCategory = cMSCategoryService.findById(listCategory);
			List<String> ortherCat = new ArrayList<String>();
			for (CMSCategory cat : lstCMSCategory) {
				ortherCat.add(cat.getCode());
			}
			viewCms.setCmsId(cms.getCmsId().toString());
			if (StringUtils.isNotEmpty(cms.getImage())) {
				viewCms.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage());
			}
			if (StringUtils.isNotEmpty(cms.getCmsHobby())) {
				String[] listhobby = cms.getCmsHobby().split(";");
				viewCms.setHobbys(Arrays.asList(listhobby));
			}

			viewCms.setOrtherCat(ortherCat);
			viewCms.setTitle(cms.getTitle());
			viewCms.setTag(cms.getTag());
			if (StringUtils.isNotEmpty(cms.getImageLong())) {
				viewCms.setImagesquare(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
				viewCms.setImagesquare_VI(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
			}
			if (StringUtils.isNotEmpty(cms.getImage_long_en())) {
				viewCms.setImagesquare_EN(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage_long_en());
			}
			viewCms.setSharecomment(cms.getShareComment());
			viewCms.setUrl(cms.getUrl());
			List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(customerId, cms.getCmsId());
			if (customerId != null && customerWishs != null && customerWishs.size() > 0) {
				viewCms.setWishlist(true);
			}
			viewCms.setCreatedDate(cms.getCreateDate());
			viewCmsS.add(viewCms);
		}

		return viewCmsS;
	}

	@Override
	public List<ViewCms> getOfferByCustomer(String category, Long customerId, String searchName, Integer page, Integer limit) {
		CMSCategory cmsCategory = cMSCategoryService.findByCode(category);
		List<ViewCms> viewCmsS = new ArrayList<ViewCms>();
		if (customerId != null) {

			List<CustomerHobby> customerHobbies = customerHobbyService.findByCustomerId(customerId);

			List<Long> list = new ArrayList<Long>();

			for (CustomerHobby item : customerHobbies) {
				list.add(item.getHobbyId());
			}

			List<CMSEmtity> lstCMS = cmsDao.getTitleEXCategoryOffer(cmsCategory.getCms_categoryId().toString(), list, searchName, page, limit);
			for (CMSEmtity cms : lstCMS) {
				ViewCms viewCms = new ViewCms();
				String[] listCategory = cms.getCmsCategory().split(";");
				List<CMSCategory> lstCMSCategory = cMSCategoryService.findById(listCategory);
				List<String> ortherCat = new ArrayList<String>();
				for (CMSCategory cat : lstCMSCategory) {
					ortherCat.add(cat.getCode());
				}
				viewCms.setCmsId(cms.getCmsId().toString());
				if (StringUtils.isNotEmpty(cms.getImage())) {
					viewCms.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage());
				}
				if (StringUtils.isNotEmpty(cms.getCmsHobby())) {
					String[] listhobby = cms.getCmsHobby().split(";");
					List<String> hobbys = new ArrayList<String>();
					for (String string : listhobby) {
						if (StringUtils.isNotEmpty(string)) {
							hobbys.add(string);
						}
					}
					viewCms.setHobbys(hobbys);
				}

				viewCms.setOrtherCat(ortherCat);
				viewCms.setTitle(cms.getTitle());
				viewCms.setTag(cms.getTag());
				if (StringUtils.isNotEmpty(cms.getImageLong())) {
					viewCms.setImagesquare(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
					viewCms.setImagesquare_VI(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
				}
				if (StringUtils.isNotEmpty(cms.getImage_long_en())) {
					viewCms.setImagesquare_EN(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage_long_en());
				}
				viewCms.setSharecomment(cms.getShareComment());
				viewCms.setUrl(cms.getUrl());
				List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(customerId, cms.getCmsId());
				if (customerId != null && customerWishs != null && customerWishs.size() > 0) {
					viewCms.setWishlist(true);
				}
				viewCms.setCreatedDate(cms.getCreateDate());

				viewCmsS.add(viewCms);
			}

		}
		return viewCmsS;
	}

	@Override
	public List<ViewCms> getTitlebyCategoryByOne(String category, Long customerId, String searchName, Long cmsId, String preornext) {
		CMSCategory cmsCategory = cMSCategoryService.findByCode(category);
		List<CMSEmtity> lstCMS = cmsDao.getTitleEXCategoryByOne(cmsCategory.getCms_categoryId().toString(), searchName, cmsId, preornext);

		List<ViewCms> viewCmsS = new ArrayList<ViewCms>();
		for (CMSEmtity cms : lstCMS) {
			ViewCms viewCms = new ViewCms();
			String[] listCategory = cms.getCmsCategory().split(";");
			List<CMSCategory> lstCMSCategory = cMSCategoryService.findById(listCategory);
			List<String> ortherCat = new ArrayList<String>();
			for (CMSCategory cat : lstCMSCategory) {
				ortherCat.add(cat.getCode());
			}
			viewCms.setCmsId(cms.getCmsId().toString());
			if (StringUtils.isNotEmpty(cms.getImage())) {
				viewCms.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage());
			}
			if (StringUtils.isNotEmpty(cms.getCmsHobby())) {
				String[] listhobby = cms.getCmsHobby().split(";");
				List<String> hobbys = new ArrayList<String>();
				for (String string : listhobby) {
					if (StringUtils.isNotEmpty(string)) {
						hobbys.add(string);
					}
				}
				viewCms.setHobbys(hobbys);
			}

			viewCms.setOrtherCat(ortherCat);
			viewCms.setTitle(cms.getTitle());
			viewCms.setTag(cms.getTag());
			if (StringUtils.isNotEmpty(cms.getImageLong())) {
				viewCms.setImagesquare(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
				viewCms.setImagesquare_VI(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
			}
			if (StringUtils.isNotEmpty(cms.getImage_long_en())) {
				viewCms.setImagesquare_EN(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage_long_en());
			}
			viewCms.setSharecomment(cms.getShareComment());
			viewCms.setUrl(cms.getUrl());
			List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(customerId, cms.getCmsId());
			if (customerId != null && customerWishs != null && customerWishs.size() > 0) {
				viewCms.setWishlist(true);
			}
			viewCms.setCreatedDate(cms.getCreateDate());
			viewCmsS.add(viewCms);
		}

		return viewCmsS;
	}

	@Override
	public List<ViewCms> getOfferByCustomerByOne(String category, Long customerId, String searchName, Long cmsId, String preornext) {
		CMSCategory cmsCategory = cMSCategoryService.findByCode(category);
		List<ViewCms> viewCmsS = new ArrayList<ViewCms>();
		if (customerId != null) {

			List<CustomerHobby> customerHobbies = customerHobbyService.findByCustomerId(customerId);

			List<Long> list = new ArrayList<Long>();

			for (CustomerHobby item : customerHobbies) {
				list.add(item.getHobbyId());
			}

			List<CMSEmtity> lstCMS = cmsDao.getTitleEXCategoryOfferByOne(cmsCategory.getCms_categoryId().toString(), list, searchName, cmsId,
					preornext);
			for (CMSEmtity cms : lstCMS) {
				ViewCms viewCms = new ViewCms();
				String[] listCategory = cms.getCmsCategory().split(";");
				List<CMSCategory> lstCMSCategory = cMSCategoryService.findById(listCategory);
				List<String> ortherCat = new ArrayList<String>();
				for (CMSCategory cat : lstCMSCategory) {
					ortherCat.add(cat.getCode());
				}
				viewCms.setCmsId(cms.getCmsId().toString());
				if (StringUtils.isNotEmpty(cms.getImage())) {
					viewCms.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage());
				}
				if (StringUtils.isNotEmpty(cms.getCmsHobby())) {
					String[] listhobby = cms.getCmsHobby().split(";");
					List<String> hobbys = new ArrayList<String>();
					for (String string : listhobby) {
						if (StringUtils.isNotEmpty(string)) {
							hobbys.add(string);
						}
					}
					viewCms.setHobbys(hobbys);
				}

				viewCms.setOrtherCat(ortherCat);
				viewCms.setTitle(cms.getTitle());
				viewCms.setTag(cms.getTag());
				if (StringUtils.isNotEmpty(cms.getImageLong())) {
					viewCms.setImagesquare(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
					viewCms.setImagesquare_VI(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImageLong());
				}
				if (StringUtils.isNotEmpty(cms.getImage_long_en())) {
					viewCms.setImagesquare_EN(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage_long_en());
				}
				viewCms.setSharecomment(cms.getShareComment());
				viewCms.setUrl(cms.getUrl());
				List<CustomerWish> customerWishs = customerWishService.findByCustomerWish(customerId, cms.getCmsId());
				if (customerId != null && customerWishs != null && customerWishs.size() > 0) {
					viewCms.setWishlist(true);
				}
				viewCms.setCreatedDate(cms.getCreateDate());

				viewCmsS.add(viewCms);
			}

		}
		return viewCmsS;
	}

	@Override
	public Integer getStatistic(String category, Long customerId, String searchName, Integer page, Integer limit) {

		List<Long> list = new ArrayList<Long>();
		if (customerId != null) {
			List<CustomerHobby> customerHobbies = customerHobbyService.findByCustomerId(customerId);
			if (category.equalsIgnoreCase("OFFERS")) {
				for (CustomerHobby item : customerHobbies) {
					list.add(item.getHobbyId());
				}
			}
		}

		CMSCategory cmsCategory = cMSCategoryService.findByCode(category);
		Integer number = cmsDao.getStatistic(cmsCategory.getCms_categoryId().toString(), customerId, list, searchName, page, limit);
		return number;
	}

	@Override
	public CMSEmtity getContentByID(Long bannerId) {
		CMSEmtity cms = cmsDao.find(bannerId);

		String[] listCategory = cms.getCmsCategory().split(";");
		List<CMSCategory> lstCMSCategory = cMSCategoryService.findById(listCategory);
		List<String> ortherCat = new ArrayList<String>();
		for (CMSCategory cat : lstCMSCategory) {
			ortherCat.add(cat.getCode());
		}
		cms.setOrtherCat(ortherCat);
		return cms;
	}

	@Override
	public List<ViewCms> getListCMSByCustomer(Long customerID) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		List<CustomerWish> customerWishs = customerWishService.findByCustomer(customerID);
		List<ViewCms> viewCmsS = new ArrayList<ViewCms>();
		for (CustomerWish customerWish : customerWishs) {

			CMSEmtity cms = cmsDao.find(customerWish.getWishId());
			if (cms != null) {
				ViewCms viewCms = new ViewCms();
				String[] listCategory = cms.getCmsCategory().split(";");
				List<CMSCategory> lstCMSCategory = cMSCategoryService.findById(listCategory);
				List<String> ortherCat = new ArrayList<String>();
				for (CMSCategory cat : lstCMSCategory) {
					ortherCat.add(cat.getCode());
				}
				viewCms.setCmsId(cms.getCmsId().toString());
				viewCms.setImagePath(systemConfig.getConfig(SystemConfig.URL_DEFAULT) + "/ajax/download?fileName=" + cms.getImage());
				viewCms.setOrtherCat(ortherCat);
				viewCms.setTitle(cms.getTitle());
				viewCms.setSharecomment(cms.getShareComment());
				viewCms.setUrl(cms.getUrl());
				viewCms.setTag(cms.getTag());
				viewCms.setCreatedDate(cms.getCreateDate());
				viewCmsS.add(viewCms);
			}
		}

		return viewCmsS;
	}

	@Override
	@Transactional
	public void updateStatus(Long cmsId, Boolean check) {
		cmsDao.updateStatus(cmsId, check);

	}

	@Override
	public Map<String, String> getMobileAppTerm(long categoryId) throws Exception {
		return cmsDao.getMobileAppTerm(categoryId);
	}

	@Override
	@Transactional
	public Long pushMessageToAllDevice(long cmsId, String title, String content) {

		MessageDashBoard messageDashBoard = new MessageDashBoard();
		messageDashBoard.setSubject(title);
		messageDashBoard.setContent(content);
		messageDashBoard.setCmsId(cmsId);
		messageDashBoard.setType(10);
		messageDashBoard.setIsRead(0);
		messageDashBoard.setStatus(MessageStatus.SENT.getIntValue());
		messageDashBoard.setIsMsgUser(0);
		messageDashBoard.setCreatedDate(new Date());
		messageDashBoard.setDeviceID(null);
		messageDashBoard.setIsParent(true);
		messageDashBoard.setACCOUNT(userProfile.getAccount());
		messagesDao.save(messageDashBoard);

		return messageDashBoard.getMessageID();

	}
}
