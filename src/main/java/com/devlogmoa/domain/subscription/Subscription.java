package com.devlogmoa.domain.subscription;

import com.devlogmoa.domain.BaseTimeEntity;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.member.Member;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Subscription extends BaseTimeEntity {

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

    public static Subscription Subscribe(Blog blog, Member member) {
        Subscription subscription = new Subscription();
        subscription.blog = blog;
        subscription.member = member;

        return subscription;
    }
}
