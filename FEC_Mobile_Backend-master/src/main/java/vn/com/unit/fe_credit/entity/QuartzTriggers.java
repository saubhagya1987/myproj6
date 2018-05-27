package vn.com.unit.fe_credit.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@Entity
@Table(name = "QRTZ_TRIGGERS")
@JsonAutoDetect
public class QuartzTriggers {

	@Id
	@GeneratedValue
	@Column(name = "SCHED_ID")
	private String schedId;

	@Column(name = "SCHED_NAME")
	private String schedName;

	@Column(name = "TRIGGER_NAME")
	private String triggerName;

	@Column(name = "TRIGGER_GROUP")
	private String triggerGroup;

	@Column(name = "JOB_NAME")
	private String jobName;

	@Column(name = "JOB_GROUP")
	private String jobGroup;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "NEXT_FIRE_TIME")
	private Long nextFireTime;

	@Column(name = "PREV_FIRE_TIME")
	private Long prevFireTime;

	@Column(name = "PRIORITY")
	private Long priority;

	@Column(name = "TRIGGER_STATE")
	private String triggerState;

	@Column(name = "TRIGGER_TYPE")
	private String triggerType;

	@Column(name = "START_TIME")
	private Long startTime;

	@Column(name = "END_TIME")
	private Long endTime;

	@Column(name = "CALENDAR_NAME")
	private String calendarName;

	@Column(name = "MISFIRE_INSTR")
	private Integer misfireInstr;

	@Column(name = "JOB_DATA")
	private Long jobData;

	@Transient
	private String cronExpression;

	public String getSchedId() {
		return schedId;
	}

	public void setSchedId(String schedId) {
		this.schedId = schedId;
	}

	public String getSchedName() {
		return schedName;
	}

	public void setSchedName(String schedName) {
		this.schedName = schedName;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getNextFireTime() {
		return nextFireTime;
	}

	public void setNextFireTime(Long nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public Long getPrevFireTime() {
		return prevFireTime;
	}

	public void setPrevFireTime(Long prevFireTime) {
		this.prevFireTime = prevFireTime;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getTriggerState() {
		return triggerState;
	}

	public void setTriggerState(String triggerState) {
		this.triggerState = triggerState;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public Long getStartTime() {
		return startTime;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public Integer getMisfireInstr() {
		return misfireInstr;
	}

	public void setMisfireInstr(Integer misfireInstr) {
		this.misfireInstr = misfireInstr;
	}

	public Long getJobData() {
		return jobData;
	}

	public void setJobData(Long jobData) {
		this.jobData = jobData;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

}
