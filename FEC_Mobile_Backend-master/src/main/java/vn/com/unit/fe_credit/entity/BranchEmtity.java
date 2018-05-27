package vn.com.unit.fe_credit.entity;

import java.util.Date;

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
@Table(name = "BRANCH")
@SequenceGenerator(allocationSize = 1, name = "seq_branch", sequenceName = "SEQ_BRANCH_BRANCHID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
public class BranchEmtity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_branch")
	@Column(name = "BRANCHID")
	private Long branchid;

	@Column(name = "PARTNER_NAME", length = 200)
	private String partner_name;

	@Column(name = "BRANCH_NAME", length = 200)
	private String branch_name;

	@Column(name = "ADDRESS_NUMBER", length = 50)
	private String address_number;

	@Column(name = "ADDRESS_STREET", length = 200)
	private String address_street;

	@Column(name = "AWARD", length = 100)
	private String award;

	@Column(name = "DISTRICT", length = 100)
	private String district;

	@Column(name = "PROVINCE", length = 100)
	private String province;

	@Column(name = "REMARK", length = 500)
	private String remark;

	@Column(name = "LONGITUDE", length = 500)
	private String longitude;

	@Column(name = "LATITUDE", length = 500)
	private String latitude;

	@Transient
	private String addressNo;

	@Transient
	private String page;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	

	@Column(name = "CREATED_DATE",updatable=false)
	private Date created_date;
	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Long getBranchid() {
		return branchid;
	}

	public void setBranchid(Long branchid) {
		this.branchid = branchid;
	}

	public String getBranch_name() {
		return branch_name;
	}

	public void setBranch_name(String branch_name) {
		this.branch_name = branch_name;
	}

	public String getAddress_number() {
		return address_number;
	}

	public void setAddress_number(String address_number) {
		this.address_number = address_number;
	}

	public String getAddress_street() {
		return address_street;
	}

	public void setAddress_street(String address_street) {
		this.address_street = address_street;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
	}

	public String getPartner_name() {
		return partner_name;
	}

	public void setPartner_name(String partner_name) {
		this.partner_name = partner_name;
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

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
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
