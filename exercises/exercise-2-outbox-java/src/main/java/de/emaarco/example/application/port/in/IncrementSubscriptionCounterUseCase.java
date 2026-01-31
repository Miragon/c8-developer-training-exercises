package de.emaarco.example.application.port.in;

import de.emaarco.example.domain.OperationId;
import de.emaarco.example.domain.SubscriptionId;

public interface IncrementSubscriptionCounterUseCase {
    void incrementCounter(SubscriptionId subscriptionId, OperationId operationId);
}
