package vn.com.unit.fe_credit.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import lombok.Data;

@Entity
@Table(name = "MGM_EXPORT_FILE_VTIGER")
@JsonAutoDetect
@SequenceGenerator(allocationSize = 1, name = "seq_export_vtiger", sequenceName = "SEQ_EXPORT_VTIGER")
@Data
public class MgmExportFileDataToVTiger {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_export_vtiger")
	@Column(name = "VTIGER_ID", length = 16)
	private Long vtigerId;

	@Column(name = "CUSTOMER_ID")
	private String customerId;

	@Column(name = "FILE_NAME", length = 50)
	private String fileName;

	@Column(name = "STATUS_PROCESS")
	private Integer statusProcess;

	@Column(name = "VALUE_TYPE", length = 10)
	private String valueType;

	@Column(name = "SIZE_FILE", length = 10)
	private String sizeFile;

	@Column(name = "EXPORT_FILE_DATE")
//	@Temporal(TemporalType.TIMESTAMP)
	private Date exportFileDate;

	@Column(name = "PATH_FILE", length = 50)
	private String pathFile;

	@Column(name = "CREATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column(name = "CREATED_BY", length = 50)
	private String createdBy;

	@Column(name = "UPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;

	@Column(name = "UPDATED_BY", length = 50)
	private String updateBy;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "REMARK", length = 500)
	private String remark;

	@Column(name = "NO_IN_DATE", length = 3)
	private Integer noInDate;
	
	@Column(name = "IS_EXPORTING ")
	private Boolean is_exporting = Boolean.TRUE;
	
	@Column(name = "REPORT_EXCEPTION")
	private String reportException;

}
