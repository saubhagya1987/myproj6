/**
 * 
 */
package vn.com.unit.fe_credit.bean.v1;

import java.math.BigDecimal;
import java.util.Date;

public class BidderDto {
	private Long id;	
	private BigDecimal amount;
	private Date approvedDate;
	private String bidderName;
	private Date entryDate;
	private String idNumber;
	private String phoneNumber;	
	private Integer repossessionId;
	private Date requestDate;
	private String userApprove;
	private String  userRequest;
	private String  status;
	private String  applId;
	private String approvalStatus;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	public String getBidderName() {
		return bidderName;
	}
	public void setBidderName(String bidderName) {
		this.bidderName = bidderName;
	}
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Integer getRepossessionId() {
		return repossessionId;
	}
	public void setRepossessionId(Integer repossessionId) {
		this.repossessionId = repossessionId;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getUserApprove() {
		return userApprove;
	}
	public void setUserApprove(String userApprove) {
		this.userApprove = userApprove;
	}
	public String getUserRequest() {
		return userRequest;
	}
	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApplId() {
		return applId;
	}
	public void setApplId(String applId) {
		this.applId = applId;
	}
	public String getApprovalStatus() {
		return approvalStatus;
	}
	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}
	
	
	
}
