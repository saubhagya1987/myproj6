package vn.com.unit.fe_credit.bean.v1;

import java.sql.Blob;
import java.util.List;
import java.util.Set;


public class RecordDto {
	public Integer recordId;
	public String contactDate;
	public String contactTime;
	public String personContacted;
	public String contactedWith;
	public String contactMode;
	public String contactPlace;
	public String nextActionDate;
	public Double ptpAmount;
	public String remark;
	public String checkIn;
	public String attachedFilesLink;
	public String contractId;
	public String reminderMode;
	public String responseCode;
	private String attachmentType;
	private byte[] attachmentData;
	private Integer id;
	
	
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public String getContactDate() {
		return contactDate;
	}
	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}
	public String getContactTime() {
		return contactTime;
	}
	public void setContactTime(String contactTime) {
		this.contactTime = contactTime;
	}
	public String getPersonContacted() {
		return personContacted;
	}
	public void setPersonContacted(String personContacted) {
		this.personContacted = personContacted;
	}
	public String getContactedWith() {
		return contactedWith;
	}
	public void setContactedWith(String contactedWith) {
		this.contactedWith = contactedWith;
	}
	public String getContactMode() {
		return contactMode;
	}
	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}
	public String getContactPlace() {
		return contactPlace;
	}
	public void setContactPlace(String contactPlace) {
		this.contactPlace = contactPlace;
	}
	public String getNextActionDate() {
		return nextActionDate;
	}
	public void setNextActionDate(String nextActionDate) {
		this.nextActionDate = nextActionDate;
	}
	public Double getPtpAmount() {
		return ptpAmount;
	}
	public void setPtpAmount(Double ptpAmount) {
		this.ptpAmount = ptpAmount;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getAttachedFilesLink() {
		return attachedFilesLink;
	}
	public void setAttachedFilesLink(String attachedFilesLink) {
		this.attachedFilesLink = attachedFilesLink;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getReminderMode() {
		return reminderMode;
	}
	public void setReminderMode(String reminderMode) {
		this.reminderMode = reminderMode;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/*public List<RecordAttachmentDto> getAttachmentData() {
		return attachmentData;
	}
	public void setAttachmentData(List<RecordAttachmentDto> attachmentData) {
		this.attachmentData = attachmentData;
	}*/
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}
	public byte[] getAttachmentData() {
		return attachmentData;
	}
	public void setAttachmentData(byte[] attachmentData) {
		this.attachmentData = attachmentData;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
	
	
	
	
}