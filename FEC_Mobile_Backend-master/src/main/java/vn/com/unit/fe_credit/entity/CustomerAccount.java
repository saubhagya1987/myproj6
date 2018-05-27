package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import vn.com.unit.validator.Unique;

@Entity
@Table(name="CustomerAccount")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_customeraccount",sequenceName="SEQ_CUSTOMERACCOUNTID")
public class CustomerAccount {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_customeraccount")
	@Column(name = "CustomerAccountId")
	private Long customerAccountId;
	
	@Column(name = "UserName", length = 50)
	private String userName;
	
	@JsonIgnore
	@Column(name = "Password", length = 50)
	private String password;
	
	@JsonIgnore
	@Column(name = "OldPassword", length = 50)
	private String oldPassword;
	
	@Column(name = "AccountStatus")
	private Integer accountStatus;
	
	@Column(name = "IsLogged")
	private Boolean isLogged;
	
	@Column(name = "LastLoginTime")
	private Date lastLoginTime;
	
	@Column(name = "ResetPasswordKey", length = 50)
	private String resetPasswordKey;
	
	@Column(name = "ResetPasswordExpiredDate")
	private Date resetPasswordExpiredDate;
	
	@Column(name = "ActivateCode", length = 50)
	private String activateCode;
	
	@Column(name = "ActivateCodeExpiredDate")
	private Date activateCodeExpiredDate;
	
	@Column(name = "CustomerId")
	private Long customerId;

	public Long getCustomerAccountId() {
		return customerAccountId;
	}

	public void setCustomerAccountId(Long customerAccountId) {
		this.customerAccountId = customerAccountId;
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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
