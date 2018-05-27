package vn.com.unit.fe_credit.config;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import vn.com.unit.fe_credit.entity.Account;

public class LDAPSyn extends SpringBeanAutowiringSupport {

	@Autowired
	SystemConfig systemConfig;

	private static final Logger logger = LoggerFactory.getLogger(LDAPSyn.class);

	public List<Account> SynAccountLdap(String principal, String credentials, String sAMAccountName) {
		List<Account> listAccount = new ArrayList<Account>();

		Hashtable<String, String> env = new Hashtable<>();
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, systemConfig.getConfig(SystemConfig.CONFIG_LDAP_URL));
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, principal);
			env.put(Context.SECURITY_CREDENTIALS, credentials);
			NamingEnumeration results = null;
			DirContext context = new InitialDirContext(env);

			SearchControls searchControls = new SearchControls();
			int UF_ACCOUNTDISABLE = 0x0002;
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filter = "(objectClass=user)";
			if (StringUtils.isEmpty(sAMAccountName)) {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE) + ")))";
			} else {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE)
						+ "))(sAMAccountName=" + sAMAccountName + "))";
			}
			NamingEnumeration<SearchResult> enumeration = context.search(systemConfig.getConfig(SystemConfig.CONFIG_LDAP_SEARCH), filter,
					searchControls);
			while (enumeration.hasMore()) {
				try {
					SearchResult searchResult = enumeration.next();
					Attributes att = searchResult.getAttributes();
					Account account = new Account();

					account.setUsername(att.get("sAMAccountName").get().toString().trim());
					try {
						account.setFullName(att.get("displayName").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						account.setEmail(att.get("mail").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						account.setMobile(att.get("telephonenumber").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}

					listAccount.add(account);
					System.out.println(att.toString());
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());

		}
		return listAccount;
	}

	public Integer check(String principal, String credentials, String sAMAccountName) {
		if (StringUtils.isEmpty(credentials)) {
			return 0;
		}
		Hashtable<String, String> env = new Hashtable<>();
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, systemConfig.getConfig(SystemConfig.CONFIG_LDAP_URL));
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, principal);
			env.put(Context.SECURITY_CREDENTIALS, credentials);
			// NamingEnumeration results = null;
			DirContext context = new InitialDirContext(env);

			SearchControls searchControls = new SearchControls();
			int UF_ACCOUNTDISABLE = 0x0002;
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filter = "(objectClass=user)";
			if (StringUtils.isEmpty(sAMAccountName)) {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE) + ")))";
			} else {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE)
						+ "))(sAMAccountName=" + sAMAccountName + "))";
			}
			NamingEnumeration<SearchResult> enumeration = context.search(systemConfig.getConfig(SystemConfig.CONFIG_LDAP_SEARCH), filter,
					searchControls);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());
			return 0;

		}
		return 1;
	}

	public List<Account> SynAccountLdapLike(String principal, String credentials, String sAMAccountName, String name) {
		List<Account> listAccount = new ArrayList<Account>();

		Hashtable<String, String> env = new Hashtable<>();
		try {
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			env.put(Context.PROVIDER_URL, systemConfig.getConfig(systemConfig.CONFIG_LDAP_URL));
			env.put(Context.SECURITY_AUTHENTICATION, "simple");
			env.put(Context.SECURITY_PRINCIPAL, principal);
			env.put(Context.SECURITY_CREDENTIALS, credentials);
			NamingEnumeration results = null;
			DirContext context = new InitialDirContext(env);

			SearchControls searchControls = new SearchControls();
			searchControls.setCountLimit(600);
			int UF_ACCOUNTDISABLE = 0x0002;
			searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filter = "(objectClass=user)";
			if (StringUtils.isEmpty(sAMAccountName)) {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE) + ")))";
				if (StringUtils.isNotEmpty(name)) {
					filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE)
							+ "))(sAMAccountName=*" + name + "*))";
				}
			} else {
				filter = "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=" + Integer.toString(UF_ACCOUNTDISABLE)
						+ "))(sAMAccountName=" + sAMAccountName + "))";
			}
			NamingEnumeration<SearchResult> enumeration = context.search(systemConfig.getConfig(systemConfig.CONFIG_LDAP_SEARCH), filter,
					searchControls);
			while (enumeration.hasMore()) {
				try {
					SearchResult searchResult = enumeration.next();
					Attributes att = searchResult.getAttributes();
					Account account = new Account();
					account.setUsername(att.get("sAMAccountName").get().toString().trim());
					try {
						account.setFullName(att.get("displayName").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						account.setEmail(att.get("mail").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					try {
						account.setMobile(att.get("telephonenumber").get().toString().trim());
					} catch (Exception e) {
						logger.error(e.getMessage());
					}

					listAccount.add(account);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			logger.error(e.getMessage());

		}
		return listAccount;
	}

	public static void main(String[] args) {
		/*
		 * LDAPSyn ldapSyn = new LDAPSyn(); Utils.getProperties().setProperty("ldap.url", "ldap://192.168.1.253:389");
		 * Utils.getProperties().setProperty("ldap.domain", "company.local");
		 * Utils.getProperties().setProperty("ldap.search", "dc=company,dc=local");
		 * ldapSyn.SynAccountLdapLike("90900036@company.local", "Pass@word1", "","bui");
		 */
	}

}