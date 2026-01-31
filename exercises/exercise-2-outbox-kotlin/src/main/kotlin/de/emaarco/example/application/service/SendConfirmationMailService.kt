package de.emaarco.example.application.service

import de.emaarco.example.application.port.`in`.SendConfirmationMailUseCase
import de.emaarco.example.application.port.out.NewsletterSubscriptionRepository
import de.emaarco.example.application.port.out.ProcessedOperationRepository
import de.emaarco.example.domain.OperationId
import de.emaarco.example.domain.SubscriptionId
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class SendConfirmationMailService(
    private val repository: NewsletterSubscriptionRepository,
    private val processedOperationRepository: ProcessedOperationRepository
) : SendConfirmationMailUseCase {

    private val log = KotlinLogging.logger {}

    override fun sendConfirmationMail(subscriptionId: SubscriptionId, operationId: OperationId) {
        TODO("Implement Check-Execute-Record pattern for idempotency")
        // STEP 1: Check if already processed (return early if yes)
        // HINT: Use processedOperationRepository.existsById(operationId)
        // HINT: If already processed, log.info and return early
        // HINT: Example log: "Skipping already processed operation: ${operationId.value}"

        // STEP 2: Execute business logic
        // HINT: Find subscription using repository.find(subscriptionId)
        // HINT: Log the mail send: log.info { "Sending confirmation mail to ${subscription.email}" }
        // HINT: (In real implementation, you would call email service here)

        // STEP 3: Record operation as completed
        // HINT: Use processedOperationRepository.save(operationId)
        // HINT: This prevents duplicate execution if Zeebe retries the job

        // IMPORTANT: All steps happen in same @Transactional boundary
        // - Either all succeed (check + execute + record) or all rollback
        // - This ensures consistency between business logic and idempotency tracking
    }
}
