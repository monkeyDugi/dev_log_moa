package com.devlogmoa.domain.subscription;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscription_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
