package vn.com.unit.fe_credit.bean.v1;

public class ContractAttachmentDto {
	
	private Integer attachmentId;
	private String attachmentName;
	private String attachmentData;
	private String attachmentType;
	private String contractId;

	public Integer getAttachmentId() {
		return attachmentId;
	}
	
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentData() {
		return attachmentData;
	}

	public void setAttachmentData(String attachmentData) {
		this.attachmentData = attachmentData;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}
