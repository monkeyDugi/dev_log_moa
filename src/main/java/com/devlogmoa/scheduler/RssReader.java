package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogProperties;
import com.devlogmoa.config.blog.BlogPropertiesDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class RssReader {

    public RssDtos ReadRss(BlogProperties blogProperties, SyndFeedable syndFeedable) throws IOException {
        List<BlogPropertiesDto> blogPropertiesDtos = blogProperties.getList();
        RssDtos rssDtos = new RssDtos();

        for (BlogPropertiesDto blogPropertiesDto : blogPropertiesDtos) {
            String rssUrl = blogPropertiesDto.getRssUrl(); // 블로그 rss 주소
            rssDtos.addAll(syndFeedable.read(blogPropertiesDto, rssUrl));
        }

        return rssDtos;
    }
}