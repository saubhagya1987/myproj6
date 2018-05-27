package vn.com.unit.fe_credit.dao.impl;



import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.RoleBean;
import vn.com.unit.fe_credit.dao.RoleDao;
import vn.com.unit.fe_credit.entity.Role;


@Repository
public class RoleDaoImpl extends GenericDAOImpl<Role, Long> implements RoleDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	@Override
	public List<Role> searchByTeam(Long teamID) {
		String hql="from Role role1 where role1.id in ("
				+ "select role.id from Role role inner join "
				+ "role.teeprocurement team "
				+ "where team.id=:teamId) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("teamId", teamID);
		return query.list();
	}
	@Override
	public List<Role> searchNotByTeam(Long teamID) {
		String hql="from Role role1 where role1.id not in ("
				+ "select role.id from Role role inner join "
				+ "role.teeprocurement team "
				+ "where team.id=:teamId) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("teamId", teamID);
		return query.list();
	}
	@Override
	public List<Role> findByIds(List<Long> chkRoleLeft) {
		String hql="from Role role1 where role1.id in (:list)";
		Query query = getSession().createQuery(hql);
		 query.setParameterList("list", chkRoleLeft);
		return query.list();
	}

	@Override
	public List<Object[]> findAll(RoleBean roleBean){
		String hql="SELECT  rl.code as code , rl.name as name , rl.type as type , rl.description as description , rl.id as id  FROM Role  rl";
		Query query = getSession().createQuery(hql);
		query.setMaxResults(roleBean.getLimit());
		List<Object[]> lst = query.list();
		return lst;
	}

	@Override
	public List<Object[]> search(RoleBean roleBean) {

		Role role = roleBean.getEntity(); 

		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT  rl.code as code , rl.name as name , rl.type as type , rl.description as description , rl.RoleId as id FROM Role  rl ");

		hql.append(" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(role.getCode())) {
			hql.append(" AND rl.code LIKE :code");
		}
		if (StringUtils.isNotEmpty(role.getName())) {
			hql.append(" AND rl.name LIKE :name");
		}
		if (StringUtils.isNotEmpty(role.getType())) {
			hql.append(" AND rl.type LIKE :type");
		}
	
		hql.append(" ORDER BY rl.RoleId");
		Query query = getSession().createSQLQuery(hql.toString());
		if (StringUtils.isNotEmpty(role.getCode())) {
			query.setParameter("code", "%" + role.getCode() + "%");
		}
		if (StringUtils.isNotEmpty(role.getName())) {
			query.setParameter("name", "%" + role.getName() + "%");
		}
		if (StringUtils.isNotEmpty(role.getType())) {
			query.setParameter("type", "%" + role.getType() );
		}
		
		if (roleBean.getLimit() > 0) {
			query.setMaxResults(roleBean.getLimit());
			query.setFirstResult((roleBean.getPage() - 1) * roleBean.getLimit());
		}
		List<Object[]> lst = query.list();

		return lst;
	}
	
	@Override
	public Role findByCode(String code) {
		try{
		String hql="  FROM Role  rl WHERE rl.code=:code ";
		Query query = getSession().createQuery(hql);
		query.setParameter("code", code);
		Role role = (Role) query.uniqueResult();
		return role;
		}catch(Exception ex){
			ex.printStackTrace();
			throw ex;
		}
	}
	
	@Override
	public Integer roleCountSearch(RoleBean roleBean) {
		Role role = roleBean.getEntity();
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*) FROM (");
		hql.append(" SELECT  rl.code as code , rl.name as name , rl.type as type , rl.description as description , rl.RoleId as id FROM Role  rl");

		hql.append(" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(role.getCode())) {
			hql.append(" AND rl.code LIKE :code");
		}
		if (StringUtils.isNotEmpty(role.getName())) {
			hql.append(" AND rl.name LIKE :name");
		}
		if (StringUtils.isNotEmpty(role.getType())) {
			hql.append(" AND rl.type LIKE :type");
		}

		hql.append(" ORDER BY rl.RoleId");
		hql.append(" ) ");
		Query query = getSession().createSQLQuery(hql.toString());
		if (StringUtils.isNotEmpty(role.getCode())) {
			query.setParameter("code", "%" + role.getCode() + "%");
		}
		if (StringUtils.isNotEmpty(role.getName())) {
			query.setParameter("name", "%" + role.getName() + "%");
		}
		if (StringUtils.isNotEmpty(role.getType())) {
			query.setParameter("type", "%" + role.getType() );
		}
		
		query.uniqueResult().toString();
		Integer roleCount = 0;
		try {
			roleCount = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return roleCount;
	}
	
}
