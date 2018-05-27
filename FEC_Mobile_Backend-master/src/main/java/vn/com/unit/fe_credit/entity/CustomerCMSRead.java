package vn.com.unit.fe_credit.entity;

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

@Entity
@Table(name = "CUSTOMERCMSREAD")
@SequenceGenerator(allocationSize = 1, name = "seq_customercms", sequenceName = "SEQ_CUSTOMERCMS")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
//@Unique(hql = "FROM MasterdataDetal ac WHERE ac.name =:name AND ac.masterdata.masterdataId = :masterdataId AND ac.masterdatadetailId != :masterdatadetailId ", idField = "masterdatadetailId", message = "{hobby.Code.Fail}", errorAtField = "name")

public class CustomerCMSRead {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_customercms")
	@Column(name = "CUSTOMERCMSREADID")
	private Long customerCMSReadId;
	
	@ManyToOne
	@JoinColumn(name = "CMSID", referencedColumnName = "CMSID")
	private CMSEmtity cmsEmtity;
	
	@ManyToOne
	@JoinColumn(name = "customerId", referencedColumnName = "CUSTOMERID")
	private Customer customer;
	
	@Transient
	private Long cmsId;
	
	@Transient
	private Long customerId;
	
	public Long getCustomerCMSReadId() {
		return customerCMSReadId;
	}

	public void setCustomerCMSReadId(Long customerCMSReadId) {
		this.customerCMSReadId = customerCMSReadId;
	}

	public CMSEmtity getCmsEmtity() {
		return cmsEmtity;
	}

	public void setCmsEmtity(CMSEmtity cmsEmtity) {
		this.cmsEmtity = cmsEmtity;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Long getCmsId() {
		return cmsId;
	}

	public void setCmsId(Long cmsId) {
		this.cmsId = cmsId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
	
}
