package de.emaarco.example.application.port.inbound.bike

import de.emaarco.example.domain.bike.BikeSubscriptionId

interface ShipBikeUseCase {
    fun shipBike(subscriptionId: BikeSubscriptionId)
}
