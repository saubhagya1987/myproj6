package vn.com.unit.fe_credit.dao;



import java.util.List;

import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;



public interface LoanDao extends GenericDAO<Loan, Long>{

	SearchResult<Loan> search(LoanBean bean);
	
	List<Loan> RemoveConditionWhenCreate(String category,Long loanDetailID);
}
