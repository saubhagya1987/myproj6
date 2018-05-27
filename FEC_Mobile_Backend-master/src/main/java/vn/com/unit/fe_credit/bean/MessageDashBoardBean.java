package vn.com.unit.fe_credit.bean;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.webservice.Hotline;

public class MessageDashBoardBean extends AbstractBean<MessageDashBoard> {
	private String pathFile;
	private CommonsMultipartFile fileUpload;
	private List<MessageDashBoard> messageImportLst;
	private List<Hotline> messageTypeDAO;
	private String customerName;
	private String cellPhone;
	private MessageDashBoard message;
	private String subject;
	private String isRead;;
	private Date formSendDate;
	private Date toSendDate;
	private String messageType;
	private String status;

	public Date getFormSendDate() {
		return formSendDate;
	}

	public void setFormSendDate(Date formSendDate) {
		this.formSendDate = formSendDate;
	}

	public Date getToSendDate() {
		return toSendDate;
	}

	public void setToSendDate(Date toSendDate) {
		this.toSendDate = toSendDate;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public MessageDashBoard getMessage() {
		return message;
	}

	public void setMessage(MessageDashBoard message) {
		this.message = message;
	}

	public String getPathFile() {
		return pathFile;
	}

	public void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	public CommonsMultipartFile getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(CommonsMultipartFile fileUpload) {
		this.fileUpload = fileUpload;
	}

	public List<MessageDashBoard> getMessageImportLst() {
		return messageImportLst;
	}

	public void setMessageImportLst(List<MessageDashBoard> messageImportLst) {
		this.messageImportLst = messageImportLst;
	}

	public List<Hotline> getMessageTypeDAO() {
		return messageTypeDAO;
	}

	public void setMessageTypeDAO(List<Hotline> messageTypeDAO) {
		this.messageTypeDAO = messageTypeDAO;
	}


	public String getIsRead() {
		return isRead;
	}

	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	
}
