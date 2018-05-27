package vn.com.unit.fe_credit.entity;


import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.codehaus.jackson.annotate.JsonIgnore;

import vn.com.unit.validator.Unique;

@Entity
@Table(name="department")
@Unique(hql = "FROM Department b WHERE b.departmentCode = :departmentCode AND b.departmentId != :departmentId  ",idField="departmentId", message = "{msg.code.duplicate}", errorAtField = "")
@SequenceGenerator(allocationSize=1,name="seq_department",sequenceName="SEQ_DEPARTMENT_DEPARTMENTID")
public class Department implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_department")
	@Column(name = "DEPARTMENTID")
	private Long departmentId;

	@JsonIgnore
	@OneToMany(mappedBy = "parent", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Department> departments;
	
	

	public Set<Department> getDepartments() {
		return departments;
	}

	public void setDepartments(Set<Department> departments) {
		this.departments = departments;
	}
	 @Column(name = "ROOTID")
	    private Long rootId;
	 
	
	public Long getRootId() {
		return rootId;
	}

	public void setRootId(Long rootId) {
		this.rootId = rootId;
	}
	
	@Column(name = "DEPARTMENTCODE")	
	@Size(min=1,max=100)
	private String departmentCode;

	@Column(name = "NAME", length = 255)
	@Size(min=1,max=255)
	private String name;
	
		
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "PARENTID", referencedColumnName = "DEPARTMENTID", insertable = true, updatable = true)
	private Department parent;

	

	@Column(name = "PARENTLINK", length = 255)
	@Size(min=0,max=255)
	private String parentLink;
	
	@Column(name = "NOTE", length = 3000)
	private String note;

	public String getParentLink() {
		return parentLink;
	}

	public void setParentLink(String parentLink) {
		this.parentLink = parentLink;
	}

	public Long getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Long id) {
		this.departmentId = id;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Department getParent() {
		return parent;
	}

	public void setParent(Department parent) {
		this.parent = parent;
	}



	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}


	@Column(name = "Phone", length = 50)
	@Size(min=0,max=50)
	private String phone;

	

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if(this.getDepartmentId() != null && obj != null){
			Department dep = (Department) obj;
			if(this.getDepartmentId().equals(dep.getDepartmentId())){
				result = true;
			}
		}
		return result;
	}
	
	@Override
	public int hashCode() {
		if (departmentId != null && departmentCode != null) 
			return departmentId.intValue() + departmentCode.hashCode();
		return 0;
	}
	
	
}
