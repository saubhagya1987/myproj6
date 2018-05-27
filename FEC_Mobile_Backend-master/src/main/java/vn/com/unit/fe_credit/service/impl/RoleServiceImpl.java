package vn.com.unit.fe_credit.service.impl;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.RoleBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.RoleDao;
import vn.com.unit.fe_credit.entity.Module;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ModuleService;
import vn.com.unit.fe_credit.service.RoleService;
import vn.com.unit.fe_credit.service.TeamService;

@Service("roleService")
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {
	@Autowired
	RoleDao roleDao;
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	UserProfile userProfile;

	@Autowired
	ActivityLogService activityLogService;
	
	@Autowired
	ModuleService moduleService;
	

	@Override
	public List<Role> findRolebyTeamID(Long ID) {
		return roleDao.searchByTeam(ID);
	}

	@Override
	public List<Role> findRoleNotbyTeamID(Long ID) {
		return roleDao.searchNotByTeam(ID);
	}

	@Override
	public List<Role> findByIds(List<Long> chkRoleLeft) {
		return roleDao.findByIds(chkRoleLeft);
	}

	@Override
	public Role findById(Long id) {
		return roleDao.find(id);
	}

	@Override
	@Transactional
	public String save(RoleBean roleBean, BindingResult bindingResult,Model model, Locale locale, 
			RedirectAttributes redirectAttributes,MessageSource msgSrc,List<Long> chkTeamLeft) {
		
		Role role = new Role();
		boolean newRole = false;
		boolean isSavedFail = false;
		Role roleFromDb=null;
		
		if (StringUtils.isEmpty(roleBean.getEntity().getCode())) {
			bindingResult.rejectValue("entity.code", "javax.validation.constraints.NotNull.message");
		}else{
			try{
			roleFromDb = findByCode(roleBean.getEntity().getCode());
			}catch(Exception e){
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		if (StringUtils.isEmpty(roleBean.getEntity().getName())) {
			bindingResult.rejectValue("entity.name", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(roleBean.getEntity().getType())) {
			bindingResult.rejectValue("entity.type", "javax.validation.constraints.NotNull.message");
		}
		
		if (roleBean.getEntity().getId() != null) {
			//for edit req
			role = roleDao.find(roleBean.getEntity().getId());
			if(roleFromDb != null && !(roleFromDb.getId().equals(roleBean.getEntity().getId()))){
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.role.code.exits", null, locale));
				model.addAttribute("bean", roleBean);
				return "system.role.edit.ajax";
			}
				
		} else {		
			//for new req
			if(roleFromDb != null){
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.role.code.exits", null, locale));
				model.addAttribute("bean", roleBean);
				return "system.role.edit.ajax";
			}
			role.setCreationDate(new Date());
			role.setCreatedBy(userProfile.getAccount().getEmail());
			newRole = true;
		    }
		
		
		if (StringUtils.isNotEmpty(roleBean.getEntity().getCode())) {
			role.setCode(roleBean.getEntity().getCode());
		}
		if (StringUtils.isNotEmpty(roleBean.getEntity().getName())) {
			role.setName(roleBean.getEntity().getName());
		}
		if (StringUtils.isNotEmpty(roleBean.getEntity().getType())) {
			role.setType(roleBean.getEntity().getType());
		}
	
			role.setDescription(roleBean.getEntity().getDescription());
		//	role.setAllowMobileAccess(roleBean.getEntity().getAllowMobileAccess());
		//	role.setAllowPortalAccess(roleBean.getEntity().getAllowPortalAccess());
	
		roleBean.setEntity(role);	
		List<Module> teamLeft = null;
		List<Module> teamRight = null;
		if (chkTeamLeft != null) {
			teamLeft = moduleService.findByIds(chkTeamLeft);
			teamRight = moduleService.findByNotInIds(chkTeamLeft);
		}
		if (!bindingResult.hasErrors()) {

			try {
				List<Module> teamListLeft = null;
				List<Module> teamListRight = null;

				if (chkTeamLeft != null) {
					teamLeft = moduleService.findByIds(chkTeamLeft);
					roleBean.getEntity().setModules(new HashSet<Module>(teamLeft));

				} else {
					roleBean.getEntity().setModules(new HashSet<Module>());
				}

				Long checkid = roleBean.getEntity().getId();
				
				roleDao.save(roleBean.getEntity());
				// saving project into Role Table

				if (checkid != null)
					activityLogService.saveActivityLog(systemConfig.EDIT_ROLE, systemConfig.ROLE, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());
				else
					activityLogService.saveActivityLog(systemConfig.ADD_NEW_ROLE, systemConfig.ACCOUNT, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());

				roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", roleBean);

				teamListLeft =  moduleService.searchByRoleId(roleBean.getEntity().getId());
				teamListRight = moduleService.searchNotByRoleId(roleBean.getEntity().getId());
				
				model.addAttribute("bean", roleBean);
				model.addAttribute("right", teamListRight);
				model.addAttribute("left", teamListLeft);
				model.addAttribute("success", true);

				return "system.role.edit.ajax";
			} catch (Exception ex) {
				isSavedFail = true;
				ex.printStackTrace();
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}

		} else {
			isSavedFail = true;
			if (bindingResult.getFieldError("entity") != null) {
				roleBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			} else {
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		
		roleBean.setIsSavedFail(isSavedFail);
		
		List<Module> teamListLeft = moduleService.searchByRoleId(roleBean.getEntity().getId());
		List<Module> teamListRight = moduleService.searchNotByRoleId(roleBean.getEntity().getId());
		if (roleBean.getEntity().getId() == null) {
			model.addAttribute("right", teamRight);
			model.addAttribute("left", teamLeft);
		} else {
			model.addAttribute("right", teamListRight);
			model.addAttribute("left", teamListLeft);
		}

		model.addAttribute("bean", roleBean);
		return "system.role.edit.ajax";
	
	}

	@Override
	public List<Object[]> getAllRoles(	RoleBean roleBean , Locale locale,MessageSource msgSrc) {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		roleBean.setLimit(pagesize);
		roleBean.setEntity(new Role());
		List<Object[]> result =  roleDao.findAll(roleBean);
		Integer total = roleDao.roleCountSearch(roleBean);

		if (CollectionUtils.isEmpty(result)) {
			roleBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		roleBean.setTotal(new Integer(total + ""));
		return result;		
	}

	@Override
	public List<Object[]> search(RoleBean roleBean, Model model, Locale locale,MessageSource msgSrc) {
		
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		roleBean.setLimit(pagesize);

		List<Object[]> result = roleDao.search(roleBean);
		Integer total = result.size();
		
		if (CollectionUtils.isEmpty(result)) {
			roleBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		roleBean.setTotal(total);
		return result;
	}

	@Override
	@Transactional
	public void deleteRoleById(RoleBean roleBean,Locale locale,TeamService teamService,MessageSource msgSrc) {
		List<Team> team=null ;
		try {
			if(roleBean.getEntity().getId() != null){
				 team = teamService.findTeambyRoleID(roleBean.getEntity().getId());									
				//System.out.println("role team size "+team.size());
						if(team.size() >0)
							throw new Exception();
						// roleDao.removeById(roleBean.getEntity().getId());
				roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
			}
		} catch (Exception ex) {
			String msg="msg.delete.fail";
			if(team.size() >0)
				msg="msg.role.delete.fail";
			roleBean.addMessage(Message.ERROR, msgSrc.getMessage(msg, null, locale));
		}
		
	}
	
	@Override
	public Role findByCode(String role) {
		return roleDao.findByCode(role);
	}
	
	@Override
	public Integer roleCountsearch(RoleBean roleBean) {
		return roleDao.roleCountSearch(roleBean);
	}
	
}
