package vn.com.unit.fe_credit.config;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.FieldMethodizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.entity.SystemSetting;
import vn.com.unit.fe_credit.service.SystemSettingService;

@Component(value = "systemConfig")
// @Scope(value="singleton", proxyMode=ScopedProxyMode.TARGET_CLASS)
// @Service(value="systemConfig")
@Scope(value = "singleton")
public class SystemConfig {

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	SystemSettingService systemSettingService;

	@Autowired
	@Qualifier(value = "mailSender")
	private JavaMailSenderImpl mailSender;

	// This for load constant to email template
	public static FieldMethodizer loadStaticField = new FieldMethodizer("vn.com.unit.fe_credit.config.SystemConfig");

 
	// /*DEV*/
//	 public final static String SOAP_SERVIES_URL = "http://115.79.47.252:7801/";
//	 public final static String SEND_MESS_SOAP_SERVIES_URL = "http://esb-uat.deltavn.vn:7800/";
//	 public final static String PUSH_NOTIFICATION_URL = "http://wltest.sokhambenh.vn:9081/fecredit/";
//	 public final static String NET_CLIENT_GET_URL = "http://115.79.47.252:7800/Mobile_getContract/";

	// /*UAT*/
	public final static String SOAP_SERVIES_URL = "http://uat-app60.deltavn.vn:7800/";
	public final static String SEND_MESS_SOAP_SERVIES_URL = "http://uat-app60.deltavn.vn:7800/";
	public final static String PUSH_NOTIFICATION_URL = "http://10.30.135.64:9080/mobilews/";
	public final static String NET_CLIENT_GET_URL = "http://uat-app60.deltavn.vn:7800/Mobile_getContract/";

	// /*PRODUCTION*/
//	 public final static String SOAP_SERVIES_URL = "http://esb-prod.deltavn.vn:7800/";
//	 public final static String SEND_MESS_SOAP_SERVIES_URL = "http://esb-prod.deltavn.vn:7800/";
//	 public final static String PUSH_NOTIFICATION_URL = "http://10.30.135.69/mobilews/";
//	 public final static String NET_CLIENT_GET_URL = "http://esb-prod.deltavn.vn:7800/Mobile_getContract/";
 
	private Map<String, String> configMap = new HashMap<>();
	private Map<String, String> categoryBannerMap = new HashMap<>();
	private Map<String, String> loanCategoryMap = new HashMap<>();

	// unit currency in report
	private Map<Long, String> unitCurrencyMap = new TreeMap<Long, String>();

	public static final String USER_LDAP = "USER_LDAP";
	public static final String PASSWORD_LDAP = "PASSWORD_LDAP";
	public static final String REPOSITORY_ROOT_FOLDER = "REPOSITORY_ROOT_FOLDER";
	public static final String TEMP_FOLDER = "TEMP_FOLDER";
	public static final String PARENT_LINK_SYMBOL = "PARENT_LINK_SYMBOL";
	public static final String PAGING_SIZE = "PAGING_SIZE";
	public static final String TEAM_TYPE = "TEAM_TYPE";
	public static final String DEFAULT_CASH_ACCOUNT_NO = "DEFAULT_CASH_ACCOUNT_NO";
	public static final String LANGUAGE_DEFAULT = "LANGUAGE_DEFAULT";
	public static final String DATE_PATTERN = "DATE_PATTERN";
	public static final String DATE_TIME_PATTERN = "DATE_TIME_PATTERN";
	public static final String FROM_YEAR = "FROM_YEAR";
	public static final String TO_YEAR = "TO_YEAR";
	public static final String CURRENCY_DEFAULT = "CURRENCY_DEFAULT";
	public static final String EMAIL_OPTION = "EMAIL_OPTION";
	public static final String SCHEDULE_MAIL = "SCHEDULE_MAIL";
	public static final String CHECK_LIMIT_EMAIL = "CHECK_LIMIT_EMAIL";
	public static final String LIMIT_EMAIL = "LIMIT_EMAIL";
	public static final String BMS_HR = "BMS_HR";
	// public static final String UPLOAD_DOMAIN = "UPLOAD_DOMAIN";
	// public static final String UPLOAD_PATH = "UPLOAD_PATH";
	// public static final String UPLOAD_TEMP_PATH = "UPLOAD_TEMP_PATH";
	public static final String UNUSEDLONGTIME = "UNUSEDLONGTIME";
	public static final String TYPE_REGISTER = "BANNER_CARDTYPE";
	public static final String BANNER_CARDTYPE = "BANNER_CARDTYPE";
	public static final String BANNER_CATEGORY = "BANNER_CATEGORY";
	public static final String BANNER_COMPARE_FULL = "BANNER_COMPARE_FULL";
	public static final String BANNER_COMPARE_QUICK = "BANNER_COMPARE_QUICK";
	public static final String BANNER_APPLY_FORM = "BANNER_APPLY_FORM";
	public static final String BANNER_REWARD_BENEFIT_BENEFIT = "BANNER_REWARD_BENEFIT_BENEFIT";
	public static final String BANNER_REWARD_BENEFIT_PLATIUM = "BANNER_REWARD_BENEFIT_PLATIUM";
	public static final String BANNER_REWARD_BENEFIT_T_365 = "BANNER_REWARD_BENEFIT_T_365";
	public static final String BANNER_SEND_REQUEST = "BANNER_SEND_REQUEST";
	public static final String TIME_RECEIVE = "TIME_RECEIVE";
	public static final String NUMBER_SUPPORT = "NUMBER_SUPPORT";
	public static final String CONFIG_APPLY_FORM = "CONFIG_APPLY_FORM";
	public static final String CONFIG_LDAP_DOMAIN = "CONFIG_LDAP_DOMAIN";
	public static final String CONFIG_LDAP_URL = "CONFIG_LDAP_URL";
	public static final String CONFIG_LDAP_SEARCH = "CONFIG_LDAP_SEARCH";	
	public static final String CONFIG_ACTIVATECODEEXPIREDATE = "CONFIG_ACTIVATECODEEXPIREDATE";
	public static final String CONFIG_RESERTPASSWORLDEXPIREDDATE = "CONFIG_RESERTPASSWORLDEXPIREDDATE";
	public static final String CONFIG_ACTIVATECODEDATE = "CONFIG_ACTIVATECODEDATE";
	public static final String CONFIG_GETCODEDATE = "CONFIG_GETCODEDATE";
	public static final String WRG_OTP_MAXIMUM_ATTEMPTS = "WRG_OTP_MAXIMUM_ATTEMPTS";
	public static final String WRG_OTP_RESET_PWD_BAN_DUE = "WRG_OTP_RESET_PWD_BAN_DUE";

