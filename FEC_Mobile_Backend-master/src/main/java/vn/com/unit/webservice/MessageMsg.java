package vn.com.unit.webservice;

import java.util.ArrayList;

import org.hibernate.mapping.Array;

import vn.com.unit.fe_credit.entity.DataImg;
import antlr.collections.List;

public class MessageMsg {

	private Long customerId;

	private Long parentMsgId;
	private String customerName;
	private String cellPhone;
	private String contractMsgId;	
	private MsgSub msg;
	private Integer type;
	private Integer category;
	private Integer subcategory;
	
	public String getContractMsgId() {
		return contractMsgId;
	}

	public void setContractMsgId(String contractMsgId) {
		this.contractMsgId = contractMsgId;
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

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(Integer subcategory) {
		this.subcategory = subcategory;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public MsgSub getMsg() {
		return msg;
	}

	public void setMsg(MsgSub msg) {
		this.msg = msg;
	}

	public Long getParentMsgId() {
		return parentMsgId;
	}

	public void setParentMsgId(Long parentMsgId) {
		this.parentMsgId = parentMsgId;
	}
}

class dataImg
{
private String fileName;
private String imgData;
public String getFileName() {
	return fileName;
}
public void setFileName(String fileName) {
	this.fileName = fileName;
}
public String getImgData() {
	return imgData;
}
public void setImgData(String imgData) {
	this.imgData = imgData;
}
}


class MsgSub {

	private String subject;
	private String content;
	private ArrayList<DataImg> attachment;
//	private ArrayList<dataImg>dataImg;
//
//	
//
//	public ArrayList<dataImg> getDataImg() {
//		return dataImg;
//	}
//
//	public void setDataImg(ArrayList<dataImg> dataImg) {
//		this.dataImg = dataImg;
//	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<DataImg> getAttachment() {
		return attachment;
	}

	public void setAttachment(ArrayList<DataImg> attachment) {
		this.attachment = attachment;
	}

	

}