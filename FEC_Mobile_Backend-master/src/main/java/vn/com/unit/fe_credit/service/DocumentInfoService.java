package vn.com.unit.fe_credit.service;

import java.util.ArrayList;

import vn.com.unit.fe_credit.entity.DataJson;
import vn.com.unit.fe_credit.entity.DocumentInfo;

public interface DocumentInfoService {

	void saveDocumentInfo(DocumentInfo entity);

	ArrayList<DocumentInfo> documentInfos(Long documentId);
	ArrayList<DataJson> getAttachment(Long documentId);

}
