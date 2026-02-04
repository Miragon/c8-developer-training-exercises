package io.miragon.example.application.port.in;

import io.miragon.example.domain.SubscriptionId;

public interface ConfirmSubscriptionUseCase {
    void confirm(SubscriptionId subscriptionId);
}
