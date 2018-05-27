package vn.com.unit.fe_credit.entity.collection;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "record")
public class Record {	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "record_id_sequence")
	@SequenceGenerator(name = "record_id_sequence", sequenceName = "RECORD_ID_SEQ")
	@Column(name = "record_id")
	private Integer recordId;
	
	@Column(name = "contract_id", length = 50)
	private String contractId;
	
	@Column(name = "customer_id")
	private String 	customerId;

	@Column(name = "contact_mode", length = 100)
	private String contactMode;
	
	@Column(name = "place_contacted")
	private String placeContacted;
	

	@Column(name = "currency")
	private String currency;

	@Column(name = "reminder_mode", length = 50)
	private String reminderMode;

	@Column(name = "submit_by", length = 500)
	private String submitBy;
	
	@Column(name = "remarks")
	private String	remarks;

	@Column(name = "action_code")
	private String	actionCode;
	
	@Column(name = "check_in", length = 1000)
	private String checkIn;
	
	@Column(name = "person_contacted", length = 50)
	private String personContacted;

	@Column(name = "action_amount")
	private Double actionAmount;

	@Column(name = "financier_id")
	private String financierId;
	
	
	@Column(name = "action_date")
	@Temporal(TemporalType.DATE)
	private Date actionDate;
	
	@Column(name = "action_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date actionTime;
	@Column(name="status")
	private String status;
	@Column(name = "next_action_date")
	@Temporal(TemporalType.DATE)
	private Date nextActionDate;
	


	@Column(name = "next_action_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextActionTime;

	@Column(name = "contacted_by")
	private String contactedBy;
	
	@Column(name = "isskiptracer", length=1)
	private Character isskiptracer  ; 
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)	
	@JoinColumn(name="record_id")
	private Set<RecordAttachment> recordAttachment;

	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getContactMode() {
		return contactMode;
	}

	public void setContactMode(String contactMode) {
		this.contactMode = contactMode;
	}

	public String getPlaceContacted() {
		return placeContacted;
	}

	public void setPlaceContacted(String placeContacted) {
		this.placeContacted = placeContacted;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getReminderMode() {
		return reminderMode;
	}

	public void setReminderMode(String reminderMode) {
		this.reminderMode = reminderMode;
	}

	public String getSubmitBy() {
		return submitBy;
	}

	public void setSubmitBy(String submitBy) {
		this.submitBy = submitBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}

	public String getPersonContacted() {
		return personContacted;
	}

	public void setPersonContacted(String personContacted) {
		this.personContacted = personContacted;
	}

	public Double getActionAmount() {
		return actionAmount;
	}

	public void setActionAmount(Double actionAmount) {
		this.actionAmount = actionAmount;
	}

	public String getFinancierId() {
		return financierId;
	}

	public void setFinancierId(String financierId) {
		this.financierId = financierId;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getNextActionDate() {
		return nextActionDate;
	}

	public void setNextActionDate(Date nextActionDate) {
		this.nextActionDate = nextActionDate;
	}

	public Date getNextActionTime() {
		return nextActionTime;
	}

	public void setNextActionTime(Date nextActionTime) {
		this.nextActionTime = nextActionTime;
	}

	public String getContactedBy() {
		return contactedBy;
	}

	public void setContactedBy(String contactedBy) {
		this.contactedBy = contactedBy;
	}

	public Character getIsskiptracer() {
		return isskiptracer;
	}

	public void setIsskiptracer(Character isskiptracer) {
		this.isskiptracer = isskiptracer;
	}

	public Set<RecordAttachment> getRecordAttachment() {
		return recordAttachment;
	}

	public void setRecordAttachment(Set<RecordAttachment> recordAttachment) {
		this.recordAttachment = recordAttachment;
	}

		
}
