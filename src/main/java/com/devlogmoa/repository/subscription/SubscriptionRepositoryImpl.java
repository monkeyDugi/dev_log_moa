package com.devlogmoa.repository.subscription;

import com.devlogmoa.domain.blog.QBlog;
import com.devlogmoa.domain.blog.QBlogContents;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.domain.member.QMember;
import com.devlogmoa.domain.subscription.QSubscription;
import com.devlogmoa.web.dto.response.subscription.SubscriptionResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

@RequiredArgsConstructor
public class SubscriptionRepositoryImpl implements SubscriptionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SubscriptionResponseDto> findByMemberEmail(String email, Pageable pageable) {
        QueryResults<SubscriptionResponseDto> result =
                queryFactory.select(
                        Projections.constructor(
                                SubscriptionResponseDto.class,
                                QSubscription.subscription.blog.id,
                                QSubscription.subscription.blog.blogTitle,
                                QBlogContents.blogContents.pubDate,
                                QBlogContents.blogContents.title,
                                QBlogContents.blogContents.pubLink
                                )
                        )
                .from(QSubscription.subscription)
                .innerJoin(QBlog.blog)
                .on(QSubscription.subscription.blog.id.eq(QBlog.blog.id))
                .innerJoin(QMember.member)
                .on(QSubscription.subscription.member.id.eq(QMember.member.id))
                .innerJoin(QBlogContents.blogContents)
                .on(QSubscription.subscription.blog.id.eq(QBlogContents.blogContents.blog.id))
                .innerJoin(QBlog.blog)
                .on(QBlog.blog.id.eq(QBlogContents.blogContents.blog.id))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(QBlog.blog.usageStatus.eq(UsageStatus.USE))
                .where(QBlogContents.blogContents.pubDate.gt(LocalDate.now().minusDays(31)))
                .where(QMember.member.email.eq(email))
                .orderBy(QBlogContents.blogContents.pubDate.desc())
                .orderBy(QBlogContents.blogContents.id.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}