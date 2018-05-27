package vn.com.unit.fe_credit.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.com.unit.fe_credit.bean.MgmExchangeRateBean;
import vn.com.unit.fe_credit.config.SystemConfig;

@Controller
@RequestMapping("/system")
public class MgmController {
	
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private MessageSource msgSrc;
	
	@RequestMapping(value = "/mgm_config", method = RequestMethod.GET)
	public String mgmConfig(@ModelAttribute(value = "bean") MgmExchangeRateBean mgmExchangeRateBean, Model model,
			Locale locale) throws Exception {
		
		model.addAttribute("bean", mgmExchangeRateBean);
		
		return "system.mgm.config";
	}

}
