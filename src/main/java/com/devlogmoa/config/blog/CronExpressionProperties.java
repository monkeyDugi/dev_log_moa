package com.devlogmoa.config.blog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter @Setter
@Component
@ConfigurationProperties("cron")
public class CronExpressionProperties {

    private String expression;
}
