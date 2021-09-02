package com.devlogmoa.util;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.repository.blog.BlogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CommonRepositoryTest {

    @DisplayName("entity id가 null 이면 저장한다.")
    @Test
    void saveEntity() {
        // given
        BlogRepository blogRepository = mock(BlogRepository.class);

        String blogLink = "blogLink";
        String blogRssLink = "blogRssLink";
        String blogTitle = "blogTitle";
        Blog blog = Blog.createBlog(blogLink, blogRssLink, blogTitle);

        when(blogRepository.save(any())).thenReturn(null);

        // when
        CommonRepository.saveIfNullId(blogRepository, blog, blog.getId());

        // then
        verify(blogRepository, times(1)).save(any());
    }

    @DisplayName("entity id가 null이 아니면 아무것도 하지 않는다.")
    @Test
    void no_saveEntity() {
        // given
        BlogRepository blogRepository = mock(BlogRepository.class);

        when(blogRepository.save(any())).thenReturn(null);

        // when
        CommonRepository.saveIfNullId(null, null, 1L);

        // then
        verify(blogRepository, times(0)).save(any());
    }
}