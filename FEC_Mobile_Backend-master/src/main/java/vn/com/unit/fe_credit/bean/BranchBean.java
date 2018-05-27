package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.entity.StatusTable;

public class BranchBean extends AbstractBean<BranchEmtity> {
	private List<BranchEmtity> listBearch;
	private String searchField;
	private String branchName;
	private String succesorfail;
	private List<StatusTable> listStatusTable;
	private List<PosEmtity> listPosEmtity;
	
	public List<BranchEmtity> getListBearch() {
		return listBearch;
	}
	public void setListBearch(List<BranchEmtity> listBearch) {
		this.listBearch = listBearch;
	}
	public String getSearchField() {
		return searchField;
	}
	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}
	public String getSuccesorfail() {
		return succesorfail;
	}
	public void setSuccesorfail(String succesorfail) {
		this.succesorfail = succesorfail;
	}
	public List<StatusTable> getListStatusTable() {
		return listStatusTable;
	}
	public void setListStatusTable(List<StatusTable> listStatusTable) {
		this.listStatusTable = listStatusTable;
	}
	public List<PosEmtity> getListPosEmtity() {
		return listPosEmtity;
	}
	public void setListPosEmtity(List<PosEmtity> listPosEmtity) {
		this.listPosEmtity = listPosEmtity;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
		
}
