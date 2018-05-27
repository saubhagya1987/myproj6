package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;
import vn.com.unit.fe_credit.bean.v1.ContractTerminationDto;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;

public interface ContractTerminationDao {

	public List<ContractTerminationDto> findAll();
	
	public ContractTermination findByTerminationId(Integer contractTerminationId);
	
	/**
	 * Method returns list of all terminated contracts
	 * 
	 * @param approverEmailId
	 * @return
	 */
	public List<ContractTerminationDto> getTerminatedContractsAssigned(String approverEmailId,boolean isAdmin);
	
	
	public void updateTerminatedContractApprover();
	
	public int updateApproverLevelById(Integer contractTerminationId, String approverLevel);

	public ContractTermination findByContractTerminationId(Integer contractTerminationId);

	public List<ContractTerminationDto> getTerminatedContracts(String email);

	public void updateStatus(ContractTerminationBean contractTerminationBean,ContractTermination contractTermination);

	
}
