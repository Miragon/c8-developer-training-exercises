package de.emaarco.example.application.service.bike

import de.emaarco.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionRepository
import de.emaarco.example.domain.bike.BikeSubscriptionId
import mu.KotlinLogging
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CheckBikeAvailabilityService(
    private val repository: BikeSubscriptionRepository
) : CheckBikeAvailabilityUseCase {

    private val log = KotlinLogging.logger {}

    override fun checkAvailability(subscriptionId: BikeSubscriptionId): Boolean {
        val subscription = repository.find(subscriptionId)

        // Mock availability check: 80% chance of being available
        val available = Random.nextDouble() < 0.8

        log.info { "Bike ${subscription.bikeId.value} availability check: $available" }

        repository.save(subscription.markAvailability(available))

        return available
    }
}
