package com.devlogmoa.domain.blog;

import com.devlogmoa.web.dto.RssDto;
import lombok.Getter;

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

    public boolean isNewPublish(Date publishedDate, String pubLink) {
        if (pubDate == null) {
            return false;
        }

        return pubDate.compareTo(publishedDate) < 0 && !this.pubLink.equals(pubLink);
    }

    public void updatePublish(RssDto rssDto) {
        this.pubDate = rssDto.getPubDate();
        this.title = rssDto.getTitle();
        this.contents = rssDto.getContents();
    }

    public static BlogDetail createPublish(RssDto rssDto) {
        BlogDetail blogDetail = new BlogDetail();

        blogDetail.pubLink = rssDto.getPubLink();
        blogDetail.pubDate = rssDto.getPubDate();
        blogDetail.title = rssDto.getTitle();
        blogDetail.contents = rssDto.getContents();
        blogDetail.blog = rssDto.getBlog();

        return blogDetail;
    }
}
