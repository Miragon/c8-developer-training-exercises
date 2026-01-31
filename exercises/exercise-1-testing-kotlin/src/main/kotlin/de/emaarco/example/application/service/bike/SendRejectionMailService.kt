package de.emaarco.example.application.service.bike

import de.emaarco.example.application.port.inbound.bike.SendRejectionMailUseCase
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionRepository
import de.emaarco.example.domain.bike.BikeSubscriptionId
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SendRejectionMailService(
    private val repository: BikeSubscriptionRepository
) : SendRejectionMailUseCase {

    private val log = KotlinLogging.logger {}

    override fun sendRejectionMail(subscriptionId: BikeSubscriptionId) {
        val subscription = repository.find(subscriptionId)
        log.info { "Sending rejection mail to ${subscription.customerEmail.value} - bike ${subscription.bikeId.value} not available" }
    }
}
