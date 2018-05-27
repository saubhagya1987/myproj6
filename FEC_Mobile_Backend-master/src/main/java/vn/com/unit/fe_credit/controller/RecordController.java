package vn.com.unit.fe_credit.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.entity.collection.RecordAttachment;
import vn.com.unit.fe_credit.service.v1.RecordService;


@Controller
@RequestMapping("/cms")
public class RecordController {
	
	@Autowired
	RecordService recordService;
	
	@RequestMapping(value = "/record/list", method = RequestMethod.GET)
	public String getRecords(Model model){
		
		model = recordService.getRecords(model);
		
		return "system.record.list";
		
	}
	
	@RequestMapping(value = "/record/attachment/{id}", method = RequestMethod.GET)
	public void getAttachment(@PathVariable(value="id") String id ,HttpServletRequest request, HttpServletResponse response) throws IOException{
				
		System.out.println("id--->>"+id);		
		RecordDto attachmentId=recordService.getAttachmentId(Integer.parseInt(id));
	    byte[] buffer=attachmentId.getAttachmentData();
		//byte[] buffer=recordDtos.get(0).getAttachmentData();
		final byte[]  dec=Base64.decodeBase64(buffer);
		ServletOutputStream oStream = response.getOutputStream();
		if(attachmentId.getAttachmentType().equals("pdf")){
			response.setContentType("application/pdf");
		}
		else if(attachmentId.getAttachmentType().equals("jpg")){
			response.setContentType("image/jpeg");
		}
		else if(attachmentId.getAttachmentType().equals("jpeg")){
			response.setContentType("image/pjpeg");
		}
		else if(attachmentId.getAttachmentType().equals("txt")){
			response.setContentType("text/plain");
		}
		oStream.write(dec);
   		oStream.close();
		
		
	}
	
	

}
