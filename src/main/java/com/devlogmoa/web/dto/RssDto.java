package com.devlogmoa.web.dto;

import com.devlogmoa.domain.blog.Blog;
import com.sun.syndication.feed.synd.SyndEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class RssDto {

    private Blog blog;
    private Date pubDate;
    private String title;
    private String pubLink;
    private String contents;

    private RssDto() {

    }

    public static RssDto newRss(SyndEntry entry, Blog blog) {
        RssDto rssDto = new RssDto();

        rssDto.blog = blog;
        rssDto.pubDate = entry.getPublishedDate();
        rssDto.title = entry.getTitle();
        rssDto.pubLink = entry.getLink();
        rssDto.contents = entry.getDescription().getValue();

        return rssDto;
    }

    public static RssDto existNewRss(SyndEntry entry) {
        RssDto rssDto = new RssDto();

        rssDto.pubDate = entry.getPublishedDate();
        rssDto.title = entry.getTitle();
        rssDto.contents = entry.getDescription().getValue();

        return rssDto;
    }
}
