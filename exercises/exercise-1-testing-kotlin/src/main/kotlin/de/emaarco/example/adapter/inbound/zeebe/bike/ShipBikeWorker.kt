package de.emaarco.example.adapter.inbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.inbound.bike.ShipBikeUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
import io.camunda.client.annotation.JobWorker
import io.camunda.client.annotation.Variable
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ShipBikeWorker(
    private val useCase: ShipBikeUseCase
) {
    private val log = KotlinLogging.logger {}

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.ACTIVITY_SHIP_BIKE)
    fun handle(@Variable subscriptionId: UUID) {
        log.info { "Shipping bike for subscription: $subscriptionId" }
        useCase.shipBike(BikeSubscriptionId(subscriptionId))
    }
}
