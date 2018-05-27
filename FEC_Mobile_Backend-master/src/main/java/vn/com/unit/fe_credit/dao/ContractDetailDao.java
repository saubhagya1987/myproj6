package vn.com.unit.fe_credit.dao;



import vn.com.unit.fe_credit.bean.ContractDetailBean;
import vn.com.unit.fe_credit.entity.ContractDetail;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.SearchResult;



public interface ContractDetailDao extends GenericDAO<ContractDetail, Long>{

	SearchResult<ContractDetail> search(ContractDetailBean bean);
}
