package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.config.SystemConfig.Gender;
import vn.com.unit.fe_credit.config.SystemConfig.Status;
import vn.com.unit.fe_credit.config.SystemConfig.UserAccessFlag;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.StatusTable;
import vn.com.unit.fe_credit.entity.Team;

public class AccountBean extends AbstractBean<Account> {

	private List<Team> leftTeeprocurement;

	private List<Team> rightTeeprocurement;

	private List<String> userStatusList;

	private String searchField;

	private List<Account> accounts;

	private List<StatusTable> listStatusTable;

	private Integer status;

	private Integer isDefaultApproval; // 0: no , 1: yes

	private String image;

	private String signature;

	private String ext;

	private Long branchId;

	private List<Long> projectIds;

	private Long projectId;

	private Long stockId;

	private Boolean isSavedFail;

	private String passwordOld;

	private String passwordNew;

	private String passwordEnter;
	
	private String userCode;
	
	private String regionCode;
	
	private String provinceCode;
	
	private String joiningDate;
	
	private List<UserAccessFlag> UserAccessFlag;
	private List<Gender> genderList;
	private List<Status> statusList;
	
	

	public List<UserAccessFlag> getUserAccessFlag() {
		return UserAccessFlag;
	}

	public void setUserAccessFlag(List<UserAccessFlag> userAccessFlag) {
		UserAccessFlag = userAccessFlag;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(String joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getLineManager() {
		return lineManager;
	}

	public void setLineManager(String lineManager) {
		this.lineManager = lineManager;
	}

	private String lineManager;

	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public List<Team> getRightTeeprocurement() {
		return rightTeeprocurement;
	}

	public void setRightTeeprocurement(List<Team> rightTeeprocurement) {
		this.rightTeeprocurement = rightTeeprocurement;
	}

	public AccountBean() {
		this.entity = new Account();
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public List<Team> getLeftTeeprocurement() {
		return leftTeeprocurement;
	}

	public void setLeftTeeprocurement(List<Team> leftTeeprocurement) {
		this.leftTeeprocurement = leftTeeprocurement;
	}

	public List<String> getUserStatusList() {
		return userStatusList;
	}

	public void setUserStatusList(List<String> userStatusList) {
		this.userStatusList = userStatusList;
	}

	public Integer getIsDefaultApproval() {
		return isDefaultApproval;
	}

	public void setIsDefaultApproval(Integer isDefaultApproval) {
		this.isDefaultApproval = isDefaultApproval;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public Long getBranchId() {
		return branchId;
	}

	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public String getPasswordNew() {
		return passwordNew;
	}

	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}

	public String getPasswordEnter() {
		return passwordEnter;
	}

	public void setPasswordEnter(String passwordEnter) {
		this.passwordEnter = passwordEnter;
	}

	public Boolean getIsSavedFail() {
		return isSavedFail;
	}

	public void setIsSavedFail(Boolean isSavedFail) {
		this.isSavedFail = isSavedFail;
	}

	public List<StatusTable> getListStatusTable() {
		return listStatusTable;
	}

	public void setListStatusTable(List<StatusTable> listStatusTable) {
		this.listStatusTable = listStatusTable;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<Gender> getGenderList() {
		return genderList;
	}

	public void setGenderList(List<Gender> genderList) {
		this.genderList = genderList;
	}

	public List<Status> getStatusList() {
		return statusList;
	}

	public void setStatusList(List<Status> statusList) {
		this.statusList = statusList;
	}

}
