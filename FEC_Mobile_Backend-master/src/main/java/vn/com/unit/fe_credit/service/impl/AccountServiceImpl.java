package vn.com.unit.fe_credit.service.impl;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.AccountDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.service.AccountService;

@Service("accountService")
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private UserProfile userProfile;
	public AccountServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public Account findByAccountName(String userName) {
		Search search = new Search(Account.class);
		search.addFilterEqual("username", userName);
		List<Account> result = accountDao.search(search);
		if (result.size() > 0)
			return result.get(0);
		return null;
	}
	
	@Override
	public boolean findByAccountNameCount(String userName) {
		Search search = new Search(Account.class);
		search.addFilterEqual("username", userName);
		 int result = accountDao.count(search);
		if (result > 0){
			return true;
		}
		return false;
	}

	@Override
	public Account findByAccountNameObj(String userName) {
		Search search = new Search();
		search.addField("id");
		search.addField("fullName");
		search.addField("username");
		search.addField("imagePath");
		search.addField("email");
		search.addFilterEqual("username", userName);
		Account account = new Account();
		if (userName != null) {
			List<Object> objects = accountDao.search(search);
			if (objects != null && objects.size() > 0) {
				Object[] objs = (Object[]) objects.get(0);
				account.setId((Long) objs[0]);
				account.setFullName((String) objs[1]);
				account.setUsername((String) objs[2]);
				account.setImagePath((String) objs[3]);
				account.setEmail((String) objs[4]);
			}
			return account;
		} else {
			return null;
		}

	}

	@Override
	@Transactional
	public void saveAccount(Account account) {
		if(account.getId() == null){
			account.setCreated_date(new Date());
			if(userProfile.getAccount() != null)
			account.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			account.setUpdate_date(new Date());
			if(userProfile.getAccount() != null)
			account.setUpdate_by(userProfile.getAccount().getUsername());
		}
		accountDao.save(account);
	}

	@Override
	@Transactional
	public void updateAccountTeam(Long accountID, Long teamID) {
		accountDao.updateAccountTeam(accountID, teamID);
	}

	@Override
	public List<Account> findAccounts(String name) {
		Search search = new Search();
		search.addFilterILike("fullName", "%" + name + "%");
		return accountDao.search(search);
	}

	@Override
	public Account findById(Long id) {
		return accountDao.find(id);
	}

	@Override
	public List<Object[]> search(AccountBean bean) {
		List<Object[]> searchResult = accountDao.search(bean);
//		bean.setTotal(searchResult.getTotalCount());
//		bean.setListResult(searchResult.getResult());
		return searchResult;
	}
	@Override
	public Integer countSearch(AccountBean bean){
		return accountDao.countSearch(bean);
	}

	@Override
	public void merge(Account account) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Account account) {

	}

	@Override
	public List<Account> findAccountByName(String name) {
		Search search = new Search(Account.class);
		if (StringUtils.isNotEmpty(name))
			search.addFilterOr(Filter.ilike("username", "%" + name + "%"), Filter.ilike("fullName", "%" + name + "%"));

		return accountDao.search(search);
	}

	@Override
	public List<Account> findAccountByLstName(String currentUser, String lstUsername) {
		Search search = new Search(Account.class);
		if (StringUtils.isNotEmpty(lstUsername)) {
			String[] arr = lstUsername.split(";");
			List<String> lst = new ArrayList<String>();
			for (String item : arr) {
				if (StringUtils.isNotEmpty(item) && item.equals(currentUser) == false) {
					lst.add(item);
				}
			}
			if (lst.size() > 0) {
				search.addFilterIn("username", lst);
				return accountDao.search(search);
			}
		}

		return null;
	}

	@Override
	public List<Account> findAccountByLstName(List<String> lstUserName) {
		Search search = new Search(Account.class);
		if (lstUserName != null && lstUserName.size() > 0) {
			search.addFilterIn("username", lstUserName);
			return accountDao.search(search);
		}

		return null;
	}

	@Override
	public List<Account> findAccountByLstName(List<String> lstUserName, String notIncludeUser) {
		Search search = new Search(Account.class);
		if (lstUserName != null && lstUserName.size() > 0) {
			search.addFilterIn("username", lstUserName);
			search.addFilterNotEqual("username", notIncludeUser);
			return accountDao.search(search);
		}

		return null;
	}

	// search account theo 1 role cu the
	@Override
	public List<Account> findAccountsByRole(String code) {
		return accountDao.findAccountsByRole(code, false);
	}

	//
	// @Override
	// public Map<Long, Account> searchByListDepartment(List<Department> result)
	// {
	// return accountDao.searchByListDepartment(result);
	// }

	@Override
	public List<Account> findAccountsByRole(String code, boolean excludeCurrentUser) {
		return accountDao.findAccountsByRole(code, excludeCurrentUser);
	}

	@Override
	@Transactional
	public List<Account> findAccountByDepartment(Long departmentId) {
		return accountDao.findAccountByDepartment(departmentId);
	}

	@Override
	@Transactional
	public List<Account> findAccountByBranch(Long branchId) {
		return accountDao.findAccountByBranch(branchId);
	}

	@Override
	public List<Account> findAccountBylstBranch(String lstbranchId) {
		if (StringUtils.isNotEmpty(lstbranchId)) {
			return accountDao.findAccountBylstBranch(lstbranchId);
		} else {
			return null;
		}
	}

	@Override
	public List<Account> findAll() {
		Search search = new Search();
		search.addField("id");
		search.addField("fullName");
		search.addField("username");
		search.addField("imagePath");
		search.addField("email");
		List<Account> result = new ArrayList<Account>();
		List<Object> objects = accountDao.search(search);
		if (objects != null) {
			for (Object object : objects) {
				Object[] objs = (Object[]) object;
				Account account = new Account();
				account.setId((Long) objs[0]);
				account.setFullName((String) objs[1]);
				account.setUsername((String) objs[2]);
				account.setImagePath((String) objs[3]);
				account.setEmail((String) objs[4]);
				result.add(account);
			}
			return result;
		} else {
			return null;
		}
	}

	@Override
	public List<Account> findAccounts(Long branchId) {
		if (branchId == null || 0 == branchId) {
			return findAll();
		} else {
			return findAccountByBranch(branchId);
		}
	}

	@Override
	public List<Account> findDeptIdRoleID(Long departmentId, String roleCode) {
		// TODO Auto-generated method stub
		return accountDao.findDeptIdRoleID(departmentId, roleCode);
	}

	@Override
	public List<Account> findAccountByBranchs(Long... branchIds) {
		return accountDao.findAccountByBranchs(branchIds);
	}

	@Override
	public Account findDeptIdRoleIDAccountId(String roleCode, Long accountId) {
		return accountDao.findDeptIdRoleIDAccountId(roleCode, accountId);
	}

	@Override
	@Transactional
	public List<Long> findByAccountNameOrFullName(String searchCondition) {
		Search search = new Search(Account.class);
		search.addFilterOr(Filter.ilike("username", "%" + searchCondition + "%"),
				Filter.ilike("fullName", "%" + searchCondition + "%"));
		List<Account> result = accountDao.search(search);
		List<Long> lstId = new ArrayList<Long>();
		for (Account ob : result) {
			lstId.add(ob.getId());
		}
		return lstId;
	}

	@Override
	public List<Account> finAllNotCurrentUser(String currentUser) {
		Search search = new Search(Account.class);
		if (StringUtils.isNotEmpty(currentUser)) {
			search.addFilterNotIn("username", currentUser);
		}
		List<Account> result = accountDao.search(search);
		return result;
	}



	@Override
	public List<Account> findAllObj() {
		Search search = new Search();
		search.addField("id");
		search.addField("fullName");
		search.addField("username");
		search.addField("imagePath");
		search.addField("email");
		List<Account> result = new ArrayList<Account>();
		List<Object> objects = accountDao.search(search);
		if (objects != null) {
			for (Object object : objects) {
				Object[] objs = (Object[]) object;
				Account account = new Account();
				account.setId((Long) objs[0]);
				account.setFullName((String) objs[1]);
				account.setUsername((String) objs[2]);
				account.setImagePath((String) objs[3]);
				account.setEmail((String) objs[4]);
				result.add(account);
			}
			return result;
		} else {
			return null;
		}

	}

	@Override
	public List<Account> finAllAccountSameStock(Long stockId) {
		Search search = new Search();
		if (stockId != null)
			search.addFilterEqual("stock.stockId", stockId);

		return accountDao.search(search);
	}
	
	
	@Override
	public List<Account> findAccountStockProjects(Long stockId) {
		Search search = new Search(Account.class);
		if (stockId != null) {
			search.addFilterIn("stockId", stockId);
		}
		/*if(projectId!=null){
			search.addFilterIn("projectId", projectId);
		}	*/	
		return accountDao.search(search);

	}

	@Override
	public List<Account> findAllAccount() {
		return accountDao.findAll();
	}

	@Override
	public AccountBean searchForm(AccountBean bean) {
		Search search = new Search(Account.class);
		if(bean.getStatus()==-1){
			
		}
		else{
			search.addFilterEqual("statusTable.status_tableId", bean.getStatus());
		}
		
		search.addFilterOr(Filter.ilike("username", "%" + bean.getEntity().getUsername().trim() + "%"));
		search.addFilterOr(Filter.ilike("email", "%" + bean.getEntity().getEmail().trim() + "%"));
		search.addFilterOr(Filter.ilike("fullName", "%" + bean.getEntity().getFullName().trim() + "%"));
		search.addFilterOr(Filter.ilike("birthday", "%" + bean.getEntity().getBirthday() + "%"));
//		search.addFilterEqual("statusTable.status_tableId", value);
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),"desc".equalsIgnoreCase(bean.getSort()));
		}
		SearchResult<Account> searchResult = accountDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}


}
