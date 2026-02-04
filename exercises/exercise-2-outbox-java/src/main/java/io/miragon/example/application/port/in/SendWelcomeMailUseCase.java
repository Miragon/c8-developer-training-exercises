package io.miragon.example.application.port.in;

import io.miragon.example.domain.OperationId;
import io.miragon.example.domain.SubscriptionId;

public interface SendWelcomeMailUseCase {
    void sendWelcomeMail(SubscriptionId subscriptionId, OperationId operationId);
}
