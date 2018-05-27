package vn.com.unit.fe_credit.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.CustomerBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.MgmPointConversionHistoryBean;
import vn.com.unit.fe_credit.bean.MgmSuggestedContactsBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.service.ContractMessagesService;
import vn.com.unit.fe_credit.service.CustomerActivityLogService;
import vn.com.unit.fe_credit.service.CustomerHobbyService;
import vn.com.unit.fe_credit.service.CustomerService;
import vn.com.unit.fe_credit.service.CustomerWishService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MessageService;
import vn.com.unit.fe_credit.service.MgmPointConversionHistoryService;
import vn.com.unit.fe_credit.service.MgmSuggestedContactsService;
import vn.com.unit.fe_credit.service.MgmViewPointService;
import vn.com.unit.fe_credit.utils.Utils;
import vn.com.unit.fe_credit.utils.excel.ExcelHelper;
import vn.com.unit.fe_credit.utils.excel.ExcelUtils;
import vn.com.unit.webservice.Hotline;
import vn.com.unit.webservice.SendMessSoapServies;

@Controller
@RequestMapping("/master_data/customer")
public class CustomerController {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	MasterdataDetailService masterdatalDetailService;
	
	@Autowired
	MessageSource msgSrc;

	@Autowired
	CustomerService customerService;

	@Autowired
	CustomerHobbyService customerHobbyService;

	@Autowired
	MgmSuggestedContactsService mgmSuggestedContactsService;

	@Autowired
	MgmViewPointService mgmViewPointService;

	@Autowired
	MgmPointConversionHistoryService mgmPointConverHistoryService;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	CustomerWishService customerWishService;

	@Autowired
	MessageService messageDashBoardService;

	@Autowired
	ContractMessagesService contractMessagesService;

	@Autowired
	CustomerActivityLogService customerActivityLogService;

