package vn.com.unit.fe_credit.entity.collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
//import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "REPOSSESSION_APPROVER")
public class RepossessionApprover {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPOSSESSION_APPROVER_SEQ")
	@SequenceGenerator(name = "REPOSSESSION_APPROVER_SEQ", sequenceName = "REPOSSESSION_APPROVER_SEQ")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REPOSSESSION_APPROVER_ID")
	private Integer repossessionApproverId;

	@Column(name = "APPROVER_EMAIL_ID")
	private String approverEmailId;

	@Column(name = "APPL_ID")
	private String contractId;

	@Column(name = "ASSIGNED_BY")
	private String assignedBy;

	@Column(name = "ASSIGNED_DATE")
	private Date assignedDate;	
	
	@Column(name = "REPOSSESSION_ID")
	private Long repossesionId;
	
	
	@Column(name = "STEP")
	private String step;	
	
	@Column(name = "BIDDER_ID")
	private String bidderId;
	
	@Column(name = "ROLE")
	private String role;

	

	public String getApproverEmailId() {
		return approverEmailId;
	}

	public void setApproverEmailId(String approverEmailId) {
		this.approverEmailId = approverEmailId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	public Integer getRepossessionApproverId() {
		return repossessionApproverId;
	}

	public void setRepossessionApproverId(Integer repossessionApproverId) {
		this.repossessionApproverId = repossessionApproverId;
	}

	public Long getRepossesionId() {
		return repossesionId;
	}

	public void setRepossesionId(Long repossesionId) {
		this.repossesionId = repossesionId;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
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

	
	
}