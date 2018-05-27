package vn.com.unit.fe_credit.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import vn.com.unit.fe_credit.bean.QuartzJobBean;
import vn.com.unit.fe_credit.entity.QuartzTriggers;

public interface QuartzJobDao extends GenericDAO<QuartzTriggers, Long>{

	QuartzJobBean getQuartzJob(QuartzJobBean bean);

}
