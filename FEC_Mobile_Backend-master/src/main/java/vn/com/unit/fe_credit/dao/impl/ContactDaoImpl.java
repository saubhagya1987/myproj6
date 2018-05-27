package vn.com.unit.fe_credit.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.ContactDao;
import vn.com.unit.fe_credit.dao.RoleDao;
import vn.com.unit.fe_credit.entity.Contact;
import vn.com.unit.fe_credit.entity.Role;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;


@Repository
public class ContactDaoImpl extends GenericDAOImpl<Contact, Long> implements ContactDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

}
