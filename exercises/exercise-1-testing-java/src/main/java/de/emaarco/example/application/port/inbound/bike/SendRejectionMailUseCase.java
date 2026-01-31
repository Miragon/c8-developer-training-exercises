package de.emaarco.example.application.port.inbound.bike;

import de.emaarco.example.domain.bike.BikeSubscriptionId;

public interface SendRejectionMailUseCase {
    void sendRejectionMail(BikeSubscriptionId subscriptionId);
}
