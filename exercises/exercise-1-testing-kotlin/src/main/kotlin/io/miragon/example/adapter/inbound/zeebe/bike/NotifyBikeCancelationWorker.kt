package io.miragon.example.adapter.inbound.zeebe.bike

import io.miragon.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import io.miragon.example.application.port.inbound.bike.NotifyBikeCancelationUseCase
import io.miragon.example.domain.bike.BikeSubscriptionId
import io.camunda.client.annotation.JobWorker
import io.camunda.client.annotation.Variable
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class NotifyBikeCancelationWorker(
    private val useCase: NotifyBikeCancelationUseCase
) {
    private val log = KotlinLogging.logger {}

    @JobWorker(type = BikeSubscriptionSignupProcessApi.ServiceTasks.BIKE_NOTIFY_CANCELATION)
    fun handle(@Variable subscriptionId: String) {
        log.info { "Notifying about cancelation for subscription: $subscriptionId" }
        useCase.notifyCancelation(BikeSubscriptionId(UUID.fromString(subscriptionId)))
    }
}
