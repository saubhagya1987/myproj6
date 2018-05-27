package vn.com.unit.fe_credit.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import javax.swing.Timer;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.ExportRedeemTrasPointBean;
import vn.com.unit.fe_credit.bean.RedeemTrasPointBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.DataExportRedeemMomo;
import vn.com.unit.fe_credit.config.DataExportRedeemPayment;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.ExportFileRedeemPointDao;
import vn.com.unit.fe_credit.dao.MgmRedeemTransDao;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;
import vn.com.unit.fe_credit.entity.MgmRedeemTrans;
import vn.com.unit.fe_credit.service.ExportFileRedeemPointService;
import vn.com.unit.fe_credit.utils.Utils;

@Service("ExportFileRedeemPointService")
@Transactional(readOnly = true)
public class ExportFileRedeemPointServiceImpl implements ExportFileRedeemPointService {

	Logger logger = LoggerFactory.getLogger(ExportFileRedeemPointServiceImpl.class);
	
	@Autowired
	private ExportFileRedeemPointDao exportFileRedeemPointDao;
	
	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MgmRedeemTransDao mgmRedeemTrasDao;

	@Autowired
	private UserProfile userProfile;

	public ExportFileRedeemPointServiceImpl() {
		super();
	}

	@Override
	public List<MgmExportFileRedeemPoint> findAllex() {
		return exportFileRedeemPointDao.findAll();
	}

	@Override
	public ExportRedeemTrasPointBean search(ExportRedeemTrasPointBean redeemTrasBean) {
		Search search = new Search(MgmExportFileRedeemPoint.class);
		search.setMaxResults(redeemTrasBean.getLimit());
		search.setPage(redeemTrasBean.getPage() - 1);
		MgmExportFileRedeemPoint redeemTrans = redeemTrasBean.getEntity();
		Collection<String> myCollection = new HashSet<String>();
		myCollection.add("Momo");
		myCollection.add("Payment");
		if (redeemTrans != null) {
		} else {
			search.addFilterIn("valueType", myCollection);
		}
		search.addSortDesc("exportFileDate", true);
		SearchResult<MgmExportFileRedeemPoint> searchResult = exportFileRedeemPointDao.searchAndCount(search);
		redeemTrasBean.setListResult(searchResult.getResult());
		redeemTrasBean.setTotal(searchResult.getTotalCount());
		return redeemTrasBean;
	}

	@Override
	public RedeemTrasPointBean getDataForExportRedeemTransactionPoint(RedeemTrasPointBean bean) throws Exception {
		return mgmRedeemTrasDao.getDataForExportRedeemTransactionPoint(bean);
	}

	public RedeemTrasPointBean exportMomo(RedeemTrasPointBean bean) {
		return mgmRedeemTrasDao.searchValueExportMomo(bean);
	}

	@Override
	public List<Object[]> exportRedeem(RedeemTrasPointBean bean) {
		return mgmRedeemTrasDao.searchValueExportRedeem(bean);
	}

	@Transactional
	@Override
	public void save(MgmExportFileRedeemPoint exportRedeemPointEntity, Boolean flagExport) {
		if (flagExport) {
			if (exportRedeemPointEntity.getExportRedeemId() == null) {
				exportRedeemPointEntity.setCreateDate(new Date());
				exportRedeemPointEntity.setCreatedBy(userProfile.getAccount().getUsername());
			} else {
				exportRedeemPointEntity.setUpdateDate(new Date());
				exportRedeemPointEntity.setUpdateBy(userProfile.getAccount().getUsername());
			}
		}
		exportFileRedeemPointDao.save(exportRedeemPointEntity);

	}

//	@Transactional
//	@Override
//	public void saveExportFileRedeem(MgmExportFileRedeemPoint exportRedeemPointEntity) {
//		String userName = "admin";
//		exportRedeemPointEntity.setCreateDate(new Date());
//		exportRedeemPointEntity.setCreatedBy(userName);
//		exportFileRedeemPointDao.save(exportRedeemPointEntity);

//	}

	@Override
	public Integer getNoFile(String date) {
		return exportFileRedeemPointDao.searchMaxNoByDate(date);

	}

	@Override
	@Transactional
	public void deleteExportRedeem(Long exportRedeemId) {
		exportFileRedeemPointDao.deleteExportRedeem(exportRedeemId);

	}

	@Override
	public void updateStatusMobile(RedeemTrasPointBean redeemTrasPointBean) {
		mgmRedeemTrasDao.updateStatus(redeemTrasPointBean);

	}

	@Override
	public void updateStatusRedeem(List<Object[]> lst) {
		mgmRedeemTrasDao.updateStatusRedeem(lst);

	}
	
