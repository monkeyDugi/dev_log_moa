package com.devlogmoa.domain.blog;

import com.devlogmoa.web.dto.RssDto;
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

    public static BlogContents createPublish(RssDto rssDto) {
        BlogContents blogContents = new BlogContents();

        blogContents.pubLink = rssDto.getPubLink();
        blogContents.pubDate = rssDto.getPubDate();
        blogContents.title = rssDto.getTitle();
        blogContents.contents = rssDto.getContents();
        blogContents.blog = rssDto.getBlog();

        return blogContents;
    }
}
