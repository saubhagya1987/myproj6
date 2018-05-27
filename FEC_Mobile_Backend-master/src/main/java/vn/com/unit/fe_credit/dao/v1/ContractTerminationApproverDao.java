package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.entity.collection.ContractTerminationApprover;

public interface ContractTerminationApproverDao {

	public List<ContractTerminationApprover> findAll();
	
	public List<ContractTerminationApprover> findByApproverEmail(String emailId);
	
	public Integer deleteByTerminationId(Integer contractTerminationId);
	
	public void updateApproverEmails(Integer contractTerminationId, String contractId, List<String> approverEmailIds);
}
