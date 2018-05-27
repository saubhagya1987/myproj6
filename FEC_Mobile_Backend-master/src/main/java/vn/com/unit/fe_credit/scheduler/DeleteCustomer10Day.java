package vn.com.unit.fe_credit.scheduler;

import java.util.Calendar;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import vn.com.unit.fe_credit.service.CustomerService;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class DeleteCustomer10Day extends QuartzJobBean  implements ScheduleTask{
	@Autowired
	CustomerService customerService;
	@Override
	public void doTask() {
		Date date = new Date();
		 
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -10); // add 10 days
		 
		date = cal.getTime();
		customerService.deleteCustomerImport(date);
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		doTask();
		
	}

}
