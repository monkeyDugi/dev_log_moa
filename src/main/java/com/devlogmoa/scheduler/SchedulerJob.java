package com.devlogmoa.scheduler;

import com.devlogmoa.util.CustomBeanUtil;
import com.devlogmoa.util.RssReader;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * RssReader의 bean을 조회하여 사용한다.
 * Job을 quartz에서 설정 주기 마다 execute 메서드를 실행하기 때문에 bean에서 관리할 필요 없다.
 */
public class SchedulerJob implements Job {

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        RssReader bean = (RssReader) CustomBeanUtil.getBean("rssReader");
        bean.searchRssData("https://dev-monkey-dugi.tistory.com/");
        bean.searchRssData("https://monkey-dugi.tistory.com/");
        bean.searchRssData("https://jojoldu.tistory.com/");
    }
}
