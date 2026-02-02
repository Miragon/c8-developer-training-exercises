package de.emaarco.example.adapter.inbound.zeebe.bike;

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi;
import de.emaarco.example.application.port.inbound.bike.NotifyBikeCancelationUseCase;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class NotifyBikeCancellationWorker {
	
	private static final Logger log = LoggerFactory.getLogger(NotifyBikeCancellationWorker.class);
	
	private final NotifyBikeCancelationUseCase useCase;
	
	public NotifyBikeCancellationWorker(NotifyBikeCancelationUseCase useCase) {
		this.useCase = useCase;
	}
	
	@JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.BIKE_NOTIFY_CANCELATION)
	public void handle(@Variable UUID subscriptionId) {
		log.info("Notifying about cancelation for subscription: {}", subscriptionId);
		useCase.notifyCancelation(new BikeSubscriptionId(subscriptionId));
	}
	
}
