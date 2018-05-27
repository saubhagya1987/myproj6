package vn.com.unit.fe_credit.bean.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.AbstractBean;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;

public class ContractTerminationBean extends AbstractBean<ContractTermination>{

	private Integer contractTerminationId;
	
	private String contractId;
	
	private String nextApproverLevel;
	
	private String approvalStatus;
	
	ContractTerminationDto contractTerminationDto; 
	
	// Contract Attachments for a Terminated Contract to be displayed on "View Detail" screen
	private List<ContractAttachmentDto> attachments;  
	
	private Integer id;
	private String remark;
	private String status;
	private String product;
	private String step;
	// Terminated contracts list to be displayed on Contract Termination Screen
	private List<ContractTerminationDto> contractTerminationDtoList;
	
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


	public List<ContractAttachmentDto> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<ContractAttachmentDto> attachments) {
		this.attachments = attachments;
	}

	/*public List<ContractTerminationDto> getContractTerminationDtoList() {
		return terminatedContracts;
	}

	public void setContractTerminationDtoList(List<ContractTerminationDto> contractTerminationDtoList) {
		this.terminatedContracts = contractTerminationDtoList;
	}*/

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

	public List<ContractTerminationDto> getContractTerminationDtoList() {
		return contractTerminationDtoList;
	}

	public void setContractTerminationDtoList(
			List<ContractTerminationDto> contractTerminationDtoList) {
		this.contractTerminationDtoList = contractTerminationDtoList;
	}

	
}
