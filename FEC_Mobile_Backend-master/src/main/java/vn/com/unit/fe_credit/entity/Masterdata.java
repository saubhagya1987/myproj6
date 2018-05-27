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
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "MASTERDATA")
@SequenceGenerator(allocationSize = 1, name = "seq_masterdata", sequenceName = "SEQ_MASTERDATA_MASTERDATAID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
@Unique(hql = "FROM Masterdata ac WHERE ac.name = :name AND ac.masterdataId != :masterdataId ", idField = "masterdataId", message = "{hobby.Code.Fail}", errorAtField = "name")
public class Masterdata {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_masterdata")
	@Column(name = "MASTERDATAID")
	private Long masterdataId;


	@Column(name = "NAME", length = 255)
	@NotEmpty
	private String name;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	

	@Column(name = "CREATED_DATE",updatable=false)
	private Date created_date;
	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	public Long getMasterdataId() {
		return masterdataId;
	}

	public void setMasterdataId(Long masterdataId) {
		this.masterdataId = masterdataId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
	}
	
	
}
