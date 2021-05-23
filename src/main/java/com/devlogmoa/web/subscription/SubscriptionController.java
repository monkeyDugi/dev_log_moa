package com.devlogmoa.web.subscription;

import com.devlogmoa.config.auth.LoginMember;
import com.devlogmoa.config.auth.dto.SessionMember;
import com.devlogmoa.domain.blog.Blog;
import com.devlogmoa.domain.member.Member;
import com.devlogmoa.domain.subscription.Subscription;
import com.devlogmoa.repository.blog.BlogRepository;
import com.devlogmoa.repository.member.MemberRepository;
import com.devlogmoa.repository.subscription.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscriptionController {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BlogRepository blogRepository;

    @PostMapping("/api/blog/subscription/{blogId}")
    public void saveSubscription(@PathVariable("blogId") Long blogId, @LoginMember SessionMember member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();
        Blog findBlog = blogRepository.findById(blogId).get();

        Subscription subscription = Subscription.Subscribe(findBlog, findMember);
        subscriptionRepository.save(subscription);
    }

    @DeleteMapping("/api/blog/subscription/{subscriptionId}")
    public void deleteSubscription(@PathVariable("subscriptionId") Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }
}
