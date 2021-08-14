package com.devlogmoa.repository.blog;

import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogContentsRepository extends JpaRepository<BlogContents, Long> {

    BlogContents findTopByBlogIdOrderByPubDateDesc(Long blog_id);
    Page<BlogContentsResponseDto> findAllByOrderByPubDateDesc(Pageable pageable);
}