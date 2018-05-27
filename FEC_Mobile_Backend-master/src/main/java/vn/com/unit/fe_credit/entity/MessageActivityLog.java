package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "MESSAGEACTIVITYLOG")
@SequenceGenerator(allocationSize = 1, name = "seq_messageactivity_log", sequenceName = "SEQ_MESSAGEACTIVITYLOGID")
@JsonAutoDetect
public class MessageActivityLog {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_messageactivity_log")
	@Column(name = "ACTIVITYID")
	private Long activityId;

	@Column(name = "LOGCODE", length = 1000)
	private String logCode;
	@Column(name = "LOGDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logDate;
	@Column(name = "DESCRITPTION", length = 1000)
	private String descritption;

	@Column(name = "USERTYPE")
	private Long userType;
	@Column(name = "TYPE")
	private Long type;
	@Column(name = "REFEID")
	private Long refeId;

	@Column(name = "USERID")
	private Long userId;

	@Column(name = "ID")
	private Long Id;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getLogCode() {
		return logCode;
	}

	public void setLogCode(String logCode) {
		this.logCode = logCode;
	}

	
	
	

	public String getDescritption() {
		return descritption;
	}

	public void setDescritption(String descritption) {
		this.descritption = descritption;
	}

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public Long getUserType() {
		return userType;
	}

	public void setUserType(Long userType) {
		this.userType = userType;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getRefeId() {
		return refeId;
	}

	public void setRefeId(Long refeId) {
		this.refeId = refeId;
	}

}
