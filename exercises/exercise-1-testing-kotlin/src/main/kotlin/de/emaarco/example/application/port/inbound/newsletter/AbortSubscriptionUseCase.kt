package de.emaarco.example.application.port.inbound.newsletter

import de.emaarco.example.domain.SubscriptionId

interface AbortSubscriptionUseCase {
    fun abort(subscriptionId: SubscriptionId)
}