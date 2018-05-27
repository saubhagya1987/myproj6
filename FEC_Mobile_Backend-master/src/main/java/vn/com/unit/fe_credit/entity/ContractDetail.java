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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "CONTRACTDETAIL")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_contractdetail", sequenceName = "SEQ_CONTRACTDETAILID")
public class ContractDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contractdetail")
	@Column(name = "CONTRACTDETAILID")
	private Long contractdetailID;

	@Column(name = "APPID", length = 20)
	private String appID;

	@Column(name = "CONTRACTNUMBER", length = 50)
	private String contractNumber;

	@Column(name = "CUSTOMERNAME", length = 200)
	private String customerName;

	@Column(name = "CUSTOMERID_CARDNUMBER", length = 50)
	private String customerID_CardNumber;

	@Column(name = "CREDITAMOUNT")
	private Long creditAmount;

	@Column(name = "STATUS")
	private Integer contractStatus;

	@ManyToOne
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "CUSTOMERID")
	private Customer customer;

	@Column(name = "CREATEDDATE")
	private Date createddate;

	@ManyToOne
	@JoinColumn(name = "LOANDETAILID", referencedColumnName = "LOANDETAILID")
	private LoanDetail loandetail;

	public Date getCreateddate() {
		return createddate;
	}

	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	public Long getContractdetailID() {
		return contractdetailID;
	}

	public void setContractdetailID(Long contractdetailID) {
		this.contractdetailID = contractdetailID;
	}

	public String getAppID() {
		return appID;
	}

	public void setAppID(String appID) {
		this.appID = appID;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerID_CardNumber() {
		return customerID_CardNumber;
	}

	public void setCustomerID_CardNumber(String customerID_CardNumber) {
		this.customerID_CardNumber = customerID_CardNumber;
	}

	public Long getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Long creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Integer getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(Integer contractStatus) {
		this.contractStatus = contractStatus;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public LoanDetail getLoandetail() {
		return loandetail;
	}

	public void setLoandetail(LoanDetail loandetail) {
		this.loandetail = loandetail;
	}

}
