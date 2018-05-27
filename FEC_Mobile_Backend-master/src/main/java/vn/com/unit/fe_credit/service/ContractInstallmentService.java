package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.ContractInstallmentBean;
import vn.com.unit.fe_credit.entity.ContractInstallment;

public interface ContractInstallmentService {
	List<ContractInstallment> findAll();

	ContractInstallmentBean search(ContractInstallmentBean bean);

	void saveContractInstallment(ContractInstallment entity);

	ContractInstallment findById(Long contractinstallmentID);

	List<ContractInstallment> findByContractDetailID(Long contractdetailID);

	ContractInstallment findFirstValuebyContractDetail(Long contractdetailID);
}
