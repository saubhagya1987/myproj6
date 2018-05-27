package vn.com.unit.fe_credit.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.RecordsDao;
import vn.com.unit.fe_credit.entity.collection.Record;
import vn.com.unit.fe_credit.exception.CronJobException;

@Transactional
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class RecordJob extends QuartzJobBean implements ScheduleTask {
	
	Logger logger = LoggerFactory.getLogger("RecordJob");
	
	@Autowired
	RecordsDao recordsDao;
	
	SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat dateFormatFile = new SimpleDateFormat("ddMMyyyy");
	
	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		importCollectionRecords();
	}
	private void importCollectionRecords() {
		System.out.println("GOING TO CREATE RECORD REPORTS..........................");
		
	try {
		
		
        String fcPath = getProperties().getProperty("report.directory.fc");
        String sfPath = getProperties().getProperty("report.directory.sf");
        String fcfile = getProperties().getProperty("record.final.report.fc");
    	String sffile = getProperties().getProperty("record.final.report.sf");
    	
    	String templatePath = getProperties().getProperty("template.directory")+getProperties().getProperty("record.template");
    	String fcGeneratedPath = fcPath.replace("yyyymmdd", localDateFormat.format(new Date()))+fcfile.replace("ddmmyyyy", dateFormatFile.format(new Date()));
    	String sfGeneratedPath = sfPath.replace("yyyymmdd", localDateFormat.format(new Date()))+sffile.replace("ddmmyyyy", dateFormatFile.format(new Date()));
        logger.info("Record Path ::: "+fcPath);
        File files = new File(fcPath.replace("yyyymmdd", localDateFormat.format(new Date())));
        File filessf = new File(sfPath.replace("yyyymmdd", localDateFormat.format(new Date())));
        
        String filePath = "";
        String filePathsf = "";
        
        File myFile = new File(fcGeneratedPath);
		filePath = createFilePath(myFile, templatePath,fcGeneratedPath,files,filePath);
		List<Record> fcRecords = recordsDao.findAllByStatusAndFC();
		logger.info("Number of Records FC  : " +fcRecords.isEmpty());
		if(fcRecords != null && !fcRecords.isEmpty())
			createExcel(myFile, filePath, fcGeneratedPath, fcRecords);
        
        File myFilesf = new File(sfGeneratedPath);        
        filePathsf = createFilePath(myFilesf, templatePath,sfGeneratedPath,filessf,filePathsf);
        List<Record> sfRecords = recordsDao.findAllByStatusAndSF();
        logger.info("Number of Records SF  : " +sfRecords.isEmpty());
        if(fcRecords != null && !fcRecords.isEmpty())
        	createExcel(myFilesf, filePathsf, sfGeneratedPath, sfRecords);
		System.out.println("Writing on Record XLSX file Finished ...");		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		logger.info("Exception Occured ");
		e.printStackTrace();
	}

	}
	private String createFilePath(File myFile, String templatePath, String generatedPath, File files, String filePath) throws CronJobException {
		// TODO Auto-generated method stub
		System.out.println("Creating file Path...............");
		try{
			if (!myFile.exists()) {
	            if (files.mkdirs()) {
	            	filePath = templatePath;
	            } else { 
	            	filePath = templatePath;
	            }
	        }else{
	        	filePath = generatedPath;        	
	        }
		}catch(Exception e){
			logger.info("Exception Occured : " + "Folder Cannot be Created due to permission");
			e.printStackTrace();
		}		
		return filePath;
	}

	private void createExcel(File myFile, String filePath, String generatedPath, List<Record> fcRecords) {
		// TODO Auto-generated method stub
		System.out.println("Reading Records..........");
		try {
			myFile = new File(filePath);
			FileInputStream fis = new FileInputStream(myFile);
		
			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
			XSSFSheet mySheet = myWorkBook.getSheetAt(0);	

			int rownum = mySheet.getLastRowNum();
			if(fcRecords != null && !fcRecords.isEmpty()){
				System.out.println("Records Size : "+fcRecords.size());
				String ids="";
				System.out.println("Creating Excel sheet.................");
				for (Record record : fcRecords) {
					rownum++;
					Row row = createRecordRow(mySheet.createRow(rownum),record);
					if(record.getRecordId() != null)
						ids=ids+record.getRecordId() + ",";
				}
				if(ids!=null){
					recordsDao.updateStatus("record", ids.substring(0, ids.length()-1));
				}
			}
			System.out.println("Going to write Records in output file.....");
			FileOutputStream os = new FileOutputStream(generatedPath);
			myWorkBook.write(os);
			myWorkBook.close();
			System.out.println("--------------------DONE----------------------");
			logger.debug("debug!!!!!!!!!!!!!!!!!!!!");
			logger.trace("trace@@@@@@@@@@@@@@@@@@@@");
			logger.info("info######################");
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
			logger.debug("debug!!!!!!!!!!!!!!!!!!!!");
			logger.trace("trace@@@@@@@@@@@@@@@@@@@@");
			logger.info("info######################");
		}
	}
	
	private Row createRecordRow(XSSFRow row, Record record) {
		// TODO Auto-generated method stub
		try{
			SimpleDateFormat excelDateFormat = new SimpleDateFormat("dd-MM-yy");
			row.createCell(0).setCellValue(record.getFinancierId() != null ? record.getFinancierId() : "");
			row.createCell(1).setCellValue(record.getContractId() != null ? record.getContractId() : "");
			row.createCell(2).setCellValue(record.getCustomerId() != null ? record.getCustomerId() : "");
			row.createCell(3).setCellValue(record.getSubmitBy() != null ? record.getSubmitBy() : "");
			if(record.getActionDate() == null)
				row.createCell(4);
			else
				row.createCell(4).setCellValue(excelDateFormat.format(record.getActionDate()));
			if(record.getActionTime() == null){
				row.createCell(5);
			}else{
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		        String time = localDateFormat.format(record.getActionTime());
		        row.createCell(5).setCellValue(time);
			}
				
			row.createCell(6).setCellValue(record.getActionCode() != null ? record.getActionCode() : "");
			row.createCell(7).setCellValue(record.getContactMode() != null ? record.getContactMode() : "");
			row.createCell(8).setCellValue(record.getPersonContacted() != null ? record.getPersonContacted() : "");
			row.createCell(9).setCellValue(record.getPlaceContacted() != null ? record.getPlaceContacted() : "");
			row.createCell(10).setCellValue(record.getCurrency() != null ? record.getCurrency() : "");
			if(record.getActionAmount() == null)
				row.createCell(11);
			else
				row.createCell(11).setCellValue(record.getActionAmount());
			if(record.getNextActionDate() == null)
				row.createCell(12);
			else
				row.createCell(12).setCellValue(excelDateFormat.format(record.getNextActionDate()));
			if(record.getNextActionTime() == null){
				row.createCell(13);
			}else{
				SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
		        String time = localDateFormat.format(record.getNextActionTime());
				row.createCell(13).setCellValue(time);
			}
			row.createCell(14).setCellValue(record.getReminderMode() != null ? record.getReminderMode() : "");
			row.createCell(15).setCellValue(record.getContactedBy() != null ? record.getContactedBy() : "");
			row.createCell(16).setCellValue(record.getRemarks() != null ? record.getRemarks() : "");
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return row;
	}
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("## Record Job ##" + new Date());
		doTask();
	}
	
	private Properties getProperties(){
		InputStream inputStream;
		 
		Properties prop = new Properties();
		String propFileName = "configure.properties";
		try {
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);	
			if (inputStream != null) {
				prop.load(inputStream);				
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prop;
	}
	

}
