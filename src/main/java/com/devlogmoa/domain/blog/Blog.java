package com.devlogmoa.domain.blog;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String blogLink;

    @Column(nullable = false, unique = true)
    private String blogRssLink;

    @Column(nullable = false)
    private String blogTitle;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private UsageStatus usageStatus;

    public static Blog createBlog(String blogLink, String blogRssLink, String blogTitle) {
        Blog blog = new Blog();

        blog.blogLink = blogLink;
        blog.blogRssLink = blogRssLink;
        blog.blogTitle = blogTitle;
        blog.usageStatus = UsageStatus.USE;

        return blog;
    }

    public void changeStatus(UsageStatus usageStatus) {
        this.usageStatus = usageStatus;
    }
}
