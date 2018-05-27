/*package vn.com.unit.fe_credit.entity;

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

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name = "USERS")
@SequenceGenerator(allocationSize = 1, name = "seq_user", sequenceName = "seq_user")
public class User implements Serializable{
	
	private static final long serialVersionUID = 2531852082536909229L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_user")
	@Column(name = "USERID")
	private Long id;
	
	@Column(name = "EMAIL")
	private String email;
	@Column(name = "FIRST_NAME")
	private String firstName;
	@Column(name = "LAST_NAME")
	private String lastName;
	@Column(name = "DESIGNATION")
	private String designation;
	@Column(name = "DEPARTMENT")
	private String department;
	@Column(name = "REPORTING_TO")
	private String reportingTo;	
	@Column(name = "CREATED_DATE",updatable=false)
	private Date created_date;	
	@Column(name = "UPDATED_DATE")
	private Date update_date;
	@Column(name = "CREATED_BY",updatable=false)
	private String created_by;	
	@Column(name = "UPDATE_BY")
	private String update_by;


	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "userteam", joinColumns = { @JoinColumn(name = "USERID", nullable = true) }, inverseJoinColumns = { @JoinColumn(name = "TEAMID", nullable = true) })
	private Set<Team> teams = new HashSet<Team>(0);

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getDesignation() {
		return designation;
	}


	public void setDesignation(String designation) {
		this.designation = designation;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getReportingTo() {
		return reportingTo;
	}


	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
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


	public Set<Team> getTeams() {
		return teams;
	}


	public void setTeams(Set<Team> teams) {
		this.teams = teams;
	}
	
}
*/