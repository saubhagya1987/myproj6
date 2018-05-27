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
@Table(name = "APPLYNOW")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_apply_now", sequenceName = "SEQ_APPLYNOW_APPLYNOWID")
public class ApplyNow {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_apply_now")
	@Column(name = "APPLYNOWID")
	private Long applyNowID;

	@Column(name = "FULLNAME", length = 200)
	private String fullName;

	@Column(name = "IDCARDNUMBER", length = 50)
	private String idCardNumber;

	@Column(name = "CELLPHONE", length = 50)
	private String cellPhone;

	@Column(name = "EMAIL", length = 100)
	private String email;

	@Column(name = "CITY", length = 50)
	private String city;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "STATUS")
	private Long status;

	@Column(name = "EXPORTEDBY", length = 100)
	private String exportedBy;

	@Column(name = "EXPORTEDDATE")
	private Date exportedDate;

	@Column(name = "SUBMITTEDDATE")
	private Date submittedDate;

	@Column(name = "MONTHLYINCOME")
	private Long monthlyIncome;

	@Column(name = "LOANAMOUNT")
	private Long loanAmount;

	@Column(name = "PRODUCT", length = 100)
	private String product;

	@Column(name = "LOANTENURE")
	private Long loanTenure;

	@Column(name = "EMI")
	private Double emi;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATED_BY", updatable = false)
	private String created_by;

	@Column(name = "UPDATE_BY")
	private String update_by;

	@Column(name = "CREATED_DATE", updatable = false)
	private Date created_date;

	@Column(name = "UPDATED_DATE")
	private Date update_date;

	public Long getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Long loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(Long loanTenure) {
		this.loanTenure = loanTenure;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ADDRESS", length = 200)
	private String address;

	public Long getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(Long monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	@ManyToOne
	@JoinColumn(name = "CUSTOMERID", referencedColumnName = "CUSTOMERID")
	private Customer customer;

	@ManyToOne
	@JoinColumn(name = "LOANID", referencedColumnName = "LOANID")
	private Loan loan;

	@ManyToOne
	@JoinColumn(name = "POSID", referencedColumnName = "POSID")
	private PosEmtity posEmtity;

	@Transient
	private Long loanId;

	@Transient
	private Long customerId;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getPosEmtityId() {
		return posEmtityId;
	}

	public void setPosEmtityId(Long posEmtityId) {
		this.posEmtityId = posEmtityId;
	}

	@Transient
	private Long posEmtityId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public PosEmtity getPosEmtity() {
		return posEmtity;
	}

	public void setPosEmtity(PosEmtity posEmtity) {
		this.posEmtity = posEmtity;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Long getApplyNowID() {
		return applyNowID;
	}

	public void setApplyNowID(Long applyNowID) {
		this.applyNowID = applyNowID;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getExportedBy() {
		return exportedBy;
	}

	public void setExportedBy(String exportedBy) {
		this.exportedBy = exportedBy;
	}

	public Date getExportedDate() {
		return exportedDate;
	}

	public void setExportedDate(Date exportedDate) {
		this.exportedDate = exportedDate;
	}

	public Double getEmi() {
		return emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
