package io.miragon.example.application.service;

import io.miragon.example.application.port.in.SendConfirmationMailUseCase;
import io.miragon.example.application.port.out.NewsletterSubscriptionRepository;
import io.miragon.example.application.port.out.ProcessedOperationRepository;
import io.miragon.example.domain.OperationId;
import io.miragon.example.domain.SubscriptionId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class SendConfirmationMailService implements SendConfirmationMailUseCase {

    private static final Logger log = LoggerFactory.getLogger(SendConfirmationMailService.class);

    private final NewsletterSubscriptionRepository repository;
    private final ProcessedOperationRepository processedOperationRepository;

    public SendConfirmationMailService(
            NewsletterSubscriptionRepository repository,
            ProcessedOperationRepository processedOperationRepository
    ) {
        this.repository = repository;
        this.processedOperationRepository = processedOperationRepository;
    }

    @Override
    public void sendConfirmationMail(SubscriptionId subscriptionId, OperationId operationId) {
        throw new UnsupportedOperationException("TODO: Implement Check-Execute-Record pattern for idempotency");
        // STEP 1: Check if already processed (return early if yes)
        // HINT: Use processedOperationRepository.existsById(operationId)
        // HINT: If already processed, log.info and return early
        // HINT: Example log: "Skipping already processed operation: {}", operationId.value()

        // STEP 2: Execute business logic
        // HINT: Find subscription using repository.find(subscriptionId)
        // HINT: Log the mail send: log.info("Sending confirmation mail to {}", subscription.email())
        // HINT: (In real implementation, you would call email service here)

        // STEP 3: Record operation as completed
        // HINT: Use processedOperationRepository.save(operationId)
        // HINT: This prevents duplicate execution if Zeebe retries the job

        // IMPORTANT: All steps happen in same @Transactional boundary
        // - Either all succeed (check + execute + record) or all rollback
        // - This ensures consistency between business logic and idempotency tracking
    }
}
