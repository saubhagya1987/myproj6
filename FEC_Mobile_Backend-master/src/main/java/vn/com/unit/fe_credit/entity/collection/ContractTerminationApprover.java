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
@Table(name = "contract_termination_approver")
public class ContractTerminationApprover {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACT_APPROVER_SEQ")
	@SequenceGenerator(name = "CONTRACT_APPROVER_SEQ", sequenceName = "CONTRACT_APPROVER_SEQ")
	@Column(name = "TERMINATION_APPROVER_ID")
	private Integer terminationApproverId;

	@Column(name = "CONTRACT_TERMINATION_ID")
	private Integer contractTerminationId;

	@Column(name = "APPROVER_EMAIL_ID")
	private String approverEmailId;

	@Column(name = "CONTRACT_ID")
	private String contractId;

	@Column(name = "ASSIGNED_BY")
	private String assignedBy;

	@Column(name = "ASSIGNED_DATE")
	private Date assignedDate;

	public Integer getRepossessionApproverId() {
		return terminationApproverId;
	}

	public void setTerminationApproverId(Integer terminationApproverId) {
		this.terminationApproverId = terminationApproverId;
	}

	public Integer getTerminationApproverId() {
		return terminationApproverId;
	}

	public void setContractTerminationId(Integer contractTerminationId) {
		this.contractTerminationId = contractTerminationId;
	}

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

	
	
}