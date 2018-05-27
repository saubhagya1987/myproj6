package vn.com.unit.fe_credit.config;

import static vn.com.unit.fe_credit.config.SystemConfig.AUTO_EXPORT_MGM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import vn.com.unit.fe_credit.bean.MgmExportVtigerBean;
import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;
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

public class ExportMGMConfig extends SpringBeanAutowiringSupport {

	Logger logger = LoggerFactory.getLogger(ExportMGMConfig.class);

	@Autowired
	ExportFileRedeemPointService exportFileService;

	@Autowired
	ExportFileDataToVTigerService exportFileDataToVTigerService;

	@Autowired
	MasterdataDetailService masterdataDetailService;

	@Autowired
	MasterdataService masterdatalService;

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
	ExportFileRedeemPointService exportFileRedeemPointService;

	@Autowired
	ExportFileMgmService exportFileMgmService;
	
	public void export() {
		
		String autoExportMgm = systemConfig.getConfig(AUTO_EXPORT_MGM);
		if (autoExportMgm.equals("on")) {
			try {
				// Momo
				RedeemTrasPointBean redeembBean1 = new RedeemTrasPointBean();
				MgmRedeemTrans mgmRedeem1 = new MgmRedeemTrans();
				mgmRedeem1.setTranxType("1");
				redeembBean1.setEntity(mgmRedeem1);
				redeembBean1.setFlagExport(false);
				exportFileMgmService.exportFileRedeem(redeembBean1);
			} catch(Exception e) {
				logger.debug("##Exception##", e);
			}
			
			try {
				// Payment
				RedeemTrasPointBean redeembBean2 = new RedeemTrasPointBean();
				MgmRedeemTrans mgmRedeem2 = new MgmRedeemTrans();
				mgmRedeem2.setTranxType("2");
				redeembBean2.setEntity(mgmRedeem2);
				redeembBean2.setFlagExport(false);
				exportFileMgmService.exportFileRedeem(redeembBean2);
			} catch(Exception e) {
				logger.debug("##Exception##", e);
			}
			
			try {
				// vtiger
				exportFileVTiger();
			} catch (Exception e) {
				logger.debug("##Exception##", e);
			}
				
		}
	}
	
	private void exportFileVTiger() {
		try {

			if (exportFileDataToVTigerService.isHasExportingProcess()) {
				throw new Exception("Another process is already running");
			}

			MgmExportVtigerBean vTigerBean = new MgmExportVtigerBean();

			// get NoID file
			SimpleDateFormat dateExport = new SimpleDateFormat("MM-dd-yyyy");
			SimpleDateFormat datefileName = new SimpleDateFormat("yyyyMMdd");
			Date today = Calendar.getInstance().getTime();
			String reportDate = dateExport.format(today);
			String dateFileName = datefileName.format(today);
			Integer nofile = exportFileDataToVTigerService.getNoFileVTiger(reportDate);
			Integer noFileExport = (nofile == 0) ? 1 : nofile + 1;
			String type = "V-Tiger";

			String filename = dateFileName + "_MGM_Mobile_" + type + "_" + String.format("%03d", noFileExport);
			// export file redeem
			String csvFileName = filename + ".csv";
			
			MgmExportFileDataToVTiger exportVTigerEntity = null;
			
			try {
				String path = systemConfig.getConfig(SystemConfig.VTIGER_PATH);
				File fileTemp = new File(path + csvFileName);
	
				// add new row
				exportVTigerEntity = new MgmExportFileDataToVTiger();
				exportVTigerEntity.setFileName(filename);
				exportVTigerEntity.setStatusProcess(1);
				exportVTigerEntity.setNoInDate(noFileExport);
				exportVTigerEntity.setValueType("1");
				exportVTigerEntity.setExportFileDate(new Date());
				exportFileDataToVTigerService.save(exportVTigerEntity, false);
	
				Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTemp), "UTF-8"));
				out.write("\ufeff");
				ICsvBeanWriter csvWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
				String[] header = { "lastName", "idCardNumber", "primaryPhone", "city", "address", "productCode", "email", "sourceInfo", "campaign" };
				csvWriter.writeHeader(header);
	
				MgmExportVtigerBean resultExportVTiger = exportFileDataToVTigerService.export(vTigerBean);
				for (MgmSuggestedContacts mgmSuggetTrans : resultExportVTiger.getListResult()) {
	
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
				// save size and set status by completed
				exportVTigerEntity.setSizeFile(sizeDisplay);
				exportFileDataToVTigerService.updateStatusMobile(resultExportVTiger);
			} catch (Exception e) {
				exportVTigerEntity.setReportException(e.getMessage());
				exportFileDataToVTigerService.save(exportVTigerEntity, false);
			}
			
			exportVTigerEntity.setStatusProcess(2);
			exportVTigerEntity.setIs_exporting(false);
			exportFileDataToVTigerService.save(exportVTigerEntity, false);

		} catch (Exception e) {
			logger.debug("##Exception##", e);
		}
	}
}
