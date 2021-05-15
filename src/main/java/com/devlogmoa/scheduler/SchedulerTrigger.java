package com.devlogmoa.scheduler;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SchedulerTrigger {

    @PostConstruct
    public void start() throws SchedulerException{
        // Job 구현 내용이 담긴 클래스로 JobDetail 생성
        JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class)
                .withIdentity("jobName", Scheduler.DEFAULT_GROUP)
                .build();

        // 실행 시점을 결정하는 Trigger 생성
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", Scheduler.DEFAULT_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 6 1/1 * ? *"))
                .build();

        // 스케줄러 실행 및 JobDetail과 Trigger 정보로 스케줄링
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
