package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.MasterdataDetailBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.MasterdataDetailDAO;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.webservice.Hotline;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("MasterdataDetailService")
@Transactional(readOnly = true)
public class MasterdataDetailServiceImpl implements MasterdataDetailService {

	@Autowired
	private MasterdataDetailDAO masterdataDetailDAO;
	
	@Autowired
	private UserProfile userProfile;
	
	public MasterdataDetailServiceImpl() {
		super();
	}

	@Override
	public List<MasterdataDetal> findAllex() {

		return masterdataDetailDAO.findAll();
	}

	@Override
	public MasterdataDetailBean search(MasterdataDetailBean bean) {
		Search search = new Search(MasterdataDetal.class);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		
		 if (StringUtils.isNotEmpty(bean.getSearchField())) {
				search.addFilterOr(
				Filter.ilike("masterdata.name", "%" + bean.getSearchField().trim()+ "%"));
				 }
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),"desc".equalsIgnoreCase(bean.getSort()));
		}
		search.addSort("masterdatadetailId", true);
		SearchResult<MasterdataDetal> searchResult = masterdataDetailDAO.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}
	
	@Override
	public MasterdataDetal findNameAndMasterDuplicate(String name , Long masterId, Long masterdetailId) {
		Search search = new Search(MasterdataDetal.class);
		search.addFilterEqual("name", name);
		search.addFilterEqual("masterdata.masterdataId", masterId);
		search.addFilterNotIn("masterdatadetailId", masterdetailId);
		List<MasterdataDetal> searchResult = masterdataDetailDAO.search(search);
		if(searchResult != null && searchResult.size() > 0){
			return searchResult.get(0);
		}
		return null;
	}
	
	@Override
	public List<Hotline> findDetail(String masterType) {
		Search search = new Search(MasterdataDetal.class);	
		search.addFilterEqual("masterdata.name", masterType);
		List<MasterdataDetal> searchResult = masterdataDetailDAO.search(search);	
		List<Hotline> hotlines = new ArrayList<Hotline>();
		for (MasterdataDetal item : searchResult) {
			Hotline hotline = new Hotline();
			hotline.setCode(item.getName());
			hotline.setName(item.getDescription());			
			hotlines.add(hotline);
		}
		return hotlines;
	}

	@Override
	@Transactional
	public void saveMasterdataDetal(MasterdataDetal entity) {
		if(entity.getMasterdatadetailId() == null){
			entity.setCreated_date(new Date());
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		masterdataDetailDAO.save(entity);

	}

	@Override
	public MasterdataDetal findById(Long masterdataDetalId) {
		return masterdataDetailDAO.find(masterdataDetalId);
	}


	@Override
	public MasterdataDetailBean findByMasterdataDetailBeanName(MasterdataDetailBean bean) {
		Search search = new Search(MasterdataDetal.class);
		search.addFilterEqual("name", bean.getEntity().getName());

		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<MasterdataDetal> searchResult = masterdataDetailDAO.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void deleteMasterdataDetail(Long masterdataDetailId) {
		masterdataDetailDAO.removeById(masterdataDetailId);
		
	}

	/*@Override
	public List<Hobby> listHobbyByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		return hobbyDao.listHobbyByCustomer(customerId);
	}

	@Override
	public List<Hobby> searchInStatic() {
		Search search = new Search(Hobby.class);
		 search.addFilterOr(
		 Filter.ilike("status","Activated"));
		 List<Hobby> searchResult = hobbyDao.search(search);
		return searchResult;
	}*/
	
	@Override
	public MasterdataDetal getMasterData(String parentName, String childName) throws Exception {
		Search search = new Search(MasterdataDetal.class);
		search.addFilterEqual("masterdata.name", parentName);
		search.addFilterEqual("name", childName);
		List<MasterdataDetal> searchResult = masterdataDetailDAO.search(search);
		if (CollectionUtils.isNotEmpty(searchResult)) {
			return searchResult.get(0);
		} else {
			return null;
		}
	}

}
