package vn.com.unit.fe_credit.service;


import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.RoleBean;
import vn.com.unit.fe_credit.entity.Role;

public interface RoleService {
	Role findById(Long id);
	List<Role> findRolebyTeamID(Long ID);
	List<Role> findRoleNotbyTeamID(Long ID);
	List<Role> findByIds(List<Long> chkTeamLeft);
	String save(RoleBean roleBean, BindingResult bindingResult,Model model, Locale locale, 
			RedirectAttributes redirectAttributes,MessageSource msgSrc,List<Long> chkTeamLeft);
	List<Object[]> getAllRoles(RoleBean roleBean, Locale locale,MessageSource msgSrc);
	List<Object[]> search(RoleBean roleBean, Model model, Locale locale,MessageSource msgSrc);
	void deleteRoleById(RoleBean roelBean,Locale locale,TeamService teamService,MessageSource msgSrc);
	Role findByCode(String code);
	Integer roleCountsearch(RoleBean roleBean);
}
