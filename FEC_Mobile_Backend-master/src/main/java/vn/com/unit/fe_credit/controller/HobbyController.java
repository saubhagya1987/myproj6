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
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.CrunchifyMySQLJDBCConnection;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.HobbyActivityLogService;
import vn.com.unit.fe_credit.service.HobbyService;

@Controller
@RequestMapping("/master_data/hobby")
public class HobbyController {
	@Autowired
	HobbyService hobbyService;
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
	HobbyActivityLogService hobbyActivityLogService;
	@Autowired
	private UserProfile userProfile;
	
	
	

	@RequestMapping(value = "/list", method = RequestMethod.GET )
	public String listHobby(@ModelAttribute(value = "bean") HobbyBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		HobbyBean result = hobbyService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "master_data.hobby.list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listHobbyPost(@ModelAttribute(value = "bean") HobbyBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		HobbyBean result = hobbyService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "master_data.hobby.list";
	}
	
	

	@RequestMapping(value = "/hobbyEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean") @Valid HobbyBean bean,
			@RequestParam(value = "hobbyId", required = false) Long hobbyId,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		bean.setSuccesorfail("fail");
		if (hobbyId != null) {
			bean.setEntity(hobbyService.findById(hobbyId));
		} else {

			bean.setEntity(new Hobby());
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.hobby.edit";
	}
	
	@RequestMapping(value = "/hobbyEdit", method = RequestMethod.POST)
	public String saverHobby(
			@ModelAttribute(value = "bean") @Valid HobbyBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			RedirectAttributes redirectAttributes,HttpServletResponse response) throws Exception {
		if (!bindingResult.hasErrors()) {
			// dung luu su lieu
			Hobby hobby = bean.getEntity();
			if (bean.getEntity().getHobbyId() == null) {
				hobbyActivityLogService.saveHobbyActivityLog(systemConfig.ADD_NEW_HOBBY,
							systemConfig.HOBBY, locale, systemConfig.SYSTEM,
							null, userProfile.getAccount().getId().toString());
			}else{
				hobbyActivityLogService.saveHobbyActivityLog(systemConfig.EDIT_HOBBY,
						systemConfig.HOBBY, locale, systemConfig.SYSTEM,
						null, userProfile.getAccount().getId().toString());
			}
			hobbyService.saveHobby(hobby);
			bean.setSuccesorfail("succes");
			bean.addMessage(Message.SUCCESS,
					messageSource.getMessage("msg.save.success", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "master_data.hobby.addHobby";
		} else {
			bean.setSuccesorfail("fail");
			bean.addMessage(Message.ERROR,
					msgSrc.getMessage("msg.save.fail", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "master_data.hobby.addHobby";
		}
}
}
