package vn.com.unit.fe_credit.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import vn.com.unit.fe_credit.bean.Message;
import vn.com.unit.fe_credit.bean.QuartzJobBean;
import vn.com.unit.fe_credit.config.SystemConfig;
import vn.com.unit.fe_credit.entity.QuartzTriggers;
import vn.com.unit.fe_credit.service.QuartzJobService;

@Controller
@RequestMapping("/system/quartz_job")
public class QuartzJobController {

	@Autowired
	private MessageSource msgSrc;

	@Autowired
	SystemConfig systemConfig;

	@Autowired
	QuartzJobService quartzJobService;

	@Autowired
	@Qualifier("schedulerFactoryBeanCluster")
	private SchedulerFactoryBean schedulerFactory;

	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String quartzJob(@ModelAttribute(value = "bean") QuartzJobBean quartzJobBean, Model model, Locale locale) throws Exception {
		QuartzJobBean bean = quartzJobService.getQuartzJob(quartzJobBean);
		model.addAttribute("bean", bean);

		return "system.quartz.trigger";
	}

	@RequestMapping(value = "/play", method = RequestMethod.GET)
	public String playJob(@ModelAttribute(value = "bean") QuartzJobBean quartzJobBean, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse resp, RedirectAttributes redirectAttributes, HttpServletResponse response) throws Exception {
		try {
			String nameTrigger = quartzJobBean.getEntity().getTriggerName();

			Scheduler scheduler = this.schedulerFactory.getScheduler();
			scheduler.resumeTrigger(TriggerKey.triggerKey(nameTrigger, "DEFAULT"));

			quartzJobBean.addMessage(Message.SUCCESS, msgSrc.getMessage("quartz.job.play.trigger", null, locale));
			redirectAttributes.addFlashAttribute("bean", quartzJobBean);
		} catch (Exception e) {
			quartzJobBean.addMessage(Message.ERROR, e.getMessage());
		}
		return "redirect:/system/quartz_job/";
	}

	@RequestMapping(value = "/pause", method = RequestMethod.GET)
	public String pauseJob(@ModelAttribute(value = "bean") QuartzJobBean quartzJobBean, Model model, Locale locale, HttpServletRequest request,
			HttpServletResponse resp, RedirectAttributes redirectAttributes, HttpServletResponse response) throws Exception {
		try {
			String nameTrigger = quartzJobBean.getEntity().getTriggerName();

			Scheduler scheduler = this.schedulerFactory.getScheduler();
			scheduler.pauseTrigger(TriggerKey.triggerKey(nameTrigger, "DEFAULT"));

			quartzJobBean.addMessage(Message.SUCCESS, msgSrc.getMessage("quartz.job.pause.trigger", null, locale));
			redirectAttributes.addFlashAttribute("bean", quartzJobBean);
		} catch (Exception e) {
			quartzJobBean.addMessage(Message.ERROR, e.getMessage());
		}
		return "redirect:/system/quartz_job/";
	}

	@RequestMapping(value = "/loadQuartzEditPopup", method = RequestMethod.GET)
	public String loadQuartzEditPopup(@ModelAttribute(value = "bean") QuartzJobBean quartzJobBean,
			@RequestParam(required = true, value = "schedName") String schedName,
			@RequestParam(required = true, value = "triggerName") String triggerName, Model model, Locale locale) throws Exception {

		quartzJobService.getQuartzJob(quartzJobBean);
		QuartzTriggers quartzTriggers = quartzJobBean.getListResult().get(0);
		String cronExpression = quartzTriggers.getCronExpression();
		quartzJobBean.setCronExpression(cronExpression);
		QuartzJobBean bean = quartzJobService.getQuartzJob(quartzJobBean);
		model.addAttribute("bean", bean);

		return "system.quartz.edit.popup";
	}

	@RequestMapping(value = "/loadQuartzEditPopup", method = RequestMethod.POST)
	public String doSaveCronExpression(@ModelAttribute(value = "bean") QuartzJobBean quartzJobBean,
			@RequestParam(required = true, value = "cronExpression") String cronExpression,
			@RequestParam(required = true, value = "schedName") String schedName,
			@RequestParam(required = true, value = "triggerName") String triggerName, Model model, Locale locale,
			RedirectAttributes redirectAttributes) throws Exception {

		try {
			Scheduler scheduler = this.schedulerFactory.getScheduler();
			TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, "DEFAULT");
			scheduler.getTrigger(triggerKey);
			CronTriggerImpl trigger = (CronTriggerImpl) scheduler.getTrigger(triggerKey);
			try {
				trigger.setCronExpression(StringUtils.trimToEmpty(cronExpression));
				// trigger.setTimeZone(TimeZone.getTimeZone("Asia/Saigon"));
				Date nextFire = DateUtils.addMinutes(new Date(), 1);
				trigger.setStartTime(nextFire);
				System.out.println("##nextFire##" + nextFire);
			} catch (ParseException e1) {
				throw new Exception("Invalid cron expression");
			}

			Date nextFire = scheduler.rescheduleJob(triggerKey, trigger);

			System.out.println("##nextFire2##" + nextFire);
			quartzJobBean.addMessage(Message.SUCCESS, msgSrc.getMessage("msg.save.success", null, locale));
		} catch (Exception e) {
			quartzJobBean.addMessage(Message.ERROR, e.getMessage());
		}

		return "system.quartz.edit.popup";

	}

}
