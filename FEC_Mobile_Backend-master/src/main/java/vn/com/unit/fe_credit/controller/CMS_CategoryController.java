package vn.com.unit.fe_credit.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.unit.fe_credit.bean.CMSCategoryBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.CMSCategoryService;
import vn.com.unit.fe_credit.service.StatusTableService;

@Controller
@RequestMapping("/master_data/cms_category")
public class CMS_CategoryController {
	@Autowired
	CMSCategoryService cms_CategoryService;
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
	@Autowired
	ActivityLogService activityLogService;
	@Autowired
	private UserProfile userProfile;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listCms_category(
			@ModelAttribute(value = "bean") CMSCategoryBean bean,
			@RequestParam(value = "search_form_CMSCategory", required = false) Long hobbyId,
			Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig
				.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setListStatusTable(statusTableService.findAllex());
		CMSCategoryBean result = cms_CategoryService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "master_data.cms_category.list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listCms_category(
			@ModelAttribute(value = "bean") CMSCategoryBean bean, Model model,
			Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig
				.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		CMSCategoryBean result = cms_CategoryService.search(bean);
		bean.setListCMS_Category(result.getListResult());
		model.addAttribute("bean", result);
		if (bean.getListCMS_Category().isEmpty()) {
			bean.addMessage(Message.INFO,
					msgSrc.getMessage("msg.search.nodata", null, locale));

		}

		response.setContentType("text/html; charset=UTF-8");
		return "master_data.cms_category.list";
	}

	@RequestMapping(value = "/cms_categoryEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean") @Valid CMSCategoryBean bean,
			@RequestParam(value = "cms_categoryId", required = false) Long cms_categoryId,
			Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig
				.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setSuccesorfail("fail");

		if (cms_categoryId != null) {
			bean.setEntity(cms_CategoryService.findById(cms_categoryId));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.cms_category.edit";
	}

	@RequestMapping(value = "/cms_categoryView", method = RequestMethod.GET)
	public String itemsGetView(
			@ModelAttribute(value = "bean") @Valid CMSCategoryBean bean,
			@RequestParam(value = "cms_categoryId", required = false) Long cms_categoryId,
			Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig
				.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setSuccesorfail("fail");

		if (cms_categoryId != null) {
			bean.setEntity(cms_CategoryService.findById(cms_categoryId));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.cms_category.view";
	}

	@RequestMapping(value = "/cms_categoryEdit", method = RequestMethod.POST)
	public String saverSystemConfigTVController(

	@ModelAttribute(value = "bean") @Valid CMSCategoryBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		bean.setListStatusTable(statusTableService.findAllex());
		if (!bindingResult.hasErrors()) {
			// dung luu su lieu
			CMSCategory cmsCategoryBean = bean.getEntity();
			// kiem tra ma da ton tai chua
			if (bean.getEntity().getCms_categoryId() == null) {
					activityLogService.saveActivityLog(
							systemConfig.ADD_NEW_CMS_CATEGORY,
							systemConfig.CMS_CATEGORY, locale,
							systemConfig.SYSTEM, null, userProfile.getAccount()
									.getId().toString());
			} else {
					activityLogService.saveActivityLog(
							systemConfig.EDIT_CMS_CATEGORY,
							systemConfig.CMS_CATEGORY, locale,
							systemConfig.SYSTEM, null, userProfile.getAccount()
									.getId().toString());
			}
			cms_CategoryService.saveCMS_Category(bean.getEntity());
			bean.setSuccesorfail("succes");
			bean.addMessage(Message.SUCCESS,
					messageSource.getMessage("msg.save.success", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);

			return "master_data.cms_category.edit";

		} else {
			bean.setSuccesorfail("fail");
			bean.addMessage(Message.ERROR,
					msgSrc.getMessage("msg.save.fail", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "master_data.cms_category.edit";
		}

	}

}
