package vn.com.unit.binding;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import vn.com.unit.fe_credit.config.SystemConfig;

public class BindingInitializer implements WebBindingInitializer {

	@Autowired
	private SystemConfig systemConfig;

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				systemConfig.getConfig(SystemConfig.DATE_PATTERN));
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				systemConfig.getConfig(SystemConfig.DATE_TIME_PATTERN));
		CustomDateEditor editor1 = new CustomDateEditor(dateTimeFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
		binder.registerCustomEditor(Date.class, editor1);
		binder.registerCustomEditor(Double.class,
				new DoubleEditor(request.getLocale(),
						SystemConfig.NUMBER_PATTERN));
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}

}
