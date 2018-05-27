package vn.com.unit.fe_credit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "Customer")
@JsonAutoDetect
@Unique(hql = "FROM Customer c WHERE c.idCardNumber = :idCardNumber AND c.customerId != :customerId ", idField = "customerId", message = "{customer.field.idCardNumber.duplicate}", errorAtField = "idCardNumber")
@SequenceGenerator(allocationSize = 1, name = "seq_customer", sequenceName = "SEQ_CUSTOMER_CUSTOMERID")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	public Customer() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customer")
	@Column(name = "CustomerId")
	private Long customerId;

	@Column(name = "FirstName", length = 50)
	private String firstName;

	@Column(name = "LastName", length = 50)
	private String lastName;

	@Column(name = "MiddleName", length = 50)
	private String middleName;

	@Column(name = "IdCardNumber", length = 50)
	private String idCardNumber;

	@Column(name = "CellPhone", length = 50)
	private String cellPhone;

	@Column(name = "Status")
	private Integer status;

	@Column(name = "EmailAddress", length = 200)
	private String emailAddress;

	@Column(name = "CODEMACHINE")
	private String codeMachine;

	@Column(name = "Birthday")
	private Date birthday;

	@Column(name = "DATEGETCODE")
	private Date dateGetCode;

	@Column(name = "NUMBERGETCODE")
	private Integer numberGetCode = 0;

	@Column(name = "DATEFORGETCODE")
	private Date dateForgetGetCode;

	@Column(name = "NUMBERFORGETGETCODE")
	private Integer numberForgetGetCode;

	@Column(name = "olduserid", length = 50)
	private String oldUserId;

	@Column(name = "DATEIMPORT")
	// @Temporal(TemporalType.DATE)
	private Date DATEIMPORT;

	@Column(name = "DEFAULTLANGUAGE")
	private String defaultLanguage;

	@Column(name = "WRG_OTP_RESET_PWD_ATTEMPTS")
	private Integer wrg_OTP_RESET_PWD_ATTEMPTS = 0;

	@Column(name = "WRG_OTP_RESET_PWD_BAN_DUE")
	private Date wrg_OTP_RESET_PWD_BAN_DUE;

	@Column(name = "REQUEST_OTP_BLOCKED_DUE")
	private Date request_OTP_BLOCKED_DUE;

	@Column(name = "OTP_LIVE_DUE")
	private Date otp_LIVE_DUE;

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	@Column(name = "ImagePath")
	private String imagePath;

	@Column(name = "fullName")
	private String fullName;

	@Column(name = "SmsCode")
	private String smsCode;

	@Column(name = "RefId")
	private String refId;

	@Column(name = "UserName", length = 50)
	private String userName;

	@Column(name = "Password", length = 50)
	private String password;

	@JsonIgnore
	@Column(name = "PasswordOld", length = 50)
	private String passwordOld;

	@Column(name = "AccountStatus")
	private Integer accountStatus;

	@Column(name = "IsLogged")
	private Boolean isLogged;

	@Column(name = "LastLoggedTime")
	private Date lastLoggedTime;

	@Column(name = "ResetPasswordKey", length = 50)
	private String resetPasswordKey;

	@Column(name = "ActivateCodeDate")
	private Date activateCodeDate;

	@Column(name = "ResetPasswordExpiredDate")
	private Date resetPasswordExpiredDate;

	@Column(name = "ActivateCode", length = 50)
	private String activateCode;

	@Column(name = "ActivateCodeExpiredDate")
	private Date activateCodeExpiredDate;

	@Column(name = "AddressNo", length = 50)
	private String addressNo;

	@Column(name = "Street", length = 200)
	private String street;

	@Column(name = "Ward", length = 100)
	private String ward;

	@Column(name = "District", length = 100)
	private String district;

	@Column(name = "Province", length = 100)
	private String province;

	@Column(name = "MapLocation", length = 500)
	private String mapLocation;

	@Column(name = "CREATEDATE", updatable = false)
	private Date createDate;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "UPDATEDATE")
	private Date updateDate;

	@Column(name = "CREATEBY", length = 100, updatable = false)
	private String createBy;

	@Column(name = "UPDATEBY", length = 100)
	private String updateBy;

	@Transient
	private String strCreatedDate;

	@Transient
	private Long parentMsgId;

	@Transient
	private String subject;

	@Transient
	private String customerContractId;

	public String getCustomerContractId() {
		return customerContractId;
	}

	public void setCustomerContractId(String customerContractId) {
		this.customerContractId = customerContractId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Long getParentMsgId() {
		return parentMsgId;
	}

	public void setParentMsgId(Long parentMsgId) {
		this.parentMsgId = parentMsgId;
	}

	public String getStrCreatedDate() {
		return strCreatedDate;
	}

	public void setStrCreatedDate(String strCreatedDate) {
		this.strCreatedDate = strCreatedDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MapLocationOld", length = 500)
	private String mapLocationOld;

	@Column(name = "ADDRESS", length = 500)
	private String addressS;

	public String getAddressS() {
		return addressS;
	}

	public void setAddressS(String addressS) {
		this.addressS = addressS;
	}

	@Transient
	private String address;

	@Transient
	private String newPass;

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	@Transient
	private String countMsg;

	@Transient
	private Integer msgType;

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public String getCountMsg() {
		return countMsg;
	}

	public void setCountMsg(String countMsg) {
		this.countMsg = countMsg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordOld() {
		return passwordOld;
	}

	public void setPasswordOld(String passwordOld) {
		this.passwordOld = passwordOld;
	}

	public Integer getAccountStatus() {
		return accountStatus;
	}

	public void setAccountStatus(Integer accountStatus) {
		this.accountStatus = accountStatus;
	}

	public Boolean getIsLogged() {
		return isLogged;
	}

	public void setIsLogged(Boolean isLogged) {
		this.isLogged = isLogged;
	}

	public Date getLastLoggedTime() {
		return lastLoggedTime;
	}

	public void setLastLoggedTime(Date lastLoggedTime) {
		this.lastLoggedTime = lastLoggedTime;
	}

	public String getResetPasswordKey() {
		return resetPasswordKey;
	}

	public void setResetPasswordKey(String resetPasswordKey) {
		this.resetPasswordKey = resetPasswordKey;
	}

	public Date getResetPasswordExpiredDate() {
		return resetPasswordExpiredDate;
	}

	public void setResetPasswordExpiredDate(Date resetPasswordExpiredDate) {
		this.resetPasswordExpiredDate = resetPasswordExpiredDate;
	}

	public String getActivateCode() {
		return activateCode;
	}

	public void setActivateCode(String activateCode) {
		this.activateCode = activateCode;
	}

	public Date getActivateCodeExpiredDate() {
		return activateCodeExpiredDate;
	}

	public void setActivateCodeExpiredDate(Date activateCodeExpiredDate) {
		this.activateCodeExpiredDate = activateCodeExpiredDate;
	}

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getMapLocation() {
		return mapLocation;
	}

	public void setMapLocation(String mapLocation) {
		this.mapLocation = mapLocation;
	}

	public String getMapLocationOld() {
		return mapLocationOld;
	}

	public void setMapLocationOld(String mapLocationOld) {
		this.mapLocationOld = mapLocationOld;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getIdCardNumber() {
		return idCardNumber;
	}

	public void setIdCardNumber(String idCardNumber) {
		this.idCardNumber = idCardNumber;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Date getActivateCodeDate() {
		return activateCodeDate;
	}

	public void setActivateCodeDate(Date activateCodeDate) {
		this.activateCodeDate = activateCodeDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getDateGetCode() {
		return dateGetCode;
	}

	public void setDateGetCode(Date dateGetCode) {
		this.dateGetCode = dateGetCode;
	}

	public Integer getNumberGetCode() {
		return numberGetCode;
	}

	public void setNumberGetCode(Integer numberGetCode) {
		this.numberGetCode = numberGetCode;
	}

	public Date getDateForgetGetCode() {
		return dateForgetGetCode;
	}

	public void setDateForgetGetCode(Date dateForgetGetCode) {
		this.dateForgetGetCode = dateForgetGetCode;
	}

	public Integer getNumberForgetGetCode() {
		return numberForgetGetCode;
	}

	public void setNumberForgetGetCode(Integer numberForgetGetCode) {
		this.numberForgetGetCode = numberForgetGetCode;
	}

	public String getCodeMachine() {
		return codeMachine;
	}

	public void setCodeMachine(String codeMachine) {
		this.codeMachine = codeMachine;
	}

	public Date getDATEIMPORT() {
		return DATEIMPORT;
	}

	public void setDATEIMPORT(Date dATEIMPORT) {
		DATEIMPORT = dATEIMPORT;
	}

	public Integer getWrg_OTP_RESET_PWD_ATTEMPTS() {
		return wrg_OTP_RESET_PWD_ATTEMPTS;
	}

	public void setWrg_OTP_RESET_PWD_ATTEMPTS(Integer wrg_OTP_RESET_PWD_ATTEMPTS) {
		this.wrg_OTP_RESET_PWD_ATTEMPTS = wrg_OTP_RESET_PWD_ATTEMPTS;
	}

	public Date getWrg_OTP_RESET_PWD_BAN_DUE() {
		return wrg_OTP_RESET_PWD_BAN_DUE;
	}

	public void setWrg_OTP_RESET_PWD_BAN_DUE(Date wrg_OTP_RESET_PWD_BAN_DUE) {
		this.wrg_OTP_RESET_PWD_BAN_DUE = wrg_OTP_RESET_PWD_BAN_DUE;
	}

	public Date getRequest_OTP_BLOCKED_DUE() {
		return request_OTP_BLOCKED_DUE;
	}

	public void setRequest_OTP_BLOCKED_DUE(Date request_OTP_BLOCKED_DUE) {
		this.request_OTP_BLOCKED_DUE = request_OTP_BLOCKED_DUE;
	}

	public Date getOtp_LIVE_DUE() {
		return otp_LIVE_DUE;
	}

	public void setOtp_LIVE_DUE(Date otp_LIVE_DUE) {
		this.otp_LIVE_DUE = otp_LIVE_DUE;
	}

}
