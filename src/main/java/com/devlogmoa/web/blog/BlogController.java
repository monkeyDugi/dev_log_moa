package com.devlogmoa.web.blog;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.member.Role;
import com.devlogmoa.repository.member.MemberRepository;
import com.devlogmoa.service.blog.BlogService;
import com.devlogmoa.web.dto.response.blog.BlogContentsResponseDto;
import com.devlogmoa.web.dto.response.blog.BlogResponseDto;
import io.swagger.annotations.ApiOperation;
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

    private final BlogService blogService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String findBLogContents(Model model, @LoginMember SessionMember member, @PageableDefault(size = 10) Pageable pageable) {
        Page<BlogContentsResponseDto> blogContents = blogService.findAllByOrderByPubDateDescIdDesc(pageable);

        model.addAttribute("blogContents", blogContents);

        if (member != null) {
            model.addAttribute("member", member);
        }

        return "blogContents";
    }

    @GetMapping("/blogs")
    public String findBlogs(Model model, @LoginMember SessionMember member, @PageableDefault(size = 10) Pageable pageable) {
        Page<BlogResponseDto> blogs = blogService.findAllBlog(pageable, member);
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();

        model.addAttribute("blogs", blogs);

        if (member != null) {
            // ????????? ????????? ?????? ?????? ????????? ????????? ???????? ????????? ????????? ????????? ??????????????? ?????? ????????????? ?????? ??????
            member.updateMailReceiptStatus(findMember);
            model.addAttribute("member", member);
        }

        return "blogs";
    }

    @PutMapping("/api/admin/blogs/{blogId}/useStatus")
    public ResponseEntity<String> updateUseStatus(@PathVariable("blogId") Long blogId, @LoginMember SessionMember member) {
        if (member.getRole() != Role.ADMIN) {
            return new ResponseEntity<>("????????? ????????? ?????? ?????????.", HttpStatus.BAD_REQUEST);
        }

        blogService.updateUseStatus(blogId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "???????????? ????????? ????????????")
    @PutMapping("/api/admin/blogs/{blogId}/unusedStatus")
    public ResponseEntity<String> updateUnusedStatus(@PathVariable("blogId") Long blogId, @LoginMember SessionMember member) {
        if (member.getRole() != Role.ADMIN) {
            return new ResponseEntity<>("????????? ????????? ?????? ?????????.", HttpStatus.BAD_REQUEST);
        }

        blogService.updateUnusedStatus(blogId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "?????????????????? ????????????")
    @ResponseBody
    @PutMapping("/api/blog/mailReceiptStatus/{mailReceiptStatus}")
    public void updateMailReceiptStatus(@PathVariable("mailReceiptStatus") String mailReceiptStatus, @LoginMember SessionMember member) {
        blogService.updateMailReceiptStatus(mailReceiptStatus, member);
    }
}