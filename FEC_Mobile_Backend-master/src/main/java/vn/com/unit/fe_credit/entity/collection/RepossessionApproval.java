package vn.com.unit.fe_credit.entity.collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "REPOSSESSION_APPROVAL")
public class RepossessionApproval {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPOSSESSION_APPROVAL_SEQ")
	@SequenceGenerator(name = "REPOSSESSION_APPROVAL_SEQ", sequenceName = "REPOSSESSION_APPROVAL_SEQ")
	@Column(name = "REPOSSESSION_APPROVAL_ID")
	private Integer repossessionApprovalId;
	
	@Column(name = "APPL_ID")
	private String contractId;

	@Column(name = "customer_name")
	private String customerName;

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "product")
	private String product;

	@Column(name = "dpd")
	private String dpd;

	@Column(name = "action_code")
	private String actionCode;

	@Column(name = "REPOSSESSION_ID")
	private Integer repossessionId;

	@Column(name = "user_request")
	private String userRequest;

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "user_approve")
	private String userApprove;

	@Column(name = "approve_date")
	private Date approveDate;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "entry_date")
	private Date entryDate;

	@Column(name = "step")
	private String step;
	
	
	@Column(name = "BIDDER_ID")
	private String bidderId;
	
	@Column(name = "ROLE")
	private String role;

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
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

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getDpd() {
		return dpd;
	}

	public void setDpd(String dpd) {
		this.dpd = dpd;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	/*public Integer getTerminationId() {
		return terminationId;
	}

	public void setTerminationId(Integer terminationId) {
		this.terminationId = terminationId;
	}*/

	public String getUserRequest() {
		return userRequest;
	}

	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getUserApprove() {
		return userApprove;
	}

	public void setUserApprove(String userApprove) {
		this.userApprove = userApprove;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public Integer getRepossessionApprovalId() {
		return repossessionApprovalId;
	}

	public void setRepossessionApprovalId(Integer repossessionApprovalId) {
		this.repossessionApprovalId = repossessionApprovalId;
	}

	public Integer getRepossessionId() {
		return repossessionId;
	}

	public void setRepossessionId(Integer repossessionId) {
		this.repossessionId = repossessionId;
	}

	

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getBidderId() {
		return bidderId;
	}

	public void setBidderId(String bidderId) {
		this.bidderId = bidderId;
	}

	/*public Integer getTerminationApprovalId() {
		return terminationApprovalId;
	}

	public void setTerminationApprovalId(Integer terminationApprovalId) {
		this.terminationApprovalId = terminationApprovalId;
	}*/

	
	
}
