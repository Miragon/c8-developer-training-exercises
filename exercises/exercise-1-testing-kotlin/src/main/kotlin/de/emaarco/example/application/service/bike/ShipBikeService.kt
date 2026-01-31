package de.emaarco.example.application.service.bike

import de.emaarco.example.application.port.inbound.bike.ShipBikeUseCase
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionRepository
import de.emaarco.example.domain.bike.BikeSubscriptionId
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ShipBikeService(
    private val repository: BikeSubscriptionRepository
) : ShipBikeUseCase {

    private val log = KotlinLogging.logger {}

    override fun shipBike(subscriptionId: BikeSubscriptionId) {
        val subscription = repository.find(subscriptionId)
        log.info { "Shipping bike ${subscription.bikeId.value} to customer ${subscription.customerName.value}" }
        repository.save(subscription.markShipped())
    }
}
