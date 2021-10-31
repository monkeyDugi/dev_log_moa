package com.devlogmoa.web.dto.response.rss;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.scheduler.FeedDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class RssResponseDto {

    private Blog blog;
    private LocalDate pubDate;
    private String title;
    private String pubLink;

    private RssResponseDto() {

    }

    public static RssResponseDto newRss(FeedDto feedDto, Blog blog) {
        RssResponseDto rssResponseDto = new RssResponseDto();

        rssResponseDto.blog = blog;
        rssResponseDto.pubDate = feedDto.getPubDate();
        rssResponseDto.title = feedDto.getTitle();
        rssResponseDto.pubLink = feedDto.getPubLink();

        return rssResponseDto;
    }

    public static RssResponseDto newRss(LocalDate pubDate) {
        RssResponseDto rssResponseDto = new RssResponseDto();

        rssResponseDto.blog = null;
        rssResponseDto.title = null;
        rssResponseDto.pubLink = null;
        rssResponseDto.pubDate = pubDate;

        return rssResponseDto;
    }
}
