package de.emaarco.example.application.service.newsletter

import de.emaarco.example.application.port.inbound.newsletter.SendConfirmationMailUseCase
import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionRepository
import de.emaarco.example.domain.SubscriptionId
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SendConfirmationMailService(
    private val repository: NewsletterSubscriptionRepository,
) : SendConfirmationMailUseCase {

    private val log = KotlinLogging.logger {}

    override fun sendConfirmationMail(subscriptionId: SubscriptionId) {
        val subscription = repository.find(subscriptionId)
        log.info { "Sending confirmation mail to ${subscription.email}" }
    }
}