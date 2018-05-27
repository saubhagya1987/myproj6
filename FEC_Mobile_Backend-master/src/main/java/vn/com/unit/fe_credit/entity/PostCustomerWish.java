package vn.com.unit.fe_credit.entity;

import java.util.List;

public class PostCustomerWish {
	private String customerId;
	private List<Wish> wishs;
	private String cmsId;

	public String getCmsId() {
		return cmsId;
	}

	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public List<Wish> getWishs() {
		return wishs;
	}

	public void setWishs(List<Wish> wishs) {
		this.wishs = wishs;
	}
}
