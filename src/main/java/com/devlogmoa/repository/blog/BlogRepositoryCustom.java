package com.devlogmoa.repository.blog;

import com.devlogmoa.web.dto.response.blog.BlogDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogRepositoryCustom {

    Page<BlogDto> findAllBlog(String email, Pageable pageable);
}
