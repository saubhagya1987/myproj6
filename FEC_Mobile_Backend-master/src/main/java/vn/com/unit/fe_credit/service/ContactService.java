package vn.com.unit.fe_credit.service;


import java.util.List;

import vn.com.unit.fe_credit.bean.CMSBean;
import vn.com.unit.fe_credit.bean.ContactBean;
import vn.com.unit.fe_credit.entity.Contact;
import vn.com.unit.fe_credit.entity.Role;

public interface ContactService {
	Contact findById(Long id);
	void save(Contact contact);
	Contact findByAll();
}
