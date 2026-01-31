package de.emaarco.example.adapter.outbound.db.newsletter;

import de.emaarco.example.domain.Email;
import de.emaarco.example.domain.Name;
import de.emaarco.example.domain.SubscriptionId;
import de.emaarco.example.domain.NewsletterId;
import de.emaarco.example.domain.NewsletterSubscription;
import org.springframework.stereotype.Component;

@Component
public class NewsletterSubscriptionEntityMapper {

    public NewsletterSubscription toDomain(NewsletterSubscriptionEntity entity) {
        return new NewsletterSubscription(
            new SubscriptionId(entity.getSubscriptionId()),
            new Name(entity.getName()),
            new Email(entity.getEmail()),
            new NewsletterId(entity.getNewsletterId()),
            entity.getRegistrationDate(),
            entity.getStatus()
        );
    }

    public NewsletterSubscriptionEntity toEntity(NewsletterSubscription domain) {
        return new NewsletterSubscriptionEntity(
            domain.id().value(),
            domain.name().value(),
            domain.email().value(),
            domain.newsletter().value(),
            domain.registrationDate(),
            domain.status()
        );
    }

}
