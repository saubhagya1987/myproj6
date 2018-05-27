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
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "Module")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_module",sequenceName="SEQ_MODULE_MODULEId")
public class Module implements Serializable{

	private static final long serialVersionUID = 3201495431556940705L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_module")
	@Column(name = "ModuleId")
	private Long id;
	
	@Column(name = "Name", length = 255)
	private String name;

	@Column(name = "Description", length = 255)
	private String description;
	
	@Column(name = "CreationDate")
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	
	@ManyToMany(mappedBy = "modules", cascade = { CascadeType.ALL })
	@JsonIgnore
	private Set<Role> roles;
	
	@Column(name = "Createdby")
	private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


}
