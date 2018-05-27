package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;

public class LoanDetailBean extends AbstractBean<LoanDetail> {
	private List<LoanDetail> listLoanDetail;
	private List<Loan> listLoan;
	private String searchField;
	private String succesorfail;

	public List<LoanDetail> getListLoanDetail() {
		return listLoanDetail;
	}

	public void setListLoanDetail(List<LoanDetail> listLoanDetail) {
		this.listLoanDetail = listLoanDetail;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSuccesorfail() {
		return succesorfail;
	}

	public void setSuccesorfail(String succesorfail) {
		this.succesorfail = succesorfail;
	}

	public List<Loan> getListLoan() {
		return listLoan;
	}

	public void setListLoan(List<Loan> listLoan) {
		this.listLoan = listLoan;
	}

}
