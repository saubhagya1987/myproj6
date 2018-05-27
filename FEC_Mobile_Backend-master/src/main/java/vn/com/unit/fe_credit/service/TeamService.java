package vn.com.unit.fe_credit.service;


import java.util.List;

import vn.com.unit.fe_credit.bean.TeamBean;
import vn.com.unit.fe_credit.entity.Team;

public interface TeamService {
	public void saveOrUpdateTeam(Team team);
	public boolean deleteTeamByCode(String code);
	public boolean deleteTeamById(Long id);
	public Team findById(Long id);
	public TeamBean search(TeamBean bean);
	//public List<Team> findTeambyUserID(Long ID);
	public List<Team> findTeambyAccountID(Long ID);
	public List<Team> findTeamNotbyAccountID(Long ID);
	public List<Team> findByIds(List<Long> chkTeamLeft);
	public List<Team> findAll();
	public List<Team> findByNotInIds(List<Long> chkTeamLeft);
	//public List<Team> findTeamNotbyUserID(Long ID);
	List<Team> findTeambyRoleID(Long ID);
}
