package vn.com.unit.fe_credit.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="SystemSetting")
@SequenceGenerator(allocationSize=1,name="seq_systemsetting",sequenceName="SEQ_SYSTEMSETTINGID")
public class SystemSetting {
	@Column(name="SYSTEMSETTINGID")
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_systemsetting")
	private Long id;
	
	@Column(name="SYSTEMSETTINGKEY") //For SQL Server 2008
	@NotEmpty
	private String key;
	@NotEmpty
	@Column(name="SYSTEMSETTINGVALUE")
	private String value;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
