package de.emaarco.example.application.port.inbound.bike

import de.emaarco.example.domain.bike.BikeSubscriptionId

interface CheckBikeAvailabilityUseCase {
    fun checkAvailability(subscriptionId: BikeSubscriptionId): Boolean
}
