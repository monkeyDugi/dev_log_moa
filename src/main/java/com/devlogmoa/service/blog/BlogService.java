package com.devlogmoa.service.blog;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.domain.member.Role;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import com.devlogmoa.web.dto.response.blog.BlogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BlogService {

    private final BlogContentsRepository blogContentsRepository;
    private final BlogRepository blogRepository;

    public Page<BlogContentsResponseDto> findAllByOrderByPubDateDesc(Pageable pageable) {
        return blogContentsRepository.findAllByOrderByPubDateDesc(pageable);
    }

    public Page<BlogResponseDto> findAllBlog(Pageable pageable, SessionMember member) {
        return blogRepository.findAllBlog(member.getEmail(), member.getRole(), pageable);
    }

    @Transactional
    public void updateUseStatus(Long blogId) {
        Blog blog = blogRepository.findById(blogId).get();
        blog.useStatus();
    }

    @Transactional
    public void updateUnusedStatus(Long blogId) {
        Blog blog = blogRepository.findById(blogId).get();
        blog.unusedStatus();
    }
}
