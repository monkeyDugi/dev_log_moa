package com.devlogmoa.repository.subscription;

import com.devlogmoa.domain.subscription.Subscription;
import com.devlogmoa.web.dto.response.subscription.SubscriptionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long>, SubscriptionRepositoryCustom {

    @Override
    Page<SubscriptionResponseDto> findByMemberEmail(String email, Pageable pageable);
}
