package vn.com.unit.fe_credit.dao.v1;

import java.util.List;

import vn.com.unit.fe_credit.entity.collection.ContractAttachment;

public interface ContractAttachmentDao {

	public List<ContractAttachment> getAttachmentsByContractId(String contractId);
	
	public ContractAttachment getAttachmentById(Integer attachmentId);
	
	public void saveAttachment(ContractAttachment contractAttachment);
	
	public void deleteAttachments();
}