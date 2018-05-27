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

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "CMS_CATEGORY")
@SequenceGenerator(allocationSize = 1, name = "seq_cms_category", sequenceName = "SEQ_CATEGORY_CATEGORYID")
@JsonAutoDetect
@Unique(hql = "FROM CMSCategory cms WHERE cms.code = :code AND cms.cms_categoryId != :cms_categoryId ", idField = "cms_categoryId", message = "{hobby.Code.Fail}", errorAtField = "code")
public class CMSCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cms_category")
	@Column(name = "CMS_CATEGORYID")
	private Long cms_categoryId;

	@Column(name = "CODE", length = 50)
	@NotEmpty
	private String code;

	@Column(name = "NAME", length = 255)
	@NotEmpty
	private String name;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	@Transient
	private String category;

	@Transient
	private Long customerId;

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

	@Transient
	private Integer page;

	@Transient
	private Integer limit;

	@Transient
	private String searchName;

	@Transient
	private String preornext;

	@Transient
	private Long cmsId;

	public Long getCmsId() {
		return cmsId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public String getPreornext() {
		return preornext;
	}

	public void setPreornext(String preornext) {
		this.preornext = preornext;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getCms_categoryId() {
		return cms_categoryId;
	}

	public void setCms_categoryId(Long cms_categoryId) {
		this.cms_categoryId = cms_categoryId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
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
