package vn.com.unit.fe_credit.security;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.UserAttempts;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.UserAttemptsService;

@Service
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	SessionLocaleResolver localeResolver;

	@Autowired
	UserProfile userProfile;

	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	AccountService accountService;

	@Autowired
	UserAttemptsService userAttemptsService;

	private static final Logger logger = LoggerFactory.getLogger(LoginFailureHandler.class);

	@Override
	@Transactional
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {

		String userName = "";
		try {
			userName = exception.getAuthentication().getPrincipal().toString();
		} catch (Exception e) {
			// Do nothing
		}

		String ipAddress = request.getHeader("X-FORWARDED-FOR");
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		UserAttempts userAttempts = userAttemptsService.findByIpAddress(ipAddress);
		if (userAttempts == null) {
			userAttempts = new UserAttempts(ipAddress, userName);
		}
		userAttempts.setAttempts(userAttempts.getAttempts() + 1);
		userAttempts.setUsername(userName);
		userAttemptsService.save(userAttempts);

		String errorReason = "";

		if (exception instanceof DisabledException) {
			errorReason = exception.getMessage();
		} else if (exception instanceof LockedException) {
			errorReason = "invalid.user.password";
		} else {
			errorReason = "invalid.user.password";

			Account account = accountService.findByAccountName(userName);
			if (account != null) {
				Map<String, Integer> mapAccessCounter = (Map<String, Integer>) httpServletRequest.getSession().getAttribute("accessCounter");
				Map<String, Date> mapLoginTime = (Map<String, Date>) httpServletRequest.getSession().getAttribute("firstLoginTime");

				if (mapAccessCounter == null || mapAccessCounter.get(userName) == null) {
					if (mapAccessCounter == null) {
						// init
						mapAccessCounter = new HashedMap();
						mapLoginTime = new HashedMap();
					}

					mapLoginTime.put(userName, new Date());
					mapAccessCounter.put(userName, 1);

					httpServletRequest.getSession().setAttribute("firstLoginTime", mapLoginTime);
					httpServletRequest.getSession().setAttribute("accessCounter", mapAccessCounter);
				}
			}

		}

		setDefaultFailureUrl("/login?reason=" + errorReason + "&lang=en");
		super.onAuthenticationFailure(request, response, exception);
	}
}
