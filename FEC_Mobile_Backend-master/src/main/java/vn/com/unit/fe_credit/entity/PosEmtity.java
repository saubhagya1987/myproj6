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
@Table(name = "POS")
@SequenceGenerator(allocationSize = 1, name = "seq_pos", sequenceName = "SEQ_POS_POSID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
public class PosEmtity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_pos")
	@Column(name = "POSID")
	private Long posid;

	@Column(name = "POS_NAME", length = 200)
	private String pos_name;

	@Column(name = "ADDRESS_NUMBER", length = 255)
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
	
	@Column(name = "BRANCH_NAMEPOS", length = 255)
	private String branch_namepos;
	
	
	@Column(name = "BUYORPAY", length = 255)
	private String buyOrPay;
	
	@Column(name = "CODE_BRANCH_POS", length = 255)
	private String code_branch_pos;

	@Transient
	private String addressNo;

	@Transient
	private String distance;
	
	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	

	@Column(name = "CREATED_DATE",updatable=false)
	private Date created_date;
	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "POS_PROVINCEID", referencedColumnName = "BRANCHID")
	private BranchEmtity branchEmtity;

	@Transient
	private Long pos_provinceID;
	
	@Column(name = "IDDISTRICT")
	private Long iddistrict;
	
	@Transient
	private String page;	
	
	@Transient
	private Integer limit;		
	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Long getPos_provinceID() {
		return pos_provinceID;
	}

	public void setPos_provinceID(Long pos_provinceID) {
		this.pos_provinceID = pos_provinceID;
	}

	public Long getPosid() {
		return posid;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setPosid(Long posid) {
		this.posid = posid;
	}

	public String getPos_name() {
		return pos_name;
	}

	public void setPos_name(String pos_name) {
		this.pos_name = pos_name;
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

	public BranchEmtity getBranchEmtity() {
		return branchEmtity;
	}

	public void setBranchEmtity(BranchEmtity branchEmtity) {
		this.branchEmtity = branchEmtity;
	}

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}

	public String getBranch_namepos() {
		return branch_namepos;
	}

	public void setBranch_namepos(String branch_namepos) {
		this.branch_namepos = branch_namepos;
	}

	public String getBuyOrPay() {
		return buyOrPay;
	}

	public void setBuyOrPay(String buyOrPay) {
		this.buyOrPay = buyOrPay;
	}

	public Long getIddistrict() {
		return iddistrict;
	}

	public void setIddistrict(Long iddistrict) {
		this.iddistrict = iddistrict;
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

	public String getCode_branch_pos() {
		return code_branch_pos;
	}

	public void setCode_branch_pos(String code_branch_pos) {
		this.code_branch_pos = code_branch_pos;
	}
	
}
