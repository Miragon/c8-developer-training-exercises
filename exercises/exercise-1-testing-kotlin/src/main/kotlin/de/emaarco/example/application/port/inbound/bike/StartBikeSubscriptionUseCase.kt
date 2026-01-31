package de.emaarco.example.application.port.inbound.bike

import de.emaarco.example.domain.Email
import de.emaarco.example.domain.Name
import de.emaarco.example.domain.bike.BikeId
import de.emaarco.example.domain.bike.BikeSubscriptionId

interface StartBikeSubscriptionUseCase {
    data class Command(
        val bikeId: BikeId,
        val email: Email,
        val name: Name
    )

    fun start(command: Command): BikeSubscriptionId
}
