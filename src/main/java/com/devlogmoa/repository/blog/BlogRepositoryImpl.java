package com.devlogmoa.repository.blog;

import com.devlogmoa.domain.blog.QBlog;
import com.devlogmoa.domain.member.QMember;
import com.devlogmoa.domain.subscription.QSubscription;
import com.devlogmoa.web.dto.response.blog.BlogResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BlogRepositoryImpl implements BlogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BlogResponseDto> findAllBlog(String email, Pageable pageable) {
        QBlog blogMain = new QBlog("blogMain");
        QBlog blogSub = new QBlog("blogSub");

        QueryResults<BlogResponseDto> result =
                queryFactory.select(
                        Projections.constructor(
                                BlogResponseDto.class,
                                blogMain.id,
                                blogMain.blogTitle,
                                blogMain.blogLink,
                                blogMain.blogRssLink,
                                ExpressionUtils.as(
                                        JPAExpressions.select(
                                                QSubscription.subscription.id
                                        )
                                                .from(QSubscription.subscription)
                                                .innerJoin(QMember.member)
                                                .on(QSubscription.subscription.member.id.eq(QMember.member.id))
                                                .innerJoin(blogSub)
                                                .on(QSubscription.subscription.blog.id.eq(blogSub.id))
                                                .where(QMember.member.email.eq(email))
                                                .where(blogMain.id.eq(QSubscription.subscription.blog.id)),
                                        "subscriptionId"
                                        )
                        )
                )
                .from(blogMain)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
