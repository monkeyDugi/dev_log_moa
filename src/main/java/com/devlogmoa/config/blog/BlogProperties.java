package com.devlogmoa.config.blog;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Component
@ConfigurationProperties("blogs")
public class BlogProperties {

    private List<BlogPropertiesDto> list = new ArrayList<>();

    public void add(BlogPropertiesDto blogPropertiesDto) {
        list.add(blogPropertiesDto);
    }
}
