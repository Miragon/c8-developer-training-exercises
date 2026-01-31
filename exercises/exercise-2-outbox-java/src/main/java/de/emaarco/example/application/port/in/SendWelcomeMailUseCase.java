package de.emaarco.example.application.port.in;

import de.emaarco.example.domain.OperationId;
import de.emaarco.example.domain.SubscriptionId;

public interface SendWelcomeMailUseCase {
    void sendWelcomeMail(SubscriptionId subscriptionId, OperationId operationId);
}
