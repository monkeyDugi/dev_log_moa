package com.devlogmoa.scheduler;

import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.domain.blog.ContentsStatus;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.sun.syndication.feed.synd.SyndEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RssReaderTest {

    public static final String TITLE = "title";
    public static final String LINK_URL = "linkUrl";
    public static final Date PUBLISHED_DATE = new Date();

    private RssReader rssReader;

    @BeforeEach
    void setUp() {
        // 음.. static 테스트는 어떻게 할까?
        // 음.. static을 상태로 가지는건 좋지 않다. 공유가 되어버리기 때문에 여기저기서 바뀔 수 있기 때문이다.
        RssReader.contentsStatus = ContentsStatus.DEFAULT;
    }

    @DisplayName("데이터 하나도 없을 때 신규 데이터 생성하고, contentsStatus는 NEW.")
    @Test
    void createBlogContents_basic() {
        // given
        SyndEntry syndEntry = Mockito.mock(SyndEntry.class);
        BlogRepository blogRepository = Mockito.mock(BlogRepository.class);
        BlogContentsRepository blogContentsRepository = Mockito.mock(BlogContentsRepository.class);
        rssReader = new RssReader(blogRepository, blogContentsRepository);


        when(syndEntry.getPublishedDate()).thenReturn(PUBLISHED_DATE);
        when(syndEntry.getTitle()).thenReturn(TITLE);
        when(syndEntry.getLink()).thenReturn(LINK_URL);
        when(blogContentsRepository.save(any())).thenReturn(null);

        // when
        rssReader.createBlogContents(null, syndEntry, null);

        // then
        verify(blogContentsRepository, times(1)).save(any());
        assertThat(RssReader.contentsStatus).isEqualTo(ContentsStatus.NEW);
    }

    @DisplayName("기초 데이터 생긴 후 신규 및 수정 컨텐츠가 없으면 아무것도 실행하지 않는다. contentsStatus는 DEFAULT.")
    @Test
    void no_create() {
        // given
        SyndEntry syndEntry = Mockito.mock(SyndEntry.class);
        BlogContents findLastBlogContents = Mockito.mock(BlogContents.class);
        BlogRepository blogRepository = Mockito.mock(BlogRepository.class);
        BlogContentsRepository blogContentsRepository = Mockito.mock(BlogContentsRepository.class);
        rssReader = new RssReader(blogRepository, blogContentsRepository);


        when(syndEntry.getPublishedDate()).thenReturn(PUBLISHED_DATE);
        when(findLastBlogContents.isNewPublish(any())).thenReturn(false);

        // when
        rssReader.createBlogContents(null, syndEntry, findLastBlogContents);

        // then
        verify(blogContentsRepository, times(0)).save(any());
        assertThat(RssReader.contentsStatus).isEqualTo(ContentsStatus.DEFAULT);
    }

    @DisplayName("기초 데이터 생긴 후 신규 or 수정 컨텐츠 일 때. contentsStatus는 NEW.")
    @Test
    void createContents_new() {

    }
}