package com.devlogmoa.repository;

import com.devlogmoa.domain.BlogDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogDetailRepository extends JpaRepository<BlogDetail, Long> {

    BlogDetail findTopByBlogIdOrderByPubDateDesc(Long blog_id);
    List<BlogDetail> findTop20ByOrderByPubDateDesc();
}
