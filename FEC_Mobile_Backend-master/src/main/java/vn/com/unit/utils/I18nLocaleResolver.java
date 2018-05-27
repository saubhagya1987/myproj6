package vn.com.unit.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;

@Component
public class I18nLocaleResolver extends SessionLocaleResolver {

	private static final Logger logger = LoggerFactory.getLogger(I18nLocaleResolver.class);

	public static final String LOCALE_SESSION_ATTRIBUTE = "localeSelect";

	@Autowired
	private UserProfile userProfile;

	@Autowired
	SystemConfig systemConfig;

	public Locale resolveLocale(HttpServletRequest request) {
		Locale locale = (Locale) WebUtils.getSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE_NAME);
		if (request.getSession().getAttribute("localeSelect") != null) {
			locale = (Locale) request.getSession().getAttribute("localeSelect");
		}
		if (locale == null && systemConfig != null) {
			locale = new Locale(systemConfig.getConfig(SystemConfig.LANGUAGE_DEFAULT));
		}
		if (locale == null) {
			locale = determineDefaultLocale(request);
		}
		return locale;
	}

	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		WebUtils.setSessionAttribute(request, LOCALE_SESSION_ATTRIBUTE, locale);
		request.getSession().setAttribute("localeSelect", locale);
		// AccountSetting setting = accountSettingService.findByAccountName(request.getUserPrincipal().getName());
		// if(setting==null ) {
		// setting=new AccountSetting();
		// setting.setUserName(request.getUserPrincipal().getName());
		// }
		// setting.setLanguage(locale.getLanguage());
		// try{
		// userProfile.setDefaultLang(locale.getLanguage());
		// }catch(Exception e ){
		//
		// }
		// accountSettingService.SaveAccountSetting(setting);
		// logger.debug("==>update language for {} to {}",new
		// Object[]{request.getUserPrincipal().getName(),locale.getLanguage()});
		this.setDefaultLocale(locale);
	}

}
