package com.devlogmoa.scheduler;

import com.devlogmoa.domain.blog.ContentsStatus;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.sun.syndication.feed.synd.SyndEntry;
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

    /**
     * 1. findLastBlogContents가 null이면 save가 1번 실행 되는가?
     * -> ContentsStatus가 New인가?
     *
     * 2. findLastBlogContents가 null이 아닐 때
     * 1) 신규 글이 아닐 때 아무 일도 일어나지 않는다.
     * ContentsStatus가가 DEFAULT이다.
     *
     * 2) 신규 글일 때
     * saveIfNullId가 1번 실행된다.
     */
    @DisplayName("데이터 하나도 없을 때 신규 데이터 생성")
    @Test
    void createBlogContents_null() {
        // given
        BlogRepository blogRepository = Mockito.mock(BlogRepository.class);
        BlogContentsRepository blogContentsRepository = Mockito.mock(BlogContentsRepository.class);
        rssReader = new RssReader(blogRepository, blogContentsRepository);

        SyndEntry syndEntry = Mockito.mock(SyndEntry.class);

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
}