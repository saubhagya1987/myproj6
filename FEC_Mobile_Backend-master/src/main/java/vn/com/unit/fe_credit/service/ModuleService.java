package vn.com.unit.fe_credit.service;


import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.ModuleBean;
import vn.com.unit.fe_credit.entity.Module;
import vn.com.unit.fe_credit.entity.Team;

public interface ModuleService {
	Module findById(Long id);
	Module findByName(String name);
	String save(ModuleBean moduleBean, BindingResult bindingResult,Model model, Locale locale, 	RedirectAttributes redirectAttributes,MessageSource msgSrc);
	List<Object[]> getAllModules(ModuleBean moduleBean, Locale locale,MessageSource msgSrc);
	List<Object[]> search(ModuleBean moduleBean, Model model, Locale locale,MessageSource msgSrc);
	//void deleteModuleById(ModuleBean moduleBean,Locale locale,TeamService teamService,MessageSource msgSrc);
	//Integer moduleCountsearch(ModuleBean moduleBean);
	public List<Module> findAll();
	public List<Module> searchByRoleId(Long roleId);
	public List<Module> searchNotByRoleId(Long roleId);
	public List<Module> findByIds(List<Long> chkModuleLeft);
	public List<Module> findByNotInIds(List<Long> chkModuleLeft);
}
