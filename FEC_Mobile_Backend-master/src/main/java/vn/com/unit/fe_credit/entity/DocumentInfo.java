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
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "DOCUMENTINFO")
@SequenceGenerator(allocationSize=1,name="seq_documentInfo",sequenceName="SEQ_DOCUMENTINFO_DOCUMENTID")
// @SequenceGenerator(name = "seq_currency", sequenceName = "SEQ_CURRENCY_ID",
// allocationSize = 1)
@JsonAutoDetect
public class DocumentInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="seq_documentInfo")
	@Column(name = "DOCUMENTID")
	private Long documentId;

	@Column(name = "TITLE", length = 255)
	private String title;
	
	@Column(name = "BCOMMENT", length = 1000)
	private String bComment;
	
	@Column(name = "KEYWORDS", length = 1000)
	private String keyWords;
	
	
	@Column(name = "UPLOADDATE")
	@Temporal(TemporalType.DATE)
	private Date uploadDate;
	
	
	@Column(name = "UPLOADBY")
	@Temporal(TemporalType.DATE)
	private Date uploadBy;
	
	
	@Column(name = "FILENAME", length = 255)
	private String fileName;
	
	@Column(name = "FILESIZE")
	private Long fileSize;
	
	
	@Column(name = "FILETYPE", length = 100)
	private String fileType;
	
	
	@Column(name = "MODIFIEDDATE")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;
	
	
	@Column(name = "DOCUMENTSOURCE")
	private Long documentSource;
	
	
	@Column(name = "REFERENCEID")
	private Long referenceId;
	
	@Column(name = "REFERENCENO", length = 100)
	private String referenceNo;
	
	@Column(name = "REPOSITORYID")
	private Long repositoryId;
	
	
	@Column(name = "PHYSICALFILENAME", length = 255)
	private String physicalFileName;
	
	
	@Column(name = "PHYSICALFOLDER", length =255)
	private String physicalFolder;
	
	
	@Column(name = "DOCUMENTFROM")
	private Long documentFrom;


	


	public Long getDocumentId() {
		return documentId;
	}


	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getbComment() {
		return bComment;
	}


	public void setbComment(String bComment) {
		this.bComment = bComment;
	}


	public String getKeyWords() {
		return keyWords;
	}


	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}


	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	public Date getUploadBy() {
		return uploadBy;
	}


	public void setUploadBy(Date uploadBy) {
		this.uploadBy = uploadBy;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public Long getFileSize() {
		return fileSize;
	}


	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public Long getDocumentSource() {
		return documentSource;
	}


	public void setDocumentSource(Long documentSource) {
		this.documentSource = documentSource;
	}


	public Long getReferenceId() {
		return referenceId;
	}


	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}


	public String getReferenceNo() {
		return referenceNo;
	}


	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}


	public Long getRepositoryId() {
		return repositoryId;
	}


	public void setRepositoryId(Long repositoryId) {
		this.repositoryId = repositoryId;
	}


	public String getPhysicalFileName() {
		return physicalFileName;
	}


	public void setPhysicalFileName(String physicalFileName) {
		this.physicalFileName = physicalFileName;
	}


	public String getPhysicalFolder() {
		return physicalFolder;
	}


	public void setPhysicalFolder(String physicalFolder) {
		this.physicalFolder = physicalFolder;
	}


	public Long getDocumentFrom() {
		return documentFrom;
	}


	public void setDocumentFrom(Long documentFrom) {
		this.documentFrom = documentFrom;
	}
	

	
	
	


}
