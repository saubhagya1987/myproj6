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
import vn.com.unit.fe_credit.bean.ModuleBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Module;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ModuleService;
import vn.com.unit.fe_credit.service.TeamService;

@Controller
@RequestMapping("/system")
public class ModuleController {
	
	@Autowired
	ModuleService moduleService;

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

	@RequestMapping(value = "/module/list", method = RequestMethod.GET)
	public String listModule(@ModelAttribute(value = "bean") ModuleBean moduleBean ,  Model model,
			@RequestParam(value = "message", required = false) String message,  Locale locale) throws Exception {
		if (message != null && message.equals("success")) {
			moduleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		List<Object[]> result = moduleService.getAllModules(moduleBean, locale,msgSrc);
		model.addAttribute("bean", moduleBean);
		model.addAttribute("result", result);
		return "system.module.list";
	}


@Transactional
	@RequestMapping(value = "/module/edit", method = RequestMethod.GET)
	public String editRoleGet(@RequestParam(required = false) Long id, HttpServletRequest request, Model model) throws Exception {
		Module module;
		ModuleBean moduleBean = new ModuleBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			module = moduleService.findById(id);
		} else {
			module = new Module();
		}
		moduleBean.setEntity(module);
		model.addAttribute("bean", moduleBean);
		return "system.module.edit";
	}
	
@RequestMapping(value = "/module/edit", method = RequestMethod.POST)
	public String roleEditPost(@ModelAttribute(value = "bean") @Valid ModuleBean moduleBean,BindingResult bindingResult, Model model,  Locale locale, 
			RedirectAttributes redirectAttributes) throws Exception {
		return moduleService.save(moduleBean, bindingResult, model, locale,redirectAttributes,msgSrc);
	}



	
		@RequestMapping(value = "/module/list", method = RequestMethod.POST)
	public String listRolePost(@ModelAttribute(value = "bean") ModuleBean moduleBean ,  Model model, HttpServletRequest request, Locale locale)
			throws Exception {
		List<Object[]> result = moduleService.search(moduleBean, model, locale,msgSrc);
		model.addAttribute("bean", moduleBean);
		model.addAttribute("result", result);
		return "system.module.list";
	}
	
}