	public static final String MESSAGE_TYPE_CONTACT = "Contact Us";
	public static final String MESSAGE_TYPE_REMINDER = "Reminder";
	
	
	public static final String DRIVERCLASS = "DRIVERCLASS";
	public static final String JDBCURL = "JDBCURL";
	public static final String USER_KH = "USER_KH";
	public static final String PASSWORD_KH = "PASSWORD_KH";
	public static final String UPLOAD_APPLYNOW = "UPLOAD_APPLYNOW";
	
	public static final String REDEEM_PATH = "REDEEM_PATH";
	public static final String VTIGER_PATH = "VTIGER_PATH";
	public static final String VTIGER_PATH_OTHER = "VTIGER_PATH_OTHER";
	public static final String AUTO_EXPORT_MGM = "AUTO_EXPORT_MGM";
	public static final String PUSH_MESSAGE_FOLLOW_SCHEDULE = "PUSH_MESSAGE_FOLLOW_SCHEDULE";
	public static final String MIN_TIME_PUSHMESSAGE = "MIN_TIME_PUSHMESSAGE";
	
	
	public static final Long MESSAGE_SOURCES = 1l;
	// Activity Log
	public static final Integer SAVE_CUSTOMER = 1;
	public static final Integer EDIT_CUSTOMER = 2;
	public static final Integer EXPORT_LOAN_REPUEST = 3;
	public static final Integer ADD_LOAN_CONDITION = 4;
	public static final Integer EDIT_LOAN_CONDITION = 5;
	public static final Integer ADD_LOAN_DETAIL_CALCULATOR = 6;
	public static final Integer EDIT_LOAN_DETAIL_CALCULATOR = 7;
	public static final Integer USER_LOGIN = 8;
	public static final Integer USER_LOGOUT = 9;
	public static final Integer SAVE_MESSAGE = 10;
	public static final Integer SAVE_CUSTOMER_IMPORT = 11;
	public static final Integer SAVE_MESSAGE_IMPORT = 12;
	public static final Integer SAVE_CUSTOMER_DELETE = 13;
//	public static final Integer CHANGE_STATUS_MESSAGE = 11;

	// HOBBY Log
	public static final Integer EXPORT_HOBBY = 12;
	public static final Integer USER_LOGIN_HOBBY = 13;
	public static final Integer USER_LOGOUT_HOBBY = 14;

	// cms_Category Log
	public static final Integer ADD_NEW_CMS_CATEGORY = 15;
	public static final Integer EDIT_CMS_CATEGORY = 16;
	public static final Integer EXPORT_CMS_CATEGORY = 17;
	public static final Integer USER_LOGIN_CMS_CATEGORY = 18;
	public static final Integer USER_LOGOUT_CMS_CATEGORY = 19;

	// Banner
	public static final Integer ADD_NEW_BANNER = 20;
	public static final Integer EDIT_BANNER = 21;

	// Account
	public static final Integer ADD_NEW_ACCOUNT = 22;
	public static final Integer EDIT_ACCOUNT = 23;	

	// MOBILE
	public static final Integer MOBILE_LOGIN = 24;
	public static final Integer MOBILE_LOGOUT = 25;

