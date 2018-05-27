package vn.com.unit.fe_credit.dao.v1;

import vn.com.unit.fe_credit.bean.v1.RepossessionBean;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;
import vn.com.unit.fe_credit.entity.collection.Repossession;
import vn.com.unit.fe_credit.entity.collection.RepossessionApproval;

public interface RepossessionApprovalDao {
	public RepossessionApproval  updateApproval(Repossession repossession,RepossessionBean repossessionBean, String stepNo, String role);	

	public RepossessionApproval findByContractId(String contractId);

	/**
	 * @param approval
	 */
	public void save(RepossessionApproval approval);

}
