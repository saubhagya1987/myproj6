package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.HobbyBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.HobbyDAO;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.service.HobbyService;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("hobbyService")
@Transactional(readOnly = true)
public class HobbyServiceImpl implements HobbyService {

	@Autowired
	private HobbyDAO hobbyDao;
	
	@Autowired
	private UserProfile userProfile;

	public HobbyServiceImpl() {
		super();
	}

	@Override
	public List<Hobby> findAllex() {

		return hobbyDao.findAll();
	}

	@Override
	public HobbyBean search(HobbyBean bean) {
		Search search = new Search(Hobby.class);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		 if (StringUtils.isNotEmpty(bean.getSearchField())) {
				search.addFilterOr(
				Filter.ilike("code", "%" + bean.getSearchField().trim()+ "%"),
				Filter.ilike("name", "%" + bean.getSearchField().trim()+ "%"));
				 }
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		search.addSort("hobbyId", true);
		SearchResult<Hobby> searchResult = hobbyDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void saveHobby(Hobby entity) {
		if(entity.getHobbyId() == null){
			entity.setCreated_date(new Date());
			if(userProfile.getAccount() != null)
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			if(userProfile.getAccount() != null)
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		hobbyDao.save(entity);

	}

	@Override
	public Hobby findById(Long hobbyId) {
		return hobbyDao.find(hobbyId);
	}

	@Override
	public HobbyBean findByHobbyCode(HobbyBean bean) {
		Search search = new Search(Hobby.class);
		search.addFilterEqual("code", bean.getEntity().getCode());

		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<Hobby> searchResult = hobbyDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
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
	}
}
