package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.bean.LoanDetailBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.LoanDetailService;
import vn.com.unit.fe_credit.service.LoanService;

@Controller
@RequestMapping("/loan")
public class LoanController {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	ActivityLogService activityLogService;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private LoanService loanService;
	@Autowired
	UserProfile userProfile;
	@Autowired
	private LoanDetailService loanDetailService;

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale,
			HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request
				.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale,
				"#,###.##"));
	}

	@RequestMapping(value = "/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String listPost(@ModelAttribute(value = "bean") LoanBean bean,
			Model model, HttpServletRequest request, Locale locale,
			HttpServletResponse response) throws Exception {

		LoanBean result = loanService.search(bean);

		model.addAttribute("bean", result);
		response.setContentType("text/html; charset=UTF-8");
		return "loan.list";
	}

	@RequestMapping(value = "/listdetail", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String listPost(@ModelAttribute(value = "bean") LoanDetailBean bean,
			Model model, HttpServletRequest request, Locale locale,
			HttpServletResponse response) throws Exception {

		LoanDetailBean result = loanDetailService.search(bean);

		model.addAttribute("bean", result);
		response.setContentType("text/html; charset=UTF-8");
		return "loandetail.list";
	}

	@RequestMapping(value = "/loanEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean") @Valid LoanBean bean,
			@RequestParam(value = "loanID", required = false) Long loanID,
			Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		bean.setSuccesorfail("fail");
		if (loanID != null) {
			bean.setEntity(loanService.findById(loanID));
		}

		model.addAttribute("bean", bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("loanCategory", systemConfig.getLoanCategoryMap());
		return "loan.edit";
	}

	@RequestMapping(value = "/loandetailEdit", method = RequestMethod.GET)
	public String itemsGet(
			@ModelAttribute(value = "bean") @Valid LoanDetailBean bean,
			@RequestParam(value = "loanDetailID", required = false) Long loanDetailID,
			Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (loanDetailID != null) {
			bean.setEntity(loanDetailService.findById(loanDetailID));
			bean.setListLoan(loanService.RemoveConditionWhenCreate(bean.getEntity()
					.getLoan().getCondition_category(),loanDetailID));
		} else {
			bean.setListLoan(loanService.RemoveConditionWhenCreate("PL",loanDetailID));
		}

		bean.setListResult(loanDetailService.findAll());
		model.addAttribute("bean", bean);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("loanCategory", systemConfig.getLoanCategoryMap());
		return "loandetail.edit";
	}

	@RequestMapping(value = "/loanEdit", method = RequestMethod.POST)
	public String saveLoan(
			@ModelAttribute(value = "bean") @Valid LoanBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Loan loan = bean.getEntity();

		if (StringUtils.isEmpty(loan.getCondition_name())) {
			bindingResult.rejectValue("entity.condition_name",
					"javax.validation.constraints.NotNull.message");
		}

		if (loan.getCondition_value() == null) {
			bindingResult.rejectValue("entity.condition_value",
					"javax.validation.constraints.NotNull.message");
		}

		if (!bindingResult.hasErrors()) {

			if (bean.getEntity().getLoanID() == null) {
				Boolean checkloanName = loanService.CheckByLoanConditionName(
						loan.getCondition_name(), loan.getCondition_category());
				if (checkloanName) {
					bean.addMessage(Message.ERROR, msgSrc.getMessage(
							"loan.error.loan.condition.name.exist", null,
							locale));
					model.addAttribute("bean", bean);
					model.addAttribute("loanCategory",
							systemConfig.getLoanCategoryMap());
					response.setContentType("text/html; charset=UTF-8");
					return "loan.addLoan";
				}
			}

			Long loanID = loanService.CheckID(loan.getCondition_name(),
					loan.getCondition_category());
			if (bean.getEntity().getLoanID() != null) {
				if (loanID != null && loanID.equals(loan.getLoanID())) {
					
				} else {
					Boolean checkloanName = loanService
							.CheckByLoanConditionName(loan.getCondition_name(),
									loan.getCondition_category());
					if (checkloanName) {
						bean.addMessage(Message.ERROR, msgSrc.getMessage(
								"loan.error.loan.condition.name.exist", null,
								locale));
						model.addAttribute("bean", bean);
						model.addAttribute("loanCategory",
								systemConfig.getLoanCategoryMap());
						response.setContentType("text/html; charset=UTF-8");
						return "loan.addLoan";
					}
				}
			}

			loan.setCreateDate(new Date());
			Long loanid = loan.getLoanID();
			loanService.saveLoan(loan);
			if (loanid != null) {
				activityLogService.saveActivityLog(
						systemConfig.EDIT_LOAN_CONDITION,
						systemConfig.LOAN_CONDITION, locale,
						systemConfig.SYSTEM, null, userProfile.getAccount()
								.getId().toString());
			} else {
				activityLogService.saveActivityLog(
						systemConfig.ADD_LOAN_CONDITION,
						systemConfig.LOAN_CONDITION, locale,
						systemConfig.SYSTEM, null, userProfile.getAccount()
								.getId().toString());
			}
			bean.setSuccesorfail("succes");
			bean.addMessage(Message.SUCCESS,
					msgSrc.getMessage("msg.save.success", null, locale));
			model.addAttribute("loanCategory",
					systemConfig.getLoanCategoryMap());
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "loan.addLoan";

		} else {
			bean.setSuccesorfail("fail");
			// bean.addMessage(Message.ERROR,
			// msgSrc.getMessage("msg.save.fail", null, locale));
			model.addAttribute("loanCategory",
					systemConfig.getLoanCategoryMap());
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "loan.addLoan";
		}

	}

	@RequestMapping(value = "/loandetailEdit", method = RequestMethod.POST)
	public String saveLoan(
			@ModelAttribute(value = "bean") @Valid LoanDetailBean bean,
			BindingResult bindingResult, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Loan loan;
		LoanDetail loandetail = bean.getEntity();

		if (loandetail.getMinamount() == null) {
			bindingResult.rejectValue("entity.minamount",
					"javax.validation.constraints.NotNull.message");
		}

		if (loandetail.getMaxamount() == null) {
			bindingResult.rejectValue("entity.maxamount",
					"javax.validation.constraints.NotNull.message");
		}

		/*if (loandetail.getMintenure() == null) {
			bindingResult.rejectValue("entity.mintenure",
					"javax.validation.constraints.NotNull.message");
		}*/

		/*if (loandetail.getMaxtenure() == null) {
			bindingResult.rejectValue("entity.maxtenure",
					"javax.validation.constraints.NotNull.message");
		}*/

		/*if (loandetail.getTenureperslide() == null) {
			bindingResult.rejectValue("entity.tenureperslide",
					"javax.validation.constraints.NotNull.message");
		}*/

		if (loandetail.getAmountslide() == null) {
			bindingResult.rejectValue("entity.amountslide",
					"javax.validation.constraints.NotNull.message");
		}
		if (loandetail.getTenure() == null) {
			bindingResult.rejectValue("entity.tenure",
					"javax.validation.constraints.NotNull.message");
		}
		if ((loandetail.getMinamount() != null
				&& loandetail.getMaxamount() != null && loandetail
				.getAmountslide() != null)
				&& (loandetail.getTenureperslide() != null
						&& loandetail.getMaxtenure() != null && loandetail
						.getMintenure() != null)) {

			if (loandetail.getMinamount() > loandetail.getMaxamount()) {
				bindingResult.rejectValue("entity.minamount",
						"loan.error.min.amount");
			} else if (loandetail.getMaxamount() < loandetail.getMinamount()) {
				bindingResult.rejectValue("entity.maxamount",
						"loan.error.max.amount");
			}

			if (loandetail.getAmountslide() > loandetail.getMaxamount()) {
				bindingResult.rejectValue("entity.amountslide",
						"loan.error.amount.slide.max");
			}

			if (loandetail.getMintenure() > loandetail.getMaxtenure()) {
				bindingResult.rejectValue("entity.mintenure",
						"loan.error.min.tenure");
			} else if (loandetail.getMaxtenure() < loandetail.getMintenure()) {
				bindingResult.rejectValue("entity.maxtenure",
						"loan.error.max.tenure");
			}

			if (loandetail.getTenureperslide() > loandetail.getMaxtenure()) {
				bindingResult.rejectValue("entity.tenureperslide",
						"loan.error.tenure.per.slide.max");
			}

		}

		if (!bindingResult.hasErrors()) {

			if (bean.getEntity().getLoanDetailID() == null) {
				Boolean checkloanID = loanDetailService
						.CheckByLoanId(loandetail.getLoan().getLoanID());
				if (checkloanID) {
					// bindingResult.rejectValue("entity.loan.condition_category","loan.error.loanid.exist");
					bean.addMessage(Message.ERROR, msgSrc.getMessage(
							"loan.error.loanid.exist", null, locale));
					bean.setListLoan(loanService.RemoveConditionWhenCreate(loandetail
							.getLoan().getCondition_category(),loandetail.getLoanDetailID()));
					model.addAttribute("bean", bean);
					response.setContentType("text/html; charset=UTF-8");
					model.addAttribute("loanCategory",
							systemConfig.getLoanCategoryMap());
					return "loandetail.addLoanDetail";
				}
			}


			// if (bean.getEntity().getLoanDetailID() != null) {
			// if (bean.getEntity().getLoan().getLoanID() != null) {
			// loan = loanService.findConditionNameByLoanID(bean
			// .getEntity().getLoan().getLoanID());
			// loanDetailService.updateCategoryAndNameByLoanID(bean
			// .getEntity().getLoan().getLoanID(), bean
			// .getEntity().getLoan().getCondition_category(),
			// loan.getCondition_name());
			// }
			//
			// }

			loandetail.setCreateDate(new Date());
			Long loanDetailID = loandetail.getLoanDetailID();
			loanDetailService.saveLoanDetail(loandetail);
			if (loanDetailID != null) {
				activityLogService.saveActivityLog(
						systemConfig.EDIT_LOAN_DETAIL_CALCULATOR,
						systemConfig.LOAN_CALCULATOR, locale,
						systemConfig.SYSTEM, null, userProfile.getAccount()
								.getId().toString());
			} else {
				activityLogService.saveActivityLog(
						systemConfig.ADD_LOAN_DETAIL_CALCULATOR,
						systemConfig.LOAN_CALCULATOR, locale,
						systemConfig.SYSTEM, null, userProfile.getAccount()
								.getId().toString());
			}
			bean.addMessage(Message.SUCCESS,
					msgSrc.getMessage("msg.save.success", null, locale));
			bean.setListLoan(loanService.RemoveConditionWhenCreate(loandetail.getLoan()
					.getCondition_category(), loandetail.getLoanDetailID()));
			model.addAttribute("loanCategory",
					systemConfig.getLoanCategoryMap());
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", bean);
			return "loandetail.addLoanDetail";

		} else {
			// bean.addMessage(Message.ERROR,
			// msgSrc.getMessage("msg.save.fail", null, locale));
			if (bindingResult.getFieldError("entity") != null) {
				bean.addMessage(Message.ERROR,
						bindingResult.getFieldError("entity")
								.getDefaultMessage());
			}
			response.setContentType("text/html; charset=UTF-8");
			bean.setListLoan(loanService.RemoveConditionWhenCreate(loandetail.getLoan()
					.getCondition_category(),loandetail.getLoanDetailID()));
			model.addAttribute("loanCategory",
					systemConfig.getLoanCategoryMap());
			model.addAttribute("bean", bean);
			return "loandetail.addLoanDetail";
		}

	}

	@RequestMapping(value = "/listCatagory", method = RequestMethod.GET)
	public @ResponseBody List<Loan> itemsGet(Model model,
			HttpServletRequest request,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) Long id,
			HttpServletResponse response) throws Exception {
		List<Loan> root = new ArrayList<Loan>();
//		root = loanService.findByCategory(category);
		root = loanService.RemoveConditionWhenCreate(category,id);
		response.setContentType("text/json; charset=UTF-8");
		return root;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showInfo(@RequestParam(required = false) Long id,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception {
		// Customer customer;
		Loan loan;
		LoanBean loanBean = new LoanBean();

		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			loan = loanService.findById(id);
		} else {
			loan = new Loan();
		}
		loanBean.setEntity(loan);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", loanBean);
		return "loan.showInfo";
	}

	@RequestMapping(value = "/showSetup", method = RequestMethod.GET)
	public String showSetupInfo(@RequestParam(required = false) Long id,
			HttpServletRequest request, Model model,
			HttpServletResponse response) throws Exception {
		// Customer customer;
		LoanDetail loandetail;
		LoanDetailBean loandetailBean = new LoanDetailBean();

		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			loandetail = loanDetailService.findById(id);
		} else {
			loandetail = new LoanDetail();
		}
		loandetailBean.setEntity(loandetail);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", loandetailBean);
		return "loandetail.showInfo";
	}

}
