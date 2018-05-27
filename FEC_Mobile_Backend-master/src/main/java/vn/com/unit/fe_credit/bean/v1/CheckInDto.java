package vn.com.unit.fe_credit.bean.v1;

import java.util.Date;

public class CheckInDto {
	public String fcId;
	public String latLong;
	public String address;
	public String addressType;
	public String createdBy;
	public Date creationDate;
	/**
	 * @return the fcId
	 */
	public String getFcId() {
		return fcId;
	}
	/**
	 * @return the latLong
	 */
	public String getLatLong() {
		return latLong;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @return the addressType
	 */
	public String getAddressType() {
		return addressType;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param fcId the fcId to set
	 */
	public void setFcId(String fcId) {
		this.fcId = fcId;
	}
	/**
	 * @param latLong the latLong to set
	 */
	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @param addressType the addressType to set
	 */
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
