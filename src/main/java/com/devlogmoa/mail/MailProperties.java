package com.devlogmoa.mail;

import com.devlogmoa.util.YamlPropertiesSourceFactory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter @Setter
@Configuration
@PropertySource(value = "classpath:application-mailing.yml", factory = YamlPropertiesSourceFactory.class)
@ConfigurationProperties(prefix = "sender-mail")
public class MailProperties {

    private String id;
    private String password;
}
