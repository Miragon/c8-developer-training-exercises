package io.miragon.example.application.service;

import io.miragon.example.application.port.in.AbortSubscriptionUseCase;
import io.miragon.example.application.port.out.NewsletterSubscriptionRepository;
import io.miragon.example.application.port.out.ProcessedOperationRepository;
import io.miragon.example.domain.NewsletterSubscription;
import io.miragon.example.domain.OperationId;
import io.miragon.example.domain.SubscriptionId;
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
