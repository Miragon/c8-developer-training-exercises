package de.emaarco.example.adapter.inbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.inbound.bike.NotifyBikeCancelationUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
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

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.ACTIVITY_NOTIFY_ABOUT_CANCELATION)
    fun handle(@Variable subscriptionId: UUID) {
        log.info { "Notifying about cancelation for subscription: $subscriptionId" }
        useCase.notifyCancelation(BikeSubscriptionId(subscriptionId))
    }
}
