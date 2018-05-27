package vn.com.unit.fe_credit.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import vn.com.unit.fe_credit.bean.UserProfile;

public class BackdoorFilter implements Filter{
	WebApplicationContext springContext;
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		springContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest rq=(HttpServletRequest) request;
		UserProfile userProfile=(UserProfile) rq.getSession().getAttribute("scopedTarget.userProfile");
//		if(userProfile!=null &&userProfile.getDepartment()==null){
//			HttpServletResponse httpResponse = (HttpServletResponse) response;
//			httpResponse.sendRedirect(rq.getContextPath() +"/backdoor");
//			return;
//		}
		chain.doFilter(request, response);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	

}
