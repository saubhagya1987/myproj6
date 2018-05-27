package vn.com.unit.fe_credit.bean;

import java.util.Date;
import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.ContractDetail;
import vn.com.unit.fe_credit.entity.ContractInstallment;
import vn.com.unit.fe_credit.entity.ContractMessages;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PaymentHistory;

public class ContractDetailBean extends AbstractBean<ContractDetail> {
	private Integer status = 0;
	private Date fromDate;
	private Date toDate;
	private List<ActivityLog> activityLogs;
	private List<MessageDashBoard> messgesLst;
	private List<PaymentHistory> paymentLst;
	private List<ContractInstallment> installmentLst;
	private String contractnumber;
	private String cellphone;
	private String fullname;
	private String customeridcard;
	private String searchField;

	public List<PaymentHistory> getPaymentLst() {
		return paymentLst;
	}

	public void setPaymentLst(List<PaymentHistory> paymentLst) {
		this.paymentLst = paymentLst;
	}

	public List<ContractInstallment> getInstallmentLst() {
		return installmentLst;
	}

	public void setInstallmentLst(List<ContractInstallment> installmentLst) {
		this.installmentLst = installmentLst;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getContractnumber() {
		return contractnumber;
	}

	public void setContractnumber(String contractnumber) {
		this.contractnumber = contractnumber;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getCustomeridcard() {
		return customeridcard;
	}

	public void setCustomeridcard(String customeridcard) {
		this.customeridcard = customeridcard;
	}

	public List<ActivityLog> getActivityLogs() {
		return activityLogs;
	}

	public void setActivityLogs(List<ActivityLog> activityLogs) {
		this.activityLogs = activityLogs;
	}

	public List<MessageDashBoard> getMessgesLst() {
		return messgesLst;
	}

	public void setMessgesLst(List<MessageDashBoard> messgesLst) {
		this.messgesLst = messgesLst;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public ContractDetailBean() {
		this.entity = new ContractDetail();
	}

}