	// Mess mobile
	public static final Integer SEND_MESS_CUSTOMER = 26;
	public static final Integer REPLY_MESS_CUSTOMER = 27;
	public static final Integer MOBILE_REGISTER = 28;
	public static final Integer MOBILE_CHANGER_PASS = 29;
	public static final Integer MOBILE_EDIT_CUSTOMER = 30;
	public static final Integer MOBILE_REGISTER_FOGET = 31;
	public static final Integer ADD_NEW_HOBBY = 32;
	public static final Integer EDIT_HOBBY = 33;
	public static final Integer ADD_NEW_CMS = 34;
	public static final Integer EDIT_CMS = 35;
	public static final Integer CONTENT_CHANGE = 36;
 	//mess log be
	public static final Integer MESSAGE_BE_SUBMIT = 37;
	public static final Integer MESSAGE_BE_ASSIGN = 38;
	public static final Integer MESSAGE_BE_REASSIGN = 39;
	public static final Integer MESSAGE_BE_REPLY = 40;
	public static final Integer MESSAGE_BE_MOVE_TO_CLOSE_QUEUE = 42;
	public static final Integer MESSAGE_BE_MOVE_TO_CANCEL_QUEUE = 43;
	public static final Integer MESSAGE_BE_CLOSE = 44;
	public static final Integer MESSAGE_BE_CANCEL = 45;
	public static final Integer MOBILE_FORGOT_PASSWORD = 46;
	public static final Integer MOBILE_RESET_PASSWORD = 47;
	public static final Integer MOBILE_CHANGE_AVATAR = 48;
	public static final Integer MOBILE_CHANGE_PASSWORD = 49;
	public static final Integer MOBILE_REGISTER_SUBMIT = 50;
	public static final Integer MOBILE_REGISTER_ACTIVATE = 51;
	public static final Integer APPLY_NOW_SUBMIT = 52;
	public static final Integer POS_READ = 53;
	
	// Role
		public static final Integer ADD_NEW_ROLE = 54;
		public static final Integer EDIT_ROLE = 55;
	
	// USER
	public static final Integer ADD_NEW_USER = 54;
	public static final Integer EDIT_USER = 55;

	public static final Integer SEARCH_TYPE_LIST_ONLY = 1;
	public static final Integer SEARCH_TYPE_COUNT_ONLY = 2;
	public static final Integer SEARCH_TYPE_LIST_AND_COUNT = 3;
	
	// Type
	public static final Integer CUSTOMER = 1;
	public static final Integer LOAN_REQUEST = 2;
	public static final Integer LOAN_CALCULATOR = 3;
	public static final Integer LOAN_CONDITION = 4;
	public static final Integer BANNER = 5;
	public static final Integer LOGIN = 6;
	public static final Integer LOGOUT = 7;
	public static final Integer HOBBY = 8;
	public static final Integer CMS_CATEGORY = 9;
	public static final Integer ACCOUNT = 10;
	public static final Integer MESSAGE = 11;
	public static final Integer LOGIN_MOBILE = 12;
	public static final Integer LOGOUT_MOBILE = 13;
	public static final Integer REGISTER_MOBILE = 14;
	public static final Integer CHANGER_PASS_MOBILE = 15;
	public static final Integer CUSTOMER_MOBILE = 16;
	public static final Integer REGISTER_FORGET_MOBILE = 17;
	public static final Integer CMS = 18;
	public static final Integer CONTENT = 19;
	public static final Integer ROLE = 20;
	
	public static final Integer USER = 20;

	public String getBMS_HR() {
		return BMS_HR;
	}

	// email config
	public static final String EMAIL_HOST = "EMAIL_HOST";
	public static final String EMAIL_PORT = "EMAIL_PORT";
	public static final String EMAIL_PASSWORD = "EMAIL_PASSWORD";
	public static final String EMAIL_DEFAULT = "EMAIL_DEFAULT";
	public static final String EMAIL_DEFAULT_NAME = "EMAIL_DEFAULT_NAME";
	public static final String URL_DEFAULT = "URL_DEFAULT";

	public static final Integer DOCUMENT_DOCUMENT = 1;

	public static enum Remind {
		CARD_DETAIL(1), CARD_DEDTAIL_OFCATEGORY(2), BENEFIT_365(3), BENEFIT_BENEFIT_PLATIUM(
				4), HOMEPAGE(5), POPUP_HOMEPAGE(6), RECEIVECARDINFO(7), CREDITCARD(
				8), FEEDBACK(9);
		private Remind(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}

	public static enum RemindStatus {
		PENDING(1), REJECT(2);
		private RemindStatus(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}

	public static enum Action {
		Active(1);
		private Action(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}

	public static enum TitleBenefits {
		BENEFIT(1), PLATIUM(2), T_365(3);
		private TitleBenefits(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}

	public static enum BenefitsDetailType {
		DEBIT(1), CREDIT(2), PRE_PAID(3);
		private BenefitsDetailType(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}
	
	public static enum UserAccessFlag {
		PORTAL(1,"Portal access"), MOBILE(2,"Mobile access"),MOBILE_PORTAL(3,"Both Mobile and Portal access");
		private UserAccessFlag(int intValue,String desc) {
			this.intValue = intValue;
			this.desc=desc;
		}

		private int intValue;
		private String desc;

		public int getIntValue() {
			return intValue;
		}
		
		public String getDesc(){
			return desc;
		}
		
	}

	public static enum Gender {
		MALE(1,"Male"),FEMALE(2,"Female"),OTHER(3,"Other");
		private Gender(int intValue,String desc) {
			this.intValue = intValue;
			this.desc=desc;
		}

		private int intValue;
		private String desc;

		public int getIntValue() {
			return intValue;
		}
		
		public String getDesc(){
			return desc;
		}
		
	}
	
	public static enum Status {
		ACTIVE(1,"Active"),INACTIVE(2,"Inactive");
		private Status(int intValue,String desc) {
			this.intValue = intValue;
			this.desc=desc;
		}

		private int intValue;
		private String desc;

		public int getIntValue() {
			return intValue;
		}
		
