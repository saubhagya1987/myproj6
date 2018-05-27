package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USER_ATTEMPTS")
@Getter
@Setter
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_user_attempts", sequenceName = "SEQ_USER_ATTEMPTS")
public class UserAttempts {

	public UserAttempts() {
		// TODO Auto-generated constructor stub
	}

	public UserAttempts(String ipAddress, String username) {
		setIpAddress(ipAddress);
		setUsername(username);
		setLastModified(new Date());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user_attempts")
	@Column(name = "USER_ATTEMPTS_ID", length = 16)
	private Long userAttemptsId;

	@Column(name = "IP_ADDRESS")
	private String ipAddress;

	@Column(name = "USERNAME")
	private String username;

	@Column(name = "ATTEMPTS")
	private Integer attempts = 0;

	@Column(name = "LAST_MODIFIED")
	private Date lastModified;

}
