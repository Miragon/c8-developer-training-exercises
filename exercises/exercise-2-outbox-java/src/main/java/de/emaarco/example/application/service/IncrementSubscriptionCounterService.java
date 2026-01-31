package de.emaarco.example.application.service;

import de.emaarco.example.application.port.in.IncrementSubscriptionCounterUseCase;
import de.emaarco.example.application.port.out.ProcessedOperationRepository;
import de.emaarco.example.application.port.out.SubscriptionCounterRepository;
import de.emaarco.example.domain.OperationId;
import de.emaarco.example.domain.SubscriptionId;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class IncrementSubscriptionCounterService implements IncrementSubscriptionCounterUseCase {

    private static final Logger log = LoggerFactory.getLogger(IncrementSubscriptionCounterService.class);

    private final SubscriptionCounterRepository counterRepository;
    private final ProcessedOperationRepository processedOperationRepository;

    public IncrementSubscriptionCounterService(
            SubscriptionCounterRepository counterRepository,
            ProcessedOperationRepository processedOperationRepository
    ) {
        this.counterRepository = counterRepository;
        this.processedOperationRepository = processedOperationRepository;
    }

    @Override
    public void incrementCounter(SubscriptionId subscriptionId, OperationId operationId) {
        throw new UnsupportedOperationException("TODO: Implement Check-Execute-Record pattern for idempotency");
        // STEP 1: Check if already processed (return early if yes)
        // HINT: Use processedOperationRepository.existsById(operationId)
        // HINT: If already processed, log and return

        // STEP 2: Execute business logic (increment counter)
        // HINT: Find counter using counterRepository.find()
        // HINT: Increment counter using counter.increment()
        // HINT: Save updated counter using counterRepository.save()
        // HINT: Log: "Incremented subscription counter for {}: {}", subscriptionId.value(), updatedCounter.count()

        // STEP 3: Record operation as completed
        // HINT: Use processedOperationRepository.save(operationId)

        // Why is idempotency critical here?
        // - Without it, counter would increment multiple times if Zeebe retries
        // - This would corrupt business metrics and analytics
    }
}
