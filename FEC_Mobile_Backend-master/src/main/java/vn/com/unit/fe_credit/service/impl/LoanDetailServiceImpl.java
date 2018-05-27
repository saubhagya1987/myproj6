package vn.com.unit.fe_credit.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.LoanDetailBean;
import vn.com.unit.fe_credit.bean.UserProfile;
import vn.com.unit.fe_credit.dao.LoanDetailDao;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.service.LoanDetailService;
import vn.com.unit.webservice.LoanCalculator;
import vn.com.unit.webservice.ProofList;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Service("loanDetailService")
@Transactional(readOnly = true)
public class LoanDetailServiceImpl extends GenericDAOImpl<LoanDetail, Long> implements LoanDetailService {

	@Autowired
	private UserProfile userProfile;

	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Autowired
	private LoanDetailDao loanDetailDao;

	public LoanDetailServiceImpl() {
		super();
	}

	@Override
	public List<LoanDetail> findAll() {
		// TODO Auto-generated method stub
		return loanDetailDao.findAll();
	}

	@Override
	public LoanDetailBean search(LoanDetailBean bean) {
		// TODO Auto-generated method stub
		Search search = new Search(LoanDetail.class);

		if (StringUtils.isNotEmpty(bean.getDir())) {
			search.addSort(bean.getDir(), "desc".equalsIgnoreCase(bean.getSort()));
		} else {
			search.addSortDesc("createDate");
		}
		SearchResult<LoanDetail> searchResult = loanDetailDao.searchAndCount(search);
		bean.setListResult(searchResult.getResult());
		return bean;
	}

	@Override
	@Transactional
	public void saveLoanDetail(LoanDetail entity) {
		if (entity.getLoanDetailID() == null) {
			entity.setCreateDate(new Date());
			entity.setCreated_by(userProfile.getAccount().getUsername());
		} else {
			entity.setUpdate_date(new Date());
			entity.setUpdate_by(userProfile.getAccount().getUsername());
		}
		loanDetailDao.save(entity);
	}

	@Override
	public LoanDetail findById(Long loandetailId) {
		// TODO Auto-generated method stub
		return loanDetailDao.find(loandetailId);
	}

	@Override
	public void updateCategoryAndNameByLoanID(Long loanID, String category, String name) {
		// TODO Auto-generated method stub
		loanDetailDao.updateCategoryAndNameByLoanID(loanID, category, name);
	}

	@Override
	public List<LoanCalculator> findLoanDetailByLoanType(String condition_category) {
		Search search = new Search();
		search.addFilterEqual("loan.condition_category", condition_category);
		List<LoanDetail> result = loanDetailDao.search(search);

		List<LoanCalculator> calculators = new ArrayList<LoanCalculator>();

		for (LoanDetail loanDetail : result) {
			ProofList proofList = new ProofList();
			proofList.setId(loanDetail.getLoan().getCondition_category());
			proofList.setText(loanDetail.getLoan().getCondition_name());
			proofList.setCondition_value(loanDetail.getLoan().getCondition_value());
			proofList.setText_en(loanDetail.getLoan().getCondition_name_en());
			proofList.setText_vi(loanDetail.getLoan().getCondition_name());

			LoanCalculator calculator = new LoanCalculator();
			calculator.setLoanID(loanDetail.getLoan().getLoanID());
			calculator.setAmountMin(loanDetail.getMinamount());
			calculator.setAmountMax(loanDetail.getMaxamount());
			calculator.setTenureMin(loanDetail.getMintenure());
			calculator.setTenureMax(loanDetail.getMaxtenure());
			calculator.setAmountslide(loanDetail.getAmountslide());
			calculator.setTenureperslide(loanDetail.getTenureperslide());
			calculator.setTenure(loanDetail.getTenure());
			calculator.setProofList(proofList);

			calculators.add(calculator);
		}
		return calculators;
	}

	@Override
	public Boolean CheckByLoanId(Long loanId) {
		// TODO Auto-generated method stub
		Search search = new Search(LoanDetail.class);
		search.addField("loanDetailID");

		if (loanId != null) {
			search.addFilterEqual("loan.loanID", loanId);
		}

		List<Object> searchResult = loanDetailDao.search(search);
		if (loanId != null && searchResult.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public Long CheckID(String name, String category) {
		// TODO Auto-generated method stub
		Search search = new Search(LoanDetail.class);
		search.addField("loanDetailID");

		if (name != null && category != null) {
			search.addFilterEqual("loan.condition_name", name);
			search.addFilterEqual("loan.condition_category", category);
		}
		List<Long> loandetail = loanDetailDao.search(search);
		if (loandetail.size() > 0)
			return loandetail.get(0);
		return null;
	}

}
