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
@Table(name = "ContractMessages")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_contractmessages",sequenceName="SEQ_CONTRACTMESSAGESID")
public class ContractMessages {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_contractmessages")
	@Column(name = "ContractMessagesId")
	private Long contractMessagesId;
	
	@Column(name = "Subject", length = 255)
	private String subject;
	
	@Column(name = "Datetime")
	private Date datetime;
	
	@Column(name = "ContractNumber", length = 255)
	private String contractNumber;
	
	@Column(name = "Status")
	private Integer status;

	@Column(name = "CustomerId")
	private Long customerId;
	
	@Transient
	private Integer commentsCount;
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Long getContractMessagesId() {
		return contractMessagesId;
	}

	public void setContractMessagesId(Long contractMessagesId) {
		this.contractMessagesId = contractMessagesId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
