package vn.com.unit.fe_credit.service;

import vn.com.unit.fe_credit.bean.MgmPointConversionHistoryBean;

public interface MgmPointConversionHistoryService {
	MgmPointConversionHistoryBean search(MgmPointConversionHistoryBean bean, Long customerId);
}
