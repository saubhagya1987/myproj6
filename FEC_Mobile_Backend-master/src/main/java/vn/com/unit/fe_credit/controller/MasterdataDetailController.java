package vn.com.unit.fe_credit.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.HobbyBean;
import vn.com.unit.fe_credit.bean.MasterdataDetailBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Masterdata;
import vn.com.unit.fe_credit.entity.MasterdataDetal;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MasterdataService;

@Controller
@RequestMapping("/system/masterdataDetail")
public class MasterdataDetailController {
	@Autowired
	MasterdataDetailService masterdataDetailService;
	@Autowired
	MasterdataService masterdatalService;
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
	public String listMasterData(@ModelAttribute(value = "bean") MasterdataDetailBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setListMasterdata(masterdatalService.findAllex());
		MasterdataDetailBean result = masterdataDetailService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "system.masterdataDetail.list"; 
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listMasterDataPost(@ModelAttribute(value = "bean") MasterdataDetailBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		MasterdataDetailBean result = masterdataDetailService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "system.masterdataDetail.list";
	}
	/*@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listStudentPost(@ModelAttribute(value = "bean") MasterdataDetailBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		MasterdataDetailBean result = masterdataDetailService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "master_data.Masterdata.list";
	}*/
	
	

	@RequestMapping(value = "/MasterdataDetail", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean") @Valid MasterdataDetailBean bean,
			@RequestParam(value = "masterdataDetailId", required = false) Long masterdataDetailId,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		bean.setSuccesorfail("fail");
		bean.setListMasterdata(masterdatalService.findAllex());
		if (masterdataDetailId != null) {
			bean.setEntity(masterdataDetailService.findById(masterdataDetailId));
		} else {

			bean.setEntity(new MasterdataDetal());
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.masterdataDetail.edit";
	}

	@RequestMapping(value = "/MasterdataDetail", method = RequestMethod.POST)
	public String saverSystemConfigTVController(
			@ModelAttribute(value = "bean") @Valid MasterdataDetailBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			RedirectAttributes redirectAttributes,HttpServletResponse response) throws Exception {
		bean.setListMasterdata(masterdatalService.findAllex());
		if (!bindingResult.hasErrors()) {
			// dung luu su lieu 
			MasterdataDetal masterdataDetal = bean.getEntity();
			// kiem tra ma da ton tai chua
			
			MasterdataDetal detal = masterdataDetailService.findNameAndMasterDuplicate(masterdataDetal.getName(), masterdataDetal.getMasterdata().getMasterdataId(), masterdataDetal.getMasterdatadetailId());
				if (detal != null) {
					bean.setSuccesorfail("fail");
					bean.addMessage(Message.ERROR,
							msgSrc.getMessage("Masterdata.Name.Fail", null, locale));
					model.addAttribute("bean", bean);
					return "master_data.masterdataDetail.edit";

				}
			
			// if(masterdataDetailService.findByMasterdataCode(Masterdata.getCode())!=null &&
			// Masterdata.getMasterdataId()==null ){
			// bean.addMessage(Message.ERROR,
			// msgSrc.getMessage("msg.save.fail", null, locale));
			// model.addAttribute("bean", bean);
			// return "master_data.Masterdata.addMasterdata";
			// }
			// else{

			/*if (bean.getEntity().getMasterdatadetailId() != null) {
				activityLogService.saveActivityLog(systemConfig.EDIT_Masterdata,
						systemConfig.Masterdata, locale, systemConfig.SYSTEM, null,
						userProfile.getAccount().getId().toString());
			} else {
				activityLogService.saveActivityLog(systemConfig.ADD_NEW_Masterdata,
						systemConfig.Masterdata, locale, systemConfig.SYSTEM, null,
						userProfile.getAccount().getId().toString());
			}*/
			
			masterdataDetailService.saveMasterdataDetal(masterdataDetal);
			bean.setSuccesorfail("succes");
			bean.addMessage(Message.SUCCESS,
					messageSource.getMessage("msg.save.success", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			// model.addAttribute("bean", bean);
			return "master_data.masterdataDetail.edit";

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
			return "master_data.masterdataDetail.edit";
			//
		}

	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean") MasterdataDetailBean bean, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (bean.getEntity().getMasterdatadetailId() != null) {
			try {
				if(bean.getEntity().getMasterdatadetailId() != null){
//					posService.deletePosLocation(bean.getEntity().getBranchid());
					masterdataDetailService.deleteMasterdataDetail(bean.getEntity().getMasterdatadetailId());
					
					bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
//				logger.info("can't delete Team id={} with ex={}", bean.getEntity().getId(), ex);
				bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/system/masterdataDetail/list";
	}

}
