package vn.com.unit.fe_credit.bean.v1;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ContractTerminationDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer contractTerminationId;

	private String contractId;

	private String customerId;

	private BigDecimal dpd;

	private BigDecimal bucket;

	private String unitCode;

	private String chargeOffFlag;

	private BigDecimal principleOutstanding;

	private Double overdueCharges;

	private String interestOutstanding;

	private Double prepaymentPenalty;

	private Double refunds;

	private Double interFund;

	private BigDecimal totalAmontPaidByCustomer;

	private BigDecimal penaltyFeeRequested;

	private BigDecimal earlyTerminationFeeRequested;

	private Double billedInst;

	private String phoneNumber;

	private String payer;

	private String reason;

	private String approverLevel;

	private String assetsCondition;

	private String financialConditionAssessment;

	private String updationDate;

	private Double proposedSalesPrice;

	private String updatedBy;

	private String roleCode;

	private List<String> emails;
	
	private String customerName;
	
	private Integer id;
	
	private String approvalStatus;
	private String approvedUserEmailId;
	private Date approvedDate;
	private String nextApproverLevel;
	public Integer getContractTerminationId() {
		return contractTerminationId;
	}
	public void setContractTerminationId(Integer contractTerminationId) {
		this.contractTerminationId = contractTerminationId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public BigDecimal getDpd() {
		return dpd;
	}
	public void setDpd(BigDecimal dpd) {
		this.dpd = dpd;
	}
	public BigDecimal getBucket() {
		return bucket;
	}
	public void setBucket(BigDecimal bucket) {
		this.bucket = bucket;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	public String getChargeOffFlag() {
		return chargeOffFlag;
	}
	public void setChargeOffFlag(String chargeOffFlag) {
		this.chargeOffFlag = chargeOffFlag;
	}
	public BigDecimal getPrincipleOutstanding() {
		return principleOutstanding;
	}
	public void setPrincipleOutstanding(BigDecimal principleOutstanding) {
		this.principleOutstanding = principleOutstanding;
	}
	public Double getOverdueCharges() {
		return overdueCharges;
	}
	public void setOverdueCharges(Double overdueCharges) {
		this.overdueCharges = overdueCharges;
	}
	public String getInterestOutstanding() {
		return interestOutstanding;
	}
	public void setInterestOutstanding(String interestOutstanding) {
		this.interestOutstanding = interestOutstanding;
	}
	public Double getPrepaymentPenalty() {
		return prepaymentPenalty;
	}
	public void setPrepaymentPenalty(Double prepaymentPenalty) {
		this.prepaymentPenalty = prepaymentPenalty;
	}
	public Double getRefunds() {
		return refunds;
	}
	public void setRefunds(Double refunds) {
		this.refunds = refunds;
	}
	public Double getInterFund() {
		return interFund;
	}
	public void setInterFund(Double interFund) {
		this.interFund = interFund;
	}
	/*public Double getTotalAmontPaidByCustomer() {
		return totalAmontPaidByCustomer;
	}
	public void setTotalAmontPaidByCustomer(Double totalAmontPaidByCustomer) {
		this.totalAmontPaidByCustomer = totalAmontPaidByCustomer;
	}*/
	/*public Double getPenaltyFeeRequested() {
		return penaltyFeeRequested;
	}
	public void setPenaltyFeeRequested(Double penaltyFeeRequested) {
		this.penaltyFeeRequested = penaltyFeeRequested;
	}
	public Double getEarlyTerminationFeeRequested() {
		return earlyTerminationFeeRequested;
	}
	public void setEarlyTerminationFeeRequested(Double earlyTerminationFeeRequested) {
		this.earlyTerminationFeeRequested = earlyTerminationFeeRequested;
	}*/
	public Double getBilledInst() {
		return billedInst;
	}
	public void setBilledInst(Double billedInst) {
		this.billedInst = billedInst;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPayer() {
		return payer;
	}
	public void setPayer(String payer) {
		this.payer = payer;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getApproverLevel() {
		return approverLevel;
	}
	public void setApproverLevel(String approverLevel) {
		this.approverLevel = approverLevel;
	}
	public String getAssetsCondition() {
		return assetsCondition;
	}
	public void setAssetsCondition(String assetsCondition) {
		this.assetsCondition = assetsCondition;
	}
	public String getFinancialConditionAssessment() {
		return financialConditionAssessment;
	}
	public void setFinancialConditionAssessment(String financialConditionAssessment) {
		this.financialConditionAssessment = financialConditionAssessment;
	}
	public String getUpdationDate() {
		return updationDate;
	}
	public void setUpdationDate(String updationDate) {
		this.updationDate = updationDate;
	}
	public Double getProposedSalesPrice() {
		return proposedSalesPrice;
	}
	public void setProposedSalesPrice(Double proposedSalesPrice) {
		this.proposedSalesPrice = proposedSalesPrice;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public List<String> getEmails() {
		return emails;
	}
	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public String getApprovedUserEmailId() {
		return approvedUserEmailId;
	}
	public void setApprovedUserEmailId(String approvedUserEmailId) {
		this.approvedUserEmailId = approvedUserEmailId;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getNextApproverLevel() {
		return nextApproverLevel;
	}
	public void setNextApproverLevel(String nextApproverLevel) {
		this.nextApproverLevel = nextApproverLevel;
	}
	public BigDecimal getTotalAmontPaidByCustomer() {
		return totalAmontPaidByCustomer;
	}
	public void setTotalAmontPaidByCustomer(BigDecimal totalAmontPaidByCustomer) {
		this.totalAmontPaidByCustomer = totalAmontPaidByCustomer;
	}
	public BigDecimal getPenaltyFeeRequested() {
		return penaltyFeeRequested;
	}
	public void setPenaltyFeeRequested(BigDecimal penaltyFeeRequested) {
		this.penaltyFeeRequested = penaltyFeeRequested;
	}
	public BigDecimal getEarlyTerminationFeeRequested() {
		return earlyTerminationFeeRequested;
	}
	public void setEarlyTerminationFeeRequested(
			BigDecimal earlyTerminationFeeRequested) {
		this.earlyTerminationFeeRequested = earlyTerminationFeeRequested;
	}
	
	
}
