package vn.com.unit.fe_credit.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExceptionController {

	@RequestMapping("/accessDenied")
	public String handleAccessDeniedException(Model model, HttpServletRequest request) {
		model.addAttribute("message", request.getSession().getAttribute("login_error"));
		return "access.denied";
	}
	
	@RequestMapping("/pageNotFound")
	public String handlePageNotFoundException(Model model, HttpServletRequest request) {
		return "not.found";
	}
}
