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

@Entity
@Table(name = "CONTACT")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_contact", sequenceName = "SEQ_CONTACT_CONTACTID")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_contact")
	@Column(name = "CONTACTID")
	private Long contactID;

	@Column(name = "CONTENT", length = 255)
	private String content;

	@Column(name = "CONTENTCODE", length = 100)
	private String contentcode;
	
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
	
	public Long getContactID() {
		return contactID;
	}

	public void setContactID(Long contactID) {
		this.contactID = contactID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentcode() {
		return contentcode;
	}

	public void setContentcode(String contentcode) {
		this.contentcode = contentcode;
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
