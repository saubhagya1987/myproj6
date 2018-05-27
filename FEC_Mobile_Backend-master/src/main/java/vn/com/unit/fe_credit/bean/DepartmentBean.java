package vn.com.unit.fe_credit.bean;


import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;

public class DepartmentBean extends AbstractBean<Department> {

	public DepartmentBean(){
		this.entity= new Department();
/*		this.entity.setRegions(new Region());
		this.entity.setCitys(new City());
		this.entity.setDistricts(new District());*/
	}
	private String searchContent;
	private List<DepartmentTreeBean> tree;
	private Long parentId;
	private Long branchId;
	private String jsonString;
	private String departmentCodeName;
	private Map<Long, Account> mapAccount=new HashedMap();
	
	
	private Long currentNode;
	

	private Account acc;


	
	public Long getCurrentNode() {
		return currentNode;
	}



	public void setCurrentNode(Long currentNode) {
		this.currentNode = currentNode;
	}



	public Map<Long, Account> getMapAccount() {
		return mapAccount;
	}



	public void setMapAccount(Map<Long, Account> mapAccount) {
		this.mapAccount = mapAccount;
	}



	public Account getAcc() {
		return acc;
	}



	public void setAcc(Account acc) {
		this.acc = acc;
	}



	public String getDepartmentCodeName() {
		return departmentCodeName;
	}



	public void setDepartmentCodeName(String departmentCodeName) {
		this.departmentCodeName = departmentCodeName;
	}



	public String getSearchContent() {
		return searchContent;
	}



	public void setSearchContent(String searchContent) {
		this.searchContent = searchContent;
	}



	public List<DepartmentTreeBean> getTree() {
		return tree;
	}



	public void setTree(List<DepartmentTreeBean> tree) {
		this.tree = tree;
	}



	public Long getParentId() {
		return parentId;
	}



	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}



	public String getJsonString() {
		return jsonString;
	}



	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}


	



	
}
