package vn.com.unit.fe_credit.dao.v1;

import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;
import vn.com.unit.fe_credit.entity.collection.ContractTermination;
import vn.com.unit.fe_credit.entity.collection.ContractTerminationApproval;


public interface ContractTerminationApprovalDao {

	public ContractTerminationApproval  updateApproval(ContractTermination contractTermination, ContractTerminationBean contractTerminationBean);	

	public ContractTerminationApproval findByContractId(String contractId);

	

}
