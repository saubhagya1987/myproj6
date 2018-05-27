package vn.com.unit.fe_credit.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.ExportDataToVTigerBean;
import vn.com.unit.fe_credit.bean.MgmExportVtigerBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.ExportFileDataToVTigerDao;
import vn.com.unit.fe_credit.dao.MgmExportVtigerDao;
import vn.com.unit.fe_credit.entity.MgmExportFileDataToVTiger;
import vn.com.unit.fe_credit.entity.MgmExportFileRedeemPoint;
import vn.com.unit.fe_credit.service.ExportFileDataToVTigerService;

@Service("ExportFileDataToVTigerService")
@Transactional(readOnly = true)
public class ExportFileDataToVTigerServiceImpl implements ExportFileDataToVTigerService {

	@Autowired
	private ExportFileDataToVTigerDao exportFileDataToVTigerDao;

	@Autowired
	private MgmExportVtigerDao mgmSuggestDao;

	@Autowired
	private UserProfile userProfile;

	public ExportFileDataToVTigerServiceImpl() {
		super();
	}

	@Override
	public List<MgmExportFileDataToVTiger> findAllex() {
		return exportFileDataToVTigerDao.findAll();
	}

	@Override
	public ExportDataToVTigerBean search(ExportDataToVTigerBean exportVTigerBean) {
		Search search = new Search(MgmExportFileDataToVTiger.class);
		search.setMaxResults(exportVTigerBean.getLimit());
		search.setPage(exportVTigerBean.getPage() - 1);
		MgmExportFileDataToVTiger vtiger = exportVTigerBean.getEntity();
		Collection<String> myCollection = new HashSet<String>();
		myCollection.add("1");
		if (vtiger != null) {
		} else {
			search.addFilterIn("valueType", myCollection);
		}
		search.addSortDesc("exportFileDate", true);
		SearchResult<MgmExportFileDataToVTiger> searchResult = exportFileDataToVTigerDao.searchAndCount(search);
		exportVTigerBean.setListResult(searchResult.getResult());
		exportVTigerBean.setTotal(searchResult.getTotalCount());
		return exportVTigerBean;
	}

	@Override
	public MgmExportVtigerBean export(MgmExportVtigerBean bean) {
		return mgmSuggestDao.searchValueExport(bean);
	}

	@Override
	@Transactional
	public void save(MgmExportFileDataToVTiger exportVTiger, Boolean flagExport) {
		if (flagExport == true) {
			if (exportVTiger.getVtigerId() == null) {
				exportVTiger.setCreateDate(new Date());
				if (userProfile.getAccount() != null)
					exportVTiger.setCreatedBy(userProfile.getAccount().getUsername());
			} else {
				exportVTiger.setUpdateDate(new Date());
				if (userProfile.getAccount() != null)
					exportVTiger.setUpdateBy(userProfile.getAccount().getUsername());
			}
		}
		exportFileDataToVTigerDao.save(exportVTiger);

	}
	
//	@Override
//	@Transactional
//	public void saveExportFileVTiger(MgmExportFileDataToVTiger exportVTiger) {
//		String userName = "admin";
//		if (exportVTiger.getVtigerId() == null) {
//			exportVTiger.setCreateDate(new Date());
//			exportVTiger.setCreatedBy(userName);
//		} else {
//			exportVTiger.setUpdateDate(new Date());
//			exportVTiger.setUpdateBy(userName);
//		}
//		exportFileDataToVTigerDao.save(exportVTiger);
//
//	}

	@Override
	public Integer getNoFileVTiger(String date) {
		return exportFileDataToVTigerDao.searchMaxNoByDateVTiger(date);
	}

	@Override
	@Transactional
	public void deleteVTiger(Long exportVTigerId) {
		exportFileDataToVTigerDao.deleteExportVTiger(exportVTigerId);

	}

	@Override
	public void updateStatusMobile(MgmExportVtigerBean dataToVTigerBean) {
		mgmSuggestDao.updateStatus(dataToVTigerBean);

	}
	
	@Override
	public boolean isHasExportingProcess() throws Exception {

		Search search = new Search(MgmExportFileDataToVTiger.class);
		search.addFilterEqual("is_exporting", Boolean.TRUE);
		int count = exportFileDataToVTigerDao.count(search);
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

}
