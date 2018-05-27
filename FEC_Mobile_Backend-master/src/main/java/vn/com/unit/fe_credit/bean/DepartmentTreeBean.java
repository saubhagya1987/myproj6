package vn.com.unit.fe_credit.bean;


import vn.com.unit.fe_credit.entity.Department;

public class DepartmentTreeBean extends TreeBean<DepartmentTreeBean>{
	private String parentName;
	private String costCenterNo;
	private String costCenterCode;
	private String manager;
	private String location;
	private String note;
	private String branchCode;
	private Department parentObj;
	private Integer rowNum;
	
	
	
	
	public String getCostCenterCode() {
		return costCenterCode;
	}

	public void setCostCenterCode(String costCenterCode) {
		this.costCenterCode = costCenterCode;
	}

	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getCostCenterNo() {
		return costCenterNo;
	}

	public void setCostCenterNo(String costCenterNo) {
		this.costCenterNo = costCenterNo;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Department getParentObj() {
		return parentObj;
	}

	public void setParentObj(Department parentObj) {
		this.parentObj = parentObj;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
}
