package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.bean.ContractInstallmentBean;
import vn.com.unit.fe_credit.entity.ContractInstallment;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;

public interface ContractInstallmentDao extends GenericDAO<ContractInstallment, Long> {

	SearchResult<ContractInstallment> search(ContractInstallmentBean bean);
	
	List<ContractInstallment> findByContractDetailID(Long contractdetailID);
	
	ContractInstallment findFirstValuebyContractDetail(Long contractdetailID);
	
}