	@Autowired
	UserProfile userProfile;

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

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

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(@ModelAttribute(value = "bean") CustomerBean customerBean, Model model,
			@RequestParam(value = "message", required = false) String message, HttpServletRequest request, Locale locale,
			HttpServletResponse response) throws Exception {
		// if (message != null && message.equals("success")) {
		// customerBean.addMessage(Message.SUCCESS,
		// msgSrc.getMessage("msg.save.success", null, locale));
		// }
		// HttpSession session = request.getSession();

		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		// if(request.getMethod().equals(RequestMethod.GET.name())){
		// if(session != null && session.getAttribute("customer@@@customerName")
		// != null){
		// customerBean.setCustomerName(session.getAttribute("customer@@@customerName").toString());
		// }
		// if(session != null && session.getAttribute("customer@@@cellPhone") !=
		// null){
		// customerBean.setCellPhone(session.getAttribute("customer@@@cellPhone").toString());
		// }
		// if(session != null && session.getAttribute("customer@@@cardNo") !=
		// null){
		// customerBean.setCardNo(session.getAttribute("customer@@@cardNo").toString());
		// }
		// if(session != null && session.getAttribute("customer@@@status") !=
		// null){
		// customerBean.setStatus(session.getAttribute("customer@@@status") !=
		// null ?
		// Integer.parseInt(session.getAttribute("customer@@@status").toString())
		// : null);
		// }
		// if(session != null && session.getAttribute("customer@@@Page") !=
		// null){
		// customerBean.setPage(Integer.parseInt(session.getAttribute("customer@@@Page").toString()));
		// }
		// }else{
		// session.setAttribute("customer@@@customerName",
		// customerBean.getCustomerName());
		// session.setAttribute("customer@@@cellPhone",
		// customerBean.getCellPhone());
		// session.setAttribute("customer@@@cardNo", customerBean.getCardNo());
		// session.setAttribute("customer@@@status", customerBean.getStatus());
		// session.setAttribute("customer@@@Page", customerBean.getPage());
		// }

		customerBean.setLimit(pagesize);
		customerBean.setEntity(new Customer());
		CustomerBean result = new CustomerBean();
		if (request.getMethod().equals(RequestMethod.GET.name())) {
			customerBean.setStatus(1);
		}
		try {
			result = customerService.search(customerBean);
			// if(customerBean != null &&
			// session.getAttribute("contract@@@Page") != null &&
			// customerBean.getListResult().size() == 0){
			// customerBean.setPage(1);
			// customerBean = customerService.search(customerBean);
			// }
		} catch (Exception ex) {
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
		}
		if (CollectionUtils.isEmpty(result.getListResult())) {
			result.addMessage(Message.INFO, msgSrc.getMessage("msg.search.nodata", null, locale));
		}
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", result);
		return "master_data.customer.list";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit_get(@RequestParam(required = false) Long id, HttpServletRequest request, Model model, HttpServletResponse response)
			throws Exception {
		Customer customer;

		CustomerBean customerBean = new CustomerBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			customer = customerService.findById(id);
		} else {
			customer = new Customer();
		}
		customerBean.setSuccesorfail("fail");
		customerBean.setEntity(customer);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", customerBean);
		return "master_data.customer.edit";
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	private String editAction(@ModelAttribute(value = "bean") @Valid CustomerBean customerBean, BindingResult bindingResult, Model model,
			Locale locale, RedirectAttributes redirectAttributes, HttpServletResponse response) {
		customerBean.clearMessages();
		Customer customer = customerBean.getEntity();
		if (StringUtils.isEmpty(customer.getOldUserId())) {
			if (StringUtils.isEmpty(customer.getProvince())) {
				bindingResult.rejectValue("entity.province", "javax.validation.constraints.NotNull.message");
			}
		}
		if (StringUtils.isEmpty(customer.getFirstName())) {
			bindingResult.rejectValue("entity.firstName", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(customer.getLastName())) {
			bindingResult.rejectValue("entity.lastName", "javax.validation.constraints.NotNull.message");
		}
		/*
		 * if (StringUtils.isEmpty(customer.getFullName())) { bindingResult.rejectValue("entity.fullName",
		 * "javax.validation.constraints.NotNull.message"); }
		 */
		if (StringUtils.isEmpty(customer.getIdCardNumber())) {
			bindingResult.rejectValue("entity.idCardNumber", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(customer.getOldUserId())) {
			if (customer.getBirthday() == null) {
				bindingResult.rejectValue("entity.birthday", "javax.validation.constraints.NotNull.message");
			}
		}
		/*
		 * if (customer.getPassword() == null || customer.getPassword().equalsIgnoreCase("") ) {
		 * bindingResult.rejectValue("entity.password", "javax.validation.constraints.NotNull.message"); }
		 */
		if (StringUtils.isEmpty(customer.getCellPhone())) {
			bindingResult.rejectValue("entity.cellPhone", "javax.validation.constraints.NotNull.message");
		} else {
			Customer customer_ = customerService.findByIdCellPhone(customer.getCellPhone());
			if (customer_ != null) {
				if (customer.getCustomerId() == null) {
					bindingResult.rejectValue("entity.cellPhone", "customer.id.cell.phone.must.be.unique");
				} else if (!customer.getCustomerId().equals(customer_.getCustomerId())) {
					bindingResult.rejectValue("entity.cellPhone", "customer.id.cell.phone.must.be.unique");
				}
			}
		}
		if (customer.getStatus() == null) {
			bindingResult.rejectValue("entity.status", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isNotEmpty(customer.getEmailAddress())) {
			String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			if (!customer.getEmailAddress().matches(EMAIL_REGEX)) {
				bindingResult.rejectValue("entity.emailAddress", "customer.email.invalid.format");
			}
		}

		if (!bindingResult.hasErrors()) {
			// not select parent
			try {
				boolean flag = false;
				Integer rand = 0;
				if (customerBean.getEntity().getCustomerId() == null) {
					rand = (int) (Math.random() * 900000) + 100000;
					customer.setPassword(Utils.encryptMD5(rand.toString()));
					flag = true;
				}
				// kiem tr duoi data co pass chua.neu chua thi ma hoa
				if (customerBean.getEntity().getCustomerId() != null) {
					Customer checkPass = new Customer();
					checkPass = customerService.findById(customerBean.getEntity().getCustomerId());
					if (checkPass.getPassword() == null) {
						rand = (int) (Math.random() * 900000) + 100000;
						customer.setPassword(Utils.encryptMD5(rand.toString()));
						flag = true;
					}
				}
				if (StringUtils.isNotEmpty(customer.getImagePath())) {
					String fileTempName = customer.getImagePath();
					String newName = Utils.moveTempToUploadFolder(fileTempName, systemConfig);
					customer.setImagePath(newName);
				} else {
					customer.setImagePath(null);
				}
				Long checkid = customer.getCustomerId();
				customerBean.getEntity().setCreateDate(new Date());
				customerService.save(customerBean);
				customerBean.setSuccesorfail("succes");
				if (checkid != null)
					customerActivityLogService.saveCustomerActivityLog(SystemConfig.EDIT_CUSTOMER, SystemConfig.CUSTOMER, locale, SystemConfig.SYSTEM,
							String.valueOf(checkid), userProfile.getAccount().getId().toString());
				else
					customerActivityLogService.saveCustomerActivityLog(SystemConfig.ADD_NEW_HOBBY, SystemConfig.CUSTOMER, locale, SystemConfig.SYSTEM,
							String.valueOf(customerBean.getEntity().getCustomerId()), userProfile.getAccount().getId().toString());
				customerBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));

				if (flag) {
					String send = SendMessSoapServies.sendMess(
							"+84" + customerBean.getEntity().getCellPhone().substring(1, customerBean.getEntity().getCellPhone().length()),
							msgSrc.getMessage("web.customer.newpass", new String[] { rand.toString() }, locale), "Status");
				}

			} catch (Exception e) {
				e.printStackTrace();
				customerBean.setSuccesorfail("fail");
				customerBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
				model.addAttribute("bean", customerBean);
				model.addAttribute("success", false);
				return "master_data.customer.edit";
			}
			response.setContentType("text/html; charset=UTF-8");
			model.addAttribute("bean", customerBean);
			// redirectAttributes.addFlashAttribute("bean", customerBean);
			model.addAttribute("success", true);
			// return "redirect:/master_data/customer/list";
			return "master_data.customer.edit";
		} else {
			if (bindingResult.getFieldError("entity") != null) {

				customerBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			}
			customerBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			customerBean.setSuccesorfail("fail");
			model.addAttribute("bean", customerBean);
			model.addAttribute("success", false);
			response.setContentType("text/html; charset=UTF-8");
			return "master_data.customer.edit";
		}
	}

	@RequestMapping(value = "/viewMGMPointConversationHistory", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewMGMPointConversationHistory(@ModelAttribute(value = "mgmSCBean") MgmSuggestedContactsBean mgmSCBean,
			@ModelAttribute(value = "mgmPCHBean") MgmPointConversionHistoryBean mgmPCHBean, @RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, Model model, HttpServletResponse response, Locale locale) throws Exception {
		try {

			model.addAttribute("id", id);

			Map<Integer, String> mgmRedeemStatusMap = new LinkedHashMap<Integer, String>(systemConfig.mgmRedeemStatusMap(locale));
			Map<Integer, String> mgmSCInviteMethodLst = new LinkedHashMap<Integer, String>(systemConfig.mgmSCInviteMethodLst(locale));

			Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
			mgmPCHBean.setLimit(pagesize);
			MgmPointConversionHistoryBean mgmHistorResult = mgmPointConverHistoryService.search(mgmPCHBean, id);
			model.addAttribute("mgmPCHBean", mgmHistorResult);
			model.addAttribute("mgmRedeemStatusMap", mgmRedeemStatusMap);
			model.addAttribute("mgmSCInviteMethodLst", mgmSCInviteMethodLst);

		} catch (Exception e) {
			logger.debug("##viewMGMListFriend##", e);
		}

		return "DEF.MGM.PointConversationHistory";

	}

	@RequestMapping(value = "/viewMGMListFriend", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewMGMListFriend(@ModelAttribute(value = "mgmSCBean") MgmSuggestedContactsBean mgmSCBean,
			@ModelAttribute(value = "mgmPCHBean") MgmPointConversionHistoryBean mgmPCHBean, @RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, Model model, HttpServletResponse response, Locale locale) throws Exception {

		try {
			model.addAttribute("id", id);

			Map<Integer, String> mgmSCStatusLst = new LinkedHashMap<Integer, String>(systemConfig.mgmSCStatusLst(locale));
			Map<Integer, String> mgmSCInviteMethodLst = new LinkedHashMap<Integer, String>(systemConfig.mgmSCInviteMethodLst(locale));

			Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
			mgmSCBean.setLimit(pagesize);
			MgmSuggestedContactsBean mgmSCResult = mgmSuggestedContactsService.searchListFriendsByCustomerId(mgmSCBean, id);

			model.addAttribute("mgmSCStatusLst", mgmSCStatusLst);
			model.addAttribute("mgmSCInviteMethodLst", mgmSCInviteMethodLst);
			model.addAttribute("mgmSCBean", mgmSCResult);

		} catch (Exception e) {
			logger.debug("##viewMGMListFriend##", e);
		}

		return "DEF.MGM.ListFriend";

	}

	@ModelAttribute(value = "listMessStatus")
	public Map<Integer, String> allMessStatus() {
		Map<Integer, String> allMessStatus = systemConfig.getMessageStatusList();
		return allMessStatus;
	}

	@ModelAttribute(value = "listMessType")
	public Map<Integer, String> allMessType() {
		List<Hotline> getValueDAO = masterdatalDetailService.findDetail("MessageType");
		Map<Integer, String> allContractCCMTypes = new HashMap<Integer, String>();
		if (getValueDAO != null) {
			for (Hotline iteam : getValueDAO) {
				allContractCCMTypes.put(Integer.parseInt(iteam.getCode()), iteam.getName());
			}
		}
		return allContractCCMTypes;
	}
	
	@RequestMapping(value = "/view", method = { RequestMethod.GET, RequestMethod.POST })
	public String viewCustomer(@ModelAttribute(value = "bean") CustomerBean customerBean,
			@ModelAttribute(value = "mgmSCBean") MgmSuggestedContactsBean mgmSCBean,
			@ModelAttribute(value = "mgmPCHBean") MgmPointConversionHistoryBean mgmPCHBean, @RequestParam(value = "id", required = true) Long id,
			HttpServletRequest request, Model model, HttpServletResponse responses, Locale locale) throws Exception {

		try {

			Customer customer = customerService.findById(id);
			customerBean.setCustomerHobbyLst(customerHobbyService.findByCustomerId(id));
			customerBean.setCustomerWishLst(customerWishService.findByCustomerId(id));
			
			
			customerBean.setMessgesLst(messageDashBoardService.findByCustomerIdNoCon(id));
			
			Map<String, Object> messgesLstMap = messageDashBoardService.getInboxList(id, null, "ALL", SystemConfig.SEARCH_TYPE_LIST_ONLY, 1, 100);
			model.addAttribute("messgesLstMap", messgesLstMap.get("LIST"));
			
			customerBean.setMessgesLstContract(messageDashBoardService.findByCustomerIdConTract(id));
			customerBean.setCustomerActivityLogs(customerActivityLogService.customerActivityLogsCustomer(SystemConfig.CUSTOMER, id));

			model.addAttribute("customer", customer);
			model.addAttribute("id", id);

			// MGM.ListFriends
			// customerBean.setSuggestedContactsLst(suggestedContactsService.findByCustomerId(id));

			String address = "";
			if (StringUtils.isNotEmpty(customer.getAddressNo())) {
				address += customer.getAddressNo() + " ";
			}
			if (StringUtils.isNotEmpty(customer.getStreet())) {
				address += customer.getStreet() + " ";
			}
			if (StringUtils.isNotEmpty(customer.getWard())) {
				address += customer.getWard() + " ";
			}
			if (StringUtils.isNotEmpty(customer.getDistrict())) {
				address += customer.getDistrict() + " ";
			}
			if (StringUtils.isNotEmpty(customer.getProvince())) {
				address += customer.getProvince() + ", ";
			}
			if (StringUtils.isNotEmpty(address)) {
				address = address.substring(0, address.length() - 2);
			}
			customerBean.setAddrestmap(address);

			// MGM.ListFriends

			Map<Integer, String> mgmSCStatusLst = new LinkedHashMap<Integer, String>(systemConfig.mgmSCStatusLst(locale));
			Map<Integer, String> mgmSCInviteMethodLst = new LinkedHashMap<Integer, String>(systemConfig.mgmSCInviteMethodLst(locale));

			Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
			mgmSCBean.setLimit(pagesize);
			MgmSuggestedContactsBean mgmSCResult = mgmSuggestedContactsService.searchListFriendsByCustomerId(mgmSCBean, id);

			model.addAttribute("mgmSCStatusLst", mgmSCStatusLst);
			model.addAttribute("mgmSCInviteMethodLst", mgmSCInviteMethodLst);
			model.addAttribute("mgmSCBean", mgmSCResult);

			// MGM.PointConversationHistory

			mgmPCHBean.setLimit(pagesize);
			MgmPointConversionHistoryBean mgmHistorResult = mgmPointConverHistoryService.search(mgmPCHBean, id);
			
			model.addAttribute("mgmPCHBean", mgmHistorResult);
			Map<Integer, String> mgmRedeemStatusMap = new LinkedHashMap<Integer, String>(systemConfig.mgmRedeemStatusMap(locale));
			model.addAttribute("mgmRedeemStatusMap", mgmRedeemStatusMap);

			// MGM.ViewPoint

			Map<String, Object> customerPointSummary = mgmViewPointService.getCustomerPointSummary(id);
			model.addAttribute("customerPointSummary", customerPointSummary);

			customerBean.setEntity(customer);
			model.addAttribute("bean", customerBean);

		} catch (Exception e) {
			customerBean.addMessage(Message.ERROR, e.getMessage());
		}
		return "master_data.customer.view";
	}

	// Get page file import
	@RequestMapping(value = "/init", method = { RequestMethod.GET, RequestMethod.POST })
	public String init(@ModelAttribute(value = "bean") CustomerBean customerBean, HttpServletRequest request, Model model) {
		// pendingPaymentBean = new PendingPaymentBean();
		model.addAttribute("systemConfig", systemConfig);
		model.addAttribute("bean", customerBean);
		return "master_data.customer.import";
	}

	// down load template file import customer
	@RequestMapping(value = "/downloadImportCustomer", method = RequestMethod.POST)
	public void downloadImportVSD(HttpServletResponse response) throws FileNotFoundException, IOException {
		repairFileDownload(response, "trading_vsd");
	}

	private void repairFileDownload(HttpServletResponse response, String type) throws FileNotFoundException, IOException {
		String template = "ImportCustomer.xlsx";
		String fileName = null;
		template = "/WEB-INF/exportlist/" + template;
		fileName = template.substring(template.lastIndexOf("/") + 1);
		template = servletContext.getRealPath(template);
		ExcelUtils excelUtils = new ExcelUtils();
		Workbook workbook = excelUtils.getWorkbook(template);
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			byte[] bytes = bos.toByteArray();
			response.getOutputStream().write(bytes);
		} finally {
			bos.close();
		}
	}

	@RequestMapping(value = "/import", headers = "content-type=multipart/*", method = { RequestMethod.POST })
	public String importCustomer(MultipartHttpServletRequest multipartHttpServletRequest, @ModelAttribute(value = "bean") CustomerBean customerBean,
			Model model, Locale locale, HttpServletRequest req, HttpServletResponse resp, RedirectAttributes redirectAttributes) throws Exception {

		Workbook workbook = null;
		Sheet sheetBuy = null;
		MultipartFile multipartFile = null;
		Row row = null;
		try {
			multipartFile = multipartHttpServletRequest.getFile("fileUpload");

			if (multipartFile.equals(null)) {
				return "master_data.customer.import";
			}

			String contentType = multipartFile.getContentType();
			try {
				if (StringUtils.equals(contentType, "application/vnd.ms-excel")) {
					workbook = new HSSFWorkbook(multipartFile.getInputStream());
				} else {
					workbook = new XSSFWorkbook(multipartFile.getInputStream());
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				customerBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.import.onlyExcelAllow", null, locale));
				return "master_data.customer.import";
			}
			sheetBuy = workbook.getSheet("VSD");
			// kiem tra loai file import vao

			if (sheetBuy == null) {
				customerBean.addMessage(Message.ERROR, msgSrc.getMessage("pendingPayment.file.not.equal.template", null, locale));
				return "master_data.customer.import";
			}

			CellStyle cellStyleError = workbook.createCellStyle();
			// cellStyleError.set màu
			Font fontRed = workbook.createFont();
			fontRed.setColor(HSSFColor.RED.index);
			cellStyleError.setFont(fontRed);

			ExcelHelper excelHelper = new ExcelHelper();

			List<Customer> customerImport = new ArrayList<Customer>();
			String err = "";
			boolean isHasError = false;
			for (int i = 1; i < sheetBuy.getPhysicalNumberOfRows(); i++) {

				Customer customer = new Customer();
				row = (Row) sheetBuy.getRow(i);
				customer.setFirstName(row.getCell(0).getStringCellValue());

				if (row.getCell(0) != null) {
					String firstName = String.valueOf(row.getCell(0).getStringCellValue());
					if (StringUtils.isNotEmpty(firstName)) {
						customer.setFirstName(firstName);
					} else {
						row.createCell(0).setCellStyle(cellStyleError);
						excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.first.name.null", null, locale) + " ");
						isHasError = true;
					}
				} else {
					row.createCell(0).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.first.name.null", null, locale) + " ");
					isHasError = true;
				}

				if (row.getCell(1) != null) {
					String lastName = String.valueOf(row.getCell(1).getStringCellValue());
					if (StringUtils.isNotEmpty(lastName)) {
						customer.setLastName(lastName);
					} else {
						row.createCell(1).setCellStyle(cellStyleError);
						excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.last.name.null", null, locale) + " ");
						isHasError = true;
					}
				} else {
					row.createCell(1).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.last.name.null", null, locale) + " ");
					isHasError = true;
				}
				if (row.getCell(2) != null) {
					String midName = String.valueOf(row.getCell(2).getStringCellValue());
					if (StringUtils.isNotEmpty(midName)) {
						customer.setMiddleName(midName);
					} else {
						customer.setMiddleName(null);
					}
				} else {
					customer.setMiddleName(null);
				}
				if (row.getCell(3) != null) {
					String idCarNumber = String.valueOf(row.getCell(3));
					if (StringUtils.isNotEmpty(idCarNumber)) {
						Customer customerSearchIdcarNumber = customerService.findByIdCardNumber(idCarNumber.trim());

						if (customerSearchIdcarNumber != null) {
							// Bôi đỏ
							// Ghi log
							// Set flag có lỗi
							row.getCell(3).setCellStyle(cellStyleError);
							excelHelper.fillCell(sheetBuy, i, 14,
									err += " " + msgSrc.getMessage("customer.field.idcarnumber.exist", null, locale) + " ");
							isHasError = true;
						} else
							customer.setIdCardNumber(idCarNumber.trim());
					} else {
						row.createCell(3).setCellStyle(cellStyleError);
						excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.idcarnumber.nul", null, locale) + " ");
						isHasError = true;
					}
				} else {
					row.createCell(3).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.idcarnumber.nul", null, locale) + " ");
					isHasError = true;
				}

				if (row.getCell(4) != null) {
					String cellPhone = String.valueOf(row.getCell(4));
					;
					if (StringUtils.isNotEmpty(cellPhone)) {
						Customer customerSearchCellPhone = customerService.findByIdCellPhone(cellPhone.trim());
						if (customerSearchCellPhone != null) {
							// Bôi đỏ
							// Ghi log
							// Set flag có lỗi
							row.getCell(4).setCellStyle(cellStyleError);
							excelHelper.fillCell(sheetBuy, i, 14,
									err += " " + msgSrc.getMessage("customer.field.cellphone.exist", null, locale) + " ");
							isHasError = true;
						} else
							customer.setCellPhone(cellPhone.trim());
					} else {
						row.createCell(4).setCellStyle(cellStyleError);
						excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.cellphone.null", null, locale) + " ");
						isHasError = true;
					}
				} else {
					row.createCell(4).setCellStyle(cellStyleError);
					excelHelper.fillCell(sheetBuy, i, 14, err += " " + msgSrc.getMessage("customer.field.cellphone.null", null, locale) + " ");
					isHasError = true;
				}
				if (row.getCell(5) != null) {
					String emailAddress = String.valueOf(row.getCell(5).getRichStringCellValue());
					if (StringUtils.isNotEmpty(emailAddress)) {
						customer.setEmailAddress(emailAddress);
					} else {
						customer.setEmailAddress(null);
					}
				} else {
					customer.setEmailAddress(null);
				}
				if (row.getCell(6) != null) {
					if (row.getCell(6).getDateCellValue() != null) {
						customer.setBirthday(row.getCell(6).getDateCellValue());
					} else {
						customer.setBirthday(null);
					}
				} else {
					customer.setBirthday(null);
				}
				if (row.getCell(7) != null) {
					String addressNo = String.valueOf(row.getCell(7).getRichStringCellValue());
					if (StringUtils.isNotEmpty(addressNo)) {
						customer.setAddressNo(addressNo);
					} else {
						customer.setAddressNo(null);
					}
				} else {
					customer.setAddressNo(null);
				}

				if (row.getCell(8) != null) {
					String street = String.valueOf(row.getCell(8).getRichStringCellValue());
					if (StringUtils.isNotEmpty(street)) {
						customer.setStreet(street);
					} else {
						customer.setStreet(null);
					}
				} else {
					customer.setStreet(null);
				}
				if (row.getCell(9) != null) {
					String ward = String.valueOf(row.getCell(9).getRichStringCellValue());
					if (StringUtils.isNotEmpty(ward)) {
						customer.setWard(ward);
					} else {
						customer.setWard(null);
					}
				} else {
					customer.setWard(null);
				}
				if (row.getCell(10) != null) {
					String district = String.valueOf(row.getCell(10).getRichStringCellValue());
					if (StringUtils.isNotEmpty(district)) {
						customer.setDistrict(district);
					} else {
						customer.setDistrict(null);
					}
				} else {
					customer.setDistrict(null);
				}
				if (row.getCell(11) != null) {
					String province = String.valueOf(row.getCell(11).getRichStringCellValue());
					if (StringUtils.isNotEmpty(province)) {
						customer.setProvince(province);
					} else {
						customer.setProvince(null);
					}
				} else {
					customer.setProvince(null);
				}
				if (row.getCell(12) != null) {
					String userName = String.valueOf(row.getCell(12).getRichStringCellValue());
					if (StringUtils.isNotEmpty(userName)) {
						customer.setUserName(userName);
					} else {
						customer.setUserName(null);
					}
				} else {
					customer.setUserName(null);
				}
				if (row.getCell(13) != null) {
					String oldUserId = String.valueOf(row.getCell(13).getRichStringCellValue());
					if (StringUtils.isNotEmpty(oldUserId)) {
						customer.setOldUserId(oldUserId);
					} else {
						customer.setOldUserId(null);
					}
				} else {
					customer.setOldUserId(null);
				}
				customer.setStatus(1);
				customer.setIsLogged(false);
				customer.setDATEIMPORT(new Date());

				Integer rand = 0;
				rand = (int) (Math.random() * 900000) + 100000;
				customer.setPassword(Utils.encryptMD5(rand.toString()));
				customer.setSubject(rand.toString());

				customer.setCreateDate(new Date());
				String fullName = "";
				if (StringUtils.isNotEmpty(customer.getMiddleName())) {
					fullName = customer.getLastName() + " " + customer.getMiddleName() + " " + customer.getFirstName();
				} else {
					fullName = customer.getLastName() + " " + customer.getFirstName();
				}
				customer.setFullName(fullName);
				customerImport.add(customer);

			}
			if (isHasError == false) {

				try {

					customerBean.setCustomerImportLst(customerImport);

					customerService.saveCustomers(customerImport);

					if (customerImport == null || customerImport.size() <= 0) {
						customerBean.addMessage(Message.ERROR, msgSrc.getMessage("pendingPayment.import.fail", null, locale));
					} else {
						for (Customer iteamCustomer : customerImport) {
							String send = SendMessSoapServies.sendMess(
									"+84" + iteamCustomer.getCellPhone().substring(1, iteamCustomer.getCellPhone().length()),
									msgSrc.getMessage("web.customer.newpass", new String[] { iteamCustomer.getSubject() }, locale), "Status");
						}
						customerBean.addMessage(Message.SUCCESS, msgSrc.getMessage("pendingPayment.import.done", null, locale));

					}
					// gi log
					try {
						if (customerImport != null || customerImport.size() > 0)
							customerActivityLogService.saveCustomerImportActivityLog(SystemConfig.SAVE_CUSTOMER_IMPORT, SystemConfig.CUSTOMER, locale,
									SystemConfig.SYSTEM, null, userProfile.getAccount().getId().toString(),
									"Import " + customerImport.size() + " customer");
					} catch (Exception e) {
						logger.debug("##saveCustomerImportActivityLog##", e);
					}
				} catch (Exception e) {
					logger.debug("##saveCustomers##", e);
				}

			} else {
				resp.setContentType(contentType);
				resp.setHeader("Content-Disposition", "attachment; filename=\"OUT_" + multipartFile.getOriginalFilename() + "\"");
				ServletOutputStream servletOutputStream = resp.getOutputStream();
				workbook.write(servletOutputStream);
				resp.flushBuffer();
				return "redirect:master_data/customer/init";
			}

		} catch (IOException e) {
			System.out.println("Format file is incorrect!");
		}

		return "master_data.customer.import";
	}

	// delete 10 ngay
	@RequestMapping(value = "/delete10", method = RequestMethod.GET)
	public void delete10(HttpServletResponse response) throws FileNotFoundException, IOException {
		Date date = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -10);
		// add 10 days
		date = cal.getTime();
		customerService.deleteCustomerImport(date);
	}

}
