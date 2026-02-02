package io.miragon.example.adapter.out.db.subscription;

import io.miragon.example.domain.Email;
import io.miragon.example.domain.Name;
import io.miragon.example.domain.NewsletterId;
import io.miragon.example.domain.NewsletterSubscription;
import io.miragon.example.domain.SubscriptionId;

public class NewsletterSubscriptionEntityMapper {

    public static NewsletterSubscription toDomain(NewsletterSubscriptionEntity entity) {
        return new NewsletterSubscription(
                new SubscriptionId(entity.getSubscriptionId()),
                new Name(entity.getName()),
                new Email(entity.getEmail()),
                new NewsletterId(entity.getNewsletterId()),
                entity.getRegistrationDate(),
                entity.getStatus()
        );
    }

    public static NewsletterSubscriptionEntity toEntity(NewsletterSubscription domain) {
        return new NewsletterSubscriptionEntity(
                domain.id().value(),
                domain.name().value(),
                domain.email().value(),
                domain.newsletter().value(),
                domain.registrationDate(),
                domain.status()
        );
    }

    private NewsletterSubscriptionEntityMapper() {
    }
}
