package com.devlogmoa.repository;

import com.devlogmoa.domain.blog.BlogDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogDetailRepository extends JpaRepository<BlogDetail, Long> {

    BlogDetail findTopByBlogIdOrderByPubDateDesc(Long blog_id);
    Page<BlogDetail> findAllByOrderByPubDateDesc(Pageable pageable);
}
