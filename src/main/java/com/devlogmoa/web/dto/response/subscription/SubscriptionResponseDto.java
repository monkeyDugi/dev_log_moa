package com.devlogmoa.web.dto.response.subscription;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class SubscriptionResponseDto {

    private Long blogId;
    private String blogTitle;
    private Date pubDate;
    private String title;
    private String pubLink;

    @Builder
    @QueryProjection
    public SubscriptionResponseDto(Long blogId, String blogTitle, Date pubDate, String title, String pubLink) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.pubDate = pubDate;
        this.title = title;
        this.pubLink = pubLink;
    }
}
