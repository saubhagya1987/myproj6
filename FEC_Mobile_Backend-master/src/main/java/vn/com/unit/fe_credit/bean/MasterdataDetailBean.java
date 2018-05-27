package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Masterdata;
import vn.com.unit.fe_credit.entity.MasterdataDetal;

public class MasterdataDetailBean extends AbstractBean<MasterdataDetal> {
	private List<Masterdata> listMasterdata;
	private String searchField;
	private String succesorfail;
	
	public List<Masterdata> getListMasterdata() {
		return listMasterdata;
	}

	public void setListMasterdata(List<Masterdata> listMasterdata) {
		this.listMasterdata = listMasterdata;
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

}
