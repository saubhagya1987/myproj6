package vn.com.unit.fe_credit.dao.impl;


import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.persistence.NoResultException;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.AccountTeamDao;
import vn.com.unit.fe_credit.entity.AccountTeam;
import vn.com.unit.fe_credit.entity.AccountTeamPK;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
@Repository
public class AccountTeamDaoImpl extends GenericDAOImpl<AccountTeam, AccountTeamPK> implements AccountTeamDao{
	
	private static final String FS_CODE = "fs.Code";
	private static final String CA_CODE = "ca.Code";
	
	@Autowired
	MessageSource msgSrc;
	
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void deleteAccountTeamByAccId(Long accountId) {
		// TODO Auto-generated method stub
		String hql="delete from AccountTeam accountTeam  where accountTeam.pk.accountId=:accountId";
		Query query = getSession().createQuery(hql);
		query.setParameter("accountId", accountId);
		query.executeUpdate();
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Transactional("transactionManager")
	@Override
	 public List<Object> getFSCAEmails() {
	  // TODO Auto-generated method stub
	  try {
	   //Session session = SessionFactoryCMS.getSessionFactory().openSession();
	   String queryString = "SELECT EMAIL FROM ACCOUNT acc inner join ACCOUNTTEAM at on acc.ACCOUNTID=at.ACCOUNTID inner join TEAMROLE tr on tr.TEAMID=at.TEAMID inner join ROLE role on tr.ROLEID=role.ROLEID WHERE role.CODE=:fsCode OR role.CODE=:caCode";
	   Query query = getSession().createSQLQuery(queryString).addScalar("EMAIL", new StringType())/*.addScalar("ACCOUNTID", new IntegerType())*/;
	   
	   query.setParameter("fsCode", msgSrc.getMessage(FS_CODE, null, Locale.getDefault()));
	   query.setParameter("caCode", msgSrc.getMessage(CA_CODE, null, Locale.getDefault()));
	   List<Object> emails = query.list();   
	   return emails;
	  } catch (NoResultException ex) {
	   return null;
	  } catch (Exception e) {	   
	   throw new RuntimeException(e);
	  }
	 }
	
	@Override
	@Transactional("transactionManager")
	public List<Object> getEmailIdByMail(String nextRole) {		  
		 List<Object> emails =null;  
		try {				
			 if(nextRole == null || nextRole== null)
				 return Collections.emptyList();
						 
		   String queryString = "SELECT EMAIL FROM ACCOUNT acc inner join ACCOUNTTEAM at on acc.ACCOUNTID=at.ACCOUNTID inner join TEAMROLE tr on tr.TEAMID=at.TEAMID inner join ROLE role on tr.ROLEID=role.ROLEID WHERE role.CODE= :role";
		   Query query = getSession().createSQLQuery(queryString).addScalar("EMAIL", new StringType());
		   
		   query.setParameter("role", nextRole );		   
		      emails = query.list();  		  
			 
			 return emails;
		  } catch (NoResultException ex) {
		   return null;
		  } catch (Exception e) {	   
		   throw new RuntimeException(e);
		  }
		 
	}
}
