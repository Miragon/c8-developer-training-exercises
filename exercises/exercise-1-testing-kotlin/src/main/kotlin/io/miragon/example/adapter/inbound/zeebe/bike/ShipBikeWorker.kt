package io.miragon.example.adapter.inbound.zeebe.bike

import io.miragon.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import io.miragon.example.application.port.inbound.bike.ShipBikeUseCase
import io.miragon.example.domain.bike.BikeSubscriptionId
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
    fun handle(@Variable subscriptionId: String) {
        log.info { "Shipping bike for subscription: $subscriptionId" }
        useCase.shipBike(BikeSubscriptionId(UUID.fromString(subscriptionId)))
    }
}