		public String getDesc(){
			return desc;
		}
		
	}
	
	private Map<Integer, String> documentSourceMap = new LinkedHashMap<>();
	private Map<Integer, String> documentSourceLinkMap = new LinkedHashMap<>();
	private Map<Integer, String> homepageLinkMap = new LinkedHashMap<>();
	private Map<Integer, String> requestLinkMap = new LinkedHashMap<>();
	private Map<Integer, String> typeProcessTableLinkMap = new LinkedHashMap<>();
	private Map<Integer, String> categoryLinkMap = new LinkedHashMap<>();
	private Map<Integer, String> statusApplyNew = new LinkedHashMap<>();
	private Map<Integer, String> activityLog = new LinkedHashMap<>();
	private Map<Integer, String> messageStatusList = new LinkedHashMap<>();

	// sub category
	public static final Integer SUB_CATEGORY = 1;

	public static final Integer HOMEPAGE_STATUS_DRAFT = 0;
	public static final Integer HOMEPAGE_STATUS_WAITING_APPROVAL = 1;
	public static final Integer HOMEPAGE_STATUS_APPROVED = 2;

	public static final Integer REQUEST_STATUS_NEW = 0;
	public static final Integer REQUEST_STATUS_SOLVED = 1;

	public static final Integer SEX_MALE = 1;
	public static final Integer SEX_FEMALE = 2;

	public static final Integer POSSESSORY_HOME = 1;
	public static final Integer POSSESSORY_HOME_PARENTS = 2;
	public static final Integer POSSESSORY_HOME_RENT_BELOW3 = 3;
	public static final Integer POSSESSORY_HOME_RENT_ON3 = 4;

	public static final Integer PAYCHECK_FORM_CASH = 1;
	public static final Integer PAYCHECK_FORM_TRANSFER = 2;

	public static final Integer FAMILY_RELATION_FATHER = 1;
	public static final Integer FAMILY_RELATION_MOTHER = 2;
	public static final Integer FAMILY_RELATION_BROTHER = 3;
	public static final Integer FAMILY_RELATION_SISTERS = 4;
	public static final Integer FAMILY_RELATION_CHILDREN = 5;

	public static final Integer PAYMENT_RATE_MIN = 1;
	public static final Integer PAYMENT_RATE_FULL = 2;

	public static final Integer ADDRESS_HOUSEHOLD = 1;
	public static final Integer ADDRESS_CURRENT = 2;
	public static final Integer ADDRESS_COMPANY = 3;
	public static final Integer ADDRESS_CONTACT = 4;
	public static final Integer ADDRESS_BRANCH = 5;

	public static final Integer CREDIT_LIMIT_MAIN = 1;
	public static final Integer CREDIT_LIMIT_OTHER = 2;

	public static final Integer LOAN_TYPE_CREDIT_CARD = 1;
	public static final Integer LOAN_TYPE_OVERDRAFT = 2;
	public static final Integer LOAN_TYPE_CONSUMER_INSTALLMENT = 3;
	public static final Integer LOAN_TYPE_MORTGAGE = 4;

	public static final Integer TYPE_PROCESS_TABLE_CARD_DETAIL = 1;
	public static final Integer TYPE_PROCESS_TABLE_HOMEPAGE_ADMIN = 2;

	public static final Integer MESSAGE_CATEGORY_FEEDBACK = 1;
	public static final Integer MESSAGE_CATEGORY_CONSULTING_LENDING = 2;

	public static final Integer COMMENT_CARD_DETAIL = 1;

	public static final String CATEGORY_HOME = "home";
	public static final String CATEGORY_AUTOMOBIKE = "automobike";
	public static final String CATEGORY_FAQ = "faq";
	public static final String CATEGORY_FINANCETIPS = "financetips";
	public static final String CATEGORY_TIPS = "tips";

	public static final String LOAN_CATEGORY_PL = "PL";
	public static final String LOAN_CATEGORY_TW = "TW";
	public static final String LOAN_CATEGORY_CD = "CD";

	public static final Integer STATUS_ACTIVE = 1;
	public static final Integer STATUS_INACTIVE = -1;

	public static final String NUMBER_PATTERN = "#,##0.##";

	public static final Integer APPLY_NOW_STATUS_NEW = 1;
	public static final Integer APPLY_NOW_STATUS_EXPORTED = 2;

	// user type
	public static final Integer SYSTEM = 1;
	public static final Integer MOBILE = 2;
	
	/*MGM list friends */
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_DUPLICATED = 1;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_EXIST_CUSTOMER = 2;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_ACTIVE = 3;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_BECOME_LOAN = 4;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_LOCKED = 5;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_IO_PROCESS = 6;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_WAITING_FOR_LOAN = 7;
	public static final Integer MGM_SUGGEST_CONTACT_STATUS_LOANS = 8;
	
	/*MGM_REDEEM*/
	public static final Integer MGM_REDEEM_STATUS_SUBMITED =1 ;
	public static final Integer MGM_REDEEM_STATUS_IN_PROCESS = 2;
	public static final Integer MGM_REDEEM_STATUS_SUCCESSFUL = 3;
	public static final Integer MGM_REDEEM_STATUS_FAILURE = 4;
	public static final Integer MGM_REDEEM_STATUS_REVERTED = 5;
	
