package de.emaarco.example.application.port.outbound.bike;

import de.emaarco.example.domain.bike.BikeSubscription;
import de.emaarco.example.domain.bike.BikeSubscriptionId;

public interface BikeSubscriptionRepository {
    BikeSubscription save(BikeSubscription subscription);
    BikeSubscription find(BikeSubscriptionId id);
    BikeSubscription search(BikeSubscriptionId id);
}
