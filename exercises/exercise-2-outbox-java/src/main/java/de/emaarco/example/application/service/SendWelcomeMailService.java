package de.emaarco.example.application.service;

import de.emaarco.example.application.port.in.SendWelcomeMailUseCase;
import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository;
import de.emaarco.example.application.port.out.ProcessedOperationRepository;
import de.emaarco.example.domain.OperationId;
import de.emaarco.example.domain.SubscriptionId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SendWelcomeMailService implements SendWelcomeMailUseCase {

    private static final Logger log = LoggerFactory.getLogger(SendWelcomeMailService.class);

    private final NewsletterSubscriptionRepository repository;
    private final ProcessedOperationRepository processedOperationRepository;

    public SendWelcomeMailService(
            NewsletterSubscriptionRepository repository,
            ProcessedOperationRepository processedOperationRepository
    ) {
        this.repository = repository;
        this.processedOperationRepository = processedOperationRepository;
    }

    @Override
    public void sendWelcomeMail(SubscriptionId subscriptionId, OperationId operationId) {
        throw new UnsupportedOperationException("TODO: Implement Check-Execute-Record pattern for idempotency");
        // STEP 1: Check if already processed (return early if yes)

        // STEP 2: Execute business logic

        // STEP 3: Record operation as completed
    }
}
