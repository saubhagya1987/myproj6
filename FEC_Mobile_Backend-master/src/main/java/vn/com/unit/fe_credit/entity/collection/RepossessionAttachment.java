package vn.com.unit.fe_credit.entity.collection;

import java.sql.Blob;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "REPOSSESSION_ATTACHMENT")
public class RepossessionAttachment {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REPOSSESSION_ATTACHMENT_SEQ")
	@SequenceGenerator(name = "REPOSSESSION_ATTACHMENT_SEQ", sequenceName = "REPOSSESSION_ATTACHMENT_SEQ")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "ATTACHMENT_NAME")
	private String attachmentName;
	
	@Column(name = "ATTACHMENT_DATA", length= 1000000000)
	private Blob attachmentData;
	
	@Column(name = "ATTACHMENT_TYPE")
	private String attachmentType;
	
	@Column(name = "CONTRACT_ID")
	private String contractId;
	
	@Column(name = "REPOSSESSION_ID")
	private String repossessionId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Blob getAttachmentData() {
		return attachmentData;
	}

	public void setAttachmentData(Blob attachmentData) {
		this.attachmentData = attachmentData;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getRepossessionId() {
		return repossessionId;
	}

	public void setRepossessionId(String repossessionId) {
		this.repossessionId = repossessionId;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

}
