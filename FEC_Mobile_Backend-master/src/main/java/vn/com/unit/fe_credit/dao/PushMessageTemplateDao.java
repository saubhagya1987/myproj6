package vn.com.unit.fe_credit.dao;

import java.util.List;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.bean.PushMessageBean;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;

public interface PushMessageTemplateDao extends GenericDAO<PushMessageTemplate, Long> {

	Integer searchMaxNoByDate(String date);
	
	List<PushMessageTemplate> getPushMessageTemplateByDate(String date);

	PushMessageBean searchPushMessageListTemplate(PushMessageBean bean);

	Integer pushCountSuccess(Long templateId);

	Integer pushCountFail(Long templateId);

}
