package vn.com.unit.binding;

import java.beans.PropertyEditorSupport;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.util.StringUtils;

public class DoubleEditor extends PropertyEditorSupport {
	private Locale locale;
	private String pattern;

	public DoubleEditor(Locale locale, String pattern) {
		super();
		this.locale = locale;
		this.pattern = pattern;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	@Override
	public String getAsText() {
		if (getValue() == null)
			return null;
		NumberFormat nf = NumberFormat.getNumberInstance(locale);
		DecimalFormat df = (DecimalFormat) nf;
		df.applyPattern(pattern);
		return df.format(getValue());
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		if (!StringUtils.hasText(text)) {
			setValue(null);
		} else {
			try {
				NumberFormat nf = NumberFormat.getNumberInstance(locale);
				DecimalFormat df = (DecimalFormat) nf;
				df.applyPattern(pattern);
				setValue(df.parse(text).doubleValue());
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}

	}
}
