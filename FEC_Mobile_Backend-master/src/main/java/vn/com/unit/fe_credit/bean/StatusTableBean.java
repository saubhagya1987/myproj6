package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.StatusTable;

public class StatusTableBean extends AbstractBean<StatusTable>{
	private List<StatusTable> listStatusTable;

	public List<StatusTable> getListStatusTable() {
		return listStatusTable;
	}

	public void setListStatusTable(List<StatusTable> listStatusTable) {
		this.listStatusTable = listStatusTable;
	}
	
	

}
