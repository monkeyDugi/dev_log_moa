package com.devlogmoa.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class SchedulerJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("================= job 실행 ===============");
        System.out.println(new Date().getTime());

    }
}
