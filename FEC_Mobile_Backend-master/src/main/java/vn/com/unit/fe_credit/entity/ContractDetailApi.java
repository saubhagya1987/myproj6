package vn.com.unit.fe_credit.entity;

public class ContractDetailApi {

	private String appID;
	private String contractNum;
	private String CustomerName;
	private String CustomerIDCardNum;
	private String CustomerCellPhone;
	private String CustomerID;
	private String contractStatus;

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getCustomerIDCardNum() {
		return CustomerIDCardNum;
	}

	public void setCustomerIDCardNum(String customerIDCardNum) {
		CustomerIDCardNum = customerIDCardNum;
	}

	public String getCustomerCellPhone() {
		return CustomerCellPhone;
	}

	public void setCustomerCellPhone(String customerCellPhone) {
		CustomerCellPhone = customerCellPhone;
	}

	public String getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(String customerID) {
		CustomerID = customerID;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

}
