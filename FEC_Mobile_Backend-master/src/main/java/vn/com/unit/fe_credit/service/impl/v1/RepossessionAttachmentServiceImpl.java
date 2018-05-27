package vn.com.unit.fe_credit.service.impl.v1;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.v1.ContractAttachmentDto;
import vn.com.unit.fe_credit.bean.v1.RepossessionAttachmentDto;
import vn.com.unit.fe_credit.dao.v1.RepossessionAttachmentDao;
import vn.com.unit.fe_credit.entity.collection.ContractAttachment;
import vn.com.unit.fe_credit.entity.collection.RepossessionAttachment;
import vn.com.unit.fe_credit.service.v1.ContractAttachmentService;
import vn.com.unit.fe_credit.service.v1.RepossessionAttachmentService;

@Service
public class RepossessionAttachmentServiceImpl implements RepossessionAttachmentService{
	private static final Logger LOG = LoggerFactory.getLogger(ContractAttachmentService.class);

	private static final int BLOB_START_POSITON = 1;
	private static final String IMAGE_ATTACHMENT_TYPE = "image";
	
	@Autowired
	private RepossessionAttachmentDao attachmentDao;

	@Override
	@Transactional("txnManagerCollections")
	public List<RepossessionAttachmentDto> getContractAttachments(String contractId) {

		List<RepossessionAttachmentDto> repossessionAttachmentDto = null;
		LOG.info("Getting repossession attachments based on contract Id - " + contractId);

		List<RepossessionAttachment> repossessionAttachments = attachmentDao.getAttachmentsByContractId(contractId);

		if (CollectionUtils.isEmpty(repossessionAttachments)) {
			// No contract attachments
			repossessionAttachmentDto = Collections.emptyList();
		} else {

			int blobLength = 0;
			repossessionAttachmentDto = new ArrayList<>();

			for (RepossessionAttachment attachment : repossessionAttachments) {

				Blob attachmentData = attachment.getAttachmentData();
				if (attachmentData != null) {
					
					try {
						RepossessionAttachmentDto attachmentDto = new RepossessionAttachmentDto();
						attachmentDto.setAttachmentId(attachment.getId());
						attachmentDto.setAttachmentName(attachment.getAttachmentName());
						attachmentDto.setContractId(contractId);
						attachmentDto.setAttachmentType(attachment.getAttachmentType());
						
						if(attachment.getAttachmentType().contains(IMAGE_ATTACHMENT_TYPE)){
							blobLength = (int) attachmentData.length();
							byte[] dataBytes = attachmentData.getBytes(BLOB_START_POSITON, blobLength);
							attachmentDto.setAttachmentData(String.format("data:%s;base64," , attachment.getAttachmentType())+ new String(dataBytes));
						}
						
						repossessionAttachmentDto.add(attachmentDto);

					} catch (SQLException exp) {
						exp.printStackTrace();
						LOG.error(
								"Error occurred while getting attachment data - " + ExceptionUtils.getStackTrace(exp));
						throw new RuntimeException(exp);
					}
				}
			}
		}
		return repossessionAttachmentDto;
	}

	
	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.service.v1.ContractAttachmentService#getContractAttachment(java.lang.Integer)
	 */
	@Override
	@Transactional("txnManagerCollections")
	public RepossessionAttachmentDto getContractAttachment(Integer attachmentId) {
		
		RepossessionAttachmentDto repossessionAttachmentDto = null;
		LOG.info("Getting contract attachments based on attachmentId Id - " + attachmentId);
		
		RepossessionAttachment repossessionAttachment = attachmentDao.getAttachmentById(attachmentId);
		if(repossessionAttachment != null){
		
			repossessionAttachmentDto = new RepossessionAttachmentDto();
			repossessionAttachmentDto.setAttachmentId(attachmentId);
			repossessionAttachmentDto.setAttachmentName(repossessionAttachment.getAttachmentName());
			repossessionAttachmentDto.setAttachmentType(repossessionAttachment.getAttachmentType());
			repossessionAttachmentDto.setContractId(repossessionAttachment.getContractId());
			
			try{
				Blob attachmentBlob = repossessionAttachment.getAttachmentData();
				int blobLength = (int) attachmentBlob.length();
				byte[] dataBytes = attachmentBlob.getBytes(BLOB_START_POSITON, blobLength);
				repossessionAttachmentDto.setAttachmentData(new String(dataBytes));
				
			}catch (SQLException exp) {
				exp.printStackTrace();
				LOG.error(
						"Error occurred while getting attachment data - " + ExceptionUtils.getStackTrace(exp));
			}
		}
		
		return repossessionAttachmentDto;
	}
	
	
	/*public static void main(String[] args) throws SerialException, SQLException, FileNotFoundException, IOException {

		File file = new File("E:/Nitin/Nitin Malik.JPG");
		
		byte[] bytes = new byte[(int)file.length()];
		IOUtils.read(new FileInputStream(file), bytes);

		String encodedString = "data:image/jpeg;base64," + new String(java.util.Base64.getEncoder().encode(bytes));
		
		
		System.out.println("Base 64 encoded string - \n\n\n" + encodedString);
		
		Blob blob = new javax.sql.rowset.serial.SerialBlob(encodedString.getBytes());
		byte[] blobBytes = blob.getBytes(BLOB_START_POSITON, (int)blob.length());

		System.out.println("Base 64 decoded string - " + new String(blobBytes));
		
		// File to base64 string
		// base64 string to bytes
		// bytes to blob
		// blob to bytes
		// bytes to base64 string
	}*/


}
