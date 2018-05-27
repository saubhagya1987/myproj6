package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.BranchBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.BranchDao;
import vn.com.unit.fe_credit.dao.POSDao;
import vn.com.unit.fe_credit.entity.BranchEmtity;
import vn.com.unit.fe_credit.service.BranchService;
import vn.com.unit.webservice.BranchCities;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("BranchService")
@Transactional(readOnly = true)
public class BranchServiceImpl implements BranchService {

	@Autowired
	private BranchDao branchDao;
	
	@Autowired
	private POSDao posDao;
	
	@Autowired
	private UserProfile userProfile;
	
	public BranchServiceImpl() {
		super();
	}

	@Override
	public List<BranchEmtity> findAllex() {
		List<BranchEmtity>  getDAO = new ArrayList<BranchEmtity>();
					getDAO=	branchDao.findAll();
				for (int i = 0; i < getDAO.size(); i++) {
					String address = "";
					if (StringUtils.isNotEmpty(getDAO.get(i).getAddress_street())) {
						address += getDAO.get(i).getAddress_street() + " ";
					}
					if (StringUtils.isNotEmpty(getDAO.get(i).getAward())) {
						address += getDAO.get(i).getAward() + ", ";
					}
					if (StringUtils.isNotEmpty(getDAO.get(i).getDistrict())) {
						address += getDAO.get(i).getDistrict() + ", ";
					}
					if (StringUtils.isNotEmpty(getDAO.get(i).getProvince())) {
						address += getDAO.get(i).getProvince() + ", ";
					}
					if (StringUtils.isNotEmpty(address)) {
						address = address.substring(0, address.length() - 2);
					}
					getDAO.get(i).setAddressNo(address);
					
				}
		
		return getDAO;
	}

	@Override
	public BranchBean search(BranchBean bean) {
		Search search = new Search(BranchEmtity.class);
		 if (StringUtils.isNotEmpty(bean.getSearchField())) {
		search.addFilterOr(
		Filter.ilike("province", "%" + bean.getSearchField().trim()+ "%"));
		 }
		// search.addFilterEqual("statusTable.status_tableId", value);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		search.addSort("branchid", true);
		SearchResult<BranchEmtity> searchResult = branchDao.searchAndCount(search);
		
		for (int i = 0; i < searchResult.getResult().size(); i++) {
			String address = "";
			if (StringUtils.isNotEmpty(searchResult.getResult().get(i).getAddress_street())) {
				address += searchResult.getResult().get(i).getAddress_street() + " ";
			}
			if (StringUtils.isNotEmpty(searchResult.getResult().get(i).getAward())) {
				address += searchResult.getResult().get(i).getAward() + ", ";
			}
			if (StringUtils.isNotEmpty(searchResult.getResult().get(i).getDistrict())) {
				address += searchResult.getResult().get(i).getDistrict() + ", ";
			}
			if (StringUtils.isNotEmpty(searchResult.getResult().get(i).getProvince())) {
				address += searchResult.getResult().get(i).getProvince() + ", ";
			}
			if (StringUtils.isNotEmpty(address)) {
				address = address.substring(0, address.length() - 2);
			}
			searchResult.getResult().get(i).setAddressNo(address);
			
		}
		
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void saveBranch(BranchEmtity entity) {
		if(entity.getBranchid() == null){
			entity.setCreated_date(new Date());
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		branchDao.save(entity);

	}

	@Override
	public BranchEmtity findById(Long CMSId) {
		return branchDao.find(CMSId);
	}

	@Override
	public void updateLocation(String province, String longitude,
			String latitude) {
		branchDao.updateLocation(province, longitude, latitude);
	}

	@Override
	public List<BranchEmtity> findProvince(String province) {
		return branchDao.findProvince(province);
	}
	@Override
	public List<BranchCities> findAll() {
		// TODO Auto-generated method stub
		Search search = new Search();
		search.addSort("province", false);
		List<BranchEmtity> result = branchDao.search(search);
		List<BranchCities> branchLst = new ArrayList<BranchCities>();
		for (BranchEmtity branchs : result) {
			BranchCities brachCities = new BranchCities();
			brachCities.setId(branchs.getBranchid());
			brachCities.setName(branchs.getProvince());			
			branchLst.add(brachCities);
		}
		
		return branchLst;
	}

	@Override
	@Transactional
	public void deleteBranchLocation(Long branchId) {
		 posDao.deletePosLocation(branchId);
		 branchDao.deleteBranchLocation(branchId);
	}

}
