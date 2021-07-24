package com.devlogmoa.web.dto.response.blog;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogContents;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BlogContentsResponseDto {

    private Long blogContentsId;
    private Blog blog;
    private Date pubDate;
    private String title;
    private String pubLink;

    public BlogContentsResponseDto(BlogContents entity) {
        this.blogContentsId = entity.getId();
        this.blog = entity.getBlog();
        this.pubDate = entity.getPubDate();
        this.title = entity.getTitle();
        this.pubLink = entity.getPubLink();
    }
}
