package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.CronExpressionProperties;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Component
public class SchedulerTrigger {

    private final CronExpressionProperties cronExpressionProperties;

    @PostConstruct
    public void start() throws SchedulerException{
        JobDetail jobDetail = JobBuilder.newJob(SchedulerJob.class)
                .withIdentity("jobName", Scheduler.DEFAULT_GROUP)
                .build();

        // 실행 시점을 결정하는 Trigger 생성
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("triggerName", Scheduler.DEFAULT_GROUP)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpressionProperties.getExpression()))
                .build();

        // 스케줄러 실행 및 JobDetail과 Trigger 정보로 스케줄링
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.start();
    }
}
