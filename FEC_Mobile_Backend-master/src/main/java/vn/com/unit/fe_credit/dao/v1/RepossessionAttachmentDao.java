package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.entity.collection.RepossessionAttachment;

public interface RepossessionAttachmentDao {
public List<RepossessionAttachment> getAttachmentsByContractId(String contractId);
	
	public RepossessionAttachment getAttachmentById(Integer attachmentId);
	
	public void saveAttachment(RepossessionAttachment repossessionAttachment);
	
	public void deleteAttachments();

}
