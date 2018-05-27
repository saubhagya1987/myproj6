package vn.com.unit.fe_credit.service;

import java.util.List;

import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.entity.ApplyNow;

public interface ApplyNowService {

	List<Object[]> search(ApplyNowBean bean);

	Integer countSearch(ApplyNowBean bean);

	void saveApplyNow(ApplyNow entity);

	ApplyNowBean searchEx(ApplyNowBean bean);

	ApplyNow findById(Long id);

	List<ApplyNow> searchApplyNow(long customerId, Long loanId, String product, long status) throws Exception;

	void exportCSVToVTiger(ApplyNowBean applyNowBean) throws Exception;

}
