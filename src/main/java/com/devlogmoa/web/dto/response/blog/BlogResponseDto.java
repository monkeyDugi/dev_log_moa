package com.devlogmoa.web.dto.response.blog;

import com.devlogmoa.domain.blog.UsageStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BlogResponseDto {

    private Long blogId;
    private String blogTitle;
    private String blogLink;
    private String blogRssLink;
    private UsageStatus usageStatus;
    private Long subscriptionId;

    @Builder
    @QueryProjection
    public BlogResponseDto(Long blogId, String blogTitle, String blogLink, String blogRssLink, UsageStatus usageStatus, Long subscriptionId) {
        this.blogId = blogId;
        this.blogTitle = blogTitle;
        this.blogLink = blogLink;
        this.blogRssLink = blogRssLink;
        this.usageStatus = usageStatus;
        this.subscriptionId = subscriptionId;
    }
}
