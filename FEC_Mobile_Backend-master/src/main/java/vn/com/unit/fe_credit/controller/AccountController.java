package vn.com.unit.fe_credit.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.binding.DoubleEditor;
import vn.com.unit.fe_credit.bean.AccountBean;
import vn.com.unit.fe_credit.bean.DepartmentTreeBean;
import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.LDAPSyn;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.AccountTeam;
import vn.com.unit.fe_credit.entity.AccountTeamPK;
import vn.com.unit.fe_credit.entity.StatusTable;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.AccountService;
import vn.com.unit.fe_credit.service.AccountTeamService;
import vn.com.unit.fe_credit.service.ActivityLogService;
import vn.com.unit.fe_credit.service.DepartmentService;
import vn.com.unit.fe_credit.service.RoleService;
import vn.com.unit.fe_credit.service.StatusTableService;
import vn.com.unit.fe_credit.service.TeamService;
import vn.com.unit.fe_credit.utils.Utils;

@Controller
@RequestMapping("/system")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
	RoleService roleService;

	@Autowired
	AccountTeamService accountTeamService;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	UserProfile userProfile;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	ActivityLogService activityLogService;

	@Autowired
	StatusTableService statusTableService;

	@InitBinder
	public void dateBinder(WebDataBinder binder, Locale locale, HttpServletRequest request) {
		binder.setAutoGrowCollectionLimit(10000);
		// The date format to parse or output your dates
		SimpleDateFormat dateFormat = new SimpleDateFormat((String) request.getSession().getAttribute("formatDate"));
		// Create a new CustomDateEditor
		CustomDateEditor editor = new CustomDateEditor(dateFormat, true);
		// Register it as custom editor for the Date type
		binder.registerCustomEditor(Date.class, editor);

		binder.registerCustomEditor(Double.class, new DoubleEditor(locale, "#,###.##"));
	}

	@RequestMapping(value = "/account/list", method = RequestMethod.POST)
	public String listPost(@ModelAttribute(value = "bean") AccountBean accountBean, Model model, HttpServletRequest request, Locale locale)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		accountBean.setLimit(pagesize);
		// accountBean.setEntity(new Account());

		int success = 0;
		accountBean.setListStatusTable(statusTableService.findAllex());
		// List<Object[]> result = accountService.search(accountBean);
		List<Object[]> result = accountService.search(accountBean);
		Integer total = accountService.countSearch(accountBean);
		accountBean.setListStatusTable(statusTableService.findAllex());
		if (StringUtils.isNotEmpty(request.getParameter("action")) && request.getParameter("action").equals("delete")) {
			accountBean.clearMessages();
			accountBean.addMessage(Message.INFO,
					msgSrc.getMessage("msg.have.no.items.deleted", null, locale).replace("{0}", String.valueOf(success)));
		}
		if (CollectionUtils.isEmpty(result)) {
			accountBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		accountBean.setTotal(total);
		model.addAttribute("bean", accountBean);
		model.addAttribute("result", result);
		LDAPSyn ldapSyn = new LDAPSyn();
		Integer checkLdapSyn = ldapSyn.check(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null);
		model.addAttribute("checkLdapSyn", checkLdapSyn);
		return "system.account.list";
	}

	@RequestMapping(value = "/account/list", method = RequestMethod.GET)
	public String listget(@ModelAttribute(value = "bean") AccountBean accountBean, Model model,
			@RequestParam(value = "message", required = false) String message, HttpServletRequest request, Locale locale) throws Exception {
		if (message != null && message.equals("success")) {
			accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		accountBean.setLimit(pagesize);
		accountBean.setStatus(1);
		accountBean.setEntity(new Account());
		accountBean.setListStatusTable(statusTableService.findAllex());
		List<Object[]> result = accountService.search(accountBean);
		Integer total = accountService.countSearch(accountBean);
		List<String> userStatusList = new ArrayList<String>();

		for (Object[] obj : result) {
			// check locked user
			userStatusList.add(msgSrc.getMessage("locked.normal", null, locale));
		}
		// set status List
		accountBean.setUserStatusList(userStatusList);
		if (CollectionUtils.isEmpty(result)) {
			accountBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}

		accountBean.setTotal(new Integer(total + ""));
		model.addAttribute("bean", accountBean);
		model.addAttribute("result", result);
		LDAPSyn ldapSyn = new LDAPSyn();
		Integer checkLdapSyn = ldapSyn.check(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null);
		model.addAttribute("checkLdapSyn", checkLdapSyn);
		System.out.println("account list");
		return "system.account.list";
	}

	@Autowired
	TeamService teamService;

	@Transactional
	@RequestMapping(value = "/account/edit", method = RequestMethod.GET)
	public String edit_get(@RequestParam(required = false) Long id, HttpServletRequest request, Model model) throws Exception {
		Account account;
		// Team team;
		List<Team> teamListLeft = null;
		List<Team> teamListRight = null;
		List<Long> projectIds = new ArrayList<Long>();
		AccountBean accountBean = new AccountBean();
		accountBean.setListStatusTable(statusTableService.findAllex());
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			account = accountService.findById(id);
			// check locked user
			model.addAttribute("lockeduser", false);

			teamListRight = teamService.findTeamNotbyAccountID(id);
			teamListLeft = teamService.findTeambyAccountID(id);
		} else {
			account = new Account();
			account.setLdap(false);
			teamListRight = teamService.findAll();

		}

		accountBean.setEntity(account);
		accountBean.setLeftTeeprocurement(new ArrayList<Team>(account.getTeams()));
		accountBean.setImage(account.getImagePath());
		accountBean.setProjectIds(projectIds);

		model.addAttribute("bean", accountBean);
		model.addAttribute("right", teamListRight);
		model.addAttribute("left", teamListLeft);
		return "system.account.edit";
	}

	@RequestMapping(value = "/account/view", method = RequestMethod.GET)
	public String view(Model model, HttpServletRequest request, Locale locale) throws Exception {
		Account account;
		List<Team> teamListLeft = null;
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			account = accountService.findById(Long.valueOf(request.getParameter("id")));
			// check locked user
			model.addAttribute("userstatus", msgSrc.getMessage("locked.normal", null, locale));
			account = accountService.findById(Long.valueOf(request.getParameter("id")));
			teamListLeft = teamService.findTeambyAccountID(Long.valueOf(request.getParameter("id")));
		} else {
			account = new Account();
		}
		AccountBean accountBean = new AccountBean();
		accountBean.setEntity(account);
		// if (accountSetting != null) {
		// accountBean.setDept(accountSetting.getDepts());
		// }
		model.addAttribute("bean", accountBean);
		model.addAttribute("left", teamListLeft);
		return "system.account.view";
	}

	@RequestMapping(value = "/account/edit", method = RequestMethod.POST)
	public String edit_Post(@ModelAttribute(value = "bean") @Valid AccountBean accountBean, BindingResult bindingResult, Model model,
			HttpServletRequest request, @RequestParam(value = "chkTeamLeft", required = false) List<Long> chkTeamLeft, Locale locale,
			RedirectAttributes redirectAttributes) throws Exception {
		Account account = new Account();
		boolean newAccount = false;
		boolean isSavedFail = false;
		accountBean.setListStatusTable(statusTableService.findAllex());
		if (accountBean.getEntity().getId() != null) {
			account = accountService.findById(accountBean.getEntity().getId());
		} else {
			account.setUsername(accountBean.getEntity().getUsername());
			account.setLdap(false);
			account.setEnabled(true);
			newAccount = true;
		}
		if (StringUtils.isNotEmpty(accountBean.getEntity().getPassword())) {
			account.setPassword(Utils.encryptMD5(accountBean.getEntity().getPassword()));
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getFullName())) {
			bindingResult.rejectValue("entity.fullName", "javax.validation.constraints.NotNull.message");
		}
		// account.setFullName(accountBean.getEntity().getUsername());
		account.setFullName(accountBean.getEntity().getFullName());
		account.setBirthday(accountBean.getEntity().getBirthday());
		account.setEmail(accountBean.getEntity().getEmail());
		account.setMobile(accountBean.getEntity().getMobile());
		account.setDepartmentId(accountBean.getEntity().getDepartmentId());
		account.setStatusTable(accountBean.getEntity().getStatusTable());
		accountBean.setEntity(account);
		if (accountBean.getEntity().getUsername().length() != accountBean.getEntity().getUsername().trim().length()) {
			accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.acc.err.space", null, locale));
			redirectAttributes.addFlashAttribute("bean", accountBean);

		}
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
		if (StringUtils.isEmpty(accountBean.getEntity().getFullName())) {
			bindingResult.rejectValue("entity.fullName", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(accountBean.getEntity().getFullName())) {
			bindingResult.rejectValue("entity.fullName", "javax.validation.constraints.NotNull.message");
		}

		if (StringUtils.isEmpty(accountBean.getEntity().getUsername())) {
			bindingResult.rejectValue("entity.username", "javax.validation.constraints.NotNull.message");
		}
		if (account.getLdap() == false) {
			if (StringUtils.isEmpty(accountBean.getEntity().getPassword()) && account.getId() == null) {
				bindingResult.rejectValue("entity.password", "javax.validation.constraints.NotNull.message");
			}
		}
		List<Team> teamLeft = null;
		List<Team> teamRight = null;
		if (chkTeamLeft != null) {
			teamLeft = teamService.findByIds(chkTeamLeft);
			teamRight = teamService.findByNotInIds(chkTeamLeft);
		}

		// if(accountBean.getEntity().getUsername().){}
		if (!bindingResult.hasErrors()) {

			try {
				List<Team> teamListLeft = null;
				List<Team> teamListRight = null;

				if (chkTeamLeft != null) {
					teamLeft = teamService.findByIds(chkTeamLeft);
					accountBean.getEntity().setTeams(new HashSet<Team>(teamLeft));

				} else {
					accountBean.getEntity().setTeams(new HashSet<Team>());
				}

				// upload account images
				if (StringUtils.isNotEmpty(accountBean.getImage())) {
					String fileTempName = accountBean.getImage();
					String newName = Utils.moveTempToUploadFolder(fileTempName, systemConfig);
					accountBean.getEntity().setImagePath(newName);
				} else {
					accountBean.getEntity().setImagePath(null);
				}
				// Saving Account into Account Table
				Long checkid = accountBean.getEntity().getId();
				accountService.saveAccount(accountBean.getEntity());
				// saving project into AccountProject Table

				// Update to AccountTeam
				Long accId = accountBean.getEntity().getId();
				if (!newAccount) {
					accountTeamService.deleteAccountTeamByAccId(accId);
				}
				for (Team teamleft : accountBean.getEntity().getTeams()) {
					AccountTeamPK accTeamPk = new AccountTeamPK();
					accTeamPk.setAccountId(accId);
					accTeamPk.setTeamId(teamleft.getId());
					AccountTeam accTeam = new AccountTeam();
					accTeam.setPk(accTeamPk);
					accountTeamService.saveAccountTeam(accTeam);
				}

				// log

				if (checkid != null)
					activityLogService.saveActivityLog(systemConfig.EDIT_ACCOUNT, systemConfig.ACCOUNT, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());
				else
					activityLogService.saveActivityLog(systemConfig.ADD_NEW_ACCOUNT, systemConfig.ACCOUNT, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());

				// end approval info
				accountBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", accountBean);

				teamListLeft = teamService.findTeambyAccountID(accountBean.getEntity().getId());
				teamListRight = teamService.findTeamNotbyAccountID(accountBean.getEntity().getId());

				model.addAttribute("bean", accountBean);
				model.addAttribute("right", teamListRight);
				model.addAttribute("left", teamListLeft);
				model.addAttribute("success", true);

				return "system.account.edit.ajax";
			} catch (Exception ex) {
				isSavedFail = true;
				ex.printStackTrace();
				accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));

			}

		} else {
			isSavedFail = true;
			if (bindingResult.getFieldError("entity") != null) {
				accountBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			} else {
				accountBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		accountBean.setIsSavedFail(isSavedFail);
		List<Team> teamListLeft = teamService.findTeambyAccountID(accountBean.getEntity().getId());
		List<Team> teamListRight = teamService.findTeamNotbyAccountID(accountBean.getEntity().getId());
		if (accountBean.getEntity().getId() == null) {
			model.addAttribute("right", teamRight);
			model.addAttribute("left", teamLeft);
		} else {
			model.addAttribute("right", teamListRight);
			model.addAttribute("left", teamListLeft);
		}

		model.addAttribute("bean", accountBean);
		return "system.account.edit.ajax";
	}

	@RequestMapping(value = "/account/unlockuser", method = RequestMethod.GET)
	public String unlockUser(@RequestParam(required = true) Long id, Model model, Locale locale) throws Exception {
		Account account = accountService.findById(id);
		// get locked account
		return "redirect:edit?id=" + id;
	}

	@RequestMapping(value = "/account/listAccount_json", method = RequestMethod.GET)
	public @ResponseBody List<Account> list_categoryJson() {
		List<Account> root = accountService.findAll();
		return root;
	}

	@RequestMapping(value = "/account/listsync", method = RequestMethod.GET)
	public String listSyncGet(@ModelAttribute(value = "bean") AccountBean bean, BindingResult bindingResult, Model model, HttpServletRequest request,
			@RequestParam(value = "message", required = false) String message, Locale locale) throws Exception {

		if (bean == null) {
			bean = new AccountBean();
		} else {
			try {
				String paramsearchFieldOld = request.getParameter("searchFieldOld");
				bean.setSearchField(paramsearchFieldOld);
			} catch (Exception e) {

			}
		}
		if (StringUtils.isEmpty(bean.getSearchField())) {
			bindingResult.rejectValue("searchField", "javax.validation.constraints.NotNull.message");
		}
		bean.setListStatusTable(statusTableService.findAllex());
		List<Account> list = new ArrayList<Account>();
		if (StringUtils.isNotEmpty(bean.getSearchField())) {
			LDAPSyn ldapSyn = new LDAPSyn();
			list = ldapSyn.SynAccountLdapLike(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null,
					bean.getSearchField());
		}
		List<Account> listshow = new ArrayList<Account>();
		for (Account account : list) {
			boolean accountCheck = accountService.findByAccountNameCount(account.getUsername());
			if (!accountCheck) {
				listshow.add(account);
			}
		}
		if (message != null && message.equals("success")) {
			bean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		bean.setAccounts(listshow);
		model.addAttribute("bean", bean);
		return "system.account.sync.list";
	}

	@RequestMapping(value = "/account/listsync", method = RequestMethod.POST)
	public String listSyncPost(@ModelAttribute(value = "bean") AccountBean bean, BindingResult bindingResult, Model model, HttpServletRequest request,
			Locale locale) throws Exception {
		if (bean == null) {
			bean = new AccountBean();
		}
		bean.setListStatusTable(statusTableService.findAllex());
		if (StringUtils.isEmpty(bean.getSearchField())) {
			bindingResult.rejectValue("searchField", "javax.validation.constraints.NotNull.message");
		}
		LDAPSyn ldapSyn = new LDAPSyn();
		List<Account> list = new ArrayList<Account>();
		if (StringUtils.isNotEmpty(bean.getSearchField())) {
			list = ldapSyn.SynAccountLdapLike(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null,
					bean.getSearchField());
		}
		List<Account> listshow = new ArrayList<Account>();
		for (Account account : list) {
			boolean accountCheck = accountService.findByAccountNameCount(account.getUsername());
			if (!accountCheck) {
				listshow.add(account);
			}
		}
		bean.setAccounts(listshow);
		model.addAttribute("bean", bean);
		return "system.account.sync.list";
	}

	@RequestMapping(value = "/account/importsync", method = RequestMethod.POST)
	@ResponseBody
	public String importSyncPost(@ModelAttribute(value = "bean") AccountBean bean, @RequestParam(value = "index", required = false) String index,
			Model model, HttpServletRequest request, Locale locale) throws Exception {
		Long id = 0L;
		LDAPSyn ldapSyn = new LDAPSyn();
		List<Account> list = ldapSyn.SynAccountLdapLike(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), index, null);
		List<Account> listshow = new ArrayList<Account>();
		for (Account account : list) {
			boolean accountCheck = accountService.findByAccountNameCount(account.getUsername());
			if (!accountCheck) {
				listshow.add(account);
			}
		}
		if (listshow != null && listshow.size() > 0) {
			for (Account account : list) {
				try {
					if (account.getUsername().equals(index)) {
						account.setEnabled(true);
						account.setLdap(true);
						StatusTable statusTable = statusTableService.findActive();
						account.setStatusTable(statusTable);
						boolean accountCheck = accountService.findByAccountNameCount(account.getUsername());
						if (!accountCheck) {
							accountService.saveAccount(account);
						}
						id = account.getId();
						break;
					}
				} catch (Exception e) {

				}
			}
		}
		if (request.getParameter("auto") != null && request.getParameter("auto").equals("true")) {
			model.addAttribute("auto", "no");
		} else {
			model.addAttribute("auto", "yes");
		}
		model.addAttribute("bean", bean);
		return id.toString();
	}

	@RequestMapping(value = "/account/importsyncAll", method = RequestMethod.POST)
	@ResponseBody
	public String importSyncPostAll(@ModelAttribute(value = "bean") AccountBean bean, Model model, HttpServletRequest request, Locale locale)
			throws Exception {
		LDAPSyn ldapSyn = new LDAPSyn();
		List<Account> list = new ArrayList<Account>();
		if (StringUtils.isNotEmpty(bean.getSearchField())) {
			list = ldapSyn.SynAccountLdapLike(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null,
					bean.getSearchField());
		}
		List<Account> listshow = new ArrayList<Account>();
		for (Account account : list) {
			boolean accountCheck = accountService.findByAccountNameCount(account.getUsername());
			if (!accountCheck) {
				listshow.add(account);
			}
		}

		for (Account account : listshow) {

			try {
				account.setEnabled(true);
				account.setLdap(true);
				StatusTable statusTable = statusTableService.findActive();
				account.setStatusTable(statusTable);
				boolean accountCheck = accountService.findByAccountNameCount(account.getUsername());
				if (!accountCheck) {
					accountService.saveAccount(account);
				}
			} catch (Exception e) {

			}
		}
		if (request.getParameter("auto") != null && request.getParameter("auto").equals("true")) {
			model.addAttribute("auto", "no");
		} else {
			model.addAttribute("auto", "yes");
		}
		model.addAttribute("bean", bean);
		return "tuan";
	}

	@RequestMapping(value = "/account/department/json_tree", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody List<DepartmentTreeBean> getDeptTree(Model model, @RequestParam(value = "id", required = false) Long id) throws Exception {
		List<DepartmentTreeBean> root = departmentService.createTree(id);
		root.remove(0);
		return root;
	}

/*	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public String listRole(@ModelAttribute(value = "bean") RoleBean roleBean ,  Model model,
			@RequestParam(value = "message", required = false) String message, HttpServletRequest request, Locale locale) throws Exception {
		if (message != null && message.equals("success")) {
			roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		}
		List<Object[]> result = getRoleList(roleBean, locale);
		model.addAttribute("bean", roleBean);
		model.addAttribute("result", result);
		LDAPSyn ldapSyn = new LDAPSyn();
		Integer checkLdapSyn = ldapSyn.check(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null);
		model.addAttribute("checkLdapSyn", checkLdapSyn);
		return "system.account.role.list";
	}

	private List<Object[]> getRoleList(RoleBean roleBean, Locale locale) {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		roleBean.setLimit(pagesize);
		roleBean.setEntity(new Role());
		List<Object[]> result = roleService.getAllRoles(roleBean);
		Integer total = roleService.roleCountsearch(roleBean);

		if (CollectionUtils.isEmpty(result)) {
			roleBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		
		roleBean.setTotal(new Integer(total + ""));
		return result;
	}

	@Transactional
	@RequestMapping(value = "/role/edit", method = RequestMethod.GET)
	public String edit_role_get(@RequestParam(required = false) Long id, HttpServletRequest request, Model model) throws Exception {
		Role role;
		List<Team> teamListLeft = null;
		List<Team> teamListRight = null;
		List<Long> projectIds = new ArrayList<Long>();
		RoleBean roleBean = new RoleBean();
		if (StringUtils.isNotEmpty(request.getParameter("id"))) {
			role = roleService.findById(id);
		} else {
			role = new Role();
		}

		roleBean.setEntity(role);
		model.addAttribute("bean", roleBean);
		model.addAttribute("right", teamListRight);
		model.addAttribute("left", teamListLeft);
		return "system.role.edit";
	}
	
	@RequestMapping(value = "/role/edit", method = RequestMethod.POST)
	public String role_edit_Post(@ModelAttribute(value = "bean") @Valid AccountBean accountBean , RoleBean roleBean ,BindingResult bindingResult, Model model,
			HttpServletRequest request, @RequestParam(value = "chkTeamLeft", required = false) List<Long> chkTeamLeft, Locale locale,
			RedirectAttributes redirectAttributes) throws Exception {
		Account account = new Account();
		Role role = new Role();
		boolean newRole = false;
		boolean isSavedFail = false;
		Role roleFromDb=null;
		
		if (StringUtils.isEmpty(roleBean.getEntity().getCode())) {
			bindingResult.rejectValue("entity.code", "javax.validation.constraints.NotNull.message");
		}else{
			try{
			roleFromDb = roleService.findByCode(roleBean.getEntity().getCode());
			}catch(Exception e){
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		if (StringUtils.isEmpty(roleBean.getEntity().getName())) {
			bindingResult.rejectValue("entity.name", "javax.validation.constraints.NotNull.message");
		}
		if (StringUtils.isEmpty(roleBean.getEntity().getType())) {
			bindingResult.rejectValue("entity.type", "javax.validation.constraints.NotNull.message");
		}
		
		if (accountBean.getEntity().getId() != null) {
			//for edit req
			role = roleService.findById(roleBean.getEntity().getId());
			if(roleFromDb != null && roleFromDb.getId() != roleBean.getEntity().getId()){
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.role.code.exits", null, locale));
				model.addAttribute("bean", roleBean);
				return "system.role.edit.ajax";
			}
				
		} else {		
			//for new req
			if(roleFromDb != null){
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.role.code.exits", null, locale));
				model.addAttribute("bean", roleBean);
				return "system.role.edit.ajax";
			}
			role.setCreationDate(new Date());
			role.setCreatedBy(userProfile.getAccount().getEmail());
			newRole = true;
		}
		
		
		if (StringUtils.isNotEmpty(roleBean.getEntity().getCode())) {
			role.setCode(roleBean.getEntity().getCode());
		}
		if (StringUtils.isNotEmpty(roleBean.getEntity().getName())) {
			role.setName(roleBean.getEntity().getName());
		}
		if (StringUtils.isNotEmpty(roleBean.getEntity().getType())) {
			role.setType(roleBean.getEntity().getType());
		}
		//if (StringUtils.isNotEmpty(roleBean.getEntity().getDescription())) {
			role.setDescription(roleBean.getEntity().getDescription());
		//}
		
		roleBean.setEntity(role);
		if (roleBean.getEntity().getCode().length() != roleBean.getEntity().getCode().trim().length()) {
			roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.acc.err.space", null, locale));
			redirectAttributes.addFlashAttribute("bean", roleBean);
		}
	
		List<Team> teamLeft = null;
		List<Team> teamRight = null;
		if (chkTeamLeft != null) {
			teamLeft = teamService.findByIds(chkTeamLeft);
			teamRight = teamService.findByNotInIds(chkTeamLeft);
		}

		if (!bindingResult.hasErrors()) {

			try {
				List<Team> teamListLeft = null;
				List<Team> teamListRight = null;

				Long checkid = roleBean.getEntity().getId();
				roleService.save(roleBean.getEntity());
				// saving project into Role Table

				if (checkid != null)
					activityLogService.saveActivityLog(systemConfig.EDIT_ROLE, systemConfig.ROLE, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());
				else
					activityLogService.saveActivityLog(systemConfig.ADD_NEW_ROLE, systemConfig.ACCOUNT, locale, systemConfig.SYSTEM, null,
							userProfile.getAccount().getId().toString());


				roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
				redirectAttributes.addFlashAttribute("bean", roleBean);

				model.addAttribute("bean", roleBean);
				model.addAttribute("success", true);

				return "system.role.edit.ajax";
			} catch (Exception ex) {
				isSavedFail = true;
				ex.printStackTrace();
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));

			}

		} else {
			isSavedFail = true;
			if (bindingResult.getFieldError("entity") != null) {
				roleBean.addMessage(Message.ERROR, bindingResult.getFieldError("entity").getDefaultMessage());
			} else {
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage("msg.save.fail", null, locale));
			}
		}
		
		roleBean.setIsSavedFail(isSavedFail);
		model.addAttribute("bean", roleBean);
		return "system.role.edit.ajax";
	}
	
	@RequestMapping(value = "/role/list", method = RequestMethod.POST)
	public String listRolePost(@ModelAttribute(value = "bean") AccountBean accountBean,RoleBean roleBean ,  Model model, HttpServletRequest request, Locale locale)
			throws Exception {
		Integer pagesize = Integer.parseInt(systemConfig.getConfig(SystemConfig.PAGING_SIZE));
		roleBean.setLimit(pagesize);

		int success = 0;

		List<Object[]> result = roleService.search(roleBean);
		Integer total = result.size();
		if (StringUtils.isNotEmpty(request.getParameter("action")) && request.getParameter("action").equals("delete")) {
		//	accountBean.clearMessages();
			//accountBean.addMessage(Message.INFO,msgSrc.getMessage("msg.have.no.items.deleted", null, locale).replace("{0}", String.valueOf(success)));
		}
		if (CollectionUtils.isEmpty(result)) {
			roleBean.addMessage(Message.INFO, msgSrc.getMessage("msg.no.data", null, locale));
		}
		roleBean.setTotal(total);
		model.addAttribute("bean", roleBean);
		model.addAttribute("result", result);
		LDAPSyn ldapSyn = new LDAPSyn();
		Integer checkLdapSyn = ldapSyn.check(userProfile.getSECURITY_PRINCIPAL(), userProfile.getSECURITY_CREDENTIALS(), null);
		model.addAttribute("checkLdapSyn", checkLdapSyn);
		return "system.account.role.list";
	}
	
	@RequestMapping(value = "/role/delete", method = RequestMethod.GET)
	public String delete(@ModelAttribute(value = "bean")  RoleBean roleBean , Model model, Locale locale, RedirectAttributes redirectAttributes , @RequestParam(required = false) Long id ) throws Exception {
		if (roleBean.getEntity().getId() != null) {
			List<Team> team=null ;
			try {
				if(roleBean.getEntity().getId() != null){
					 team = teamService.findTeambyRoleID(roleBean.getEntity().getId());									
					System.out.println("role team size "+team.size());
							if(team.size() >0)
								throw new Exception();
					roleService.deleteRoleById(roleBean.getEntity().getId());
					roleBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.delete.success", null, locale));
				}
			} catch (Exception ex) {
				String msg="msg.delete.fail";
				if(team.size() >0)
					msg="msg.role.delete.fail";
				roleBean.addMessage(Message.ERROR, msgSrc.getMessage(msg, null, locale));
			}
		}
				
		redirectAttributes.addFlashAttribute("bean", roleBean);
		return "redirect:/system/role/list";
	
	}*/
	
}
