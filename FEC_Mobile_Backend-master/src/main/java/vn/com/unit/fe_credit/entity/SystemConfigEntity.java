package vn.com.unit.fe_credit.entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

public class SystemConfigEntity {
	@NotNull
	private String pageRow;
	@Valid
	@Size(min = 1, max = 255)
	@NotNull
	private String date;
	@Valid
	@Size(min = 1, max = 255)
	@NotNull
	private String defaultLanguage;
	@Valid
	@NotNull
	private Long formYear;
	@Valid
	@NotNull
	private Long toYear;
	@Valid
	@NotEmpty
	private String emailDefault;
	@Valid
	@NotEmpty
	private String emailDefaultName;
	@Valid
	@NotEmpty
	private String emailHost;
	@Valid
	@NotNull
	private Integer emailPort;

	@Valid
	@NotEmpty
	private String emailPassword;

	@Valid
	@NotEmpty
	private String repository;

	@Valid
	@NotEmpty
	private String tempfolder;
	
	@Valid
	@NotEmpty
	private String uploadApplynow; 
	
	
	@Valid
	@NotEmpty
	private String urlDefault;
	private Integer emailOption;
	private String emailSchedule;
	private Integer checkLimmitEmail;
	private Integer limmitEmail;
	private Integer notUsedLongTime;	
	private String userldap;
	private String pswldap;
	private Long bannerCardType;
	private Long bannerCardCategory;
	private Long bannerQuickCompare;
	private Long bannerFullCompare;
	private Long bannerApplyForm;
	private Long bannerRewardBenefit;
	private Long bannerRewardBenefitPlatium;
	private Long bannerRewardBenefitT365;
	private Long bannerSendRequest;
	private Double timeReceive;
	private Integer numberSupport;
	private Integer configApplyForm;

	private String ldapdomain;
	private String ldapurl;
	private String ldapsearch;
	@Valid
	@Size(min = 1, max = 2)
	@NotNull
	private String activateCodeExpiredDate;
	@Valid
	@Size(min = 1, max = 2)
	@NotNull
	private String resetPasswordExpiredDate;
	@Valid
	@Size(min = 1, max = 2)
	@NotNull
	private String activateCodeDate;
	
	@Valid
	@Size(min = 1, max = 2)
	@NotNull
	private String getCodeDate;
	
	@Valid
	private String driverclass;
	
	@Valid
	private String jdbcurl;
	
	@Valid
	private String userkh;
	
	@Valid
	private String passworldkh;
	
	@Valid
	private String redeemPath;
	
	@Valid
	private String vTigerPath;
	
	@Valid
	private String vTigerPathOther;
	
	@Valid
	private String autoExportMgm;
	
	@Valid
	private String pushMessageFollowSchedule;
	
	@Valid
	private String minTimePushmessage;
	
	public String getMinTimePushmessage() {
		return minTimePushmessage;
	}

	public void setMinTimePushmessage(String minTimePushmessage) {
		this.minTimePushmessage = minTimePushmessage;
	}

	public String getPushMessageFollowSchedule() {
		return pushMessageFollowSchedule;
	}

	public void setPushMessageFollowSchedule(String pushMessageFollowSchedule) {
		this.pushMessageFollowSchedule = pushMessageFollowSchedule;
	}

	public String getAutoExportMgm() {
		return autoExportMgm;
	}

	public void setAutoExportMgm(String autoExportMgm) {
		this.autoExportMgm = autoExportMgm;
	}

	public String getvTigerPathOther() {
		return vTigerPathOther;
	}

	public void setvTigerPathOther(String vTigerPathOther) {
		this.vTigerPathOther = vTigerPathOther;
	}

	public String getRedeemPath() {
		return redeemPath;
	}

	public void setRedeemPath(String redeemPath) {
		this.redeemPath = redeemPath;
	}

	public String getvTigerPath() {
		return vTigerPath;
	}

	public void setvTigerPath(String vTigerPath) {
		this.vTigerPath = vTigerPath;
	}

	public String getLdapdomain() {
		return ldapdomain;
	}

	public void setLdapdomain(String ldapdomain) {
		this.ldapdomain = ldapdomain;
	}

	public String getLdapurl() {
		return ldapurl;
	}

	public void setLdapurl(String ldapurl) {
		this.ldapurl = ldapurl;
	}

	public String getLdapsearch() {
		return ldapsearch;
	}

	public void setLdapsearch(String ldapsearch) {
		this.ldapsearch = ldapsearch;
	}

	public Integer getConfigApplyForm() {
		return configApplyForm;
	}

	public void setConfigApplyForm(Integer configApplyForm) {
		this.configApplyForm = configApplyForm;
	}

	public Long getBannerCardType() {
		return bannerCardType;
	}

	public void setBannerCardType(Long bannerCardType) {
		this.bannerCardType = bannerCardType;
	}

	public Long getBannerCardCategory() {
		return bannerCardCategory;
	}

	public void setBannerCardCategory(Long bannerCardCategory) {
		this.bannerCardCategory = bannerCardCategory;
	}

	public Long getBannerQuickCompare() {
		return bannerQuickCompare;
	}

	public void setBannerQuickCompare(Long bannerQuickCompare) {
		this.bannerQuickCompare = bannerQuickCompare;
	}

	public Long getBannerFullCompare() {
		return bannerFullCompare;
	}

	public void setBannerFullCompare(Long bannerFullCompare) {
		this.bannerFullCompare = bannerFullCompare;
	}

	public Long getBannerApplyForm() {
		return bannerApplyForm;
	}

