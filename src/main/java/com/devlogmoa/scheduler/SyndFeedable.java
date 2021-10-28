package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogPropertiesDto;

import java.io.IOException;
import java.util.List;

public interface SyndFeedable {

    List<RssDto> read(BlogPropertiesDto blogPropertiesDto, String rssUrl) throws IOException;
}
