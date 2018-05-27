package vn.com.unit.fe_credit.controller;


import static vn.com.unit.fe_credit.config.SystemConfig.AUTO_EXPORT_MGM;
import static vn.com.unit.fe_credit.config.SystemConfig.PUSH_MESSAGE_FOLLOW_SCHEDULE;
import static vn.com.unit.fe_credit.config.SystemConfig.MIN_TIME_PUSHMESSAGE;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_APPLY_FORM;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_CARDTYPE;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_CATEGORY;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_COMPARE_FULL;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_COMPARE_QUICK;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_REWARD_BENEFIT_BENEFIT;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_REWARD_BENEFIT_PLATIUM;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_REWARD_BENEFIT_T_365;
import static vn.com.unit.fe_credit.config.SystemConfig.BANNER_SEND_REQUEST;
import static vn.com.unit.fe_credit.config.SystemConfig.CHECK_LIMIT_EMAIL;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_ACTIVATECODEDATE;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_ACTIVATECODEEXPIREDATE;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_APPLY_FORM;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_GETCODEDATE;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_LDAP_DOMAIN;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_LDAP_SEARCH;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_LDAP_URL;
import static vn.com.unit.fe_credit.config.SystemConfig.CONFIG_RESERTPASSWORLDEXPIREDDATE;
import static vn.com.unit.fe_credit.config.SystemConfig.CURRENCY_DEFAULT;
import static vn.com.unit.fe_credit.config.SystemConfig.DATE_PATTERN;
import static vn.com.unit.fe_credit.config.SystemConfig.DEFAULT_CASH_ACCOUNT_NO;
import static vn.com.unit.fe_credit.config.SystemConfig.DRIVERCLASS;
import static vn.com.unit.fe_credit.config.SystemConfig.EMAIL_DEFAULT;
import static vn.com.unit.fe_credit.config.SystemConfig.EMAIL_DEFAULT_NAME;
import static vn.com.unit.fe_credit.config.SystemConfig.EMAIL_HOST;
import static vn.com.unit.fe_credit.config.SystemConfig.EMAIL_OPTION;
import static vn.com.unit.fe_credit.config.SystemConfig.EMAIL_PASSWORD;
import static vn.com.unit.fe_credit.config.SystemConfig.EMAIL_PORT;
import static vn.com.unit.fe_credit.config.SystemConfig.FROM_YEAR;
import static vn.com.unit.fe_credit.config.SystemConfig.JDBCURL;
import static vn.com.unit.fe_credit.config.SystemConfig.LANGUAGE_DEFAULT;
import static vn.com.unit.fe_credit.config.SystemConfig.LIMIT_EMAIL;
import static vn.com.unit.fe_credit.config.SystemConfig.NUMBER_SUPPORT;
import static vn.com.unit.fe_credit.config.SystemConfig.PAGING_SIZE;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.PASSWORD_LDAP;
import static vn.com.unit.fe_credit.config.SystemConfig.REDEEM_PATH;
import static vn.com.unit.fe_credit.config.SystemConfig.REPOSITORY_ROOT_FOLDER;
import static vn.com.unit.fe_credit.config.SystemConfig.SCHEDULE_MAIL;
import static vn.com.unit.fe_credit.config.SystemConfig.TEMP_FOLDER;
import static vn.com.unit.fe_credit.config.SystemConfig.TIME_RECEIVE;
import static vn.com.unit.fe_credit.config.SystemConfig.TO_YEAR;
import static vn.com.unit.fe_credit.config.SystemConfig.UNUSEDLONGTIME;
import static vn.com.unit.fe_credit.config.SystemConfig.UPLOAD_APPLYNOW;
import static vn.com.unit.fe_credit.config.SystemConfig.URL_DEFAULT;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_KH;
import static vn.com.unit.fe_credit.config.SystemConfig.USER_LDAP;
import static vn.com.unit.fe_credit.config.SystemConfig.VTIGER_PATH;
import static vn.com.unit.fe_credit.config.SystemConfig.VTIGER_PATH_OTHER;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.SystemConfigBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.SystemSetting;
import vn.com.unit.fe_credit.service.SystemSettingService;
import vn.com.unit.utils.I18nLocaleResolver;

