package de.emaarco.example.application.port.outbound.newsletter;

import de.emaarco.example.domain.NewsletterSubscription;
import de.emaarco.example.domain.SubscriptionId;

public interface NewsletterSubscriptionRepository {
    NewsletterSubscription find(SubscriptionId subscriptionId);
    NewsletterSubscription search(SubscriptionId subscriptionId);
    void save(NewsletterSubscription subscription);
    void delete(SubscriptionId subscriptionId);
}
