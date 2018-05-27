package vn.com.unit.fe_credit.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.config.DataExportRedeemMomo;
import vn.com.unit.fe_credit.config.DataExportRedeemPayment;
import vn.com.unit.fe_credit.config.ExportMGMConfig;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.ExportFileDataToVTigerService;
import vn.com.unit.fe_credit.service.ExportFileMgmService;
import vn.com.unit.fe_credit.service.ExportFileRedeemPointService;
import vn.com.unit.fe_credit.service.MasterdataDetailService;
import vn.com.unit.fe_credit.service.MasterdataService;
import vn.com.unit.fe_credit.utils.Utils;

@Service("ExportFileMgmService")
public class ExportFileMgmServiceImpl implements ExportFileMgmService {

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
	ExportFileRedeemPointService exportFileRedeemPointService;

	@Override
	public synchronized RedeemTrasPointBean exportFileRedeem(RedeemTrasPointBean redeembBean) throws Exception {

		// Kiểm tra loại export
		String tranxType = redeembBean.getEntity().getTranxType();
		String type = null;
		if (StringUtils.equals(tranxType, "1")) {
			type = "Momo";
		} else if (StringUtils.equals(tranxType, "2")) {
			type = "Payment";
		}

		if (exportFileRedeemPointService.isHasExportingProcess(type)) {
			throw new Exception("Another process is already running");
		}

		// get NoID file
		SimpleDateFormat dateExport = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat datefileName = new SimpleDateFormat("yyyyMMdd");
		Date today = new Date();
		String reportDate = dateExport.format(today);
		String dateFileName = datefileName.format(today);
		Integer noFileExport = exportFileRedeemPointService.getNoFile(reportDate) + 1;

		// Kiểm tra xem có tiến trình nào đang chạy không

		String filename = dateFileName + "_MGM_Mobile_" + type + "_" + String.format("%03d", noFileExport);
		String csvFileName = filename + ".csv";

		MgmExportFileRedeemPoint exportRedeemEntity = new MgmExportFileRedeemPoint();
		RedeemTrasPointBean resultExport = null;
		
		try {
			// add new row
			exportRedeemEntity.setFileName(filename);
			exportRedeemEntity.setStatusProcess(1);
			exportRedeemEntity.setNoInDate(noFileExport);
			exportRedeemEntity.setValueType(type);
			exportRedeemEntity.setExportFileDate(new Date());
			exportRedeemEntity.setCreateDate(new Date());
			exportFileRedeemPointService.save(exportRedeemEntity, redeembBean.getFlagExport());
	
			String path = systemConfig.getConfig(SystemConfig.REDEEM_PATH);
			File fileTemp = new File(path + csvFileName);
	
			resultExport = exportFileRedeemPointService.getDataForExportRedeemTransactionPoint(redeembBean);
	
			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTemp), "UTF-8"));
			out.write("\ufeff");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
	
			if (StringUtils.equals(tranxType, "1")) {
				String[] header = { "fullName", "contractNo", "idCardNo", "moneyWasConvert" };
				csvWriter.writeHeader(header);
	
				if (CollectionUtils.isNotEmpty(resultExport.getListResult())) {
					for (MgmRedeemTrans item : resultExport.getListResult()) {
	
						String fullName = String.valueOf(item.getLastName() + " " + item.getMiddleName() + " " + item.getFirstName());
						String contractNo = item.getValueDescription();
						String idCardNo = String.valueOf(item.getIDCardNumber());
						BigDecimal moneyWasConvert = item.getExchangeValue();
	
						DataExportRedeemMomo data = new DataExportRedeemMomo();
						data.setFullName(fullName);
						data.setContractNo(contractNo);
						data.setIdCardNo(idCardNo);
						data.setMoneyWasConvert(moneyWasConvert);
						csvWriter.write(data, header);
					}
				}
	
			} else if (StringUtils.equals(tranxType, "2")) {
				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
				String datetimeExport = sdf1.format(today);
	
				String[] header1 = { "~", "Bill_" + datetimeExport + ".csv", "", "", "", "", "", "" };
				csvWriter.writeHeader(header1);
	
				String[] header = { "refNum", "contractNumber", "customerName", "description", "paidDate", "currency", "paidAmount",
						"customerBankAccount" };
				csvWriter.writeHeader(header);
	
				if (CollectionUtils.isNotEmpty(resultExport.getListResult())) {
					for (MgmRedeemTrans item : resultExport.getListResult()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
						DataExportRedeemPayment data = new DataExportRedeemPayment();
						data.setRefNum(item.getRefNum());
						data.setContractNumber(item.getValueDescription());
						String fullName = String.valueOf(item.getLastName() + " " + item.getMiddleName() + " " + item.getFirstName());
						data.setCustomerName(fullName);
						data.setDescription("");
						data.setPaidDate(sdf.format(item.getSubmissionDate()));
						data.setCurrency("VND");
						data.setPaidAmount(item.getExchangeValue());
						data.setCustomerBankAccount("");
						csvWriter.write(data, header);
					}
				}
			}
	
			csvWriter.close();
			long fileSize = fileTemp.length();// in Bytes
			String sizeDisplay = Utils.readableFileSize(fileSize);
			exportRedeemEntity.setSizeFile(sizeDisplay);
			exportFileRedeemPointService.updateStatusMobile(resultExport);
			
		} catch (Exception e) {
			exportRedeemEntity.setReportException(e.getMessage());
			exportFileRedeemPointService.save(exportRedeemEntity, redeembBean.getFlagExport());
		}
		
		exportRedeemEntity.setStatusProcess(2);
		exportRedeemEntity.setIs_exporting(false);
		exportFileRedeemPointService.save(exportRedeemEntity, redeembBean.getFlagExport());

		return resultExport;
	}

}
