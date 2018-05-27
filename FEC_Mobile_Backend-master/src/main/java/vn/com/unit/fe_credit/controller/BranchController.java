package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.BranchBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.TeamBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.BranchService;
import vn.com.unit.fe_credit.service.POSService;
import vn.com.unit.fe_credit.service.StatusTableService;

@Controller
@RequestMapping("/master_data/branch")
public class BranchController {
	@Autowired
	BranchService branchService;
	@Autowired
	POSService posService;
	@Autowired
	private MessageSource msgSrc;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	AccountTeamService accountTeamService;
	@Autowired
	AccountService accountService;
	@Autowired
	MessageSource messageSource;
	@Autowired
	StatusTableService statusTableService;
	
	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale,
			HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request
				.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale,
				"#,###.##"));
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listCms_category(@ModelAttribute(value = "bean") BranchBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setListStatusTable(statusTableService.findAllex());
		BranchBean result = branchService.search(bean);
		bean.setListBearch(result.getListBearch());
		
//		BranchBean result = branchService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "master_data.branch.list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listStudent(@ModelAttribute(value = "bean") BranchBean bean,
			Model model, Locale locale, HttpServletRequest request)
			throws Exception {
//		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		BranchBean result = branchService.search(bean);
		bean.setListBearch(result.getListResult());
//		bean.setListResult(branchService.findAllex());
		if(bean.getListBearch().isEmpty()){
			bean.addMessage(Message.INFO,
					msgSrc.getMessage("msg.search.nodata", null, locale));
			
		}
		model.addAttribute("bean", bean);
		return "master_data.branch.list";
	}
	
	@RequestMapping(value = "/branchEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean")
			@Valid BranchBean bean,
			@RequestParam(value = "branchid", required = false) Long branchid,
			Model model, Locale locale, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setSuccesorfail("fail");
		
		if (branchid != null) {
			bean.setEntity(branchService.findById(branchid));
		}
		model.addAttribute("bean", bean);		
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.branch.edit";
	}
//
//	@RequestMapping(value = "/cms_categoryEdit", method = RequestMethod.POST)
//	@Transactional
//	public String saverSystemConfigTVController(
//
//	@ModelAttribute(value = "bean") @Valid CMSCategoryBean bean,
//			BindingResult bindingResult, Model model, Locale locale,
//			HttpServletRequest request) throws Exception {
//		bean.setListStatusTable(statusTableService.findAllex());
//		if (!bindingResult.hasErrors()) {
//			// dung luu su lieu
//			CMSCategory cmsCategoryBean = bean.getEntity();
//			// kiem tra ma da ton tai chua
//			
////			hobbyService.findByHobbyCode(hobby.getCode());
////			if(hobbyService.findByHobbyCode(hobby.getCode())!=null && hobby.getHobbyId()==null ){
////				bean.addMessage(Message.ERROR,
////						msgSrc.getMessage("msg.save.fail", null, locale));
////				model.addAttribute("bean", bean);
////				return "master_data.hobby.addHobby";
////			}
////			else{
//			cms_CategoryService.saveCMS_Category(bean.getEntity());
//			bean.setSuccesorfail("succes");
//			bean.addMessage(Message.SUCCESS,messageSource.getMessage("msg.save.success", null, locale));
//			model.addAttribute("bean", bean);
//			return "master_data.cms_category.edit";
//		
////			}
//		} else {
//			bean.setSuccesorfail("fail");
//			bean.addMessage(Message.ERROR,
//					msgSrc.getMessage("msg.save.fail", null, locale));
//			model.addAttribute("bean", bean);
//			return "master_data.cms_category.edit";
//			//
//		}
//
//	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean") BranchBean bean, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (bean.getEntity().getBranchid() != null) {
			try {
				if(bean.getEntity().getBranchid() != null){
//					posService.deletePosLocation(bean.getEntity().getBranchid());
					branchService.deleteBranchLocation(bean.getEntity().getBranchid());
					
					bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
//				logger.info("can't delete Team id={} with ex={}", bean.getEntity().getId(), ex);
				bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/master_data/branch/list";
	}
	
}
