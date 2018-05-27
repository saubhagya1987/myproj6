package vn.com.unit.fe_credit.dao;

import java.util.List;
import java.util.Map;

import vn.com.unit.fe_credit.entity.CMSEmtity;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface CMSDao extends GenericDAO<CMSEmtity, Long> {

	List<CMSEmtity> getTitlebyCategory(String category);
	List<CMSEmtity> getTitleEXCategory(String category, String searchName, Integer page, Integer limit);
	List<CMSEmtity> getTitleEXCategoryOffer(String category, List<Long> list, String searchName, Integer page, Integer limit);
	Integer getStatistic(String category, Long customerId, List<Long> list, String searchName, Integer page, Integer limit);
	List<CMSEmtity> getTitleEXCategoryOfferByOne(String category, List<Long> list, String searchName, Long cmsId, String preornext);
	List<CMSEmtity> getTitleEXCategoryByOne(String category, String searchName, Long cmsId, String preornext);
	void updateStatus(Long cmsId,Boolean check);
	Map<String, String> getMobileAppTerm(long categoryId) throws Exception;
}
