package vn.com.unit.fe_credit.entity;

import java.math.BigDecimal;
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
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import lombok.Data;

@Entity
@Table(name = "MGM_REDEEM_TRANS")
@Data
@JsonAutoDetect
public class MgmRedeemTrans {

	@Id
	@GeneratedValue
	@Column(name = "TRANS_ID", length = 50)
	private String transId;

	@Column(name = "ACCOUNT_ID", length = 20)
	private String accountId;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "CUSTOMERID")
	private Customer customer;

	@Column(name = "REDEEM_POINT")
	private Long redeemPoint;

	@Column(name = "EXCHANGE_RATE")
	private Long exchangeRate;

	@Column(name = "TRANX_TYPE", length = 10)
	private String tranxType;

	@Column(name = "VALUE_DESCRIPTION", length = 10)
	private String valueDescription;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "SUBMISSION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date submissionDate;

	@Column(name = "COMPLETION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date completionDate;

	@Column(name = "PARTNER", length = 20)
	private String partner;

	@Column(name = "PARTNER_TRANS_REF_ID", length = 50)
	private String partnerTransRefId;

	@Column(name = "EXCHANGE_VALUE")
	private BigDecimal exchangeValue;

	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created_date;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updated_date;

	@Column(name = "CREATED_BY", length = 50, updatable = false)
	private String created_by;

	@Column(name = "UPDATED_BY")
	private String updated_by;

	@Column(name = "REMARK")
	private String remark;

	@Column(name = "REMAINING_POINT")
	private Long remainingPoint;

	@Column(name = "DATE_NEXT_PAYMENT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateNextPayment;

	@Transient
	private String firstName;

	@Transient
	private String lastName;

	@Transient
	private String middleName;

	@Transient
	private String iDCardNumber;

	@Transient
	private String customerId;

	@Transient
	private String refNum;

}
