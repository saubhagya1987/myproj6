package vn.com.unit.fe_credit.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import vn.com.unit.fe_credit.bean.ApplyNowBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.service.ApplyNowService;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@DisallowConcurrentExecution
public class ExportApplyNow extends QuartzJob<ExportApplyNow> {

	@Autowired
	ApplyNowService applyNowService;

	@Autowired
	SystemConfig systemConfig;

	@Override
	public void doTask() throws Exception {

		applyNowService.exportCSVToVTiger(new ApplyNowBean());

	}

}
