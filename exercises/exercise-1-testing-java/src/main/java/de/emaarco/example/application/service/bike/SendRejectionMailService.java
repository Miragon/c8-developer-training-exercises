package de.emaarco.example.application.service.bike;

import de.emaarco.example.application.port.inbound.bike.SendRejectionMailUseCase;
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionRepository;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SendRejectionMailService implements SendRejectionMailUseCase {

    private static final Logger log = LoggerFactory.getLogger(SendRejectionMailService.class);

    private final BikeSubscriptionRepository repository;

    public SendRejectionMailService(BikeSubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void sendRejectionMail(BikeSubscriptionId subscriptionId) {
        var subscription = repository.find(subscriptionId);
        log.info("Sending rejection mail to {} - bike {} not available",
            subscription.customerEmail().value(), subscription.bikeId().value());
    }

}
