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
@Table(name = "CONTRACTINSTALLMENT")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_contractinstallment", sequenceName = "SEQ_CONTRACTINSTALLMENTID")
public class ContractInstallment {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contractinstallment")
	@Column(name = "CONTRACTINSTALLMENTID")
	private Long contractinstallmentID;

	@Column(name = "CONTRACTNUMBER", length = 50)
	private String contractNumber;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "PAYMENTDATE")
	@Temporal(TemporalType.DATE)
	private Date paymentDate;

	@Column(name = "DUEDATE")
	@Temporal(TemporalType.DATE)
	private Date dueDate;

	@Column(name = "STATUS")
	private Long status;

	@Transient
	private Long contractdetailID;

	public Long getContractdetailID() {
		return contractdetailID;
	}

	public void setContractdetailID(Long contractdetailID) {
		this.contractdetailID = contractdetailID;
	}

	public Long getContractinstallmentID() {
		return contractinstallmentID;
	}

	public void setContractinstallmentID(Long contractinstallmentID) {
		this.contractinstallmentID = contractinstallmentID;
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

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

}
