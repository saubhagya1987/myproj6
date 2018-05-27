package vn.com.unit.fe_credit.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.validator.constraints.NotEmpty;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "Banner")
@JsonAutoDetect
@Unique(hql = "FROM Banner b WHERE b.bannerCode = :bannerCode AND b.bannerId != :bannerId ", idField = "bannerId", message = "{banner.field.bannerCode.duplicate}", errorAtField = "bannerCode")
@SequenceGenerator(allocationSize = 1, name = "seq_banner", sequenceName = "SEQ_BANNER_BANNERID")
public class Banner {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_banner")
	@Column(name = "BannerId")
	private Long bannerId;
	
	@Column(name = "Category", length = 100)
	@NotEmpty
	private String category;

	@Column(name = "BannerCode", length = 100)
	@Size(min = 1, max = 100)
	private String bannerCode;

	@Column(name = "BannerType", length = 50)
	private String bannerType;

	@Column(name = "Status")
	private Integer status;

	@Column(name = "ActiveFromDate")
	@NotNull
	private Date activeFromDate;

	@Column(name = "ActiveToDate")
	private Date activeToDate;

	@Column(name = "Link", length = 200)
	private String link;

	@Column(name = "SlidePeriod")
	private Double slidePeriod;

	@Column(name = "Title", length = 255)
	private String title;
	
	@Column(name = "ORDERBANNER")
	private Long orderbanner;

	@Transient
	private String imagePath;

	@Transient
	private String imageFileName;

	@Transient
	private String imageLink;

	@Transient
	private List<String> catList;
	
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	

	@Column(name = "CREATED_DATE",updatable=false)
	private Date created_date;
	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	
	
	public List<String> getCatList() {
		return catList;
	}

	public void setCatList(List<String> catList) {
		this.catList = catList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Double getSlidePeriod() {
		return slidePeriod;
	}

	public void setSlidePeriod(Double slidePeriod) {
		this.slidePeriod = slidePeriod;
	}

	public Long getBannerId() {
		return bannerId;
	}

	public void setBannerId(Long bannerId) {
		this.bannerId = bannerId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getBannerCode() {
		return bannerCode;
	}

	public void setBannerCode(String bannerCode) {
		this.bannerCode = bannerCode;
	}

	public String getBannerType() {
		return bannerType;
	}

	public void setBannerType(String bannerType) {
		this.bannerType = bannerType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getActiveFromDate() {
		return activeFromDate;
	}

	public void setActiveFromDate(Date activeFromDate) {
		this.activeFromDate = activeFromDate;
	}

	public Date getActiveToDate() {
		return activeToDate;
	}

	public void setActiveToDate(Date activeToDate) {
		this.activeToDate = activeToDate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Long getOrderbanner() {
		return orderbanner;
	}

	public void setOrderbanner(Long orderbanner) {
		this.orderbanner = orderbanner;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Date getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	public Date getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(Date update_date) {
		this.update_date = update_date;
	}
	
	
}
