package de.emaarco.example.adapter.outbound.zeebe.bike

import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionProcess
import de.emaarco.example.adapter.process.config.ProcessEngineApi
import de.emaarco.example.domain.bike.BikeId
import de.emaarco.example.domain.bike.BikeSubscriptionId
import org.springframework.stereotype.Component

@Component
class BikeSubscriptionProcessAdapter(
    private val engineApi: ProcessEngineApi
) : BikeSubscriptionProcess {

    override fun startSubscription(id: BikeSubscriptionId, bikeId: BikeId): Long {
        val variables = mapOf(
            "subscriptionId" to id.value.toString(),
            "bikeId" to bikeId.value.toString()
        )
        return engineApi.startProcess(
            processId = BikeSubscriptionSignupProcessApi.PROCESS_ID,
            variables = variables
        )
    }

    override fun sendPaymentReceived(id: BikeSubscriptionId) {
        engineApi.sendMessage(
            messageName = BikeSubscriptionSignupProcessApi.Messages.MESSAGE_PAYMENT_RECEIVED,
            correlationId = id.value.toString()
        )
    }

    override fun sendRequestCanceled(id: BikeSubscriptionId) {
        engineApi.sendMessage(
            messageName = BikeSubscriptionSignupProcessApi.Messages.MESSAGE_REQUEST_CANCELED,
            correlationId = id.value.toString()
        )
    }

    override fun sendBikeReceived(id: BikeSubscriptionId) {
        engineApi.sendMessage(
            messageName = BikeSubscriptionSignupProcessApi.Messages.MESSAGE_BIKE_RECEIVED,
            correlationId = id.value.toString()
        )
    }
}
