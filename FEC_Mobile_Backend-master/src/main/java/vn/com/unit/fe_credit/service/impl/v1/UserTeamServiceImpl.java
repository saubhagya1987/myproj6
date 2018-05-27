/*package vn.com.unit.fe_credit.service.impl.v1;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.dao.v1.UserTeamDao;
import vn.com.unit.fe_credit.entity.UserTeam;
import vn.com.unit.fe_credit.entity.UserTeamPK;
import vn.com.unit.fe_credit.service.v1.UserTeamService;
@Service(value="userTeamService")
@Transactional(readOnly=true)
public class UserTeamServiceImpl implements UserTeamService{
	@Autowired
	UserTeamDao userTeamDao;
	@Override
	public UserTeam findById(Long userId, Long teamId) {
		return userTeamDao.find(new UserTeamPK(userId, teamId));
	}
	@Transactional
	@Override
	public void saveUserTeam(UserTeam entity) {
		// TODO Auto-generated method stub
		userTeamDao.save(entity);
	}
	@Transactional
	@Override
	public void deleteUserTeamByUserId(Long userId) {
		userTeamDao.deleteUserTeamByUserId(userId);
	}

}
*/