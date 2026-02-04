package io.miragon.example.application.port.out;

import io.miragon.example.domain.SubscriptionId;

public interface NewsletterSubscriptionProcess {
    void submitForm(SubscriptionId id);
    void confirmSubscription(SubscriptionId id);
}
