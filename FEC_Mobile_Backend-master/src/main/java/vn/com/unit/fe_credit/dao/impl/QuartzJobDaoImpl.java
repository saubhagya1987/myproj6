package vn.com.unit.fe_credit.dao.impl;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.QuartzJobBean;
import vn.com.unit.fe_credit.dao.QuartzJobDao;
import vn.com.unit.fe_credit.entity.QuartzTriggers;

@Repository
public class QuartzJobDaoImpl extends GenericDAOImpl<QuartzTriggers, Long> implements QuartzJobDao {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public QuartzJobBean getQuartzJob(QuartzJobBean bean) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	QT.SCHED_NAME AS schedName, ");
		sql.append(" 	QT.TRIGGER_NAME AS triggerName, ");
		sql.append(" 	QT.TRIGGER_GROUP AS triggerGroup, ");
		sql.append(" 	QT.JOB_NAME AS jobName, ");
		sql.append(" 	QT.JOB_GROUP AS jobGroup, ");
		sql.append(" 	QT.NEXT_FIRE_TIME AS nextFireTime, ");
		sql.append(" 	QT.PREV_FIRE_TIME AS prevFireTime, ");
		sql.append(" 	QT.TRIGGER_STATE AS triggerState, ");
		sql.append(" 	QT.TRIGGER_TYPE AS triggerType, ");
		sql.append(" 	QC.CRON_EXPRESSION AS cronExpression ");
		sql.append(" FROM ");
		sql.append(" 	QRTZ_TRIGGERS QT ");
		sql.append(" JOIN QRTZ_CRON_TRIGGERS QC ON QT.SCHED_NAME = QC.SCHED_NAME ");
		sql.append(" AND QT.TRIGGER_NAME = QC.TRIGGER_NAME ");
		sql.append(" WHERE ");
		sql.append(" 	1 = 1  ");

		if (StringUtils.isNotEmpty(bean.getSchedName())) {
			sql.append(" and QT.SCHED_NAME like :schedName ");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerName())) {
			sql.append(" and QT.TRIGGER_NAME like :triggerName ");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerGroup())) {
			sql.append(" and QT.TRIGGER_GROUP like :triggerGroup ");
		}
		if (StringUtils.isNotEmpty(bean.getJobName())) {
			sql.append(" and QT.JOB_NAME like :jobName ");
		}
		if (StringUtils.isNotEmpty(bean.getJobGroup())) {
			sql.append(" and QT.JOB_GROUP like :jobGroup ");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerState())) {
			sql.append(" and QT.TRIGGER_STATE like :triggerState ");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerType())) {
			sql.append(" and QT.TRIGGER_TYPE like :triggerType ");
		}
		sql.append(" order by QT.TRIGGER_NAME asc ");

		// build query get list
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("schedName", new StringType()).addScalar("triggerName", new StringType())
				.addScalar("triggerGroup", new StringType()).addScalar("jobName", new StringType()).addScalar("jobGroup", new StringType())
				.addScalar("nextFireTime", new LongType()).addScalar("prevFireTime", new LongType()).addScalar("triggerState", new StringType())
				.addScalar("triggerType", new StringType()).addScalar("cronExpression", new StringType());

		if (StringUtils.isNotEmpty(bean.getSchedName())) {
			query.setParameter("schedName", "%" + bean.getSchedName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerName())) {
			query.setParameter("triggerName", "%" + bean.getTriggerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerGroup())) {
			query.setParameter("triggerGroup", "%" + bean.getTriggerGroup().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getJobName())) {
			query.setParameter("jobName", "%" + bean.getJobName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getJobGroup())) {
			query.setParameter("jobGroup", "%" + bean.getJobGroup().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerState())) {
			query.setParameter("triggerState", "%" + bean.getTriggerState().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getTriggerType())) {
			query.setParameter("triggerType", "%" + bean.getTriggerType().trim() + "%");
		}

		query.setResultTransformer(Transformers.aliasToBean(QuartzTriggers.class));

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = query.list().size();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}
}
