package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.PushMessageBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.PushMessageTemplateDao;
import vn.com.unit.fe_credit.entity.PushMessageTemplate;
import vn.com.unit.fe_credit.utils.Utils;

@Repository
public class PushMessageTemplateDaoImpl extends GenericDAOImpl<PushMessageTemplate, Long> implements PushMessageTemplateDao {

	@Autowired
	SystemConfig systemConfig;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public Integer searchMaxNoByDate(String date) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT MAX(NO_IN_DATE) from PUSH_MESSAGE_TEMPLATE ");
		hql.append(" WHERE 1=1 ");
		hql.append(" AND to_char(CREATED_DATE,'MM-dd-yyyy') = :date");

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("date", date);
		Integer a = null;
		if (query.uniqueResult() == null) {
			a = 0;
		} 
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;
	}
	
	@Override
	public Integer pushCountSuccess(Long templateId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*)"
				+ " from PUSH_MESSAGE_TEMPLATE p JOIN MESSAGES mes on p.TEMPLATE_ID = mes.PUSH_MESSAGE_ID ");
		hql.append(" WHERE 1=1 AND mes.PUSH_NOTIFICATION_STATUS = '200' ");
		hql.append(" AND p.TEMPLATE_ID = :templateId");

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("templateId", templateId);
		Integer a = null;
		if (query.uniqueResult() == null) {
			a = 0;
		} 
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;
	}
	
	@Override
	public Integer pushCountFail(Long templateId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*)"
				+ " from PUSH_MESSAGE_TEMPLATE p JOIN MESSAGES mes on p.TEMPLATE_ID = mes.PUSH_MESSAGE_ID ");
		hql.append(" WHERE 1=1 AND mes.PUSH_NOTIFICATION_STATUS != '200' ");
		hql.append(" AND p.TEMPLATE_ID = :templateId");

		Query query = getSession().createSQLQuery(hql.toString());
		query.setParameter("templateId", templateId);
		Integer a = null;
		if (query.uniqueResult() == null) {
			a = 0;
		} 
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;
	}
	
	@Override
	public List<PushMessageTemplate> getPushMessageTemplateByDate(String date) {
		String sql ="SELECT TEMPLATE_ID as templateId, FILE_NAME as fileName, MESSAGE_TYPE as messageType, SCHEDULE as schedule, IS_SENDNOW as isSendNow, "
				+ "TEMPLATE_SUBJECT as templateSubject, IP_ADDRESS as ipAddress, NO_IN_DATE as noInDate"
				+ " FROM PUSH_MESSAGE_TEMPLATE"
				+ " WHERE IS_SENDNOW = '0' AND SCHEDULE = TO_DATE('" + date + "','yyyy-MM-dd HH24:MI')";
		
		Query query = getSession().createSQLQuery(sql)
				.addScalar("templateId", new LongType())
				.addScalar("schedule", new TimestampType())
				.addScalar("fileName", new StringType())
				.addScalar("templateSubject", new StringType())
				.addScalar("ipAddress", new StringType())
				.addScalar("isSendNow", new IntegerType())
				.addScalar("noInDate", new IntegerType())
				.addScalar("messageType", new IntegerType());
		query.setResultTransformer(Transformers.aliasToBean(PushMessageTemplate.class));

		return query.list();		
	}
	
	@Override
	public PushMessageBean searchPushMessageListTemplate(PushMessageBean bean) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT PUSH.STATUS as status, PUSH.TEMPLATE_ID as templateId, PUSH.TEMPLATE_SUBJECT as templateSubject, PUSH.MESSAGE_TYPE as messageType, PUSH.FILE_NAME as fileName, "
				+ "PUSH.SCHEDULE as schedule, PUSH.CREATED_DATE as created_date"
				+ " FROM PUSH_MESSAGE_TEMPLATE push"
				+ " WHERE 1=1");
		
		if(StringUtils.isNotEmpty(bean.getTemplateSubject())){
			sql.append(" and  UPPER (PUSH.TEMPLATE_SUBJECT) like UPPER(:subject) ");
		}	
	
		 if(bean.getToSendDate() != null){    
	         sql.append(" and  PUSH.CREATED_DATE  <= :tosenddate  ");
		 } else {
//			 sql.append(" and  PUSH.CREATED_DATE  <= SYSDATE  ");
		 }
	 
		 if(bean.getFormSendDate() != null){           
			   sql.append(" and PUSH.CREATED_DATE >= :fromsenddate ");
		 } else {
//			 sql.append(" and  PUSH.CREATED_DATE  >= ADD_MONTHS(SYSDATE, -1)  ");
		 }		 
		 
		 if(bean.getMessageType() != null){
				sql.append(" and PUSH.MESSAGE_TYPE = :type ");
		 }
		 
		String sqlCount = "select count(*) from (" + sql.toString() + ")"; 
		
		sql.append(" order by PUSH.SCHEDULE desc ");
		
		// build query get list
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("status", new BooleanType())
				.addScalar("templateId", new LongType())
				.addScalar("templateSubject", new StringType())
				.addScalar("messageType", new IntegerType())
				.addScalar("fileName", new StringType())
				.addScalar("schedule", new TimestampType())
				.addScalar("created_date", new TimestampType());
		
		Query queryCount = getSession().createSQLQuery(sqlCount.toString());
		
		if(StringUtils.isNotEmpty(bean.getTemplateSubject())){
			 query.setParameter( "subject","%" + bean.getTemplateSubject().trim()+"%" );
			 queryCount.setParameter( "subject","%" + bean.getTemplateSubject().trim()+"%" );
		}
		
		if(bean.getMessageType() != null){		
			query.setParameter( "type",bean.getMessageType() );
			queryCount.setParameter( "type",bean.getMessageType() );
		}		
		
		 if(bean != null && bean.getFormSendDate() != null){              
             query.setParameter( "fromsenddate", Utils.setTimeToZero(bean.getFormSendDate()));
             queryCount.setParameter( "fromsenddate", Utils.setTimeToZero(bean.getFormSendDate()));
		 }

		 if(bean != null && bean.getToSendDate() != null){              
             query.setParameter( "tosenddate", Utils.setTimeToMax(bean.getToSendDate()));
             queryCount.setParameter( "tosenddate", Utils.setTimeToMax(bean.getToSendDate()));
		 }
		// build query get count
		query.setResultTransformer(Transformers.aliasToBean(PushMessageTemplate.class));

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount =  Integer.valueOf(queryCount.uniqueResult().toString());
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		bean.setTotal(totalCount);
		
		bean.setListResult(query.list());
		return bean;
	}
	
}
