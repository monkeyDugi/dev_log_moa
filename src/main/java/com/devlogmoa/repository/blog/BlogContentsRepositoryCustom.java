package com.devlogmoa.repository.blog;

import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlogContentsRepositoryCustom {

    Page<BlogContentsResponseDto> findAllByOrderByPubDateDescIdDesc(Pageable pageable);
}
