package vn.com.unit.fe_credit.entity.collection;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.sql.rowset.serial.SerialException;

@Entity
@Table(name = "record_attachment")
public class RecordAttachment implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_attachment_id_sequence")
	@SequenceGenerator(name = "record_attachment_id_sequence", sequenceName = "RECORD_ATTACHMENT_ID_SEQ")
	@Column(name = "id")
	private Integer id;
	@Lob
	@Column(name = "attachment_data", length= 1000000000)
	//private Blob attachment_data;
	private byte[] attachment_data;
	@Column(name = "attachment_type")
	private String attachment_type;
	@Column(name = "record_id")
	private String recordId;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	/*public Blob getAttachment_data() {
		return attachment_data;
	}
	public void setAttachment_data(String attachment_data) throws SerialException, SQLException {
		
		Blob blob = new javax.sql.rowset.serial.SerialBlob(attachment_data.getBytes());
		this.attachment_data = blob;
	}*/
	public String getAttachment_type() {
		return attachment_type;
	}
	public void setAttachment_type(String attachment_type) {
		this.attachment_type = attachment_type;
	}
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public byte[] getAttachment_data() {
		return attachment_data;
	}
	public void setAttachment_data(byte[] attachment_data) {
		this.attachment_data = attachment_data;
	}

	
}
