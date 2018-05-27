package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.fe_credit.bean.CMSBean;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.ViewCms;

public interface CMSService {

	List<CMSEmtity> findAllex();

	CMSBean search(CMSBean bean);

	CMSBean searchAll(CMSBean bean);

	CMSEmtity findById(Long CMSId);

	void updateStatus(Long cmsId, Boolean check);

	boolean saveCMS(CMSEmtity entity);

	List<ViewCms> getListCMSByCustomer(Long customerID);

	CMSEmtity getContentByID(Long bannerId);

	List<ViewCms> getTitlebyCategory(String category, Long customerId, String searchName, Integer page, Integer limit);

	List<ViewCms> getOfferByCustomer(String category, Long customerId, String searchName, Integer page, Integer limit);

	Integer getStatistic(String category, Long customerId, String searchName, Integer page, Integer limit);

	List<ViewCms> getOfferByCustomerByOne(String category, Long customerId, String searchName, Long cmsId, String preornext);

	List<ViewCms> getTitlebyCategoryByOne(String category, Long customerId, String searchName, Long cmsId, String preornext);

	Map<String, String> getMobileAppTerm(long categoryId) throws Exception;

	Long pushMessageToAllDevice(long cmsId, String title, String content);

}
