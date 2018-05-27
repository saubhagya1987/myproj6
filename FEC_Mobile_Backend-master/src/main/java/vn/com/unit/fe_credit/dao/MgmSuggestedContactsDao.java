package vn.com.unit.fe_credit.dao;

import java.util.List;
import java.util.Map;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.bean.MgmSuggestedContactsBean;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.entity.MgmSuggestedContactsPK;

public interface MgmSuggestedContactsDao extends GenericDAO<MgmSuggestedContacts, MgmSuggestedContactsPK> {

	MgmSuggestedContactsBean searchListFriendsByCustomerId(MgmSuggestedContactsBean bean, long customerId) throws Exception;

	List<Map<String, Object>> getListPushMessageWhenFriendBecomeLoan() throws Exception;
}
