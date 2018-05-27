package vn.com.unit.fe_credit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name="CustomerAlert")
@JsonAutoDetect
@SequenceGenerator(allocationSize=1,name="seq_customeralert",sequenceName="SEQ_CUSTOMERALERTID")
public class CustomerAlert {
	@Id 
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_customeralert")
	@Column(name = "CustomerAlertId")
	private Long customerAlertId;
	
	@Column(name = "CustomerProfileId")
	private Long customerId;

	public Long getCustomerAlertId() {
		return customerAlertId;
	}

	public void setCustomerAlertId(Long customerAlertId) {
		this.customerAlertId = customerAlertId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
}
