package vn.com.unit.utils;

import java.util.HashMap;
import java.util.Map;

public class Description {
	private Integer id;
	private String type;
	private String contractId;
	private String status;
	private NotificationMessages notificationMessages;
	private Map<String, Object> other = new HashMap<String, Object>();
	
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public NotificationMessages getNotificationMessages() {
		return notificationMessages;
	}
	public void setNotificationMessages(NotificationMessages notificationMessages) {
		this.notificationMessages = notificationMessages;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getOther() {
		return other;
	}
	public void setOther(String key, Object value) {
		this.other.put(key, value);
	}
	

}

