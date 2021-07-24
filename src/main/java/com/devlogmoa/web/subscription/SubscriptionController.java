package com.devlogmoa.web.subscription;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.service.subscription.SubscriptionService;
import com.devlogmoa.web.dto.response.subscription.SubscriptionResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @ApiOperation(value = "블로그 구독하기")
    @ResponseBody
    @PostMapping("/api/blogs/subscription/{blogId}")
    public void saveSubscription(@PathVariable("blogId") Long blogId, @LoginMember SessionMember member) {
        subscriptionService.saveSubscription(blogId, member);
    }

    @ApiOperation(value = "블로그 구독 취소")
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
