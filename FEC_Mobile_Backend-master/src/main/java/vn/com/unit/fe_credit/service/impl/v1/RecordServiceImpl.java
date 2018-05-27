package vn.com.unit.fe_credit.service.impl.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.dao.RecordsDao;
import vn.com.unit.fe_credit.entity.collection.RecordAttachment;
import vn.com.unit.fe_credit.service.v1.ConverterService;
import vn.com.unit.fe_credit.service.v1.RecordService;

@Service
public class RecordServiceImpl implements RecordService {
	
	@Autowired
	RecordsDao recordsDao;
	
	@Autowired
	ConverterService converterService;

	@Override
	public Model getRecords(Model model) {
		// TODO Auto-generated method stub
		List<Object[]> records = recordsDao.findAll();
		List<RecordDto> recordDtos = converterService.entitiesToDtosRecord(records);
		model.addAttribute("result", recordDtos);
		//model.addAttribute("bean", recordBean);
		
		return model;
	}

	@Override
	public RecordDto getAttachmentId(int id) {
		RecordDto attachmentId = recordsDao.getAttachmentId(id);
		return attachmentId;
	}

}
