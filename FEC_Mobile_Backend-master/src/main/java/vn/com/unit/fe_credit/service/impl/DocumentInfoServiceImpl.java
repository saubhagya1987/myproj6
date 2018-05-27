package vn.com.unit.fe_credit.service.impl;

import static vn.com.unit.fe_credit.config.SystemConfig.URL_DEFAULT;

import java.util.ArrayList;
import java.util.List;

import oracle.net.aso.d;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.DocumentInfoDao;
import vn.com.unit.fe_credit.entity.DataJson;
import vn.com.unit.fe_credit.entity.DocumentInfo;
import vn.com.unit.fe_credit.service.DocumentInfoService;

@Service("DocumentInfoService")
@Transactional(readOnly = true)
public class DocumentInfoServiceImpl implements DocumentInfoService {

	@Autowired
	private DocumentInfoDao documentInfoDao;
	@Autowired
	SystemConfig systemConfig;

	public DocumentInfoServiceImpl() {
		super();
	}

	@Override
	@Transactional
	public void saveDocumentInfo(DocumentInfo entity) {
		// TODO Auto-generated method stub
		documentInfoDao.save(entity);

	}

	@Override
	public ArrayList<DocumentInfo> documentInfos(Long documentId) {
		// TODO Auto-generated method stub
		Search search = new Search(DocumentInfo.class);
		search.addFilterEqual("documentId", documentId);
		List<DocumentInfo> result = documentInfoDao.search(search);
		return (ArrayList<DocumentInfo>) result;

	}

	@Override
	public ArrayList<DataJson> getAttachment(Long documentId) {
		// TODO Auto-generated method stub
		Search search = new Search();
		search.addFilterEqual("referenceId", documentId);
		List<DataJson> result = new ArrayList<DataJson>();
		List<DocumentInfo> objects = documentInfoDao.search(search);
		if (objects != null) {
			for (DocumentInfo object : objects) {
				DataJson dataJson = new DataJson();
				dataJson.setFileName(object.getFileName());
				dataJson.setImgData(systemConfig.getConfig(URL_DEFAULT)
						+ "/ajax/download?fileName=" + object.getFileName());
				result.add(dataJson);
			}
			return (ArrayList<DataJson>) result;
		} else {
			return null;
		}
	}

}
