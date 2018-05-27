package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.SearchMapDao;
import vn.com.unit.fe_credit.entity.SearchMap;
import vn.com.unit.fe_credit.service.SearchMapService;

@Service("SearchMapService")
@Transactional(readOnly = true)
public class SearchMapServiceImpl implements SearchMapService {

	@Autowired
	private SearchMapDao searchMapDao;

	public SearchMapServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public void save(SearchMap entity) {
		searchMapDao.save(entity);
	}

	



}
