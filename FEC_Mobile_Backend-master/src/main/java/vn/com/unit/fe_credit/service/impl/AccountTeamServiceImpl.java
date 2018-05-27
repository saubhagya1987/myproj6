package vn.com.unit.fe_credit.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.AccountTeamDao;
import vn.com.unit.fe_credit.entity.AccountTeam;
import vn.com.unit.fe_credit.entity.AccountTeamPK;
import vn.com.unit.fe_credit.service.AccountTeamService;
@Service(value="accountTeamService")
@Transactional(readOnly=true)
public class AccountTeamServiceImpl implements AccountTeamService{
	@Autowired
	AccountTeamDao accountTeamDao;
	@Override
	public AccountTeam findById(Long accountId, Long teamId) {
		return accountTeamDao.find(new AccountTeamPK(accountId, teamId));
	}
	@Transactional
	@Override
	public void saveAccountTeam(AccountTeam entity) {
		// TODO Auto-generated method stub
		accountTeamDao.save(entity);
	}
	@Transactional
	@Override
	public void deleteAccountTeamByAccId(Long accountId) {
		accountTeamDao.deleteAccountTeamByAccId(accountId);
	}

}
