package vn.com.unit.fe_credit.scheduler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import vn.com.unit.fe_credit.dao.CheckInDao;
import vn.com.unit.fe_credit.entity.collection.CheckIn;

@Transactional
@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class CheckInJob extends QuartzJobBean implements ScheduleTask {
	
	Logger logger = LoggerFactory.getLogger("CheckInJob");
	
	@Autowired
	CheckInDao checkInDao;

	@Override
	public void doTask() {
		// TODO Auto-generated method stub
		importCollectionCheckIns();
	}
	private void importCollectionCheckIns() {
		System.out.println("GOING TO CREATE CHECKIN REPORTS..........................");
	
		
		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat dateFormatFile = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
		
		String path = getProperties().getProperty("report.directory.rg");
		String fcfile = getProperties().getProperty("record.final.report.rg");
				
        File files = new File(path.replace("yyyymmdd", localDateFormat.format(new Date())));
        File myFile = new File(path.replace("yyyymmdd", localDateFormat.format(new Date()))+fcfile.replace("ddmmyyyy", dateFormatFile.format(new Date())));
        String filePath = "";
        try{
	        if (!myFile.exists()) {
	            if (files.mkdirs()) {
	            	filePath = getProperties().getProperty("template.directory")+getProperties().getProperty("checkin.template");
	            } else {
	            	filePath = getProperties().getProperty("template.directory")+getProperties().getProperty("checkin.template");            	
	            }
	        }else{        	
	        	filePath = path.replace("yyyymmdd", localDateFormat.format(new Date()))+fcfile.replace("ddmmyyyy", dateFormatFile.format(new Date()));        	
	        }
        } catch(Exception e){
        	e.printStackTrace();
        	logger.info("Exception Occured : " + "Folder Cannot be Created due to permission");
        }
        
        try {
		myFile = new File(filePath);
		FileInputStream fis = new FileInputStream(myFile);
	
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
	
		List<CheckIn> records = checkInDao.findAll();
		//List<Object[]> checkInNos = checkInDao.findByAppIDAndCreationDate();
//		Object fcCheckInDaily = checkInDao.findFcCheckInDaily();
//		List<Object[]> appCheckInDaily = checkInDao.findAppCheckInDaily();
		Object fcCheckInMonthly = checkInDao.findFcCheckInMonthly();
		List<Object[]> appCheckInMonthly = checkInDao.findAppCheckInMonthly();
		int rownum = mySheet.getLastRowNum();
		
		//List<CheckIn> checkins = new ArrayList<CheckIn>();
		String NoAppIdCheckin = "";
		String NoAppIdcheckInDaily = "";
		String NoFCCheckin = "";
		String FcNocheckinDaily = "";
		String ids="";
		Map<String, List<Object[]>> checkInDailyByDates  = new HashMap<String, List<Object[]>>();
		Map<String, Object> fcCheckInDailyByDates  = new HashMap<String, Object>();
		if(records != null && !records.isEmpty()){	
			System.out.println("Checkin Size  ::  "+records.size());
			for (CheckIn record : records) {

				String checkInDate = formater.format(record.getCheckinTime());
				List<Object[]> appCheckInDaily = checkInDailyByDates.get(checkInDate);
				if(appCheckInDaily==null){
					System.out.println("Check In date  :::  "+checkInDate);
					 appCheckInDaily = checkInDao.findAppCheckInDaily(checkInDate);
					checkInDailyByDates.put(checkInDate, appCheckInDaily);
				}
				String number = "";
				Object fcCheckInDaily = fcCheckInDailyByDates.get(checkInDate);
				if(fcCheckInDaily==null){
					 fcCheckInDaily = checkInDao.findFcCheckInDaily(checkInDate);
					 fcCheckInDailyByDates.put(checkInDate, fcCheckInDaily);
				}
				
				for(Object[] count : appCheckInMonthly){
					if(record.getAppId() != null && count[0] != null){
						if(record.getAppId().equals((String)count[0])){
							NoAppIdCheckin = ((Long)count[1]).toString();
						}
					}
				}
				for(Object[] count : appCheckInDaily){
					if(record.getAppId() != null && count[0] != null){
						if(record.getAppId().equals((String)count[0])){
							NoAppIdcheckInDaily = ((Long)count[1]).toString();
						}
					}					
				}
				if(fcCheckInMonthly != null)
					FcNocheckinDaily = fcCheckInMonthly.toString();
				//TODO:
				if(fcCheckInDaily != null)
				NoFCCheckin = fcCheckInDaily.toString();
				rownum++;
				Row row = createRecordRow(mySheet.createRow(rownum),record,NoAppIdCheckin,NoAppIdcheckInDaily,NoFCCheckin,FcNocheckinDaily);
				if(record.getId() != null)
					ids=ids+record.getId() + ",";
				
			}
			System.out.println(ids);
			if(ids!=null){
				checkInDao.updateStatus("check_in", ids.substring(0, ids.length()-1));
			}
			
		}
		System.out.println("Going to write Checkin Records in output file.....");
		FileOutputStream os = new FileOutputStream(path.replace("yyyymmdd", localDateFormat.format(new Date()))+fcfile.replace("ddmmyyyy", dateFormatFile.format(new Date())));
		myWorkBook.write(os); 
		System.out.println("Writing on CheckIn XLSX file Finished ...");		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		logger.trace("Exception occure in CheckIn  :  "+e);
	}

	}

	private Row createRecordRow(XSSFRow row, CheckIn record, String noAppIdCheckin, String noAppIdcheckInDaily, String noFCCheckin, String fcNocheckinDaily) {
		// TODO Auto-generated method stub
		try{
		
			if(record.getCheckinTime() == null){
				row.createCell(0);
			} else {
				SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String time = localDateFormat.format(record.getCheckinTime());
				row.createCell(0).setCellValue(time);
			}
			row.createCell(1).setCellValue(record.getCheckinDate() != null ? record.getCheckinDate() : "");
			row.createCell(2).setCellValue(record.getMonth() != null ? record.getMonth() : "");
			row.createCell(3).setCellValue(record.getAppId() != null ? record.getAppId() : "");
			row.createCell(4).setCellValue(record.getAddress() != null ? record.getAddress() : "");
			row.createCell(5).setCellValue(record.getCheckinAddress() != null ? record.getCheckinAddress() : "");
			if(record.getDistance().equals("null") || record.getDistance().equals("undefined"))
				row.createCell(6).setCellValue("");
			else
				row.createCell(6).setCellValue(record.getDistance() != null ? record.getDistance() : "");
			row.createCell(7).setCellValue(record.getUnitCode() != null ? record.getUnitCode() : "");
			row.createCell(8).setCellValue(record.getUnitCodeDesc() != null ? record.getUnitCodeDesc() : "");
			row.createCell(9).setCellValue(noFCCheckin/*record.getNoFCCheckin() != null ? record.getNoFCCheckin() : ""*/);		
			row.createCell(10).setCellValue(noAppIdcheckInDaily/*record.getNoAppIdcheckInDaily() != null ? record.getNoAppIdcheckInDaily() : ""*/);	
			row.createCell(11).setCellValue(fcNocheckinDaily/*record.getFcNocheckinDaily() != null ? record.getFcNocheckinDaily() : ""*/);
			row.createCell(12).setCellValue(noAppIdCheckin/*record.getNoAppIdCheckin() != null ? record.getNoAppIdCheckin() : ""*/);
			row.createCell(13).setCellValue(record.getRegionTeam() != null ? record.getRegionTeam() : "");
			row.createCell(14).setCellValue(record.getRegion() != null ? record.getRegion() : "");
		}catch(Exception e){
			e.printStackTrace();
		}
		return row;
	}
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		System.out.println("## CheckIn Job ##" + new Date());
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
