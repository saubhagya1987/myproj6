package vn.com.unit.utils;

import java.util.Date;

import vn.com.unit.fe_credit.entity.collection.Notification;

public class NotificationUtil {

	public Notification _createNotification(String description, String email, String type,String sender) {
		// TODO Auto-generated method stub
		Notification notification = new Notification();
		notification.setDateOfNotification(new Date());
		notification.setDescription(description);
		notification.setEmail(email);
		notification.setType(type);
		notification.setSender(sender);
		return notification;
	}
}
