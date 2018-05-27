package vn.com.unit.fe_credit.security;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.UserAttempts;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.UserAttemptsService;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	@Qualifier(value = "systemConfig")
	SystemConfig systemConfig;

	@Autowired
	SessionLocaleResolver localeResolver;

	@Autowired
	@Qualifier(value = "userProfile")
	UserProfile userProfile;

	@Autowired
	AccountService accountService;

	@Autowired
	private ActivityLogService activityLogService;

	@Autowired
	private UserAttemptsService userAttemptsService;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(LoginSuccessHandler.class);

	@Override
	@Transactional
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		String username = authentication.getName();
		UserAttempts userAttempts = userAttemptsService.findByIpAddress(ipAddress);
		if (userAttempts == null) {
			userAttempts = new UserAttempts(ipAddress, username);
		}
		userAttempts.setAttempts(0);
		userAttempts.setUsername(username);
		userAttemptsService.save(userAttempts);

		String userName = "";
		if (authentication.getPrincipal() instanceof org.springframework.security.ldap.userdetails.LdapUserDetailsImpl) {
			userName = ((LdapUserDetailsImpl) authentication.getPrincipal()).getUsername();
		}
		if (authentication.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
			userName = ((User) authentication.getPrincipal()).getUsername();
		}
		if (org.apache.commons.lang.StringUtils.isEmpty(userName)) {
			userName = authentication.getPrincipal().toString();
		}

		String lang = "EN";
		Locale locale = null;

		localeResolver.setDefaultLocale(StringUtils.parseLocaleString(lang));
		WebUtils.setSessionAttribute(request, "localeSelect", new Locale(lang));
		httpServletRequest.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		httpServletRequest.getSession().setAttribute("formatDateTime", systemConfig.getConfig(SystemConfig.DATE_TIME_PATTERN));
		httpServletRequest.getSession().setAttribute("formatNumber", SystemConfig.NUMBER_PATTERN);
		if (systemConfig.getConfig(SystemConfig.TIME_RECEIVE) != null) {
			request.getSession().setAttribute("timerecevice", systemConfig.getConfig(SystemConfig.TIME_RECEIVE));
		} else {
			request.getSession().setAttribute("timerecevice", 1000);
		}
		logger.debug("LoginSuccessHandler =>> change default language to {}", lang);
		activityLogService.saveActivityLog(SystemConfig.USER_LOGIN, SystemConfig.LOGIN, locale, SystemConfig.SYSTEM, null,
				userProfile.getAccount().getId().toString());
		// setDefaultTargetUrl("/?lang=en");
		// setDefaultTargetUrl("/index?lang=en");
		setDefaultTargetUrl("/?lang=en");
		response.sendRedirect("index");
		// super.onAuthenticationSuccess(request, response, authentication);

	}

}
