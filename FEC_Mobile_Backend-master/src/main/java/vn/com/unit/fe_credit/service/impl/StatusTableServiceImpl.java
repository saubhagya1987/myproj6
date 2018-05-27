package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.dao.StatusTableDao;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.StatusTable;
import vn.com.unit.fe_credit.service.StatusTableService;


@Service("HobbyService")
@Transactional(readOnly = true)
public class StatusTableServiceImpl implements StatusTableService{

	
	@Autowired
	private StatusTableDao statusTableDao;

	public StatusTableServiceImpl() {
		super();
	}

	@Override
	public List<StatusTable> findAllex() {
		return statusTableDao.findAll();
	}

	@Override
	public StatusTable findActive() {
		Search search = new Search();
		search.addFilterEqual("status_tableId", 1);
		
		List<StatusTable> statusTables = statusTableDao.search(search);
		if(statusTables != null && statusTables.size() > 0){
			return statusTables.get(0);
		}
		return null;
	}

}
