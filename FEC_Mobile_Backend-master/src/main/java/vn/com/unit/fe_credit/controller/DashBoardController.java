package vn.com.unit.fe_credit.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.ActitvityLogChartBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.service.ActivityLogService;

@Controller
@RequestMapping("/dashboard")
public class DashBoardController {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	UserProfile userProfile;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private ActivityLogService activityLogService;

	static final Logger logger = LoggerFactory.getLogger(DashBoardController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates

		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.roll(Calendar.DATE, -1);
		model.addAttribute("fromday", simpleDateFormat.format(calendar.getTime()));
		model.addAttribute("today", simpleDateFormat.format(new Date()));
		return "dashboard.view";
	}

	@RequestMapping(value = "/getListReport", method = RequestMethod.GET)
	@ResponseBody
	public Object getListReport(SecurityContextHolderAwareRequestWrapper request, Locale locale,
			@RequestParam(value = "fromDate", required = false) String fromDateTemp,
			@RequestParam(value = "toDate", required = false) String toDateTemp, HttpServletResponse response) throws JSONException, IOException {

		Map<String, Object> dataResult = new HashMap<String, Object>();
		List<ActitvityLogChartBean> actitvityLogChartBeans = new ArrayList<ActitvityLogChartBean>();

		dataResult.put("title", msgSrc.getMessage("report.online.user.daily", null, locale));
		long sum = activityLogService.sumChart(getlist(), fromDateTemp, toDateTemp, userProfile.getAccount().getId().toString());
		long replay = activityLogService.countReport(Long.parseLong(SystemConfig.REPLY_MESS_CUSTOMER.toString()), fromDateTemp, toDateTemp,
				userProfile.getAccount().getId().toString());

		for (ActitvityLogChartBean actitvityLogChartBean : actitvityLogChartBeans(locale)) {
			long va = activityLogService.countReport(Long.parseLong(actitvityLogChartBean.getReferID().toString()), fromDateTemp, toDateTemp,
					userProfile.getAccount().getId().toString());
			if (actitvityLogChartBean.getReferID().toString().equals(SystemConfig.SEND_MESS_CUSTOMER.toString()))
				va = va + replay;
			double ss = getvalue(va, sum);
			actitvityLogChartBean.setY(ss);
			actitvityLogChartBeans.add(actitvityLogChartBean);
		}

		dataResult.put("dataChart", actitvityLogChartBeans);
		logger.debug("##dataResult##" + dataResult);
		return dataResult;

		// request.setCharacterEncoding("utf8");
		// response.setContentType("application/json");
		// PrintWriter out = response.getWriter();
		// ObjectMapper mapper = new ObjectMapper();
		// out.print(mapper.writeValueAsString(dataResult));

	}

	private double getvalue(long value, long sum) {
		double value1 = Double.parseDouble(value + "");
		double sum1 = Double.parseDouble(sum + "");
		double s = (value1 / sum1) * 100;
		DecimalFormat df = new DecimalFormat(".#");
		String str = df.format(s);
		double result = Double.valueOf(str);
		return result;
	}

	private List<ActitvityLogChartBean> actitvityLogChartBeans(Locale locale) {
		ArrayList<ActitvityLogChartBean> actitvityLogChartBeans = new ArrayList<ActitvityLogChartBean>();
		actitvityLogChartBeans.add(new ActitvityLogChartBean(msgSrc.getMessage("report.login", null, locale), 0, SystemConfig.MOBILE_LOGIN));
		actitvityLogChartBeans
				.add(new ActitvityLogChartBean(msgSrc.getMessage("report.submit.new.loan", null, locale), 0, SystemConfig.ADD_LOAN_CONDITION));
		actitvityLogChartBeans.add(new ActitvityLogChartBean(msgSrc.getMessage("report.send.mess.cs", null, locale), 0, SystemConfig.SAVE_MESSAGE));
		actitvityLogChartBeans
				.add(new ActitvityLogChartBean(msgSrc.getMessage("report.messagers.to.customer", null, locale), 0, SystemConfig.SEND_MESS_CUSTOMER));
		actitvityLogChartBeans.add(new ActitvityLogChartBean(msgSrc.getMessage("report.click.to.banner", null, locale), 0, 5555));
		return actitvityLogChartBeans;
	}

	private List<String> getlist() {
		List<String> strings = new ArrayList<String>();
		strings.add(SystemConfig.MOBILE_LOGIN.toString());
		strings.add(SystemConfig.ADD_LOAN_CONDITION.toString());
		strings.add(SystemConfig.SAVE_MESSAGE.toString());
		strings.add(SystemConfig.SEND_MESS_CUSTOMER.toString());
		strings.add(SystemConfig.REPLY_MESS_CUSTOMER.toString());
		strings.add("555");
		return strings;
	}

}
