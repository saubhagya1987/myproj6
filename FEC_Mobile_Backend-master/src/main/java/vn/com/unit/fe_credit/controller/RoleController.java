package vn.com.unit.fe_credit.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.RoleBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Module;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ModuleService;
import vn.com.unit.fe_credit.service.RoleService;
import vn.com.unit.fe_credit.service.TeamService;

@Controller
@RequestMapping("/system")
public class RoleController {
	
	@Autowired
	RoleService roleService;

	@Autowired
	AccountTeamService accountTeamService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	UserProfile userProfile;

	@Autowired
	ActivityLogService activityLogService;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	ModuleService moduleService;

	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public String listRole(@ModelAttribute(value = "bean") RoleBean roleBean ,  Model model,
			@RequestParam(value = "message", required = false) String message,  Locale locale) throws Exception {
		if (message != null && message.equals("success")) {
			roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		List<Object[]> result = roleService.getAllRoles(roleBean, locale,msgSrc);
		model.addAttribute("bean", roleBean);
		model.addAttribute("result", result);
/*		LDAPSyn ldapSyn = new LDAPSyn();
		Integer checkLdapSyn = ldapSyn.check(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null);
		model.addAttribute("checkLdapSyn", checkLdapSyn);*/
		return "system.role.list";
	}


	@Transactional
	@RequestMapping(value = "/role/edit", method = RequestMethod.GET)
	public String editRoleGet(@RequestParam(required = false) Long id, HttpServletRequest request, Model model) throws Exception {
		Role role;
		RoleBean roleBean = new RoleBean();
		List<Module> moduleListRight = null;
		List<Module> moduleListLeft = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			role = roleService.findById(id);
			moduleListLeft=moduleService.searchByRoleId(id);
			moduleListRight=moduleService.searchNotByRoleId(id);
		} else {
			role = new Role();
			moduleListRight=moduleService.findAll();
		}
		roleBean.setEntity(role);
		model.addAttribute("bean", roleBean);
		model.addAttribute("right",moduleListRight);
		model.addAttribute("left",moduleListLeft);
		return "system.role.edit";
	}
	
	@RequestMapping(value = "/role/edit", method = RequestMethod.POST)
	public String roleEditPost(@ModelAttribute(value = "bean") @Valid RoleBean roleBean ,BindingResult bindingResult, Model model, @RequestParam(value = "chkTeamLeft", required = false) List<Long> chkTeamLeft,   Locale locale, 
			RedirectAttributes redirectAttributes) throws Exception {
		return roleService.save(roleBean, bindingResult, model, locale,redirectAttributes,msgSrc,chkTeamLeft);
	}



	
	@RequestMapping(value = "/role/list", method = RequestMethod.POST)
	public String listRolePost(@ModelAttribute(value = "bean") RoleBean roleBean ,  Model model, HttpServletRequest request, Locale locale)
			throws Exception {
		List<Object[]> result = roleService.search(roleBean, model, locale,msgSrc);
		model.addAttribute("bean", roleBean);
		model.addAttribute("result", result);
	/*	LDAPSyn ldapSyn = new LDAPSyn();
		Integer checkLdapSyn = ldapSyn.check(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null);
		model.addAttribute("checkLdapSyn", checkLdapSyn);*/
		return "system.role.list";
	}
	
	@RequestMapping(value = "/role/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean")  RoleBean roleBean , Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (roleBean.getEntity().getId() != null) {
			roleService.deleteRoleById(roleBean, locale,teamService,msgSrc);
		}
		redirectAttributes.addFlashAttribute("bean", roleBean);
		return "redirect:/system/role/list";
	
	}
	
}
