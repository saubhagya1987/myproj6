package vn.com.unit.fe_credit.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.ContractInstallmentBean;
import vn.com.unit.fe_credit.dao.ContractInstallmentDao;
import vn.com.unit.fe_credit.entity.ContractInstallment;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.service.ContractInstallmentService;
import vn.com.unit.fe_credit.service.LoanService;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("contractInstallmentService")
@Transactional(readOnly = true)
public class ContractInstallmentServiceImpl implements ContractInstallmentService {
	@Autowired
	private ContractInstallmentDao contractInstallmentDao;

	public ContractInstallmentServiceImpl() {
		super();
	}

	@Override
	public List<ContractInstallment> findAll() {
		// TODO Auto-generated method stub
		return contractInstallmentDao.findAll();
	}

	@Override
	public ContractInstallmentBean search(ContractInstallmentBean bean) {
		// TODO Auto-generated method stub
		Search search = new Search(ContractInstallment.class);
//		search.setMaxResults(bean.getLimit());
//		search.setPage(bean.getPage() - 1);
		
		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		} else {
			search.addSortDesc("dueDate");
		}
		
		SearchResult<ContractInstallment> searchResult = contractInstallmentDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
//		bean.setTotal(searchResult.getTotalCount());
		return bean;
	}

	@Override
	@Transactional
	public void saveContractInstallment(ContractInstallment entity) {
		contractInstallmentDao.save(entity);
	}

	@Override
	public ContractInstallment findById(Long contractInstallmentId) {
		// TODO Auto-generated method stub
		return contractInstallmentDao.find(contractInstallmentId);
	}

	@Override
	public List<ContractInstallment> findByContractDetailID(Long contractdetailID) {
		// TODO Auto-generated method stub
		return contractInstallmentDao.findByContractDetailID(contractdetailID);
	}

	@Override
	public ContractInstallment findFirstValuebyContractDetail(Long contractdetailID) {
		// TODO Auto-generated method stub
		return contractInstallmentDao.findFirstValuebyContractDetail(contractdetailID);
	}


}
