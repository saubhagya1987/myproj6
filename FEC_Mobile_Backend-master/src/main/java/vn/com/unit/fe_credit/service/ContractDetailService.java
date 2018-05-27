package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.ContractDetailBean;
import vn.com.unit.fe_credit.entity.ContractDetail;

public interface ContractDetailService {
	List<ContractDetail> findAll();

	ContractDetailBean search(ContractDetailBean bean);

	void saveContract(ContractDetail entity);

	ContractDetail findById(Long contractId);
	
}
