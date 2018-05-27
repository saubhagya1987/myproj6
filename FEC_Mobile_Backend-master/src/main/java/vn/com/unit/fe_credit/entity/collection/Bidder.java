/**
 * 
 */
package vn.com.unit.fe_credit.entity.collection;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import vn.com.unit.utils.RepossessionAndBidderStatus;

/**
 * @author rahul
 *
 */
@Entity
@Table(name="bidder")
public class Bidder {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,  generator="bidder_seq")
	@SequenceGenerator(name="bidder_seq", sequenceName="BIDDER_SEQ")
	private Long id;
	@Column(name="bidder_name")
	private String bidderName;
	
	@Column(name="id_number")
	private String idNumber;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="amount")
	private BigDecimal amount;
	
	@Column(name="entry_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date entryDate;
	
	@Column(name="request_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date requestDate;
	
	@Column(name="approved_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date approvedDate;
	
	@Column(name="user_request")
	private String userRequest;
	
	@Column(name="user_approve")
	private String userApprove;
	
	@Column(name="repossession_id")
	private Long repossessionId;
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private RepossessionAndBidderStatus status;
	
	@Column(name="APPL_ID")
	private String applId;
	
	/**
	 * @return the status
	 */
	public RepossessionAndBidderStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(RepossessionAndBidderStatus status) {
		this.status = status;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @return the bidderName
	 */
	public String getBidderName() {
		return bidderName;
	}
	/**
	 * @return the idNumber
	 */
	public String getIdNumber() {
		return idNumber;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}
	/**
	 * @return the entryDate
	 */
	public Date getEntryDate() {
		return entryDate;
	}
	/**
	 * @return the requestDate
	 */
	public Date getRequestDate() {
		return requestDate;
	}
	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}
	/**
	 * @return the userRequest
	 */
	public String getUserRequest() {
		return userRequest;
	}
	/**
	 * @return the userApprove
	 */
	public String getUserApprove() {
		return userApprove;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @param bidderName the bidderName to set
	 */
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}
	/**
	 * @param idNumber the idNumber to set
	 */
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	/**
	 * @param entryDate the entryDate to set
	 */
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	/**
	 * @param requestDate the requestDate to set
	 */
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	/**
	 * @param userRequest the userRequest to set
	 */
	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}
	/**
	 * @param userApprove the userApprove to set
	 */
	public void setUserApprove(String userApprove) {
		this.userApprove = userApprove;
	}
/**
	 * @return the repossessionId
	 */
	public Long getRepossessionId() {
		return repossessionId;
	}
	/**
	 * @param repossessionId the repossessionId to set
	 */
	public void setRepossessionId(Long repossessionId) {
		this.repossessionId = repossessionId;
	}
	public String getApplId() {
		return applId;
	}
	public void setApplId(String applId) {
		this.applId = applId;
	}
}
