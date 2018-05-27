package vn.com.unit.fe_credit.bean;

import java.util.Date;
import java.util.List;

import vn.com.unit.fe_credit.entity.DataJson;

public class MessageInboxBean {
	private Long messageID;
	private String title;
	private String summary;
	private String relatedtopic;
	private Date datetime;
	private boolean hasAttachment;
	private Integer numberOfReplies;
	private boolean isRead;
	private String imgPathAdmin;
	private String imgPathCustomer;
	private Integer status;
	private Long accountID;
	private Long customerID;
	private String contractMsgId;
	private Date readDate;
	private Integer type;

	public String getContractMsgId() {
		return contractMsgId;
	}

	public void setContractMsgId(String contractMsgId) {
		this.contractMsgId = contractMsgId;
	}

	public String getImgPathCustomer() {
		return imgPathCustomer;
	}

	public void setImgPathCustomer(String imgPathCustomer) {
		this.imgPathCustomer = imgPathCustomer;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}

	public Long getAccountID() {
		return accountID;
	}

	public void setAccountID(Long accountID) {
		this.accountID = accountID;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getImgPathAdmin() {
		return imgPathAdmin;
	}

	public void setImgPathAdmin(String imgPathAdmin) {
		this.imgPathAdmin = imgPathAdmin;
	}

	private String ismsgUser;
	private String fullName;

	public String getIsmsgUser() {
		return ismsgUser;
	}

	public void setIsmsgUser(String ismsgUser) {
		this.ismsgUser = ismsgUser;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	private List<DataJson> attachment;

	public Long getMessageID() {
		return messageID;
	}

	public List<DataJson> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<DataJson> attachment) {
		this.attachment = attachment;
	}

	public void setMessageID(Long messageID) {
		this.messageID = messageID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRelatedtopic() {
		return relatedtopic;
	}

	public void setRelatedtopic(String relatedtopic) {
		this.relatedtopic = relatedtopic;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public boolean isHasAttachment() {
		return hasAttachment;
	}

	public void setHasAttachment(boolean hasAttachment) {
		this.hasAttachment = hasAttachment;
	}

	public Integer getNumberOfReplies() {
		return numberOfReplies;
	}

	public void setNumberOfReplies(Integer numberOfReplies) {
		this.numberOfReplies = numberOfReplies;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
