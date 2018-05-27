package vn.com.unit.fe_credit.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AccountTeamPK implements Serializable{
	/**
	 * 
	 */
	public AccountTeamPK(){
		
	}
	
	public AccountTeamPK(Long accountId, Long teamId) {
		super();
		this.accountId = accountId;
		this.teamId = teamId;
	}

	private static final long serialVersionUID = -4194714744303753858L;
	@Column(name="ACCOUNTID")
	 private Long accountId;
	@Column(name="TEAMID")
	 private Long teamId;
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getTeamId() {
		return teamId;
	}
	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}
	
	  
}
