package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.bean.RoleBean;
import vn.com.unit.fe_credit.entity.Role;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface RoleDao extends GenericDAO<Role, Long>{
	public List<Role> searchByTeam(Long teamID);
	public List<Role> searchNotByTeam(Long teamID);
	public List<Role> findByIds(List<Long> chkRoleLeft);
	public List<Object[]> findAll(RoleBean roleBean);
	List<Object[]> search(RoleBean roleBean);
	 Role  findByCode(String code);
	 Integer roleCountSearch(RoleBean roleBean);
}
