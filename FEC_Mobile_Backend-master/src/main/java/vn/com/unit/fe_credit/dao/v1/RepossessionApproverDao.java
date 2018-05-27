package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;

public interface RepossessionApproverDao {
public List<RepossessionApprover> findAll();
	
	public List<RepossessionApprover> findByApproverEmail(String emailId);
	
	public Integer deleteByRepossessionId(Integer repossessionId);
	
	public void updateApproverEmails(Integer repossessionId, String contractId, List<String> approverEmailIds,RepossessionApprover repossessionApprover,Role role);

	public List<RepossessionApprover> findByRepossessionId(Integer repossessionId);

	/**
	 * @param id
	 * @param bidderId
	 * @return
	 */
	public RepossessionApprover findByRepossessionAndBidderId(Long id, String bidderId, String email);

	/**
	 * @param id
	 * @param bidderId
	 * @return
	 */
	public int deleteByRepossessionAndBidderId(Long id, String bidderId);

	/**
	 * @param approvers
	 */
	public void save(List<RepossessionApprover> approvers);

}

