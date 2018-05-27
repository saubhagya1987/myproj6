package vn.com.unit.fe_credit.controller;


import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.commons.collections.map.HashedMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.DepartmentBean;
import vn.com.unit.fe_credit.bean.DepartmentTreeBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.DepartmentService;

@Controller
@RequestMapping("/master_data")
public class DepartmentController {
	//private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired
	DepartmentService deptService;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	AccountService accountService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	private MessageSource msgSrc;
	private static final Logger logger = LoggerFactory.getLogger(DepartmentController.class);
	@RequestMapping(value="/department/account_json",method=RequestMethod.GET)
	//@Transactional
	public @ResponseBody List<Account> list_accountJson(@RequestParam(value="query", required = false ) String name){
		List<Account> root=accountService.findAccountByName(name);
		return root;
	}
	@RequestMapping(value="/department/json_parent_combo",method=RequestMethod.GET)
	@Transactional
	public @ResponseBody List<DepartmentTreeBean> json_parent_combo(@RequestParam Long currentNode,Locale locale){
		DepartmentBean bean=new DepartmentBean();
		bean.setCurrentNode(currentNode);
		Map<String, Object> result= new HashedMap();
		List<DepartmentTreeBean> root=deptService.createTreeForCombo(currentNode);
		DepartmentTreeBean defaultNode=new DepartmentTreeBean();
		defaultNode.setId(new Long(-1));
		defaultNode.setText(messageSource.getMessage("root.element", null, locale));
		root.add(0, defaultNode);
		return root;
	}


	@RequestMapping(value = "/department/json_tree", method = RequestMethod.GET)
	public @ResponseBody
	List<DepartmentTreeBean> getDeptTree(Model model,
			@RequestParam(value = "id", required = false) Long id)
					throws Exception {
		List<DepartmentTreeBean> root = deptService.createTree(id);
		return root;
	}
	@RequestMapping(value="/department/list_json",method=RequestMethod.GET)
	public @ResponseBody List<Department> list_json(@RequestParam(value="query", required = false ) String code){
		List<Department> root=deptService.findDepartments(code, null);
		return root;
	}

