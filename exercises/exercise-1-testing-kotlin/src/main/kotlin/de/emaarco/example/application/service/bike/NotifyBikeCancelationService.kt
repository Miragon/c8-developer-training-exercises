package de.emaarco.example.application.service.bike

import de.emaarco.example.application.port.inbound.bike.NotifyBikeCancelationUseCase
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionRepository
import de.emaarco.example.domain.bike.BikeSubscriptionId
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class NotifyBikeCancelationService(
    private val repository: BikeSubscriptionRepository
) : NotifyBikeCancelationUseCase {

    private val log = KotlinLogging.logger {}

    override fun notifyCancelation(subscriptionId: BikeSubscriptionId) {
        val subscription = repository.find(subscriptionId)
        log.info { "Notifying customer about cancelation: ${subscription.customerEmail.value}" }
        repository.save(subscription.cancel())
    }
}
