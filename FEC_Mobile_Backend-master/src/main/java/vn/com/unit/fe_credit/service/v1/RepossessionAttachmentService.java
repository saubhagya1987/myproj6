package vn.com.unit.fe_credit.service.v1;

import java.util.List;

import vn.com.unit.fe_credit.bean.v1.RepossessionAttachmentDto;

public interface RepossessionAttachmentService {
	// Return contract attachments by contract Id
		public List<RepossessionAttachmentDto> getContractAttachments(String contractId);
		
		// Return contract attachment by Id
		public RepossessionAttachmentDto getContractAttachment(Integer attachmentId);

}
