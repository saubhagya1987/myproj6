package vn.com.unit.fe_credit.scheduler;

/**
 * @author CongDT
 * @since Jul 20, 2016
 */
public interface ScheduleTask {

	enum JobStatus {

		RUN_JOB_STARTING(1), RUN_JOB_RUNNING(2), RUN_JOB_COMPLETED(3), RUN_JOB_ERROR(3), RUN_JOB_UNKNOWN(4), RUN_JOB_CANCELED(5);

		private int intValue;

		private JobStatus(int intValue) {
			this.intValue = intValue;
		}

		public int getIntValue() {
			return this.intValue;
		}

	}

	public void doTask() throws Exception;
}
