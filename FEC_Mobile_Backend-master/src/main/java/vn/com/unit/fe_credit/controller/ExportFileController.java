package vn.com.unit.fe_credit.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.ExportDataToVTigerBean;
import vn.com.unit.fe_credit.bean.ExportRedeemTrasPointBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.MgmExportVtigerBean;
import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.DataExportMGMVtiger;
import vn.com.unit.fe_credit.config.DataExportRedeemMomo;
import vn.com.unit.fe_credit.config.DataExportRedeemPayment;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;
import vn.com.unit.fe_credit.entity.MgmSuggestedContacts;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ExportFileDataToVTigerService;
import vn.com.unit.fe_credit.service.ExportFileMgmService;
import vn.com.unit.fe_credit.service.ExportFileRedeemPointService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MasterdataService;
import vn.com.unit.fe_credit.utils.Utils;

@Controller
@RequestMapping("/system/exportFile")
public class ExportFileController {

	Logger logger = LoggerFactory.getLogger(ExportFileController.class);

	@Autowired
	ExportFileRedeemPointService exportFileRedeemPointService;

	@Autowired
	ExportFileDataToVTigerService exportFileDataToVTigerService;

	@Autowired
	MasterdataDetailService masterdataDetailService;

	@Autowired
	MasterdataService masterdatalService;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	AccountTeamService accountTeamService;

	@Autowired
	AccountService accountService;

	@Autowired
	MessageSource messageSource;

	@Autowired
	ActivityLogService activityLogService;

	@Autowired
	UserProfile userProfile;
	
	@Autowired
	ExportFileMgmService exportFileMgmService;

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

