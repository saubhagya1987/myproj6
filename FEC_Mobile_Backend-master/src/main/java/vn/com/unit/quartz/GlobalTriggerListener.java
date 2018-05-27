package vn.com.unit.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

public class GlobalTriggerListener implements TriggerListener {

	private static final String TRIGGER_LISTENER_NAME = "GlobalTriggerListener";

	@Override
	public String getName() {
		return TRIGGER_LISTENER_NAME;
	}

	@Override
	public void triggerFired(Trigger trigger, JobExecutionContext context) {
		String triggerName = context.getJobDetail().getKey().toString();
		System.out.println("##trigger : " + triggerName + " is fired");
	}

	@Override
	public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
		boolean veto = false;
		System.out.println("Veto Job Excecution trigger: " + veto);
		return veto;
	}

	@Override
	public void triggerMisfired(Trigger trigger) {
		System.out.println(getName() + " trigger: " + trigger.getKey() + " misfired at " + trigger.getStartTime());
	}

	@Override
	public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
		System.out.println(getName() + " trigger: " + trigger.getKey() + " completed at " + trigger.getStartTime());
	}
}