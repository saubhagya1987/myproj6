package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.ContactBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.ContactDao;
import vn.com.unit.fe_credit.dao.RoleDao;
import vn.com.unit.fe_credit.entity.CMSEmtity;
import vn.com.unit.fe_credit.entity.Contact;
import vn.com.unit.fe_credit.entity.Customer;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.service.ContactService;
import vn.com.unit.fe_credit.service.RoleService;

@Service("ContactService")
@Transactional(readOnly = true)
public class ContactServiceImpl implements ContactService {
	@Autowired
	ContactDao contactDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private UserProfile userProfile;

	@Override
	public Contact findById(Long id) {
		return contactDao.find(id);
	}

	@Override
	@Transactional
	public void save(Contact cms) {
		if(cms.getContactID() == null){
			cms.setCreated_date(new Date());
			if(userProfile.getAccount() != null)
			cms.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			cms.setUpdate_date(new Date());
			if(userProfile.getAccount() != null)
			cms.setUpdate_by(userProfile.getAccount().getUsername());
		}
		contactDao.save(cms);

	}

	@Override
	public Contact findByAll() {
		Search search = new Search();				
		List<Contact> lst = contactDao.search(search);		
		if(lst.size() > 0){
			return lst.get(0);
		}
		
		return null;
	}
}
