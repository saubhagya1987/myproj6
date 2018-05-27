package vn.com.unit.fe_credit.bean;


import vn.com.unit.fe_credit.entity.SystemConfigEntity;

public class SystemConfigBean extends AbstractBean<SystemConfigEntity> {

	private Long accountChartId;
	
	private Long currencyId;
	public SystemConfigBean() {
		this.entity=new SystemConfigEntity();
	}
	public Long getAccountChartId() {
		return accountChartId;
	}
	public void setAccountChartId(Long accountChartId) {
		this.accountChartId = accountChartId;
	}
	public Long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}


}
