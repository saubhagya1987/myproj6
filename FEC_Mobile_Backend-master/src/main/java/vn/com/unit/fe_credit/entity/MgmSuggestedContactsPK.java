package vn.com.unit.fe_credit.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable

public class MgmSuggestedContactsPK implements Serializable {

	private static final long serialVersionUID = -4194714744303753858L;

	@Column(name = "CONTACT_PHONE", length = 11)
	private String contactPhone;

	@Column(name = "RELATION_ID", length = 50)
	private String relationId;

	public MgmSuggestedContactsPK() {

	}

	public MgmSuggestedContactsPK(String contactPhone, String relationId) {
		super();
		this.contactPhone = contactPhone;
		this.relationId = relationId;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}
	
	

}
