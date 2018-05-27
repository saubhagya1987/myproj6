package vn.com.unit.fe_credit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "SERACHMAP")
@SequenceGenerator(allocationSize = 1, name = "seq_searchmap", sequenceName = "SEQ_SERACHMAP_SERACHMAPID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
public class SearchMap {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_searchmap")
	@Column(name = "SERACHMAPID")
	private Long searchmapId;

	@Column(name = "CITY", length = 50)
	@NotEmpty
	private String city;
	
	@Column(name = "DISTRICT", length = 50)
	@NotEmpty
	private String district;
	
	@Column(name = "LONGITUDE")
	private String longitude;
	
	@Column(name = "LATITUDE")
	private String latitude;

	public Long getSearchmapId() {
		return searchmapId;
	}

	public void setSearchmapId(Long searchmapId) {
		this.searchmapId = searchmapId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	

	

}
