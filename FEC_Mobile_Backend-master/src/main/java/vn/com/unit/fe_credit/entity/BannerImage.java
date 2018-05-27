package vn.com.unit.fe_credit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "Banner")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_bannerimage", sequenceName = "SEQ_BANNERIMAGESID")
public class BannerImage {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bannerimage")
	@Column(name = "BannerImageId")
	private Long bannerImageId;
	
	@Column(name = "ImageFileName", length = 100)
	private String imageFileName;
	
	@Column(name = "ImagePath", length = 200)
	private String imagePath;
	
	@Column(name = "Status")
	private Integer status;
	
	@Column(name = "BannerId")
	private Integer bannerId;

	public Long getBannerImageId() {
		return bannerImageId;
	}

	public void setBannerImageId(Long bannerImageId) {
		this.bannerImageId = bannerImageId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getBannerId() {
		return bannerId;
	}

	public void setBannerId(Integer bannerId) {
		this.bannerId = bannerId;
	}
	
}
