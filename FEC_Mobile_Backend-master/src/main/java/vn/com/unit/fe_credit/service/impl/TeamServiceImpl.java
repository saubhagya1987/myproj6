package vn.com.unit.fe_credit.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.TeamBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.TeamDao;
import vn.com.unit.fe_credit.entity.Team;
import vn.com.unit.fe_credit.service.TeamService;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("teamService")
@Transactional(readOnly = true)
public class TeamServiceImpl implements TeamService {
	@Autowired
	TeamDao teamDao;
	@Autowired
	SystemConfig systemConfig;
	@Autowired
	private UserProfile userProfile;

	@Override
	public boolean deleteTeamByCode(String code) {
		Team team = teamDao.searchUnique(new Search(Team.class).addFilterEqual("code", code));
		return teamDao.remove(team);
	}

	@Override
	@Transactional
	public boolean deleteTeamById(Long id) {

		return teamDao.removeById(id);
	}

	@Override
	public TeamBean search(TeamBean bean) {
		Search search = new Search(Team.class);
		Team team = bean.getEntity();
		search.addFilterEqual("enabled", true);
		if (team != null) {

			if (StringUtils.isNotEmpty(team.getCode())) {
				search.addFilterILike("code", "%" + team.getCode() + "%");
			}
			if (StringUtils.isNotEmpty(team.getName())) {
				search.addFilterILike("name", "%" + team.getName() + "%");
			}
			if (StringUtils.isNotEmpty(team.getDescription())) {
				search.addFilterILike("description", "%" + team.getDescription() + "%");
			}
			/*
			 * if(team.getEnabled()!=null){ search.addFilterEqual("enabled",
			 * team.getEnabled()); }
			 */
		}
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(), "desc".equalsIgnoreCase(bean.getSort()));
		} else {
			search.addSortDesc("id");
		}
		SearchResult<Team> searchResult = teamDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void saveOrUpdateTeam(Team team) {
		if(team.getId() == null){
			team.setCreated_date(new Date());
			team.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			team.setUpdate_date(new Date());
			team.setUpdate_by(userProfile.getAccount().getUsername());
		}
		teamDao.save(team);

	}

	@Override
	public Team findById(Long id) {
		// TODO Auto-generated method stub
		return teamDao.find(id);
	}

	@Override
	public List<Team> findTeambyAccountID(Long ID) {

		return teamDao.searchByAccount(ID);
	}
	
	/*@Override
	public List<Team> findTeambyUserID(Long ID) {

		return teamDao.searchByUser(ID);
	}*/

	@Override
	public List<Team> findTeamNotbyAccountID(Long ID) {

		return teamDao.searchNotByAccount(ID);
	}
	
	/*@Override
	public List<Team> findTeamNotbyUserID(Long ID) {

		return teamDao.searchNotByUser(ID);
	}*/

	@Override
	public List<Team> findByIds(List<Long> chkTeamLeft) {
		// TODO Auto-generated method stub

		return teamDao.findByIds(chkTeamLeft);
	}
	
	@Override
	public List<Team> findByNotInIds(List<Long> chkTeamLeft) {
		return teamDao.findByNotInIds(chkTeamLeft);
	}

	@Override
	public List<Team> findAll() {
		// TODO Auto-generated method stub
		return teamDao.findAll();
	}

	@Override
	public List<Team> findTeambyRoleID(Long ID) {
		return teamDao.searchByRoleId(ID);
	}

}
