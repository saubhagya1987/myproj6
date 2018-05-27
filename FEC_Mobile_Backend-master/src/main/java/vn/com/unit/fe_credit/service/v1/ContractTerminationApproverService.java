package vn.com.unit.fe_credit.service.v1;

import java.util.List;

public interface ContractTerminationApproverService {
	
	public Integer deleteContractMappingById(Integer contractTerminationId);

	public void updateApproverEmails(Integer contractTerminationId, String contractId, List<String> approverEmailIds);
	
}
