package vn.com.unit.fe_credit.service.v1;

import org.springframework.ui.Model;

import vn.com.unit.fe_credit.bean.v1.RecordDto;
import vn.com.unit.fe_credit.entity.collection.RecordAttachment;

public interface RecordService {

	public Model getRecords(Model model);

	public RecordDto getAttachmentId(int id);

}
