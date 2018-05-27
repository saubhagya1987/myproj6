package vn.com.unit.fe_credit.bean.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.AbstractBean;
import vn.com.unit.fe_credit.entity.collection.Repossession;

public class RepossessionBean extends AbstractBean<Repossession>{
    private Integer repossessionId;	
	private String contractId;
	
	private String nextApproverLevel;
	
	private String approvalStatus;
	
	RepossessionDto repossessionDto; 
	
	// Contract Attachments for a Terminated Contract to be displayed on "View Detail" screen
	private List<RepossessionAttachmentDto> attachments;  
	
	private Integer id;
	private String remark;
	private String status;
	private String product;
	private String step;
	// Terminated contracts list to be displayed on Contract Termination Screen
	private List<RepossessionDto> terminatedContracts;
	public Integer getRepossessionId() {
		return repossessionId;
	}
	public void setRepossessionId(Integer repossessionId) {
		this.repossessionId = repossessionId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getNextApproverLevel() {
		return nextApproverLevel;
	}
	public void setNextApproverLevel(String nextApproverLevel) {
		this.nextApproverLevel = nextApproverLevel;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	public RepossessionDto getRepossessionDto() {
		return repossessionDto;
	}
	public void setRepossessionDto(RepossessionDto repossessionDto) {
		this.repossessionDto = repossessionDto;
	}
	public List<RepossessionAttachmentDto> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<RepossessionAttachmentDto> attachments) {
		this.attachments = attachments;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public List<RepossessionDto> getTerminatedContracts() {
		return terminatedContracts;
	}
	public void setTerminatedContracts(List<RepossessionDto> terminatedContracts) {
		this.terminatedContracts = terminatedContracts;
	}

}
