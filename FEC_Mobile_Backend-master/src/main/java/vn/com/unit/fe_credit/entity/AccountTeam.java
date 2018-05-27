package vn.com.unit.fe_credit.entity;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="accountteam")
public class AccountTeam {
	@EmbeddedId
	private
	AccountTeamPK pk;

	public AccountTeamPK getPk() {
		return pk;
	}

	public void setPk(AccountTeamPK pk) {
		this.pk = pk;
	}

}
