package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.MasterdataBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.MasterdataDAO;
import vn.com.unit.fe_credit.dao.MasterdataDetailDAO;
import vn.com.unit.fe_credit.entity.Masterdata;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.fe_credit.service.MasterdataService;
import vn.com.unit.webservice.Hotline;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("MasterdataService")
@Transactional(readOnly = true)
public class MasterdataServiceImpl implements MasterdataService {
	@Autowired
	private MasterdataDetailDAO masterdataDetailDAO;
	
	@Autowired
	private MasterdataDAO masterdataDao;
	
	@Autowired
	private UserProfile userProfile;
	
	public MasterdataServiceImpl() {
		super();
	}

	@Override
	public List<Masterdata> findAllex() {

		return masterdataDao.findAll();
	}

	@Override
	public MasterdataBean search(MasterdataBean bean) {
		Search search = new Search(Masterdata.class);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		search.addSort("masterdataId", true);
		SearchResult<Masterdata> searchResult = masterdataDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void saveMasterdata(Masterdata entity) {
		if(entity.getMasterdataId() == null){
			entity.setCreated_date(new Date());
			if(userProfile.getAccount() != null)
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			if(userProfile.getAccount() != null)
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		
		masterdataDao.save(entity);

	}

	@Override
	public Masterdata findById(Long masterdataId) {
		return masterdataDao.find(masterdataId);
	}


	@Override
	public MasterdataBean findByMasterdataName(MasterdataBean bean) {
		Search search = new Search(Masterdata.class);
		search.addFilterEqual("name", bean.getEntity().getName());

		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<Masterdata> searchResult = masterdataDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void deleteMasterdata(Long masterdataId) {
		masterdataDetailDAO.deleteMasterdataID(masterdataId);
		masterdataDao.removeById(masterdataId);
		
	}


	@Override
	public Masterdata findByName(Masterdata masterdata) {
		return null;
	}

	@Override
	public List<Hotline> findDetail(String masterType) {
		Search search = new Search(Masterdata.class);	
		search.addFilterEqual("name", masterType);
		List<Masterdata> searchResult = masterdataDao.search(search);	
		List<Hotline> hotlines = new ArrayList<Hotline>();
		for (Masterdata item : searchResult) {
			Hotline hotline = new Hotline();
			hotline.setCode(item.getName());
			hotline.setName(item.getDescription());
			hotlines.add(hotline);
		}
		return hotlines;
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
}
