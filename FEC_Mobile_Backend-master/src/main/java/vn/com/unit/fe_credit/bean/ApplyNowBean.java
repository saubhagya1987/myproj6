package vn.com.unit.fe_credit.bean;

import java.util.Date;

import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.ApplyNow;

public class ApplyNowBean extends AbstractBean<ApplyNow> {
	private Integer status = 1;
	private Integer reStatus = 1;
	private Double applyNowID;

	public Double getApplyNowID() {
		return applyNowID;
	}

	public void setApplyNowID(Double applyNowID) {
		this.applyNowID = applyNowID;
	}

	public Integer getReStatus() {
		return reStatus;
	}

	public void setReStatus(Integer reStatus) {
		this.reStatus = reStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private Date submitedFrom;
	private Date submitedTo;

	public Date getSubmitedFrom() {
		return submitedFrom;
	}

	public void setSubmitedFrom(Date submitedFrom) {
		this.submitedFrom = submitedFrom;
	}

	public Date getSubmitedTo() {
		return submitedTo;
	}

	public void setSubmitedTo(Date submitedTo) {
		this.submitedTo = submitedTo;
	}

	public ApplyNowBean() {
		this.entity = new ApplyNow();
	}

}
