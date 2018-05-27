package vn.com.unit.fe_credit.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;

import vn.com.unit.fe_credit.bean.POSBean;
import vn.com.unit.fe_credit.dao.POSDao;
import vn.com.unit.fe_credit.entity.PosEmtity;
import vn.com.unit.webservice.BranchCities;

@Repository
public class POSDaoImpl extends GenericDAOImpl<PosEmtity, Long> implements POSDao {


	@Override
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}


	@Override
	public void updateLocation(String province, String longitude,
			String latitude,String district,Long branchId) {
		String hql = " " + "update pos set longitude  =:longitude  , latitude   =:latitude ,POS_PROVINCEID   =:POS_PROVINCEID " + "  "
				+ "where province =:province " + "  " +"and district=:district";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("longitude", longitude);
		query.setParameter("latitude", latitude);
		query.setParameter("province", province);
		query.setParameter("district", district);
		query.setParameter("POS_PROVINCEID", branchId);
		query.executeUpdate();
		
	}


	@Override
	public List<PosEmtity> findDistrict(String district) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.posid from POS  p where p.district=:district");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("posid", new LongType());
		query.setParameter("district", district);
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		return query.list();
	}



	@Override
	public List<PosEmtity> findByLongAndLat(String longitude, String latitude, String distance, String buyorpay,int page) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.posid as posid, p.address_number as address_number, p.pos_name as pos_name,p.longitude as longitude, p.latitude as latitude, p.branch_namepos as branch_namepos, "
				+ "(acos(sin(:latitude) * sin(p.latitude) + cos(:latitude) * cos(p.latitude) * cos(p.longitude - (:longitude))) * 6371) as distance ");
		sql.append(" from POS p ");
		sql.append(" where (acos(sin(:latitude) * sin(p.latitude) + cos(:latitude) * cos(p.latitude) * cos(p.longitude - (:longitude))) * 6371) <= :distance ");
		sql.append(" AND lower(p.buyOrPay)=lower(:buyOrPay) order by distance asc ");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("posid", new LongType())
				.addScalar("address_number", new StringType())
				.addScalar("pos_name", new StringType())
				.addScalar("longitude", new StringType())
				.addScalar("latitude", new StringType())
				.addScalar("branch_namepos", new StringType())
				.addScalar("distance", new StringType());
		query.setParameter("latitude", latitude);
		query.setParameter("longitude", longitude);
		query.setParameter("distance", distance);
		query.setParameter("buyOrPay", buyorpay);
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		query.setMaxResults(20);
		query.setFirstResult((page - 1) * 20);
		return query.list();
	}
	
	
	@Override
	public List<PosEmtity> findByLongAndLatTmp(String longitude, String latitude, String distance, String buyorpay,int page) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.posid as posid, p.address_number as address_number, p.pos_name as pos_name,p.longitude as longitude, p.latitude as latitude, p.branch_namepos as branch_namepos, "
				+ "'' as distance ");
		sql.append(" from POS p ");
		sql.append(" where 1=1 ");
		sql.append(" AND lower(p.buyOrPay)=lower(:buyOrPay) order by p.branch_namepos, p.district desc ");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("posid", new LongType())
				.addScalar("address_number", new StringType())
				.addScalar("pos_name", new StringType())
				.addScalar("longitude", new StringType())
				.addScalar("latitude", new StringType())
				.addScalar("branch_namepos", new StringType())
				.addScalar("distance", new StringType());
		//query.setParameter("latitude", latitude);
		//query.setParameter("longitude", longitude);
		//query.setParameter("distance", distance);
		query.setParameter("buyOrPay", buyorpay);
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		query.setMaxResults(20);
		query.setFirstResult((page - 1) * 20);
		return query.list();
	}


	@Override
	public List<PosEmtity> findByBranchListByCity(Long pos_provinceID, int page, String buyOrPay) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT pos.posid AS posid, pos.district AS district, pos.longitude AS longitude, pos.latitude AS latitude, pos.branch_namepos as branch_namepos ");
		hql.append(" FROM POS pos ");
		hql.append(" WHERE 1=1 ");
		hql.append(" and pos.iddistrict=:pos_provinceID AND lower(pos.buyOrPay) =lower(:buyOrPay) order by pos.branch_namepos, pos.district desc ");
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("posid", new LongType())
				.addScalar("district", new StringType())
				.addScalar("longitude", new StringType())
				.addScalar("latitude" , new StringType())
				.addScalar("branch_namepos", new StringType());
		query.setParameter("pos_provinceID", pos_provinceID);
		query.setParameter("buyOrPay", buyOrPay);
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		query.setMaxResults(20);
		query.setFirstResult((page - 1) * 20);
		
		List<PosEmtity> lst = query.list();

		return lst;
	}

	@Override
	public List<PosEmtity> GetPOSListBySearch(PosEmtity pos) {
		
		Integer page = Integer.parseInt(pos.getPage());
		Integer limit = pos.getLimit();
		if(limit == null){
			limit = 20;
		}
		
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT pos.posid AS posid, pos.branch_namepos AS branch_namepos,pos.address_number AS address_number, ");
		hql.append(" pos.longitude AS longitude, pos.latitude AS latitude, pos.pos_name as pos_name ");
		hql.append(" FROM POS pos ");
		hql.append(" WHERE 1=1 ");
		if(StringUtils.isNotEmpty(pos.getPos_name())){
			hql.append(" AND ( ");
			hql.append(" CONVERTTOUNSIGN(lower(pos.address_number)) like CONVERTTOUNSIGN(lower(:pos_name)) " );
			hql.append(" OR CONVERTTOUNSIGN(lower(pos.pos_name)) like CONVERTTOUNSIGN(lower(:pos_name)) "  );
			hql.append(" ) ");
		}		
		hql.append(" AND lower(pos.buyOrPay) =lower(:buyOrPay) ");
		hql.append(" order by pos.pos_name asc  ");				
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("posid", new LongType())
				.addScalar("address_number", new StringType())
				.addScalar("longitude", new StringType())
				.addScalar("latitude" , new StringType())
				.addScalar("pos_name", new StringType())
				.addScalar("branch_namepos", new StringType());
	 	
		query.setParameter("buyOrPay", pos.getBuyOrPay());
		if (StringUtils.isNotBlank(pos.getPos_name())) {
			query.setParameter("pos_name", "%" + StringUtils.trim(pos.getPos_name()) + "%");
		}

		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		query.setMaxResults(limit);
		query.setFirstResult((page - 1) * limit);
		
		List<PosEmtity> lst = query.list();

		return lst;
	}
	

	@Override
	public void deletePosLocation(Long branchid) {
		String hql = " " + "delete from POS where POS_PROVINCEID  =:branchid" + "  ";
		Query query = getSession().createSQLQuery(hql);
		query.setParameter("branchid", branchid);
		query.executeUpdate();
	}




	@Override
	public List<PosEmtity> findProvinceDistrict(Long pos_provinceID, String district) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT pos.iddistrict AS iddistrict ,pos.posid AS posid, pos.district AS district, pos.longitude AS longitude, pos.latitude AS latitude, pos.branch_namepos as branch_namepos ");
		hql.append(" FROM POS pos ");
		hql.append(" WHERE 1=1 ");
		hql.append(" and pos.pos_provinceID=:pos_provinceID ");
		hql.append(" and pos.district=:district");
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("iddistrict", new LongType())
				.addScalar("posid", new LongType())
				.addScalar("district", new StringType())
				.addScalar("longitude", new StringType())
				.addScalar("latitude" , new StringType())
				.addScalar("branch_namepos", new StringType());
		query.setParameter("pos_provinceID", pos_provinceID);
		query.setParameter("district", district);
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		query.setMaxResults(20);
		List<PosEmtity> lst = query.list();

		return lst;
	}

	@Override
	public List<PosEmtity> maxId() {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT * FROM ( ");
		hql.append(" SELECT pos.IDDISTRICT as iddistrict");
		hql.append(" FROM POS pos ");
		hql.append(" WHERE  pos.IDDISTRICT IS NOT NULL ORDER BY pos.IDDISTRICT DESC ) ");
		hql.append(" WHERE ROWNUM = 1 ");
		Query query = getSession().createSQLQuery(hql.toString())
				.addScalar("iddistrict", new LongType());
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		List<PosEmtity> lst = query.list();	
		return lst;
	}

	@Override
	public List<BranchCities> findByBranchID(Long cityID) {
		// TODO Auto-generated method stub
				StringBuilder hql = new StringBuilder();
				hql.append(" SELECT distinct pos.iddistrict AS id, pos.district AS name ");
				hql.append(" FROM POS pos ");
				hql.append(" WHERE 1=1 ");
				hql.append(" and pos.pos_provinceID=:cityID");
				hql.append(" order by pos.district asc");
				Query query = getSession().createSQLQuery(hql.toString())
						.addScalar("id", new LongType())
						.addScalar("name", new StringType());
				query.setParameter("cityID", cityID);
				query.setResultTransformer(Transformers.aliasToBean(BranchCities.class));			
				List<BranchCities> lst = query.list();
				return lst;
	}




	@Override
	public List<PosEmtity> findCodePosBranch(String code_branch_pos) {
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.posid from POS  p where p.code_branch_pos=:code_branch_pos");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("posid", new LongType());
		query.setParameter("code_branch_pos", code_branch_pos);
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		return query.list();
	}

	@Override
	public List<PosEmtity> getListDAONullValue() {
		StringBuilder sql = new StringBuilder();
		sql.append(" select p.address_number,p.posid from POS  p where p.longitude is null and p.latitude is null");
		Query query = getSession().createSQLQuery(sql.toString())
				.addScalar("posid", new LongType())
				.addScalar("address_number", new StringType());
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		return query.list();
	}

	@Override
	public POSBean search(POSBean bean) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT POS_NAME AS pos_name, ADDRESS_NUMBER AS address_number, ADDRESS_STREET AS address_street,  ");
		sql.append(" PROVINCE AS province, LONGITUDE AS longitude, LATITUDE AS latitude, BRANCH_NAMEPOS AS branch_namepos,  ");
		sql.append(" BUYORPAY AS buyOrPay, AWARD AS award, DISTRICT AS district ");
		sql.append(" FROM POS  ");
		sql.append(" WHERE 1=1");
		
		String searchField = bean.getSearchField();
		if (StringUtils.isNotBlank(searchField)) {
			sql.append(" AND ( " );
			sql.append(" CONVERTTOUNSIGN(lower(BUYORPAY)) like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(POS_NAME))   like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(BRANCH_NAMEPOS))   like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(DISTRICT))   like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(AWARD))  like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(ADDRESS_STREET))  like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(ADDRESS_NUMBER )) like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" OR CONVERTTOUNSIGN(lower(PROVINCE))     like CONVERTTOUNSIGN(lower(:searchField))   ");
			sql.append(" ) ");
		}
		
		String sqlCount = "select count(*) from (" + sql.toString() + ")";
		
		Query query = getSession().createSQLQuery(sql.toString())
						.addScalar("pos_name",new StringType())
						.addScalar("address_number",new StringType())
						.addScalar("address_street",new StringType())
						.addScalar("province",new StringType())
						.addScalar("longitude",new StringType())
						.addScalar("latitude",new StringType())
						.addScalar("branch_namepos",new StringType())
						.addScalar("buyOrPay",new StringType())	
						.addScalar("award",new StringType())
						.addScalar("district",new StringType());
		query.setResultTransformer(Transformers.aliasToBean(PosEmtity.class));
		Query queryCount = getSession().createSQLQuery(sqlCount.toString());
		
		if (StringUtils.isNotBlank(searchField)) {
			query.setParameter("searchField", "%" + StringUtils.trim(searchField) + "%");
			queryCount.setParameter("searchField", "%" + StringUtils.trim(searchField) + "%");
		}

		int totalCount = 0;
		if(bean.getLimit() > 0){
			totalCount = Integer.valueOf(queryCount.uniqueResult().toString());
			query.setMaxResults(bean.getLimit());
			query.setFirstResult((bean.getPage() - 1) * bean.getLimit());
		}		
		bean.setTotal(totalCount);
		bean.setListResult(query.list());
		return bean;
	}
	
}
