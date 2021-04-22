package com.devlogmoa.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
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
        return pubDate.compareTo(publishedDate) < 0;
    }

    public void updatePublish(Date pubDate, String title, String description) {
        this.pubDate = pubDate;
        this.title = title;
        this.contents = description;
    }

    public void createPublish(String link, Date pubDate, String title, String description, Blog blog) {
        this.pubLink = link;
        this.pubDate = pubDate;
        this.title = title;
        this.contents = description;
        this.blog = blog;
    }
}
