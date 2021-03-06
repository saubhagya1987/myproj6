package vn.com.unit.fe_credit.service.impl.v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.service.v1.ConverterService;
import vn.com.unit.fe_credit.utils.Utils;

@Service
public class ConverterServiceImple implements ConverterService {

	@SuppressWarnings("restriction")
	@Override
	public RecordDto entityToDtoRecord(Object[] record) {
		// TODO Auto-generated method stub
		RecordDto recordDto = new RecordDto();
	   /* List<RecordAttachment> recordaAttachment=new ArrayList<RecordAttachment>();
		recordDto.setAttachedFilesLink(record.getAttachedFilesLink());
		//recordDto.setCheckIn(record.getCheckIn());
		recordDto.setContactDate(record.getContactDate().toString().split(" ")[0]);
		recordDto.setContactedWith(record.getContactedWith());
		recordDto.setContactMode(record.getContactMode());
		recordDto.setContactPlace(record.getContactPlace());
		recordDto.setContactTime(record.getContactTime());
		recordDto.setContractId(record.getContractId());
		//recordDto.setAttachmentData(recordaAttachment.addAll(record.getRecordAttachment()));
		
		//recordDto.setNextActionDate(record.getNextActionDate());
		recordDto.setPersonContacted(record.getPersonContacted());
		recordDto.setPtpAmount(record.getPtpAmount());
		recordDto.setCheckIn(record.getCheckIn());
		recordDto.setContactDate(record.getActionDate() != null ? record.getActionDate().toString().split(" ")[0] :"");
		recordDto.setContactedWith(record.getPersonContacted() != null ? record.getPersonContacted() : "");
		recordDto.setContactMode(record.getContactMode() != null ? record.getContactMode() : "");
		recordDto.setContactPlace(record.getPlaceContacted() != null ? record.getPlaceContacted() : "");
		recordDto.setContractId(record.getContractId() != null ? record.getContractId() : "");
		recordDto.setPersonContacted(record.getPersonContacted() != null ? record.getPersonContacted() : "");		
		recordDto.setRecordId(record.getRecordId());
		recordDto.setRemark(record.getRemark());
		recordDto.setReminderMode(record.getReminderMode());
		recordDto.setResponseCode(record.getResponseCode());		
		recordDto.setReminderMode(record.getReminderMode() != null ? record.getReminderMode() : "");*/
		
			
		recordDto.setContractId((String)record[0]);
		recordDto.setPersonContacted((String)record[1]);
		recordDto.setContactMode((String)record[2]);
		recordDto.setContactPlace((String)record[3]);		
		try {
			recordDto.setContactDate(Utils.getFormatDate((Date)(record[4]), "yyyy-MM-dd"));
		} catch (Exception e) {			
			e.printStackTrace();
		}
		recordDto.setContactedWith((String)record[5]);
		recordDto.setAttachmentType((String)record[6]);
		
		recordDto.setAttachmentData((byte[]) record[7]);
		recordDto.setId((Integer) record[8]);
		recordDto.setCheckIn((String)record[9]);
		 /* try {
			   File f1=new File("E:\\Autogenerated_Xml\\");
			   if(!f1.isDirectory())f1.mkdirs();
			   OutputStream out =new java.io.FileOutputStream(new File("E:\\Autogenerated_Xml\\"+"abcde.pdf"),true);
			   final byte[]  dec=Base64.decodeBase64((byte[]) record[7]);
			   out.write(dec);
			   out.flush();
			   out.close();
			   //return true;
			  } catch (FileNotFoundException e) {
			   e.printStackTrace();
			   //return false;
			  } catch (IOException e) {
			   e.printStackTrace();
			   //return false;
			   
			  }
			
		String image=new String((byte[]) record[7]);
		System.out.println("image:--------------------    "+image);*/
		
		
		//recordDto.setAttachmentData(StringUtils.newStringUtf8(Base64.encode((byte[]) record[7])));
		//recordDto.setAttachmentData(Base64.decodeBase64((byte[]) record[7]));
		//System.out.println("image :"+Base64.encodeBase64String((byte[]) record[7]));
		//new sun.misc.BASE64Encoder().encode((byte[]) record[7]);
		//System.out.println("image :"+new sun.misc.BASE64Encoder().encode((byte[]) record[7]));
		//(byte[])record[7]
		return recordDto;
	}
	
	@Override
	public List<RecordDto> entitiesToDtosRecord(List<Object[]> records) {
		// TODO Auto-generated method stub
		List<RecordDto> recordDtos = new ArrayList<RecordDto>();
		for(int i=0;i < records.size();i++){
			Object[] record = records.get(i);
			recordDtos.add(entityToDtoRecord(record));
		}
		
		return recordDtos;
	}

}
