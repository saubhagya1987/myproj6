package vn.com.unit.fe_credit.entity.collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "repossession")
public class Repossession {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPOSSESSION_SEQ")
	@SequenceGenerator(name = "REPOSSESSION_SEQ", sequenceName = "REPOSSESSION_SEQ")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "FC_CODE")
	private String fcCode;
	
	@Column(name = "FC_NAME")
	private String fcName;
	
	@Column(name = "ASSET_CONDITION")
	private String assetCondition;
	
	@Column(name = "BRAND")
	private String brand;
	
	@Column(name = "CALLEDWHOM")
	private String calledwhom;
	
	
	
	@Column(name = "CONTACTED_DATE")
	private String contactedDate;
	
	
	@Column(name = "APPL_ID")
	private String APPL_ID;
	
	@Column(name = "CUSTOMERS_PHONE")
	private String customersPhone;
	
	/*@Column(name = "CONTRACT_ID")
	private String contractId;*/
	
	
	
	@Column(name = "DPD")
	private Integer dpd;
	
	@Column(name = "FINANCIAL_CONDITION_ASSESSMENT")
	private String financialConditionAssessment;
	
	@Column(name = "CUST_FULL_NAME")
	private String custFullName;
	
	@Column(name = "LAA_ASSET_MAKE_C")
	private String laaAssetMakeC;
	
	/*@Column(name = "LAST_NAME")
	private String lastName;
	*/
	@Column(name = "LOAN_ACCT_NUM")
	private Integer loanAcctNum;
	
	@Column(name = "LOAN_AMOUNT")
	private Integer loanAmount;
	
	/*@Column(name = "MIDDLE_NAME")
	private String middleName;*/
	
	@Column(name = "PRINCIPLE_OUTSTANDING")
	private Integer principleOutstanding;
	
	@Column(name = "REPOSESSION_ADDRESS")
	private String reposessionAddress;
	
	@Column(name = "SUGGESTIONS")
	private String suggestions;
	
	@Column(name = "TOTAL_AMOUNT_PAID")
	private Integer totalAmountPaid;
	
	@Column(name = "UNIT_CODE")
	private String unitCode;

	@Column(name = "UNIT_DESC")
	private String unitDesc;
	
	@Column(name = "STATUS")
	private String status;
	
	

	/*@Column(name = "APPROVAL_LEVEL")
	private String approvalLevel;
	*/
	/*@Column(name = "APPROVAL_ROLE")
	private String approvalRole;*/
	
	@Column(name = "STEP")
	private String step;
	
	@Column(name = "APPROVER_LEVEL")
	private String approverLevel;
	
	
	@Column(name = "NEXT_APPROVER_LEVEL")
	private String nextApproverLevel;
	
	@Column(name = "updated_by")
	private String updatedBy;
	@Column(name="customer_id")
	private String customerId;
	@Column(name="product")
	private String product;


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


	public String getCalledwhom() {
		return calledwhom;
	}


	public void setCalledwhom(String calledwhom) {
		this.calledwhom = calledwhom;
	}


	public String getContactedDate() {
		return contactedDate;
	}


	public void setContactedDate(String contactedDate) {
		this.contactedDate = contactedDate;
	}


	public String getAPPL_ID() {
		return APPL_ID;
	}


	public void setAPPL_ID(String aPPL_ID) {
		APPL_ID = aPPL_ID;
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


	public String getCustFullName() {
		return custFullName;
	}


	public void setCustFullName(String custFullName) {
		this.custFullName = custFullName;
	}


	public String getLaaAssetMakeC() {
		return laaAssetMakeC;
	}


	public void setLaaAssetMakeC(String laaAssetMakeC) {
		this.laaAssetMakeC = laaAssetMakeC;
	}


	public Integer getLoanAcctNum() {
		return loanAcctNum;
	}


	public void setLoanAcctNum(Integer loanAcctNum) {
		this.loanAcctNum = loanAcctNum;
	}


	public Integer getLoanAmount() {
		return loanAmount;
	}


	public void setLoanAmount(Integer loanAmount) {
		this.loanAmount = loanAmount;
	}


	public Integer getPrincipleOutstanding() {
		return principleOutstanding;
	}


	public void setPrincipleOutstanding(Integer principleOutstanding) {
		this.principleOutstanding = principleOutstanding;
	}


	public String getReposessionAddress() {
		return reposessionAddress;
	}


	public void setReposessionAddress(String reposessionAddress) {
		this.reposessionAddress = reposessionAddress;
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


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
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


	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getProduct() {
		return product;
	}


	public void setProduct(String product) {
		this.product = product;
	}

	
	
}
