package de.emaarco.example.application.service;

import de.emaarco.example.application.port.in.AbortSubscriptionUseCase;
import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.application.port.out.ProcessedOperationRepository;
import de.emaarco.example.domain.NewsletterSubscription;
import de.emaarco.example.domain.OperationId;
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
    private final ProcessedOperationRepository processedOperationRepository;

    public AbortSubscriptionService(
            NewsletterSubscriptionRepository repository,
            ProcessedOperationRepository processedOperationRepository
    ) {
        this.repository = repository;
        this.processedOperationRepository = processedOperationRepository;
    }

    @Override
    public void abort(SubscriptionId subscriptionId, OperationId operationId) {
        if (processedOperationRepository.existsById(operationId)) {
            log.info("Skipping already processed operation: {}", operationId.value());
            return;
        }

        var subscription = repository.find(subscriptionId);
        var aborted = subscription.abortRegistration();
        repository.save(aborted);
        log.info("Aborted subscription-registration {}", aborted.id());

        processedOperationRepository.save(operationId);
    }
}
