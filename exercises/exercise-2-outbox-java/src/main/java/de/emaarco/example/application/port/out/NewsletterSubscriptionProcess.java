package de.emaarco.example.application.port.out;

import de.emaarco.example.domain.SubscriptionId;

public interface NewsletterSubscriptionProcess {
    void submitForm(SubscriptionId id);
    void confirmSubscription(SubscriptionId id);
}
