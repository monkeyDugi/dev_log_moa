package com.devlogmoa.repository.blog;

import com.devlogmoa.domain.blog.BlogContents;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogContentsRepository extends JpaRepository<BlogContents, Long> {

    BlogContents findTopByBlogIdOrderByPubDateDesc(Long blog_id);
    Page<BlogContents> findAllByOrderByPubDateDesc(Pageable pageable);
}
