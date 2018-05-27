package vn.com.unit.fe_credit.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import vn.com.unit.webservice.NetClientGet;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SendMessagerTask extends QuartzJobBean implements ScheduleTask {

	@Override
	public void doTask() {

		try {
			NetClientGet.syncMessage();
		} catch (Exception e) {

		}

	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		doTask();

	}
}
