package vn.com.unit.fe_credit.service.v1;

import java.util.List;
import java.util.Locale;

import vn.com.unit.fe_credit.bean.v1.RepossessionBean;

public interface RepossessionService {
	public RepossessionBean getRepossessionContracts();
	
	public List<String> getNextApproverLevelEmails(Integer id);
	
	public void updateRepossessionStatus(RepossessionBean repossessionBean,Locale locale);
	
	public RepossessionBean assignContract(RepossessionBean repossessionBean, List<String> approverEmailIds,Locale locale);
	
	// Method retrieves the Contract Attachments to be displayed on "View Contract Attachments" page based on contract Id 
	public RepossessionBean getRepossessionAttachments(String contractId);
	
	// Method retrieves the Contract Attachments to be displayed on "View Contract Attachments" page based on contract Id
	public RepossessionBean getRepossessionAttachment(Integer attachmentId);

	//public RepossessionBean getRepossessionFormData(String appleId,String customerName,String approvalStatus);

	
	


}
