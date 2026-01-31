package de.emaarco.example.adapter.inbound.zeebe.newsletter;

import de.emaarco.example.adapter.process.generated.NewsletterSubscriptionProcessApi.TaskTypes;
import de.emaarco.example.application.port.inbound.newsletter.AbortSubscriptionUseCase;
import de.emaarco.example.domain.SubscriptionId;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AbortRegistrationWorker {

    private static final Logger log = LoggerFactory.getLogger(AbortRegistrationWorker.class);

    private final AbortSubscriptionUseCase useCase;

    public AbortRegistrationWorker(AbortSubscriptionUseCase useCase) {
        this.useCase = useCase;
    }

    @JobWorker(type = TaskTypes.NEWSLETTER_ABORT_REGISTRATION)
    public void handle(@Variable UUID subscriptionId) {
        log.debug("Received job to abort registration for subscriptionId: {}", subscriptionId);
        useCase.abort(new SubscriptionId(subscriptionId));
    }

}
