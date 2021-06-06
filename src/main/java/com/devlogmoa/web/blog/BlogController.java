package com.devlogmoa.web.blog;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.blog.UsageStatus;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.repository.blog.BlogContentsRepository;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.repository.member.MemberRepository;
import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import com.devlogmoa.web.dto.response.blog.BlogResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class BlogController {

    private final BlogContentsRepository blogContentsRepository;
    private final BlogRepository blogRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String getBLogContents(Model model, @PageableDefault(size = 10) Pageable pageable, @LoginMember SessionMember member) {
        Page<BlogContentsResponseDto> blogContents = blogContentsRepository.findAllByOrderByPubDateDesc(pageable);

        model.addAttribute("blogContents", blogContents);

        if (member != null) {
            model.addAttribute("memberName", member);
        }

        return "blogContents";
    }

    @GetMapping("/blogs")
    public String getBlogs(Model model, @PageableDefault(size = 10) Pageable pageable, @LoginMember SessionMember member) {
        Page<BlogResponseDto> blogs = blogRepository.findAllBlog(member.getEmail(), pageable);

        model.addAttribute("blogs", blogs);

        if (member != null) {
            model.addAttribute("memberName", member);
        }

        return "blogs";
    }

    @ResponseBody
    @PutMapping("/api/admin/blogs/{blogId}/usageStatus/{usageStatus}")
    public ResponseEntity<String> updateUsageStatus(@PathVariable("blogId") Long blogId,
                                            @PathVariable("usageStatus") String usageStatus,
                                            @LoginMember SessionMember member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();

        if (!findMember.isAdmin()) {
            return new ResponseEntity<>("관리자 권한이 필요 합니다.", HttpStatus.BAD_REQUEST);
        }

        Blog blog = blogRepository.findById(blogId).get();
        blog.changeStatus(UsageStatus.valueOf(usageStatus));

        blogRepository.save(blog);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
