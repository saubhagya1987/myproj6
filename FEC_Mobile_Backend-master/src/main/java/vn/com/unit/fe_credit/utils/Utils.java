package vn.com.unit.fe_credit.utils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URI;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import vn.com.unit.fe_credit.config.SystemConfig;

public class Utils {
	
	final static Logger logger = LoggerFactory.getLogger(Utils.class);

	public static void writeLog(String msg) {
		logger.debug(msg);
	}
	
	public static Date addDate(Date date, int field, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(field, amount);
		return c.getTime();
	}
	
	public static String writeValueAsString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	private static Properties properties;
	
	public static void copyProperties(Object frombean, Object tobean, List<String> include) {
		final ArrayList<String> excludes = new ArrayList<String>();
		final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(frombean.getClass());
		for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propName = propertyDescriptor.getName();
			if (!include.contains(propName)) {
				excludes.add(propName);
			}
		}
		BeanUtils.copyProperties(frombean, tobean, excludes.toArray(new String[excludes.size()]));
	}
	

	public static OfficeManager officeManager;
	
	public static void startOfficeManager() {
		if (officeManager == null) {
			try {
				String absoluteFilePath = "";
				String yourOS = System.getProperty("os.name").toLowerCase();
				if (yourOS.indexOf("win") >= 0) {
					// if windows
					absoluteFilePath = getProperties().getProperty("openoffice.home.window");
				} else if (yourOS.indexOf("nix") >= 0 || yourOS.indexOf("nux") >= 0 || yourOS.indexOf("mac") >= 0) {
					// if unix or mac
					absoluteFilePath = getProperties().getProperty("openoffice.home.linux");
				} else {
					// unknow os? 
				}
				Utils.officeManager = new DefaultOfficeManagerConfiguration()
					.setOfficeHome(absoluteFilePath)
      				.buildOfficeManager();
				Utils.officeManager.start();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ERROR: Can not start OfficeManager");
			}
		}
	}
	
	public static void stopOfficeManager() {
		if (officeManager != null) {
			officeManager.stop();
		}
	}

	public static String converterToPDF(String fileName) {
		String fileOut = "";
		try {
			fileOut = Utils.getFile(fileName) + ".pdf";
			OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
			converter.convert(new File(fileName), new File(fileOut));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileOut;
	}

	public static boolean contains(List<?> coll, Object o) {
		return coll.contains(o);
	}

	// remove dau, ky tu dac biet
	public static String convertNewFileName(String input) {
		String output = "";
		if (StringUtils.isNotEmpty(input)) {
			output = Normalizer.normalize(input, Normalizer.Form.NFD).replaceAll("đ", "d")
					.replaceAll("[^\\p{ASCII}]", "").replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
		}
		return output;
	}

	public static String moveTempToUploadFolder(String fileName, SystemConfig systemConfig) {
		if (fileName.startsWith("@@@") == true) {
			String newName = fileName.substring(fileName.lastIndexOf("@") + 1);
			File source = new File(systemConfig.getConfig(SystemConfig.TEMP_FOLDER) + fileName);
			File target = new File(systemConfig.getConfig(SystemConfig.REPOSITORY_ROOT_FOLDER) + newName);
			try {
				FileCopyUtils.copy(source, target);
				source.delete();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
			return newName;
		} else {
			return fileName;
		}
	}

	public static String getCountQuery(String query) {
		String countQuery = "select count(*) as count ";
		int lastIndex = query.length();
		if (query.indexOf("ORDER BY") > 0) {
			lastIndex = query.indexOf("ORDER BY");
		}
		countQuery += query.substring(query.indexOf("FROM"), lastIndex);
		return countQuery;
	}
	/**
	 * Get count Distinct query
	 * HungNN
	 */
	public static String getDistinctCountQuery(String query) {
		String countQuery = "select count(DISTINCT *) as count ";
		int lastIndex = query.length();
		if (query.indexOf("ORDER BY") > 0) {
			lastIndex = query.indexOf("ORDER BY");
		}
		countQuery += query.substring(query.indexOf("FROM"), lastIndex);
		return countQuery;
	}

	// QUAN : check null object (user define)
	/**
	 * @param obj (user define)
	 * @return
	 */
	public static boolean isNull(Object obj) {
		for (Field f : obj.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.get(obj) != null) {
					return false;
				}
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
				return true;
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
				return true;
			}
		}
		return true;
	}
	
	// check object thong thuong
	/**
	 * @param obj (regular object (Integer,Objec[] ....)
	 * @return
	 */
	public static boolean isNullObjectRegular(Object obj){
		for (Field f : obj.getClass().getFields()) {
			  f.setAccessible(true);
			  try {
				if (f.get(obj) != null) {
				     return false;
				  }
			} catch (IllegalArgumentException e) {
				logger.error(e.getMessage(), e);
				return true;
			} catch (IllegalAccessException e) {
				logger.error(e.getMessage(), e);
				return true;
			}
		}
		return true;
	}

	public static Calendar setTimeToZero(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	public static Calendar setTimeToMax(Calendar cal) {
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 99);
		return cal;
	}

	public static Date setTimeToZero(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date setTimeToMax(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 99);
		return cal.getTime();
	}
	
	public static Properties getProperties() {
		if (properties == null) {
			properties = new Properties();
			try {
				String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String context[] = null;
				/*if (path.contains("standalone")) {
					context = path.split("standalone");
				} else if (path.contains("bin")) {
					context = path.split("bin");
				}*/	
				FileInputStream fileInput = null;
				String your_os = System.getProperty("os.name").toLowerCase();
				if (your_os.indexOf("win") >= 0) {
					// if windows
					fileInput = new FileInputStream(new File(path + "\\configure.properties"));
				} else if (your_os.indexOf("nix") >= 0 || your_os.indexOf("nux") >= 0 || your_os.indexOf("mac") >= 0) {
					// if unix or mac
					fileInput = new FileInputStream(new File(path + "/configure.properties"));
				} else {
					// unknow os?
					fileInput = new FileInputStream(new File(path + "/configure.properties"));
				}
				properties.load(fileInput);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}else{
			properties = new Properties();
			try {
				String path = Utils.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				String context[] = null;
				/*if (path.contains("standalone")) {
					context = path.split("standalone");
				} else if (path.contains("bin")) {
					context = path.split("bin");
				}*/	
				FileInputStream fileInput = null;
				String your_os = System.getProperty("os.name").toLowerCase();
				if (your_os.indexOf("win") >= 0) {
					// if windows
					fileInput = new FileInputStream(new File(path + "\\configure.properties"));
				} else if (your_os.indexOf("nix") >= 0 || your_os.indexOf("nux") >= 0 || your_os.indexOf("mac") >= 0) {
					// if unix or mac
					fileInput = new FileInputStream(new File(path + "/configure.properties"));
				} else {
					// unknow os?
					fileInput = new FileInputStream(new File(path + "/configure.properties"));
				}
				properties.load(fileInput);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return properties;
	}
	
	
	

	public static String getFile(String fileName) {
		if (fileName == null)
			return null;
		int pos = fileName.lastIndexOf(".");
		if (pos == -1)
			return fileName;
		return fileName.substring(0, pos);
	}
	
	
	
	public static Font createFont(Workbook workbook){ 
		Font newFont=workbook.createFont();
		newFont.setFontName("Arial");
		newFont.setFontHeightInPoints((short)11);
		return newFont;
	}
	
	public static CellStyle createCellStyleNormal(Workbook workbook,Font font){ 
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setFont(font);
		return cellStyle;
	}
	
	
	public static CellStyle createCellStyleDateNormal(Workbook workbook,Font font){ 
		CellStyle cellStyleDate = workbook.createCellStyle();
		CreationHelper createHelper = workbook.getCreationHelper();
		cellStyleDate.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyleDate.setBorderTop(CellStyle.BORDER_THIN);
		cellStyleDate.setBorderRight(CellStyle.BORDER_THIN);
		cellStyleDate.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
		cellStyleDate.setFont(font);
		return cellStyleDate;
		
	}
	
	private static void createCellBorder(Workbook workbook, Row row, Object object, Integer column,CellStyle cellStyle,CellStyle cellStyleDate) {
		if (object != null) {
			CreationHelper createHelper = workbook.getCreationHelper();
			Cell cell = row.createCell(column);;
			if (object instanceof Double) {
				// format cell
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
				cell.setCellValue((Double) object);
				cell.setCellStyle(cellStyle);
			}
			if (object instanceof Long) {
				// format cell
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
				cell.setCellValue((Long) object);
				cell.setCellStyle(cellStyle);
			}
			if (object instanceof Integer) {
				cell = row.createCell(column, Cell.CELL_TYPE_NUMERIC);
				cell.setCellValue((Integer) object);
				cell.setCellStyle(cellStyle);
			}
			if (object instanceof String) {
				cell = row.createCell(column, Cell.CELL_TYPE_STRING);
				cell.setCellValue(object.toString());
				cell.setCellStyle(cellStyle);
			}
			if (object instanceof Date) {
				cell.setCellValue((Date) object);
				cell.setCellStyle(cellStyleDate);
			}
		}else{
			row.createCell(column, Cell.CELL_TYPE_STRING).setCellValue("");
		}
	}

	private static void createCellNoBorder(Workbook workbook, Row row, Object object, Integer column,Font font) {
		if (object != null) {
			CreationHelper createHelper = workbook.getCreationHelper();
			CellStyle cellStyle = workbook.createCellStyle();
			if (object instanceof Double) {
				// format cell
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
				cellStyle.setFont(font);
				Cell cell = row.createCell(column);
				cell.setCellValue((Double) object);
				cell.setCellStyle(cellStyle);
			}
			if (object instanceof Long) {
				// format cell
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("_(* #,##0_);_(* (#,##0);_(* \"-\"??_);_(@_)"));
				cellStyle.setFont(font);
				Cell cell = row.createCell(column);
				cell.setCellValue((Long) object);
				cell.setCellStyle(cellStyle);
			}
			if (object instanceof Integer) {
				row.createCell(column, Cell.CELL_TYPE_NUMERIC).setCellValue((Integer) object);
				row.getCell(column).getCellStyle().setFont(font);
			}
			if (object instanceof String) {
				row.createCell(column, Cell.CELL_TYPE_STRING).setCellValue(object.toString());
				row.getCell(column).getCellStyle().setFont(font);
			}
			if (object instanceof Date) {
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("mm/dd/yyyy"));
				cellStyle.setFont(font);
				Cell cell = row.createCell(column);
				cell.setCellValue((Date) object);
				cell.setCellStyle(cellStyle);
			}
		}else{
			row.createCell(column, Cell.CELL_TYPE_STRING).setCellValue("");
			row.getCell(column).getCellStyle().setFont(font);
		}
	}
	public static String encryptMD5(String input) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String hashedPass = encoder.encodePassword(input, null);
		return hashedPass;
	}

	public static void createFile(String pathToFileTmp, HttpServletRequest request, HttpServletResponse response) throws IOException{

		// export file pdf file
		File fileTemp = new File(pathToFileTmp);
		FileInputStream fileInputStream = new FileInputStream(fileTemp);
		byte[] arr = new byte[1024];
		int numRead = -1;
		String userAgent = request.getHeader("user-agent");
		if (userAgent.toLowerCase().contains("chrome")) {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition","attachment; filename=\"" + fileTemp.getName() + "\"");
		} else {
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.addHeader("Content-Disposition", "attachment; filename=\"" + fileTemp.getName() + "\"");
		}
		
		while ((numRead = fileInputStream.read(arr)) != -1) {
			response.getOutputStream().write(arr, 0, numRead);
		}
		
		fileInputStream.close();
		fileTemp.delete();
	}
	
	public static void createFile(Workbook workbook,String newFile,HttpServletRequest request, HttpServletResponse response) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				workbook.write(bos);
				byte[] bytes = bos.toByteArray();
				response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
				response.addHeader("Content-Disposition", "attachment; filename=\"" + newFile + "\"");
				response.getOutputStream().write(bytes);
			} finally {
				bos.close();
			}
		} catch (Exception e) {
		}
	}

	
	public static <E> String convertListToString(List<E> lst){
		String result = "";
		if(lst != null && lst.size() > 0){
			int i = 0;
			for (E item : lst) {
				if(i == lst.size() - 1){
					result += item;
				}else{
					result += item + ";";
				}
				
				
				++i;
			}
		}
		return ";"+result+";";
	}
	
	public static <E> List<E> convertStringToList(E dataType, String str){
		List<E> lst = new ArrayList<E>();
		if(StringUtils.isNotEmpty(str)){
			String[] arr = str.split(";");
			for (String each : arr) {
				if(StringUtils.isNotEmpty(each)){
					lst.add((E)each);
				}
			}
		}
		
		return lst;
	}
	
	public static String removeDuplicate(String input, String regex) {
		Set<String> set = new HashSet<String>();
		if (StringUtils.isNotEmpty(input)) {
			set.addAll(Arrays.asList(input.replaceAll(" ","").split(regex)));
		}
		if (set.size() > 0)
			return set.toString().substring(1, set.toString().length() - 1);
		return "";
	}
	
	public static Locale getLocale(){
		Locale locale = LocaleContextHolder.getLocale();
		if (locale.toString().startsWith("en")) {
			locale = new Locale("en");
		}else{
			locale = new Locale("vi");
		}
		return locale;
	}
	

	
	public static String generateId(String lastId, String prefix) {
		DecimalFormat indexFormatter = new DecimalFormat("000");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
		String date = dateFormat.format(Calendar.getInstance().getTime());
		int iIndex = 1;
		
		if(lastId != null && lastId != "" && lastId.length() == 8){
			String sPrefix = lastId.substring(0, 1);
			String sDate = lastId.substring(1, 5);			
			String sIndex = lastId.substring(5, 8);
			
			if(date.equals(sDate)) {
				iIndex = Integer.parseInt(sIndex) + 1;
				return sPrefix+sDate+indexFormatter.format(iIndex);
			}
			
			return sPrefix+sDate+indexFormatter.format(iIndex);
			
		} else {
			return prefix + date + "001";
		}
	}
	
	public static Timestamp getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
		return timestamp;
	}
	
	public static void testEmail1() {
		logger.info("TEST EMAIL BEGIN TEST 1");
		try{
			JavaMailSenderImpl test = new JavaMailSenderImpl(); 		
			test.setHost("mail.fe_credit.vn");
			test.setPort(25);
			test.setUsername("cms@coteccons.vn");
			test.setPassword("C0ntruct10nm@n@5yS");
					
			
			test.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
			test.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
			test.getJavaMailProperties().setProperty("mail.smtp.ssl.trust", "*");
			
		
	
			
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");				
					message.setTo("tuannd@unit.com.vn");
					String personal = "CMS Coteccons";				
					message.setFrom("cms@coteccons.vn", personal);
					message.setSubject("TEST EMAIL 1");
	
					message.setText("TEST EMAIL 1");
				}
			};
			test.send(preparator);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("TEST EMAIL SUCCESS TEST 1");
	}
	
	public static void testEmail2() {
		logger.info("TEST EMAIL BEGIN TEST 2");
		try{
			JavaMailSenderImpl test = new JavaMailSenderImpl(); 		
			test.setHost("mail.fe_credit.vn");
			test.setPort(25);
			test.setUsername("cms@coteccons.vn");
			test.setPassword("C0ntruct10nm@n@5yS");
					
			
			test.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
			test.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
			test.getJavaMailProperties().setProperty("mail.smtp.ssl.trust", "*");
			
		
	
			
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");				
					message.setTo("cms@coteccons.vn");
					String personal = "CMS Coteccons";				
					message.setFrom("cms@coteccons.vn", personal);
					message.setSubject("TEST EMAIL 2");
	
					message.setText("TEST EMAIL 2");
				}
			};
			test.send(preparator);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("TEST EMAIL SUCCESS TEST 2");
	}
	
	public static void testEmail3() {
		logger.info("TEST EMAIL BEGIN TEST 3");
		try{
			JavaMailSenderImpl test = new JavaMailSenderImpl(); 		
			test.setHost("smtp.gmail.com");
			test.setPort(587);
			test.setUsername("ninhhang07@gmail.com");
			test.setPassword("haohang8288");
					
			
			test.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
			test.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
			test.getJavaMailProperties().setProperty("mail.smtp.ssl.trust", "*");
			
		
	
			
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws Exception {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage,"UTF-8");				
					message.setTo("cms@coteccons.vn");
					String personal = "CMS Coteccons";				
					message.setFrom("ninhhang07@gmail.com", personal);
					message.setSubject("TEST EMAIL 3");
	
					message.setText("TEST EMAIL 3");
				}
			};
			test.send(preparator);
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		logger.info("TEST EMAIL SUCCESS TEST 3");
	}
	
	
	

	private static String encryptBasic(String plainText, Integer addNum ) {
		if(StringUtils.isEmpty(plainText)){
			return "";
		}
		StringBuffer encryptedString = new StringBuffer();
		int encryptedInt;
		for (int i = 0; i < plainText.length(); i++) {
			int plainTextInt = (int) (plainText.charAt(i));
			encryptedInt = (plainTextInt + addNum);			
			if(encryptedInt>=200){
				encryptedInt = (plainTextInt - addNum);		
			}
			encryptedString.append((char) (encryptedInt));
		}
		return encryptedString.toString();
	}

	private static String decryptBasic(String decryptedText, Integer subNum) {
		if(StringUtils.isEmpty(decryptedText)){
			return "";
		}
		StringBuffer decryptedString = new StringBuffer();
		for (int i = 0; i < decryptedText.length(); i++) {
			int decryptedTextInt = (int) (decryptedText.charAt(i) - subNum);
			if(decryptedTextInt>(200-subNum*2)){
				decryptedTextInt = (int) (decryptedText.charAt(i) + subNum);	
			}
			decryptedString.append((char) (decryptedTextInt));
		}
		return decryptedString.toString();
	}
	
	public static void main(String[] args) {
		String plainText = "c0n9thon9t1nNoibo@20xx?_@()";
		String encryptedText = encryptBasic(plainText, 10);
		System.out.println("Encrypted Text After Encryption: " + encryptedText);
		String decryptedText = decryptBasic(encryptedText, 10);
		System.out.println("Decrypted Text After Decryption: " + decryptedText);
	}
	
	public static String readableFileSize(long size) {
	    if(size <= 0) return "0";
	    final String[] units = new String[] { "B", "kB", "MB", "GB", "TB" };
	    int digitGroups = (int) (Math.log10(size)/Math.log10(1024));
	    return new DecimalFormat("#,##0.#").format(size/Math.pow(1024, digitGroups)) + " " + units[digitGroups];
	}
	
	public static String getFormatDate(Date dateString, String format)
			throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format,Locale.ENGLISH);
		String date = null;
		try {
			if (dateString != null) {
				date = simpleDateFormat.format(dateString);
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			// throw new BusinessException(exception);
		}
		return date;
	}

public static boolean sendPushNotification(String msg, String userId,String adapterurl) throws Exception {
		
		System.out.println("sending notification to userId "+userId);
		
	  String   temMsg = "['"+userId+"','"+msg+"']";
		String spec = adapterurl;//+"?params=['"+userId+"','"+msg+"']";
		System.out.println("specification------- "+spec);

		
			
			    RestTemplate restTemplate = new RestTemplate();
			    System.out.println(msg);
			    URI  targetUrl= UriComponentsBuilder.fromUriString(spec).queryParam("params", temMsg).build()
			    	    .toUri();
			    String result = restTemplate.getForObject(targetUrl, String.class);
			    System.out.println("result restTemplate ------------------------ "+result);

		return true;
	}
}
