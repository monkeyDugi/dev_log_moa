package com.devlogmoa.repository.blog;

import com.devlogmoa.web.dto.response.blog.BlogResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogRepositoryCustom {

    Page<BlogResponseDto> findAllBlog(String email, Pageable pageable);
}
