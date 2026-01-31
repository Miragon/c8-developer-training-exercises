package de.emaarco.example.adapter.inbound.zeebe.bike;

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi;
import de.emaarco.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SendBikeConfirmationMailWorker {

    private static final Logger log = LoggerFactory.getLogger(SendBikeConfirmationMailWorker.class);

    private final SendBikeConfirmationMailUseCase useCase;

    public SendBikeConfirmationMailWorker(SendBikeConfirmationMailUseCase useCase) {
        this.useCase = useCase;
    }

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.BIKE_SEND_CONFIRMATION_MAIL)
    public void handle(@Variable UUID subscriptionId) {
        log.info("Sending bike confirmation mail for subscription: {}", subscriptionId);
        useCase.sendConfirmationMail(new BikeSubscriptionId(subscriptionId));
    }

}
