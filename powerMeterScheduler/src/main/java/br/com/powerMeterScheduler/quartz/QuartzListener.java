package br.com.powerMeterScheduler.quartz;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import br.com.powerMeterScheduler.job.FillDojotDeviceJob;
import br.com.powerMeterScheduler.job.PowerMeterJob;
import br.com.powerMeterScheduler.util.Util;

@WebListener
public class QuartzListener extends QuartzInitializerListener {

	Integer powerMeterRepetitionTime = Integer.valueOf(Util.getConfig(Util.POWER_METER_REPETIION_TIME));
	Integer fillDojotDeviceRepetitionTime = Integer.valueOf(Util.getConfig(Util.FILL_DOJOT_DEVICE_REPETITION_TIME));
	Integer elapseTimeBetweenProcess = Integer.valueOf(Util.getConfig(Util.ELAPSE_TIME_BETWEEN_PROCESS)) * 1000; // converte para milis
	
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        
    	super.contextInitialized(servletContextEvent);
    	ServletContext servletContext = servletContextEvent.getServletContext();
    	StdSchedulerFactory factory = (StdSchedulerFactory) servletContext.getAttribute(QUARTZ_FACTORY_KEY);
        
        try {
    
        	JobKey jobKeyPowerMeter = new JobKey("jobKeyPowerMeter", "equipe3");
        	JobDetail jobPowerMeter = JobBuilder.newJob(PowerMeterJob.class).withIdentity(jobKeyPowerMeter).build();

           	JobKey jobKeyFillDojotDevice = new JobKey("jobKeyFillDojotDevice", "equipe3");
        	JobDetail jobFillDojotDevice = JobBuilder.newJob(FillDojotDeviceJob.class).withIdentity(jobKeyFillDojotDevice).build();

        	Trigger triggerPowerMeter = TriggerBuilder
        			.newTrigger().withIdentity("powerMeterTriggerName", "equipe3").withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(powerMeterRepetitionTime)).build();
        
        	Trigger triggerFillDojotDevice = TriggerBuilder
        			.newTrigger().withIdentity("fillDojotDeviceTriggerName", "equipe3").withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(fillDojotDeviceRepetitionTime)).build();
         	
        	
        	Scheduler scheduler = factory.getScheduler();
        	scheduler.start();
        	scheduler.scheduleJob(jobPowerMeter, triggerPowerMeter);
        	Thread.sleep(elapseTimeBetweenProcess);
        	scheduler.scheduleJob(jobFillDojotDevice, triggerFillDojotDevice);
        	
            
        } catch (Exception e) {
        	
        	servletContext.log("Erro no agendador de tarefas...", e);
        	
        }
    }

}