	@RequestMapping(value = "/department/json_tree_grid", method = RequestMethod.GET)
	public @ResponseBody
	List<DepartmentTreeBean> getCatTreeGrid(
			@ModelAttribute(value = "bean") DepartmentBean deptBean,
			Model model)
					throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		deptBean.setLimit(pagesize);
		DepartmentBean result = deptService.search(deptBean);
		return result.getTree();
	}

	@RequestMapping(value = "/department/list", method = {RequestMethod.GET,RequestMethod.POST})
	public String listget(
			@ModelAttribute(value = "bean") DepartmentBean deptBean,
			Model model, Locale locale) throws Exception {

		Integer pagesize=Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		deptBean.setLimit(pagesize);
		DepartmentBean result = deptService.search(deptBean);
		ObjectMapper mapper = new ObjectMapper();
		result.setJsonString(mapper.writeValueAsString(result.getTree()));
		model.addAttribute("bean", deptBean);
		model.addAttribute("systemConfig", systemConfig);
		return "master_data.department.list";
	}
	@RequestMapping(value = "/department/edit", method = RequestMethod.GET)
	public String editget(Model model,@RequestParam(value = "id", required = false) Long id,
			@ModelAttribute(value = "bean") DepartmentBean deptBean)throws Exception {

		if (id != null) {
			Department dept = deptService.findById(id);
			deptBean.setEntity(dept);
		}
		model.addAttribute("bean", deptBean);
		model.addAttribute("systemConfig", systemConfig);
		return "master_data.department.edit";
	}


	/*@RequestMapping(value = "/department/delete", method = RequestMethod.GET)
	public String delete(Model model,@RequestParam(value = "id", required = false) Long id,
			@ModelAttribute(value = "bean") DepartmentBean deptBean,
			RedirectAttributes redirectAttributes,Locale locale)
					throws Exception {
		deptBean.clearMessages();
		if (id != null) {
			try{
				deptService.deleteDepartment(id);
				deptBean.addMessage(Message.SUCCESS,msgSrc.getMessage("msg.delete.success", null, locale));
			}catch(Exception ex){

				logger.info("department in used");

				deptBean.addMessage(Message.ERROR,
						msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		model.addAttribute("bean", deptBean);
		redirectAttributes.addFlashAttribute("bean",deptBean);
		return "redirect:/master_data/department/list";
	}*/
	@RequestMapping(value = "/department/delete", method = RequestMethod.GET)
	public String delete(Model model,@ModelAttribute(value = "bean") DepartmentBean bean,
			@RequestParam(value = "id", required = false) Long id,
			RedirectAttributes redirectAttributes,Locale locale)
			throws Exception {
		bean.clearMessages();
		if (id != null) {
			try{
				deptService.deleteByNode(id);
				bean.addMessage(Message.SUCCESS,
						messageSource.getMessage("msg.delete.success", null, locale));
			}catch(Exception ex){
				bean.addMessage(Message.ERROR,
						messageSource.getMessage("msg.delete.fail", null, locale));
			}
		}
		model.addAttribute("bean", bean);
		redirectAttributes.addFlashAttribute("bean",bean);
		return "redirect:/master_data/department/list";
	}
	@RequestMapping(value = "/department/view", method = RequestMethod.GET)
	public String viewget(Model model,@RequestParam(value = "id", required = false) Long id,
			@ModelAttribute(value = "bean") DepartmentBean departmentBean)throws Exception {

		if (id != null) {
			Department dept = deptService.findById(id);
			departmentBean.setEntity(dept);
		}
		model.addAttribute("bean", departmentBean);	
		model.addAttribute("systemConfig", systemConfig);
		return "master_data.department.view";
	}
	/*@RequestMapping(value = "/department/edit", method = RequestMethod.POST)
	private String editAction(@ModelAttribute(value = "bean") @Valid DepartmentBean deptBean,
			BindingResult bindingResult, Model model, Locale locale,RedirectAttributes redirectAttributes) {
		deptBean.clearMessages();
		if (!bindingResult.hasErrors()) {
			// not select parent
			if (deptBean.getEntity() != null && deptBean.getEntity().getParent() != null
					&& deptBean.getEntity().getParent().getDepartmentId() == null) {
				deptBean.getEntity().setParent(null);
			} else if (deptBean.getEntity() != null
					&& deptBean.getEntity().getParent() != null
					&& deptBean.getEntity().getParent().getDepartmentId() != null) {// select parent

				// parentlink
				Long parentId = deptBean.getEntity().getParent().getDepartmentId();
				String parentSymbol = systemConfig.getConfig(SystemConfig.PARENT_LINK_SYMBOL);
				if (parentId != null) {
					Department parent = deptService.findById(parentId);
					deptBean.getEntity().setParent(parent);
					String parentLink = parent.getParentLink();
					if (parentLink == null)
						parentLink = "";
					if (!parentLink.endsWith(parentSymbol))
						parentLink += parentSymbol;
					parentLink += parent.getDepartmentId() + parentSymbol;
					deptBean.getEntity().setParentLink(parentLink);
				}


			}

			try {
				/*if(deptBean.getAcc().getUsername()!=null){
					deptBean.getEntity().setManager(deptBean.getAcc().getUsername());
				}*/
	/*		deptService.saveOrUpdateDeapartment(deptBean.getEntity());
				deptService.updateDeapartmentChild(deptBean.getEntity());
				deptBean.addMessage(Message.SUCCESS,
						msgSrc.getMessage("msg.save.success", null, locale));
				model.addAttribute("bean", deptBean);
				redirectAttributes.addFlashAttribute("bean",deptBean);
				return "redirect:/master_data/department/list";
			} catch (Exception e) {
				e.printStackTrace();
				return "master_data.department.edit";
			}
		} else {

			if(bindingResult.getFieldError("entity")!=null)
				deptBean.addMessage(Message.ERROR,
						bindingResult.getFieldError("entity").getDefaultMessage());
			model.addAttribute("bean", deptBean);

			return "master_data.department.edit";
		}
	}
	 */

	@RequestMapping(value="/department/edit/{id}",method=RequestMethod.GET)
	public String editView(@ModelAttribute(value="bean") DepartmentBean departmentBean,@PathVariable(value="id") Long id,Model model) throws Exception {
		
		if(id!=null){
			Department department=deptService.findById(id);
			departmentBean.setParentId(department.getParent()!=null?department.getParent().getDepartmentId():-1);
			departmentBean.setEntity(department);
		}
		model.addAttribute("systemConfig", systemConfig);
		model.addAttribute("bean", departmentBean);
		return "master_data.department.edit";
	}
	@RequestMapping(value="/department/edit/{id}",method=RequestMethod.POST)
	@Transactional
	public String editAction(@ModelAttribute(value = "bean") @Valid DepartmentBean departmentBean,
			BindingResult bindingResult, Model model,Locale locale,RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()){
			if(bindingResult.getFieldError("entity")!=null){
				departmentBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			}else{
				departmentBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
			return "master_data.department.edit";
		}
		Department department= departmentBean.getEntity();
		Long parentId=departmentBean.getParentId();
		if(parentId!=null&&parentId>0){
			Department parent=deptService.findById(parentId);
			if(parent!=null){
				department.setParent(parent);
			}
		}

		boolean saveNew=(department.getDepartmentId()==null);
		try{

			deptService.saveOrUpdateDeapartment(department);
			deptService.updateDeapartmentChild(department);
		}catch(Exception e){
			if(saveNew) 
				departmentBean.addMessage(Message.ERROR, messageSource.getMessage("msg.save.fail", null, locale));			
			else departmentBean.addMessage(Message.ERROR, messageSource.getMessage("msg.save.fail", null, locale));

		}
		if(saveNew) 
			departmentBean.addMessage(Message.SUCCESS, messageSource.getMessage("msg.save.success", null, locale));
		else 
			departmentBean.addMessage(Message.SUCCESS, messageSource.getMessage("msg.save.success", null, locale));
		model.addAttribute("bean",departmentBean);
		redirectAttributes.addFlashAttribute("bean",departmentBean);
		model.addAttribute("systemConfig", systemConfig);
		return "redirect:/master_data/department/list";
	}
	@RequestMapping(value="/department/view/{id}",method=RequestMethod.GET)
	public String view(@ModelAttribute(value="bean") DepartmentBean departmentBean,@PathVariable(value="id") Long id,Model model) throws Exception {
		if(id!=null){
			Department department=deptService.findById(id);
			departmentBean.setParentId(department.getParent()!=null?department.getParent().getDepartmentId():-1);
			departmentBean.setEntity(department);
		}
		model.addAttribute("bean", departmentBean);
		model.addAttribute("systemConfig", systemConfig);
		return "master_data.department.view";
	}
	@RequestMapping(value="/department/add",method=RequestMethod.GET)
	public String addView(@ModelAttribute(value="bean") DepartmentBean departmentBean,Model model) throws Exception {
		departmentBean.setParentId(new Long(-1));
		return editView(departmentBean, null,model);
	}

	@RequestMapping(value="/department/add",method=RequestMethod.POST)
	@Transactional
	public String addAction(@ModelAttribute(value="bean") @Valid DepartmentBean departmentBean,BindingResult bindingResult,Model model,Locale locale,RedirectAttributes redirectAttributes) {
		return editAction(departmentBean,bindingResult,model,locale,redirectAttributes);
	}

}
