package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.bean.ModuleBean;
import vn.com.unit.fe_credit.entity.Module;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface ModuleDao extends GenericDAO<Module, Long>{

	public List<Object[]> findAll(ModuleBean moduleBean);
	Integer moduleCountSearch(ModuleBean moduleBean);
	Module findByname(String name);
	public List<Module> searchByRole(Long roleId);
	public List<Module> searchNotByRole(Long roleId);
	public List<Module> findByIds(List<Long> chkModuleLeft);
	public List<Module> findByNotInIds(List<Long> chkModuleLeft);
	List<Object[]> search(ModuleBean moduleBean);
}
