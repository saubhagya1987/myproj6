package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.DoubleType;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.TimestampType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import vn.com.unit.fe_credit.bean.ContractInstallmentBean;
import vn.com.unit.fe_credit.bean.LoanBean;
import vn.com.unit.fe_credit.dao.ContractInstallmentDao;
import vn.com.unit.fe_credit.dao.LoanDao;
import vn.com.unit.fe_credit.entity.Banner;
import vn.com.unit.fe_credit.entity.ContractInstallment;
import vn.com.unit.fe_credit.entity.Hobby;
import vn.com.unit.fe_credit.entity.Loan;
import vn.com.unit.fe_credit.entity.LoanDetail;
import vn.com.unit.fe_credit.entity.PaymentHistory;
import vn.com.unit.fe_credit.utils.Utils;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

@Repository
public class ContractInstallmentDaoImpl extends
		GenericDAOImpl<ContractInstallment, Long> implements
		ContractInstallmentDao {
	// @Autowired
	// UserProfile userProfile;
	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public SearchResult<ContractInstallment> search(ContractInstallmentBean bean) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT distinct contractinstallment");
		hql.append(" FROM ContractInstallment contractinstallment ");

		// init filter
		hql.append(" WHERE 1=1 ");

		// build query get list
		Query query = getSession().createQuery(hql.toString());
		// build query get count
		Query countQuery = getSession().createQuery(
				Utils.getCountQuery(hql.toString()));

		int totalCount = 0;
		if (bean.getLimit() > 0) {
			totalCount = ((Long) countQuery.list().get(0)).intValue();
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}

		List<ContractInstallment> lst = query.list();
		SearchResult<ContractInstallment> searchResult = new SearchResult<ContractInstallment>();
		searchResult.setResult(lst);

		if (bean.getLimit() > 0) {
			searchResult.setTotalCount(totalCount);
		}
		return searchResult;
	}

	@Override
	public List<ContractInstallment> findByContractDetailID(
			Long contractdetailID) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT installment.contractinstallmentID as contractinstallmentID, installment.contractNumber as contractNumber, installment.dueDate as dueDate, installment.amount as amount, installment.status as status, installment.paymentDate as paymentDate");
		sql.append(" FROM ContractInstallment installment ");
		sql.append(" JOIN ContractDetail cd on cd.contractdetailID=installment.contractdetailID");
		sql.append(" WHERE cd.contractdetailID = :contractdetailID");
		sql.append(" ORDER BY installment.dueDate desc");

		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("contractinstallmentID", new LongType())
				.addScalar("contractNumber", new StringType())
				.addScalar("dueDate", new TimestampType())
				.addScalar("amount", new DoubleType())
				.addScalar("status", new LongType())
				.addScalar("paymentDate", new TimestampType());
		query.setParameter("contractdetailID", contractdetailID);
		query.setResultTransformer(Transformers
				.aliasToBean(ContractInstallment.class));

		return query.list();
	}

	@Override
	public ContractInstallment findFirstValuebyContractDetail(Long contractdetailID) {

		 StringBuilder sql = new StringBuilder();
		 sql.append(" SELECT CONTRACTINSTALLMENTID as contractinstallmentID, CONTRACTNUMBER as contractNumber, "
		 		+ "DUEDATE as dueDate, AMOUNT as amount, STATUS as status, PAYMENTDATE as paymentDate FROM");
		 sql.append(" (SELECT ci.*, rank() over (order by ci.DUEDATE desc) rn from CONTRACTINSTALLMENT ci where ci.contractdetailID = :contractdetailID)");
		 sql.append(" where rn=1 ");
		
		 Query query = getSession().createSQLQuery(sql.toString())
				 	.addScalar("contractinstallmentID", new LongType())
					.addScalar("contractNumber", new StringType())
					.addScalar("dueDate", new TimestampType())
					.addScalar("amount", new DoubleType())
					.addScalar("status", new LongType())
					.addScalar("paymentDate", new TimestampType());
		 query.setParameter("contractdetailID", contractdetailID);
		 query.setResultTransformer(Transformers.aliasToBean(ContractInstallment.class));
		 List<ContractInstallment> result = query.list();
		 if(result != null && result.size() > 0){
		 return result.get(0);
		 }
		 return null;

//		String hql = "SELECT CONTRACTINSTALLMENTID, CONTRACTNUMBER, DUEDATE, AMOUNT, STATUS, PAYMENTDATE, CONTRACTDETAILID FROM(SELECT ci.*, rank() over (order by ci.DUEDATE desc) rn from CONTRACTINSTALLMENT ci) where rn=1";
//		Query query = getSession().createQuery(hql);
//		query.setMaxResults(1);
//		List<ContractInstallment> result = query.list();
//		if (result != null && result.size() > 0) {
//			return result.get(0);
//		}
//		return null;
	}

}
