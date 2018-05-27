package vn.com.unit.fe_credit.bean;

import vn.com.unit.fe_credit.entity.SearchMap;

public class SearchMapBean extends AbstractBean<SearchMap> {
	private String searchField;
	private String branchName;
	private String buyOrPay;

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getBuyOrPay() {
		return buyOrPay;
	}

	public void setBuyOrPay(String buyOrPay) {
		this.buyOrPay = buyOrPay;
	}
	
}
