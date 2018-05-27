package vn.com.unit.fe_credit.service.v1;

import java.util.Locale;

import vn.com.unit.fe_credit.bean.v1.BidderBean;

public interface BidderService {
	public BidderBean getBidderList(String contractId);

	
//  void dummy(String contractId, Long repossessionId);
	public void updateBidderStatus(BidderBean bidderBean, Locale locale);

}
