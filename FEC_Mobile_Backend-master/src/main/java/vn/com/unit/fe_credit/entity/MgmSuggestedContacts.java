package vn.com.unit.fe_credit.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "MGM_SUGGESTED_CONTACTS")
@JsonAutoDetect
public class MgmSuggestedContacts extends MgmSuggestedContactsPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MgmSuggestedContactsPK pk;

	@Column(name = "RELATION_NAME", length = 50)
	private String relationName;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMERID")
	private Customer customer;
	 
	@Column(name = "CONTACT_NAME", length = 150)
	private String contactName;

	@Column(name = "NATIONAL_ID", length = 20)
	private String nationalId;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SUBMITED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submitedDate;
	
	@Column(name = "INVITE_METHOD")
	private Integer inviteMethod;

	@Column(name = "VALIDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date validatedDate;

	@Column(name = "REF_CONTRACT_ID", length = 50)
	private String refContractId;

	@Column(name = "IS_ALREADY_LOAN")
	private Boolean isAlreadyLoan;

	@Column(name = "AGE_RANGE", length = 50)
	private String ageRange;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date update_date;

	@Column(name = "CREATED_BY", length = 50, updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "CONTACT_ID_CARD_NUMBER")
	private Integer contactIdCardNumber;
	
	private String contactPhone;
	
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}

	public MgmSuggestedContactsPK getPk() {
		return pk;
	}

	public void setPk(MgmSuggestedContactsPK pk) {
		this.pk = pk;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSubmitedDate() {
		return submitedDate;
	}

	public void setSubmitedDate(Date submitedDate) {
		this.submitedDate = submitedDate;
	}

	public Integer getInviteMethod() {
		return inviteMethod;
	}

	public void setInviteMethod(Integer inviteMethod) {
		this.inviteMethod = inviteMethod;
	}

	public Date getValidatedDate() {
		return validatedDate;
	}

	public void setValidatedDate(Date validatedDate) {
		this.validatedDate = validatedDate;
	}

	public String getRefContractId() {
		return refContractId;
	}

	public void setRefContractId(String refContractId) {
		this.refContractId = refContractId;
	}

	public Boolean getIsAlreadyLoan() {
		return isAlreadyLoan;
	}

	public void setIsAlreadyLoan(Boolean isAlreadyLoan) {
		this.isAlreadyLoan = isAlreadyLoan;
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

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Integer getContactIdCardNumber() {
		return contactIdCardNumber;
	}

	public void setContactIdCardNumber(Integer contactIdCardNumber) {
		this.contactIdCardNumber = contactIdCardNumber;
	}
	
}