	@Override
	public boolean isHasExportingProcess(String valueType) throws Exception {
		Search search = new Search(MgmExportFileRedeemPoint.class);
		search.addFilterEqual("is_exporting", Boolean.TRUE);
		search.addFilterEqual("valueType", valueType);
		int count = exportFileRedeemPointDao.count(search);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
		
	}

//	@Override
//	public RedeemTrasPointBean exportFileRedeem(RedeemTrasPointBean redeembBean) throws Exception {
//		RedeemTrasPointBean resultExport = new RedeemTrasPointBean();
//		
////			Thread.sleep(1);
//			// Kiểm tra loại export
//			String tranxType = redeembBean.getEntity().getTranxType();
//			String type = null;
//			if (StringUtils.equals(tranxType, "1")) {
//				type = "Momo";
//			} else if (StringUtils.equals(tranxType, "2")) {
//				type = "Payment";
//			}
//			
//			if (isHasExportingProcess(type)) {
//				throw new Exception("Another process is already running");
//			}
//			
//			// get NoID file
//			SimpleDateFormat dateExport = new SimpleDateFormat("MM-dd-yyyy");
//			SimpleDateFormat datefileName = new SimpleDateFormat("yyyyMMdd");
//			Date today = new Date();
//			String reportDate = dateExport.format(today);
//			String dateFileName = datefileName.format(today);
//			Integer nofile = getNoFile(reportDate);
//			Integer noFileExport = (nofile == 0) ? 1 : nofile + 1;
//
//			// Kiểm tra xem có tiến trình nào đang chạy không
//
//			String filename = dateFileName + "_MGM_Mobile_" + type + "_" + String.format("%03d", noFileExport);
//			String csvFileName = filename + ".csv";
//			String path = systemConfig.getConfig(SystemConfig.REDEEM_PATH);
//			File fileTemp = new File(path + csvFileName);
//
//			// add new row
//			MgmExportFileRedeemPoint exportRedeemEntity = new MgmExportFileRedeemPoint();
//			exportRedeemEntity.setFileName(filename);
//			exportRedeemEntity.setStatusProcess(1);
//			exportRedeemEntity.setNoInDate(noFileExport);
//			exportRedeemEntity.setValueType(type);
//			exportRedeemEntity.setExportFileDate(new Date());
//			exportRedeemEntity.setCreateDate(new Date());
//			save(exportRedeemEntity, redeembBean.getFlagExport());
////			try {
////				exportRedeemEntity.setCreatedBy(userProfile.getAccount().getUsername());
////				save(exportRedeemEntity);
////			} catch (Exception e) {
////				logger.debug("Export file Mgm by account default: Admin");
////				saveExportFileRedeem(exportRedeemEntity); // save default follow account Admin
////			}
//
//			resultExport = getDataForExportRedeemTransactionPoint(redeembBean);
//
//			Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileTemp), "UTF-8"));
//			out.write("\ufeff");
//			ICsvBeanWriter csvWriter = new CsvBeanWriter(out, CsvPreference.STANDARD_PREFERENCE);
//
//			if (StringUtils.equals(tranxType, "1")) {
//				String[] header = { "fullName", "contractNo", "idCardNo", "moneyWasConvert" };
//				csvWriter.writeHeader(header);
//				for (MgmRedeemTrans item : resultExport.getListResult()) {
//
//					String fullName = String.valueOf(item.getLastName() + " " + item.getMiddleName() + " " + item.getFirstName());
//					String contractNo = item.getValueDescription();
//					String idCardNo = String.valueOf(item.getIDCardNumber());
//					BigDecimal moneyWasConvert = item.getExchangeValue();
//
//					DataExportRedeemMomo data = new DataExportRedeemMomo();
//					data.setFullName(fullName);
//					data.setContractNo(contractNo);
//					data.setIdCardNo(idCardNo);
//					data.setMoneyWasConvert(moneyWasConvert);
//					csvWriter.write(data, header);
//
//				}
//
//			} else if (StringUtils.equals(tranxType, "2")) {
//				SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
//				String datetimeExport = sdf1.format(today);
//				
//				String[] header1 = { "~", "Bill_"+ datetimeExport +".csv", "","","","","",""};
//				csvWriter.writeHeader(header1);
//				
//				String[] header = { "refNum", "contractNumber", "customerName", "description", "paidDate", "currency", "paidAmount",
//						"customerBankAccount" };
//				csvWriter.writeHeader(header);
//				for (MgmRedeemTrans item : resultExport.getListResult()) {
//
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//					DataExportRedeemPayment data = new DataExportRedeemPayment();
//					data.setRefNum(item.getRefNum());
//					data.setContractNumber(item.getValueDescription());
//					String fullName = String.valueOf(item.getLastName() + " " + item.getMiddleName() + " " + item.getFirstName());
//					data.setCustomerName(fullName);
//					data.setDescription("");
//					data.setPaidDate(sdf.format(item.getSubmissionDate()));
//					data.setCurrency("VND");
//					data.setPaidAmount(item.getExchangeValue());
//					data.setCustomerBankAccount("");
//					csvWriter.write(data, header);
//
//				}
//			}
//
//			csvWriter.close();
//
//			long fileSize = fileTemp.length();// in Bytes
//			String sizeDisplay = Utils.readableFileSize(fileSize);
//
//			// save size and set status by completed
////			MgmExportFileRedeemPoint exportRedeemEntity2 = findByFileName(filename);
//			exportRedeemEntity.setSizeFile(sizeDisplay);
//			exportRedeemEntity.setStatusProcess(2);
//			exportRedeemEntity.setIs_exporting(false);
//			updateStatusMobile(resultExport);
//			save(exportRedeemEntity, redeembBean.getFlagExport());
////			try {
////				exportRedeemEntity.setCreatedBy(userProfile.getAccount().getUsername());
////				save(exportRedeemEntity);
////			} catch (Exception e) {
////				logger.debug("Export file Mgm by account default: Admin");
////				saveExportFileRedeem(exportRedeemEntity); // save default follow account Admin
////			}
//		
//		return resultExport;
//	}
	
	public MgmExportFileRedeemPoint findByFileName(String fileName) {
		return exportFileRedeemPointDao.find(fileName);
	}

}
