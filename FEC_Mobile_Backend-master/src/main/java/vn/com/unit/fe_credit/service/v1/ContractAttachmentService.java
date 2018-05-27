package vn.com.unit.fe_credit.service.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.ContractAttachmentDto;

public interface ContractAttachmentService {
	
	// Return contract attachments by contract Id
	public List<ContractAttachmentDto> getContractAttachments(String contractId);
	
	// Return contract attachment by Id
	public ContractAttachmentDto getContractAttachment(Integer attachmentId);
}
