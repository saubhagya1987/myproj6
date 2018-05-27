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
@Table(name = "BannerImages")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_bannerimage", sequenceName = "SEQ_BANNERIMAGESID")
public class BannerImages {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_bannerimage")
	@Column(name = "BannerImagesId")
	private Long bannerImagesId;
	
	@Column(name = "ImageFileName", length = 100)
	private String imageFileName;
	
	@Column(name = "ImagePath", length = 200)
	private String imagePath;
	
	@Column(name = "Status")
	private Integer status;
	
	@Column(name = "BannerId")
	private Long bannerId;

	public Long getBannerImagesId() {
		return bannerImagesId;
	}

	public void setBannerImagesId(Long bannerImagesId) {
		this.bannerImagesId = bannerImagesId;
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

	public Long getBannerId() {
		return bannerId;
	}

	public void setBannerId(Long bannerId) {
		this.bannerId = bannerId;
	}
	
}
