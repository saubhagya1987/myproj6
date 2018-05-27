package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.ContractDetailBean;
import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.dao.ContractDetailDao;
import vn.com.unit.fe_credit.dao.LoanDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.ContractDetail;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.service.ContractDetailService;
import vn.com.unit.fe_credit.service.LoanService;

@Service("contractService")
@Transactional(readOnly = true)
public class ContractDetailServiceImpl implements ContractDetailService {
	@Autowired
	private ContractDetailDao contractDao;

	public ContractDetailServiceImpl() {
		super();
	}

	@Override
	public List<ContractDetail> findAll() {
		// TODO Auto-generated method stub
		return contractDao.findAll();
	}

	@Override
	public ContractDetailBean search(ContractDetailBean bean) {
		// TODO Auto-generated method stub
		Search search = new Search(ContractDetail.class);
		
		if(StringUtils.isNotEmpty(bean.getContractnumber())) {
			search.addFilterOr(Filter.ilike("contractNumber", "%" + bean.getContractnumber().trim() + "%"));
		}
		
		if(StringUtils.isNotEmpty(bean.getFullname())) {
			search.addFilterOr(Filter.ilike("customerName", "%" + bean.getFullname().trim() + "%"));
		}
		
		if(StringUtils.isNotEmpty(bean.getCellphone())){
			search.addFilterOr(Filter.ilike("customer.cellPhone", "%" + bean.getCellphone().trim() + "%"));
		}
		
		if(StringUtils.isNotEmpty(bean.getCustomeridcard())) {
			search.addFilterOr(Filter.ilike("customerID_CardNumber", "%" + bean.getCustomeridcard().trim() + "%"));
		}
		
		if(bean.getFromDate() != null){
			search.addFilterGreaterOrEqual("createddate", bean.getFromDate());
		}
		
		if(bean.getToDate() != null){
			search.addFilterLessOrEqual("createddate", bean.getToDate());
		}

		search.setMaxResults(bean.getLimit());
		search.setPage(bean.getPage() - 1);
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		}
		
		SearchResult<ContractDetail> searchResult = contractDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	
	@Override
	@Transactional
	public void saveContract(ContractDetail entity) {
		contractDao.save(entity);
	}

	@Override
	public ContractDetail findById(Long loanId) {
		// TODO Auto-generated method stub
		return contractDao.find(loanId);
	}


}
