package vn.com.unit.fe_credit.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.SystemSettingDao;
import vn.com.unit.fe_credit.entity.SystemSetting;
import vn.com.unit.fe_credit.service.SystemSettingService;

import com.googlecode.genericdao.search.Search;

@Service
@Transactional(readOnly = true)
public class SystemSettingServiceImpl implements SystemSettingService {
	@Autowired
	SystemSettingDao systemSettingDao;

	@Override
	public List<SystemSetting> findAll() {
		return systemSettingDao.findAll();
	}
	@Transactional
	@Override
	public void saveOrUpdate(SystemSetting systemSetting) {
		systemSettingDao.save(systemSetting);
	}

	@Override
	public SystemSetting findKey(String key) {
		Search search = new Search();
		search.addFilterEqual("key", key);
		List<SystemSetting> result = systemSettingDao.search(search);
		if (result.size() > 0)
			return result.get(0);
		return null;
	}
	
}
