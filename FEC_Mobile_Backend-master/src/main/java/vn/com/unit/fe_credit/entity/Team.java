package vn.com.unit.fe_credit.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "Team")

@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_team", sequenceName = "SEQ_TEAM_TEAMID")
@Unique(hql = "FROM Team e WHERE e.code = :code AND e.id != :id", idField = "id", message = "{Unique.team.code}", errorAtField = "")
public class Team implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8990045005704234287L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_team")
	@Column(name = "TeamId")
	private Long id;

	@ManyToMany(mappedBy = "teams", cascade = { CascadeType.ALL })
	@JsonIgnore
	private Set<Account> accounts;
	
	/*@ManyToMany(mappedBy = "teams", cascade = { CascadeType.ALL })
	@JsonIgnore
	private Set<User> users;*/

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "TeamRole", joinColumns = { @JoinColumn(name = "TeamId", nullable = false) }, inverseJoinColumns = {
			@JoinColumn(name = "RoleId", nullable = false) })
	private Set<Role> roles;

	@Column(name = "Code", length = 255)
	@Size(min = 1, max = 100)
	private String code;

	@Column(name = "Name", length = 255)
	@Size(min = 1, max = 255)
	private String name;

	@Column(name = "Description")
	private String description;

	@Column(name = "Enabled")
	private Boolean enabled;

	@Column(name = "Type", length = 50)
	private String type;

	public Long getId() {
		return this.id;
	}

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

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "STATUSID", referencedColumnName = "STATUSID")
	private StatusTable statusTable;

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
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

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public StatusTable getStatusTable() {
		return statusTable;
	}

	public void setStatusTable(StatusTable statusTable) {
		this.statusTable = statusTable;
	}

	@Override
	public String toString() {
		return "id=" + id;
	}

}
