package vn.com.unit.fe_credit.service;


import java.util.List;

import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.bean.CMSBean;
import vn.com.unit.fe_credit.entity.Account;



public interface AccountService {
	Account findById(Long id);

	// Account findTeamNotInAccountId(Long id);
	Account findByAccountName(String userName);
	
	Account findByAccountNameObj(String userName);

	void saveAccount(Account account);
	AccountBean searchForm(AccountBean bean);

	List<Account> findAccounts(String name);

	List<Account> findAccountsByRole(String code);

	List<Account> findAccountsByRole(String code, boolean excludeCurrentUser);

	void merge(Account account);

	void remove(Account account);

	List<Object[]> search(AccountBean bean);
	
	Integer countSearch(AccountBean bean);

	void updateAccountTeam(Long accountID, Long teamID);

	List<Account> findAccountByName(String name);


	public List<Account> findAccountByDepartment(Long departmentId);

	List<Account> findAccountByBranch(Long branchId);
	List<Account> findAccountBylstBranch(String lstbranchId);

	List<Account> findAll();
	
	List<Account> findAllObj();
	
	List<Account> findAccounts(Long branchId);
	
	List<Account> findDeptIdRoleID(Long departmentId, String roleCode);
	
	List<Account> findAccountByLstName(String currentUser, String lstUsername);
	
	List<Account> findAccountByLstName(List<String> lstUserName);
	
	List<Account> findAccountByLstName(List<String> lstUserName, String notIncludeUser);

	List<Account> findAccountByBranchs(Long... branchIds);
	

	Account findDeptIdRoleIDAccountId(String roleCode, Long accountId);
	
	List<Long> findByAccountNameOrFullName(String searchCondition);

	List<Account> finAllNotCurrentUser(String currentUser);

	List<Account> finAllAccountSameStock(Long stockId);

	List<Account> findAccountStockProjects(Long stockId);

	boolean findByAccountNameCount(String userName);

	List<Account> findAllAccount();
	
}
