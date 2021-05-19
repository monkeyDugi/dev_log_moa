package com.devlogmoa.web.dto.response.blog;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.BlogDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class BlogDetailDto {

    private Long id;
    private Blog blog;
    private Date pubDate;
    private String title;
    private String pubLink;

    public BlogDetailDto(BlogDetail entity) {
        this.id = entity.getId();
        this.blog = entity.getBlog();
        this.pubDate = entity.getPubDate();
        this.title = entity.getTitle();
        this.pubLink = entity.getPubLink();
    }
}
