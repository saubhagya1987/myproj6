package vn.com.unit.fe_credit.dao.impl;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.DepartmentBean;
import vn.com.unit.fe_credit.dao.DepartmentDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Repository
public class DepartmentDaoImpl extends GenericDAOImpl<Department, Long> implements DepartmentDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Department> searchByParent(Department dept) {
		Search search =new Search(Department.class);
		if(dept==null) search.addFilterNull("parent");
		else search.addFilterEqual("parent", dept);
		return this.search(search);
	}	
	@Override
	public Map<Long, Department> searchByListAccount(List<Account> accounts) {
		String hql="select st,acc from AccountSetting st,Account  acc  where acc in (:accounts) and st.userName=acc.username";
		Query query = getSession().createQuery(hql);
		if(accounts.size()>0 && accounts!=null){
		query.setParameterList("accounts", accounts);
		}else
		{
			query.setParameter("accounts",null);
		}
		List<Object[]> result=query.list();
		Map<Long, Department> departments=new HashMap<Long, Department>();
		for(Object[] item:result){
			Long id = ((Account)item[1]).getId();
//			departments.put(id, ((AccountSetting)item[0]).getDepts());
		}
		return departments;
	}

	@Override
	public SearchResult<Long> searchRootAndTotal(DepartmentBean departmentBean){
		String sql="select distinct dept.rootId from Department dept left join dept.departments p ";
		if(StringUtils.isNotEmpty(departmentBean.getSearchContent()))  sql+=" where lower(dept.name) like lower(:name) or lower(p.name) like lower(:parentname)"			
				+ " or lower(dept.note) like lower(:note)   "
				+ " or lower(dept.departmentCode)like lower(:departmentCode)";
		Query query=this.getSession().createQuery(sql);
		if(StringUtils.isNotEmpty(departmentBean.getSearchContent())){
			
			query.setParameter("name", "%"+departmentBean.getSearchContent()+"%");
			query.setParameter("parentname", "%"+departmentBean.getSearchContent()+"%");
			query.setParameter("note", "%"+departmentBean.getSearchContent()+"%");
			query.setParameter("departmentCode", "%"+departmentBean.getSearchContent()+"%");
		}
		query.setMaxResults(departmentBean.getLimit());
		query.setFirstResult(departmentBean.getLimit()*(departmentBean.getPage()-1));
		List<Long> rootId=query.list();
		
		if(rootId.size()==0) {
			SearchResult<Long> searchResult= new SearchResult<Long>();
			searchResult.setResult(new ArrayList<Long>());
			searchResult.setTotalCount(0);
			return searchResult;
		}
		else {
			SearchResult<Long> searchResult= new SearchResult<Long>();
			searchResult.setResult(rootId);
			//tinh total 
			String sql1="select count (distinct dept.rootId) from Department dept left join dept.departments p ";
			if(StringUtils.isNotEmpty(departmentBean.getSearchContent()))  sql1+=" where lower(dept.name) like lower(:name) or lower(p.name) like lower(:parentname)"
					+ " or lower(dept.note) like lower(:note) ";
			query=this.getSession().createQuery(sql1);
			if(StringUtils.isNotEmpty(departmentBean.getSearchContent())){
				query.setParameter("name", "%"+departmentBean.getSearchContent()+"%");
				query.setParameter("parentname", "%"+departmentBean.getSearchContent()+"%");
				query.setParameter("note", "%"+departmentBean.getSearchContent()+"%");
			}
			searchResult.setTotalCount(((Long)query.uniqueResult()).intValue());
			
			return searchResult ;
		}
	}

}
