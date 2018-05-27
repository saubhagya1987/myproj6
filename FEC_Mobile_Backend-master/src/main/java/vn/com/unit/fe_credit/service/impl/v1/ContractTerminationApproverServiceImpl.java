package vn.com.unit.fe_credit.service.impl.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.fe_credit.dao.v1.ContractTerminationApproverDao;
import vn.com.unit.fe_credit.service.v1.ContractTerminationApproverService;

@Service
public class ContractTerminationApproverServiceImpl implements ContractTerminationApproverService {

	@Autowired
	ContractTerminationApproverDao contractTerminationApproverDao;
	
	@Override
	public Integer deleteContractMappingById(Integer contractTerminationId) {	
		return contractTerminationApproverDao.deleteByTerminationId(contractTerminationId);
	}

	
	@Override
	public void updateApproverEmails(Integer contractTerminationId, String contractId, List<String> approverEmailIds) {

		//throw new RuntimeException();
		contractTerminationApproverDao.updateApproverEmails(contractTerminationId, contractId, approverEmailIds);
		
	}
}
