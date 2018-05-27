package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import lombok.Data;

@Entity
@Table(name = "MGM_EXCHANGE_RATE")
@Data
@JsonAutoDetect
public class MgmExchangeRate {

	@Id
	@GeneratedValue
	@Column(name = "RATE_ID", length = 50)
	private String rateId;
	
	@Column(name = "MGM_POINT")
	private Integer mgmPoint;
	
	@Column(name = "XCHANGE_VALUE")
	private Long xchangeValue;
	
	@Column(name = "VALUE_TYPE", length = 10)
	private String valueType;
	
	@Column(name = "START_DATE")
	private String startDate;
	
	@Column(name = "END_DATE")
	private String endDate;

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
	
}
