package com.devlogmoa;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("servers")
public class Yml {

    private List<MyPojo> blog = new ArrayList<>();

    public List<MyPojo> getBlog() {
        return blog;
    }

    public void setBlog(List<MyPojo> blog) {
        this.blog = blog;
    }
}
