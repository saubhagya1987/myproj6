package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.BannerBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.BannerImages;
import vn.com.unit.fe_credit.service.BannerActivityLogService;
import vn.com.unit.fe_credit.service.BannerImageService;
import vn.com.unit.fe_credit.service.BannerService;

@Controller
@RequestMapping(value = "/master_data/banner")
public class BannerController {
	
	@Autowired
	MessageSource msgSrc;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	BannerService bannerService;

	@Autowired
	BannerImageService bannerImageService;

	@Autowired
	BannerActivityLogService bannerActivityLogService;

	@Autowired
	private UserProfile userProfile;

	Logger logger = LoggerFactory.getLogger(BannerController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder , Locale locale, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate")+ "HH:mm");
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
	public String list(@ModelAttribute(value = "bean") BannerBean bannerBean, Model model, @RequestParam(value = "message", required = false) String message, HttpServletRequest request, Locale locale,HttpServletResponse response) throws Exception {
		if (message != null && message.equals("success")) {
			bannerBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bannerBean.setLimit(pagesize);
		BannerBean result = new BannerBean();
		if(request.getMethod().equals(RequestMethod.GET.name())){
			bannerBean.setStatus(1);
		}
		try {
			result = bannerService.search(bannerBean);
			List<Banner> bannerLst = result.getListResult();
			for(int i = 0; i < bannerLst.size(); i++){
				List<BannerImages> bannerImageLst = bannerImageService.findByBannerId(bannerLst.get(i).getBannerId());
				if(bannerImageLst != null && bannerImageLst.size() > 0){
					bannerLst.get(i).setImagePath(bannerImageLst.get(0).getImageFileName());
				}
			}
			result.setListResult(bannerLst);
		} catch (Exception ex) {
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
		}
		if(CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		model.addAttribute("categoryBanner", systemConfig.getCategoryBannerMap());
		return "master_data.banner.list";
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit_get(@ModelAttribute(value = "bean") @Valid BannerBean bannerBean, @RequestParam(required = false) Long id, HttpServletRequest request, Model model,HttpServletResponse response) throws Exception {
		Banner banner;
		//BannerBean bannerBean = new BannerBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			banner = bannerService.findById(id);
			
			String lstImage = "";
			String lstImageLink = "";
			List<BannerImages> bannerImageLst = bannerImageService.findByBannerId(banner.getBannerId());
			for(int s = 0; s < bannerImageLst.size(); s++){
				lstImage += bannerImageLst.get(s).getImageFileName() + ",";
				lstImageLink += ((bannerImageLst.get(s).getImagePath() != null && StringUtils.isNotEmpty(bannerImageLst.get(s).getImagePath())) ? bannerImageLst.get(s).getImagePath() : " ") +"@@@";
			}
			if(lstImage.length() > 0){
				lstImage = lstImage.substring(0, lstImage.length() - 1);
			}
			if(lstImageLink.length() > 0){
				lstImageLink = lstImageLink.substring(0, lstImageLink.length() - 3);
			}
			bannerBean.setLstImage(lstImage);
			bannerBean.setLstImageLink(lstImageLink);
		}else {
			banner = new Banner();
		}
		bannerBean.setEntity(banner);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bannerBean);
		model.addAttribute("categoryBanner", systemConfig.getCategoryBannerMap());
		return "master_data.banner.edit";
	}
	
	
	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String view_get(@ModelAttribute(value = "bean") @Valid BannerBean bannerBean, @RequestParam(required = false) Long id, HttpServletRequest request, Model model,HttpServletResponse response) throws Exception {
		Banner banner;
		//BannerBean bannerBean = new BannerBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			banner = bannerService.findById(id);
			
			String lstImage = "";
			String lstImageLink = "";
			List<BannerImages> bannerImageLst = bannerImageService.findByBannerId(banner.getBannerId());
			for(int s = 0; s < bannerImageLst.size(); s++){
				lstImage += bannerImageLst.get(s).getImageFileName() + ",";
				lstImageLink += ((bannerImageLst.get(s).getImagePath() != null && StringUtils.isNotEmpty(bannerImageLst.get(s).getImagePath())) ? bannerImageLst.get(s).getImagePath() : " ") +"@@@";
			}
			if(lstImage.length() > 0){
				lstImage = lstImage.substring(0, lstImage.length() - 1);
			}
			if(lstImageLink.length() > 0){
				lstImageLink = lstImageLink.substring(0, lstImageLink.length() - 3);
			}
			bannerBean.setLstImage(lstImage);
			bannerBean.setLstImageLink(lstImageLink);
		}else {
			banner = new Banner();
		}
		bannerBean.setEntity(banner);
		model.addAttribute("bean", bannerBean);
		model.addAttribute("categoryBanner", systemConfig.getCategoryBannerMap());
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.banner.view";
	}
	
	
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	private String editAction(@ModelAttribute(value = "bean") @Valid BannerBean bannerBean,
			BindingResult bindingResult, Model model, Locale locale, RedirectAttributes redirectAttributes,HttpServletResponse response) {
		
		bannerBean.clearMessages();
		bannerBean.setCheckDateTo(false);
		if (bannerBean.getEntity().getActiveToDate() != null) {
			if (bannerBean.getEntity().getActiveFromDate().after(bannerBean.getEntity().getActiveToDate())) {
				bannerBean.addMessage(Message.ERROR, msgSrc.getMessage("mess.data.err", null, locale));
				bannerBean.setCheckDateTo(true);
				model.addAttribute("bean", bannerBean);
				model.addAttribute("categoryBanner", systemConfig.getCategoryBannerMap());
				response.setContentType("text/html; charset=UTF-8");
				return "master_data.banner.edit";
			}
		}
		
		if (!bindingResult.hasErrors()) {
			try {

				Long checkid = bannerBean.getEntity().getBannerId();
				bannerService.save(bannerBean);
				// log
				if (checkid != null) {
					bannerActivityLogService.saveBannerActivityLog(SystemConfig.EDIT_BANNER, SystemConfig.BANNER, locale, SystemConfig.SYSTEM,
							String.valueOf(checkid), userProfile.getAccount().getId().toString());
				} else {
					bannerActivityLogService.saveBannerActivityLog(SystemConfig.ADD_NEW_BANNER, SystemConfig.BANNER, locale, SystemConfig.SYSTEM,
							null, userProfile.getAccount().getId().toString());
				}
				bannerBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));

			} catch (Exception e) {
				e.printStackTrace();
				logger.debug("##SAVE_BANNER##", e);
				bannerBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
				model.addAttribute("bean", bannerBean);
				model.addAttribute("categoryBanner", systemConfig.getCategoryBannerMap());
				return "master_data.banner.edit";
			}
			
			model.addAttribute("bean", bannerBean);
			redirectAttributes.addFlashAttribute("bean", bannerBean);
			response.setContentType("text/html; charset=UTF-8");
			return "redirect:/master_data/banner/edit?id="+bannerBean.getEntity().getBannerId();
		} else {
			if (bindingResult.getFieldError("entity") != null){
				bannerBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			}
			bannerBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bannerBean);
			model.addAttribute("categoryBanner", systemConfig.getCategoryBannerMap());
			
