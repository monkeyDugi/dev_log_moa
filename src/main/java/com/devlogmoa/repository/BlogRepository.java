package com.devlogmoa.repository;

import com.devlogmoa.domain.blog.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    Blog findByBlogLink(String blogLink);
}
