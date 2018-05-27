package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.bean.CustomerBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.CustomerDao;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.ViewCustomer;
import vn.com.unit.fe_credit.service.CustomerActivityLogService;
import vn.com.unit.fe_credit.service.CustomerService;

@Service("customerService")
@Transactional(readOnly = true)
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	private UserProfile userProfile;

	@Autowired
	CustomerActivityLogService customerActivityLogService;

	Locale locale = new Locale("EN");

	public CustomerServiceImpl() {
		super();
	}

	@Override
	public Customer findById(Long id) {
		return customerDao.find(id);
	}

	@Override
	public Customer findByIdCardNum(String idCardNum, String smsCode, String password, String cellPhone) {
		Search search = new Search();
		search.addFilterEqual("idCardNumber", idCardNum.trim());
		if (StringUtils.isNotEmpty(smsCode)) {
			search.addFilterEqual("smsCode", smsCode.trim());
		}
		if (StringUtils.isNotEmpty(password)) {
			search.addFilterEqual("password", password);
		}

		if (StringUtils.isNotEmpty(cellPhone)) {
			search.addFilterEqual("cellPhone", cellPhone.trim());
		}

		List<Customer> lst = customerDao.search(search);

		if (lst.size() > 0) {
			return lst.get(0);
		}

		return null;
	}

	@Override
	public Customer findByCustomerLogin(String idCardNum, String password) {
		Search search = new Search();
		search.addFilterEqual("idCardNumber", idCardNum);
		search.addFilterEqual("password", password);
		search.addFilterEqual("status", SystemConfig.STATUS_ACTIVE);
		List<Customer> lst = customerDao.search(search);

		if (lst.size() > 0) {
			return lst.get(0);
		}

		return null;
	}

	@Transactional
	@Override
	public void deleteCustomer(Long id) {
		Customer customer = findById(id);
		if (customer != null)
			customerDao.remove(customer);
	}

	@Transactional
	@Override
	public void saveCustomerAccount(Customer customer) {
		if (customer.getCustomerId() == null) {
			customer.setCreateDate(new Date());
			if (userProfile != null && userProfile.getAccount() != null)
				customer.setCreateBy(userProfile.getAccount().getUsername());
		} else {
			customer.setUpdateDate(new Date());
			if (userProfile != null && userProfile.getAccount() != null)
				customer.setUpdateBy(userProfile.getAccount().getUsername());
		}
		customerDao.save(customer);

	}

	@Transactional
	@Override
	public void save(CustomerBean bean) {
		Customer customer = bean.getEntity();
		String fullName = "";
		if (StringUtils.isNotEmpty(customer.getMiddleName())) {
			fullName = customer.getLastName() + " " + customer.getMiddleName() + " " + customer.getFirstName();
		} else {
			fullName = customer.getLastName() + " " + customer.getFirstName();
		}
		customer.setFullName(fullName);
		if (customer.getCustomerId() == null) {
			customer.setCreateDate(new Date());
			customer.setCreateBy(userProfile.getAccount().getUsername());
		} else {
			customer.setUpdateDate(new Date());
			customer.setUpdateBy(userProfile.getAccount().getUsername());
		}
		customerDao.save(customer);
	}

	@Override
	public CustomerBean search(CustomerBean bean) {
		CustomerBean ret = customerDao.search(bean);

		List<Customer> lstCustomer = ret.getListResult();
		for (int i = 0; i < lstCustomer.size(); i++) {
			String address = "";
			if (StringUtils.isNotEmpty(lstCustomer.get(i).getAddressNo())) {
				address += lstCustomer.get(i).getAddressNo() + "  ";
			}
			if (StringUtils.isNotEmpty(lstCustomer.get(i).getStreet())) {
				address += lstCustomer.get(i).getStreet() + ", ";
			}
			if (StringUtils.isNotEmpty(lstCustomer.get(i).getWard())) {
				address += lstCustomer.get(i).getWard() + ", ";
			}
			if (StringUtils.isNotEmpty(lstCustomer.get(i).getDistrict())) {
				address += lstCustomer.get(i).getDistrict() + ", ";
			}
			if (StringUtils.isNotEmpty(lstCustomer.get(i).getProvince())) {
				address += lstCustomer.get(i).getProvince() + ", ";
			}
			if (StringUtils.isNotEmpty(address)) {
				address = address.substring(0, address.length() - 2);
			}
			lstCustomer.get(i).setAddress(address);
		}
		return ret;
	}

	@Override
	@Transactional
	public Customer findByIdCardNumber(String idCardNumber) {

		Search search = new Search(Customer.class);
		search.addFilterEqual("idCardNumber", idCardNumber);
		Customer result = customerDao.searchUnique(search);
		return result;
	}

	@Override
	@Transactional
	public Customer findByIdCellPhone(String cellPhone) {
		Search search = new Search();
		search.addFilterEqual("cellPhone", cellPhone);
		List<Customer> result = customerDao.search(search);
		if (result.size() > 0) {
			return result.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void update(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.save(customer);
	}

	@Override
	public ViewCustomer findViewById(Long id) {

		Search search = new Search();
		search.addFilterEqual("customerId", id);
		Customer customer = customerDao.searchUnique(search);

		ViewCustomer viewCustomer = new ViewCustomer();
		viewCustomer.setCustomerId(customer.getCustomerId());
		viewCustomer.setActivateCode(customer.getActivateCode());
		viewCustomer.setActivateCodeDate(customer.getActivateCodeDate());
		viewCustomer.setActivateCodeExpiredDate(customer.getActivateCodeExpiredDate());
		viewCustomer.setAddress(customer.getAddressS());
		viewCustomer.setBirthday(customer.getBirthday());
		viewCustomer.setCellPhone(customer.getCellPhone());
		viewCustomer.setEmailAddress(customer.getEmailAddress());
		viewCustomer.setFirstName(customer.getFirstName());
		viewCustomer.setFullName(customer.getFullName());
		viewCustomer.setIdCardNumber(customer.getIdCardNumber());
		viewCustomer.setImagePath(customer.getImagePath());
		viewCustomer.setIsLogged(customer.getIsLogged());
		viewCustomer.setLastLoggedTime(customer.getLastLoggedTime());
		viewCustomer.setLastName(customer.getLastName());
		viewCustomer.setMapLocation(customer.getMapLocation());
		viewCustomer.setMapLocationOld(customer.getMapLocationOld());
		viewCustomer.setMiddleName(customer.getMiddleName());
		viewCustomer.setCustomerContactId(customer.getOldUserId());
		viewCustomer.setSmsCode(customer.getSmsCode());
		viewCustomer.setStatus(customer.getStatus());
		viewCustomer.setUserName(customer.getUserName());
		viewCustomer.setResetPasswordExpiredDate(customer.getResetPasswordExpiredDate());
		viewCustomer.setResetPasswordKey(customer.getResetPasswordKey());
		viewCustomer.setAddressNo(customer.getAddressNo());
		viewCustomer.setStreet(customer.getStreet());
		viewCustomer.setWard(customer.getWard());
		viewCustomer.setDistrict(customer.getDistrict());
		viewCustomer.setProvince(customer.getProvince());

		return viewCustomer;
	}

	@Override
	public Customer findbyCustomerId(String customerId) {
		// TODO Auto-generated method stub
		return customerDao.findbyCustomerId(customerId);
	}

	@Override
	public Customer customerIDtoAccountID(String customerContractId) {
		Search search = new Search(Customer.class);
		search.addFilterEqual("oldUserId", customerContractId);
		List<Customer> customers = customerDao.search(search);
		if (customers != null && customers.size() > 0) {
			return customers.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void saveCustomers(List<Customer> customer) throws Exception {

		if (CollectionUtils.isEmpty(customer)) {
			return;
		}
		try {
			customerDao.save(customer.toArray(new Customer[customer.size()]));
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	@Transactional
	public void deleteCustomerImport(Date date) {
		// truoc khi delete tim bao nhiu roi ghi log
		List<Customer> listDao = customerDao.cuontDeleteCustomerImport(date);
		try {

			if (CollectionUtils.isNotEmpty(listDao)) {
				customerActivityLogService.saveCustomerImportActivityLog(SystemConfig.SAVE_CUSTOMER_DELETE, SystemConfig.CUSTOMER, locale,
						SystemConfig.SYSTEM, null, null, "Delete " + listDao.size() + " customer");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		customerDao.deleteCustomerImport(date);

	}

	@Override
	public void markAsCustomerHasBeenInstalledTheMobileApp(String idCardNumber, String cellphone) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public int updateOTPResetPasswordAttempts(long customerId, int otpAttempts, Date bannedDueDate) throws Exception {

		return customerDao.updateOTPResetPasswordAttempts(customerId, otpAttempts, bannedDueDate);

	}

	@Override
	@Transactional
	public int resetRequestOTPAttempts(long customerId) {
		return customerDao.resetRequestOTPAttempts(customerId);
	}
}
