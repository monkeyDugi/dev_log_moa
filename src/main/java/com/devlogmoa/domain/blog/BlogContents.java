package com.devlogmoa.domain.blog;

import com.devlogmoa.domain.BaseTimeEntity;
import com.devlogmoa.web.dto.response.rss.RssResponseDto;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Entity
public class BlogContents extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_contents_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    private LocalDate pubDate;
    private String title;
    @Column(length = 1000)
    private String pubLink;

    public boolean isNewPublish(LocalDate publishedDate, String pubLink) {
        if (pubDate == null) {
            return false;
        }

        return pubDate.compareTo(publishedDate) < 0 && !this.pubLink.equals(pubLink);
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
