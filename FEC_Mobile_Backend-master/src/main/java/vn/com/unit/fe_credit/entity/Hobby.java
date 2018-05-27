package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "HOBBY")
@SequenceGenerator(allocationSize = 1, name = "seq_hobby", sequenceName = "SEQ_HOBBY_HOBBYID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
@Unique(hql = "FROM Hobby hb WHERE hb.code = :code AND hb.hobbyId != :hobbyId ", idField = "hobbyId", message = "{hobby.Code.Fail}", errorAtField = "code")
public class Hobby {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_hobby")
	@Column(name = "HOBBYID")
	private Long hobbyId;

	@Column(name = "CODE", length = 50)
	@NotEmpty
	private String code;

	@Column(name = "NAME", length = 255)
	@NotEmpty
	private String name;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Column(name = "STATUS", length = 50)
	@NotEmpty
	private String status;

	@Transient
	private Long activity;

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
	
	public Long getActivity() {
		return activity;
	}

	public void setActivity(Long activity) {
		this.activity = activity;
	}

	public Long getHobbyId() {
		return hobbyId;
	}

	public void setHobbyId(Long hobbyId) {
		this.hobbyId = hobbyId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
