package vn.com.unit.fe_credit.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import vn.com.unit.fe_credit.bean.PushMessageBean;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;

public interface PushMessageTemplateService {

	void save(PushMessageTemplate pushMessageTemplate);
	
	Integer searchMaxNoByDate(String date);
	
	Integer pushCountSuccess(Long templateId);
	
	Integer pushCountFail(Long templateId);

	List<PushMessageTemplate> getPushMessageTemplateByDate(String date);

	PushMessageTemplate findById(Long templateId);
	
	PushMessageBean searchPushMessageListTemplate(PushMessageBean bean);

	void delete(Long messageId);
	
	List<MessageDashBoard> getDataFileImported(Workbook workbook);
	
	void pushMessageFromTemplate(PushMessageTemplate pushMessageTemplate) throws Exception;
	
	PushMessageTemplate findTemplateNoSentById(Long templateId);
	
	void delete(PushMessageTemplate pushMessageTemplate);

}
