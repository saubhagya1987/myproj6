/*package vn.com.unit.fe_credit.entity.collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPOSSESSION_APPROVER")
public class ContractRepossessionApprover {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "repossession_approver_id")
	private Integer repossessionApproverId;

	@Column(name = "repossession_id")
	private Integer repossessionId;

	@Column(name = "approver_email_id")
	private String approverEmailId;

	@Column(name = "contract_id")
	private String contractId;

	@Column(name = "assigned_by")
	private String assignedBy;

	@Column(name = "assigned_date")
	private Date assignedDate;

	public Integer getRepossessionApproverId() {
		return repossessionApproverId;
	}

	public void setRepossessionApproverId(Integer repossessionApproverId) {
		this.repossessionApproverId = repossessionApproverId;
	}

	public Integer getRepossessionId() {
		return repossessionId;
	}

	public void setRepossessionId(Integer repossessionId) {
		this.repossessionId = repossessionId;
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
*/