package vn.com.unit.fe_credit.service;


import vn.com.unit.fe_credit.entity.AccountTeam;

public interface AccountTeamService {
	public AccountTeam findById(Long accountId,Long teamId);
	void saveAccountTeam(AccountTeam entity);
	void deleteAccountTeamByAccId(Long accountId);
}
