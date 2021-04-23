package com.devlogmoa.domain;

import com.devlogmoa.web.dto.BlogDetailDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
public class BlogDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_detail_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private Date pubDate;
    private String title;
    private String pubLink;

    @Lob
    private String contents;

    public boolean isNewPublish(Date publishedDate) {
        if (pubDate == null) {
            return false;
        }

        return pubDate.compareTo(publishedDate) < 0;
    }

    public void updatePublish(BlogDetailDto blogDetailDto) {
        this.pubDate = blogDetailDto.getPubDate();
        this.title = blogDetailDto.getTitle();
        this.contents = blogDetailDto.getContents();
    }

    public static BlogDetail createPublish(BlogDetailDto blogDetailDto) {
        BlogDetail blogDetail = new BlogDetail();

        blogDetail.pubLink = blogDetailDto.getPubLink();
        blogDetail.pubDate = blogDetailDto.getPubDate();
        blogDetail.title = blogDetailDto.getTitle();
        blogDetail.contents = blogDetailDto.getContents();
        blogDetail.blog = blogDetailDto.getBlog();

        return blogDetail;
    }
}
