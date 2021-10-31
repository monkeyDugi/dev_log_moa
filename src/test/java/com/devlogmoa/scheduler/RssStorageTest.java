package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogProperties;
import com.devlogmoa.config.blog.BlogPropertiesDto;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class RssStorageTest {

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
//        RssDtos rssDtos = rssReader.ReadRss(blogProperties, new SyndFeedImpl());
//        System.out.println(rssDtos.toString());

//        RssStorage rssStorage = new RssStorage();
//        rssStorage.save(rssDtos);
    }
}