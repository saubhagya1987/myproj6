package vn.com.unit.fe_credit.entity.collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="approval_rule")
public class ApprovalRule {
@Id
@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="approvalrule_seq")
@SequenceGenerator(name="approvalrule_seq", sequenceName="approvalrule_seq")
@Column(name="id")
private Integer id;
@Column(name="role")
private String role;
@Column(name="role_to_approve")
private String roleToApprove;
@Column(name="mail_to")
private String mailTo;
/**
 * @return the id
 */
public Integer getId() {
	return id;
}
/**
 * @return the role
 */
public String getRole() {
	return role;
}
/**
 * @return the roleToApprove
 */
public String getRoleToApprove() {
	return roleToApprove;
}
/**
 * @return the mailTo
 */
public String getMailTo() {
	return mailTo;
}
/**
 * @param id the id to set
 */
public void setId(Integer id) {
	this.id = id;
}
/**
 * @param role the role to set
 */
public void setRole(String role) {
	this.role = role;
}
/**
 * @param roleToApprove the roleToApprove to set
 */
public void setRoleToApprove(String roleToApprove) {
	this.roleToApprove = roleToApprove;
}
/**
 * @param mailTo the mailTo to set
 */
public void setMailTo(String mailTo) {
	this.mailTo = mailTo;
}

}
