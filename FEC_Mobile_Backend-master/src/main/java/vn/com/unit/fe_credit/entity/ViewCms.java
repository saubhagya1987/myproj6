package vn.com.unit.fe_credit.entity;

import java.util.Date;
import java.util.List;

public class ViewCms {

	private String cmsId;

	private String title;

	private String imagePath;

	private List<String> ortherCat;

	private String imagesquare;

	private String imagesquare_VI;

	private String imagesquare_EN;

	private String sharecomment;

	private String url;

	private boolean wishlist = false;

	private Date createdDate;

	private Date startDate;

	private Date endDate;

	private List<String> hobbys;

	private String tag;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<String> getHobbys() {
		return hobbys;
	}

	public void setHobbys(List<String> hobbys) {
		this.hobbys = hobbys;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isWishlist() {
		return wishlist;
	}

	public void setWishlist(boolean wishlist) {
		this.wishlist = wishlist;
	}

	public String getImagesquare() {
		return imagesquare;
	}

	public void setImagesquare(String imagesquare) {
		this.imagesquare = imagesquare;
	}

	public String getSharecomment() {
		return sharecomment;
	}

	public void setSharecomment(String sharecomment) {
		this.sharecomment = sharecomment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCmsId() {
		return cmsId;
	}

	public void setCmsId(String cmsId) {
		this.cmsId = cmsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public List<String> getOrtherCat() {
		return ortherCat;
	}

	public void setOrtherCat(List<String> ortherCat) {
		this.ortherCat = ortherCat;
	}

	public String getImagesquare_VI() {
		return imagesquare_VI;
	}

	public void setImagesquare_VI(String imagesquare_VI) {
		this.imagesquare_VI = imagesquare_VI;
	}

	public String getImagesquare_EN() {
		return imagesquare_EN;
	}

	public void setImagesquare_EN(String imagesquare_EN) {
		this.imagesquare_EN = imagesquare_EN;
	}

}
