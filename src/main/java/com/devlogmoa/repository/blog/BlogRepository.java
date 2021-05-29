package com.devlogmoa.repository.blog;

import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.web.dto.response.blog.BlogResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> , BlogRepositoryCustom{

    Blog findByBlogLink(String blogLink);

    @Override
    Page<BlogResponseDto> findAllBlog(String email, Pageable pageable);
}
