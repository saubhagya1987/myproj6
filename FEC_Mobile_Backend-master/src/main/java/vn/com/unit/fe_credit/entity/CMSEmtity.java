package vn.com.unit.fe_credit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "CMS")
@SequenceGenerator(allocationSize = 1, name = "seq_cms", sequenceName = "SEQ_CMS_CMSID")
@JsonAutoDetect
public class CMSEmtity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cms")
	@Column(name = "CMSID")
	private Long cmsId;

	@Column(name = "TITLE", length = 500)
	@NotEmpty
	private String title;

	@Column(name = "GROUPCMS", length = 255)
	@NotEmpty
	private String groupcms;

	@Column(name = "TAG", length = 500)
	private String tag;

	@Column(name = "SHORT", length = 500)
	private String shortDescription;

	@Column(name = "CONTENT")
	@NotEmpty
	private String content;

	@Column(name = "IMAGE", length = 500)
	private String image;

	@Column(name = "IMAGELONG", length = 500)
	private String imageLong;

	@Column(name = "SHARECOMMENT", length = 500)
	private String shareComment;

	@Column(name = "START_DAY")
	private Date startDate;

	@Column(name = "END_DAY")
	private Date endDate;

	@Column(name = "CREATEDDATE")
	private Date createDate;

	@Column(name = "CMS_CATEGORYID")
	private String cmsCategory;

	@Column(name = "CMS_HOBBYYID")
	private String cmsHobby;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "CREATED_BY", updatable = false)
	private String created_by;

	@Column(name = "UPDATE_BY")
	private String update_by;

	@Column(name = "CREATED_DATE", updatable = false)
	private Date created_date;

	@Column(name = "UPDATED_DATE")
	private Date update_date;

	@Column(name = "IMAGE_EN", length = 500)
	private String image_en;

	@Column(name = "IMAGE_LONG_EN", length = 500)
	private String image_long_en;

	@Column(name = "MESSAGE_ID")
	private Long messageId;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	@Column(name = "URL", length = 500)
	private String url;

	@Column(name = "ORDERCMS")
	private Long ordercms;

	@Transient
	private String imagePath;

	@Transient
	private List<String> ortherCat;

	@Transient
	private String cmsCategoryList;

	@Transient
	private String imagesquare;

	@Transient
	private boolean wishlist = false;

	@Transient
	private Long customerId;

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getCmsCategoryList() {
		return cmsCategoryList;
	}

	public void setCmsCategoryList(String cmsCategoryList) {
		this.cmsCategoryList = cmsCategoryList;
	}

	public List<String> getOrtherCat() {
		return ortherCat;
	}

	public void setOrtherCat(List<String> ortherCat) {
		this.ortherCat = ortherCat;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Long getCmsId() {
		return cmsId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGroupcms() {
		return groupcms;
	}

	public void setGroupcms(String groupcms) {
		this.groupcms = groupcms;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
	}

	public String getCmsCategory() {
		return cmsCategory;
	}

	public void setCmsCategory(String cmsCategory) {
		this.cmsCategory = cmsCategory;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getImageLong() {
		return imageLong;
	}

	public void setImageLong(String imageLong) {
		this.imageLong = imageLong;
	}

	public String getShareComment() {
		return shareComment;
	}

	public void setShareComment(String shareComment) {
		this.shareComment = shareComment;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getCmsHobby() {
		return cmsHobby;
	}

	public void setCmsHobby(String cmsHobby) {
		this.cmsHobby = cmsHobby;
	}

	public Long getOrdercms() {
		return ordercms;
	}

	public void setOrdercms(Long ordercms) {
		this.ordercms = ordercms;
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

	public String getImage_en() {
		return image_en;
	}

	public void setImage_en(String image_en) {
		this.image_en = image_en;
	}

	public String getImage_long_en() {
		return image_long_en;
	}

	public void setImage_long_en(String image_long_en) {
		this.image_long_en = image_long_en;
	}

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

}
