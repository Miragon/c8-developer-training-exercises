package de.emaarco.example.adapter.inbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.inbound.bike.SendPaymentReminderUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
import io.camunda.client.annotation.JobWorker
import io.camunda.client.annotation.Variable
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SendPaymentReminderWorker(
    private val useCase: SendPaymentReminderUseCase
) {
    private val log = KotlinLogging.logger {}

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.ACTIVITY_SEND_PAYMENT_REMINDER)
    fun handle(@Variable subscriptionId: UUID) {
        log.info { "Sending payment reminder for subscription: $subscriptionId" }
        useCase.sendPaymentReminder(BikeSubscriptionId(subscriptionId))
    }
}
