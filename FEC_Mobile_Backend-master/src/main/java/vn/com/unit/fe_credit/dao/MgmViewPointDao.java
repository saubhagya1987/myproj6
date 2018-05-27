package vn.com.unit.fe_credit.dao;

import java.util.Map;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.entity.MgmSuggestedContactsPK;

public interface MgmViewPointDao extends GenericDAO<MgmSuggestedContacts, MgmSuggestedContactsPK> {

	Integer getFriendsBecomeLoans(Long id);

	Integer getRemainingPointsPreviousTerm(Long id);

	Integer getRemainingPoints(Long id);

	Integer getPointsHasConverted(Long id);

	Integer getTotalPoints(Long id);

	Integer getTotalFriends(Long id);

	Integer getFriendsValid(Long id);

	Map<String, Object> getCustomerPointSummary(long customerId) throws Exception;
}
