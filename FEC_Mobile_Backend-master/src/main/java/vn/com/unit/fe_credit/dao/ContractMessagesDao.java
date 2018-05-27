package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.entity.ContractMessages;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface ContractMessagesDao extends GenericDAO<ContractMessages, Long>{

	List<ContractMessages> findByCustomerId(Long customerId);

}
