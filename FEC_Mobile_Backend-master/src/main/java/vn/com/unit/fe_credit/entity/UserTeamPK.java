package vn.com.unit.fe_credit.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserTeamPK implements Serializable{
	/**
	 * 
	 */
	public UserTeamPK(){
		
	}
	
	public UserTeamPK(Long userId, Long teamId) {
		super();
		this.userId = userId;
		this.teamId = teamId;
	}

	private static final long serialVersionUID = -4194714744303753858L;
	@Column(name="USERID")
	 private Long userId;
	@Column(name="TEAMID")
	 private Long teamId;
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}	  
}
