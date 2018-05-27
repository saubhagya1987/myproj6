/*package vn.com.unit.fe_credit.entity.collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "BIDDER_APPROVER")
public class BidderApprover {
	@Id	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BIDDER_APPROVER_ID")
	private Integer bidderApproverId;

	@Column(name = "APPROVER_EMAIL_ID")
	private String approverEmailId;

	@Column(name = "CONTRACT_ID")
	private String contractId;

	@Column(name = "ASSIGNED_BY")
	private String assignedBy;

	@Column(name = "ASSIGNED_DATE")
	private Date assignedDate;	
	
	@Column(name = "REPOSSESSION_ID")
	private Integer repossessionId;

	public Integer getBidderApproverId() {
		return bidderApproverId;
	}

	public void setBidderApproverId(Integer bidderApproverId) {
		this.bidderApproverId = bidderApproverId;
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

	public Integer getRepossessionId() {
		return repossessionId;
	}

	public void setRepossessionId(Integer repossessionId) {
		this.repossessionId = repossessionId;
	}

	

}
*/