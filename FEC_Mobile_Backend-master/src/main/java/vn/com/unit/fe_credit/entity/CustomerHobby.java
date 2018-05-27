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

@Entity
@Table(name="customerHobby")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_customerhobby",sequenceName="SEQ_CUSTOMERHOBBYID")
public class CustomerHobby {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_customerhobby")
	@Column(name = "CustomerHobbyId")
	private Long customerHobbyId;

	@Column(name = "CheckedDate")
	private Date checkedDate;
	
	@Column(name = "Status")
	private Integer status;
	
	@Column(name = "CustomerId")
	private Long customerId;

	@Column(name = "HobbyId")
	private Long hobbyId;
	
	@Transient
	private String name;
	@Transient
	private String code;
	
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

	public Long getHobbyId() {
		return hobbyId;
	}

	public void setHobbyId(Long hobbyId) {
		this.hobbyId = hobbyId;
	}

	public Long getCustomerHobbyId() {
		return customerHobbyId;
	}

	public void setCustomerHobbyId(Long customerHobbyId) {
		this.customerHobbyId = customerHobbyId;
	}

	public Date getCheckedDate() {
		return checkedDate;
	}

	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
