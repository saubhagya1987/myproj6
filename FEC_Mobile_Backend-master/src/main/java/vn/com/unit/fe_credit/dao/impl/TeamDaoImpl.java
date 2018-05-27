package vn.com.unit.fe_credit.dao.impl;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.dao.TeamDao;
import vn.com.unit.fe_credit.entity.Team;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;


@Repository
public class TeamDaoImpl extends GenericDAOImpl<Team, Long> implements TeamDao{
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<Team> searchByAccount(Long accountId) {
		//String hql="from Team team inner join team.accounts account where account.id=:accountId and team.type=:type";
		String hql="from Team team1 where team1.id in ("
								+ "select team.id from Team team inner join "
								+ "team.accounts account "
								+ "where account.id=:accountId) ";
		 Query query = getSession().createQuery(hql);
		 query.setParameter("accountId", accountId);
		 return query.list();
	}
	
	@Override
	public List<Team> searchByUser(Long userId) {
		String hql="from Team team1 where team1.id in ("
								+ "select team.id from Team team inner join "
								+ "team.users user "
								+ "where user.id=:userId) ";
		 Query query = getSession().createQuery(hql);
		 query.setParameter("userId", userId);
		 return query.list();
	}

	@Override
	public List<Team> searchNotByAccount(Long accountId) {		
		String hql="from Team team1 where team1.id not in ("
								+ "select team.id from Team team inner join "
								+ "team.accounts account "
								+ "where account.id=:accountId) ";
		 Query query = getSession().createQuery(hql);
		 query.setParameter("accountId", accountId);
		 return query.list();
	}
	
	@Override
	public List<Team> searchNotByUser(Long userId) {		
		String hql="from Team team1 where team1.id not in ("
								+ "select team.id from Team team inner join "
								+ "team.users user "
								+ "where user.id=:userId) ";
		 Query query = getSession().createQuery(hql);
		 query.setParameter("userId", userId);
		 return query.list();
	}

	

	@Override
	public List<Team> findByIds(List<Long> chkTeamLeft) {
		// TODO Auto-generated method stub
		String hql="from Team team1 where team1.id in (:list)";
		Query query = getSession().createQuery(hql);
		 query.setParameterList("list", chkTeamLeft);
		return query.list();
	}
	
	@Override
	public List<Team> findByNotInIds(List<Long> chkTeamLeft) {
		// TODO Auto-generated method stub
		String hql="from Team team1 where team1.id not in (:list)";
		Query query = getSession().createQuery(hql);
		 query.setParameterList("list", chkTeamLeft);
		return query.list();
	}
	
	@Override
	public List<Team> searchByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		String hql="from Team team1 where team1.id in ("
				+ "select team.id from Team team inner join "
				+ "team.roles role "
				+ "where role.id=:roleId) ";
		Query query = getSession().createQuery(hql);
		query.setParameter("roleId", roleId);
		return query.list();
	}

}
