package vn.com.unit.fe_credit.service.impl.v1;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.config.SystemConfig.Gender;
import vn.com.unit.fe_credit.config.SystemConfig.Status;
import vn.com.unit.fe_credit.config.SystemConfig.UserAccessFlag;
import vn.com.unit.fe_credit.dao.AccountDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.AccountTeam;
import vn.com.unit.fe_credit.entity.AccountTeamPK;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.StatusTableService;
import vn.com.unit.fe_credit.service.TeamService;
import vn.com.unit.fe_credit.service.v1.UserService;

@Service("userService")
@Transactional/*(readOnly = true)*/
public class UserServiceImpl implements UserService {
	
	@Autowired
	AccountDao accountDao;
	
	@Autowired
	SystemConfig systemConfig;
	
	@Autowired
	private MessageSource msgSrc;
	
	@Autowired
	TeamService teamService;
	
	@Autowired
	AccountTeamService accountTeamService;
	
	@Autowired
	StatusTableService statusTableService;

	
	@Autowired
	private UserProfile userProfile;
	
	public UserServiceImpl() {
		super();
	}

	
	Logger logger = Logger.getLogger(UserServiceImpl.class.getName());
	@Override
	public Model getUsersForPost(Model model, AccountBean accountBean, HttpServletRequest request, Locale locale) {
		// TODO Auto-generated method stub
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		accountBean.setLimit(pagesize);
		accountBean.setStatus(1);
		int success = 0;
		List<Object[]> result = accountDao.search(accountBean);
		Integer total = accountDao.countSearch(accountBean);
		if (StringUtils.isNotEmpty(request.getParameter("action")) && request.getParameter("action").equals("delete")) {
			accountBean.clearMessages();
			accountBean.addMessage(Message.INFO, msgSrc.getMessage("msg.have.no.items.deleted", null, locale).replace("{0}", String.valueOf(success)));
		}
		if (CollectionUtils.isEmpty(result)) {
			accountBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		accountBean.setTotal(total);
		model.addAttribute("bean", accountBean);
		model.addAttribute("result", result);
		return model;
	}

	@Override
	public Model getUsersForGet(Model model, AccountBean accountBean, HttpServletRequest request, Locale locale, String message) {
		// TODO Auto-generated method stub
		if (message != null && message.equals("success")) {
			accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		accountBean.setLimit(pagesize);
		accountBean.setStatus(1);
		accountBean.setEntity(new Account());
		List<Object[]> result = accountDao.search(accountBean);
		Integer total = accountDao.countSearch(accountBean);
		List<String> accountStatusList = new ArrayList<String>();

		for (Object[] obj : result) {
			// check locked user
			accountStatusList.add(msgSrc.getMessage("locked.normal", null, locale));
		}
		// set status List
		accountBean.setUserStatusList(accountStatusList);
		if (CollectionUtils.isEmpty(result)) {
			accountBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}

		accountBean.setTotal(new Integer(total + ""));
		model.addAttribute("bean", accountBean);
		model.addAttribute("result", result);
		
		logger.info("User List!!!!!!");
		return model;
	}

	@Transactional
	@Override
	public Model addOrEditForGet(Model model, HttpServletRequest request, Long id,AccountBean accountBean) {
		// TODO Auto-generated method stub
		Account account;
		List<Team> teamListLeft = null;
		List<Team> teamListRight = null;
		List<Long> projectIds = new ArrayList<Long>();
		//AccountBean accountBean = new AccountBean();	
		List<UserAccessFlag> allAccessFlags = new ArrayList<UserAccessFlag>(EnumSet.allOf(UserAccessFlag.class));
		accountBean.setUserAccessFlag(allAccessFlags);
		List<Gender> allGender = new ArrayList<Gender>(EnumSet.allOf(Gender.class));
		accountBean.setGenderList(allGender);
		List<Status> allStatus = new ArrayList<Status>(EnumSet.allOf(Status.class));
		accountBean.setStatusList(allStatus);
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			account = accountDao.find(id);
			// check locked user
			model.addAttribute("lockedaccount", false);
			teamListRight = teamService.findTeamNotbyAccountID(id);
			teamListLeft = teamService.findTeambyAccountID(id);
		} else {
			account = new Account();
			teamListRight = teamService.findAll();
		}
		accountBean.setEntity(account);
		accountBean.setLeftTeeprocurement(new ArrayList<Team>(account.getTeams()));
		accountBean.setProjectIds(projectIds);

		model.addAttribute("bean", accountBean);
		model.addAttribute("right", teamListRight);
		model.addAttribute("left", teamListLeft);
		return model;
	}

	@Transactional
	@Override
	public Model addOrEditForPost(Model model, HttpServletRequest request, AccountBean accountBean, BindingResult bindingResult, List<Long> chkTeamLeft, Locale locale, RedirectAttributes redirectAttributes) {
		// TODO Auto-generated method stub
		Account account = new Account();
		boolean newUser = false;
		List<Team> teamListLeft = null;
		List<Team> teamListRight = null;
		
		if (accountBean.getEntity().getId() != null) {
			account = accountDao.find(accountBean.getEntity().getId());
			model.addAttribute("lockedaccount", false);
			account.getStatusTable().setStatus_tableId(accountBean.getEntity().getStatusTable().getStatus_tableId());
		} else {
			newUser = true;
			account.setStatusTable(statusTableService.findActive());
			teamListRight = teamService.findAll();
			model.addAttribute("right", teamListRight);
			model.addAttribute("left", teamListLeft);
		}
		
		List<UserAccessFlag> allAccessFlags = new ArrayList<UserAccessFlag>(EnumSet.allOf(UserAccessFlag.class));
		accountBean.setUserAccessFlag(allAccessFlags);
		
		List<Gender> allGender = new ArrayList<Gender>(EnumSet.allOf(Gender.class));
		accountBean.setGenderList(allGender);
		
		List<Status> allStatus = new ArrayList<Status>(EnumSet.allOf(Status.class));
		accountBean.setStatusList(allStatus);
		
		account = createAccount(accountBean.getEntity(),account);		
	
		accountBean.setEntity(account);
		if (StringUtils.isEmpty(accountBean.getEntity().getEmail())) {
			bindingResult.rejectValue("entity.email", "javax.validation.constraints.NotNull.message");
		} else {
			if (StringUtils.isNotEmpty(accountBean.getEntity().getEmail())) {
				String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
				if (!accountBean.getEntity().getEmail().matches(EMAIL_REGEX)) {
					bindingResult.rejectValue("entity.email", "javax.validation.constraints.NotNull.message");
				}
			}
		}	
		
		if (StringUtils.isEmpty(accountBean.getEntity().getUserCode())) {
			bindingResult.rejectValue("entity.userCode", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getFullName())) {
			bindingResult.rejectValue("entity.fullName", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getMobile())) {
			bindingResult.rejectValue("entity.mobile", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getLineManager())) {
			bindingResult.rejectValue("entity.lineManager", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getRegionCode())) {
			bindingResult.rejectValue("entity.regionCode", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getProvinceCode())) {
			bindingResult.rejectValue("entity.provinceCode", "javax.validation.constraints.NotNull.message");
		}
		/*if (accountBean.getEntity().getAccessFlag() == null) {
			bindingResult.rejectValue("entity.accessFlag", "javax.validation.constraints.NotNull.message");
		}*/
		if (accountBean.getEntity().getBirthday() == null) {
			bindingResult.rejectValue("entity.birthday", "javax.validation.constraints.NotNull.message");
		}
		if (accountBean.getEntity().getJoiningDate() == null) {
			bindingResult.rejectValue("entity.joiningDate", "javax.validation.constraints.NotNull.message");
		}
		
		if (StringUtils.isEmpty(accountBean.getEntity().getStatus())) {
			bindingResult.rejectValue("entity.status", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getGender() )) {
			bindingResult.rejectValue("entity.gender", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getOnBoardPosition())) {
			bindingResult.rejectValue("entity.onBoardPosition", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getPosition())) {
			bindingResult.rejectValue("entity.position", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getTypeOfSalesAgent())) {
			bindingResult.rejectValue("entity.typeOfSalesAgent", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getFirstName())) {
			bindingResult.rejectValue("entity.firstName", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getLastName())) {
			bindingResult.rejectValue("entity.lastName", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getOfficeNumber())) {
			bindingResult.rejectValue("entity.officeNumber", "javax.validation.constraints.NotNull.message");
		}
		
		
		List<Team> teamLeft = null;
		List<Team> teamRight = null;
		if (chkTeamLeft != null) {
			teamLeft = teamService.findByIds(chkTeamLeft);
			teamRight = teamService.findByNotInIds(chkTeamLeft);
		}else{
			teamLeft = teamService.findTeambyAccountID(accountBean.getEntity().getId());
			teamRight =teamService.findTeamNotbyAccountID(accountBean.getEntity().getId());
		}

		if (!bindingResult.hasErrors()) {

			try {
				

				if (chkTeamLeft != null) {
					teamLeft = teamService.findByIds(chkTeamLeft);
					accountBean.getEntity().setTeams(new HashSet<Team>(teamLeft));
				} else {
					accountBean.getEntity().setTeams(new HashSet<Team>());
				}
				// Saving Account into Users Table
				Long checkid = accountBean.getEntity().getId();				
				accountDao.save(account);				
				System.out.println("Account Saved!!!!");
				// Update to UserTeam
				Long accountId = accountBean.getEntity().getId();
				if (!newUser) {
					accountTeamService.deleteAccountTeamByAccId(accountId);
				}
				for (Team teamleft : accountBean.getEntity().getTeams()) {
					AccountTeamPK accountTeamPk = new AccountTeamPK();
					accountTeamPk.setAccountId(accountId);
					accountTeamPk.setTeamId(teamleft.getId());
					AccountTeam accountTeam = new AccountTeam();
					accountTeam.setPk(accountTeamPk);
					accountTeamService.saveAccountTeam(accountTeam);
				}
				
				// logger
				/*if (checkid != null)
					activityLogService.saveActivityLog(systemConfig.EDIT_USER, systemConfig.USER, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());
				else
					activityLogService.saveActivityLog(systemConfig.ADD_NEW_USER, systemConfig.USER, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());*/
				// end approval info
				accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", accountBean);
				teamListLeft = teamService.findTeambyAccountID(accountBean.getEntity().getId());
				teamListRight = teamService.findTeamNotbyAccountID(accountBean.getEntity().getId());
				model.addAttribute("bean", accountBean);
				model.addAttribute("right", teamListRight);
				model.addAttribute("left", teamListLeft);
				model.addAttribute("success", true);
				model.addAttribute("editAjax", "system.user.edit.ajax");
				return model;
			} catch (Exception ex) {
				accountBean.setIsSavedFail(true);
				ex.printStackTrace();
				accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		} else {
			accountBean.setIsSavedFail(true);
			if (bindingResult.getFieldError("entity") != null) {
				accountBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			} else {
				accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
			model.addAttribute("editAjax", "error");
		}
		accountBean.setIsSavedFail(false);
		teamListLeft = teamService.findTeambyAccountID(accountBean.getEntity().getId());
		 teamListRight = teamService.findTeamNotbyAccountID(accountBean.getEntity().getId());
		if (accountBean.getEntity().getId() == null) {
			model.addAttribute("right", teamRight);
			model.addAttribute("left", teamLeft);
		} else {
			model.addAttribute("right", teamListRight);
			model.addAttribute("left", teamListLeft);
		}
		model.addAttribute("bean", accountBean);
		return model;
	}

	private Account createAccount(Account entity,Account account) {
		// TODO Auto-generated method stub
		
		account.setUserCode(entity.getUserCode());
		account.setFullName(entity.getFullName());
		account.setBirthday(entity.getBirthday());
		if(null != entity.getEmail())
			account.setEmail(entity.getEmail().trim().toLowerCase());
		account.setMobile(entity.getMobile());
		account.setDepartmentId(entity.getDepartmentId());
		account.setRegionCode(entity.getRegionCode());
		account.setProvinceCode(entity.getProvinceCode());
		account.setJoiningDate(entity.getJoiningDate());
		account.setLineManager(entity.getLineManager());
		account.setAccessFlag(entity.getAccessFlag());
			
		account.setStatus(entity.getStatus());
		account.setGender(entity.getGender());
		account.setOnBoardPosition(entity.getOnBoardPosition());
		account.setPosition(entity.getPosition());
		account.setTypeOfSalesAgent(entity.getTypeOfSalesAgent());
		account.setFirstName(entity.getFirstName());
		account.setLastName(entity.getLastName());
		account.setLanguage(entity.getLanguage());
		account.setOfficeNumber(entity.getOfficeNumber());
		return account;
	}

	@Transactional
	@Override
	public Model deleteUserById(Model model, Long id, Locale locale) {
		// TODO Auto-generated method stub
		AccountBean accountBean = new AccountBean();
		if(null != teamService.findTeambyAccountID(id)){
			accountBean.setIsSavedFail(true);			
			accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
		}
		//userDao.removeById(id);
		return model;
	}
	
	@Override
	public WebDataBinder getBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		// TODO Auto-generated method stub
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
		return binder;
	}


	@Override
	public Model uploadExcelData(Model model, Locale locale, MultipartFile excelfile) {
		// TODO Auto-generated method stub
		String successMessage = msgSrc.getMessage("success.message", null, locale);
		int i = 1;
		try {
			List<Account> accounts = new ArrayList<Account>();			
			XSSFWorkbook workbook = new XSSFWorkbook(excelfile.getInputStream());
			XSSFSheet userSheet = workbook.getSheetAt(0);
			Boolean blankSheetFlag = false;
			XSSFRow row = userSheet.getRow(1);
			while (i <= userSheet.getLastRowNum()) {
				row = userSheet.getRow(i++);
				if(row != null && row.getCell(4) != null && row.getCell(0) != null){
					if(!(row.getCell(4).getStringCellValue().isEmpty() && row.getCell(0).getStringCellValue().isEmpty())){
						try {						
							Account account = createUser(row, locale);
							if(!blankSheetFlag){
								blankSheetFlag = true;								
							}
							if(account.getErrorMessage() == null)
								account.setErrorMessage("");
							List<Account> accountForEmail = accountDao.findByEmail(account.getEmail());
							List<Account> accountForUserCode = accountDao.findByUserCode(account.getUserCode());
							if(!accountForEmail.isEmpty()){
								accounts.add(account);
								account.setErrorMessage(msgSrc.getMessage("email.already.exist", null, locale));
							}else if(!accountForUserCode.isEmpty()){
								accounts.add(account);
								account.setErrorMessage(msgSrc.getMessage("usercode.already.exist", null, locale));
							}else if(account.getTeams() == null || !account.getErrorMessage().isEmpty())
								accounts.add(account);
							else{
								try{
									accountDao.save(account);
								}catch(Exception e){
									logger.info("Exception Occured during save data : "+e.getMessage());
									account.setErrorMessage(msgSrc.getMessage("system.error", null, locale));
									accounts.add(account);
								}
							}								
						} catch (Exception e) {
							logger.info("Exception Occured at Row No : "+i + ", Exception : "+e.getMessage());
							successMessage= "Exception Occured at Row No : "+i + ", Exception : "+e.getMessage();
						}
					}
				}
			}			
			workbook.close();
			model.addAttribute("accounts", accounts);			
		} catch (Exception e) {			
			e.printStackTrace();
			if(e instanceof NullPointerException)
			successMessage= msgSrc.getMessage("user.email.not.empty", null, locale);
		}
		model.addAttribute("successMessage", successMessage);
		return model;
	}


	private Account createUser(XSSFRow row, Locale locale) {
		// TODO Auto-generated method stub
		Account account = new Account();
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
		try {
			account.setBirthday(sdf.parse(sdf.format(row.getCell(3).getDateCellValue())));
		}catch(Exception e){
			
			account.setErrorMessage(msgSrc.getMessage("invalid.birthday", null, locale));
		}
		try { 
			if(null != row.getCell(4).getStringCellValue() && !row.getCell(4).getStringCellValue().isEmpty())
				account.setEmail(row.getCell(4).getStringCellValue().trim().toLowerCase());
			if(!row.getCell(4).getStringCellValue().isEmpty()) {
				String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(account.getEmail());
				if(!matcher.matches())
					account.setErrorMessage(msgSrc.getMessage("invalid.email", null, locale));
			}
			
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.email", null, locale));
		}
		try { 
			account.setJoiningDate(sdf.parse(sdf.format(row.getCell(7).getDateCellValue())));
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.joining.date", null, locale));
		}
		try { 
			account.setFullName(row.getCell(1).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.full.name", null, locale));
		}
		try { 
			account.setMobile(row.getCell(2).getStringCellValue());
			logger.info(account.getUserCode() + "  :::  "+ row.getCell(2).getStringCellValue());
			System.out.println("Mobile Number : "+row.getCell(2).getStringCellValue());
			if(!row.getCell(2).getStringCellValue().isEmpty()){
				Pattern pattern = Pattern.compile(".*[^0-9].*");
				if(pattern.matcher(account.getMobile()).matches())
					account.setErrorMessage(msgSrc.getMessage("invalid.mobile", null, locale));
			}
		}catch(Exception e){
			logger.info("Mobile Number Exception : "+e);
			account.setErrorMessage(msgSrc.getMessage("invalid.mobile", null, locale));
		}
		try {
			if(null != row.getCell(8).getStringCellValue() && !row.getCell(8).getStringCellValue().isEmpty())
				account.setLineManager(row.getCell(8).getStringCellValue().trim().toLowerCase());
			if(!row.getCell(8).getStringCellValue().isEmpty()){
				String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(account.getLineManager());
				if(!matcher.matches()){
					account.setErrorMessage(msgSrc.getMessage("invalid.line.manager.email", null, locale));
				}
			}
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.line.manager.email", null, locale));
		}
		try { 
			account.setProvinceCode(row.getCell(6).getStringCellValue());
			account.setRegionCode(row.getCell(5).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.province.region.code", null, locale));
		}
		try {
			if(!row.getCell(10).getStringCellValue().isEmpty()){
				if(row.getCell(10).getStringCellValue().equals(Status.ACTIVE.getDesc()) || row.getCell(10).getStringCellValue().equals(Status.INACTIVE.getDesc()))
					account.setStatus(row.getCell(10).getStringCellValue());
				else
					account.setErrorMessage(msgSrc.getMessage("invalid.status.active.inactive", null, locale));
			}
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.status", null, locale));
		}
		try {
			account.setGender(row.getCell(11).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.gender", null, locale));
		}
		try {
			account.setFirstName(row.getCell(12).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.first.name", null, locale));
		}
		try {
			account.setLastName(row.getCell(13).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.last.name", null, locale));
		}
		try {
			account.setOnBoardPosition(row.getCell(14).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.on.board.position", null, locale));
		}
		try {
			account.setPosition(row.getCell(15).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.position", null, locale));
		}
		try {
			account.setTypeOfSalesAgent(row.getCell(16).getStringCellValue());
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.type.of.sales.agent", null, locale));
		}
		try {
			account.setOfficeNumber(row.getCell(17).getStringCellValue());
			if(!row.getCell(17).getStringCellValue().isEmpty()){
				Pattern pattern = Pattern.compile(".*[^0-9].*");
				if(pattern.matcher(account.getOfficeNumber()).matches())
					account.setErrorMessage(msgSrc.getMessage("invalid.office.number", null, locale));
			}
		}catch(Exception e){
			account.setErrorMessage(msgSrc.getMessage("invalid.office.number", null, locale));
		}
		
		account.setEnabled(true);
		account.setStatusTable(statusTableService.findActive());
		
		Set<Team> teams = new HashSet<Team>();
		Team team = teamService.findById((long) row.getCell(9).getNumericCellValue());
		if(team == null){
			account.setTeams(null);
			account.setErrorMessage(msgSrc.getMessage("team.not.present", null, locale));
		}else{
			teams.add(team);
			account.setTeams(teams);
		}		
		
		account.setUserCode(row.getCell(0).getStringCellValue().trim());
		account.setCreated_date(new Date());
		if(null != userProfile.getAccount().getUsername())
		account.setCreated_by(userProfile.getAccount().getUsername());
		return account;
	}

	@Override
	public Model unlockUser(Model model,String email,AccountBean accountBean, Locale locale) {
		int updateResult = accountDao.unlockUser(email);
		accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		System.out.println(" unlockUser  "+updateResult);		
		logger.info("unlock User result"+updateResult);
		return model;
	}

	@Override
	public Model clearDevice(Model model,String email,AccountBean accountBean, Locale locale,String appId) {
		int result =accountDao.clearDevice(email,appId);
		System.out.println("clear Device result"+result);
		logger.info("clear Device result"+result);
		accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		model.addAttribute("success", true);
		return model;
	}


}
