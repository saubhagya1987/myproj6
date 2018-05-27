package vn.com.unit.webservice;


public class SaveMessageContract {

	private Long customerId;
	private String appId;
	private String contractMsgId;		
	private String fullName;
	private String cellphone;
	private String subject;
	private String content;
	private Integer status;
	private String type;
	private String contractID;	
	private String content_en;
	private String content_vi;
	private String language;
	
	public String getContent_en() {
		return content_en;
	}
	public void setContent_en(String content_en) {
		this.content_en = content_en;
	}
	public String getContent_vi() {
		return content_vi;
	}
	public void setContent_vi(String content_vi) {
		this.content_vi = content_vi;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getContractID() {
		return contractID;
	}
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getContractMsgId() {
		return contractMsgId;
	}
	public void setContractMsgId(String contractMsgId) {
		this.contractMsgId = contractMsgId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
