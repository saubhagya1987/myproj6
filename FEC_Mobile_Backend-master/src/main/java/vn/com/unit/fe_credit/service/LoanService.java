package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Loan;

public interface LoanService {
	List<Loan> findAll();

	LoanBean search(LoanBean bean);

	void saveLoan(Loan entity);

	Loan findById(Long loanId);
	
	Loan findConditionNameByLoanID(Long loanID);
	
	List<Loan> findByCategory(String category);
	
	Boolean CheckByLoanConditionName(String name,String category);
	
	Long CheckID(String name,String category);
	
	String findConditionNameByLoanDetailID(Long loanID);
	
	List<Loan> RemoveConditionWhenCreate(String category,Long loanDetailID);
}
