package vn.com.unit.fe_credit.dao.impl;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.ModuleBean;
import vn.com.unit.fe_credit.dao.ModuleDao;
import vn.com.unit.fe_credit.entity.Module;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;


@Repository
public class ModuleDaoImpl extends GenericDAOImpl<Module, Long> implements ModuleDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Object[]> findAll(ModuleBean moduleBean) {
		String hql="SELECT  m1.name as name , m1.description as description , m1.id as id  FROM Module  m1";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(moduleBean.getLimit());
		List<Object[]> lst = query.list();
		return lst;
	}
	@Override
	public Integer moduleCountSearch(ModuleBean moduleBean) {
		Module module = moduleBean.getEntity();
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*) FROM (");
		hql.append(" SELECT  rl.name as name ,  rl.description as description , rl.ModuleId as id FROM Module  rl");

		hql.append(" WHERE 1=1 ");
		
		if (StringUtils.isNotEmpty(module.getName())) {
			hql.append(" AND rl.name LIKE :name");
		}
		hql.append(" ORDER BY rl.ModuleId");
		hql.append(" ) ");
		Query query = getSession().createSQLQuery(hql.toString());
		if (StringUtils.isNotEmpty(module.getName())) {
			query.setParameter("name", "%" + module.getName() + "%");
		}		
		query.uniqueResult().toString();
		Integer roleCount = 0;
		try {
			roleCount = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return roleCount;
	}


	@Override
	public Module findByname(String name) {
		String hql="  FROM Module  rl WHERE rl.name=:name ";
		Query query = getSession().createQuery(hql);
		query.setParameter("name", name);
		Module module = (Module) query.uniqueResult();
		return module;
	
	}
	
	@Override
	public List<Module> searchByRole(Long roleId) {
		String hql="from Module module1 where module1.id in ("
				+ "select module.id from Module module inner join "
				+ "module.roles role "
				+ "where role.id=:roleId) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("roleId", roleId);
		return query.list();
	}

	@Override
	public List<Module> searchNotByRole(Long roleId) {
		String hql="from Module module1 where module1.id not in ("
				+ "select module.id from Module module inner join "
				+ "module.roles role "
				+ "where role.id=:roleId) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("roleId", roleId);
		return query.list();
	}

	@Override
	public List<Module> findByIds(List<Long> chkModuleLeft) {
		// TODO Auto-generated method stub
		String hql="from Module module1 where module1.id in (:list)";
		Query query = getSession().createQuery(hql);
		 query.setParameterList("list", chkModuleLeft);
		return query.list();
	}

	@Override
	public List<Module> findByNotInIds(List<Long> chkModuleLeft) {
		String hql="from Module module1 where module1.id not in (:list)";
		Query query = getSession().createQuery(hql);
		 query.setParameterList("list", chkModuleLeft);
		return query.list();
	}

	@Override
	public List<Object[]> search(ModuleBean moduleBean) {

		Module module = moduleBean.getEntity(); 

		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT  ml.name as name , ml.description as description , ml.ModuleId as id FROM Module  ml ");

		hql.append(" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(module.getName())) {
			hql.append(" AND ml.name LIKE :name");
		}
	
		hql.append(" ORDER BY ml.ModuleId");
		Query query = getSession().createSQLQuery(hql.toString());
		if (StringUtils.isNotEmpty(module.getName())) {
			query.setParameter("name", "%" + module.getName() + "%");
		}
		
		if (moduleBean.getLimit() > 0) {
			query.setMaxResults(moduleBean.getLimit());
			query.setFirstResult((moduleBean.getPage() - 1) * moduleBean.getLimit());
		}
		List<Object[]> lst = query.list();

		return lst;
	}
	
}
