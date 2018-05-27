package vn.com.unit.fe_credit.dao.impl;

import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DateType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.dao.AccountDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.service.impl.v1.UserServiceImpl;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;

@Repository
public class AccountDaoImpl extends GenericDAOImpl<Account, Long> implements
		AccountDao {
	// @Autowired
	// UserProfile userProfile;
	Logger logger = Logger.getLogger(AccountDaoImpl.class.getName());
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void updateAccountTeam(Long AccountID, Long teamID) {
		String hql = "delete from Account account  where account.id=:accountID";
		Query query = getSession().createQuery(hql);
		query.setParameter("accountId", AccountID);
		query.executeUpdate();
		hql = "insert into Team team inner join team.accounts act(team.id, act.id)"
				+ "select team.id, account.id from Team team, Account account where team.id=:teamID and account.id=:accountID ";
		Query query1 = getSession().createQuery(hql);
		query1.setParameter("accountId", AccountID);
		query1.setParameter("teamID", teamID);
		query1.executeUpdate();
	}

	@Override
	public Account findByUserName(String userName) {
		// TODO Auto-generated method stub
		List<Account> results = search(new Search().addFilterEqual("username",
				userName).addFilterEqual("ldap", true));
		if (results.size() > 0)
			return results.get(0);
		return null;
	}

	@Override
	public Account findByUserNameForLogin(String userName) {
		List<Account> results = search(new Search().addFilterEqual("username", userName).addFilterEqual("statusTable.status_tableId", 1));
		if (CollectionUtils.isNotEmpty(results)) {
			return results.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Account> findAccountsByRole(String code,
			boolean excludeCurrentUser) {
		String hql = " "
				+ "select distinct acc from Account acc inner join acc.teams team "
				+ "inner join team.roles role " + "where role.code=:code ";
		if (excludeCurrentUser)
			hql += " and acc.id!=:currentuser";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		// if(excludeCurrentUser) query.setParameter("currentuser",
		// userProfile.getAccount().getId());
		return query.list();
	}

	@Override
	public List<Account> findAccountByDepartment(Long departmentId) {
		String sql = "select acc.username as username, acc.fullName as fullName, acc.email as email "
				+ "from  Account acc "
				+ "  where a.userName = acc.username and a.departmentId = :departmentId";

		Query query = getSession().createQuery(sql);
		query.setParameter("departmentId", departmentId);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		List<Account> list = query.list();

		return list;
	}

	@Override
	public List<Account> findAccountByBranch(Long branchId) {
		String sql = "select acc.username as username, acc.fullName as fullName, acc.email as email, acc.id as id "
				+ "from  Account acc " + "  where a.userName = acc.username";

		Query query = getSession().createQuery(sql);
		query.setParameter("branchId", branchId);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		List<Account> list = query.list();

		return list;
	}

	@Override
	public List<Account> findAccountBylstBranch(String lstbranchId) {
		String sql = "select acc.username as username, acc.fullName as fullName, acc.email as email, acc.id as id "
				+ "from Account acc " + "  where 1=1  ";
		String[] lst = lstbranchId.split(";");
		if (lst != null && lst.length > 0) {
			sql += "and acc.location.branchId IN (";
			for (int i = 0; i < lst.length; i++) {
				if (i == lst.length - 1) {
					sql += lst[i];
				} else {
					sql += lst[i] + ",";
				}
			}
			sql += ")";
		}

		Query query = getSession().createQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		List<Account> list = query.list();

		return list;
	}

	@Override
	public List<Account> findAccountByBranchs(Long... branchIds) {

		if (ArrayUtils.isEmpty(branchIds)) {
			return null;
		}

		String sql = "SELECT acc.username AS username, acc.fullName AS fullName, acc.email AS email, acc.id AS id "
				+ " FROM Account acc "
				+ " WHERE acc.location.branchId in ( "
				+ StringUtils.join(branchIds, ",") + " )";
		Query query = getSession().createQuery(sql);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		return query.list();

	}

	@Override
	public List<Account> findDeptIdRoleID(Long departmentId, String roleCode) {
		String hql = " "
				+ " select distinct acc from Account acc inner join acc.teams team "
				+ " inner join team.roles role  " + " where role.code=:code "
				+ " and acss.userName = acc.username ";
		if (departmentId != null) {
			hql += " and acc.departmentId = :departmentId ";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter("code", roleCode);
		if (departmentId != null) {
			query.setParameter("departmentId", departmentId);
		}
		return query.list();
	}

	@Override
	public Account findDeptIdRoleIDAccountId(String roleCode, Long accountId) {
		String hql = " "
				+ " select distinct acc from Account acc inner join acc.teams team "
				+ " inner join team.roles role " + " where role.code=:code ";
		if (accountId != null) {
			hql += " and acc.id=:accountId";
		}
		Query query = getSession().createQuery(hql);
		query.setParameter("code", roleCode);
		if (accountId != null) {
			query.setParameter("accountId", accountId);
		}
		List<Account> list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Object[]> search(AccountBean bean) {
		Account account = bean.getEntity();
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT acc.AccountId AS Id, acc.email AS email, acc.username AS username, acc.mobile AS mobile, acc.birthday AS birthday, acc.fullName AS fullName, acc.ldap AS ldap,st.status_text AS status_text, acc.LINE_MANAGER AS lineManager, acc.USER_CODE AS userCode,acc.REGION_CODE AS regionCode, acc.PROVINCE_CODE AS provinceCode");
		hql.append(" FROM Account acc INNER JOIN STATUS_TABLE st ON  st.STATUSID = acc.STATUSID");

		hql.append(" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(account.getUsername())) {
			hql.append(" AND acc.username LIKE :username");
		}
		if (StringUtils.isNotEmpty(account.getFullName())) {
			hql.append(" AND acc.fullName LIKE :fullName");
		}
		if (StringUtils.isNotEmpty(account.getEmail())) {
			hql.append(" AND acc.email LIKE :email");
		}
		if (StringUtils.isNotEmpty(account.getMobile())) {
			hql.append(" AND acc.mobile LIKE :mobile");
		}
		if (account.getBirthday() != null) {
			hql.append(" AND acc.birthday = :birthday");
		}
		if (StringUtils.isNotEmpty(account.getLineManager())) {
			hql.append(" AND acc.LINE_MANAGER LIKE :lineManager");
		}
		if (StringUtils.isNotEmpty(account.getUserCode())) {
			hql.append(" AND acc.USER_CODE LIKE :userCode");
		}
		if (StringUtils.isNotEmpty(account.getRegionCode() )) {
			hql.append(" AND acc.REGION_CODE LIKE :regionCode");
		}
		if (StringUtils.isNotEmpty(account.getProvinceCode())) {
			hql.append(" AND acc.PROVINCE_CODE LIKE :provinceCode");
		}
		if (bean.getStatus() == -1) {

		} else {
			hql.append(" AND acc.STATUSID like :status_tableId");
		}

		hql.append(" ORDER BY acc.AccountId");
		logger.info("HQL  :: "+hql );
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("Id", new LongType())
				.addScalar("email", new StringType())
				.addScalar("username", new StringType())
				.addScalar("mobile", new StringType())
				.addScalar("birthday", new DateType())
				.addScalar("fullName", new StringType())
				.addScalar("ldap", new BooleanType())
				.addScalar("status_text", new StringType())
				.addScalar("lineManager", new StringType())
				.addScalar("userCode", new StringType())
				.addScalar("regionCode", new StringType())
				.addScalar("provinceCode", new StringType());
		if (StringUtils.isNotEmpty(account.getUsername())) {
			query.setParameter("username", "%" + account.getUsername() + "%");
		}
		if (StringUtils.isNotEmpty(account.getFullName())) {
			query.setParameter("fullName", "%" + account.getFullName() + "%");
		}
		if (StringUtils.isNotEmpty(account.getEmail())) {
			query.setParameter("email", "%" + account.getEmail() + "%");
		}
		if (StringUtils.isNotEmpty(account.getMobile())) {
			query.setParameter("mobile", "%" + account.getMobile() + "%");
		}
		if (StringUtils.isNotEmpty(account.getUserCode())) {
			query.setParameter("userCode", "%" + account.getUserCode() + "%");
		}
		if (account.getBirthday() != null) {
			query.setParameter("birthday", account.getBirthday());
		}
		if (bean.getStatus() == -1) {
			
		} else {
			query.setParameter("status_tableId", bean.getStatus());
		}
		if (bean.getLimit() > 0) {
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}
		logger.info("Query  :  " + query.toString());
		List<Object[]> lst = query.list();

		return lst;
	}

	@Override
	public Integer countSearch(AccountBean bean) {
		Account account = bean.getEntity();
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*) FROM (");
		hql.append(" SELECT acc.AccountId AS Id, acc.email AS email, acc.username AS username, acc.mobile AS mobile, acc.birthday AS birthday, acc.fullName AS fullName, acc.ldap AS ldap,st.status_text AS status_text");
		hql.append(" FROM Account acc INNER JOIN STATUS_TABLE st ON  st.STATUSID = acc.STATUSID");

		hql.append(" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(account.getUsername())) {
			hql.append(" AND acc.username LIKE :username");
		}
		if (StringUtils.isNotEmpty(account.getFullName())) {
			hql.append(" AND acc.fullName LIKE :fullName");
		}
		if (StringUtils.isNotEmpty(account.getEmail())) {
			hql.append(" AND acc.email LIKE :email");
		}
		if (StringUtils.isNotEmpty(account.getMobile())) {
			hql.append(" AND acc.mobile LIKE :mobile");
		}
		if (account.getBirthday() != null) {
			hql.append(" AND acc.birthday = :birthday");
		}
		if (bean.getStatus() == -1) {

		} else {
			hql.append(" AND acc.STATUSID = :status_tableId");
		}

		hql.append(" ORDER BY acc.AccountId");
		hql.append(" ) ");
		Query query = getSession().createSQLQuery(hql.toString());
		if (StringUtils.isNotEmpty(account.getUsername())) {
			query.setParameter("username", "%" + account.getUsername() + "%");
		}
		if (StringUtils.isNotEmpty(account.getFullName())) {
			query.setParameter("fullName", "%" + account.getFullName() + "%");
		}
		if (StringUtils.isNotEmpty(account.getEmail())) {
			query.setParameter("email", "%" + account.getEmail() + "%");
		}
		if (StringUtils.isNotEmpty(account.getMobile())) {
			query.setParameter("mobile", "%" + account.getMobile() + "%");
		}
		if (account.getBirthday() != null) {
			query.setParameter("birthday", account.getBirthday());
		}
		if (bean.getStatus() == -1) {
			
		} else {
			query.setParameter("status_tableId", bean.getStatus());
		}
		query.uniqueResult().toString();
		Integer a = 0;
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;
	}

	@Override
	public List<Account> findByEmail(String email) {
		// TODO Auto-generated method stub

		String sql = "select acc.username as username, acc.fullName as fullName, acc.email as email "
				+ "from  Account acc "
				+ "  where acc.email = :email";

		Query query = getSession().createQuery(sql);
		query.setParameter("email", email);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		List<Account> list = query.list();

		return list;
	
	}
	public List<Account> findByUserCode(String userCode){
		// TODO Auto-generated method stub
		String sql = "select acc.username as username, acc.fullName as fullName, acc.email as email, acc.userCode as userCode "
				+ "from  Account acc "
				+ "  where acc.userCode = :userCode";
		Query query = getSession().createQuery(sql);
		query.setParameter("userCode", userCode);
		query.setResultTransformer(Transformers.aliasToBean(Account.class));
		List<Account> list = query.list();
		return list;	
	}

	@Override
	public int unlockUser(String email){
		String sql = "UPDATE Account SET attempts = 0 WHERE EMAIL =:email";
		Query query = getSession().createQuery(sql);
		query.setParameter("email", email);
		int result = query.executeUpdate();
		return result;
	}
	
	@Override
	public int clearDevice(String email,String appId){
		String sql = "delete from user_device where email_id=:email AND APPLICATION_ID =:appId";
		Query query = getSession().createSQLQuery(sql);
		query.setParameter("email", email);
		query.setParameter("appId", appId);
		int result = query.executeUpdate();
		return result;
	}
	
}
