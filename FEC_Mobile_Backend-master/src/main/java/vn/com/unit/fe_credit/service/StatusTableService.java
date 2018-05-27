package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.entity.StatusTable;

public interface StatusTableService {
	List<StatusTable> findAllex();
	
	StatusTable findActive();
}
