package vn.com.unit.fe_credit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import vn.com.unit.fe_credit.bean.QuartzJobBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.dao.QuartzJobDao;
import vn.com.unit.fe_credit.service.QuartzJobService;

@Service("QuartzJobService")
@Transactional(readOnly = true)
public class QuartzJobServiceImpl implements QuartzJobService {

	@Autowired
	private QuartzJobDao quartzJobDao;
	
	@Autowired
	SystemConfig systemConfig;
	
	@Override
	public QuartzJobBean getQuartzJob(QuartzJobBean bean) {
		return quartzJobDao.getQuartzJob(bean);
	}
}
