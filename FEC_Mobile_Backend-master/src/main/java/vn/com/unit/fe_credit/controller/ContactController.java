package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.ContactBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Contact;
import vn.com.unit.fe_credit.service.ContactActivityLogService;
import vn.com.unit.fe_credit.service.ContactService;
import vn.com.unit.fe_credit.service.LoanActivityLogService;

@Controller
@RequestMapping(value = "master_data/contact")
public class ContactController {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	ContactService contactService;
	@Autowired
	MessageSource msgSrc;
	@Autowired
	private ContactActivityLogService contactactivityLogService;
	@Autowired
	private UserProfile userProfile;
	
	@InitBinder
	public void dateBinder(WebDataBinder binder , Locale locale, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String getContact(Model model,
			@ModelAttribute(value = "bean") ContactBean bean) throws Exception {
		
		Contact contact =contactService.findByAll();
		bean.setEntity(contact);
		model.addAttribute("bean", bean);

		return "master_data.contact.list";
	}

	@RequestMapping(value = "/show", method = RequestMethod.POST)
	public String saveContact(@ModelAttribute(value = "bean") ContactBean bean,
			BindingResult bindingResult, Model model,
			HttpServletRequest request, Locale locale,
			RedirectAttributes redirectAttributes) throws Exception {

		bean.clearMessages();

		Contact contact = bean.getEntity();
		
		if (!bindingResult.hasErrors()) {
			try {
				contactService.save(contact);
				contactactivityLogService.saveContactActivityLog(
						systemConfig.CONTENT_CHANGE,
						systemConfig.CONTENT, locale, systemConfig.SYSTEM,
						null, userProfile.getAccount().getId().toString());
				bean.addMessage(Message.SUCCESS,
						msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", bean);
				return "redirect:/master_data/contact/show?id=".concat(String.valueOf(contact.getContactID()));

			} catch (Exception ex) {
				bean.addMessage(Message.ERROR,
						msgSrc.getMessage("msg.save.fail", null, locale));
				redirectAttributes.addFlashAttribute("bean", bean);
				return "master_data.contact.list";
			}
		} else {
			redirectAttributes.addFlashAttribute("bean", bean);
			bean.addMessage(Message.ERROR,
					msgSrc.getMessage("msg.save.fail", null, locale));
			return "master_data.contact.list";
		}

	}

}
