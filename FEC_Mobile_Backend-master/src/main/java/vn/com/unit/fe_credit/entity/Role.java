package vn.com.unit.fe_credit.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

import vn.com.unit.validator.Unique;

@Entity
@Table(name = "Role")
@JsonAutoDetect
//@Unique(hql = "FROM Role ac WHERE ac.code = :code  ", idField = "code", message = "{account.field.code.duplicate}", errorAtField = "code")
@SequenceGenerator(allocationSize=1,name="seq_role",sequenceName="SEQ_ROLE_ROLEID")
public class Role implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 3201495431556940705L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_role")
	@Column(name = "RoleId")
	private Long id;

	/*@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "TEAM_ROLE", joinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "TEAM_ID", nullable = false) })
	private Set<Team> teeprocurement;*/
	
	@ManyToMany(mappedBy = "roles")
	@JsonIgnore
    private Set<Team> teeprocurement;

	@Column(name = "Conditional")
	private Integer conditional;

	@Column(name = "Code", length = 255)
	@NotNull
	private String code;

	@Column(name = "Name", length = 255)
	private String name;

	@Column(name = "Type", length = 255)
	private String type;
	
	@Column(name = "Description", length = 255)
	private String description;
	
	//@Column(name = "AllowMobileAccess")
	//private Boolean allowMobileAccess;
	
	//@Column(name = "AllowPortalAccess")
	//private Boolean allowPortalAccess;
	
	@Column(name = "CreationDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "rolemodule", joinColumns = { @JoinColumn(name = "RoleId", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "ModuleId", nullable = false) })
	private Set<Module> modules = new HashSet<Module>(0);
	
	
	@Column(name = "Createdby")
	private String createdBy;

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<Team> getTeeprocurement() {
		return teeprocurement;
	}

	public void setTeeprocurement(Set<Team> teeprocurement) {
		this.teeprocurement = teeprocurement;
	}

	public Integer getConditional() {
		return conditional;
	}

	public void setConditional(Integer conditional) {
		this.conditional = conditional;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/*public Boolean getAllowMobileAccess() {
		return allowMobileAccess;
	}
	public void setAllowMobileAccess(Boolean allowMobileAccess) {
		this.allowMobileAccess = allowMobileAccess;
	}
	public Boolean getAllowPortalAccess() {
		return allowPortalAccess;
	}
	public void setAllowPortalAccess(Boolean allowPortalAccess) {
		this.allowPortalAccess = allowPortalAccess;
	}*/
	public Set<Module> getModules() {
		return modules;
	}
	public void setModules(Set<Module> modules) {
		this.modules = modules;
	}




}
