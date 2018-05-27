package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.BidderBean;
import vn.com.unit.fe_credit.bean.v1.BidderDto;
import vn.com.unit.fe_credit.entity.collection.Bidder;

public interface BidderDao {

	List<BidderDto> findAll();
	List<BidderDto> getBidderList(String email, String contractId);
	List<BidderDto> getBidderResult(String email/*, boolean isAdmin*/);
	Bidder findByBidderId(Long id);
	//BidderApproval updateApproval(Bidder bidder, BidderBean bidderBean);
	/**
	 * @param contractId
	 * @param repossessionId
	 * @return
	 */
	List<Bidder> getBidderByContractId(String contractId, Long repossessionId);

}
