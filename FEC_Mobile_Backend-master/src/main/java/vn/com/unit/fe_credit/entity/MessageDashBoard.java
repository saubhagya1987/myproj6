package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "MESSAGES")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_message", sequenceName = "SEQ_MESSAGES_MESSAGEID")
public class MessageDashBoard {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_message")
	@Column(name = "MESSAGEID")
	private Long messageID;

	@Column(name = "TYPE")
	private Integer type;

	@Column(name = "CATEGORY")
	private Integer category;

	@Column(name = "SUBCATEGORY")
	private Integer subcategory;

	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "DOB")
	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name = "CELLPHONE")
	private String cellphone;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "EMAIl")
	private String Email;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "STATUSDATE")
	@Temporal(TemporalType.DATE)
	private Date statusdate;

	@ManyToOne
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "CUSTOMERID")
	private Customer customer;

	@Column(name = "DEVICEID")
	private String deviceID;

	@Transient
	private Integer commentsCount;

	@ManyToOne
	@JoinColumn(name = "ACCOUNTID", referencedColumnName = "ACCOUNTID")
	private Account ACCOUNT;

	@Column(name = "CREATEDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Column(name = "READDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date readDate;

	@Column(name = "ISREAD")
	private Integer isRead;

	@Column(name = "PARENTMSGID")
	private Long parentMsgId;

	@Column(name = "ISMSGUSER")
	private Integer isMsgUser;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "CONTRACTMSGID")
	private String contractMsgId;

	@Column(name = "PUSH_MESSAGE_ID")
	private Long pushMessageId;

	@Column(name = "PUSH_MESSAGE_SUBJECT")
	private String pushMessageSubject;

	@Transient
	private String customerName;

	@Transient
	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}

	@Column(name = "DATEIMPORT")
	// @Temporal(TemporalType.DATE)
	private Date DATEIMPORT;

	@Column(name = "ISPARENT")
	private Boolean isParent;

	@Column(name = "PUSH_DATE")
	private Date pushDate;

	@Column(name = "PUSH_NOTIFICATION_STATUS")
	private Integer pushNotificationStatus;

	@Column(name = "PUSH_ERROR_MESSAGES")
	private String pushErrorMessages;

	@Column(name = "CMS_ID")
	private Long cmsId;

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Transient
	private String managerName;

	@Transient
	private String managerEmail;

	@Transient
	private String managerImgPath;

	@Transient
	private String customerImgPath;

	@Transient
	private String creDate;

	@Transient
	private Integer limit;

	@Transient
	private Integer page;

	public String getCreDate() {
		return creDate;
	}

	public void setCreDate(String creDate) {
		this.creDate = creDate;
	}

	@Transient
	private Long customerId;

	@Transient
	private String typeInbox;

	@Transient
	private String strCreatedDate;

	public String getStrCreatedDate() {
		return strCreatedDate;
	}

	public void setStrCreatedDate(String strCreatedDate) {
		this.strCreatedDate = strCreatedDate;
	}

	public String getContractMsgId() {
		return contractMsgId;
	}

	public void setContractMsgId(String contractMsgId) {
		this.contractMsgId = contractMsgId;
	}

	public String getTypeInbox() {
		return typeInbox;
	}

	public void setTypeInbox(String typeInbox) {
		this.typeInbox = typeInbox;
	}

	@Transient
	private Long accountId;

	@Transient
	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCustomerImgPath() {
		return customerImgPath;
	}

	public void setCustomerImgPath(String customerImgPath) {
		this.customerImgPath = customerImgPath;
	}

	public String getManagerImgPath() {
		return managerImgPath;
	}

	public void setManagerImgPath(String managerImgPath) {
		this.managerImgPath = managerImgPath;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public Integer getIsMsgUser() {
		return isMsgUser;
	}

	public void setIsMsgUser(Integer isMsgUser) {
		this.isMsgUser = isMsgUser;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Account getACCOUNT() {
		return ACCOUNT;
	}

	public void setACCOUNT(Account aCCOUNT) {
		ACCOUNT = aCCOUNT;
	}

	public Long getParentMsgId() {
		return parentMsgId;
	}

	public void setParentMsgId(Long parentMsgId) {
		this.parentMsgId = parentMsgId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Long getMessageID() {
		return messageID;
	}

	public void setMessageID(Long messageID) {
		this.messageID = messageID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
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

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getStatusdate() {
		return statusdate;
	}

	public void setStatusdate(Date statusdate) {
		this.statusdate = statusdate;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Integer subcategory) {
		this.subcategory = subcategory;
	}

	@Transient
	private String appId;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public Date getDATEIMPORT() {
		return DATEIMPORT;
	}

	public void setDATEIMPORT(Date dATEIMPORT) {
		DATEIMPORT = dATEIMPORT;
	}

	public Long getPushMessageId() {
		return pushMessageId;
	}

	public void setPushMessageId(Long pushMessageId) {
		this.pushMessageId = pushMessageId;
	}

	public String getPushMessageSubject() {
		return pushMessageSubject;
	}

	public void setPushMessageSubject(String pushMessageSubject) {
		this.pushMessageSubject = pushMessageSubject;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Date getPushDate() {
		return pushDate;
	}

	public void setPushDate(Date pushDate) {
		this.pushDate = pushDate;
	}

	public String getPushErrorMessages() {
		return pushErrorMessages;
	}

	public void setPushErrorMessages(String pushErrorMessages) {
		this.pushErrorMessages = pushErrorMessages;
	}

	public Integer getPushNotificationStatus() {
		return pushNotificationStatus;
	}

	public void setPushNotificationStatus(Integer pushNotificationStatus) {
		this.pushNotificationStatus = pushNotificationStatus;
	}

	public Long getCmsId() {
		return cmsId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

}
