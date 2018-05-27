package vn.com.unit.fe_credit.service;

import java.util.Map;

public interface MgmViewPointService {

	Integer getFriendsBecomeLoans(Long id);

	Integer getRemainingPointsPreviousTerm(Long id);

	Integer getRemainingPoints(Long id);

	Integer getPointsHasConverted(Long id);

	Integer getTotalPoints(Long id);

	Integer getTotalFriends(Long id);

	Integer getFriendsValid(Long id);

	Map<String, Object> getCustomerPointSummary(long customerId) throws Exception;

}
