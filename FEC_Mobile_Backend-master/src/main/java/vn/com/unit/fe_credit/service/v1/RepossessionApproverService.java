package vn.com.unit.fe_credit.service.v1;

import java.util.List;

import vn.com.unit.fe_credit.entity.Role;
import vn.com.unit.fe_credit.entity.collection.RepossessionApprover;

public interface RepossessionApproverService {
	public Integer deleteRepossessionMappingById(Integer repossessionId);

	public void updateApproverEmails(Integer repossessionId, String contractId, List<String> approverEmailIds,RepossessionApprover repossessionApprover,Role role);

	
	public Integer deleteApprover(  Long reposessionId, String bidderId);

}
