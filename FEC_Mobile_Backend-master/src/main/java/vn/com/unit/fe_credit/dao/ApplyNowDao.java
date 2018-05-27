package vn.com.unit.fe_credit.dao;

import java.util.List;

import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.entity.ApplyNow;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface ApplyNowDao extends GenericDAO<ApplyNow, Long> {

	List<Object[]> search(ApplyNowBean bean);

	Integer countSearch(ApplyNowBean bean);
}
