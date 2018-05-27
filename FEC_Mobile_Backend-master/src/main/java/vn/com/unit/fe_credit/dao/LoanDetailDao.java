package vn.com.unit.fe_credit.dao;



import java.util.List;

import vn.com.unit.fe_credit.bean.LoanDetailBean;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.LoanDetail;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;



public interface LoanDetailDao extends GenericDAO<LoanDetail, Long>{
	
	void updateCategoryAndNameByLoanID(Long loanID,String category, String name);
	
	SearchResult<LoanDetail> search(LoanDetailBean bean);

}
