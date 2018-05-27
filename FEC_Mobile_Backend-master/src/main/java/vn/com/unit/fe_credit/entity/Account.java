package vn.com.unit.fe_credit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "account")
@JsonAutoDetect
@Unique(hql = "FROM Account ac WHERE ac.username = :username AND ac.id != :id ", idField = "id", message = "{account.field.username.duplicate}", errorAtField = "username")
@SequenceGenerator(allocationSize = 1, name = "seq_account", sequenceName = "SEQ_ACCOUNT_ACCOUNTID")
public class Account implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2531852082536909229L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_account")
	@Column(name = "ACCOUNTID")
	private Long id;

	@Column(name = "USER_CODE")
	private String userCode;
	@Column(name = "REGION_CODE")
	private String regionCode;
	@Column(name = "PROVINCE_CODE")
	private String provinceCode;
	@Column(name = "JOINING_DATE")
	@Temporal(TemporalType.DATE)
	private Date joiningDate;
	
	@Column(name = "ATTEMPTS")
	private Integer attempts;
	
	@Column(name = "LINE_MANAGER")
	private String lineManager;//	LDAP Line Manager Email	"Blank" value if user don't have any line manager	
	
	@Column(name = "USERNAME", length = 20)
	//@Pattern(regexp = "^[a-zA-Z0-9\\-\\.\\_]*$", message = "{account.username.is.valid}")
	private String username;

	@Column(name = "FULLNAME", length = 255)
	private String fullName;

	@JsonIgnore
	@Column(name = "PASSWORD", length = 100)
	private String password;

	@Column(name = "DEPARTMENTID", length = 128)
	private Long departmentId;

	@JsonIgnore
//	@Pattern(regexp="^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$") 
	@Column(name = "EMAIL", length = 255)
	private String email;

	@JsonIgnore
	@Column(name = "MOBILE", length = 255)
	private String mobile;

	@Column(name = "CREATED_DATE",updatable=false)
	private Date created_date;
	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	
	@Column(name = "BIRTHDAY")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@JsonIgnore
	@Column(name = "ENABLED")
	private Boolean enabled;

	@Column(name = "LDAP")
	private Boolean ldap;
	
	
	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;
	
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	
	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "IMAGEPATH")
	private String imagePath;

	@Column(name = "EXT")
	private String ext;
	
	@Column(name = "ACCESS_Flag")
	private Integer accessFlag;
	@Transient
	private String errorMessage;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "accountteam", joinColumns = { @JoinColumn(name = "ACCOUNTID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "TEAMID", nullable = false) })
	private Set<Team> teams = new HashSet<Team>(0);
	
	@Column(name = "STATUS", length = 50)
	private String status;

	@Column(name = "GENDER", length = 10)
	private String gender;

	@Column(name = "ON_BOARD_POSITION", length = 100)
	private String onBoardPosition;

	@Column(name = "POSITION", length = 100)
	private String position;

	@Column(name = "TYPE_OF_SALES_AGENT", length = 100)
	private String typeOfSalesAgent;

	@Column(name = "FIRST_NAME", length = 200)
	private String firstName;

	@Column(name = "LAST_NAME", length = 200)
	private String lastName;

	@Column(name = "OFFICE_NUMBER", length = 100)
	private String officeNumber;	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getLdap() {
		return ldap;
	}

	public void setLdap(Boolean ldap) {
		this.ldap = ldap;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Set<Team> getTeams() {
		return teams;
	}

	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Transient
	private String newpass;

	public String getNewpass() {
		return newpass;
	}

	public void setNewpass(String newpass) {
		this.newpass = newpass;
	}

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public Integer getAttempts() {
		return attempts;
	}

	public void setAttempts(Integer attempts) {
		this.attempts = attempts;
	}

	public String getLineManager() {
		return lineManager;
	}

	public void setLineManager(String lineManager) {
		this.lineManager = lineManager;
	}
	
	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getAccessFlag() {
		return accessFlag;
	}

	public void setAccessFlag(Integer accessFlag) {
		this.accessFlag = accessFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOnBoardPosition() {
		return onBoardPosition;
	}

	public void setOnBoardPosition(String onBoardPosition) {
		this.onBoardPosition = onBoardPosition;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getTypeOfSalesAgent() {
		return typeOfSalesAgent;
	}

	public void setTypeOfSalesAgent(String typeOfSalesAgent) {
		this.typeOfSalesAgent = typeOfSalesAgent;
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

	public String getOfficeNumber() {
		return officeNumber;
	}

	public void setOfficeNumber(String officeNumber) {
		this.officeNumber = officeNumber;
	}
		
}
