package de.emaarco.example.adapter.inbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.inbound.bike.SendBikeConfirmationMailUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
import io.camunda.client.annotation.JobWorker
import io.camunda.client.annotation.Variable
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SendBikeConfirmationMailWorker(
    private val useCase: SendBikeConfirmationMailUseCase
) {
    private val log = KotlinLogging.logger {}

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.ACTIVITY_SEND_CONFIRMATION_MAIL)
    fun handle(@Variable subscriptionId: UUID) {
        log.info { "Sending bike confirmation mail for subscription: $subscriptionId" }
        useCase.sendConfirmationMail(BikeSubscriptionId(subscriptionId))
    }
}