	public static final Integer MGM_REDEEM_TRANXTYPE_MOMO = 1;
	public static final Integer MGM_REDEEM_TRANXTYPE_PAYMENT = 2;
	
	/*CONTACT_INVITE_METHOD*/
	public static final Integer MGM_SUGGEST_CONTACT_INVITE_METHOD_INPUT = 1;
	public static final Integer MGM_SUGGEST_CONTACT_INVITE_METHOD_IMPORT = 2;

	@PostConstruct
	@Transactional
	public void init() {
		List<SystemSetting> settings = systemSettingService.findAll();
		for (SystemSetting systemSetting : settings) {
			getConfigMap()
					.put(systemSetting.getKey(), systemSetting.getValue());
		}

		unitCurrencyMap.put(new Long(1), "currency.unit.VND");
		unitCurrencyMap.put(new Long(1000), "currency.unit.thousandVND");
		unitCurrencyMap.put(new Long(1000000), "currency.unit.millionVND");

		// init mail
		if (getConfig(EMAIL_HOST) != null && getConfig(EMAIL_HOST) != "") {
			mailSender.setHost(getConfig(EMAIL_HOST));
			mailSender.setPort(getIntConfig(EMAIL_PORT));
			mailSender.setUsername(getConfig(EMAIL_DEFAULT));
			mailSender.setPassword(getConfig(EMAIL_PASSWORD));
			try {
				mailSender.getJavaMailProperties().setProperty(
						"mail.smtp.ssl.trust", "*");
			} catch (Exception e) {

			}
		}

		homepageLinkMap.put(HOMEPAGE_STATUS_DRAFT, "homepage.status.draft");
		homepageLinkMap.put(HOMEPAGE_STATUS_WAITING_APPROVAL,
				"homepage.status.wattingapprover");
		homepageLinkMap.put(HOMEPAGE_STATUS_APPROVED,
				"homepage.status.approver");

		requestLinkMap.put(REQUEST_STATUS_NEW, "sendrequest.status.waitting");
		requestLinkMap.put(REQUEST_STATUS_SOLVED, "sendrequest.status.solved");

		typeProcessTableLinkMap.put(TYPE_PROCESS_TABLE_CARD_DETAIL,
				"process.table.card.detail");
		typeProcessTableLinkMap.put(TYPE_PROCESS_TABLE_HOMEPAGE_ADMIN,
				"process.table.homepage.admin");

		categoryLinkMap.put(MESSAGE_CATEGORY_FEEDBACK,
				"message.category.feedback");
		categoryLinkMap.put(MESSAGE_CATEGORY_CONSULTING_LENDING,
				"message.consulting.lending");

		categoryBannerMap.put(CATEGORY_HOME, "Home");
		categoryBannerMap.put(CATEGORY_AUTOMOBIKE, "Automobike");
		categoryBannerMap.put(CATEGORY_FINANCETIPS, "Financetips");
		categoryBannerMap.put(CATEGORY_FAQ, "Faq");
		categoryBannerMap.put(CATEGORY_TIPS, "Tips");

		loanCategoryMap.put(LOAN_CATEGORY_PL, LOAN_CATEGORY_PL);
		loanCategoryMap.put(LOAN_CATEGORY_TW, LOAN_CATEGORY_TW);
		loanCategoryMap.put(LOAN_CATEGORY_CD, LOAN_CATEGORY_CD);

		statusApplyNew.put(APPLY_NOW_STATUS_NEW, "apply.now.new");
		statusApplyNew.put(APPLY_NOW_STATUS_EXPORTED, "apply.now.exported");
		
		// Message status list
		messageStatusList.put(0, MessageStatus.values()[0].toString());
		messageStatusList.put(1, MessageStatus.values()[1].toString());
		messageStatusList.put(2, MessageStatus.values()[2].toString());
		messageStatusList.put(3, MessageStatus.values()[3].toString());
		messageStatusList.put(4, MessageStatus.values()[4].toString());
		messageStatusList.put(5, MessageStatus.values()[5].toString());
		messageStatusList.put(6, MessageStatus.values()[6].toString());
		messageStatusList.put(7, MessageStatus.values()[7].toString());
		
		// Customer Activity Logs
		activityLog.put(SAVE_CUSTOMER, "BE.CUSTOMER.CREATE");
		activityLog.put(EDIT_CUSTOMER, "BE.CUSTOMER.EDIT");

		// banner
		activityLog.put(ADD_NEW_BANNER, "ba.banner.save");
		activityLog.put(EDIT_BANNER, "ba.banner.edit");

		// account
		activityLog.put(ADD_NEW_ACCOUNT, "mo.account.save");
		activityLog.put(EDIT_ACCOUNT, "mo.account.edit");
		// Contact
		activityLog.put(CONTENT_CHANGE, "BE.SINGLE_ABOUT.CHANGE");
		// Hobby Logs
		activityLog.put(EDIT_HOBBY, "ba.hobby.edit");
		activityLog.put(ADD_NEW_HOBBY, "ba.hobby.new");

		// CMS_CATEGORY Logs
		activityLog.put(EDIT_CMS_CATEGORY, "mo.cms_Category.edit");
		activityLog.put(ADD_NEW_CMS_CATEGORY, "mo.cms_Category.new");
		//cms
		activityLog.put(EDIT_CMS, "be.cms.edit");
		activityLog.put(ADD_NEW_CMS, "be.cms.new");
		// Loan repuest Activity Logs
		activityLog.put(EXPORT_LOAN_REPUEST, "be.loan.export");
		activityLog.put(ADD_LOAN_CONDITION, "mo.loan.add.condition");
		activityLog.put(EDIT_LOAN_CONDITION, "mo.loan.edit.condition");
		activityLog.put(ADD_LOAN_DETAIL_CALCULATOR, "mo.loan.detail.add");
		activityLog.put(EDIT_LOAN_DETAIL_CALCULATOR, "mo.loan.detail.edit");
		activityLog.put(USER_LOGIN, "mo.login.system");
		activityLog.put(USER_LOGOUT, "mo.logout.system");

		// message log
		activityLog.put(SAVE_MESSAGE, "mo.message.save");
		activityLog.put(SAVE_CUSTOMER_IMPORT, "BE.CUSTOMER.IMPORT");
		activityLog.put(SAVE_MESSAGE_IMPORT, "BE.MESSAGE.IMPORT");
		activityLog.put(SAVE_CUSTOMER_DELETE, "BE.CUSTOMER.DELETE");
		//activityLog.put(CHANGE_STATUS_MESSAGE, "mo.change.status.message");
		
		// message log be
		activityLog.put(MESSAGE_BE_SUBMIT, "BE.MESSAGE.SUBMIT");
		activityLog.put(MESSAGE_BE_ASSIGN, "BE.MESSAGE.ASSIGN");
		activityLog.put(MESSAGE_BE_REASSIGN, "BE.MESSAGE.REASSIGN");
		activityLog.put(MESSAGE_BE_REPLY, "BE.MESSAGE.REPLY");
		activityLog.put(MESSAGE_BE_MOVE_TO_CLOSE_QUEUE, "BE.MESSAGE.MOVE_TO_CLOSE_QUEUE");
		activityLog.put(MESSAGE_BE_MOVE_TO_CANCEL_QUEUE, "BE.MESSAGE.MOVE_TO_CANCEL_QUEUE");
		activityLog.put(MESSAGE_BE_CLOSE, "BE.MESSAGE.CLOSE");
		activityLog.put(MESSAGE_BE_CANCEL, "BE.MESSAGE.CANCEL");
		// mobile
		activityLog.put(MOBILE_LOGIN, "MO.CUSTOMER.SIGN_IN");
		activityLog.put(MOBILE_LOGOUT, "MO.CUSTOMER.SIGN_OUT");
		activityLog.put(MOBILE_FORGOT_PASSWORD, "MO.CUSTOMER.FORGOT_PASSWORD");
		activityLog.put(MOBILE_RESET_PASSWORD, "MO.CUSTOMER.RESET_PASSWORD");
		activityLog.put(MOBILE_CHANGE_AVATAR, "MO.CUSTOMER.CHANGE_AVATAR");
		activityLog.put(MOBILE_CHANGE_PASSWORD, "MO.CUSTOMER.CHANGE_PASSWORD");
		activityLog.put(MOBILE_REGISTER_SUBMIT, "MO.CUSTOMER.REGISTER_SUBMIT");
		activityLog.put(MOBILE_REGISTER_ACTIVATE, "MO.CUSTOMER.REGISTER_ACTIVATE");
		activityLog.put(APPLY_NOW_SUBMIT, "MO.LOAN.SUBMIT");
		activityLog.put(POS_READ, "MO.POS.READ");
	

		// mess mobile
		activityLog.put(SEND_MESS_CUSTOMER, "mo.mess.to.customer.send");
		activityLog.put(REPLY_MESS_CUSTOMER, "mo.mess.to.customer.reply");
		
		activityLog.put(MOBILE_REGISTER, "mo.register.mobile");
		activityLog.put(MOBILE_CHANGER_PASS, "mo.change.pass.mobile");
		activityLog.put(MOBILE_EDIT_CUSTOMER, "mo.edit.cus.mobile");
		activityLog.put(MOBILE_REGISTER_FOGET, "mo.register.forget.mobile");
		
		//roleLog
		activityLog.put(ADD_NEW_ROLE,"add.role.new");
		activityLog.put(EDIT_ROLE,"role.edit");
		
	}

