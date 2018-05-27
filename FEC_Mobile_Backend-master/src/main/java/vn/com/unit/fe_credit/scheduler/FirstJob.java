package vn.com.unit.fe_credit.scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.SystemSetting;
import vn.com.unit.fe_credit.service.SystemSettingService;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@DisallowConcurrentExecution
public class FirstJob extends QuartzJobBean implements ScheduleTask {

	Logger logger = LoggerFactory.getLogger("FirstJob");

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	SystemSettingService systemSettingService;

	@Override
	public void doTask() {

		try {
			// Update config
			List<SystemSetting> settings = systemSettingService.findAll();
			Map<String, String> configMap = new HashMap<>();
			for (SystemSetting systemSetting : settings) {
				configMap.put(systemSetting.getKey(), systemSetting.getValue());
			}
			systemConfig.setConfigMap(configMap);

		} catch (Exception e) {
			System.out.println("##UpdateSystemConfigException##" + e.getMessage());
		}

	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

		System.out.println("##FirstJob##" + new Date());
		doTask();

	}

}