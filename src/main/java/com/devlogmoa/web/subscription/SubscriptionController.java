package com.devlogmoa.web.subscription;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.subscription.Subscription;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.repository.member.MemberRepository;
import com.devlogmoa.repository.subscription.SubscriptionRepository;
import com.devlogmoa.web.dto.response.subscription.SubscriptionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class SubscriptionController {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BlogRepository blogRepository;

    @ResponseBody
    @PostMapping("/api/blog/subscription/{blogId}")
    public void saveSubscription(@PathVariable("blogId") Long blogId, @LoginMember SessionMember member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();
        Blog findBlog = blogRepository.findById(blogId).get();

        Subscription subscription = Subscription.Subscribe(findBlog, findMember);
        subscriptionRepository.save(subscription);
    }

    @ResponseBody
    @DeleteMapping("/api/blog/subscription/{subscriptionId}")
    public void deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    @GetMapping("/blogs/Contents/subscriptions")
    public String getSubscription(Model model, @PageableDefault(size = 10) Pageable pageable, @LoginMember SessionMember member) {
        Page<SubscriptionResponseDto> subscriptions = subscriptionRepository.findByMemberEmail("lbd4946@gmail.com", pageable);

        model.addAttribute("subscriptions", subscriptions);

        if (member != null) {
            model.addAttribute("memberName", member);
        }

        return "subscriptions";
    }
}
