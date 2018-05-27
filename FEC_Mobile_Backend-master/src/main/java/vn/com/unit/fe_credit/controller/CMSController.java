package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.CMSBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.CMSCategory;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.CMSActivityLogService;
import vn.com.unit.fe_credit.service.CMSCategoryService;
import vn.com.unit.fe_credit.service.CMSService;
import vn.com.unit.fe_credit.service.HobbyService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.PushNotificationService;
import vn.com.unit.fe_credit.service.StatusTableService;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.webservice.Hotline;

@Controller
@RequestMapping("/master_data/cms")
public class CMSController {

	@Autowired
	StatusTableService statusTableService;

	@Autowired
	MasterdataDetailService masterdataDetailService;

	@Autowired
	CMSCategoryService cms_CategoryService;

	@Autowired
	HobbyService hobbyService;

	@Autowired
	CMSService cmsService;

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
	private UserProfile userProfile;

	@Autowired
	CMSActivityLogService cmsActivityLogService;

	@Autowired
	PushNotificationService pushNotificationService;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

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

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String itemsGet(@ModelAttribute(value = "bean") @Valid CMSBean bean, @RequestParam(value = "cmsId", required = false) Long cmsId,
			Model model, Locale locale, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// group CMS
		List<Hotline> hotline = null;
		hotline = masterdataDetailService.findDetail("GroupCMS");
		if (hotline == null) {
			hotline = new ArrayList<Hotline>();
		}
		List<Long> cmsCategoryIds = new ArrayList<Long>();
		List<CMSCategory> cmsCategory = null;
		List<CMSCategory> cmsCategoryByStock = null;
		List<Long> cmsHobbyIds = new ArrayList<Long>();
		List<Hobby> listCMSHobbyByStock = new ArrayList<Hobby>();
		cmsCategory = cms_CategoryService.findInStatic();
		String CMSView = "";
		String CMSHobbyView = "";
		if (cmsId == null) {
			cmsCategory = cms_CategoryService.searchInStatic();
			cmsCategoryByStock = cms_CategoryService.searchInStatic();
			listCMSHobbyByStock = hobbyService.searchInStatic();
		}
		if (cmsId != null) {
			CMSEmtity emtity = cmsService.findById(cmsId);
			bean.setEntity(emtity);
			cmsCategory = cms_CategoryService.searchInStatic();
			cmsCategoryByStock = cms_CategoryService.searchInStatic();
			listCMSHobbyByStock = hobbyService.searchInStatic();

			for (String str : bean.getEntity().getCmsCategory().split(";")) {
				if (StringUtils.isNotEmpty(str)) {
					cmsCategoryIds.add(Long.parseLong(str));
				}
			}
			String getCodeCMS = "";
			bean.setCmsCategoryIds(cmsCategoryIds);
			for (Long idCMS : cmsCategoryIds) {
				for (CMSCategory searchID : cmsCategoryByStock) {
					if (searchID.getCms_categoryId().equals(idCMS)) {
						getCodeCMS += searchID.getCode();
					}
				}

			}
			if (StringUtils.isNotEmpty(getCodeCMS)) {
				CMSView = getCodeCMS.substring(0, getCodeCMS.length() - 1);
			}
			/*
			 * CMSView=getCodeCMS.substring(0, getCodeCMS.length()-1); bean.setCMSView(CMSView);
			 */

			// list check hobby
			bean.setListCMSHobbyByStock(listCMSHobbyByStock);
			if (bean.getListCMSHobbyByStock() != null) {
				if (!bean.getEntity().getCmsHobby().equalsIgnoreCase(";")) {

					for (String strHobby : bean.getEntity().getCmsHobby().split(";")) {
						if (StringUtils.isNotEmpty(strHobby)) {
							cmsHobbyIds.add(Long.parseLong(strHobby));
						}
					}
					String getCodeCMSHobby = "";
					bean.setCmsHobbyIds(cmsHobbyIds);
					for (Long idCMSHobby : cmsHobbyIds) {
						for (Hobby searchID : listCMSHobbyByStock) {
							if (searchID.getHobbyId().equals(idCMSHobby)) {
								getCodeCMSHobby += searchID.getCode();
							}
						}

					}
					if (StringUtils.isNotEmpty(getCodeCMSHobby)) {
						CMSHobbyView = getCodeCMSHobby.substring(0, getCodeCMSHobby.length() - 1);
					}

				}
				bean.setCMSHobbyView(CMSHobbyView);

			}
		}

		// for (CMSCategory project : cmsCategory) {
		// cmsCategoryIds.add(project.getCms_categoryId());
		// }
		// lay list status
		bean.setListStatusTable(statusTableService.findAllex());
		// lay list cms category
		bean.setListCMSCategoryByStock(cmsCategoryByStock);
		bean.setListCMSHobbyByStock(listCMSHobbyByStock);
		// bean.setListCMSCategory(cms_CategoryService.findInStatic());

		// Integer pagesize =
		// Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		// bean.setLimit(pagesize);
		// bean.setSuccesorfail("fail");

		if (cmsId != null) {
			bean.setEntity(cmsService.findById(cmsId));
		}

		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		model.addAttribute("hotline", hotline);
		return "master_data.cms.edit";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String itemsGetView(@ModelAttribute(value = "bean") @Valid CMSBean bean, @RequestParam(value = "cmsId", required = false) Long cmsId,
			Model model, Locale locale, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// lay list status
		bean.setListStatusTable(statusTableService.findAllex());
		// lay list cms category

		bean.setListCMSCategory(cms_CategoryService.findInStatic());
		List<CMSCategory> cmsCategoryByStock = null;
		List<Long> cmsCategoryIds = new ArrayList<Long>();
		List<CMSCategory> cmsCategory = null;
		List<Long> cmsHobbyIds = new ArrayList<Long>();
		List<Hobby> listCMSHobbyByStock = new ArrayList<Hobby>();
		String CMSView = "";
		String CMSViewHobby = "";
		listCMSHobbyByStock = hobbyService.searchInStatic();
		bean.setListCMSHobbyByStock(listCMSHobbyByStock);
		// Integer pagesize =
		// Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		// bean.setLimit(pagesize);
		// bean.setSuccesorfail("fail");

		if (cmsId != null) {
			cmsCategory = cms_CategoryService.searchInStatic();
			CMSEmtity emtity = cmsService.findById(cmsId);
			bean.setEntity(emtity);
			cmsCategoryByStock = cms_CategoryService.searchInStatic();
			// cmscatelogry
			for (String str : bean.getEntity().getCmsCategory().split(";")) {
				if (StringUtils.isNotEmpty(str)) {
					cmsCategoryIds.add(Long.parseLong(str));
				}
			}
			String CMSViewFor = "";
			for (Long CMSId : cmsCategoryIds) {
				for (CMSCategory getCMDIs : cmsCategoryByStock) {
					if (CMSId.equals(getCMDIs.getCms_categoryId())) {
						CMSViewFor += getCMDIs.getCode();
						CMSViewFor += "-";
						CMSViewFor += getCMDIs.getName();
						CMSViewFor += " ; ";
					}
				}

			}
			// list cms hobby
			if (!bean.getEntity().getCmsHobby().equalsIgnoreCase(";")) {
				for (String strHobby : bean.getEntity().getCmsHobby().split(";")) {
					if (StringUtils.isNotEmpty(strHobby)) {
						cmsHobbyIds.add(Long.parseLong(strHobby));
					}
				}
				String CMSViewHobbyFor = "";
				for (Long CMSHobbyId : cmsHobbyIds) {
					for (Hobby getCMDHobbyIs : listCMSHobbyByStock) {
						if (CMSHobbyId.equals(getCMDHobbyIs.getHobbyId())) {
							CMSViewHobbyFor += getCMDHobbyIs.getCode();
							CMSViewHobbyFor += "-";
							CMSViewHobbyFor += getCMDHobbyIs.getName();
							CMSViewHobbyFor += " ; ";
						}
					}

				}
				if (StringUtils.isNotEmpty(CMSViewHobbyFor)) {
					CMSViewHobby = CMSViewHobbyFor.substring(0, CMSViewHobbyFor.length() - 1);
				}
				/*
				 * CMSViewHobby=CMSViewHobbyFor.substring(0, CMSViewHobbyFor.length()-2);
				 * bean.setCMSHobbyView(CMSViewHobby);
				 */
			}
			if (StringUtils.isNotEmpty(CMSViewFor)) {
				CMSView = CMSViewFor.substring(0, CMSViewFor.length() - 2);
				bean.setCMSView(CMSView);
			}

			bean.setListCMSCategoryByStock(cmsCategoryByStock);
			bean.setCmsCategoryIds(cmsCategoryIds);
			bean.setEntity(cmsService.findById(cmsId));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);

		return "master_data.cms.view";
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public String listStudent(@ModelAttribute(value = "bean") @Valid CMSBean bean, BindingResult bindingResult, Model model, Locale locale,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws Exception {

		bean.setListCMSCategory(cms_CategoryService.searchInStatic());
		bean.setListStatusTable(statusTableService.findAllex());
		String CmsCategoryId = ";";
		String CmsHpbbyId = ";";
		if (bean.getCmsCategoryIds() != null) {
			for (Long cmsCategory : bean.getCmsCategoryIds()) {
				CmsCategoryId += cmsCategory.toString();
				CmsCategoryId += ";";
			}
		}

		if (bean.getCmsHobbyIds() != null) {
			for (Long cmsHobby : bean.getCmsHobbyIds()) {
				CmsHpbbyId += cmsHobby.toString();
				CmsHpbbyId += ";";
			}
		}
		System.out.println("CmsCategoryId : " + CmsCategoryId);
		// kiem tra co loi k

		if (!bindingResult.hasErrors()) {

			if (StringUtils.isNotEmpty(bean.getEntity().getImage())) {
				String fileTempName = bean.getEntity().getImage();
				String newName = Utils.moveTempToUploadFolder(fileTempName, systemConfig);
				bean.getEntity().setImage(newName);
			} else {
				bean.getEntity().setImage(null);
			}

			if (StringUtils.isNotEmpty(bean.getEntity().getImageLong())) {
				String fileTempNameLong = bean.getEntity().getImageLong();
				String newNameLong = Utils.moveTempToUploadFolder(fileTempNameLong, systemConfig);
				bean.getEntity().setImageLong(newNameLong);
			} else {
				bean.getEntity().setImageLong(null);
			}

			if (StringUtils.isNotEmpty(bean.getEntity().getImage_en())) {
				String fileTempNameEN = bean.getEntity().getImage_en();
				String newNameEN = Utils.moveTempToUploadFolder(fileTempNameEN, systemConfig);
				bean.getEntity().setImage_en(newNameEN);
			} else {
				bean.getEntity().setImage_en(null);
			}

			if (StringUtils.isNotEmpty(bean.getEntity().getImage_long_en())) {
				String fileTempNameLongEN = bean.getEntity().getImage_long_en();
				String newNameLongEN = Utils.moveTempToUploadFolder(fileTempNameLongEN, systemConfig);
				bean.getEntity().setImage_long_en(newNameLongEN);
			} else {
				bean.getEntity().setImage_long_en(null);
			}

			// luu
			bean.getEntity().setCreateDate(new Date());
			bean.getEntity().setCmsCategory(CmsCategoryId);
			bean.getEntity().setCmsHobby(CmsHpbbyId);

			CMSEmtity cmsEmtity = bean.getEntity();
			boolean isAddNew = cmsService.saveCMS(cmsEmtity);

			if (isAddNew) {
				cmsActivityLogService.saveCMSActivityLog(SystemConfig.ADD_NEW_CMS, SystemConfig.CMS, locale, SystemConfig.SYSTEM,
						String.valueOf(bean.getEntity().getCmsId()), userProfile.getAccount().getId().toString());
			} else {
				cmsActivityLogService.saveCMSActivityLog(SystemConfig.EDIT_CMS, SystemConfig.CMS, locale, SystemConfig.SYSTEM,
						String.valueOf(bean.getEntity().getCmsId()), userProfile.getAccount().getId().toString());
			}

			// Push message to all device
			try {
				Boolean isPushWhenSave = bean.getIsPushWhenSave();
				if (BooleanUtils.isTrue(isPushWhenSave)) {

					String pushTitle = bean.getPushTitle();
					String pushDescription = bean.getPushDescription();

					long messageId = cmsService.pushMessageToAllDevice(bean.getEntity().getCmsId(), pushTitle, pushDescription);
					pushNotificationService.sendBroadcastNotification(pushDescription, bean.getEntity().getCmsId());

					cmsEmtity.setMessageId(messageId);
					cmsService.saveCMS(cmsEmtity);
				}
			} catch (Exception e) {
				// Do nothing
			}

			bean.addMessage(Message.SUCCESS, messageSource.getMessage("msg.save.success", null, locale));
			redirectAttributes.addFlashAttribute("bean", bean);
			// model.addAttribute("bean", bean);
			return "redirect:/master_data/cms/list";

		} else {
			List<CMSCategory> cmsCategoryByStock = null;
			cmsCategoryByStock = cms_CategoryService.searchInStatic();
			bean.setListCMSCategoryByStock(cmsCategoryByStock);

			List<Hobby> listCMSHobbyByStock = null;
			listCMSHobbyByStock = hobbyService.searchInStatic();
			bean.setListCMSHobbyByStock(listCMSHobbyByStock);
			bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			model.addAttribute("bean", bean);
			return "master_data.cms.edit";
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listCMSDao(@ModelAttribute(value = "bean") @Valid CMSBean bean, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		// lay list status
		bean.setListStatusTable(statusTableService.findAllex());
		// lay list cms category
		bean.setListCMSCategory(cms_CategoryService.findAllex());
		bean.setStatus(1);
		CMSBean result = cmsService.searchAll(bean);
		model.addAttribute("bean", result);
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.cms.list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listCMSDaoPost(@ModelAttribute(value = "bean") @Valid CMSBean bean, Model model, Locale locale, HttpServletRequest request)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		// lay list status
		bean.setListStatusTable(statusTableService.findAllex());
		// lay list cms category
		bean.setListCMSCategory(cms_CategoryService.findAllex());
		// bean.setStatus(1);
		CMSBean result = null;
		// lay va kiem tra du lieu theo tim kiem
		result = cmsService.search(bean);
		// if(result.){
		//
		//
		// }
		bean.setListCMS(result.getListResult());

		if (bean.getListCMS().isEmpty()) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));

		}

		model.addAttribute("bean", bean);
		return "master_data.cms.list";
	}

	@RequestMapping(value = "/updateActiviti", method = RequestMethod.GET)
	@ResponseBody
	public String updateActiviti(@RequestParam(value = "id") Long cmsId, @RequestParam(value = "checked") Boolean check, Model model, Locale locale)
			throws Exception {
		// update status cms
		try {
			cmsService.updateStatus(cmsId, check);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return "ok";

	}

	@RequestMapping(value = "/loadPushCMSPopup", method = { RequestMethod.GET, RequestMethod.POST })
	public String loadPushCMSPopup(@ModelAttribute(value = "bean") CMSBean bean, BindingResult bindingResult, Model model, Locale locale,
			HttpServletRequest req, HttpServletResponse resp) throws Exception {

		try {

			String content = bean.getPushDescription();
			if (StringUtils.length(content) > 120) {
				content = StringUtils.left(content, 117) + "...";
			}

			bean.setPushDescription(content);

			if (StringUtils.equalsIgnoreCase(req.getMethod(), "POST")) {

				if (StringUtils.isBlank(bean.getPushTitle())) {
					bindingResult.rejectValue("pushTitle", "javax.validation.constraints.NotNull");
				}

				if (StringUtils.isBlank(bean.getPushDescription())) {
					bindingResult.rejectValue("pushDescription", "javax.validation.constraints.NotNull");
				}

				if (bean.getEntity() == null || bean.getEntity().getCmsId() == null) {
					throw new Exception("CMS must be saved before push");
				}

				if (bindingResult.hasErrors()) {
					throw new Exception("Cannot push messsage");
				}

				int status = pushNotificationService.sendBroadcastNotification(bean.getPushDescription(), bean.getEntity().getCmsId());

				bean.addMessage(Message.SUCCESS, "Push messages successfully ");

			}

		} catch (Exception e) {
			e.printStackTrace();
			bean.addMessage(Message.ERROR, e.getMessage());
		}

		return "master_data.cms.edit.pushMessagePopup";
	}

}
