package vn.com.unit.fe_credit.controller;

import static vn.com.unit.fe_credit.config.SystemConfig.UPLOAD_APPLYNOW;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.Scheduler;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.Book;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.ApplyNow;
import vn.com.unit.fe_credit.service.ApplyNowService;
import vn.com.unit.fe_credit.service.LoanActivityLogService;
import vn.com.unit.fe_credit.utils.ExcelUtils;
import vn.com.unit.fe_credit.utils.Utils;

@Controller
@RequestMapping("/apply_now")
public class ApplyNowController {

	@Autowired
	ApplyNowService applyNowService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private LoanActivityLogService activityLogService;

	@Autowired
	private UserProfile userProfile;

	@Autowired
	@Qualifier("schedulerFactoryBeanCluster")
	private SchedulerFactoryBean schedulerFactory;

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

	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listGetApplyNow(@ModelAttribute(value = "bean") ApplyNowBean bean, Model model, Locale locale, HttpServletRequest request)
			throws Exception {

		// start example
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));

		bean.setLimit(pagesize);
		bean.setEntity(new ApplyNow());

		if (bean.getReStatus() != bean.getStatus()) {
			// bean.setPage(1);
			bean.setReStatus(bean.getStatus());
		}
		int success = 0;
		List<Object[]> result = applyNowService.search(bean);
		if (CollectionUtils.isEmpty(result)) {
			bean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		Integer total = applyNowService.countSearch(bean);
		Integer size = pagesize * (bean.getPage() - 1);
		bean.setTotal(new Integer(total.toString()));
		model.addAttribute("result", result);
		model.addAttribute("systemConfig", systemConfig.getStatusApplyNew());
		model.addAttribute("categoryApplyNow", systemConfig.getStatusApplyNew());
		model.addAttribute("bean", bean);
		model.addAttribute("size", size);
		return "apply_now.list";
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String showInfo(@RequestParam(required = false) Long id, HttpServletRequest request, Model model, HttpServletResponse response)
			throws Exception {
		// Customer customer;
		ApplyNow applyNow;
		ApplyNowBean applyNowBean = new ApplyNowBean();

		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			applyNow = applyNowService.findById(id);
		} else
			applyNow = new ApplyNow();
		applyNowBean.setEntity(applyNow);
		response.setContentType("text/html; charset=UTF-8");
		model.addAttribute("bean", applyNowBean);
		return "apply_now.showInfo";
	}

	@RequestMapping(value = "/exporttoexcell1", method = RequestMethod.POST)
	public void exportToExel(@ModelAttribute(value = "bean") ApplyNowBean applyNowBean, HttpServletRequest request, HttpServletResponse response,
			Locale locale) {
		ServletContext servletContext = request.getSession().getServletContext();
		String ab = systemConfig.getConfig(SystemConfig.URL_DEFAULT);
		String templateFile = "loanSumit.xlsx";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		String template = servletContext.getRealPath("/WEB-INF/exportlist") + "/" + templateFile;
		String newFile = templateFile.substring(0, templateFile.length() - 5) + "_" + sdf.format(cal.getTime()) + "_" + new Date().getTime() + ".csv";

		ApplyNowBean resultBean = applyNowService.searchEx(applyNowBean);
		if (resultBean.getListResult().size() != 0) {
			try {

				ExcelUtils excelUtils = new ExcelUtils();
				XSSFWorkbook workbook = (XSSFWorkbook) excelUtils.getWorkbook(template);
				XSSFSheet sheet = workbook.getSheetAt(0);
				XSSFCellStyle cellStyle = ExcelUtils.setStyle(workbook, true);

				XSSFCellStyle cellStyleDate = workbook.createCellStyle();
				XSSFColor color = new XSSFColor();
				byte[] a = { (byte) 255, (byte) 255, (byte) 255 };
				color.setRGB(a);
				cellStyleDate.setBorderBottom(CellStyle.BORDER_THIN);
				cellStyleDate.setBorderLeft(CellStyle.BORDER_THIN);
				cellStyleDate.setBorderRight(CellStyle.BORDER_THIN);
				cellStyleDate.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setBottomBorderColor(color);
				cellStyle.setLeftBorderColor(color);
				cellStyle.setRightBorderColor(color);

				Row row = null;
				ApplyNow wo = null;
				Cell cell = null;
				String statusMsgs = "";

				for (int i = 0; i < resultBean.getListResult().size(); i++) {

					row = sheet.createRow(i + 1);
					wo = resultBean.getListResult().get(i);

					wo.setExportedDate(new Date());
					wo.setUpdate_date(new Date());
					wo.setStatus(2L);

					applyNowService.saveApplyNow(wo);

					statusMsgs = msgSrc.getMessage(systemConfig.getStatusApplyNew().get(Integer.parseInt(wo.getStatus().toString())), null,
							Utils.getLocale());
					/*
					 * cell = ExcelUtils.createXSSFCell(row, 0, i + 1, cellStyle, workbook.getCreationHelper());
					 */
					cell = ExcelUtils.createXSSFCell(row, 0, wo.getFullName(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 1, wo.getIdCardNumber(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 2, wo.getCellPhone(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 3, wo.getCity(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 4, wo.getAddress(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 5, wo.getProduct(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 6, wo.getEmail(), cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 7, "Mobile Application", cellStyle, workbook.getCreationHelper());
					cell = ExcelUtils.createXSSFCell(row, 8, "Mobile Application", cellStyle, workbook.getCreationHelper());

				}

				excelUtils.closeInputStream();
				// export file
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + newFile + "\"");
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				try {
					workbook.write(bos);
					byte[] bytes = bos.toByteArray();
					response.getOutputStream().write(bytes);
				} finally {
					bos.close();
				}
				activityLogService.saveLoanActivityLog(SystemConfig.EXPORT_LOAN_REPUEST, SystemConfig.LOAN_REQUEST, locale, SystemConfig.SYSTEM, null,
						userProfile.getAccount().getId().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/exportCSV", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	public String exportCSV(@ModelAttribute(value = "bean") ApplyNowBean applyNowBean, HttpServletRequest request, HttpServletResponse response,
			Locale locale, RedirectAttributes redirectAttributes) {

		try {

			Scheduler scheduler = this.schedulerFactory.getScheduler();
			TriggerState triggerState = scheduler.getTriggerState(TriggerKey.triggerKey("uploadApplyNowTrigger", "DEFAULT"));
			if (triggerState != null) {
				if (BooleanUtils.isNotTrue(triggerState.equals(TriggerState.PAUSED))) {
					throw new Exception("Cannot export. Auto mode is running");
				}
			}

			applyNowBean.setLimit(10000);
			ApplyNowBean resultBean = applyNowService.searchEx(applyNowBean);

			if (CollectionUtils.isEmpty(resultBean.getListResult())) {
				throw new Exception("Cannot export file");
			}

			ApplyNow wo = null;
			response.setContentType("text/csv;charset=utf-8");

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'_'HHmmss");
			String csvFileName = "ApllyNow_MobileApp" + "_" + sdf.format(new Date()) + ".csv";

			// creates mock data
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", csvFileName);
			response.setHeader(headerKey, headerValue);
			ICsvBeanWriter csvWriter;

			String filePath = systemConfig.getConfig(UPLOAD_APPLYNOW);
			// filePath = "d:\\";
			String pathToFileTmp = filePath + csvFileName;
			File fileDir = new File(pathToFileTmp);
			Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileDir), "UTF8"));
			writer.write("\ufeff");

			csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
			String[] header = { "LastName", "IdCardNumber", "PrimaryPhone", "City", "Address", "ProductCode", "Email", "SourceInfo", "Campaign" };
			csvWriter.writeHeader(header);

			for (int i = 0; i < resultBean.getListResult().size(); i++) {

				wo = resultBean.getListResult().get(i);

				wo.setExportedDate(new Date());
				wo.setUpdate_date(new Date());
				wo.setStatus(2L);

				applyNowService.saveApplyNow(wo);
				Book book4 = new Book(wo.getFullName(), wo.getIdCardNumber(), wo.getCellPhone(), wo.getCity(), wo.getAddress(), wo.getProduct(),
						wo.getEmail(), "Mobile Application", "Mobile Application");
				csvWriter.write(book4, header);

			}

			csvWriter.close();

			try (OutputStream out = response.getOutputStream()) {
				out.write(FileUtils.readFileToByteArray(fileDir));
			}

			try {
				activityLogService.saveLoanActivityLog(SystemConfig.EXPORT_LOAN_REPUEST, SystemConfig.LOAN_REQUEST, locale, SystemConfig.SYSTEM, null,
						userProfile.getAccount().getId().toString());
			} catch (Exception e) {
				// TODO: handle exception
			}

			response.flushBuffer();

		} catch (Exception e) {
			applyNowBean.addMessage(Message.ERROR, e.getMessage());
		}

		redirectAttributes.addFlashAttribute("bean", applyNowBean);
		return "redirect:/apply_now/list";
	}

}
