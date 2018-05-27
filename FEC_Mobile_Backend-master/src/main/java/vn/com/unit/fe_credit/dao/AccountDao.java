package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.entity.Account;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;



public interface AccountDao extends GenericDAO<Account, Long>{
	public Account findByUserName(String userName);
	public void updateAccountTeam(Long AccountID, Long TeamID);
	public List<Account> findAccountsByRole(String code, boolean excludeCurrentUser);
	public List<Account> findAccountByDepartment(Long departmentId);
	List<Account> findAccountByBranch(Long branchId);
	List<Account> findAccountBylstBranch(String lstbranchId);
	List<Account> findDeptIdRoleID(Long departmentId, String roleCode);
	List<Account> findAccountByBranchs(Long... branchIds);
	Account findDeptIdRoleIDAccountId(String roleCode,Long accountId);
	Account findByUserNameForLogin(String userName);
	List<Object[]> search(AccountBean bean);
	Integer countSearch(AccountBean bean);
	public List<Account> findByEmail(String email);
	public List<Account> findByUserCode(String userCode);
	public int unlockUser(String email);
	public int clearDevice(String email,String appId);
}
