package vn.com.unit.fe_credit.entity.collection;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialException;

@Entity
//@Table(name = "TERMINATION_ATTACHMENT")
@Table(name = "CONT_TERM_ATTACHMENT")
public class ContractAttachment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CONTRACT_ATTACHMENT_SEQ")
	@SequenceGenerator(name = "CONTRACT_ATTACHMENT_SEQ", sequenceName = "CONTRACT_ATTACHMENT_SEQ")*/
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cont_term_attach_id_sequence")
	@SequenceGenerator(name = "cont_term_attach_id_sequence", sequenceName = "CONT_TERM_ATTACH_ID_SEQ")
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
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAttachmentName() {
		return attachmentName;
	}
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	public Blob getAttachmentData() {
		return attachmentData;
	}
	public void setAttachmentData(byte[] attachment_data) throws SerialException, SQLException {
		Blob blob = new javax.sql.rowset.serial.SerialBlob(attachment_data);
		this.attachmentData = blob;
	}
	public String getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(String attachment_type) {
		this.attachmentType = attachment_type;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

}
