package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import lombok.Data;

@Entity
@Table(name = "MGM_CUSTOMER")
@Data
@JsonAutoDetect
public class MgmCustomer {

	@Id
	@GeneratedValue
	@Column(name = "ACCOUNT_ID", length = 50)
	private String accountId;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMERID")
	private Customer customer;
	
	@Column(name = "SUBMITED_CONTACT_COUNT")
	private Integer submitedContactCount;
	
	@Column(name = "DUBLICATED_CONTACT_COUNT")
	private Integer dublicatedContactCount;
	
	@Column(name = "EXISTCUST_CONTACT_COUNT")
	private Integer existcustContactCount;
	
	@Column(name = "ACTIVE_CONTACT_COUNT")
	private Integer activeContactCount;
	
	@Column(name = "LOCKED_CONTACT_COUNT")
	private Integer lockedContactCount;
	
	@Column(name = "VALIDATED_CONTACT_COUNT")
	private Integer validatedContactCount;
	
	@Column(name = "LOAN_CONTACT_COUNT")
	private Integer loanContactCount;
	
	@Column(name = "MGM_POINT")
	private Integer mgmPoint;
	
	@Column(name = "BLOCKED")
	private Boolean blocked;
	
	@Column(name = "MGM_TOTAL_POINT")
	private Integer mgmTotalPoint;
	
	@Column(name = "ACCOUNT_NAME", length = 50)
	private String accountName;
	
	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date update_date;

	@Column(name = "CREATED_BY", length = 50, updatable=false)
	private String created_by;
	
	@Column(name = "UPDATE_BY")
	private String update_by;
	
	@Column(name = "REMARK")
	private String remark;
	
	@Column(name = "POINT_EXPIRY_DATE")
	@Temporal(TemporalType.DATE)
	private Date pointExpiryDate;

}
