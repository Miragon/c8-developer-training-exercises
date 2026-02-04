package io.miragon.example.adapter.out.db.subscription;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NewsletterSubscriptionJpaRepository extends JpaRepository<NewsletterSubscriptionEntity, UUID> {
    NewsletterSubscriptionEntity findBySubscriptionId(UUID id);
}
