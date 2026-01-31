package de.emaarco.example.application.service.newsletter

import de.emaarco.example.application.port.inbound.newsletter.SendWelcomeMailUseCase
import de.emaarco.example.application.port.outbound.newsletter.NewsletterSubscriptionRepository
import de.emaarco.example.domain.SubscriptionId
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SendWelcomeMailService(
    private val repository: NewsletterSubscriptionRepository,
) : SendWelcomeMailUseCase {

    private val log = KotlinLogging.logger {}

    override fun sendWelcomeMail(subscriptionId: SubscriptionId) {
        val subscription = repository.find(subscriptionId)
        log.info { "Sending welcome mail to ${subscription.email}" }
    }
}