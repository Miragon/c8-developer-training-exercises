package de.emaarco.example.application.port.outbound.newsletter;

import de.emaarco.example.domain.SubscriptionId;

public interface NewsletterSubscriptionProcess {
    long submitForm(SubscriptionId id);
    void confirmSubscription(SubscriptionId id);
}
