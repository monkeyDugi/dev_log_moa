package com.devlogmoa.web;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.repository.blog.BlogDetailRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.web.dto.response.blog.BlogDetailDto;
import com.devlogmoa.web.dto.response.blog.BlogDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogDetailRepository blogDetailRepository;
    private final BlogRepository blogRepository;

    @GetMapping("/")
    public String getPosts(Model model, @PageableDefault(size = 10) Pageable pageable, @LoginMember SessionMember member) {

        Page<BlogDetailDto> blogDetails = blogDetailRepository.findAllByOrderByPubDateDesc(pageable)
                .map(BlogDetailDto::new);

        model.addAttribute("blogDetails", blogDetails);

        if (member != null) {
            model.addAttribute("memberName", member);
        }

        return "posts";
    }

    @GetMapping("/blogs")
    public String getBlogs(Model model, @PageableDefault(size = 10) Pageable pageable, @LoginMember SessionMember member) {
        Page<BlogDto> blogs = blogRepository.findAllBlog(member.getEmail(), pageable);

        model.addAttribute("blogs", blogs);

        if (member != null) {
            model.addAttribute("memberName", member);
        }

        return "blogs";
    }
}
