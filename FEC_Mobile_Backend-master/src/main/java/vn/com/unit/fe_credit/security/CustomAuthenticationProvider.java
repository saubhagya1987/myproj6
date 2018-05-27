package vn.com.unit.fe_credit.security;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import botdetect.web.Captcha;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.LDAPSyn;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.AccountDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.entity.UserAttempts;
import vn.com.unit.fe_credit.service.UserAttemptsService;
import vn.com.unit.fe_credit.utils.Utils;

/**
 * Class define custom Authentication by AuthenticationProvider
 * 
 * @author tuannd
 * @version 1.01 17/02/2014
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	@Qualifier(value = "systemConfig")
	SystemConfig systemConfig;

	@Autowired
	SessionLocaleResolver localeResolver;

	@Autowired
	@Qualifier(value = "userProfile")
	UserProfile userProfile;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
	AccountDao accountDao;

	@Autowired
	UserAttemptsService userAttemptsService;

	/**
	 * Class define Authentication and define user and permission
	 */
	@Override
	@Transactional
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getName();
		String password = (String) authentication.getCredentials();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		//TODO : Create Captcha Method on the basis of attempts to tried
		UserAttempts userAttempts = userAttemptsService.findByIpAddress(ipAddress);
		if (userAttempts != null && userAttempts.getAttempts() >= 3) {
			String captchaCodeTextBox = request.getParameter("captchaCodeTextBox");
			Captcha captcha = Captcha.load(request, "springFormCaptcha");
			boolean isHuman = captcha.validate(request, captchaCodeTextBox);
			if (BooleanUtils.isNotTrue(isHuman)) {
				throw new DisabledException("invalid.captcha.code");
			}
		}

		try {

			LDAPSyn ldapSyn = new LDAPSyn();

			int check = 0;
			//TODO : Create a method to authenticate from DB
			Account account = accountDao.findByUserNameForLogin(username);
			if (StringUtils.isNotEmpty(password) && account != null && account.getPassword() != null
					&& account.getPassword().equals(Utils.encryptMD5(password))) {
				check = 1;
			}
			//TODO : Create a method to authenticate from LDAP
			if (check == 0) {
				check = ldapSyn.check(username + "@" + systemConfig.getConfig(SystemConfig.CONFIG_LDAP_DOMAIN), password, username);
				if (check == 1) {
					/*
					 * userProfile.setSECURITY_PRINCIPAL(username + "@" +
					 * Utils.getProperties().getProperty("ldap.domain")); userProfile.setSECURITY_CREDENTIALS(password);
					 */
				}
			}

			// userAttempts.setLastModified(new Date());
			// if (check == 0) {
			// userAttempts.setAttempts(userAttempts.getAttempts() + 1);
			// userAttemptsService.save(userAttempts);
			// } else {
			// userAttempts.setAttempts(0);
			// userAttemptsService.save(userAttempts);
			// }
			
			//TODO : create a method to test for locked account
			if (check == 0) {
				throw new LockedException("User account is locked");
			}

		} catch (Exception e) {
			throw new LockedException("User account is locked");
		}
		//TODO : Remove following code after discussion
		Account account = accountDao.findByUserNameForLogin(username);

		if (account == null) {
			throw new LockedException("User account is locked");
		}
		// localeResolver.setDefaultLocale(StringUtils.parseLocaleString(lang));
		httpServletRequest.getSession().setAttribute("formatDate", systemConfig.getConfig(SystemConfig.DATE_PATTERN));

		List<GrantedAuthority> authoritiesRs = new ArrayList<GrantedAuthority>();
		String fullName = account.getFullName();
		userProfile.setAccount(account);
		if (fullName != null) {
			String[] fullNames = fullName.split(" ");
			if (fullNames != null && fullNames.length > 0) {
				fullName = fullNames[fullNames.length - 1];
			}
			if (fullName != null && fullName.length() > 10) {
				fullName = fullName.substring(0, 10) + "...";
			}
		} else {
			fullName = "";
		}
		if (account.getImagePath() == null) {
			userProfile.setImgPath("static/images/avata_c5.gif");
		} else {
			userProfile.setImgPath("ajax/download?fileName=" + account.getImagePath());
		}

		userProfile.setFullName(fullName);
		if (account.getTeams() != null) {
			for (Team team : account.getTeams()) {
				// if(SystemConstant.TEAM_TYPE.equalsIgnoreCase(team.getType())){
				for (Role role : team.getRoles()) {
					// if(SystemConstant.ROLE_TYPE.equalsIgnoreCase(role.getType())){
					GrantedAuthority grantedAuthorityImpl = new SimpleGrantedAuthority(role.getCode());
					authoritiesRs.add(grantedAuthorityImpl);
					// }
					if (role.getCode().equals("R011")) {
						try {
							userProfile.setSECURITY_PRINCIPAL(
									systemConfig.getConfig(SystemConfig.USER_LDAP) + "@" + systemConfig.getConfig(SystemConfig.CONFIG_LDAP_DOMAIN));
							userProfile.setSECURITY_CREDENTIALS(systemConfig.getConfig(SystemConfig.PASSWORD_LDAP));
						} catch (Exception e) {
							logger.error(e.getMessage());
						}
					}
					if (role.getCode().equals("R003")) {
						// checkSupportChat = true;
					}
				}
				// }

			}
		}

		authoritiesRs.add(new SimpleGrantedAuthority("ROLE_AUTHED"));
		return new UsernamePasswordAuthenticationToken(username, password, authoritiesRs);
	}

	/**
	 * Class staus support of class
	 */
	public boolean supports(Class<?> authentication) {
		return true;
	}

	String createBindPrincipal(String username, String domain) {
		if (domain == null || username.toLowerCase().endsWith(domain)) {
			return username;
		} else {
			return username + "@" + domain;
		}
	}

}
