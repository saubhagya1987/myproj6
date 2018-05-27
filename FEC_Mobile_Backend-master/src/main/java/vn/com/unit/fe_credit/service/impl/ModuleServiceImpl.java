package vn.com.unit.fe_credit.service.impl;


import java.util.Date;
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
import vn.com.unit.fe_credit.bean.ModuleBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.ModuleDao;
import vn.com.unit.fe_credit.entity.Module;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ModuleService;

@Service("moduleService")
@Transactional(readOnly = true)
public class ModuleServiceImpl implements ModuleService {
	@Autowired
	ModuleDao moduleDao;
	
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	UserProfile userProfile;

	@Autowired
	ActivityLogService activityLogService;

	
	@Override
	public List<Object[]> getAllModules(ModuleBean moduleBean, Locale locale,
			MessageSource msgSrc) {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		moduleBean.setLimit(pagesize);
		moduleBean.setEntity(new Module());
		List<Object[]> result =  moduleDao.findAll(moduleBean);
		Integer total = moduleDao.moduleCountSearch(moduleBean);

		if (CollectionUtils.isEmpty(result)) {
			moduleBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		moduleBean.setTotal(new Integer(total + ""));
		return result;		
	}


	@Override
	public Module findById(Long id) {
		return moduleDao.find(id);
	}


	@Override
	@Transactional
	public String save(ModuleBean moduleBean, BindingResult bindingResult,
			Model model, Locale locale, RedirectAttributes redirectAttributes,
			MessageSource msgSrc) {
		
		Module module = new Module();
		boolean newModule = false;
		boolean isSavedFail = false;
		Module moduleFromDb=null;
		
		if (StringUtils.isEmpty(moduleBean.getEntity().getName())) {
			bindingResult.rejectValue("entity.name", "javax.validation.constraints.NotNull.message");
		}else{
			try{
				moduleFromDb = findByName(moduleBean.getEntity().getName());
			}catch(Exception e){
				moduleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		
		if (StringUtils.isEmpty(moduleBean.getEntity().getDescription())) {
			bindingResult.rejectValue("entity.description", "javax.validation.constraints.NotNull.message");
		}
		
		if (moduleBean.getEntity().getId() != null) {
			//for edit req
			module = moduleDao.find(moduleBean.getEntity().getId());
			if(moduleFromDb != null && !(moduleFromDb.getId().equals(moduleBean.getEntity().getId()))){
				moduleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.module.name.exits", null, locale));
				model.addAttribute("bean", moduleBean);
				return "system.module.edit.ajax";
			}
				
		} else {		
			//for new req
			if(moduleFromDb != null){
				moduleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.module.name.exits", null, locale));
				model.addAttribute("bean", moduleBean);
				return "system.module.edit.ajax";
			}
			module.setCreationDate(new Date());
			module.setCreatedBy(userProfile.getAccount().getEmail());
			newModule = true;
		    }
		
		if (StringUtils.isNotEmpty(moduleBean.getEntity().getName())) {
			module.setName(moduleBean.getEntity().getName());
		}
	
			module.setDescription(moduleBean.getEntity().getDescription());
	
			moduleBean.setEntity(module);	

		if (!bindingResult.hasErrors()) {

			try {

				Long checkid = moduleBean.getEntity().getId();
				
				moduleDao.save(moduleBean.getEntity());
				// saving project into Module Table

			/*	if (checkid != null)
					activityLogService.saveActivityLog(systemConfig.EDIT_ROLE, systemConfig.ROLE, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());
				else
					activityLogService.saveActivityLog(systemConfig.ADD_NEW_ROLE, systemConfig.ACCOUNT, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());*/

				moduleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", moduleBean);

				model.addAttribute("bean", moduleBean);
				model.addAttribute("success", true);

				return "system.module.edit.ajax";
			} catch (Exception ex) {
				isSavedFail = true;
				ex.printStackTrace();
				moduleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}

		} else {
			isSavedFail = true;
			if (bindingResult.getFieldError("entity") != null) {
				moduleBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			} else {
				moduleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		
		moduleBean.setIsSavedFail(isSavedFail);
		model.addAttribute("bean", moduleBean);
		return "system.module.edit.ajax";
	
	}


	@Override
	public Module findByName(String name) {
		return moduleDao.findByname(name);
	}


	@Override
	public List<Module> findAll() {
		return moduleDao.findAll();
	}


	@Override
	public List<Module> searchByRoleId(Long roleId) {
		return moduleDao.searchByRole(roleId);
	}


	@Override
	public List<Module> searchNotByRoleId(Long roleId) {
		return moduleDao.searchNotByRole(roleId);
	}


	@Override
	public List<Module> findByIds(List<Long> chkModuleLeft) {
		return moduleDao.findByIds(chkModuleLeft);
	}


	@Override
	public List<Module> findByNotInIds(List<Long> chkModuleLeft) {
		return moduleDao.findByNotInIds(chkModuleLeft);
	}


	@Override
	public List<Object[]> search(ModuleBean moduleBean, Model model,
			Locale locale, MessageSource msgSrc) {
		
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		moduleBean.setLimit(pagesize);

		List<Object[]> result = moduleDao.search(moduleBean);
		Integer total = result.size();
		
		if (CollectionUtils.isEmpty(result)) {
			moduleBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		moduleBean.setTotal(total);
		return result;
	}
	
}
