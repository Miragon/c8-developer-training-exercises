package de.emaarco.example.application.port.in;

import de.emaarco.example.domain.OperationId;
import de.emaarco.example.domain.SubscriptionId;

public interface SendConfirmationMailUseCase {
    void sendConfirmationMail(SubscriptionId subscriptionId, OperationId operationId);
}
