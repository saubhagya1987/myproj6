package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.config.SystemConfig;

public abstract class AbstractController {
	//private static final Logger logger = LoggerFactory.getLogger(.class);
	
	@InitBinder
	public void dataFormatBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		// The date format to parse or output your dates
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
}
