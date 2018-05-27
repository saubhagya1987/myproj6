/*package vn.com.unit.fe_credit.dao.impl.v1;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.UserBean;
import vn.com.unit.fe_credit.dao.v1.UserDao;
import vn.com.unit.fe_credit.entity.User;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

@Repository
public class UserDaoImpl extends GenericDAOImpl<User, Long> implements UserDao{
	
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}	

	@Override
	public List<Object[]> search(UserBean bean) {
		User user = new User();//bean.getEntity();
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT feUser.userId AS Id, feUser.email AS email, feUser.FIRST_NAME AS firstName, feUser.LAST_NAME AS lastName, feUser.designation AS designation, feUser.department AS department FROM Users feUser");
		hql.append(" WHERE 1=1 ");
		
		if (StringUtils.isNotEmpty(user.getEmail())) {
			hql.append(" AND feUser.email LIKE :email");
		}
		if (StringUtils.isNotEmpty(user.getFirstName())) {
			hql.append(" AND feUser.FIRST_NAME LIKE :firstName");
		}
		if (StringUtils.isNotEmpty(user.getLastName())) {
			hql.append(" AND feUser.LAST_NAME LIKE :lastName");
		}
		if (StringUtils.isNotEmpty(user.getDepartment())) {
			hql.append(" AND feUser.department LIKE :department");
		}
		if (StringUtils.isNotEmpty(user.getDesignation())) {
			hql.append(" AND feUser.designation = :designation");
		}
		

		hql.append(" ORDER BY feUser.userId");
		System.out.println(hql);
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("Id", new LongType())
				.addScalar("email", new StringType())
				.addScalar("firstName", new StringType())
				.addScalar("lastName", new StringType())
				.addScalar("designation", new StringType())
				.addScalar("department", new StringType());
		if (StringUtils.isNotEmpty(user.getFirstName())) {
			query.setParameter("firstName", "%" + user.getFirstName() + "%");
		}
		if (StringUtils.isNotEmpty(user.getLastName())) {
			query.setParameter("lastName", "%" + user.getLastName() + "%");
		}
		if (StringUtils.isNotEmpty(user.getEmail())) {
			query.setParameter("email", "%" + user.getEmail() + "%");
		}
		if (StringUtils.isNotEmpty(user.getDepartment())) {
			query.setParameter("department", "%" + user.getDepartment() + "%");
		}
		if (StringUtils.isNotEmpty(user.getDesignation())) {
			query.setParameter("designation", user.getDesignation());
		}
		
		if (bean.getLimit() > 0) {
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}
		@SuppressWarnings("unchecked")
		List<Object[]> lst = query.list();

		return lst;}

	@Override
	public Integer countSearch(UserBean bean) {
		User user = new User();//bean.getEntity();
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT COUNT(*) FROM (");
		hql.append(" SELECT feUser.userId AS Id, feUser.email AS email, feUser.FIRST_NAME AS firstName, feUser.LAST_NAME AS lastName, feUser.designation AS designation, feUser.department AS department FROM Users feUser");
		

		hql.append(" WHERE 1=1 ");
		if (StringUtils.isNotEmpty(user.getEmail())) {
			hql.append(" AND feUser.email LIKE :email");
		}
		if (StringUtils.isNotEmpty(user.getFirstName())) {
			hql.append(" AND feUser.FIRST_NAME LIKE :firstName");
		}
		if (StringUtils.isNotEmpty(user.getLastName())) {
			hql.append(" AND feUser.LAST_NAME LIKE :lastName");
		}
		if (StringUtils.isNotEmpty(user.getDepartment())) {
			hql.append(" AND feUser.department LIKE :department");
		}
		if (StringUtils.isNotEmpty(user.getDesignation())) {
			hql.append(" AND feUser.designation = :designation");
		}

		hql.append(" ORDER BY feUser.userId");
		hql.append(" ) ");
		Query query = getSession().createSQLQuery(hql.toString());
		if (StringUtils.isNotEmpty(user.getFirstName())) {
			query.setParameter("firstName", "%" + user.getFirstName() + "%");
		}
		if (StringUtils.isNotEmpty(user.getLastName())) {
			query.setParameter("lastName", "%" + user.getLastName() + "%");
		}
		if (StringUtils.isNotEmpty(user.getEmail())) {
			query.setParameter("email", "%" + user.getEmail() + "%");
		}
		if (StringUtils.isNotEmpty(user.getDepartment())) {
			query.setParameter("department", "%" + user.getDepartment() + "%");
		}
		if (StringUtils.isNotEmpty(user.getDesignation())) {
			query.setParameter("designation", user.getDesignation());
		}
		query.uniqueResult().toString();
		Integer a = 0;
		try {
			a = Integer.parseInt(query.uniqueResult().toString());
		} catch (Exception e) {

		}
		return a;
		}
	
	private Object addConstraint(StringBuilder hql, User user) {
		// TODO Auto-generated method stub
		if (StringUtils.isNotEmpty(user.getEmail())) {
			hql.append(" AND feUser.email LIKE :email");
		}
		if (StringUtils.isNotEmpty(user.getFirstName())) {
			hql.append(" AND feUser.firstName LIKE :firstName");
		}
		if (StringUtils.isNotEmpty(user.getLastName())) {
			hql.append(" AND feUser.lastName LIKE :lastName");
		}
		if (StringUtils.isNotEmpty(user.getDepartment())) {
			hql.append(" AND feUser.department LIKE :department");
		}
		if (StringUtils.isNotEmpty(user.getDesignation())) {
			hql.append(" AND feUser.designation = :designation");
		}
		return hql;
	}

}
*/