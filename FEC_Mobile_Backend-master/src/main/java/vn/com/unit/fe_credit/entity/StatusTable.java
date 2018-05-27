package vn.com.unit.fe_credit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "STATUS_TABLE")
//@SequenceGenerator(allocationSize=1,name="seq_status_table",sequenceName="SEQ_STATUS_TABLE_STATUS_TABLEID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
public class StatusTable {
	@Id
	@GeneratedValue
	@Column(name = "STATUSID")
	private Long status_tableId;

	@Column(name = "STATUS_TEXT", length = 50)
	@NotEmpty
	private String status_text;

	@Column(name = "STATUS_VALUE", length = 10)
	@NotNull
	private Integer status_value;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	public Long getStatus_tableId() {
		return status_tableId;
	}

	public void setStatus_tableId(Long status_tableId) {
		this.status_tableId = status_tableId;
	}

	public String getStatus_text() {
		return status_text;
	}

	public void setStatus_text(String status_text) {
		this.status_text = status_text;
	}

	public Integer getStatus_value() {
		return status_value;
	}

	public void setStatus_value(Integer status_value) {
		this.status_value = status_value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
