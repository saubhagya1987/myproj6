package vn.com.unit.fe_credit.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class DemoTask implements ScheduleTask {

	@Autowired
	private static final Logger logger = LoggerFactory.getLogger(DemoTask.class);

	@Override
	public void doTask() {
 		
		
	}
}
