package com.devlogmoa.web.subscription;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.subscription.Subscription;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.repository.member.MemberRepository;
import com.devlogmoa.repository.subscription.SubscriptionRepository;
import com.devlogmoa.service.subscription.SubscriptionService;
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

    private final SubscriptionService subscriptionService;

    @ResponseBody
    @PostMapping("/api/blogs/subscription/{blogId}")
    public void saveSubscription(@PathVariable("blogId") Long blogId, @LoginMember SessionMember member) {
        subscriptionService.saveSubscription(blogId, member);
    }

    @ResponseBody
    @DeleteMapping("/api/blogs/subscription/{subscriptionId}")
    public void deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionService.deleteSubscription(subscriptionId);
    }

    @GetMapping("/blogs/Contents/subscriptions")
    public String findSubscriptions(Model model, @LoginMember SessionMember member, @PageableDefault(size = 10) Pageable pageable) {
        Page<SubscriptionResponseDto> subscriptions = subscriptionService.findSubscriptions(member, pageable);

        model.addAttribute("subscriptions", subscriptions);

        if (member != null) {
            model.addAttribute("member", member);
        }

        return "subscriptions";
    }
}
