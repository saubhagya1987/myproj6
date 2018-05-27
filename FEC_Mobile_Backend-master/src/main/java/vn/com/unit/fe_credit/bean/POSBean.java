package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.entity.StatusTable;

public class POSBean extends AbstractBean<PosEmtity> {
	private List<PosEmtity> listPOS;
	private String searchField;
	private String succesorfail;
	private List<StatusTable> listStatusTable;
	private String posAddress;
	public List<PosEmtity> getListPOS() {
		return listPOS;
	}

	public void setListPOS(List<PosEmtity> listPOS) {
		this.listPOS = listPOS;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public List<StatusTable> getListStatusTable() {
		return listStatusTable;
	}

	public void setListStatusTable(List<StatusTable> listStatusTable) {
		this.listStatusTable = listStatusTable;
	}

	public String getSuccesorfail() {
		return succesorfail;
	}

	public void setSuccesorfail(String succesorfail) {
		this.succesorfail = succesorfail;
	}

	public String getPosAddress() {
		return posAddress;
	}

	public void setPosAddress(String posAddress) {
		this.posAddress = posAddress;
	}



}
