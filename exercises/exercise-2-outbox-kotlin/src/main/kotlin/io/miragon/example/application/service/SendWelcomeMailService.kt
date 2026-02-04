package io.miragon.example.application.service

import io.miragon.example.application.port.`in`.SendWelcomeMailUseCase
import io.miragon.example.application.port.out.NewsletterSubscriptionRepository
import io.miragon.example.application.port.out.ProcessedOperationRepository
import io.miragon.example.domain.OperationId
import io.miragon.example.domain.SubscriptionId
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
@Transactional
class SendWelcomeMailService(
    private val repository: NewsletterSubscriptionRepository,
    private val processedOperationRepository: ProcessedOperationRepository
) : SendWelcomeMailUseCase {

    private val log = KotlinLogging.logger {}

    override fun sendWelcomeMail(subscriptionId: SubscriptionId, operationId: OperationId) {
        TODO("Implement Check-Execute-Record pattern for idempotency")
        // STEP 1: Check if already processed (return early if yes)

        // STEP 2: Execute business logic

        // STEP 3: Record operation as completed
    }
}
