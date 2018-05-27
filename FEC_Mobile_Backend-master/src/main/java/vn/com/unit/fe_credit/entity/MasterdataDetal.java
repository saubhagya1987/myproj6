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
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "MASTERDATADETAIL")
@SequenceGenerator(allocationSize = 1, name = "seq_masterdatadetail", sequenceName = "SEQ_MASTERDATADETAIL")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
//@Unique(hql = "FROM MasterdataDetal ac WHERE ac.name =:name AND ac.masterdata.masterdataId = :masterdataId AND ac.masterdatadetailId != :masterdatadetailId ", idField = "masterdatadetailId", message = "{hobby.Code.Fail}", errorAtField = "name")

public class MasterdataDetal {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_masterdatadetail")
	@Column(name = "MASTERDATADETAILID")
	private Long masterdatadetailId;

	@Column(name = "NAME", length = 255)
	@NotEmpty
	private String name;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	
	@ManyToOne
	@JoinColumn(name = "MASTERDATAID", referencedColumnName = "MASTERDATAID")
	private Masterdata masterdata;
	
	
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
		

	public Long getMasterdatadetailId() {
		return masterdatadetailId;
	}

	public void setMasterdatadetailId(Long masterdatadetailId) {
		this.masterdatadetailId = masterdatadetailId;
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

	public Masterdata getMasterdata() {
		return masterdata;
	}

	public void setMasterdata(Masterdata masterdata) {
		this.masterdata = masterdata;
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
	
	@Transient
	private String parentName;

	@Transient
	private String childName;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getChildName() {
		return childName;
	}

	public void setChildName(String childName) {
		this.childName = childName;
	}

}
