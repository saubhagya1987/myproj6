package vn.com.unit.fe_credit.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.LoanDao;
import vn.com.unit.fe_credit.entity.Account;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.service.LoanDetailService;
import vn.com.unit.fe_credit.service.LoanService;

@Service("loanService")
@Transactional(readOnly = true)
public class LoanServiceImpl implements LoanService {
	@Autowired
	private LoanDao loanDao;
	@Autowired
	private UserProfile userProfile;
	@Autowired
	private LoanDetailService loanDetailService;

	public LoanServiceImpl() {
		super();
	}

	@Override
	public List<Loan> findAll() {
		// TODO Auto-generated method stub
		return loanDao.findAll();
	}

	@Override
	public LoanBean search(LoanBean bean) {
		// TODO Auto-generated method stub
		Search search = new Search(Loan.class);

		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(),
					"desc".equalsIgnoreCase(bean.getSort()));
		} else {
			search.addSortDesc("createDate");
		}

		SearchResult<Loan> searchResult = loanDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		return bean;
	}

	@Override
	@Transactional
	public void saveLoan(Loan entity) {
		if(entity.getLoanID() == null){
			entity.setCreateDate(new Date());
			if(userProfile.getAccount() != null)
			entity.setCreated_by(userProfile.getAccount().getUsername());
		}else{
			entity.setUpdate_date(new Date());
			if(userProfile.getAccount() != null)
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		loanDao.save(entity);
	}

	@Override
	public Loan findById(Long loanId) {
		// TODO Auto-generated method stub
		return loanDao.find(loanId);
	}

	@Override
	public Loan findConditionNameByLoanID(Long loanID) {
		// TODO Auto-generated method stub
		Search search = new Search(Loan.class);
		search.addFilterEqual("loanID", loanID);
		List<Loan> result = loanDao.search(search);
		if (result.size() > 0)
			return result.get(0);
		return null;
	}

	@Override
	public List<Loan> findByCategory(String category) {
		Search search = new Search(Loan.class);
		if (category != null) {
			search.addFilterEqual("condition_category", category);
		}

		List<Loan> searchResult = loanDao.search(search);
		return searchResult;
	}

	@Override
	public Boolean CheckByLoanConditionName(String name, String category) {
		// TODO Auto-generated method stub
		Search search = new Search(Loan.class);
		search.addField("condition_name");
		search.addField("condition_category");

		if (name != null && category != null) {
			search.addFilterEqual("condition_name", name);
			search.addFilterEqual("condition_category", category);
		}

		List<Object> searchResult = loanDao.search(search);
		if (name != null && searchResult.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Long CheckID(String name, String category) {
		// TODO Auto-generated method stub
		Search search = new Search(Loan.class);
		search.addField("loanID");

		if (name != null && category != null) {
			search.addFilterEqual("condition_name", name);
			search.addFilterEqual("condition_category", category);
		}
		List<Long> loan = loanDao.search(search);
		if (loan.size() > 0)
			return loan.get(0);
		return null;
	}

	@Override
	public String findConditionNameByLoanDetailID(Long loanDetailID) {
		// TODO Auto-generated method stub
		LoanDetail loandetail = loanDetailService.findById(loanDetailID);
		if (loandetail != null)
			return loandetail.getLoan().getCondition_name();
		return "";
	}

	@Override
	public List<Loan> RemoveConditionWhenCreate(String category,Long loanDetailID) {
		// TODO Auto-generated method stub
		return loanDao.RemoveConditionWhenCreate(category,loanDetailID);
	}

}
