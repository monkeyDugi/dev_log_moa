package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogProperties;
import com.devlogmoa.config.blog.BlogPropertiesDto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RssReader {

    public SyndFeedDtos ReadRss(BlogProperties blogProperties, SyndFeedable syndFeedable) throws IOException {
        List<BlogPropertiesDto> blogPropertiesDtos = blogProperties.getList();
        SyndFeedDtos syndFeedDtos = new SyndFeedDtos();

        for (BlogPropertiesDto blogPropertiesDto : blogPropertiesDtos) {
            String rssUrl = blogPropertiesDto.getRssUrl(); // 블로그 rss 주소
            syndFeedDtos.add(syndFeedable.read(blogPropertiesDto, rssUrl));
        }

        return syndFeedDtos;
    }
}