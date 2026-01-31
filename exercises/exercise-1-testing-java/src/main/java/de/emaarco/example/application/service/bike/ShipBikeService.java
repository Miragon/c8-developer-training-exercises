package de.emaarco.example.application.service.bike;

import de.emaarco.example.application.port.inbound.bike.ShipBikeUseCase;
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionRepository;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShipBikeService implements ShipBikeUseCase {

    private static final Logger log = LoggerFactory.getLogger(ShipBikeService.class);

    private final BikeSubscriptionRepository repository;

    public ShipBikeService(BikeSubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void shipBike(BikeSubscriptionId subscriptionId) {
        var subscription = repository.find(subscriptionId);
        log.info("Shipping bike {} to customer {}",
            subscription.bikeId().value(), subscription.customerName().value());
        repository.save(subscription.markShipped());
    }

}
