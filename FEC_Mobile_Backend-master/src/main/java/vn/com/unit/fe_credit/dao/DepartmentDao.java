package vn.com.unit.fe_credit.dao;


import java.util.List;
import java.util.Map;

import vn.com.unit.fe_credit.bean.DepartmentBean;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;

public interface DepartmentDao extends GenericDAO<Department, Long>{
	List<Department> searchByParent(Department dept);
	Map<Long, Department> searchByListAccount(List<Account> result);
	SearchResult<Long> searchRootAndTotal(DepartmentBean bean);
}
