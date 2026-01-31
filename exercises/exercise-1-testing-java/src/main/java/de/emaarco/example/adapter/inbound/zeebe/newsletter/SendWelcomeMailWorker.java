package de.emaarco.example.adapter.inbound.zeebe.newsletter;

import de.emaarco.example.adapter.process.generated.NewsletterSubscriptionProcessApi.TaskTypes;
import de.emaarco.example.application.port.inbound.newsletter.SendWelcomeMailUseCase;
import de.emaarco.example.domain.SubscriptionId;
import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SendWelcomeMailWorker {

    private static final Logger log = LoggerFactory.getLogger(SendWelcomeMailWorker.class);

    private final SendWelcomeMailUseCase useCase;

    public SendWelcomeMailWorker(SendWelcomeMailUseCase useCase) {
        this.useCase = useCase;
    }

    @JobWorker(type = TaskTypes.NEWSLETTER_SEND_WELCOME_MAIL)
    public void handle(@Variable UUID subscriptionId) {
        log.debug("Received job to send welcome mail for subscriptionId: {}", subscriptionId);
        useCase.sendWelcomeMail(new SubscriptionId(subscriptionId));
    }

}