	public String getConfig(String key) {
		String result = configMap.get(key);
		return result;
	}

	public Integer getIntConfig(String key) {
		try {
			String result = configMap.get(key);
			if (StringUtils.isNotEmpty(result)) {
				return Integer.parseInt(result);
			}
		} catch (NumberFormatException e) {
			return null;
		}
		return null;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	public static String getUserLdap() {
		return USER_LDAP;
	}

	public static String getPasswordLdap() {
		return PASSWORD_LDAP;
	}
	
	

	public String getREPOSITORY_ROOT_FOLDER() {
		return REPOSITORY_ROOT_FOLDER;
	}

	public Map<Long, String> getUnitCurrencyMap() {
		return unitCurrencyMap;
	}

	public void setUnitCurrencyMap(Map<Long, String> unitCurrencyMap) {
		this.unitCurrencyMap = unitCurrencyMap;
	}

	public String getLANGUAGE_DEFAULT() {
		return LANGUAGE_DEFAULT;
	}

	public String getEMAIL_HOST() {
		return EMAIL_HOST;
	}

	public String getEMAIL_PORT() {
		return EMAIL_PORT;
	}

	public String getEMAIL_PASSWORD() {
		return EMAIL_PASSWORD;
	}

	public Map<Integer, String> getCategoryLinkMap() {
		return categoryLinkMap;
	}

	public void setStatusApplyNew(Map<Integer, String> statusApplyNew) {
		this.statusApplyNew = statusApplyNew;
	}

	public Map<Integer, String> getStatusApplyNew() {
		return statusApplyNew;
	}

	public void setCategoryLinkMap(Map<Integer, String> categoryLinkMap) {
		this.categoryLinkMap = categoryLinkMap;
	}

	public Map<Integer, String> getDocumentSourceMap() {
		return documentSourceMap;
	}

	public void setDocumentSourceMap(Map<Integer, String> documentSourceMap) {
		this.documentSourceMap = documentSourceMap;
	}

	public Map<Integer, String> getDocumentSourceLinkMap() {
		return documentSourceLinkMap;
	}

	public void setDocumentSourceLinkMap(
			Map<Integer, String> documentSourceLinkMap) {
		this.documentSourceLinkMap = documentSourceLinkMap;
	}

	public Map<Integer, String> getHomepageLinkMap() {
		return homepageLinkMap;
	}

	public void setHomepageLinkMap(Map<Integer, String> homepageLinkMap) {
		this.homepageLinkMap = homepageLinkMap;
	}

	public Map<Integer, String> getRequestLinkMap() {
		return requestLinkMap;
	}

	public void setRequestLinkMap(Map<Integer, String> requestLinkMap) {
		this.requestLinkMap = requestLinkMap;
	}

	public Map<Integer, String> getTypeProcessTableLinkMap() {
		return typeProcessTableLinkMap;
	}

	public void setTypeProcessTableLinkMap(
			Map<Integer, String> typeProcessTableLinkMap) {
		this.typeProcessTableLinkMap = typeProcessTableLinkMap;
	}

	public Map<Integer, String> getActivityLog() {
		return activityLog;
	}

	public void setActivityLog(Map<Integer, String> activityLog) {
		this.activityLog = activityLog;
	}

	public Map<String, String> getCategoryBannerMap() {
		return categoryBannerMap;
	}

	public void setCategoryBannerMap(Map<String, String> categoryBannerMap) {
		this.categoryBannerMap = categoryBannerMap;
	}

	public Map<String, String> getLoanCategoryMap() {
		return loanCategoryMap;
	}

	public void setLoanCategoryMap(Map<String, String> loanCategoryMap) {
		this.loanCategoryMap = loanCategoryMap;
	}

	public Integer getRequestStatusNew() {
		return REQUEST_STATUS_NEW;
	}

	public Integer getRequestStatusSolved() {
		return REQUEST_STATUS_SOLVED;
	}

	public Integer getHomepageStatusDraft() {
		return HOMEPAGE_STATUS_DRAFT;
	}

	public Integer getHomepageStatusWaitingApproval() {
		return HOMEPAGE_STATUS_WAITING_APPROVAL;
	}

	public Integer getHomepageStatusApproved() {
		return HOMEPAGE_STATUS_APPROVED;
	}

	public Integer getSEX_MALE() {
		return SEX_MALE;
	}

	public Integer getSEX_FEMALE() {
		return SEX_FEMALE;
	}

	public Integer getPOSSESSORY_HOME() {
		return POSSESSORY_HOME;
	}

	public Integer getPOSSESSORY_HOME_PARENTS() {
		return POSSESSORY_HOME_PARENTS;
	}

	public Integer getPOSSESSORY_HOME_RENT_BELOW3() {
		return POSSESSORY_HOME_RENT_BELOW3;
	}

	public Integer getPOSSESSORY_HOME_RENT_ON3() {
		return POSSESSORY_HOME_RENT_ON3;
	}

	public Integer getPAYCHECK_FORM_CASH() {
		return PAYCHECK_FORM_CASH;
	}

	public Integer getPAYCHECK_FORM_TRANSFER() {
		return PAYCHECK_FORM_TRANSFER;
	}

	public Integer getFAMILY_RELATION_FATHER() {
		return FAMILY_RELATION_FATHER;
	}

	public Integer getFAMILY_RELATION_MOTHER() {
		return FAMILY_RELATION_MOTHER;
	}

	public Integer getFAMILY_RELATION_BROTHER() {
		return FAMILY_RELATION_BROTHER;
	}

	public Integer getFAMILY_RELATION_SISTERS() {
		return FAMILY_RELATION_SISTERS;
	}

	public Integer getFAMILY_RELATION_CHILDREN() {
		return FAMILY_RELATION_CHILDREN;
	}

	public Integer getPAYMENT_RATE_MIN() {
		return PAYMENT_RATE_MIN;
	}

	public Integer getPAYMENT_RATE_FULL() {
		return PAYMENT_RATE_FULL;
	}

	public Integer getADDRESS_HOUSEHOLD() {
		return ADDRESS_HOUSEHOLD;
	}

	public Integer getADDRESS_CURRENT() {
		return ADDRESS_CURRENT;
	}

	public Integer getADDRESS_COMPANY() {
		return ADDRESS_COMPANY;
	}

	public Integer getADDRESS_CONTACT() {
		return ADDRESS_CONTACT;
	}

	public Integer getADDRESS_BRANCH() {
		return ADDRESS_BRANCH;
	}

	public Integer getCREDIT_LIMIT_MAIN() {
		return CREDIT_LIMIT_MAIN;
	}

	public Integer getCREDIT_LIMIT_OTHER() {
		return CREDIT_LIMIT_OTHER;
	}

	public Integer getLOAN_TYPE_CREDIT_CARD() {
		return LOAN_TYPE_CREDIT_CARD;
	}

	public Integer getLOAN_TYPE_OVERDRAFT() {
		return LOAN_TYPE_OVERDRAFT;
	}

	public Integer getLOAN_TYPE_CONSUMER_INSTALLMENT() {
		return LOAN_TYPE_CONSUMER_INSTALLMENT;
	}

	public Integer getLOAN_TYPE_MORTGAGE() {
		return LOAN_TYPE_MORTGAGE;
	}

	public static enum MessageType {
		CONTACT_US(1), REMINDER(2), CONTRACT(3), PROMOTION(4), NEWS(5), NEW_INFORMATION(6), WELLCOME(7),APPLY_NOW(8);
		private MessageType(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}

	public static enum MessageStatus {
		SENT(0), NEW(1), ASSIGNED(2), RE_ASSIGNED(3), REPLIED(4), MOVE_TO_CLOSING_QUEUE(
				5), CLOSED(6), MOVE_TO_CANCELLING_QUEUE(7), CANCELED(-2);
		private MessageStatus(int intValue) {
			this.intValue = intValue;
		}

		private int intValue;

		public int getIntValue() {
			return intValue;
		}
	}

	public ArrayList<Integer> lstMessageType;

	public ArrayList<Integer> getLstMessageType() {
		lstMessageType = new ArrayList<Integer>();
		lstMessageType.add(MessageType.CONTACT_US.intValue);
		lstMessageType.add(MessageType.REMINDER.intValue);
		lstMessageType.add(MessageType.CONTRACT.intValue);
		return lstMessageType;
	}

	public static String formatDateYMD(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		Date date2 = null;
		try {
			date2 = simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return simpleDateFormat1.format(date2).toString();
	}

	public static Date formatDateMDY(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date2 = null;
		try {
			date2 = simpleDateFormat.parse(formatDateYMD(stringDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date2;
	}
	public static String formatDateTimeToTimeStamp(String stringDate) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		Date date2 = null;
		try {
			date2 = simpleDateFormat.parse(stringDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return simpleDateFormat1.format(date2).toString();
	}
	
	public static String formatDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");
		sdf.format(date);
		return sdf.format(date);

	}

	// MGM list friends
	public LinkedHashMap<Integer,String> mgmSCStatusLst(Locale locale) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();

		map.put(MGM_SUGGEST_CONTACT_STATUS_DUPLICATED, "Duplicated");
		map.put(MGM_SUGGEST_CONTACT_STATUS_EXIST_CUSTOMER, "Exist Customer");
		map.put(MGM_SUGGEST_CONTACT_STATUS_ACTIVE, msgSrc.getMessage("mgm.suggest.contact.status.submited", null, locale));
		map.put(MGM_SUGGEST_CONTACT_STATUS_BECOME_LOAN, "Become Loan");
		map.put(MGM_SUGGEST_CONTACT_STATUS_LOCKED, "Locked");
		map.put(MGM_SUGGEST_CONTACT_STATUS_IO_PROCESS, msgSrc.getMessage("mgm.suggest.contact.status.io.process", null, locale));
		map.put(MGM_SUGGEST_CONTACT_STATUS_WAITING_FOR_LOAN, msgSrc.getMessage("mgm.suggest.contact.status.waiting.for.loans", null, locale));
		map.put(MGM_SUGGEST_CONTACT_STATUS_LOANS, msgSrc.getMessage("mgm.suggest.contact.status.loans", null, locale));

		return map;
		
	}
	
	public LinkedHashMap<Integer, String> mgmRedeemStatusMap(Locale locale) {

		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(MGM_REDEEM_STATUS_SUBMITED, msgSrc.getMessage("mgm.redeem.status.Submited", null, locale));
		map.put(MGM_REDEEM_STATUS_IN_PROCESS, msgSrc.getMessage("mgm.redeem.status.InProccess", null, locale));
		map.put(MGM_REDEEM_STATUS_SUCCESSFUL, msgSrc.getMessage("mgm.redeem.status.Successful", null, locale));
		map.put(MGM_REDEEM_STATUS_FAILURE, msgSrc.getMessage("mgm.redeem.status.Failure", null, locale));
		map.put(MGM_REDEEM_STATUS_REVERTED, "Reverted");
		return map;

	}

	public LinkedHashMap<Integer,String> mgmSCInviteMethodLst(Locale locale) {
		LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(MGM_SUGGEST_CONTACT_INVITE_METHOD_INPUT, msgSrc.getMessage("mgm.suggest.contact.invite.method.input", null, locale));
		map.put(MGM_SUGGEST_CONTACT_INVITE_METHOD_IMPORT, msgSrc.getMessage("mgm.suggest.contact.invite.method.import", null, locale));
		return map;
	}

	public Map<Integer, String> getMessageStatusList() {
		return messageStatusList;
	}

	public void setMessageStatusList(Map<Integer, String> messageStatusList) {
		this.messageStatusList = messageStatusList;
	}
	
	
}
