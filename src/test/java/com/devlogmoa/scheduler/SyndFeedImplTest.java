package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogProperties;
import com.devlogmoa.config.blog.BlogPropertiesDto;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class SyndFeedImplTest {

    // 음.. 검증 어떻게 하지? 로그로는 확인이 되는데..
    @Test
    void ddd() throws IOException {
        // given
        BlogProperties blogProperties = new BlogProperties();

        BlogPropertiesDto blogPropertiesDto = new BlogPropertiesDto();
        blogPropertiesDto.setUrl("https://dev-monkey-dugi.tistory.com/");
        blogPropertiesDto.setRssUrl("https://dev-monkey-dugi.tistory.com/rss");
        blogPropertiesDto.setDescription("monkeyDugi");

        blogProperties.add(blogPropertiesDto);

        RssReader rssReader = new RssReader();
        rssReader.ReadRss(blogProperties, new SyndFeedImpl());
    }
}