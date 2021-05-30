package com.devlogmoa.domain.blog;

import com.devlogmoa.web.dto.response.rss.RssResponseDto;
import lombok.Getter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
public class BlogContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_contents_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private Date pubDate;
    private String title;
    private String pubLink;

    public boolean isNewPublish(Date publishedDate, String pubLink) {
        if (pubDate == null) {
            return false;
        }

        return pubDate.compareTo(publishedDate) < 0 && !this.pubLink.equals(pubLink);
    }

    public void updatePublish(RssResponseDto rssResponseDto) {
        this.pubDate = rssResponseDto.getPubDate();
        this.title = rssResponseDto.getTitle();
    }

    public static BlogContents createPublish(RssResponseDto rssResponseDto) {
        BlogContents blogContents = new BlogContents();

        blogContents.pubLink = rssResponseDto.getPubLink();
        blogContents.pubDate = rssResponseDto.getPubDate();
        blogContents.title = rssResponseDto.getTitle();
        blogContents.blog = rssResponseDto.getBlog();

        return blogContents;
    }
}
