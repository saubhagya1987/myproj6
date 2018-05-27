package vn.com.unit.fe_credit.dao;


import java.util.List;

import vn.com.unit.fe_credit.entity.Team;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface TeamDao extends GenericDAO<Team, Long>{
	public List<Team> searchByAccount(Long accountId);
	public List<Team> searchNotByAccount(Long accountId);
	public List<Team> findByIds(List<Long> chkTeamLeft);
	public List<Team> findByNotInIds(List<Long> chkTeamLeft);
	public List<Team> searchByUser(Long userId);
	public List<Team> searchNotByUser(Long userId);
	public List<Team> searchByRoleId(Long roleId);
}
