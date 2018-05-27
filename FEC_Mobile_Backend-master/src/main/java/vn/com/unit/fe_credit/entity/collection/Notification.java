package vn.com.unit.fe_credit.entity.collection;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "notification")
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id_sequence")
	@SequenceGenerator(name = "notification_id_sequence", sequenceName = "NOTIFICATION_Id_SEQ")
	@Column(name = "notification_id")
	private Integer notificationId;
	
	@Column(name = "type", length = 200)
	private String type;
	@Column(name = "email", length = 50)
	private String email;
	@Column(name = "description", length = 512)
	private String description;
	@Column(name = "SEND_TIME")
	private Date notificationTime;
	@Column(name = "SENDER")
	private String sender;
	
	
	public Integer getNotificationId() {
		return notificationId;
	}
	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDateOfNotification() {
		return notificationTime;
	}
	public void setDateOfNotification(Date dateOfNotification) {
		this.notificationTime = dateOfNotification;
	}
	public Date getNotificationTime() {
		return notificationTime;
	}
	public void setNotificationTime(Date notificationTime) {
		this.notificationTime = notificationTime;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}	
	
}
