package com.devlogmoa.scheduler;

import com.devlogmoa.config.blog.BlogPropertiesDto;

import java.io.IOException;

public interface SyndFeedable {

    FeedDtos read(BlogPropertiesDto blogPropertiesDto, String rssUrl) throws IOException;
}
