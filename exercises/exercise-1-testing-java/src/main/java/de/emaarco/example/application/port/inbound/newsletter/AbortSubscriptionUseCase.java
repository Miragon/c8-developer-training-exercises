package de.emaarco.example.application.port.inbound.newsletter;

import de.emaarco.example.domain.SubscriptionId;

public interface AbortSubscriptionUseCase {
    void abort(SubscriptionId subscriptionId);
}
