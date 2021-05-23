package com.devlogmoa.repository.subscription;

import com.devlogmoa.domain.subscription.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
