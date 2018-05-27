package vn.com.unit.fe_credit.bean;

import java.util.List;

import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Loan;

public class LoanBean extends AbstractBean<Loan> {
	private List<Loan> listLoan;

	private String searchField;
	private String succesorfail;

	public List<Loan> getListLoan() {
		return listLoan;
	}

	public void setListLoan(List<Loan> listLoan) {
		this.listLoan = listLoan;
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
	
	

}
