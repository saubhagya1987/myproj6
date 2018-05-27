package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.LoanDetailBean;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.webservice.LoanCalculator;

public interface LoanDetailService {
	List<LoanDetail> findAll();

	LoanDetailBean search(LoanDetailBean bean);

	void saveLoanDetail(LoanDetail entity);

	LoanDetail findById(Long loandetailId);
	
	List<LoanCalculator> findLoanDetailByLoanType(String condition_category);
	
	void updateCategoryAndNameByLoanID(Long loanID,String category, String name);
	
	Boolean CheckByLoanId(Long loanId);

	Long CheckID(String name,String category);
	
	
}
