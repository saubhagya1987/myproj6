package vn.com.unit.fe_credit.entity;

import java.beans.Transient;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "LOANDETAIL")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_loandetail", sequenceName = "SEQ_LOANDETAIL_LOANDETAILID")
public class LoanDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_loandetail")
	@Column(name = "LOANDETAILID")
	private Long loanDetailID;

	@Column(name = "MINAMOUNT")
	private Double minamount;

	@Column(name = "MAXAMOUNT")
	private Double maxamount;

	@Column(name = "AMOUNTSLIDE")
	private Double amountslide;

	@Column(name = "MINTENURE")
	private Integer mintenure;

	@Column(name = "MAXTENURE")
	private Integer maxtenure;

	@Column(name = "LOANAMOUNT")
	private Double loanamount;

	@Column(name = "MONTHLYPAYMENT")
	private Integer monthlypayment;

	@Column(name = "LOANTENURE")
	private Integer loantenure;

	@Column(name = "TENUREPERSLIDE")
	private Integer tenureperslide;

	@Column(name = "REFERENCETEXT", length = 2000)
	private String referencetext;
	
	@Column(name = "TENURE", length = 2000)
	private String tenure;

	@Column(name = "REQUIREDDOCUMENTS")
	private Integer requireddoucment;

	@Column(name = "CREATEDATE",updatable=false)
	private Date createDate;
	
	
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "LOANID", referencedColumnName = "LOANID")
	private Loan loan;

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getLoanDetailID() {
		return loanDetailID;
	}

	public void setLoanDetailID(Long loanDetailID) {
		this.loanDetailID = loanDetailID;
	}

	public Double getMinamount() {
		return minamount;
	}

	public void setMinamount(Double minamount) {
		this.minamount = minamount;
	}

	public Double getMaxamount() {
		return maxamount;
	}

	public void setMaxamount(Double maxamount) {
		this.maxamount = maxamount;
	}

	public Double getAmountslide() {
		return amountslide;
	}

	public void setAmountslide(Double amountslide) {
		this.amountslide = amountslide;
	}

	public Integer getMintenure() {
		return mintenure;
	}

	public void setMintenure(Integer mintenure) {
		this.mintenure = mintenure;
	}

	public Integer getMaxtenure() {
		return maxtenure;
	}

	public void setMaxtenure(Integer maxtenure) {
		this.maxtenure = maxtenure;
	}

	public Integer getTenureperslide() {
		return tenureperslide;
	}

	public void setTenureperslide(Integer tenureperslide) {
		this.tenureperslide = tenureperslide;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
	}

	public Double getLoanamount() {
		return loanamount;
	}

	public void setLoanamount(Double loanamount) {
		this.loanamount = loanamount;
	}

	public Integer getMonthlypayment() {
		return monthlypayment;
	}

	public void setMonthlypayment(Integer monthlypayment) {
		this.monthlypayment = monthlypayment;
	}

	public Integer getLoantenure() {
		return loantenure;
	}

	public void setLoantenure(Integer loantenure) {
		this.loantenure = loantenure;
	}

	public String getReferencetext() {
		return referencetext;
	}

	public void setReferencetext(String referencetext) {
		this.referencetext = referencetext;
	}

	public Integer getRequireddoucment() {
		return requireddoucment;
	}

	public void setRequireddoucment(Integer requireddoucment) {
		this.requireddoucment = requireddoucment;
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

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
	}

	public String getTenure() {
		return tenure;
	}

	public void setTenure(String tenure) {
		this.tenure = tenure;
	}
	
}
