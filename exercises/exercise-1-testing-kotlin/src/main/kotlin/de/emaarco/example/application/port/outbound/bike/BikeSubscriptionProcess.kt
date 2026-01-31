package de.emaarco.example.application.port.outbound.bike

import de.emaarco.example.domain.bike.BikeId
import de.emaarco.example.domain.bike.BikeSubscriptionId

interface BikeSubscriptionProcess {
    fun startSubscription(id: BikeSubscriptionId, bikeId: BikeId): Long
    fun sendPaymentReceived(id: BikeSubscriptionId)
    fun sendRequestCanceled(id: BikeSubscriptionId)
    fun sendBikeReceived(id: BikeSubscriptionId)
}
