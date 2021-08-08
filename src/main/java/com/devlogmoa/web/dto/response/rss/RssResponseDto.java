package com.devlogmoa.web.dto.response.rss;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.util.CustomDateUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Getter @Setter
public class RssResponseDto {

    private Blog blog;
    private LocalDate pubDate;
    private String title;
    private String pubLink;

    private RssResponseDto() {

    }

    public static RssResponseDto newRss(SyndEntry entry, Blog blog) {
        RssResponseDto rssResponseDto = new RssResponseDto();

        rssResponseDto.blog = blog;
        // LocalDate로 변환
        rssResponseDto.pubDate = CustomDateUtils.parseLocalDate(entry.getPublishedDate());
        rssResponseDto.title = entry.getTitle();
        rssResponseDto.pubLink = entry.getLink();

        return rssResponseDto;
    }
}
