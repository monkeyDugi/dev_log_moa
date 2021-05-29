package com.devlogmoa.repository.subscription;

import com.devlogmoa.web.dto.response.subscription.SubscriptionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SubscriptionRepositoryCustom {

    Page<SubscriptionResponseDto> findByMemberEmail(String email, Pageable pageable);
}
