package de.emaarco.example.application.port.outbound.bike;

import de.emaarco.example.domain.bike.BikeId;
import de.emaarco.example.domain.bike.BikeSubscriptionId;

public interface BikeSubscriptionProcess {
    long startSubscription(BikeSubscriptionId id, BikeId bikeId);
    void sendPaymentReceived(BikeSubscriptionId id);
    void sendRequestCanceled(BikeSubscriptionId id);
    void sendBikeReceived(BikeSubscriptionId id);
}
