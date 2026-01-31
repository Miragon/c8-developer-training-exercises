package de.emaarco.example.adapter.inbound.zeebe.bike;

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi;
import de.emaarco.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
public class CheckBikeAvailabilityWorker {

    private static final Logger log = LoggerFactory.getLogger(CheckBikeAvailabilityWorker.class);

    private final CheckBikeAvailabilityUseCase useCase;

    public CheckBikeAvailabilityWorker(CheckBikeAvailabilityUseCase useCase) {
        this.useCase = useCase;
    }

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.BIKE_CHECK_AVAILABILITY)
    public Map<String, Object> handle(@Variable UUID subscriptionId) {
        log.info("Checking bike availability for subscription: {}", subscriptionId);
        boolean available = useCase.checkAvailability(new BikeSubscriptionId(subscriptionId));
        return Map.of("bikeAvailable", available);
    }

}