	public void setBannerApplyForm(Long bannerApplyForm) {
		this.bannerApplyForm = bannerApplyForm;
	}

	public Long getBannerRewardBenefit() {
		return bannerRewardBenefit;
	}

	public void setBannerRewardBenefit(Long bannerRewardBenefit) {
		this.bannerRewardBenefit = bannerRewardBenefit;
	}

	public Long getBannerSendRequest() {
		return bannerSendRequest;
	}

	public void setBannerSendRequest(Long bannerSendRequest) {
		this.bannerSendRequest = bannerSendRequest;
	}

	public Double getTimeReceive() {
		return timeReceive;
	}

	public void setTimeReceive(Double timeReceive) {
		this.timeReceive = timeReceive;
	}

	public Integer getNumberSupport() {
		return numberSupport;
	}

	public void setNumberSupport(Integer numberSupport) {
		this.numberSupport = numberSupport;
	}

	public Integer getCheckLimmitEmail() {
		return checkLimmitEmail;
	}

	public void setCheckLimmitEmail(Integer checkLimmitEmail) {
		this.checkLimmitEmail = checkLimmitEmail;
	}

	public Integer getEmailOption() {
		return emailOption;
	}

	public void setEmailOption(Integer emailOption) {
		this.emailOption = emailOption;
	}

	public String getEmailSchedule() {
		return emailSchedule;
	}

	public void setEmailSchedule(String emailSchedule) {
		this.emailSchedule = emailSchedule;
	}

	public Integer getLimmitEmail() {
		return limmitEmail;
	}

	public void setLimmitEmail(Integer limmitEmail) {
		this.limmitEmail = limmitEmail;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getPageRow() {
		return pageRow;
	}

	public void setPageRow(String pageRow) {
		this.pageRow = pageRow;
	}

	public Long getFormYear() {
		return formYear;
	}

	public void setFormYear(Long formYear) {
		this.formYear = formYear;
	}

	public Long getToYear() {
		return toYear;
	}

	public void setToYear(Long toYear) {
		this.toYear = toYear;
	}

	public String getEmailDefault() {
		return emailDefault;
	}

	public void setEmailDefault(String emailDefault) {
		this.emailDefault = emailDefault;
	}

	public String getEmailDefaultName() {
		return emailDefaultName;
	}

	public void setEmailDefaultName(String emailDefaultName) {
		this.emailDefaultName = emailDefaultName;
	}

	public String getRepository() {
		return repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	public String getUrlDefault() {
		return urlDefault;
	}

	public void setUrlDefault(String urlDefault) {
		this.urlDefault = urlDefault;
	}

	public String getTempfolder() {
		return tempfolder;
	}

	public void setTempfolder(String tempfolder) {
		this.tempfolder = tempfolder;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public Integer getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(Integer emailPort) {
		this.emailPort = emailPort;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public Integer getNotUsedLongTime() {
		return notUsedLongTime;
	}

	public void setNotUsedLongTime(Integer notUsedLongTime) {
		this.notUsedLongTime = notUsedLongTime;
	}

	public String getUserldap() {
		return userldap;
	}

	public void setUserldap(String userldap) {
		this.userldap = userldap;
	}

	public String getPswldap() {
		return pswldap;
	}

	public void setPswldap(String pswldap) {
		this.pswldap = pswldap;
	}

	public Long getBannerRewardBenefitPlatium() {
		return bannerRewardBenefitPlatium;
	}

	public void setBannerRewardBenefitPlatium(Long bannerRewardBenefitPlatium) {
		this.bannerRewardBenefitPlatium = bannerRewardBenefitPlatium;
	}

	public Long getBannerRewardBenefitT365() {
		return bannerRewardBenefitT365;
	}

	public void setBannerRewardBenefitT365(Long bannerRewardBenefitT365) {
		this.bannerRewardBenefitT365 = bannerRewardBenefitT365;
	}

	public String getActivateCodeExpiredDate() {
		return activateCodeExpiredDate;
	}

	public void setActivateCodeExpiredDate(String activateCodeExpiredDate) {
		this.activateCodeExpiredDate = activateCodeExpiredDate;
	}

	public String getResetPasswordExpiredDate() {
		return resetPasswordExpiredDate;
	}

	public void setResetPasswordExpiredDate(String resetPasswordExpiredDate) {
		this.resetPasswordExpiredDate = resetPasswordExpiredDate;
	}

	public String getActivateCodeDate() {
		return activateCodeDate;
	}

	public void setActivateCodeDate(String activateCodeDate) {
		this.activateCodeDate = activateCodeDate;
	}

	public String getGetCodeDate() {
		return getCodeDate;
	}

	public void setGetCodeDate(String getCodeDate) {
		this.getCodeDate = getCodeDate;
	}

	public String getDriverclass() {
		return driverclass;
	}

	public void setDriverclass(String driverclass) {
		this.driverclass = driverclass;
	}

	public String getJdbcurl() {
		return jdbcurl;
	}

	public void setJdbcurl(String jdbcurl) {
		this.jdbcurl = jdbcurl;
	}

	public String getUserkh() {
		return userkh;
	}

	public void setUserkh(String userkh) {
		this.userkh = userkh;
	}

	public String getPassworldkh() {
		return passworldkh;
	}

	public void setPassworldkh(String passworldkh) {
		this.passworldkh = passworldkh;
	}

	public String getUploadApplynow() {
		return uploadApplynow;
	}

	public void setUploadApplynow(String uploadApplynow) {
		this.uploadApplynow = uploadApplynow;
	}
	
}
