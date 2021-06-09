package com.devlogmoa.service.subscription;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Transactional
@Service
public class SubscriptionService {

    private final MemberRepository memberRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final BlogRepository blogRepository;

    public void saveSubscription(Long blogId, SessionMember member) {
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();
        Blog findBlog = blogRepository.findById(blogId).get();

        Subscription subscription = Subscription.Subscribe(findBlog, findMember);
        subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }

    public Page<SubscriptionResponseDto> findSubscriptions(SessionMember member, Pageable pageable) {
        return subscriptionRepository.findByMemberEmail(member.getEmail(), pageable);
    }
}
