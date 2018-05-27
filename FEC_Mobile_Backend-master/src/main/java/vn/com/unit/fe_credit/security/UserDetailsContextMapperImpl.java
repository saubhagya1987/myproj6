package vn.com.unit.fe_credit.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.AccountDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.Team;

public class UserDetailsContextMapperImpl implements UserDetailsContextMapper, Serializable {
	private static final long serialVersionUID = 3962976258168853954L;
	@Autowired
	AccountDao accountDao;
	@Autowired
	UserProfile userProfile;

	@Override
	@Transactional
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
			Collection<? extends GrantedAuthority> authority) {

		Account account = accountDao.findByUserName(username);
		if (account == null) {
			throw new LockedException("User account is locked");
		}
		List<GrantedAuthority> authoritiesRs = new ArrayList<GrantedAuthority>();
		boolean accountNonLocked = true;
		userProfile.setAccount(account);
		if (account.getTeams() != null) {
			for (Team team : account.getTeams()) {
				// if(SystemConstant.TEAM_TYPE.equalsIgnoreCase(team.getType())){
				for (Role role : team.getRoles()) {
					// if(SystemConstant.ROLE_TYPE.equalsIgnoreCase(role.getType())){
					GrantedAuthority grantedAuthorityImpl = new SimpleGrantedAuthority(role.getCode());
					authoritiesRs.add(grantedAuthorityImpl);
					// }
				}
				// }

			}
		}

		authoritiesRs.add(new SimpleGrantedAuthority("ROLE_AUTHED"));
		return new User(username, "", accountNonLocked, true, true, accountNonLocked, authoritiesRs);
	}

	@Override
	public void mapUserToContext(UserDetails arg0, DirContextAdapter arg1) {
	}
}
