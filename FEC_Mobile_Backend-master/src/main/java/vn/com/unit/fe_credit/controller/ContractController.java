package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.ContractApiBean;
import vn.com.unit.fe_credit.bean.ContractDeatailApiBean;
import vn.com.unit.fe_credit.bean.ContractInstallmentApiBean;
import vn.com.unit.fe_credit.bean.PaymentHistoryApiBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ContractDetailService;
import vn.com.unit.fe_credit.service.ContractInstallmentService;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.PaymentHistoryService;
import vn.com.unit.webservice.DataParser;
import vn.com.unit.webservice.NetClientGet;

@Controller
@RequestMapping("/contract")
public class ContractController {

	@Autowired
	ContractDetailService contractService;

	@Autowired
	ActivityLogService activityLogService;

	@Autowired
	PaymentHistoryService paymentHistoryService;

	@Autowired
	ContractInstallmentService contractInstallmentService;

	@Autowired
	MessageService messageDashBoardService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	CustomerService customerService;

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
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

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listGetContract(@ModelAttribute(value = "bean") ContractApiBean bean, Model model, Locale locale, HttpServletRequest request)
			throws Exception {

		return "contract.list";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String GetContract(@ModelAttribute(value = "bean") ContractApiBean bean, Model model, Locale locale, HttpServletRequest request)
			throws Exception {

		if (bean.getContractnumber().isEmpty() && bean.getCustomeridcard().isEmpty() && bean.getCellphone().isEmpty()) {
			return "contract.list";
		}

		try {
			if (StringUtils.isNotEmpty(bean.getContractnumber()) || StringUtils.isNotEmpty(bean.getCustomeridcard())
					|| StringUtils.isNotEmpty(bean.getCellphone())) {
				StringBuffer json = NetClientGet.searchContract(bean.getContractnumber().trim(), bean.getCustomeridcard().trim(),
						bean.getCellphone().trim());
				String dataconvert = json.toString().substring(json.indexOf(":") + 1, json.toString().lastIndexOf("}"));
				JSONArray jsonArray = new JSONArray(dataconvert);
				bean.setListResult(DataParser.parseToContractDetailApiList(jsonArray));
			}

			if (bean.getListResult().isEmpty()) {
				/*
				 * bean.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
				 */
			}
		} catch (Exception e) {

		}

		model.addAttribute("bean", bean);
		return "contract.list";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewCustomer(@RequestParam(required = false) String id, @RequestParam(required = false) String contractNum,
			HttpServletRequest request, Model model, @ModelAttribute(value = "bean") ContractDeatailApiBean bean) throws Exception {

		PaymentHistoryApiBean paymentHistoryApiBean = new PaymentHistoryApiBean();
		ContractInstallmentApiBean contractInstallmentApiBean = new ContractInstallmentApiBean();

		if (StringUtils.isNotEmpty(request.getParameter("id")) && StringUtils.isNotEmpty(request.getParameter("contractNum"))) {

			// get detail
			StringBuffer json = NetClientGet.getContractDetail(id, contractNum);
			String dataconvert = json.toString().substring(json.indexOf(":") + 1, json.toString().lastIndexOf("}"));
			JSONObject jsonObject = new JSONObject(dataconvert);
			bean.setContractdetailApi(DataParser.parseToContractApi(jsonObject));

			// get payment history
			StringBuffer json1 = NetClientGet.getPaymentHistory(id, contractNum);
			String dataconvert1 = json1.toString().substring(json1.indexOf(":") + 1, json1.toString().lastIndexOf("}"));
			JSONArray jsonarray = new JSONArray(dataconvert1);
			paymentHistoryApiBean.setListResult(DataParser.parseToPaymentHistoryList(jsonarray));

			// get contract installment
			StringBuffer json2 = NetClientGet.getInstallmentList(id, contractNum);
			String dataconvert2 = json2.toString().substring(json2.indexOf(":") + 1, json2.toString().lastIndexOf("}"));
			JSONArray jsonarray1 = new JSONArray(dataconvert2);
			contractInstallmentApiBean.setListResult(DataParser.parseToContractInstallmentList(jsonarray1));

			bean.setMessgesLst(messageDashBoardService.findByCustomerIdAndContractMSID(id, contractNum));
			// bean.setCustomer(customerService.findbyCustomerId(id));
			bean.setCustomer(customerService.customerIDtoAccountID(id.toString()));
		}

		model.addAttribute("bean", bean);
		model.addAttribute("paymentapi", paymentHistoryApiBean);
		model.addAttribute("contractinstallmentapi", contractInstallmentApiBean);
		// model.addAttribute("firstvalue", contractInstallmentService
		// .findFirstValuebyContractDetail(contract.getContractdetailID()));
		return "contract.showInfo";
	}

}
