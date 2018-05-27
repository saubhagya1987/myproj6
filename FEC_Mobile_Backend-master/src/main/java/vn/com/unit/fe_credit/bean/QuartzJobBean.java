package vn.com.unit.fe_credit.bean;

import lombok.Getter;
import lombok.Setter;
import vn.com.unit.fe_credit.entity.QuartzTriggers;

@Getter
@Setter
public class QuartzJobBean extends AbstractBean<QuartzTriggers> {

	String schedName;

	String triggerState;

	String triggerName;

	String triggerGroup;

	String jobName;

	String jobGroup;

	String triggerType;

	Boolean playStatus;

	Boolean pauseStatus;

	String cronExpression;
}
