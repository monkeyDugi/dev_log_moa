package com.devlogmoa.repository.blog;

import com.devlogmoa.domain.blog.QBlog;
import com.devlogmoa.domain.blog.QBlogContents;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class BlogContentsRepositoryImpl implements BlogContentsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<BlogContentsResponseDto> findAllByOrderByPubDateDesc(Pageable pageable) {

        QueryResults<BlogContentsResponseDto> result =
                queryFactory.select(
                        Projections.constructor(
                                BlogContentsResponseDto.class,
                                QBlogContents.blogContents
                        )
                )
                .from(QBlogContents.blogContents)
                .innerJoin(QBlog.blog)
                .on(QBlog.blog.eq(QBlogContents.blogContents.blog))
                .where(QBlog.blog.usageStatus.eq(UsageStatus.USE))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(QBlogContents.blogContents.pubDate.desc())
                .fetchResults();

        return new PageImpl<>(result.getResults(), pageable, result.getTotal());
    }
}
