package de.emaarco.example.application.service.newsletter;

import de.emaarco.example.application.port.inbound.newsletter.AbortSubscriptionUseCase;
import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionRepository;
import de.emaarco.example.domain.SubscriptionId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AbortSubscriptionService implements AbortSubscriptionUseCase {

    private static final Logger log = LoggerFactory.getLogger(AbortSubscriptionService.class);

    private final NewsletterSubscriptionRepository repository;

    public AbortSubscriptionService(NewsletterSubscriptionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void abort(SubscriptionId subscriptionId) {
        var subscription = repository.find(subscriptionId);
        subscription.abortRegistration();
        repository.save(subscription);
        log.info("Aborted subscription-registration {}", subscription.id());
    }

}
