package vn.com.unit.fe_credit.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author CongDT
 */
public abstract class QuartzJob<T> extends QuartzJobBean implements ScheduleTask {

	protected static final Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {

			// List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
			// if (jobs != null && !jobs.isEmpty()) {
			// for (JobExecutionContext job : jobs) {
			// if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
			// logger.warn("##There's another instance running, : " + this);
			// System.out.println("##There's another instance running, : " + this);
			// return;
			// }
			// }
			// }

			doTask();

		} catch (Exception e) {
			logger.debug("##QuartzJob##" + this.getClass().getName(), e);
		}
	}
}