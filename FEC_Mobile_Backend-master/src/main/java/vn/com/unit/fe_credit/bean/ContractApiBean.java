package vn.com.unit.fe_credit.bean;

import java.util.Date;
import java.util.List;

import vn.com.unit.fe_credit.entity.ActivityLog;
import vn.com.unit.fe_credit.entity.ContractDetailApi;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;

public class ContractApiBean extends AbstractBean<ContractDetailApi> {

	private String contractnumber;
	private String cellphone;
	private String customeridcard;
	private Date fromDate;
	private Date toDate;

	private List<ContractDetailApi> listContractdetailApi;
	private List<ActivityLog> activityLogs;


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

	public List<ContractDetailApi> getListContractdetailApi() {
		return listContractdetailApi;
	}

	public List<ActivityLog> getActivityLogs() {
		return activityLogs;
	}

	public void setActivityLogs(List<ActivityLog> activityLogs) {
		this.activityLogs = activityLogs;
	}

	public void setListContractdetailApi(
			List<ContractDetailApi> listContractdetailApi) {
		this.listContractdetailApi = listContractdetailApi;
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

	public String getCustomeridcard() {
		return customeridcard;
	}

	public void setCustomeridcard(String customeridcard) {
		this.customeridcard = customeridcard;
	}

}
