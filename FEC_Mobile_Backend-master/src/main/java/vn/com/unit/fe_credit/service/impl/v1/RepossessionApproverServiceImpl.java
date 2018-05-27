package vn.com.unit.fe_credit.service.impl.v1;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.unit.fe_credit.dao.v1.RepossessionApproverDao;
import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;
import vn.com.unit.fe_credit.service.v1.RepossessionApproverService;
@Service
public class RepossessionApproverServiceImpl implements RepossessionApproverService {
	@Autowired
	RepossessionApproverDao repossessionApproverDao;
	
	@Override
	public Integer deleteRepossessionMappingById(Integer repossessionId) {	
		return repossessionApproverDao.deleteByRepossessionId(repossessionId);
	}

	
	@Override
	public void updateApproverEmails(Integer repossessionId, String contractId, List<String> approverEmailIds,RepossessionApprover repossessionApprover,Role role) {

		//throw new RuntimeException();
		repossessionApproverDao.updateApproverEmails(repossessionId, contractId, approverEmailIds,repossessionApprover,role);
		
	}
	@Override
	public Integer deleteApprover(Long id,  String bidderId){
		return repossessionApproverDao.deleteByRepossessionAndBidderId( id,  bidderId);
	}



}
