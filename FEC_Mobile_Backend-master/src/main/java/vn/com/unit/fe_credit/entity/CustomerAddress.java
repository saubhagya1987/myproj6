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
@Table(name="CustomerAddress")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_customeraddress",sequenceName="SEQ_CUSTOMERADDRESSID")
public class CustomerAddress {

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_customeraddress")
	@Column(name = "CustomerAddressId")
	private Long customerAddressId;
	
	@Column(name = "AddressNo", length = 50)
	private String addressNo;
	
	@Column(name = "Street", length = 200)
	private String street;
	
	@Column(name = "Award", length = 100)
	private String award;
	
	@Column(name = "District", length = 100)
	private String district;
	
	@Column(name = "Province", length = 100)
	private String province;
	
	@Column(name = "MapLocation", length = 500)
	private String mapLocation;
	
	@Column(name = "MapLocationOld", length = 500)
	private String mapLocationOld;
	
	@Column(name = "CustomerId")
	private Long customerId;

	public Long getCustomerAddressId() {
		return customerAddressId;
	}

	public void setCustomerAddressId(Long customerAddressId) {
		this.customerAddressId = customerAddressId;
	}

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getAward() {
		return award;
	}

	public void setAward(String award) {
		this.award = award;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getMapLocation() {
		return mapLocation;
	}

	public void setMapLocation(String mapLocation) {
		this.mapLocation = mapLocation;
	}

	public String getMapLocationOld() {
		return mapLocationOld;
	}

	public void setMapLocationOld(String mapLocationOld) {
		this.mapLocationOld = mapLocationOld;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
