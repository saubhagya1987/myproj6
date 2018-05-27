package vn.com.unit.fe_credit.service.v1;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.bean.v1.ContractTerminationBean;

public interface ContractTerminationService {

	public ContractTerminationBean getTerminatedContracts();
	
	public List<String> getNextApproverLevelEmails(Integer contractTerminationId);
	
	public ContractTerminationBean updateContractStatus(ContractTerminationBean contractTerminationBean, String contractId,Locale locale);
	
	public ContractTerminationBean assignContract(ContractTerminationBean terminationBean, List<String> approverEmailIds,Locale locale);
	
	// Method retrieves the Contract Attachments to be displayed on "View Contract Attachments" page based on contract Id 
	public ContractTerminationBean getContractAttachments(String contractId);
	
	// Method retrieves the Contract Attachments to be displayed on "View Contract Attachments" page based on contract Id
	public ContractTerminationBean getContractAttachment(Integer attachmentId);
	
	
}
