package vn.com.unit.fe_credit.bean.v1;

public class RepossessionAttachmentDto {
	private Integer attachmentId;
	private String attachmentName;
	private String attachmentData;
	private String attachmentType;
	private String contractId;
	private String repossessionId;
	public Integer getAttachmentId() {
		return attachmentId;
	}
	public void setAttachmentId(Integer attachmentId) {
		this.attachmentId = attachmentId;
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
	public String getRepossessionId() {
		return repossessionId;
	}
	public void setRepossessionId(String repossessionId) {
		this.repossessionId = repossessionId;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

}
