package vn.com.unit.fe_credit.bean;


import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Department;

@Component(value="userProfile")
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
public class UserProfile {
	private Account account;
	private String defaultLang;
	private List<Long> authorizedDepartment;
	private String SECURITY_PRINCIPAL;
	private String SECURITY_CREDENTIALS;
	private String fullName;
	private String imgPath;
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	private Department department;
	public String getDefaultLang() {
		return defaultLang;
	}
	public void setDefaultLang(String defaultLang) {
		this.defaultLang = defaultLang;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public List<Long> getAuthorizedDepartment() {
		return authorizedDepartment;
	}
	public void setAuthorizedDepartment(List<Long> authorizedDepartment) {
		this.authorizedDepartment = authorizedDepartment;
	}
	public String getSECURITY_PRINCIPAL() {
		return SECURITY_PRINCIPAL;
	}
	public void setSECURITY_PRINCIPAL(String sECURITY_PRINCIPAL) {
		SECURITY_PRINCIPAL = sECURITY_PRINCIPAL;
	}
	public String getSECURITY_CREDENTIALS() {
		return SECURITY_CREDENTIALS;
	}
	public void setSECURITY_CREDENTIALS(String sECURITY_CREDENTIALS) {
		SECURITY_CREDENTIALS = sECURITY_CREDENTIALS;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	
	
	
}
