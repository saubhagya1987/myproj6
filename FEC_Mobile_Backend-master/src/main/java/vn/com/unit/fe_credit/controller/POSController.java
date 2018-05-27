package vn.com.unit.fe_credit.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.POSBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.POSService;
import vn.com.unit.fe_credit.service.StatusTableService;

@Controller
@RequestMapping("/master_data/pos")
public class POSController {
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listCms_category(@ModelAttribute(value = "bean") POSBean bean,
			@RequestParam(value = "mess", required = false) String mess,
			@RequestParam(value = "search_form_POS", required = false) Long hobbyId,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		if(mess!=null){
			bean.addMessage(Message.SUCCESS,
					msgSrc.getMessage("msg.save.success", null, locale));	
		}
		bean.setListStatusTable(statusTableService.findAllex());
//		bean.setListResult(posService.findAllex());
		POSBean result =posService.search(bean);
		model.addAttribute("bean", result);
//		POSBean result = posService.search(bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.pos.list";
	}
	
	@RequestMapping(value = "/checkListDAO", method = RequestMethod.GET)
	public @ResponseBody  List<PosEmtity> listcheckListDAO(@ModelAttribute(value = "bean") POSBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		List<PosEmtity> listLongitude =posService.getListDAONullValue();
		return listLongitude;
	}

	@RequestMapping(value = "/saveValue", method = RequestMethod.POST)
	public String saveValue(@ModelAttribute(value = "bean") POSBean bean,
			@RequestParam(value = "longitude", required = false) String longitude,
			@RequestParam(value = "latitude", required = false) String latitude,
			@RequestParam(value = "posid", required = false) Long posid,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		if(StringUtils.isNotEmpty(longitude)&&StringUtils.isNotEmpty(latitude)&&posid!=null){
			try {
				PosEmtity updateValue = new PosEmtity();
				updateValue=posService.findById(posid);
				if(updateValue!=null){
					updateValue.setLongitude(longitude);
					updateValue.setLatitude(latitude);
					posService.savePOS(updateValue);
				}
			} catch (Exception e) {
				bean.addMessage(Message.ERROR,
						msgSrc.getMessage("msg.save.fail", null, locale));
				e.printStackTrace();
			}
			
		}
		bean.addMessage(Message.SUCCESS,
				msgSrc.getMessage("msg.save.success", null, locale));
		model.addAttribute("bean", bean);
		return "master_data.pos.list";
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listStudent(@ModelAttribute(value = "bean") POSBean bean,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
//		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		POSBean result = posService.search(bean);
		bean.setListPOS(result.getListResult());
		if(bean.getListPOS().isEmpty()){
			bean.addMessage(Message.INFO,
					msgSrc.getMessage("msg.search.nodata", null, locale));
			
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.pos.list";
	}
	
	@RequestMapping(value = "/posEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean")
			@Valid POSBean bean,
			@RequestParam(value = "posId", required = false) Long posId,
			Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		bean.setListStatusTable(statusTableService.findAllex());
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setSuccesorfail("fail");
		
		if (posId != null) {
			bean.setEntity(posService.findById(posId));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.pos.edit";
	}
	@RequestMapping(value = "/editMap", method = RequestMethod.GET)
	public String editMap(@ModelAttribute(value = "bean")
		@Valid POSBean bean,
		@RequestParam(value = "posId", required = false) Long posId,
		Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setListStatusTable(statusTableService.findAllex());
		POSBean result =posService.search(bean);
		model.addAttribute("bean", result);
		if (posId != null) {
			bean.setEntity(posService.findById(posId));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.pos.editMap";
	}
	@RequestMapping(value = "/editMap", method = RequestMethod.POST)
	public String editMapPost(@ModelAttribute(value = "bean")
		@Valid POSBean bean,
		@RequestParam(value = "posId", required = false) Long posId,
		Model model, Locale locale, HttpServletRequest request,HttpServletResponse response)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		bean.setLimit(pagesize);
		bean.setListStatusTable(statusTableService.findAllex());
		if (posId != null) {
			PosEmtity saveDataPOS = posService.findById(posId);
			if(saveDataPOS!=null && !bean.getEntity().getLatitude().equalsIgnoreCase("Latitude") ){
			saveDataPOS.setPosid(posId);
			saveDataPOS.setAddress_number(bean.getEntity().getAddress_number());
			saveDataPOS.setBuyOrPay(bean.getEntity().getBuyOrPay());
			saveDataPOS.setLatitude(bean.getEntity().getLatitude());
			saveDataPOS.setLongitude(bean.getEntity().getLongitude());
			try {
				posService.savePOS(saveDataPOS);
				bean.addMessage(Message.SUCCESS,
						msgSrc.getMessage("msg.save.success", null, locale));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			}else{
				bean.addMessage(Message.ERROR,
						msgSrc.getMessage("msg.save.fail", null, locale));
				response.setContentType("text/html; charset=UTF-8");
				model.addAttribute("bean", bean);
				return "master_data.pos.editMap";
			}
		}
		POSBean result =posService.search(bean);
		model.addAttribute("bean", result);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", bean);
		return "master_data.pos.editMap";
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean") POSBean bean, Model model, Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (bean.getEntity().getPosid() != null) {
			try {
				if(bean.getEntity().getPosid() != null){
//					posService.deletePosLocation(bean.getEntity().getBranchEmtity().getBranchid());
//					branchService.deleteBranchLocation(bean.getEntity().getBranchid());
					posService.deletePosIdLocation(bean.getEntity().getPosid());
					bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
//				logger.info("can't delete Team id={} with ex={}", bean.getEntity().getId(), ex);
				bean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", bean);
		return "redirect:/master_data/pos/list";
	}
}
