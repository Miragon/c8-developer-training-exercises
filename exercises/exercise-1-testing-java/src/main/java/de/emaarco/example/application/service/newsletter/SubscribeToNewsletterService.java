package de.emaarco.example.application.service.newsletter;

import de.emaarco.example.application.port.inbound.newsletter.SubscribeToNewsletterUseCase;
import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionProcess;
import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionRepository;
import de.emaarco.example.domain.SubscriptionId;
import de.emaarco.example.domain.NewsletterSubscription;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SubscribeToNewsletterService implements SubscribeToNewsletterUseCase {

    private static final Logger log = LoggerFactory.getLogger(SubscribeToNewsletterService.class);

    private final NewsletterSubscriptionRepository repository;
    private final NewsletterSubscriptionProcess processPort;

    public SubscribeToNewsletterService(
        NewsletterSubscriptionRepository repository,
        NewsletterSubscriptionProcess processPort
    ) {
        this.repository = repository;
        this.processPort = processPort;
    }

    @Override
    public SubscriptionId subscribe(Command command) {
        var subscription = buildSubscription(command);
        repository.save(subscription);
        processPort.submitForm(subscription.id());
        log.info("Subscribed {} to newsletter {}", command.email(), command.newsletterId());
        return subscription.id();
    }

    private NewsletterSubscription buildSubscription(Command command) {
        return new NewsletterSubscription(
            command.name(),
            command.email(),
            command.newsletterId()
        );
    }

}
