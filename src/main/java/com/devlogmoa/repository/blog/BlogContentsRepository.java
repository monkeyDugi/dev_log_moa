package com.devlogmoa.repository.blog;

import com.devlogmoa.domain.blog.BlogContents;
import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogContentsRepository extends JpaRepository<BlogContents, Long>, BlogContentsRepositoryCustom {

    BlogContents findTopByBlogIdOrderByPubDateDesc(Long id);

    @Override
    Page<BlogContentsResponseDto> findAllByOrderByPubDateDescIdDesc(Pageable pageable);

    Optional<BlogContents> findByPubLink(String link);
}
