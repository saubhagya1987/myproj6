package vn.com.unit.fe_credit.entity.collection;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "check_in")
public class CheckIn {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "email", length = 1000)
	private String email;

	@Column(name = "lat_long", length = 1000)
	private String latLong;
	
	@Column(name = "address", length = 1000)
	private String address;
	
	@Column(name = "address_type", length = 100)
	private String addressType;
	
	@Column(name = "created_by", length = 100)
	private String createdBy;
	
	@Column(name = "creation_date", length = 100)
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@Column(name = "operation_id", length = 100)
	private String operationId;
	
	@Column(name = "operation_type", length = 100)
	private String operationType;

	@Column(name = "response_code", length = 50)
	private String responseCode;
	//*********************************************************************************************
	
	@Column(name = "checkin_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkinTime;
	@Column(name = "checkin_date")
	private String checkinDate;
	@Column(name = "month")
	private String month;
	@Column(name = "app_id")
	private String appId;
	@Column(name="fc_id")
	private String fcId;
	@Column(name = "checkin_address")
	private String checkinAddress;
	@Column(name = "difference")
	private String distance;
	@Column(name = "unit_code")
	private String unitCode;
	@Column(name = "unit_code_desc")
	private String unitCodeDesc;
	@Column(name = "fc_no_checkin_daily")
	private String fcNocheckinDaily;
	@Column(name = "no_app_id_check_in_daily")
	private String noAppIdcheckInDaily;
	@Column(name = "no_fc_checkin")
	private String noFCCheckin;
	@Column(name = "no_app_id_checkin")
	private String noAppIdCheckin;
	@Column(name = "region_team")
	private String regionTeam;
	@Column(name = "region")
	private String region;
	@Column(name = "lv_of_distance")
	private String lvOfDistance;
	@Column(name = "g5_t07_2016")
	private String g5_T07_2016;
	@Column(name = "no_fc_g5_checkin")
	private String noFCG5Checkin;
	@Column(name = "status")
	private String status;
	//*********************************************************************************************
	
	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	
	public String getLatLong() {
		return latLong;
	}

	public void setLatLong(String latLong) {
		this.latLong = latLong;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getOperationId() {
		return operationId;
	}

	public void setOperationId(String operationId) {
		this.operationId = operationId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the checkinDate
	 */
	public String getCheckinDate() {
		return checkinDate;
	}

	/**
	 * @param checkinDate the checkinDate to set
	 */
	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the checkinAddress
	 */
	public String getCheckinAddress() {
		return checkinAddress;
	}

	/**
	 * @param checkinAddress the checkinAddress to set
	 */
	public void setCheckinAddress(String checkinAddress) {
		this.checkinAddress = checkinAddress;
	}
	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the unitCodeDesc
	 */
	public String getUnitCodeDesc() {
		return unitCodeDesc;
	}

	/**
	 * @param unitCodeDesc the unitCodeDesc to set
	 */
	public void setUnitCodeDesc(String unitCodeDesc) {
		this.unitCodeDesc = unitCodeDesc;
	}

	/**
	 * @return the fcNocheckinDaily
	 */
	public String getFcNocheckinDaily() {
		return fcNocheckinDaily;
	}

	/**
	 * @param fcNocheckinDaily the fcNocheckinDaily to set
	 */
	public void setFcNocheckinDaily(String fcNocheckinDaily) {
		this.fcNocheckinDaily = fcNocheckinDaily;
	}

	/**
	 * @return the noAppIdcheckInDaily
	 */
	public String getNoAppIdcheckInDaily() {
		return noAppIdcheckInDaily;
	}

	/**
	 * @param noAppIdcheckInDaily the noAppIdcheckInDaily to set
	 */
	public void setNoAppIdcheckInDaily(String noAppIdcheckInDaily) {
		this.noAppIdcheckInDaily = noAppIdcheckInDaily;
	}

	/**
	 * @return the noFCCheckin
	 */
	public String getNoFCCheckin() {
		return noFCCheckin;
	}

	/**
	 * @param noFCCheckin the noFCCheckin to set
	 */
	public void setNoFCCheckin(String noFCCheckin) {
		this.noFCCheckin = noFCCheckin;
	}

	/**
	 * @return the noAppIdCheckin
	 */
	public String getNoAppIdCheckin() {
		return noAppIdCheckin;
	}

	/**
	 * @param noAppIdCheckin the noAppIdCheckin to set
	 */
	public void setNoAppIdCheckin(String noAppIdCheckin) {
		this.noAppIdCheckin = noAppIdCheckin;
	}

	/**
	 * @return the regionTeam
	 */
	public String getRegionTeam() {
		return regionTeam;
	}

	/**
	 * @param regionTeam the regionTeam to set
	 */
	public void setRegionTeam(String regionTeam) {
		this.regionTeam = regionTeam;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the lvOfDistance
	 */
	public String getLvOfDistance() {
		return lvOfDistance;
	}

	/**
	 * @param lvOfDistance the lvOfDistance to set
	 */
	public void setLvOfDistance(String lvOfDistance) {
		this.lvOfDistance = lvOfDistance;
	}

	/**
	 * @return the g5_T07_2016
	 */
	public String getG5_T07_2016() {
		return g5_T07_2016;
	}

	/**
	 * @param g5_T07_2016 the g5_T07_2016 to set
	 */
	public void setG5_T07_2016(String g5_T07_2016) {
		this.g5_T07_2016 = g5_T07_2016;
	}

	/**
	 * @return the noFCG5Checkin
	 */
	public String getNoFCG5Checkin() {
		return noFCG5Checkin;
	}

	/**
	 * @param noFCG5Checkin the noFCG5Checkin to set
	 */
	public void setNoFCG5Checkin(String noFCG5Checkin) {
		this.noFCG5Checkin = noFCG5Checkin;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the checkinTime
	 */
	public Date getCheckinTime() {
		return checkinTime;
	}

	/**
	 * @param checkinTime the checkinTime to set
	 */
	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}

	/**
	 * @return the fcId
	 */
	public String getFcId() {
		return fcId;
	}

	/**
	 * @param fcId the fcId to set
	 */
	public void setFcId(String fcId) {
		this.fcId = fcId;
	}

	/**
	 * @return the distance
	 */
	public String getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}
}
