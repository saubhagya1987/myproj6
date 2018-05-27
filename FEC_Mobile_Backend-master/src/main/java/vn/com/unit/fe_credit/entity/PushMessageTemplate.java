package vn.com.unit.fe_credit.entity;

import java.io.Serializable;
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

import lombok.Data;

@Entity
@Table(name = "PUSH_MESSAGE_TEMPLATE")
@Data
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_push_message_template", sequenceName = "SEQ_PUSH_MESSAGE_TEMPLATE")
public class PushMessageTemplate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_push_message_template")
	@Column(name = "TEMPLATE_ID", length = 16)
	private Long templateId;

	@Column(name = "TEMPLATE_SUBJECT")
	private String templateSubject;

	@Column(name = "MESSAGE_TYPE")
	private Integer messageType;

	@Column(name = "FILE_NAME")
	private String fileName;

	@Column(name = "SCHEDULE")
	private Date schedule;

	@Column(name = "NO_IN_DATE", length = 3)
	private Integer noInDate;

	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_date;

	@Column(name = "CREATED_BY", length = 50, updatable = false)
	private String created_by;

	@Column(name = "UPDATED_BY")
	private String updated_by;

	@Column(name = "IS_SENDNOW")
	private Integer isSendNow;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "STATUS")
	private Boolean status;
	
	@Column(name = "PUSH_COUNT_SUCCESS")
	private Integer pushCountSuccess;
	
	@Column(name = "PUSH_COUNT_FAIL")
	private Integer pushCountFail;

}
