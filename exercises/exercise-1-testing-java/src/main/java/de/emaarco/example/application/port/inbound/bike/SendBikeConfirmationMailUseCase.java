package de.emaarco.example.application.port.inbound.bike;

import de.emaarco.example.domain.bike.BikeSubscriptionId;

public interface SendBikeConfirmationMailUseCase {
    void sendConfirmationMail(BikeSubscriptionId subscriptionId);
}
