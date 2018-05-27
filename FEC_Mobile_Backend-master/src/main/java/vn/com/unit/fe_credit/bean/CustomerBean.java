package vn.com.unit.fe_credit.bean;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import vn.com.unit.fe_credit.entity.ContractMessages;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.CustomerActivityLog;
import vn.com.unit.fe_credit.entity.CustomerHobby;
import vn.com.unit.fe_credit.entity.CustomerWish;
import vn.com.unit.fe_credit.entity.MessageDashBoard;

public class CustomerBean extends AbstractBean<Customer> {
	private String pathFile;
	private CommonsMultipartFile fileUpload;
	private String customerName;
	private String cellPhone;
	private String cardNo;
	private String addrestmap;
	private String shearAddress;
	private String shearLoan;
	private Integer status;
	private String succesorfail;
	private List<Customer> customerImportLst;
	private List<CustomerHobby> customerHobbyLst;
	private List<CustomerWish> customerWishLst;
	private List<MessageDashBoard> messgesLst;
	private Map<String, Object> messgesLstMap;
	private List<MessageDashBoard> messgesLstContract;

	public List<MessageDashBoard> getMessgesLstContract() {
		return messgesLstContract;
	}

	public void setMessgesLstContract(List<MessageDashBoard> messgesLstContract) {
		this.messgesLstContract = messgesLstContract;
	}

	private List<ContractMessages> contractMessagesLst;
	
	private List<CustomerActivityLog> customerActivityLogs;

	
	public List<CustomerActivityLog> getCustomerActivityLogs() {
		return customerActivityLogs;
	}

	public void setCustomerActivityLogs(
			List<CustomerActivityLog> customerActivityLogs) {
		this.customerActivityLogs = customerActivityLogs;
	}

	public List<MessageDashBoard> getMessgesLst() {
		return messgesLst;
	}

	public void setMessgesLst(List<MessageDashBoard> messgesLst) {
		this.messgesLst = messgesLst;
	}

	public List<ContractMessages> getContractMessagesLst() {
		return contractMessagesLst;
	}

	public void setContractMessagesLst(
			List<ContractMessages> contractMessagesLst) {
		this.contractMessagesLst = contractMessagesLst;
	}

	public List<CustomerWish> getCustomerWishLst() {
		return customerWishLst;
	}

	public void setCustomerWishLst(List<CustomerWish> customerWishLst) {
		this.customerWishLst = customerWishLst;
	}

	public List<CustomerHobby> getCustomerHobbyLst() {
		return customerHobbyLst;
	}

	public void setCustomerHobbyLst(List<CustomerHobby> customerHobbyLst) {
		this.customerHobbyLst = customerHobbyLst;
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

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAddrestmap() {
		return addrestmap;
	}

	public void setAddrestmap(String addrestmap) {
		this.addrestmap = addrestmap;
	}

	public String getShearAddress() {
		return shearAddress;
	}

	public void setShearAddress(String shearAddress) {
		this.shearAddress = shearAddress;
	}

	public String getShearLoan() {
		return shearLoan;
	}

	public void setShearLoan(String shearLoan) {
		this.shearLoan = shearLoan;
	}

	public String getSuccesorfail() {
		return succesorfail;
	}

	public void setSuccesorfail(String succesorfail) {
		this.succesorfail = succesorfail;
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

	public List<Customer> getCustomerImportLst() {
		return customerImportLst;
	}

	public void setCustomerImportLst(List<Customer> customerImportLst) {
		this.customerImportLst = customerImportLst;
	}

	public Map<String, Object> getMessgesLstMap() {
		return messgesLstMap;
	}

	public void setMessgesLstMap(Map<String, Object> messgesLstMap) {
		this.messgesLstMap = messgesLstMap;
	}
	
}
