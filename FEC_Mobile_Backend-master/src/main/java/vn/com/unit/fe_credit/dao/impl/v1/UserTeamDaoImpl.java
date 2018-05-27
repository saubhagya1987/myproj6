/*package vn.com.unit.fe_credit.dao.impl.v1;


import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.v1.UserTeamDao;
import vn.com.unit.fe_credit.entity.UserTeam;
import vn.com.unit.fe_credit.entity.UserTeamPK;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
@Repository
public class UserTeamDaoImpl extends GenericDAOImpl<UserTeam, UserTeamPK> implements UserTeamDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void deleteUserTeamByUserId(Long userId) {
		// TODO Auto-generated method stub
		String hql="delete from UserTeam userTeam  where userTeam.pk.userId=:userId";
		Query query = getSession().createQuery(hql);
		query.setParameter("userId", userId);
		query.executeUpdate();
	}
}
*/