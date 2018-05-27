package vn.com.unit.fe_credit.bean;

import vn.com.unit.fe_credit.entity.Banner;

public class BannerBean extends AbstractBean<Banner>{
	private String lstImage;
	private String lstImageLink;
	private String category;
	private Integer status;
	private String bannerCode;
	private boolean checkDateTo;
	public String getLstImageLink() {
		return lstImageLink;
	}

	public void setLstImageLink(String lstImageLink) {
		this.lstImageLink = lstImageLink;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBannerCode() {
		return bannerCode;
	}

	public void setBannerCode(String bannerCode) {
		this.bannerCode = bannerCode;
	}

	public String getLstImage() {
		return lstImage;
	}

	public void setLstImage(String lstImage) {
		this.lstImage = lstImage;
	}

	public boolean isCheckDateTo() {
		return checkDateTo;
	}

	public void setCheckDateTo(boolean checkDateTo) {
		this.checkDateTo = checkDateTo;
	}
	
}
