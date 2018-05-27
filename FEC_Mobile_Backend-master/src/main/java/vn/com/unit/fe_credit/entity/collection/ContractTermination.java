package vn.com.unit.fe_credit.entity.collection;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "contract_termination")
public class ContractTermination {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_termination_id_sequence")
	@SequenceGenerator(name = "contract_termination_id_sequence", sequenceName = "CONTRACT_TERMINATION_Id_SEQ")
	@Column(name = "contract_termination_id")
	private Integer contractTerminationId;
	
	@Column(name = "updated_by", length = 50)
	private String updatedBy;
	
	@Column(name = "FULL_NAME", length = 200)
	private String customerName;//  (Customer Name picked from contract table)
	
	@Column(name = "customer_id", length = 50)
	private String customerId;//  (Customer Id picked from contract table)
	
	@Column(name = "loan_account_num", length = 50)
	private String contractId;//  (CONTRACT ID)
	@Column(name = "DPD", length = 50)
	private BigDecimal dpd;//  (DPD picked from contract table)
	@Column(name = "bucket", length = 50)
	private BigDecimal bucket;// (From where this data will come)
	@Column(name = "unit_code", length = 50)
	private String unitCode;// (Picked from contract table)
	@Column(name = "charge_off_flag", length = 50)
	private String chargeOffFlag;// (From where this data will come)
	@Column(name = "PRINICIPLE_OUTSTANDING", length = 50)
	private BigDecimal principleOutstanding;// (From where this data will come)
	@Column(name = "OVERDUE_CHARGES", length = 50)
	private Double overdueCharges;// (From where this data will come)
	@Column(name = "INTEREST_OUTSTANDING", length = 50)
	private String interestOutstanding;// (From where this data will come)
	@Column(name = "PREPAYMENT_PENALTY", length = 50)
	private Double prepaymentPenalty; 
	@Column(name = "REFUNDS", length = 50)
	private Double refunds;//(From where this data will come)
	@Column(name = "INTER_FUND", length = 50)
	private Double interFund;//(ETL Process needs to fill)
	@Column(name = "TOTAL_AMT_PAID_BY_CUSTOMER", length = 50)
	private BigDecimal totalAmontPaidByCustomer;
	@Column(name = "PENALTY_FEE_REQUESTED", length = 50)
	private BigDecimal penaltyFeeRequested;
	@Column(name = "EARLY_TERMINATION_FEE", length = 50)
	private BigDecimal earlyTerminationFeeRequested;
	@Column(name = "BILLED_INST", length = 50)
	private Double billedInst;//(Number of Installment) - picked from contract table 
	@Column(name = "Phone_Number", length = 50)
	private String phoneNumber;
	@Column(name = "payer", length = 50)
	private String payer;
	@Column(name = "reason", length = 50)
	private String reason;
	@Column(name = "APPROVER_LEVEL")
	private String approverLevel;
	@Column(name = "NEXT_APPROVER_LEVEL")
	private String nextApproverLevel;
	
	@Column(name = "PRODUCT")
	private String product;
	
	@Column(name = "STEP")
	private String step;
	
	
	@Column(name = "STATUS")
	private String status;
	
	
	
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

	public Integer getContractTerminationId() {
		return contractTerminationId;
	}

	public void setContractTerminationId(Integer contractTerminationId) {
		this.contractTerminationId = contractTerminationId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
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

	

	

	public Double getBilledInst() {
		return billedInst;
	}

	public void setBilledInst(Double billedInst) {
		this.billedInst = billedInst;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public BigDecimal getTotalAmontPaidByCustomer() {
		return totalAmontPaidByCustomer;
	}

	public void setTotalAmontPaidByCustomer(BigDecimal totalAmontPaidByCustomer) {
		this.totalAmontPaidByCustomer = totalAmontPaidByCustomer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
