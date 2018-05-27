package vn.com.unit.fe_credit.controller.v1;

import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.service.v1.UserService;

@Controller
@RequestMapping("/system")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private MessageSource msgSrc;
	
	Logger logger = Logger.getLogger(UserController.class.getName());
	
	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		binder = userService.getBinder(binder, locale, request);		
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.POST)
	public String listPost(@ModelAttribute(value = "bean") AccountBean accountBean, Model model, HttpServletRequest request, Locale locale) throws Exception {
		
		model = userService.getUsersForPost(model, accountBean, request, locale);
		
		return "system.user.list";
	}
	
	@RequestMapping(value = "/user/upload", method = RequestMethod.POST)
	public String uploadUser(@ModelAttribute(value = "bean") AccountBean accountBean,Model model, @RequestParam("usersExcel") MultipartFile excelfile, HttpServletRequest request, Locale locale) throws Exception {
		
		model = userService.uploadExcelData(model, locale, excelfile);
		
		return "system.user.error.list";
	}

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String listget(@ModelAttribute(value = "bean") AccountBean accountBean, Model model,
			@RequestParam(value = "message", required = false) String message, HttpServletRequest request, Locale locale) throws Exception {
		
		model = userService.getUsersForGet(model, accountBean, request, locale, message);
		
		return "system.user.list";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public String edit_get(@RequestParam(required = false) Long id, HttpServletRequest request, Locale locale, Model model,String resCode) throws Exception {
		
		System.out.println("user edit req recieved with resCode "+resCode);
		AccountBean accountBean = new AccountBean();
		if(resCode!=null){
			if( resCode.equals("200")){
				logger.info("successfully updated");
			accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.update.success", null, locale));
			}else{
				logger.info("update Failure");
			accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.update.fail", null, locale));
			}
		}
		model = userService.addOrEditForGet(model, request, id,accountBean);
		
		return "system.user.edit";
	}
	
	
	@RequestMapping(value = "/user/delete", method = RequestMethod.GET)
	public String delete_get(@RequestParam(required = false) Long id, HttpServletRequest request, Model model, Locale locale) throws Exception {
		
		model = userService.deleteUserById(model, id, locale);		
		
		return "redirect:/system/user/list";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public String edit_Post(@ModelAttribute(value = "bean")  AccountBean accountBean, BindingResult bindingResult, Model model,	HttpServletRequest request,
			@RequestParam(value = "chkTeamLeft", required = false) List<Long> chkTeamLeft, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		
		model = userService.addOrEditForPost(model, request, accountBean, bindingResult, chkTeamLeft, locale, redirectAttributes);
		
		if(model.asMap().get("editAjax").equals("system.user.edit.ajax"))
			return "system.user.edit.ajax";
		
		return "system.user.edit.ajax";
	}

	@RequestMapping(value = "/user/unlock", method = RequestMethod.GET)
	public String unlock_user(@ModelAttribute(value = "bean")  AccountBean accountBean,Model model ,  Locale locale,
			@RequestParam(required = true)  String email , Long id) throws Exception {
		System.out.println("unlock req recieved for user email & id "+email+" "+id);
		try{
			 userService.unlockUser(model,email,accountBean,locale);
			return "redirect:/system/user/edit?id="+id+"&resCode="+200;
		}catch(Exception e){
			logger.info("Exception in unlock user for email "+email);
			logger.info(ExceptionUtils.getStackTrace(e));
			return "redirect:/system/user/edit?id="+id+"&resCode="+100;
		}
	
	}
	
	@RequestMapping(value = "/user/clearDevice", method = RequestMethod.GET)
	public String clear_device(@ModelAttribute(value = "bean")  AccountBean accountBean, Model model ,  Locale locale,
			@RequestParam(required = true)  String email , Long id , String appId) throws Exception {
		System.out.println("clearDevice req recieved for user "+email+"& appid "+appId);
		logger.info("clearDevice req recieved for user "+email);
		try{
		 userService.clearDevice(model,email,accountBean,locale,appId);
		 return "redirect:/system/user/edit?id="+id+"&resCode="+200;
		}catch(Exception e){
			logger.info("Exception in clear device for email "+email);
			logger.info(ExceptionUtils.getStackTrace(e));
			 return "redirect:/system/user/edit?id="+id+"&resCode="+100;
		}
	}

	
}
