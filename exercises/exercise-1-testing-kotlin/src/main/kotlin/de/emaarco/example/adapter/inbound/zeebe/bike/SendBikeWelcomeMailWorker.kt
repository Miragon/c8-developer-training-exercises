package de.emaarco.example.adapter.inbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.inbound.bike.SendBikeWelcomeMailUseCase
import de.emaarco.example.domain.bike.BikeSubscriptionId
import io.camunda.client.annotation.JobWorker
import io.camunda.client.annotation.Variable
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class SendBikeWelcomeMailWorker(
    private val useCase: SendBikeWelcomeMailUseCase
) {
    private val log = KotlinLogging.logger {}

    @JobWorker(type = BikeSubscriptionSignupProcessApi.TaskTypes.ACTIVITY_SEND_WELCOME_MAIL)
    fun handle(@Variable subscriptionId: UUID) {
        log.info { "Sending welcome mail for subscription: $subscriptionId" }
        useCase.sendWelcomeMail(BikeSubscriptionId(subscriptionId))
    }
}
