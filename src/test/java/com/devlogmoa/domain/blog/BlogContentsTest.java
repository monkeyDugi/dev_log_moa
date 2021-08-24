package com.devlogmoa.domain.blog;

import com.devlogmoa.web.dto.response.rss.RssResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BlogContentsTest {

    @DisplayName("1개의 블로그에 최신 글이 올라왔으면 true 반환")
    @Test
    void isNewPublish_true() {
        // given
        LocalDate pubDate = LocalDate.now();
        LocalDate newPubDate = LocalDate.now().plusDays(1);

        RssResponseDto rssResponseDto = RssResponseDto.newRss(pubDate);
        BlogContents blogContents = BlogContents.createPublish(rssResponseDto);

        // when
        boolean isResult = blogContents.isNewPublish(newPubDate);

        // then
        assertThat(isResult).isTrue();
    }

    @DisplayName("1개의 블로그에 최신 글이 없으면 false 반환")
    @Test
    void isNewPublish_false() {
        // given
        LocalDate pubDate = LocalDate.now();
        LocalDate newPubDate = LocalDate.now();

        RssResponseDto rssResponseDto = RssResponseDto.newRss(pubDate);
        BlogContents blogContents = BlogContents.createPublish(rssResponseDto);

        // when
        boolean isResult = blogContents.isNewPublish(newPubDate);

        // then
        assertThat(isResult).isFalse();
    }
}