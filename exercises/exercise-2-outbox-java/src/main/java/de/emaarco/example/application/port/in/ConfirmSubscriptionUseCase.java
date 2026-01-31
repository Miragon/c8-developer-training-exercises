package de.emaarco.example.application.port.in;

import de.emaarco.example.domain.SubscriptionId;

public interface ConfirmSubscriptionUseCase {
    void confirm(SubscriptionId subscriptionId);
}