	@RequestMapping(value = "/redeemTransactionPoint/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listExportFileRedeem(@ModelAttribute(value = "bean") ExportRedeemTrasPointBean redeemTrasPointBean, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		try {

			Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
			redeemTrasPointBean.setLimit(pagesize);
			redeemTrasPointBean.setListRedeemTrasPoint(exportFileRedeemPointService.findAllex());
			response.setContentType("text/html; charset=UTF-8");
			ExportRedeemTrasPointBean result = exportFileRedeemPointService.search(redeemTrasPointBean);
			model.addAttribute("redeemTrasPointBean", result);

		} catch (Exception ex) {
			redeemTrasPointBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
		}
		return "system.exportFile.redeemTransactionPoint.list";

	}

	private volatile boolean called;

	@RequestMapping(value = "/redeemTransactionPoint/export", method = { RequestMethod.GET })
	public String doExportRedeemTransactionPoint(@ModelAttribute(value = "bean") RedeemTrasPointBean redeembBean,
			@ModelAttribute(value = "beans") ExportRedeemTrasPointBean exportRedeemBean, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

		try {
			
			if (redeembBean.getEntity().getTranxType().equals("0")) {
				exportRedeemBean.addMessage(Message.ERROR, msgSrc.getMessage("export.status.warning", null, locale));
				redirectAttributes.addFlashAttribute("bean", exportRedeemBean);
				return "redirect:/system/exportFile/redeemTransactionPoint/list/";
			}

//			if (exportFileRedeemPointService.isHasExportingProcess(redeembBean)) {
//				exportRedeemBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.export.running", null, locale));
//				redirectAttributes.addFlashAttribute("bean", exportRedeemBean);
//				return "redirect:/system/exportFile/redeemTransactionPoint/list/";
//			}
			redeembBean.setFlagExport(true);
			RedeemTrasPointBean resultExport = exportFileMgmService.exportFileRedeem(redeembBean);
			
			if (!resultExport.getListResult().isEmpty()) {
				exportRedeemBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.export.success", null, locale));
			} else {
				exportRedeemBean.addMessage(Message.WARNING, msgSrc.getMessage("msg.export.nodata", null, locale));
			}
			redirectAttributes.addFlashAttribute("bean", exportRedeemBean);
			return "redirect:/system/exportFile/redeemTransactionPoint/list/";

		} catch (Exception e) {
			logger.debug("##Exception##", e);
			listExportFileRedeem(exportRedeemBean, model, locale, request, response);
			exportRedeemBean.addMessage(Message.ERROR, e.getMessage());
			redirectAttributes.addFlashAttribute("bean", exportRedeemBean);
			return "redirect:/system/exportFile/redeemTransactionPoint/list/";
		}

	}

	@RequestMapping(value = "/redeemTransactionPoint/download", method = RequestMethod.GET)
	public void doDownloadRedeemTransactionPoint(@RequestParam(required = true) String fileName, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isEmpty(fileName)) {
			return;
		}
		try {
			fileName = fileName + ".csv";
			String domain = "";
			String path = "";
			File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.REDEEM_PATH) + fileName);
			if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
				path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
			} else {
				path = systemConfig.getConfig(SystemConfig.REDEEM_PATH) + fileName;
			}
			File fileTemp = new File(domain + path);

			FileInputStream fileInputStream = new FileInputStream(fileTemp);
			byte[] arr = new byte[1024];
			int numRead = -1;

			response.addHeader("Content-Length", Long.toString(fileTemp.length()));
			if (StringUtils.isNotEmpty(request.getParameter("isDownload")) && request.getParameter("isDownload").equalsIgnoreCase("download")) {
				response.setContentType("application/octet-stream ; charset=UTF-8");
			} else {
				// response.setContentType(getContentType(fileName));
			}
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);
			while ((numRead = fileInputStream.read(arr)) != -1) {
				response.getOutputStream().write(arr, 0, numRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/redeemTransactionPoint/delete", method = RequestMethod.GET)
	public String doDeleteFileExportRedeemTransactionPoint(@ModelAttribute(value = "bean") ExportRedeemTrasPointBean exportRedeemBean, Model model,
			Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (exportRedeemBean.getEntity().getExportRedeemId() != null) {
			try {
				if (exportRedeemBean.getEntity().getExportRedeemId() != null) {
					exportFileRedeemPointService.deleteExportRedeem(exportRedeemBean.getEntity().getExportRedeemId());

					String fileName = exportRedeemBean.getEntity().getFileName() + ".csv";
					String domain = "";
					String path = "";
					File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.REDEEM_PATH) + fileName);
					if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
						path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
					} else {
						path = systemConfig.getConfig(SystemConfig.REDEEM_PATH) + fileName;
					}
					File fileTemp = new File(domain + path);
					fileTemp.delete();
					exportRedeemBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
				exportRedeemBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", exportRedeemBean);
		return "redirect:/system/exportFile/redeemTransactionPoint/list/";
	}

	@RequestMapping(value = "/dataToVTiger/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String listExportFileVTiger(@ModelAttribute(value = "bean") ExportDataToVTigerBean exportVTigerBean, Model model, Locale locale,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		exportVTigerBean.setLimit(pagesize);
		exportVTigerBean.setListVTiger(exportFileDataToVTigerService.findAllex());
		ExportDataToVTigerBean result = exportFileDataToVTigerService.search(exportVTigerBean);
		response.setContentType("text/html; charset=UTF-8");
		try {
			result = exportFileDataToVTigerService.search(exportVTigerBean);
		} catch (Exception ex) {
			result.addMessage(Message.ERROR, msgSrc.getMessage("msg.search.fail", null, locale));
		}
		model.addAttribute("vTigerBean", result);
		return "system.exportFile.dataToVTiger.list";
	}

	@RequestMapping(value = "/dataToVTiger/export", method = { RequestMethod.GET })
	public String doExportContactSuggesion(@ModelAttribute(value = "bean") MgmExportVtigerBean vTigerBean,
			@ModelAttribute(value = "vTigerBean") ExportDataToVTigerBean exportVTigerBean, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse response, RedirectAttributes redirectAttributes) throws Exception {

		try {

			if (exportFileDataToVTigerService.isHasExportingProcess()) {
				throw new Exception("Another process is already running");
			}

			// get NoID file
			SimpleDateFormat dateExport = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat datefileName = new SimpleDateFormat("yyyyMMdd");
			Date today = Calendar.getInstance().getTime();
			String reportDate = dateExport.format(today);
			String dateFileName = datefileName.format(today);
			Integer nofile = exportFileDataToVTigerService.getNoFileVTiger(reportDate);
			Integer noFileExport = (nofile == null) ? 1 : nofile + 1;
			String type = "V-Tiger";

			String filename = dateFileName + "_MGM_Mobile_" + type + "_" + String.format("%03d", noFileExport);
			String csvFileName = filename + ".csv";
			
			String path = systemConfig.getConfig(SystemConfig.VTIGER_PATH);
			String pathOther = systemConfig.getConfig(SystemConfig.VTIGER_PATH_OTHER);
			
			File fileTemp = new File(path + csvFileName);

			// add new row
			MgmExportFileDataToVTiger exportVTigerEntity = new MgmExportFileDataToVTiger();
			exportVTigerEntity.setFileName(filename);
			exportVTigerEntity.setStatusProcess(1);
			exportVTigerEntity.setNoInDate(noFileExport);
			exportVTigerEntity.setValueType("1");
			exportVTigerEntity.setExportFileDate(new Date());
			exportVTigerEntity.setCreateDate(new Date());
			exportVTigerEntity.setCreatedBy(userProfile.getAccount().getUsername());
			exportFileDataToVTigerService.save(exportVTigerEntity, true);

			// Check path
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTemp), "UTF-8"));
			out.write("\ufeff");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
			String[] header = { "lastName", "idCardNumber", "primaryPhone", "city", "address", "productCode", "email", "sourceInfo", "campaign" };
			csvWriter.writeHeader(header);

			MgmExportVtigerBean mgmExportVtigerBean = exportFileDataToVTigerService.export(vTigerBean);
			for (MgmSuggestedContacts mgmSuggetTrans : mgmExportVtigerBean.getListResult()) {

				DataExportMGMVtiger data = new DataExportMGMVtiger();
				data.setLastName(mgmSuggetTrans.getContactName());
				data.setIdCardNumber(mgmSuggetTrans.getNationalId());
				data.setPrimaryPhone(mgmSuggetTrans.getContactPhone());
				data.setAddress("Độ tuổi : " + StringUtils.defaultString(mgmSuggetTrans.getAgeRange()));
				data.setCampaign("MGM");
				data.setSourceInfo("Mobile Application");
				csvWriter.write(data, header);

			}
			csvWriter.close();
			
			// get size file by B, KB, MB, and round 100
			String sizeDisplay = Utils.readableFileSize(fileTemp.length());
			
			// Copy vtiger to other
			try {
				String urlDest = "smb:" + pathOther + fileTemp.getName();
				String domainLDAP = systemConfig.getConfig(SystemConfig.CONFIG_LDAP_DOMAIN);
				String userLDAP = systemConfig.getConfig(SystemConfig.USER_LDAP);
				String passLDAP = systemConfig.getConfig(SystemConfig.PASSWORD_LDAP);
				NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(domainLDAP, userLDAP, passLDAP);
	
				SmbFile dest = new SmbFile(urlDest, auth);
				SmbFileOutputStream sfos = new SmbFileOutputStream(dest);
				sfos.write(IOUtils.toByteArray(new FileInputStream(fileTemp)));
				sfos.close();
			} catch (Exception e) {
				logger.debug("##Exception##", e);
				exportVTigerBean.addMessage(Message.ERROR, msgSrc.getMessage("systemconfig.field.vtiger.notice.copyfile", null, locale));
			} 
			
			// save size and set status by completed
			exportVTigerEntity.setSizeFile(sizeDisplay);
			exportVTigerEntity.setStatusProcess(2);
			exportVTigerEntity.setIs_exporting(false);
			exportFileDataToVTigerService.updateStatusMobile(mgmExportVtigerBean);
			exportFileDataToVTigerService.save(exportVTigerEntity, true);

			if (!mgmExportVtigerBean.getListResult().isEmpty()) {
				exportVTigerBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.export.success", null, locale));
			} else {
				exportVTigerBean.addMessage(Message.WARNING, msgSrc.getMessage("msg.export.nodata", null, locale));
			}
			redirectAttributes.addFlashAttribute("bean", exportVTigerBean);
			return "redirect:/system/exportFile/dataToVTiger/list/";

		} catch (Exception e) {
			logger.debug("##Exception##", e);
			model.addAttribute("bean", exportVTigerBean);
			listExportFileVTiger(exportVTigerBean, model, locale, request, response);
			exportVTigerBean.addMessage(Message.ERROR, e.getMessage());
			return "system.exportFile.dataToVTiger.list";
		}

	}

	@RequestMapping(value = "/dataToVTiger/download", method = RequestMethod.GET)
	public void doDownloadContactSuggesion(@RequestParam(required = true) String fileName, HttpServletRequest request, HttpServletResponse response) {
		if (StringUtils.isEmpty(fileName)) {
			return;
		}
		try {
			fileName = fileName + ".csv";
			String domain = "";
			String path = "";
			File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.VTIGER_PATH) + fileName);
			if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
				path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
			} else {
				path = systemConfig.getConfig(SystemConfig.VTIGER_PATH) + fileName;
			}
			File fileTemp = new File(domain + path);

			FileInputStream fileInputStream = new FileInputStream(fileTemp);
			byte[] arr = new byte[1024];
			int numRead = -1;

			response.addHeader("Content-Length", Long.toString(fileTemp.length()));
			if (StringUtils.isNotEmpty(request.getParameter("isDownload")) && request.getParameter("isDownload").equalsIgnoreCase("download")) {
				response.setContentType("application/octet-stream ; charset=UTF-8");
			} else {
				// response.setContentType(getContentType(fileName));
			}
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileName);
			response.setHeader(headerKey, headerValue);
			while ((numRead = fileInputStream.read(arr)) != -1) {
				response.getOutputStream().write(arr, 0, numRead);
			}
			fileInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/dataToVTiger/delete", method = RequestMethod.GET)
	public String doDeleteFileExportContactSuggesion(@ModelAttribute(value = "bean") ExportDataToVTigerBean dataToVtigerBean, Model model,
			Locale locale, RedirectAttributes redirectAttributes) throws Exception {
		if (dataToVtigerBean.getEntity().getVtigerId() != null) {
			try {
				if (dataToVtigerBean.getEntity().getVtigerId() != null) {
					exportFileDataToVTigerService.deleteVTiger(dataToVtigerBean.getEntity().getVtigerId());

					String fileName = dataToVtigerBean.getEntity().getFileName() + ".csv";
					String domain = "";
					String path = "";
					File fileTempCheck = new File(domain + systemConfig.getConfig(SystemConfig.VTIGER_PATH) + fileName);
					if (!fileTempCheck.exists() && fileName.startsWith("@@@")) {
						path = systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName;
					} else {
						path = systemConfig.getConfig(SystemConfig.VTIGER_PATH) + fileName;
					}
					File fileTemp = new File(domain + path);
					fileTemp.delete();
					dataToVtigerBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
				dataToVtigerBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.delete.fail", null, locale));
			}
		}
		redirectAttributes.addFlashAttribute("bean", dataToVtigerBean);
		return "redirect:/system/exportFile/dataToVTiger/list/";
	}

}
