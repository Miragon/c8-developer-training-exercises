package de.emaarco.example.application.port.outbound.bike

import de.emaarco.example.domain.bike.BikeSubscriptionId

interface BikeSubscriptionProcess {
    fun startSubscription(id: BikeSubscriptionId): Long
    fun sendPaymentReceived(id: BikeSubscriptionId)
    fun sendRequestCanceled(id: BikeSubscriptionId)
    fun sendBikeReceived(id: BikeSubscriptionId)
}
