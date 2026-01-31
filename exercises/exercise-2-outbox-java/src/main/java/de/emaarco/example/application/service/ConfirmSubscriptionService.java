package de.emaarco.example.application.service;

import de.emaarco.example.application.port.in.ConfirmSubscriptionUseCase;
import de.emaarco.example.application.port.out.NewsletterSubscriptionProcess;
import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.domain.SubscriptionId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ConfirmSubscriptionService implements ConfirmSubscriptionUseCase {

    private static final Logger log = LoggerFactory.getLogger(ConfirmSubscriptionService.class);

    private final NewsletterSubscriptionRepository repository;
    private final NewsletterSubscriptionProcess processPort;

    public ConfirmSubscriptionService(
            NewsletterSubscriptionRepository repository,
            NewsletterSubscriptionProcess processPort
    ) {
        this.repository = repository;
        this.processPort = processPort;
    }

    @Override
    public void confirm(SubscriptionId subscriptionId) {
        var subscription = repository.find(subscriptionId);
        var confirmed = subscription.confirmRegistration();
        repository.save(confirmed);
        processPort.confirmSubscription(confirmed.id());
        log.info("Confirmed subscription {}", confirmed.id());
    }
}
