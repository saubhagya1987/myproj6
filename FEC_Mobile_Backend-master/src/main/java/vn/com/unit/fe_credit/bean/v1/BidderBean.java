package vn.com.unit.fe_credit.bean.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.AbstractBean;
import vn.com.unit.fe_credit.entity.collection.Bidder;

public class BidderBean extends AbstractBean<Bidder>{
	private Integer bidderId;	
	private String contractId;
	private Long id;
	private String remark;
	private String status;
	private List<BidderDto> bidderList;
	public Integer getBidderId() {
		return bidderId;
	}
	public void setBidderId(Integer bidderId) {
		this.bidderId = bidderId;
	}
	public List<BidderDto> getBidderList() {
		return bidderList;
	}
	public void setBidderList(List<BidderDto> bidderList) {
		this.bidderList = bidderList;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
