package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.entity.ContractMessages;

public interface ContractMessagesService {

	List<ContractMessages> findByCustomerId(Long customerId);

	void save(ContractMessages contractMessages);

}
