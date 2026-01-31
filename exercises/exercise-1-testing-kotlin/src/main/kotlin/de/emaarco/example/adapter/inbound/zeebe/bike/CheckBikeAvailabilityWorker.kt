package de.emaarco.example.adapter.inbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.inbound.bike.CheckBikeAvailabilityUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
import io.camunda.client.annotation.JobWorker
import io.camunda.client.annotation.Variable
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CheckBikeAvailabilityWorker(
    private val useCase: CheckBikeAvailabilityUseCase
) {
    private val log = KotlinLogging.logger {}

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.ACTIVITY_CHECK_AVAILABILITY)
    fun handle(@Variable subscriptionId: UUID): Map<String, Any> {
        log.info { "Checking bike availability for subscription: $subscriptionId" }
        val available = useCase.checkAvailability(BikeSubscriptionId(subscriptionId))
        return mapOf("bikeAvailable" to available)
    }
}
