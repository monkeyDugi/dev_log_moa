package com.devlogmoa.web.dto.response.rss;

import com.devlogmoa.domain.blog.Blog;
import com.sun.syndication.feed.synd.SyndEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class RssResponseDto {

    private Blog blog;
    private Date pubDate;
    private String title;
    private String pubLink;

    private RssResponseDto() {

    }

    public static RssResponseDto newRss(SyndEntry entry, Blog blog) {
        RssResponseDto rssResponseDto = new RssResponseDto();

        rssResponseDto.blog = blog;
        rssResponseDto.pubDate = entry.getPublishedDate();
        rssResponseDto.title = entry.getTitle();
        rssResponseDto.pubLink = entry.getLink();

        return rssResponseDto;
    }

    public static RssResponseDto existNewRss(SyndEntry entry) {
        RssResponseDto rssResponseDto = new RssResponseDto();

        rssResponseDto.pubDate = entry.getPublishedDate();
        rssResponseDto.title = entry.getTitle();

        return rssResponseDto;
    }
}
