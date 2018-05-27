package vn.com.unit.fe_credit.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import vn.com.unit.fe_credit.bean.TeamBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.RoleService;
import vn.com.unit.fe_credit.service.TeamService;

@Controller
@RequestMapping("/system")
public class TeamController {

	@Autowired
	TeamService teamService;

	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private MessageSource msgSrc;
	private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

	@RequestMapping(value = "/team/list", method = RequestMethod.GET)
	public String list(@ModelAttribute(value = "bean") TeamBean teamBean, Model model, HttpServletRequest request, Locale locale) throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		teamBean.setLimit(pagesize);
		teamBean.setEntity(new Team());
		TeamBean result = new TeamBean();

		try {
			result = teamService.search(teamBean);
		} catch (Exception ex) {
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
		}
//		if(CollectionUtils.isEmpty(result.getListResult())) {
//			result.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
//		}
		model.addAttribute("bean", result);
		return "system.team.list";
	}

	@RequestMapping(value = "/team/list", method = RequestMethod.POST)
	public String listpot(@ModelAttribute(value = "bean") TeamBean teamBean, Model model, HttpServletRequest request, Locale locale) throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		teamBean.setLimit(pagesize);
		int success = 0;
		if (StringUtils.isNotEmpty(request.getParameter("action")) && request.getParameter("action").endsWith("delete")) {
			String[] chkDeletes = request.getParameterValues("chkDelete");
			if (chkDeletes != null && chkDeletes.length > 0) {
				for (int i = 0; i < chkDeletes.length; i++) {
					try {
						Long idTeam = Long.valueOf(chkDeletes[i]);
						Team teamRole = teamService.findById(idTeam);
						if(teamRole != null){
							teamRole.setRoles(null);
							teamService.saveOrUpdateTeam(teamRole);
						}
						teamService.deleteTeamById(idTeam);
						success++;
					} catch (Exception ex) {
						logger.info("team in used");
					}
				}
			}
		}
		TeamBean result = teamService.search(teamBean);
		if (StringUtils.isNotEmpty(request.getParameter("action")) && request.getParameter("action").equals("delete")) {
			result.clearMessages();
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.have.no.items.deleted", null, locale).replace("{0}", String.valueOf(success)));
		}
		if(CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		model.addAttribute("bean", result);
		return "system.team.list";
	}

	@Autowired
	RoleService roleService;

	@Transactional
	@RequestMapping(value = "/team/edit", method = RequestMethod.GET)
	public String editSave(@RequestParam(required = false) Long id, HttpServletRequest request, Model model) throws Exception {
		Team team;
		// Team team;
		List<vn.com.unit.fe_credit.entity.Role> roleListLeft = null;
		List<vn.com.unit.fe_credit.entity.Role> roleListRight = null;
		roleListRight = roleService.findRoleNotbyTeamID(id);
		roleListLeft = roleService.findRolebyTeamID(id);

		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			team = teamService.findById(id);
		}

		else {
			team = new Team();
		}
		TeamBean teamBean = new TeamBean();
		teamBean.setEntity(team);
		model.addAttribute("bean", teamBean);
		model.addAttribute("left", roleListLeft);
		model.addAttribute("right", roleListRight);
		return "system.team.edit";
	}

	@RequestMapping(value = "/team/view", method = RequestMethod.GET)
	public String view(Model model, HttpServletRequest request) throws Exception {
		Team team;
		List<vn.com.unit.fe_credit.entity.Role> roleListLeft = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			team = teamService.findById(Long.valueOf(request.getParameter("id")));
			roleListLeft = roleService.findRolebyTeamID(Long.valueOf(request.getParameter("id")));
		} else
			team = new Team();
		TeamBean teamBean = new TeamBean();
		teamBean.setEntity(team);
		model.addAttribute("bean", teamBean);
		model.addAttribute("left", roleListLeft);
		return "system.team.view";
	}

	@RequestMapping(value = "/team/edit", method = RequestMethod.POST)
	public String View(@ModelAttribute(value = "bean") @Valid TeamBean teamBean, BindingResult bindingResult, Model model, HttpServletRequest request,
			@RequestParam(value = "chkRoleLeft", required = false) List<Long> chkRoleLeft, @RequestParam(value = "chkCategoryLeft", required = false) List<Long> chkCategoryLeft,
			Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		List<vn.com.unit.fe_credit.entity.Role> roleListLeft = null;
		List<vn.com.unit.fe_credit.entity.Role> roleListRight = null;
		roleListRight = roleService.findRoleNotbyTeamID(teamBean.getEntity().getId());

		if (!bindingResult.hasErrors()) {
			try {
				if (teamBean.getEntity().getId() == null) {
					teamBean.getEntity().setType("BMS");
					teamBean.getEntity().setEnabled(true);
				}

				if (chkRoleLeft != null) {
					List<vn.com.unit.fe_credit.entity.Role> roleLeft = roleService.findByIds(chkRoleLeft);
					teamBean.getEntity().setRoles(new HashSet<vn.com.unit.fe_credit.entity.Role>(roleLeft));
				}

				teamService.saveOrUpdateTeam(teamBean.getEntity());
				roleListLeft = roleService.findRolebyTeamID(teamBean.getEntity().getId());
				teamBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", teamBean);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				teamBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
				model.addAttribute("bean", teamBean);
				return "system.team.edit";
			}
			model.addAttribute("bean", teamBean);
			model.addAttribute("right", roleListRight);
			model.addAttribute("left", roleListLeft);
			redirectAttributes.addFlashAttribute("bean", teamBean);
			return "redirect:/system/team/list";
		} else {
			if (bindingResult.getFieldError("entity") != null){
				teamBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			}else{
				teamBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
			model.addAttribute("bean", teamBean);
			model.addAttribute("right", roleListRight);
			return "system.team.edit";
		}

	}

	@RequestMapping(value = "/team/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean") TeamBean bean, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (bean.getEntity().getId() != null) {
			Integer noDelete = 0;
			try {
				if(bean.getEntity().getId() != null){
					Team teamRole = teamService.findById(bean.getEntity().getId());
					if(teamRole != null){
						teamRole.setRoles(null);
						teamService.saveOrUpdateTeam(teamRole);
					}
					teamService.deleteTeamById(bean.getEntity().getId());
					noDelete++;
					bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
				logger.info("can't delete Team id={} with ex={}", bean.getEntity().getId(), ex);
				bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/system/team/list";
	}

}
