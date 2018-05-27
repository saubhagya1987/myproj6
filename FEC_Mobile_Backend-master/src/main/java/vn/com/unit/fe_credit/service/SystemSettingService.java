package vn.com.unit.fe_credit.service;


import java.util.List;

import vn.com.unit.fe_credit.entity.SystemSetting;

public interface SystemSettingService {
	public List<SystemSetting> findAll();

	void saveOrUpdate(SystemSetting systemSetting);

	SystemSetting findKey(String key);
}