@Controller
@RequestMapping("/system")
public class SystemConfigController {
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private MessageSource msgSrc;

	@Autowired
	private SystemSettingService systemSettingService;

	@Autowired
	@Qualifier(value = "localeResolver")
	private I18nLocaleResolver localeResolver;
	

	@Autowired
	@Qualifier(value = "mailSender")
	private JavaMailSenderImpl mailSender;
	private static final Logger logger = LoggerFactory.getLogger(SystemConfigController.class);

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);
		
		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
		DoubleEditor doubleEditor = new DoubleEditor(locale,
				SystemConfig.NUMBER_PATTERN);
		binder.registerCustomEditor(Double.class, doubleEditor);
	}

	// khoi tao du lieu mac dinh config , lay du lieu config truoc do len
	@RequestMapping(value = "/system_config", method = RequestMethod.GET)
	public String getSystemConfig(@ModelAttribute(value = "bean") SystemConfigBean systemConfigBean, Model model,
			Locale locale) throws Exception {

		systemConfigBean.getEntity().setPageRow(systemConfig.getConfig(PAGING_SIZE));
		systemConfigBean.getEntity().setDate(systemConfig.getConfig(DATE_PATTERN));
		String defaultcash = systemConfig.getConfig(DEFAULT_CASH_ACCOUNT_NO);
		// kiem tra defaultcass de tim accountchart tuong ung
		try {

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		systemConfigBean.getEntity().setDefaultLanguage(systemConfig.getConfig(LANGUAGE_DEFAULT));

		systemConfigBean.getEntity().setFormYear(Long.parseLong(systemConfig.getConfig(FROM_YEAR)));
		systemConfigBean.getEntity().setToYear(Long.parseLong(systemConfig.getConfig(TO_YEAR)));
		String currencyten = systemConfig.getConfig(CURRENCY_DEFAULT);

		systemConfigBean.getEntity().setUrlDefault(systemConfig.getConfig(URL_DEFAULT));
		systemConfigBean.getEntity().setEmailHost(systemConfig.getConfig(EMAIL_HOST));
		systemConfigBean.getEntity().setEmailPort(systemConfig.getIntConfig(EMAIL_PORT));
		systemConfigBean.getEntity().setEmailPassword(systemConfig.getConfig(EMAIL_PASSWORD));
		systemConfigBean.getEntity().setEmailDefault(systemConfig.getConfig(EMAIL_DEFAULT));
		systemConfigBean.getEntity().setEmailDefaultName(systemConfig.getConfig(EMAIL_DEFAULT_NAME));
		systemConfigBean.getEntity().setEmailOption(systemConfig.getIntConfig(EMAIL_OPTION));
		systemConfigBean.getEntity().setEmailSchedule(systemConfig.getConfig(SCHEDULE_MAIL));
		systemConfigBean.getEntity().setCheckLimmitEmail(systemConfig.getIntConfig(CHECK_LIMIT_EMAIL));

		systemConfigBean.getEntity().setLimmitEmail(systemConfig.getIntConfig(LIMIT_EMAIL));
		systemConfigBean.getEntity().setNotUsedLongTime(systemConfig.getIntConfig(UNUSEDLONGTIME));

		systemConfigBean.getEntity().setRepository(systemConfig.getConfig(REPOSITORY_ROOT_FOLDER));
		systemConfigBean.getEntity().setTempfolder(systemConfig.getConfig(TEMP_FOLDER));
		systemConfigBean.getEntity().setUserldap(systemConfig.getConfig(USER_LDAP));
		systemConfigBean.getEntity().setLdapdomain(systemConfig.getConfig(CONFIG_LDAP_DOMAIN));
		systemConfigBean.getEntity().setLdapurl(systemConfig.getConfig(CONFIG_LDAP_URL));
		systemConfigBean.getEntity().setLdapsearch(systemConfig.getConfig(CONFIG_LDAP_SEARCH));
		systemConfigBean.getEntity().setActivateCodeExpiredDate(systemConfig.getConfig(CONFIG_ACTIVATECODEEXPIREDATE));
		systemConfigBean.getEntity().setResetPasswordExpiredDate(systemConfig.getConfig(CONFIG_RESERTPASSWORLDEXPIREDDATE));
		systemConfigBean.getEntity().setActivateCodeDate(systemConfig.getConfig(CONFIG_ACTIVATECODEDATE));
		systemConfigBean.getEntity().setGetCodeDate(systemConfig.getConfig(CONFIG_GETCODEDATE));
		systemConfigBean.getEntity().setDriverclass(systemConfig.getConfig(DRIVERCLASS));
		systemConfigBean.getEntity().setJdbcurl(systemConfig.getConfig(JDBCURL));
		systemConfigBean.getEntity().setUserkh(systemConfig.getConfig(USER_KH));
		systemConfigBean.getEntity().setPassworldkh(systemConfig.getConfig(PASSWORD_KH));
		systemConfigBean.getEntity().setUploadApplynow(systemConfig.getConfig(UPLOAD_APPLYNOW));
		systemConfigBean.getEntity().setBannerCardType(systemConfig.getConfig(BANNER_CARDTYPE) != null ? Long.parseLong(systemConfig.getConfig(BANNER_CARDTYPE)) : 0L);
		systemConfigBean.getEntity().setBannerCardCategory(systemConfig.getConfig(BANNER_CATEGORY) != null ? Long.parseLong(systemConfig.getConfig(BANNER_CATEGORY)) : 0L);
		systemConfigBean.getEntity().setBannerQuickCompare(systemConfig.getConfig(BANNER_COMPARE_QUICK) != null ? Long.parseLong(systemConfig.getConfig(BANNER_COMPARE_QUICK)) : 0L);
		systemConfigBean.getEntity().setBannerFullCompare(systemConfig.getConfig(BANNER_COMPARE_FULL) != null ? Long.parseLong(systemConfig.getConfig(BANNER_COMPARE_FULL)) : 0L);
		systemConfigBean.getEntity().setBannerApplyForm(systemConfig.getConfig(BANNER_APPLY_FORM) != null ? Long.parseLong(systemConfig.getConfig(BANNER_APPLY_FORM)) : 0L);
		systemConfigBean.getEntity().setBannerRewardBenefit(systemConfig.getConfig(BANNER_REWARD_BENEFIT_BENEFIT) != null ?Long.parseLong(systemConfig.getConfig(BANNER_REWARD_BENEFIT_BENEFIT)) : 0L);
		systemConfigBean.getEntity().setBannerRewardBenefitPlatium(systemConfig.getConfig(BANNER_REWARD_BENEFIT_PLATIUM) != null ?Long.parseLong(systemConfig.getConfig(BANNER_REWARD_BENEFIT_PLATIUM)) : 0L);
		systemConfigBean.getEntity().setBannerRewardBenefitT365(systemConfig.getConfig(BANNER_REWARD_BENEFIT_T_365) != null ?Long.parseLong(systemConfig.getConfig(BANNER_REWARD_BENEFIT_T_365)) : 0L);
		systemConfigBean.getEntity().setBannerSendRequest(systemConfig.getConfig(BANNER_SEND_REQUEST) != null ? Long.parseLong(systemConfig.getConfig(BANNER_SEND_REQUEST)) : 0L);
		systemConfigBean.getEntity().setTimeReceive(systemConfig.getConfig(TIME_RECEIVE) != null ? Double.parseDouble(systemConfig.getConfig(TIME_RECEIVE)) : 0d);
		systemConfigBean.getEntity().setNumberSupport(systemConfig.getConfig(NUMBER_SUPPORT) != null ? Integer.parseInt(systemConfig.getConfig(NUMBER_SUPPORT)) : 0);
		systemConfigBean.getEntity().setConfigApplyForm(systemConfig.getConfig(CONFIG_APPLY_FORM) != null ? Integer.parseInt(systemConfig.getConfig(CONFIG_APPLY_FORM)) : 0);
		systemConfigBean.getEntity().setRedeemPath(systemConfig.getConfig(REDEEM_PATH));
		systemConfigBean.getEntity().setvTigerPath(systemConfig.getConfig(VTIGER_PATH));
		systemConfigBean.getEntity().setvTigerPathOther(systemConfig.getConfig(VTIGER_PATH_OTHER));
		systemConfigBean.getEntity().setAutoExportMgm(systemConfig.getConfig(AUTO_EXPORT_MGM));
		systemConfigBean.getEntity().setPushMessageFollowSchedule(systemConfig.getConfig(PUSH_MESSAGE_FOLLOW_SCHEDULE));
		systemConfigBean.getEntity().setMinTimePushmessage(systemConfig.getConfig(MIN_TIME_PUSHMESSAGE));
			
		model.addAttribute("bean", systemConfigBean);
		return "system.system_config.systemconfig";
	}

	// cap nhat lai du lieu config
	@RequestMapping(value = "/system_config", method = RequestMethod.POST)
	public String saveSystemConfig(@ModelAttribute(value = "bean") @Valid SystemConfigBean systemConfigBean,
			BindingResult bindingResult, Model model, Locale locale, HttpServletRequest request) {
		// ghi du lieu xuong config , kiem tra account chart ghi account no
		// xuong
		try {

			// if(StringUtils.isNotEmpty(systemConfig.getConfig(REPOSITORY_ROOT_ID))){
			// systemConfigBean.getEntity().setRepositoryRootId(systemConfig.getConfig(REPOSITORY_ROOT_ID));
			// }
			if (!bindingResult.hasErrors()) {
				if ((systemConfigBean.getEntity().getFormYear() == null ? 0 : systemConfigBean.getEntity()
						.getFormYear()) >= (systemConfigBean.getEntity().getToYear() == null ? 0 : systemConfigBean
						.getEntity().getToYear())) {
					systemConfigBean.addMessage(Message.ERROR,
							msgSrc.getMessage("msg.save.fail.formyeartoyear", null, locale));
				} else {
					Map<String, String> syMap = systemConfig.getConfigMap();
					syMap.put(PAGING_SIZE, systemConfigBean.getEntity().getPageRow());
					syMap.put(DATE_PATTERN, systemConfigBean.getEntity().getDate());
					syMap.put(LANGUAGE_DEFAULT, systemConfigBean.getEntity().getDefaultLanguage());
					// update locale resolver
					localeResolver.setDefaultLocale(new Locale(systemConfigBean.getEntity().getDefaultLanguage()));
					syMap.put(DEFAULT_CASH_ACCOUNT_NO, "");

					syMap.put(FROM_YEAR, String.valueOf(systemConfigBean.getEntity().getFormYear()));
					syMap.put(TO_YEAR, String.valueOf(systemConfigBean.getEntity().getToYear()));
					syMap.put(URL_DEFAULT, systemConfigBean.getEntity().getUrlDefault());

					syMap.put(EMAIL_DEFAULT, systemConfigBean.getEntity().getEmailDefault());
					syMap.put(EMAIL_DEFAULT_NAME, systemConfigBean.getEntity().getEmailDefaultName());
					syMap.put(EMAIL_HOST, systemConfigBean.getEntity().getEmailHost());
					syMap.put(EMAIL_PORT, systemConfigBean.getEntity().getEmailPort().toString());
					syMap.put(EMAIL_PASSWORD, systemConfigBean.getEntity().getEmailPassword());

					mailSender.setHost(systemConfigBean.getEntity().getEmailHost());
					mailSender.setPort(systemConfigBean.getEntity().getEmailPort());
					mailSender.setUsername(systemConfigBean.getEntity().getEmailDefault());
					mailSender.setPassword(systemConfigBean.getEntity().getEmailPassword());
					try{
						mailSender.getJavaMailProperties().setProperty("mail.smtp.ssl.trust", "*");
					}catch(Exception e){
						
					}

					syMap.put(EMAIL_OPTION, String.valueOf(systemConfigBean.getEntity().getEmailOption()));
					syMap.put(SCHEDULE_MAIL, systemConfigBean.getEntity().getEmailSchedule());
					syMap.put(CHECK_LIMIT_EMAIL, String
							.valueOf(systemConfigBean.getEntity().getCheckLimmitEmail() == null ? 0 : systemConfigBean
									.getEntity().getCheckLimmitEmail()));
					syMap.put(LIMIT_EMAIL, String.valueOf(systemConfigBean.getEntity().getLimmitEmail() == null ? ""
							: systemConfigBean.getEntity().getLimmitEmail()));

					syMap.put(UNUSEDLONGTIME, String
							.valueOf(systemConfigBean.getEntity().getNotUsedLongTime() == null ? "" : systemConfigBean
									.getEntity().getNotUsedLongTime()));

					syMap.put(REPOSITORY_ROOT_FOLDER, systemConfigBean.getEntity().getRepository());
					syMap.put(TEMP_FOLDER, systemConfigBean.getEntity().getTempfolder());
					syMap.put(USER_LDAP, systemConfigBean.getEntity().getUserldap());
					syMap.put(CONFIG_LDAP_DOMAIN, systemConfigBean.getEntity().getLdapdomain());
					syMap.put(CONFIG_LDAP_URL, systemConfigBean.getEntity().getLdapurl());
					syMap.put(CONFIG_LDAP_SEARCH, systemConfigBean.getEntity().getLdapsearch());
					syMap.put(CONFIG_ACTIVATECODEEXPIREDATE, systemConfigBean.getEntity().getActivateCodeExpiredDate());
					syMap.put(CONFIG_RESERTPASSWORLDEXPIREDDATE, systemConfigBean.getEntity().getResetPasswordExpiredDate());
					syMap.put(CONFIG_ACTIVATECODEDATE, systemConfigBean.getEntity().getActivateCodeDate());
					syMap.put(CONFIG_GETCODEDATE, systemConfigBean.getEntity().getGetCodeDate());
					syMap.put(DRIVERCLASS, systemConfigBean.getEntity().getDriverclass());
					syMap.put(JDBCURL, systemConfigBean.getEntity().getJdbcurl());
					syMap.put(USER_KH, systemConfigBean.getEntity().getUserkh());
					syMap.put(PASSWORD_KH, systemConfigBean.getEntity().getPassworldkh());
					syMap.put(UPLOAD_APPLYNOW, systemConfigBean.getEntity().getUploadApplynow());
					syMap.put(REDEEM_PATH, systemConfigBean.getEntity().getRedeemPath());
					syMap.put(VTIGER_PATH, systemConfigBean.getEntity().getvTigerPath());
					syMap.put(VTIGER_PATH_OTHER, systemConfigBean.getEntity().getvTigerPathOther());
					syMap.put(AUTO_EXPORT_MGM, (systemConfigBean.getEntity().getAutoExportMgm()==null)?"off":systemConfigBean.getEntity().getAutoExportMgm());
					syMap.put(PUSH_MESSAGE_FOLLOW_SCHEDULE, systemConfigBean.getEntity().getPushMessageFollowSchedule());
					syMap.put(MIN_TIME_PUSHMESSAGE, systemConfigBean.getEntity().getMinTimePushmessage());
					
					syMap.put(BANNER_CARDTYPE, String.valueOf(systemConfigBean.getEntity().getBannerCardType() != null ? systemConfigBean.getEntity().getBannerCardType() : 0));
					syMap.put(BANNER_CATEGORY, String.valueOf(systemConfigBean.getEntity().getBannerCardCategory() != null ? systemConfigBean.getEntity().getBannerCardCategory() : 0));
					syMap.put(BANNER_COMPARE_QUICK, String.valueOf(systemConfigBean.getEntity().getBannerQuickCompare() != null ? systemConfigBean.getEntity().getBannerQuickCompare() : 0));
					syMap.put(BANNER_COMPARE_FULL, String.valueOf(systemConfigBean.getEntity().getBannerFullCompare() != null ? systemConfigBean.getEntity().getBannerFullCompare() : 0));
					syMap.put(BANNER_APPLY_FORM, String.valueOf(systemConfigBean.getEntity().getBannerApplyForm() != null ? systemConfigBean.getEntity().getBannerApplyForm() : 0));
					syMap.put(BANNER_REWARD_BENEFIT_BENEFIT, String.valueOf(systemConfigBean.getEntity().getBannerRewardBenefit() != null ? systemConfigBean.getEntity().getBannerRewardBenefit() : 0));
					syMap.put(BANNER_REWARD_BENEFIT_PLATIUM, String.valueOf(systemConfigBean.getEntity().getBannerRewardBenefitPlatium() != null ? systemConfigBean.getEntity().getBannerRewardBenefitPlatium() : 0));
					syMap.put(BANNER_REWARD_BENEFIT_T_365, String.valueOf(systemConfigBean.getEntity().getBannerRewardBenefitT365() != null ? systemConfigBean.getEntity().getBannerRewardBenefitT365() : 0));
					syMap.put(BANNER_SEND_REQUEST, String.valueOf(systemConfigBean.getEntity().getBannerSendRequest() != null ? systemConfigBean.getEntity().getBannerSendRequest() : 0));
					syMap.put(TIME_RECEIVE, String.valueOf(systemConfigBean.getEntity().getTimeReceive() != null ? systemConfigBean.getEntity().getTimeReceive() : 0));
					syMap.put(NUMBER_SUPPORT, String.valueOf(systemConfigBean.getEntity().getNumberSupport() != null ? systemConfigBean.getEntity().getNumberSupport() : 0));
					syMap.put(CONFIG_APPLY_FORM, String.valueOf(systemConfigBean.getEntity().getConfigApplyForm() != null ? systemConfigBean.getEntity().getConfigApplyForm() : 0));
					
					if(systemSettingService.findKey(PASSWORD_LDAP) == null || systemSettingService.findKey(PASSWORD_LDAP).equals("")){
						if(StringUtils.isNotEmpty(systemConfigBean.getEntity().getPswldap())){
							syMap.put(PASSWORD_LDAP, systemConfigBean.getEntity().getPswldap());
						}else {
							systemConfigBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.ldap.password.must.input", null, locale));
							model.addAttribute("bean", systemConfigBean);
							return "system.system_config.systemconfig";
						}
					}else {
						if(StringUtils.isNotEmpty(systemConfigBean.getEntity().getPswldap())){
							syMap.put(PASSWORD_LDAP, systemConfigBean.getEntity().getPswldap());
						}
					}
					// config process

					systemConfig.setConfigMap(syMap);
					for (String key : syMap.keySet()) {
						SystemSetting systemSetting = systemSettingService.findKey(key);
						if (systemSetting == null) {
							systemSetting = new SystemSetting();
							systemSetting.setKey(key);
						}
						try {
							systemSetting.setValue(syMap.get(key));
							systemSettingService.saveOrUpdate(systemSetting);

						} catch (Exception e) {
						}
					}

					systemConfigBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				}
			} else {
				systemConfigBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
				model.addAttribute("bean", systemConfigBean);
				return "system.system_config.systemconfig";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			systemConfigBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
		}
		model.addAttribute("bean", systemConfigBean);
		return "system.system_config.systemconfig";
	}

	
}
