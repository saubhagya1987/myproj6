package vn.com.unit.fe_credit.service.impl.v1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialException;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.v1.ContractAttachmentDto;
import vn.com.unit.fe_credit.dao.v1.ContractAttachmentDao;
import vn.com.unit.fe_credit.entity.collection.ContractAttachment;
import vn.com.unit.fe_credit.service.v1.ContractAttachmentService;
import sun.misc.BASE64Decoder;

@Service
public class ContractAttachmentServiceImpl implements ContractAttachmentService {

	private static final Logger LOG = LoggerFactory.getLogger(ContractAttachmentService.class);

	private static final int BLOB_START_POSITON = 1;
	private static final String IMAGE_ATTACHMENT_TYPE = "image";
	
	@Autowired
	private ContractAttachmentDao attachmentDao;

	@Override
	@Transactional("txnManagerCollections")
	public List<ContractAttachmentDto> getContractAttachments(String contractId) {

		List<ContractAttachmentDto> contractAttachmentDtos = null;
		LOG.info("Getting contract attachments based on contract Id - " + contractId);

		List<ContractAttachment> contractAttachments = attachmentDao.getAttachmentsByContractId(contractId);

		if (CollectionUtils.isEmpty(contractAttachments)) {
			// No contract attachments
			contractAttachmentDtos = Collections.emptyList();
		} else {

			int blobLength = 0;
			contractAttachmentDtos = new ArrayList<>();

			for (ContractAttachment attachment : contractAttachments) {

				Blob attachmentData = attachment.getAttachmentData();
				if (attachmentData != null) {
					
					try {
						ContractAttachmentDto attachmentDto = new ContractAttachmentDto();
						attachmentDto.setAttachmentId(attachment.getId());
						attachmentDto.setAttachmentName(attachment.getAttachmentName());
						attachmentDto.setContractId(contractId);
						attachmentDto.setAttachmentType(attachment.getAttachmentType());
						
						if(attachment.getAttachmentType().contains(IMAGE_ATTACHMENT_TYPE)){
							blobLength = (int) attachmentData.length();
							byte[] dataBytes = attachmentData.getBytes(BLOB_START_POSITON, blobLength);
							attachmentDto.setAttachmentData(String.format("data:%s;base64," , attachment.getAttachmentType())+ new String(dataBytes));
						}
						
						contractAttachmentDtos.add(attachmentDto);

					} catch (SQLException exp) {
						exp.printStackTrace();
						LOG.error(
								"Error occurred while getting attachment data - " + ExceptionUtils.getStackTrace(exp));
						throw new RuntimeException(exp);
					}
				}
			}
		}
		return contractAttachmentDtos;
	}

	
	/* (non-Javadoc)
	 * @see vn.com.unit.fe_credit.service.v1.ContractAttachmentService#getContractAttachment(java.lang.Integer)
	 */
	@Override
	@Transactional("txnManagerCollections")
	public ContractAttachmentDto getContractAttachment(Integer attachmentId) {
		
		ContractAttachmentDto contractAttachmentDto = null;
		LOG.info("Getting contract attachments based on attachmentId Id - " + attachmentId);
		
		ContractAttachment contractAttachment = attachmentDao.getAttachmentById(attachmentId);
		if(contractAttachment != null){
		
			contractAttachmentDto = new ContractAttachmentDto();
			contractAttachmentDto.setAttachmentId(attachmentId);
			contractAttachmentDto.setAttachmentName(contractAttachment.getAttachmentName());
			contractAttachmentDto.setAttachmentType(contractAttachment.getAttachmentType());
			contractAttachmentDto.setContractId(contractAttachment.getContractId());
			
			try{
				Blob attachmentBlob = contractAttachment.getAttachmentData();
				int blobLength = (int) attachmentBlob.length();
				byte[] dataBytes = attachmentBlob.getBytes(BLOB_START_POSITON, blobLength);
				contractAttachmentDto.setAttachmentData(new String(dataBytes));
				
			}catch (SQLException exp) {
				exp.printStackTrace();
				LOG.error(
						"Error occurred while getting attachment data - " + ExceptionUtils.getStackTrace(exp));
			}
		}
		
		return contractAttachmentDto;
	}
	
	
	@SuppressWarnings("restriction")
	public static void main(String[] args) throws SerialException, SQLException, FileNotFoundException, IOException {

		File file = new File("E:/saubhagya.JPG");
		
		byte[] bytes = new byte[(int)file.length()];
		IOUtils.read(new FileInputStream(file), bytes);

		String encodedString = "data:image/jpeg;base64," + new String(java.util.Base64.getEncoder().encode(bytes));
		
		
		System.out.println("Base 64 encoded string - \n\n\n" + encodedString);
		
		Blob blob = new javax.sql.rowset.serial.SerialBlob(encodedString.getBytes());
		byte[] blobBytes = blob.getBytes(BLOB_START_POSITON, (int)blob.length());

		System.out.println("Base 64 decoded string - " + new String(blobBytes));
		
		
		BufferedImage image = null;
		byte[] imageByte;

		BASE64Decoder decoder = new BASE64Decoder();
		imageByte = decoder.decodeBuffer(new String(java.util.Base64.getEncoder().encode(bytes)));
		ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
		image = ImageIO.read(bis);
		bis.close();

		// write the image to a file
		File outputfile = new File("E:/image.png");
		ImageIO.write(image, "png", outputfile);
		
		// File to base64 string
		// base64 string to bytes
		// bytes to blob
		// blob to bytes
		// bytes to base64 string
	}
}
