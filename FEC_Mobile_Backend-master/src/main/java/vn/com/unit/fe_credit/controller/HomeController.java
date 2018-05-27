package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.UserAttempts;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.UserAttemptsService;
import vn.com.unit.fe_credit.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	AccountService accountService;

	@Autowired
	UserProfile userProfile;

	@Autowired
	MessageSource msgSrc;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	UserAttemptsService userAttemptsService;

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
		dateFormat.setLenient(false);
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		// Register it as custom editor for the Double type
		DoubleEditor doubleEditor = new DoubleEditor(locale, SystemConfig.NUMBER_PATTERN);
		binder.registerCustomEditor(Double.class, doubleEditor);
	}

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@Transactional
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "redirect:/dashboard/";
		// return "redirect:/userProfile/edit";

		// return homepage(locale, model);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Locale locale, Model model) {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		UserAttempts userAttempts = userAttemptsService.findByIpAddress(ipAddress);
		if (userAttempts != null && userAttempts.getAttempts() >= 3) {
			model.addAttribute("IS_ACTIVE_CAPTCHA", true);
		}

		return "login";
	}

	@RequestMapping(value = "/backdoor", method = RequestMethod.GET)
	public String backdoor(Locale locale, Model model) {
		return "backdoor";
	}

	@RequestMapping(value = "/userProfile/edit", method = RequestMethod.GET)
	public String editget(Model model, @ModelAttribute(value = "bean") AccountBean accountBean) throws Exception {
		model.addAttribute("bean", getAccountBean(accountBean));
		return "user.edit";
	}

	private AccountBean getAccountBean(AccountBean accountBean) {
		Account account = accountService.findByAccountName(userProfile.getAccount().getUsername());
		accountBean.setEntity(account);
		accountBean.setImage(account.getImagePath());
		return accountBean;
	}

	/**
	 * This method will create the initial values and render to the page edit a Bank.
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.GET)
	public String changePassword(Model model, HttpServletRequest request) throws Exception {
		Account account;
		AccountBean accountBean = new AccountBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			account = accountService.findById(Long.valueOf(request.getParameter("id")));
		} else {
			account = new Account();
		}
		accountBean.setEntity(account);
		model.addAttribute("bean", accountBean);
		return "user.changepassword";
	}

	/**
	 * This method will create the initial values and render to the page edit a Bank.
	 * 
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/changePassword", method = RequestMethod.POST)
	public String changePassword(@ModelAttribute(value = "bean") AccountBean accountBean, BindingResult bindingResult, Model model,
			HttpServletRequest request, Locale locale) throws Exception {
		accountBean.clearMessages();
		Account account = accountService.findById(userProfile.getAccount().getId());
		if (StringUtils.isEmpty(accountBean.getPasswordOld()) || !Utils.encryptMD5(accountBean.getPasswordOld()).equals(account.getPassword())) {
			bindingResult.rejectValue("passwordOld", "account.old.passsword.fail");
		}
		if (StringUtils.isEmpty(accountBean.getPasswordNew())) {
			bindingResult.rejectValue("passwordNew", "account.new.passsword.empty");
		}
		if (StringUtils.isEmpty(accountBean.getPasswordEnter())) {
			bindingResult.rejectValue("passwordEnter", "account.new.passsword.empty");
		}
		if (StringUtils.isNotEmpty(accountBean.getPasswordNew()) && StringUtils.isNotEmpty(accountBean.getPasswordEnter())
				&& !accountBean.getPasswordNew().equals(accountBean.getPasswordEnter())) {
			bindingResult.rejectValue("passwordNew", "account.new.passsword.fail");
			bindingResult.rejectValue("passwordEnter", "account.new.passsword.fail");
		}
		if (!bindingResult.hasErrors()) {
			try {
				account.setPassword(Utils.encryptMD5(accountBean.getPasswordNew()));
				accountService.saveAccount(account);
				accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
			} catch (Exception ex) {
				accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
				model.addAttribute("bean", accountBean);
				return "user.changepassword";
			}
			model.addAttribute("bean", accountBean);
			return "user.changepassword";
		} else {
			model.addAttribute("bean", accountBean);
			accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			return "user.changepassword";
		}
	}

	@RequestMapping(value = "/userProfile/edit", method = RequestMethod.POST)
	public String saveUserProfile(@ModelAttribute(value = "bean") AccountBean accountBean, BindingResult bindingResult, Model model,
			HttpServletRequest request, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		try {
			if (StringUtils.isEmpty(accountBean.getEntity().getFullName())) {
				bindingResult.rejectValue("entity.fullName", "javax.validation.constraints.NotNull.message");
			}
		} catch (Exception e) {

		}

		Account account = accountService.findByAccountName(userProfile.getAccount().getUsername());
		account.setFullName(accountBean.getEntity().getFullName());
		account.setBirthday(accountBean.getEntity().getBirthday());
		account.setMobile(accountBean.getEntity().getMobile());
		account.setExt(accountBean.getExt());
		if (StringUtils.isNotEmpty(accountBean.getImage())) {
			String fileTempName = accountBean.getImage();
			String newName = Utils.moveTempToUploadFolder(fileTempName, systemConfig);
			account.setImagePath(newName);
		} else {
			account.setImagePath(null);
		}
		if (!bindingResult.hasErrors()) {
			accountService.saveAccount(account);
		}
		accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		accountBean = getAccountBean(accountBean);
		model.addAttribute("bean", accountBean);
		return "user.edit";
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String homepage(Locale locale, Model model) {
		return "index";
	}

	@RequestMapping(value = "/dashboard", method = { RequestMethod.GET, RequestMethod.POST })
	public String goDashboard(Locale locale, Model model) {
		return "index";
	}

}
