package io.miragon.example.application.port.in;

import io.miragon.example.domain.OperationId;
import io.miragon.example.domain.SubscriptionId;

public interface AbortSubscriptionUseCase {
    void abort(SubscriptionId subscriptionId, OperationId operationId);
}
