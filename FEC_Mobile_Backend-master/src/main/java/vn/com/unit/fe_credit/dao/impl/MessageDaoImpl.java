package vn.com.unit.fe_credit.dao.impl;

import static vn.com.unit.fe_credit.config.SystemConfig.DRIVERCLASS;
import static vn.com.unit.fe_credit.config.SystemConfig.JDBCURL;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_KH;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionBuilder;
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

import vn.com.unit.fe_credit.bean.MessageDashBoardBean;
import vn.com.unit.fe_credit.bean.MessageInboxBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.MessageDao;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.MessageDashBoard;
import vn.com.unit.fe_credit.utils.Utils;

@Repository
public class MessageDaoImpl extends GenericDAOImpl<MessageDashBoard, Long> implements MessageDao {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Autowired
	private UserProfile userProfile;

	@Autowired
	SystemConfig systemConfig;

	@Override
	public MessageDashBoardBean search(MessageDashBoardBean bean) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CUSTOMERID, ");
		sql.append(" 	ACCOUNTID, ");
		sql.append(" 	MESSAGEID, ");
		sql.append(" 	FULLNAME, ");
		sql.append(" 	DOB, ");
		sql.append(" 	CELLPHONE, ");
		sql.append(" 	SUBJECT, ");
		sql.append(" 	CREATEDDATE, ");
		sql.append(" 	TYPE, ");
		sql.append(" 	STATUS, ");
		sql.append(" 	CATEGORY, ");
		sql.append(" 	SUBCATEGORY, ");
		sql.append(" 	CUSTOMERIMGPATH, ");
		sql.append(" 	PARENTMSGID, ");
		sql.append(" 	CONTRACTMSGID ");
		sql.append(" FROM ");
		sql.append(" 	( ");
		sql.append(" 		SELECT ");
		sql.append(" 			MES.PARENTMSGID, ");
		sql.append(" 			mes.messageID, ");
		sql.append(" 			mes2.messageID AS messageID2, ");
		sql.append(" 			mes.customerId AS customerId, ");
		sql.append(" 			mes.accountId, ");
		sql.append(" 			cus.fullName, ");
		sql.append(" 			cus.birthDay AS dob, ");
		sql.append(" 			cus.cellphone, ");
		sql.append(" 			mes.subject AS subject, ");
		sql.append(" 			mes.createdDate AS createdDate, ");
		sql.append(" 			mes. TYPE, ");
		sql.append(" 			mes.STATUS, ");
		sql.append(" 			mes. CATEGORY, ");
		sql.append(" 			mes.subcategory, ");
		sql.append(" 			cus.imagePath AS customerImgPath, ");
		sql.append(" 			mes.contractMsgId, ");
		sql.append(" 			mes.ISMSGUSER ");
		sql.append(" 		FROM ");
		sql.append(" 			messages mes ");
		sql.append(" 		JOIN Customer cus ON cus.customerId = mes.customerId ");
		sql.append(" 		LEFT JOIN messages mes2 ON mes.PARENTMSGID = MES2.PARENTMSGID ");
		sql.append(" 		WHERE ");
		sql.append(" 			(mes.ISPARENT =1 OR mes.PARENTMSGID = mes.messageID  )");
		sql.append(" 		AND mes.accountId IS NULL ");

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			sql.append(" and  lower(cus.fullName) like lower(:fullName) ");
		}
		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			sql.append(" and  lower(cus.cellPhone) like lower(:cellPhone) ");
		}

		if (StringUtils.isNotEmpty(bean.getSubject())) {
			sql.append(" and  lower(mes.subject) like lower(:subject) ");
		}

		if (StringUtils.isNotEmpty(bean.getStatus())) {
			sql.append(" and  mes.status =:status ");
		}
		if (StringUtils.isNotEmpty(bean.getMessageType())) {
			sql.append(" and  mes.type = :type ");
		}

		if (bean != null && bean.getToSendDate() != null) {
			sql.append(" and  mes.createdDate <= :tosenddate ");
		}

		if (bean != null && bean.getFormSendDate() != null) {
			sql.append(" and  mes.createdDate >= :fromsenddate ");
		}
		sql.append(" 	) ");
		sql.append(" WHERE ");
		sql.append(" 	ISMSGUSER = 1 ");
		sql.append(" OR (messageID != messageID2) ");

		sql.append(" UNION");

		sql.append(
				" SELECT mes.customerId as customerId, mes.accountId, mes.messageID, cus.fullName, cus.birthDay as dob, cus.cellphone, mes.subject as subject,");
		sql.append(
				" mes.createdDate as createdDate, mes.type, mes.status, mes.category, mes.subcategory, cus.imagePath as customerImgPath, mes.parentMsgId, mes.contractMsgId");
		sql.append(" FROM messages mes");
		sql.append(" INNER JOIN Customer cus ON cus.customerId = mes.customerId");
		sql.append(" WHERE 1=1");
		sql.append(" AND mes.messageID in ( select sub.messageID");
		sql.append(" from (select messub.CUSTOMERID,messub.parentMsgId,Min(messub.messageID) as messageID from messages messub");
		sql.append(" where messub.status in(5,7)");
		sql.append(" group by messub.CUSTOMERID,messub.parentMsgId ) sub )");

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			sql.append(" and  lower(cus.fullName) like lower(:fullName) ");
		}
		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			sql.append(" and  lower(cus.cellPhone) like lower(:cellPhone) ");
		}

		if (StringUtils.isNotEmpty(bean.getSubject())) {
			sql.append(" and  lower(mes.subject) like lower(:subject) ");
		}

		if (StringUtils.isNotEmpty(bean.getStatus())) {
			sql.append(" and  mes.status =:status ");
		}
		if (StringUtils.isNotEmpty(bean.getMessageType())) {
			sql.append(" and  mes.type = :type ");
		}

		if (bean != null && bean.getToSendDate() != null) {
			sql.append(" and  mes.createdDate <= :tosenddate ");
		}

		if (bean != null && bean.getFormSendDate() != null) {
			sql.append(" and  mes.createdDate >= :fromsenddate ");
		}

		String sqlCount = "select count(*) from (" + sql.toString() + ")";
		sql.append(" order by createdDate desc ");
		// build query get list
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("customerImgPath", new StringType())
				.addScalar("contractMsgId", new StringType()).addScalar("customerId", new LongType()).addScalar("parentMsgId", new LongType())
				.addScalar("accountId", new LongType()).addScalar("messageID", new LongType()).addScalar("fullName", new StringType())
				.addScalar("dob", new TimestampType()).addScalar("cellphone", new StringType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("type", new IntegerType()).addScalar("category", new IntegerType())
				.addScalar("subcategory", new IntegerType()).addScalar("status", new IntegerType());

		Query queryCount = getSession().createSQLQuery(sqlCount.toString());

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			query.setParameter("fullName", "%" + bean.getCustomerName().trim() + "%");
			queryCount.setParameter("fullName", "%" + bean.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			query.setParameter("cellPhone", "%" + bean.getCellPhone().trim() + "%");
			queryCount.setParameter("cellPhone", "%" + bean.getCellPhone().trim() + "%");
		}

		if (StringUtils.isNotEmpty(bean.getSubject())) {
			query.setParameter("subject", "%" + bean.getSubject().trim() + "%");
			queryCount.setParameter("subject", "%" + bean.getSubject().trim() + "%");
		}

		if (StringUtils.isNotEmpty(bean.getStatus())) {
			query.setParameter("status", bean.getStatus());
			queryCount.setParameter("status", bean.getStatus());
		}
		if (StringUtils.isNotEmpty(bean.getMessageType())) {
			query.setParameter("type", Integer.valueOf(bean.getMessageType()));
			queryCount.setParameter("type", Integer.valueOf(bean.getMessageType()));
		}

		if (bean != null && bean.getToSendDate() != null) {
			query.setParameter("tosenddate", Utils.setTimeToMax(bean.getToSendDate()));
			queryCount.setParameter("tosenddate", Utils.setTimeToMax(bean.getToSendDate()));
		}

		if (bean != null && bean.getFormSendDate() != null) {
			query.setParameter("fromsenddate", Utils.setTimeToZero(bean.getFormSendDate()));
			queryCount.setParameter("fromsenddate", Utils.setTimeToZero(bean.getFormSendDate()));
		}
		// build query get count
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = Integer.valueOf(queryCount.uniqueResult().toString());
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}

	@Override
	public List<MessageDashBoard> findByCustomerId(Long customerId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT m.messageID as messageID, m.subject as subject, m.createdDate as createdDate, m.readDate as readDate, m.type as type, m.status as status");
		sql.append(" FROM Messages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType())
				.addScalar("status", new IntegerType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		return query.list();
	}

	@Override
	public List<MessageDashBoard> findByCustomerIdAndContractMSID(String customerId, String contractmsgId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT m.messageID as messageID, m.subject as subject, m.createdDate as createdDate, m.readDate as readDate, m.type as type, m.status as status");
		sql.append(" FROM Messages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId and m.contractMsgId = :contractmsgId");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType())
				.addScalar("status", new IntegerType());
		query.setParameter("customerId", customerId);
		query.setParameter("contractmsgId", contractmsgId);
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		return query.list();
	}

	@Override
	public Map<String, Object> getInboxList(Long customerId, Integer category, String typeInbox, int typeSearch, int page, int limit) {

		// /**
		// * typeSearch
		// * 1 - list only
		// * 2 - count only
		// * 3 - count and list
		// * */

		StringBuffer sql = new StringBuffer("");
		sql.append(" SELECT ms.messageID AS messageID, ");
		sql.append(" 	ms.SUBJECT AS title, ");
		sql.append(" 	'' AS summary, ");
		sql.append(" 	'' AS relatedtopic, ");
		sql.append(" 	ms.CREATEDDATE AS DATETIME, ");
		sql.append(" 	( ");
		sql.append(" 		CASE  ");
		sql.append(" 			WHEN ( ");
		sql.append(" 					SELECT COUNT(*) ");
		sql.append(" 					FROM DOCUMENTINFO do ");
		sql.append(" 					WHERE do.REFERENCEID = ms.MESSAGEID ");
		sql.append(" 						AND do.DOCUMENTSOURCE = :documentSource ");
		sql.append(" 					) > 0 ");
		sql.append(" 				THEN 1 ");
		sql.append(" 			ELSE 0 ");
		sql.append(" 			END ");
		sql.append(" 		) AS hasAttachment, ");
		sql.append(" 	( ");
		sql.append(" 		( ");
		sql.append(" 			SELECT COUNT(*) + 1 ");
		sql.append(" 			FROM MESSAGES mssub ");
		sql.append(
				" 			WHERE mssub.PARENTMSGID = ms.MESSAGEID and mssub.PARENTMSGID != mssub.MESSAGEID and ( mssub.isParent is null OR mssub.isParent != 1)");
		sql.append(" 				AND CUSTOMERID = :customerId ");
		sql.append(" 			) ");
		sql.append(" 		) AS numberOfReplies, ");
		sql.append(" 	ms.ISREAD AS isRead, ");
		sql.append(" 	ms.READDATE AS readDate , ");
		sql.append(" 	ms.type AS type , ");
		sql.append(" 	ms.STATUS AS STATUS, ");
		sql.append(" 	ms.contractMsgId AS contractMsgId ");
		sql.append(" FROM MESSAGES ms ");
		sql.append(" WHERE (ms.PARENTMSGID = ms.MESSAGEID OR ms.ISPARENT = 1) ");
		sql.append(" 	AND CUSTOMERID = :customerId ");
		sql.append(" 	AND ( ");
		sql.append(" 		ISMSGUSER = 0 ");
		sql.append(" 		OR ( ");
		sql.append(" 			ISMSGUSER = 1 ");
		sql.append(" 			AND ACCOUNTID IS NOT NULL ");
		sql.append(" 			) ");
		sql.append(" 		) ");
		sql.append("  ");

		if (StringUtils.isNotEmpty(typeInbox) && typeInbox.toString().toUpperCase().equals("READ")) {
			sql.append(" AND ISREAD=1 ");
		} else if (StringUtils.isNotEmpty(typeInbox) && typeInbox.toString().toUpperCase().equals("UNREAD")) {
			sql.append(" AND ISREAD=0 ");
		}

		String sqlCount = "select count(*) from (" + sql.toString() + ")";
		sql.append(" order by CREATEDDATE desc ");

		Map<String, Object> mapData = new HashMap<String, Object>();

		if (typeSearch == 1 || typeSearch == 3) {

			Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("title", new StringType())
					.addScalar("summary", new StringType()).addScalar("relatedtopic", new StringType()).addScalar("datetime", new TimestampType())
					.addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType()).addScalar("hasAttachment", new BooleanType())
					.addScalar("numberOfReplies", new IntegerType()).addScalar("isRead", new BooleanType()).addScalar("status", new IntegerType())
					.addScalar("contractMsgId", new StringType());

			query.setParameter("customerId", customerId);
			query.setParameter("documentSource", SystemConfig.MESSAGE_SOURCES);

			query.setMaxResults(limit);
			query.setFirstResult((page - 1) * limit);

			query.setResultTransformer(Transformers.aliasToBean(MessageInboxBean.class));
			mapData.put("LIST", query.list());

		} else if (typeSearch == 2 || typeSearch == 3) {

			Query queryCount = getSession().createSQLQuery(sqlCount.toString());
			queryCount.setParameter("customerId", customerId);
			queryCount.setParameter("documentSource", SystemConfig.MESSAGE_SOURCES);
			int totalCount = Integer.valueOf(queryCount.uniqueResult().toString());
			mapData.put("COUNT", totalCount);
		}

		return mapData;

	}

	@Override
	public List<Customer> search(Long typeId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT msg.type as msgType, cs.emailAddress , cs.fullName, cs.cellPhone, cs.imagePath, cs.idCardNumber, ");
		sql.append(" cs.customerId, msg.parentMsgId, msg.subject");
		sql.append(" FROM Messages msg");
		sql.append(" INNER JOIN Customer cs ON cs.customerId = msg.customerId");
		sql.append(" INNER JOIN Account ac ON ac.accountId = msg.accountId");
		sql.append(" WHERE ac.accountId =:accountId");
		sql.append(" AND msg.type =:typeId and msg.parentMsgId = msg.messageID and msg.status not in (5,7)");
		sql.append(
				" GROUP BY cs.emailAddress, cs.fullName, cs.cellPhone, cs.imagePath, cs.idCardNumber, cs.customerId, msg.type, msg.parentMsgId, msg.subject");
		sql.append(" ORDER BY msg.parentMsgId desc");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("msgType", new IntegerType()).addScalar("customerId", new LongType())
				.addScalar("parentMsgId", new LongType()).addScalar("emailAddress", new StringType()).addScalar("fullName", new StringType())
				.addScalar("cellPhone", new StringType()).addScalar("imagePath", new StringType()).addScalar("idCardNumber", new StringType())
				.addScalar("subject", new StringType());

		query.setParameter("typeId", typeId);
		query.setParameter("accountId", userProfile.getAccount().getId());

		query.setResultTransformer(Transformers.aliasToBean(Customer.class));

		return query.list();
	}

	@Override
	public List<MessageInboxBean> getMessageDetail(Long messageID, Long customerId) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ms.MESSAGEID AS messageID, ms.SUBJECT AS title, ms.content AS summary, ms.SUBJECT AS  ");
		sql.append(" 	relatedtopic, ms.CREATEDDATE AS DATETIME, NULL AS imgPathAdmin, ( ");
		sql.append(" 		CASE WHEN ( ");
		sql.append(" 					SELECT COUNT(*) ");
		sql.append(" 					FROM DOCUMENTINFO do ");
		sql.append(" 					WHERE do.REFERENCEID = ms.MESSAGEID ");
		sql.append(" 						AND do.DOCUMENTSOURCE = 1 ");
		sql.append(" 					) > 0 THEN 1 ELSE 0 END ");
		sql.append(" 		) AS hasAttachment, ( ");
		sql.append(" 		( ");
		sql.append(" 			SELECT COUNT(*) ");
		sql.append(" 			FROM MESSAGES mssub ");
		sql.append(" 			WHERE mssub.PARENTMSGID = ms.MESSAGEID ");
		sql.append(" 				AND CUSTOMERID = :customerId ");
		sql.append(" 				AND mssub.contractMsgId IS NULL ");
		sql.append(" 			) ");
		sql.append(" 		) AS numberOfReplies, MS.ISREAD AS isRead, ISMSGUSER AS ismsgUser, NULL AS fullName ");
		sql.append(" 	, ms.STATUS AS STATUS, ms.ACCOUNTID AS accountID, ms.CUSTOMERID AS customerID ");
		sql.append(" FROM MESSAGES ms ");
		sql.append(" WHERE ( ");
		sql.append(" 		ms.PARENTMSGID = ms.MESSAGEID ");
		sql.append(" 		OR ms.PARENTMSGID IS NULL ");
		sql.append(" 		) ");
		sql.append(" 	AND MESSAGEID = :messageID ");
		sql.append(" 	AND CUSTOMERID = :customerId ");
		sql.append(" 	AND contractMsgId IS NULL ");
		sql.append("  ");
		sql.append(" UNION ");
		sql.append("  ");
		sql.append(" SELECT ms.MESSAGEID AS messageID, ms.SUBJECT AS title, ms.content AS summary, ms.SUBJECT AS  ");
		sql.append(" 	relatedtopic, ms.CREATEDDATE AS DATETIME, ac.imagePath AS imgPathAdmin, ( ");
		sql.append(" 		CASE WHEN ( ");
		sql.append(" 					SELECT COUNT(*) ");
		sql.append(" 					FROM DOCUMENTINFO do ");
		sql.append(" 					WHERE do.REFERENCEID = ms.MESSAGEID ");
		sql.append(" 						AND do.DOCUMENTSOURCE = 1 ");
		sql.append(" 					) > 0 THEN 1 ELSE 0 END ");
		sql.append(" 		) AS hasAttachment, ( ");
		sql.append(" 		( ");
		sql.append(" 			SELECT COUNT(*) ");
		sql.append(" 			FROM MESSAGES mssub ");
		sql.append(" 			WHERE mssub.PARENTMSGID = ms.PARENTMSGID ");
		sql.append(" 				AND CUSTOMERID = :customerId ");
		sql.append(" 				AND mssub.contractMsgId IS NULL ");
		sql.append(" 			) ");
		sql.append(
				" 		) AS numberOfReplies, MS.ISREAD AS isRead, ISMSGUSER AS ismsgUser, (CASE WHEN ISMSGUSER = 0 THEN ac.FULLNAME ELSE NULL END ");
		sql.append(" 		) AS fullName, ms.STATUS AS STATUS, ms.ACCOUNTID AS accountID, ms.CUSTOMERID AS  ");
		sql.append(" 	customerID ");
		sql.append(" FROM MESSAGES ms ");
		sql.append(" LEFT JOIN ACCOUNT ac ON ac.ACCOUNTID = MS.ACCOUNTID ");
		sql.append(" WHERE MS.PARENTMSGID = :messageID ");
		sql.append(" 	AND CUSTOMERID = :customerId ");
		sql.append(" 	AND ms.contractMsgId IS NULL ");
		sql.append(" 	AND ms.PARENTMSGID <> ms.MESSAGEID ");
		sql.append("  ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("title", new StringType())
				.addScalar("summary", new StringType()).addScalar("relatedtopic", new StringType()).addScalar("hasAttachment", new BooleanType())
				.addScalar("datetime", new TimestampType()).addScalar("imgPathAdmin", new StringType())
				.addScalar("numberOfReplies", new IntegerType()).addScalar("ismsgUser", new StringType()).addScalar("fullName", new StringType())
				.addScalar("isRead", new BooleanType()).addScalar("status", new IntegerType()).addScalar("accountID", new LongType())
				.addScalar("customerID", new LongType());

		query.setParameter("messageID", messageID);
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(MessageInboxBean.class));
		@SuppressWarnings("unchecked")
		List<MessageInboxBean> messageInboxBeans = query.list();
		if (messageInboxBeans.size() == 0)
			return new ArrayList<MessageInboxBean>();
		return messageInboxBeans;
	}

	@Override
	public List<MessageInboxBean> getMessageDetailContract(Long messageID, Long customerId) {
		String sql = "SELECT ms.MESSAGEID as messageID, ms.SUBJECT as title, ms.content as summary, ms.SUBJECT as relatedtopic,"
				+ " ms.CREATEDDATE as datetime,null as imgPathAdmin,"
				+ "(CASE WHEN (SELECT COUNT(*) FROM DOCUMENTINFO do WHERE do.REFERENCEID = ms.MESSAGEID AND do.DOCUMENTSOURCE = 1) >0 THEN 1 ELSE 0 END) as hasAttachment,"
				+ "((SELECT COUNT(*) FROM MESSAGES mssub WHERE mssub.PARENTMSGID = ms.MESSAGEID AND CUSTOMERID=:customerId and mssub.contractMsgId is not null) )  as numberOfReplies, MS.ISREAD as isRead, ISMSGUSER as ismsgUser, null as fullName, ms.status as status, ms.ACCOUNTID as accountID, ms.CUSTOMERID as customerID  "
				+ " FROM MESSAGES ms "
				+ " WHERE (ms.PARENTMSGID = ms.MESSAGEID) AND MESSAGEID =:messageID  AND CUSTOMERID= :customerId  and contractMsgId is not null "
				+ "UNION " + "SELECT ms.MESSAGEID as messageID, ms.SUBJECT as title, ms.content as summary, ms.SUBJECT as relatedtopic,"
				+ "ms.CREATEDDATE as datetime,ac.imagePath as imgPathAdmin, "
				+ "(CASE WHEN (SELECT COUNT(*) FROM DOCUMENTINFO do WHERE do.REFERENCEID = ms.MESSAGEID AND do.DOCUMENTSOURCE = 1) >0 THEN 1 ELSE 0 END) as hasAttachment,"
				+ "((SELECT COUNT(*) FROM MESSAGES mssub WHERE mssub.PARENTMSGID = ms.PARENTMSGID  AND CUSTOMERID=:customerId and mssub.contractMsgId is not null ))  as numberOfReplies, MS.ISREAD as isRead, ISMSGUSER as ismsgUser,"
				+ "(CASE WHEN ISMSGUSER = 0 THEN ac.FULLNAME ELSE null END) as fullName, ms.status as status, ms.ACCOUNTID as accountID , ms.CUSTOMERID as customerID "
				+ "FROM MESSAGES ms " + "LEFT JOIN ACCOUNT  ac ON ac.ACCOUNTID = MS.ACCOUNTID "
				+ "WHERE MS.PARENTMSGID =:messageID AND CUSTOMERID= :customerId  and ms.contractMsgId is not null and ms.PARENTMSGID <> ms.MESSAGEID ";

		Query query = getSession().createSQLQuery(sql).addScalar("messageID", new LongType()).addScalar("title", new StringType())
				.addScalar("summary", new StringType()).addScalar("relatedtopic", new StringType()).addScalar("hasAttachment", new BooleanType())
				.addScalar("datetime", new TimestampType()).addScalar("imgPathAdmin", new StringType())
				.addScalar("numberOfReplies", new IntegerType()).addScalar("ismsgUser", new StringType()).addScalar("fullName", new StringType())
				.addScalar("isRead", new BooleanType()).addScalar("status", new IntegerType()).addScalar("accountID", new LongType())
				.addScalar("customerID", new LongType());

		query.setParameter("messageID", messageID);
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(MessageInboxBean.class));
		@SuppressWarnings("unchecked")
		List<MessageInboxBean> messageInboxBeans = query.list();
		if (messageInboxBeans.size() == 0)
			return new ArrayList<MessageInboxBean>();
		return messageInboxBeans;
	}

	@Override
	public List<MessageInboxBean> getListMessage(Long customerId) {
		String sql = "SELECT ms.MESSAGEID as messageID, ms.SUBJECT as title, ms.content as summary, ms.SUBJECT as relatedtopic,"
				+ " ms.CREATEDDATE as datetime, "
				+ " (CASE WHEN (SELECT COUNT(*) FROM DOCUMENTINFO do WHERE do.REFERENCEID = ms.MESSAGEID AND do.DOCUMENTSOURCE =:documentSource) >0 THEN 1 ELSE 0 END) as hasAttachment, "
				+ " (SELECT COUNT(*) FROM MESSAGES mssub WHERE mssub.PARENTMSGID = ms.MESSAGEID)  as numberOfReplies, MS.ISREAD as isRead, ms.status as status "
				+ " FROM MESSAGES ms "
				+ " WHERE (((ms.PARENTMSGID = ms.messageID OR ms.PARENTMSGID is null) and ms.ISMSGUSER = 1) OR (ms.PARENTMSGID = ms.messageID and ms.ISMSGUSER = 0 and (select count(*) FROM messages where PARENTMSGID =ms.messageID and ISMSGUSER = 1 ) > 1)) AND ms.CUSTOMERID = :customerId  and contractMsgId is null ";
		sql += " order by MESSAGEID desc ";
		Query query = getSession().createSQLQuery(sql).addScalar("messageID", new LongType()).addScalar("title", new StringType())
				.addScalar("summary", new StringType()).addScalar("relatedtopic", new StringType()).addScalar("datetime", new TimestampType())
				.addScalar("hasAttachment", new BooleanType()).addScalar("numberOfReplies", new IntegerType()).addScalar("isRead", new BooleanType())
				.addScalar("status", new IntegerType());

		query.setParameter("customerId", customerId);
		query.setParameter("documentSource", SystemConfig.MESSAGE_SOURCES);
		query.setResultTransformer(Transformers.aliasToBean(MessageInboxBean.class));
		List<MessageInboxBean> messageInboxBeans = query.list();
		if (messageInboxBeans != null && messageInboxBeans.size() > 0) {
			return messageInboxBeans;
		}
		return null;
	}

	@Override
	public List<MessageInboxBean> getListMessageContract(Long customerId, String contractMsgId) {
		String sql = "SELECT ms.MESSAGEID as messageID, ms.SUBJECT as title, ms.content as summary, ms.SUBJECT as relatedtopic,"
				+ " ms.CREATEDDATE as datetime, "
				+ " (CASE WHEN (SELECT COUNT(*) FROM DOCUMENTINFO do WHERE do.REFERENCEID = ms.MESSAGEID AND do.DOCUMENTSOURCE =:documentSource) >0 THEN 1 ELSE 0 END) as hasAttachment, "
				+ " (SELECT COUNT(*) FROM MESSAGES mssub WHERE mssub.PARENTMSGID = ms.MESSAGEID)  as numberOfReplies, MS.ISREAD as isRead, ms.status as status "
				+ " FROM MESSAGES ms "
				+ " WHERE (((ms.PARENTMSGID = ms.messageID OR ms.PARENTMSGID is null) and ms.ISMSGUSER = 1) OR (ms.PARENTMSGID = ms.messageID and ms.ISMSGUSER = 0 )) AND ms.CUSTOMERID = :customerId  and contractMsgId is not null and contractMsgId = :contractMsgId ";
		sql += " order by MESSAGEID desc ";
		Query query = getSession().createSQLQuery(sql).addScalar("messageID", new LongType()).addScalar("title", new StringType())
				.addScalar("summary", new StringType()).addScalar("relatedtopic", new StringType()).addScalar("datetime", new TimestampType())
				.addScalar("hasAttachment", new BooleanType()).addScalar("numberOfReplies", new IntegerType()).addScalar("isRead", new BooleanType())
				.addScalar("status", new IntegerType());

		query.setParameter("customerId", customerId);
		query.setParameter("contractMsgId", contractMsgId);
		query.setParameter("documentSource", SystemConfig.MESSAGE_SOURCES);
		query.setResultTransformer(Transformers.aliasToBean(MessageInboxBean.class));
		List<MessageInboxBean> messageInboxBeans = query.list();
		if (messageInboxBeans != null && messageInboxBeans.size() > 0) {
			return messageInboxBeans;
		}
		return null;
	}

	@Override
	public int assignAccountId(Long parentMsgId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE MESSAGES SET ACCOUNTID =:accountId, STATUS =:assign WHERE parentMsgId =:parentMsgId");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("assign", SystemConfig.MessageStatus.ASSIGNED.getIntValue());
		query.setParameter("parentMsgId", parentMsgId);
		query.setParameter("accountId", userProfile.getAccount().getId());
		return query.executeUpdate();
	}

	@Override
	public List<MessageDashBoard> checkMessageNew() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CUSTOMERID, PARENTMSGID, COUNT(MESSAGEID) AS commentsCount FROM MESSAGES ");
		sql.append(" WHERE ISREAD = 0 AND ISMSGUSER = 1 AND ACCOUNTID =:accountId and parentMsgId <> messageID GROUP BY CUSTOMERID, PARENTMSGID");
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("parentMsgId", new LongType()).addScalar("customerId", new LongType())
				.addScalar("commentsCount", new IntegerType());
		query.setParameter("accountId", userProfile.getAccount().getId());
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));
		return query.list();
	}

	@Override
	public int changeStatusMsg(int status, Long customerId, String reason, Long accountId, Long typeId, Long parentMsgId) {
		StringBuilder sql = new StringBuilder();

		if (status == 1) {
			sql.append(
					"UPDATE MESSAGES SET STATUS =:cancelling, REASON =:reason WHERE customerId =:customerId AND type =:typeId AND PARENTMSGID =:parentMsgId");
		}

		else if (status == 2) {
			sql.append(
					"UPDATE MESSAGES SET STATUS =:re_assign, REASON =:reason, ACCOUNTID =:accountId WHERE customerId =:customerId AND type =:typeId AND PARENTMSGID =:parentMsgId ");
		}

		else if (status == 3) {
			sql.append(
					"UPDATE MESSAGES SET STATUS =:closed, REASON =:reason WHERE customerId =:customerId AND type =:typeId AND PARENTMSGID =:parentMsgId");
		}

		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("customerId", customerId);
		query.setParameter("reason", reason);
		query.setParameter("typeId", typeId);
		if (status == 1) {
			query.setParameter("cancelling", SystemConfig.MessageStatus.MOVE_TO_CANCELLING_QUEUE.getIntValue());
			if (parentMsgId != null) {
				query.setParameter("parentMsgId", parentMsgId);
			}
		} else if (status == 2) {
			query.setParameter("re_assign", SystemConfig.MessageStatus.RE_ASSIGNED.getIntValue());
			if (accountId != null) {
				query.setParameter("accountId", accountId);
			}
			if (parentMsgId != null) {
				query.setParameter("parentMsgId", parentMsgId);
			}
		} else if (status == 3) {
			query.setParameter("closed", SystemConfig.MessageStatus.MOVE_TO_CLOSING_QUEUE.getIntValue());
			if (parentMsgId != null) {
				query.setParameter("parentMsgId", parentMsgId);
			}
		}
		return query.executeUpdate();

	}

	@Override
	public List<MessageDashBoard> getLstMessageByCustomerId(Long customerId, Long typeId, Long parentMsgId, Long accountId, Boolean dashboard,
			Long messageId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT msg.content, cs.fullName as customerName, msg.createdDate, ac.fullName as managerName, msg.parentMsgId, msg.messageID,");
		sql.append(" msg.subject, ac.email as managerEmail, ac.imagePath as managerImgPath, msg.isMsgUser, cs.imagePath as customerImgPath, ");
		sql.append(" di.fileName,cs.emailAddress as emailAddress, msg.contractMsgId");
		sql.append(" FROM Messages msg");
		sql.append(" LEFT JOIN Customer cs ON cs.customerId = msg.customerId");
		sql.append(" LEFT JOIN Account ac ON ac.accountId = msg.accountId");
		sql.append(" LEFT JOIN DocumentInfo di ON di.referenceId = msg.messageID");

		sql.append(" WHERE 1=1");
		if (customerId != null) {
			sql.append(" AND cs.customerId =:customerId");
		}
		if (parentMsgId != null) {
			if (parentMsgId == 0) {
				sql.append(" AND msg.messageId =:messageId");
			} else {
				sql.append(" AND (msg.parentMsgId =:parentMsgId OR msg.messageId = :parentMsgId )");
			}

		}
		if (typeId != null) {
			sql.append(" AND msg.type =:typeId");
		}
		if (dashboard) {
			if (accountId != null) {
				sql.append(" AND ac.accountId =:accountId");
			}
		} else {
			sql.append(" AND ac.accountId =:accountId");
		}

		sql.append(" ORDER BY msg.messageID desc");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("fileName", new StringType()).addScalar("messageID", new LongType())
				.addScalar("parentMsgId", new LongType()).addScalar("contractMsgId", new StringType()).addScalar("customerImgPath", new StringType())
				.addScalar("subject", new StringType()).addScalar("managerImgPath", new StringType()).addScalar("content", new StringType())
				.addScalar("customerName", new StringType()).addScalar("managerName", new StringType()).addScalar("managerEmail", new StringType())
				.addScalar("emailAddress", new StringType()).addScalar("createdDate", new TimestampType()).addScalar("isMsgUser", new IntegerType());
		if (customerId != null) {
			query.setParameter("customerId", customerId);
		}
		if (parentMsgId != null) {
			if (parentMsgId == 0) {
				query.setParameter("messageId", messageId);
			} else {
				query.setParameter("parentMsgId", parentMsgId);
			}
		}
		if (typeId != null) {
			query.setParameter("typeId", typeId);
		}
		if (dashboard) {
			if (accountId != null) {
				query.setParameter("accountId", accountId);
			}
		} else {
			query.setParameter("accountId", userProfile.getAccount().getId());
		}

		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		return query.list();
	}

	@Override
	public List<MessageDashBoard> findByCustomerIdNoCon(Long customerId) {

		StringBuilder sql = new StringBuilder();
		sql.append("  SELECT m.messageID as messageID, m.subject as subject, m.createdDate as createdDate, ");
		sql.append(" m.readDate as readDate, m.type as type, m.status as status");
		sql.append(" FROM Messages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId and m.contractMsgId is null");
		sql.append(" and (m.parentMsgId = m.messageID or isParent = 1) ");
		sql.append(" order by m.createdDate desc ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType())
				.addScalar("status", new IntegerType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		return query.list();

	}

	@Override
	public List<MessageDashBoard> findByCustomerIdConTract(Long customerId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT m.messageID as messageID, m.subject as subject, m.createdDate as createdDate, m.readDate as readDate, m.type as type, m.status as status");
		sql.append(" FROM Messages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId and m.contractMsgId is not null");
		sql.append(" order by m.createdDate desc ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType())
				.addScalar("status", new IntegerType());
		query.setParameter("customerId", customerId);
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		return query.list();
	}

	@Override
	public MessageDashBoard findParentCustomerIdConTract(Long customerId, String contractId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT m.messageID as messageID, m.subject as subject, m.createdDate as createdDate, m.readDate as readDate, m.type as type, m.status as status, m.ACCOUNTID as accountId");
		sql.append(" FROM Messages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId and m.contractMsgId =:contractId and m.parentMsgId = m.messageID");
		sql.append(" order by m.createdDate desc ");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType())
				.addScalar("status", new IntegerType()).addScalar("accountId", new LongType());
		query.setParameter("customerId", customerId);
		query.setParameter("contractId", contractId);
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));
		List<MessageDashBoard> messageDashBoards = query.list();

		if (messageDashBoards != null && messageDashBoards.size() > 0) {
			return messageDashBoards.get(0);
		}

		return null;
	}

	@Override
	public MessageDashBoard findParentCustomerIdparentMsgId(Long customerId, Long parentMsgId) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT m.messageID as messageID, m.subject as subject, m.createdDate as createdDate, m.readDate as readDate, m.type as type, m.status as status, m.ACCOUNTID as accountId");
		sql.append(" FROM Messages m ");
		sql.append(" JOIN Customer c on c.customerId=m.customerId");
		sql.append(" WHERE c.customerId = :customerId and m.parentMsgId =:parentMsgId and m.parentMsgId = m.messageID");

		Query query = getSession().createSQLQuery(sql.toString()).addScalar("messageID", new LongType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("readDate", new TimestampType()).addScalar("type", new IntegerType())
				.addScalar("status", new IntegerType()).addScalar("accountId", new LongType());
		query.setParameter("customerId", customerId);
		query.setParameter("parentMsgId", parentMsgId);
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));
		List<MessageDashBoard> messageDashBoards = query.list();

		if (messageDashBoards != null && messageDashBoards.size() > 0) {
			return messageDashBoards.get(0);
		}

		return null;
	}

	@Override
	public int changeStatusIsRead(Long parentMsgId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE MESSAGES SET ISREAD = 1 WHERE parentMsgId =:parentMsgId AND ISMSGUSER = 1 AND parentMsgId <> messageID");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("parentMsgId", parentMsgId);
		return query.executeUpdate();
	}

	@Override
	public void assignParentToMessageContract(String contractMsgId, Long parentMsgId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE MESSAGES SET parentMsgId =:parentMsgId WHERE contractMsgId =:contractMsgId");
		Query query = getSession().createSQLQuery(sql.toString());
		query.setParameter("parentMsgId", parentMsgId);
		query.setParameter("contractMsgId", contractMsgId);
		query.executeUpdate();

	}

	@Override
	public List<MessageDashBoard> findbyTypeOfUser() {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT distinct m.type as type");
		sql.append(" FROM Messages m ");
		sql.append(" WHERE ACCOUNTID =:accountId ");
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("type", new IntegerType());
		query.setParameter("accountId", userProfile.getAccount().getId());
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));
		return query.list();
	}

	@Override
	public MessageDashBoardBean searchImport(MessageDashBoardBean bean) {

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT mes.customerId AS customerId, mes.accountId, mes.messageID, cus.fullName,  ");
		sql.append(" cus.birthDay AS dob, cus.cellphone, mes.subject AS subject, mes.createdDate AS createdDate,  ");
		sql.append(" mes.type, mes.STATUS, mes.category, mes.subcategory, cus.imagePath AS customerImgPath,  ");
		sql.append(" mes.parentMsgId, mes.contractMsgId, mes.isRead AS isRead, mes.DATEIMPORT AS DATEIMPORT, mes.content AS content ");
		sql.append(" FROM messages mes ");
		sql.append(" INNER JOIN Customer cus ON cus.customerId = mes.customerId ");
		sql.append(" WHERE 1 = 1 ");
		sql.append(" 	AND (PARENTMSGID = messageID OR mes.ISPARENT = 1 ) ");
		sql.append(" 	AND mes.DATEIMPORT IS NOT NULL ");

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			sql.append(" and  lower(cus.fullName) like lower(:fullName) ");
		}
		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			sql.append(" and  lower(cus.cellPhone) like lower(:cellPhone) ");
		}

		if (StringUtils.isNotEmpty(bean.getSubject())) {
			sql.append(" and  lower(mes.subject) like lower(:subject) ");
		}

		if (StringUtils.isNotEmpty(bean.getStatus())) {
			sql.append(" and  mes.status =:status ");
		}
		if (StringUtils.isNotEmpty(bean.getIsRead())) {
			sql.append(" and  mes.isRead = :isRead ");
		}

		if (bean != null && bean.getToSendDate() != null) {
			sql.append(" and  mes.DATEIMPORT  <= :tosenddate ");
		}

		if (bean != null && bean.getFormSendDate() != null) {
			sql.append(" and  mes.DATEIMPORT >= :fromsenddate ");
		}
		if (StringUtils.isNotEmpty(bean.getMessageType())) {
			sql.append(" and  mes.type = :type ");
		}

		String sqlCount = "select count(*) from (" + sql.toString() + ")";
		sql.append(" order by mes.DATEIMPORT desc ");

		// build query get list
		Query query = getSession().createSQLQuery(sql.toString()).addScalar("customerImgPath", new StringType())
				.addScalar("contractMsgId", new StringType()).addScalar("customerId", new LongType()).addScalar("parentMsgId", new LongType())
				.addScalar("accountId", new LongType()).addScalar("messageID", new LongType()).addScalar("fullName", new StringType())
				.addScalar("dob", new TimestampType()).addScalar("cellphone", new StringType()).addScalar("subject", new StringType())
				.addScalar("createdDate", new TimestampType()).addScalar("type", new IntegerType()).addScalar("category", new IntegerType())
				.addScalar("subcategory", new IntegerType()).addScalar("status", new IntegerType()).addScalar("DATEIMPORT", new TimestampType())
				.addScalar("isRead", new IntegerType()).addScalar("content", new StringType());
		Query queryCount = getSession().createSQLQuery(sqlCount.toString());

		if (StringUtils.isNotEmpty(bean.getCustomerName())) {
			query.setParameter("fullName", "%" + bean.getCustomerName().trim() + "%");
			queryCount.setParameter("fullName", "%" + bean.getCustomerName().trim() + "%");
		}
		if (StringUtils.isNotEmpty(bean.getCellPhone())) {
			query.setParameter("cellPhone", "%" + bean.getCellPhone().trim() + "%");
			queryCount.setParameter("cellPhone", "%" + bean.getCellPhone().trim() + "%");
		}

		if (StringUtils.isNotEmpty(bean.getSubject())) {
			query.setParameter("subject", "%" + bean.getSubject().trim() + "%");
			queryCount.setParameter("subject", "%" + bean.getSubject().trim() + "%");
		}

		if (StringUtils.isNotEmpty(bean.getStatus())) {
			query.setParameter("status", bean.getStatus());
			queryCount.setParameter("status", bean.getStatus());
		}
		if (StringUtils.isNotEmpty(bean.getMessageType())) {
			query.setParameter("type", bean.getMessageType());
			queryCount.setParameter("type", bean.getMessageType());
		}

		if (bean != null && bean.getFormSendDate() != null) {
			query.setParameter("fromsenddate", Utils.setTimeToZero(bean.getFormSendDate()));
			queryCount.setParameter("fromsenddate", Utils.setTimeToZero(bean.getFormSendDate()));
		}

		if (bean != null && bean.getToSendDate() != null) {
			query.setParameter("tosenddate", Utils.setTimeToMax(bean.getToSendDate()));
			queryCount.setParameter("tosenddate", Utils.setTimeToMax(bean.getToSendDate()));
		}
		if (StringUtils.isNotEmpty(bean.getIsRead())) {
			query.setParameter("isRead", bean.getIsRead());
			queryCount.setParameter("isRead", bean.getIsRead());
		}
		// build query get count
		query.setResultTransformer(Transformers.aliasToBean(MessageDashBoard.class));

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = Integer.valueOf(queryCount.uniqueResult().toString());
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}

	@Override
	public void savePushMessage(MessageDashBoard item) {
		Session session = null;
		try {
			// Returns the Class object associated with the class
			Class.forName(systemConfig.getConfig(DRIVERCLASS));
			// Set connection timeout. Make sure you set this correctly as per
			// your need
			DriverManager.setLoginTimeout(5);

			// get Connection
			Connection conn = DriverManager.getConnection(systemConfig.getConfig(JDBCURL), systemConfig.getConfig(USER_KH),
					systemConfig.getConfig(PASSWORD_KH));
			if (conn == null) {
				throw new Exception("Error connection!");
			}

			// get Session
			SessionBuilder sb = getSessionFactory().withOptions();
			session = sb.connection(conn).openSession();

		} catch (Exception e) {
			e.printStackTrace();
		}

		// get customer
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT CUS.CUSTOMER_ID as customerId, CON.CONTRACT_NUMBER as contractNumber, CON.CUSTOMER_NAME as customerName"
				+ " FROM MOBILE_CUSTOMER cus join MOBILE_CONTRACT con on cus.CUSTOMER_ID=CON.CUSTOMER_ID");
		sql.append(" WHERE 1=1");
		if (!String.valueOf(item.getCustomer().getIdCardNumber()).isEmpty()) {
			sql.append(" AND cus.ID_CARD_NUMBER = :idCardNumber");
		}
		if (!String.valueOf(item.getCellphone()).isEmpty()) {
			sql.append(" AND cus.CELL_PHONE = :cellPhone");
		}

		Query query = session.createSQLQuery(sql.toString()).addScalar("customerId", new StringType()).addScalar("contractNumber", new StringType())
				.addScalar("customerName", new StringType());
		if (!String.valueOf(item.getCustomer().getIdCardNumber()).isEmpty()) {
			query.setParameter("idCardNumber", item.getCustomer().getIdCardNumber());
		}
		if (!String.valueOf(item.getCellphone()).isEmpty()) {
			query.setParameter("cellPhone", item.getCellphone());
		}
		Object[] ob = (Object[]) query.list().get(0);
		String customerId = ob[0].toString();
		String contractNumber = ob[1].toString();
		String customerName = ob[2].toString();
		// ob.getClass().getFields();
		// String customerId = ob[];
		// String customerId = query.list().get(0).toString();
		// String contractNumber = query.list().get(1).toString();

		// save message
		String hql = "insert into MOBILE_MESSAGE (CUSTOMER_ID, CONTRACT_NUMBER, CUSTOMER_FULL_NAME, CELL_PHONE, SUBJECT, CONTENT)" + " VALUES ('"
				+ customerId + "', '" + contractNumber + "', '" + customerName + "', '" + item.getCellphone() + "', '" + item.getSubject() + "', '"
				+ item.getContent() + "')";
		Query query1 = session.createSQLQuery(hql);
		// query1.setParameter("customerId", customerId);
		// query1.setParameter("cellPhone", item.getCellphone());
		// query1.setParameter("subject", item.getSubject());
		// query1.setParameter("content", item.getContent());
		query1.executeUpdate();
	}
}
