package com.devlogmoa.web.dto;

import com.devlogmoa.domain.Blog;
import com.sun.syndication.feed.synd.SyndEntry;
import lombok.Getter;

import java.util.Date;

@Getter
public class BlogDetailDto {

    private Long id;
    private Blog blog;
    private Date pubDate;
    private String title;
    private String pubLink;
    private String contents;

    public BlogDetailDto(SyndEntry entry, Blog blog) {
        this.blog = blog;
        this.pubDate = entry.getPublishedDate();
        this.title = entry.getTitle();
        this.pubLink = entry.getLink();
        this.contents = entry.getDescription().getValue();
    }

    public BlogDetailDto(SyndEntry entry) {
        this.pubDate = entry.getPublishedDate();
        this.title = entry.getTitle();
        this.contents = entry.getDescription().getValue();
    }
}