			return "master_data.banner.edit";
		}
	}
	
	@RequestMapping(value = "/view_image", method = RequestMethod.GET)
	public String viewBannerImages(@RequestParam(required = false) Long id, HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		BannerBean bannerBean = new BannerBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			List<BannerImages> bannerImageLst = bannerImageService.findByBannerId(id);
			String lstImage = "";
			for(int s = 0; s < bannerImageLst.size(); s++){
				lstImage += bannerImageLst.get(s).getImageFileName() + ",";
			}
			if(lstImage.length() > 0){
				lstImage = lstImage.substring(0, lstImage.length() - 1);
			}
			bannerBean.setLstImage(lstImage);
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bannerBean);
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.banner.image.list";
	}
	
	@RequestMapping(value = "/getPopupSlideshow", method = RequestMethod.GET)
	public String getPopupSlideshow(Model model,
			@RequestParam(value = "bannerId", required = false) Long bannerId,
			HttpServletRequest request, HttpServletResponse response, Locale locale, RedirectAttributes redirectAttributes) {
		try {
			List<BannerImages> bannerImageLst = bannerImageService.findByBannerId(bannerId);
			
			model.addAttribute("bannerImageLst", bannerImageLst);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.setContentType("text/html; charset=UTF-8");
		return "master_data.banner.popupslideshow";
	}
}
