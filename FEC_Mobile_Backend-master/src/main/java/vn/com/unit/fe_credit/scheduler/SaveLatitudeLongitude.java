package vn.com.unit.fe_credit.scheduler;

import java.sql.SQLException;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import vn.com.unit.fe_credit.config.CrunchifyMySQLJDBCConnection;
import vn.com.unit.fe_credit.scheduler.ScheduleTask;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
public class SaveLatitudeLongitude extends QuartzJobBean implements ScheduleTask{

	@Override
	public void doTask() {
		CrunchifyMySQLJDBCConnection crunchifyMySQLJDBCConnection = new CrunchifyMySQLJDBCConnection();
		try {
			crunchifyMySQLJDBCConnection.saveDAO();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		doTask();
		
	}

}
