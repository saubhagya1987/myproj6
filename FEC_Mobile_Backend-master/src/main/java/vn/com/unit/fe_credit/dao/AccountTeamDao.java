package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.entity.AccountTeam;
import vn.com.unit.fe_credit.entity.AccountTeamPK;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface AccountTeamDao extends GenericDAO<AccountTeam, AccountTeamPK>{
	public void deleteAccountTeamByAccId(Long accountId) ;

	public List<Object> getFSCAEmails();
	public List<Object> getEmailIdByMail(String nextRole);
}
