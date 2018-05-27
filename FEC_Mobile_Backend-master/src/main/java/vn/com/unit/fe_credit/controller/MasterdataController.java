package vn.com.unit.fe_credit.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import vn.com.unit.fe_credit.bean.Message;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.BranchBean;
import vn.com.unit.fe_credit.bean.MasterdataBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Masterdata;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.MasterdataService;

@Controller
@RequestMapping("/system/masterdata")
public class MasterdataController {
	@Autowired
	MasterdataService masterdataService;
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
	ActivityLogService activityLogService;
	@Autowired
	private UserProfile userProfile;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listStudent(@ModelAttribute(value = "bean") MasterdataBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		MasterdataBean result = masterdataService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "system.masterdata.list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listStudentPost(@ModelAttribute(value = "bean") MasterdataBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		MasterdataBean result = masterdataService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "system.masterdata.list";
	}
	
	

	@RequestMapping(value = "/MasterdataEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean") @Valid MasterdataBean bean,
			@RequestParam(value = "masterdataId", required = false) Long masterdataId,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		bean.setSuccesorfail("fail");
		if (masterdataId != null) {
			bean.setEntity(masterdataService.findById(masterdataId));
		} else {

			bean.setEntity(new Masterdata());
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.Masterdata.edit";
	}

	@RequestMapping(value = "/MasterdataEdit", method = RequestMethod.POST)
	public String saverSystemConfigTVController(
			@ModelAttribute(value = "bean") @Valid MasterdataBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			RedirectAttributes redirectAttributes,HttpServletResponse response) throws Exception {
		if (!bindingResult.hasErrors()) {
			// dung luu su lieu 
			Masterdata Masterdata = bean.getEntity();
			// kiem tra ma da ton tai chua
			if (bean.getEntity().getMasterdataId() == null) {
				masterdataService.findByMasterdataName(bean);
				int a = bean.getListResult().size();
				if (bean.getListResult().size() > 0) {
					bean.setSuccesorfail("fail");
					bean.addMessage(Message.ERROR,
							msgSrc.getMessage("Masterdata.Name.Fail", null, locale));
					model.addAttribute("bean", bean);
					return "master_data.Masterdata.edit";

				}
			}
			// if(masterdataService.findByMasterdataCode(Masterdata.getCode())!=null &&
			// Masterdata.getMasterdataId()==null ){
			// bean.addMessage(Message.ERROR,
			// msgSrc.getMessage("msg.save.fail", null, locale));
			// model.addAttribute("bean", bean);
			// return "master_data.Masterdata.addMasterdata";
			// }
			// else{

			/*if (bean.getEntity().getMasterdataId() != null) {
				activityLogService.saveActivityLog(systemConfig.EDIT_Masterdata,
						systemConfig.Masterdata, locale, systemConfig.SYSTEM, null,
						userProfile.getAccount().getId().toString());
			} else {
				activityLogService.saveActivityLog(systemConfig.ADD_NEW_Masterdata,
						systemConfig.Masterdata, locale, systemConfig.SYSTEM, null,
						userProfile.getAccount().getId().toString());
			}*/
			masterdataService.saveMasterdata(Masterdata);
			bean.setSuccesorfail("succes");
			bean.addMessage(Message.SUCCESS,
					messageSource.getMessage("msg.save.success", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			// model.addAttribute("bean", bean);
			return "master_data.Masterdata.edit";

			// }
		} else {

			bean.setSuccesorfail("fail");
			if (bindingResult.getFieldError("entity") != null) {
				bean.addMessage(Message.ERROR,
						bindingResult.getFieldError("entity")
								.getDefaultMessage());
			} else {
				bean.addMessage(Message.ERROR,
						msgSrc.getMessage("msg.save.fail", null, locale));
			}
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "master_data.Masterdata.edit";
			//
		}

	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean") MasterdataBean bean, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (bean.getEntity().getMasterdataId() != null) {
			try {
				if(bean.getEntity().getMasterdataId() != null){
//					posService.deletePosLocation(bean.getEntity().getBranchid());
					masterdataService.deleteMasterdata(bean.getEntity().getMasterdataId());
					
					bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
//				logger.info("can't delete Team id={} with ex={}", bean.getEntity().getId(), ex);
				bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/system/masterdata/list";
	}

}
