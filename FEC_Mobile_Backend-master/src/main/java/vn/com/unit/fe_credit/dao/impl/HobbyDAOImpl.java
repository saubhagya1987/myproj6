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

import vn.com.unit.fe_credit.bean.MessageInboxBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.HobbyDAO;
import vn.com.unit.fe_credit.entity.Hobby;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class HobbyDAOImpl extends GenericDAOImpl<Hobby, Long> implements
		HobbyDAO {

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public Hobby findByHobbyCode(String HobbyCode) {
		String hql = "from HOBBY" + " where HOBBY.CODE = :CODEIN ";
		Query query = getSession().createQuery(hql);
		query.setParameter("CODEIN", HobbyCode);
		return (Hobby) query.uniqueResult();
	}

	@Override
	public List<Hobby> listHobbyByCustomer(Long customerId) {
		// TODO Auto-generated method stub
		String sql = "select hp.CODE as code, hp.DESCRIPTION as description,hp.HOBBYID as hobbyId, hp.NAME as name "
				+ ", hp.STATUS as status, (CASE WHEN (SELECT COUNT(*) "
				+ " FROM CUSTOMERHOBBY cu where cu.CUSTOMERID="
				+ customerId
				+ " and cu.HOBBYID=hp.HOBBYID ) >0 THEN 1 ELSE 0 END)  as activity"
				+ "  from HOBBY hp  where STATUS = 'Activated' ";

		Query query = getSession().createSQLQuery(sql)
				.addScalar("hobbyId", new LongType())
				.addScalar("code", new StringType())
				.addScalar("description", new StringType())
				.addScalar("name", new StringType())
				.addScalar("status", new StringType())
				.addScalar("activity", new LongType());

		query.setResultTransformer(Transformers.aliasToBean(Hobby.class));

		return query.list();
	}
}
