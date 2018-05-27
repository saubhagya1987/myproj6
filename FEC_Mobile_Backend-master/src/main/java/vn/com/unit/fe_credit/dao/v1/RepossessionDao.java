package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.RepossessionBean;
import vn.com.unit.fe_credit.bean.v1.RepossessionDto;
import vn.com.unit.fe_credit.entity.collection.ApprovalRule;
import vn.com.unit.fe_credit.entity.collection.Repossession;

public interface RepossessionDao {
    public List<RepossessionDto> findAll();
	
	public Repossession findByRepossessionId(Integer id);
	
	public List<RepossessionDto> getRepossessionContractsAssigned(String approverEmailId,boolean isAdmin);	
	
	public void updateTerminatedContractApprover();
	
	public int updateApproverLevelById(Integer id, String approverLevel);

	//public Repossession findByContractTerminationId(Integer id);

	public List<RepossessionDto> getRepossessionContracts(String email);

	public void updateStatus(RepossessionBean repossessionBean,	Repossession repossession);

//	public List<Object> getEmailIdByMail(String role);

	public ApprovalRule getApprovalRuleByRole(String role);

	//public List<RepossessionDto> getRepossessionFormData(String appleId,String customerName, String approvalStatus);

}
