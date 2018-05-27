package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "CONTACT")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_paymenthistory", sequenceName = "SEQ_PAYMENTHISTORYID")
public class PaymentHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_paymenthistory")
	@Column(name = "PAYMENTHISTORYID")
	private Long paymenthistoryID;

	@Transient
	private Long contractdetailID;

	@Column(name = "TRANSACTIONCODE", length = 100)
	private String transactionCode;

	@Column(name = "CONTRACTNUMBER", length = 50)
	private String contractNumber;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "PAYMENTDATE")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@Column(name = "NOTE", length = 500)
	private String note;

	public Long getPaymenthistoryID() {
		return paymenthistoryID;
	}

	public void setPaymenthistoryID(Long paymenthistoryID) {
		this.paymenthistoryID = paymenthistoryID;
	}

	public String getTransactionCode() {
		return transactionCode;
	}

	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getContractdetailID() {
		return contractdetailID;
	}

	public void setContractdetailID(Long contractdetailID) {
		this.contractdetailID = contractdetailID;
	}

}
