package vn.com.unit.fe_credit.bean.v1;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;

public class RepossessionDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String fcCode;
	private String fcName;
	private String assetCondition;
	private String brand;
	private String calledWhom;
	private String ContactedDate;
	private String appleId;
	private String customersPhone;
	private Integer dpd;
	private String financialConditionAssessment;
	private String customerName;
	private String laaAssetMakeC;
	private Integer loanAccountNumber;
	private Integer loanAmount;
	private Integer principalOutstanding;
	private String repossessionAddress;
	private String status;
	private String suggestions;
	private Integer totalAmountPaid;
	private String unitCode;
	private String unitDesc;
	private String step;
	private String approverLevel;
	private String nextApproverLevel;
	private String approvalStatus;
	private String userApprove;
	private Date approvedDate;
	private String customerId;	
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFcCode() {
		return fcCode;
	}
	public void setFcCode(String fcCode) {
		this.fcCode = fcCode;
	}
	public String getFcName() {
		return fcName;
	}
	public void setFcName(String fcName) {
		this.fcName = fcName;
	}
	public String getAssetCondition() {
		return assetCondition;
	}
	public void setAssetCondition(String assetCondition) {
		this.assetCondition = assetCondition;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCalledWhom() {
		return calledWhom;
	}
	public void setCalledWhom(String calledWhom) {
		this.calledWhom = calledWhom;
	}
	public String getContactedDate() {
		return ContactedDate;
	}
	public void setContactedDate(String contactedDate) {
		ContactedDate = contactedDate;
	}
	public String getAppleId() {
		return appleId;
	}
	public void setAppleId(String appleId) {
		this.appleId = appleId;
	}
	public String getCustomersPhone() {
		return customersPhone;
	}
	public void setCustomersPhone(String customersPhone) {
		this.customersPhone = customersPhone;
	}
	public Integer getDpd() {
		return dpd;
	}
	public void setDpd(Integer dpd) {
		this.dpd = dpd;
	}
	public String getFinancialConditionAssessment() {
		return financialConditionAssessment;
	}
	public void setFinancialConditionAssessment(String financialConditionAssessment) {
		this.financialConditionAssessment = financialConditionAssessment;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLaaAssetMakeC() {
		return laaAssetMakeC;
	}
	public void setLaaAssetMakeC(String laaAssetMakeC) {
		this.laaAssetMakeC = laaAssetMakeC;
	}
	public Integer getLoanAccountNumber() {
		return loanAccountNumber;
	}
	public void setLoanAccountNumber(Integer loanAccountNumber) {
		this.loanAccountNumber = loanAccountNumber;
	}
	public Integer getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(Integer loanAmount) {
		this.loanAmount = loanAmount;
	}
	public Integer getPrincipalOutstanding() {
		return principalOutstanding;
	}
	public void setPrincipalOutstanding(Integer principalOutstanding) {
		this.principalOutstanding = principalOutstanding;
	}
	public String getRepossessionAddress() {
		return repossessionAddress;
	}
	public void setRepossessionAddress(String repossessionAddress) {
		this.repossessionAddress = repossessionAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSuggestions() {
		return suggestions;
	}
	public void setSuggestions(String suggestions) {
		this.suggestions = suggestions;
	}
	public Integer getTotalAmountPaid() {
		return totalAmountPaid;
	}
	public void setTotalAmountPaid(Integer totalAmountPaid) {
		this.totalAmountPaid = totalAmountPaid;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getUnitDesc() {
		return unitDesc;
	}
	public void setUnitDesc(String unitDesc) {
		this.unitDesc = unitDesc;
	}
	public String getStep() {
		return step;
	}
	public void setStep(String step) {
		this.step = step;
	}
	public String getApproverLevel() {
		return approverLevel;
	}
	public void setApproverLevel(String approverLevel) {
		this.approverLevel = approverLevel;
	}
	public String getNextApproverLevel() {
		return nextApproverLevel;
	}
	public void setNextApproverLevel(String nextApproverLevel) {
		this.nextApproverLevel = nextApproverLevel;
	}
	
	
	
	public String getUserApprove() {
		return userApprove;
	}
	public void setUserApprove(String userApprove) {
		this.userApprove = userApprove;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	

}
