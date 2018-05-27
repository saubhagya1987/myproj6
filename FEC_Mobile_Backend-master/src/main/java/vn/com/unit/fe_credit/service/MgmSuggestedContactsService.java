package vn.com.unit.fe_credit.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.fe_credit.bean.MgmSuggestedContactsBean;

public interface MgmSuggestedContactsService {

	MgmSuggestedContactsBean searchListFriendsByCustomerId(MgmSuggestedContactsBean bean, long customerId) throws Exception;

	List<Map<String, Object>> getListPushMessageWhenFriendBecomeLoan() throws Exception;
}
