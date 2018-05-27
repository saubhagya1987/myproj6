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

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "LOAN")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_loan", sequenceName = "SEQ_LOAN_LOANID")
public class Loan {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_loan")
	@Column(name = "LOANID")
	private Long loanID;

	@Column(name = "CONDITIONCATEGORY", length = 50)
	private String condition_category;

	@Column(name = "CONDITIONNAME", length = 200)
	private String condition_name;
	
	@Getter @Setter
	@Column(name = "CONDITIONNAME_EN", length = 200)
	private String condition_name_en;

	@Column(name = "CONDITIONVALUE")
	private Integer condition_value;

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
	
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getLoanID() {
		return loanID;
	}

	public void setLoanID(Long loanID) {
		this.loanID = loanID;
	}

	public String getCondition_category() {
		return condition_category;
	}

	public void setCondition_category(String condition_category) {
		this.condition_category = condition_category;
	}

	public String getCondition_name() {
		return condition_name;
	}

	public void setCondition_name(String condition_name) {
		this.condition_name = condition_name;
	}

	public Integer getCondition_value() {
		return condition_value;
	}

	public void setCondition_value(Integer condition_value) {
		this.condition_value = condition_value;
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
	
}
