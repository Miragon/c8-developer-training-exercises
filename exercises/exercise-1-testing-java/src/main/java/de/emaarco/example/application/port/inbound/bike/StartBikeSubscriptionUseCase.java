package de.emaarco.example.application.port.inbound.bike;

import de.emaarco.example.domain.Email;
import de.emaarco.example.domain.Name;
import de.emaarco.example.domain.bike.BikeId;
import de.emaarco.example.domain.bike.BikeSubscriptionId;

public interface StartBikeSubscriptionUseCase {
    record Command(
        BikeId bikeId,
        Email email,
        Name name
    ) {}

    BikeSubscriptionId start(Command command);
}
