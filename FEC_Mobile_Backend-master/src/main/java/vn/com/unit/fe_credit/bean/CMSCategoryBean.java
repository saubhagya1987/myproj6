package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.StatusTable;

public class CMSCategoryBean extends AbstractBean<CMSCategory> {
	private List<CMSCategory> listCMS_Category;
	private String searchField;
	private String succesorfail;
	private List<StatusTable> listStatusTable;

	public List<CMSCategory> getListCMS_Category() {
		return listCMS_Category;
	}

	public void setListCMS_Category(List<CMSCategory> listCMS_Category) {
		this.listCMS_Category = listCMS_Category;
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

}
