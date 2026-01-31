package de.emaarco.example.application.port.outbound.bike

import de.emaarco.example.domain.bike.BikeSubscription
import de.emaarco.example.domain.bike.BikeSubscriptionId

interface BikeSubscriptionRepository {
    fun save(subscription: BikeSubscription): BikeSubscription
    fun find(id: BikeSubscriptionId): BikeSubscription
    fun search(id: BikeSubscriptionId): BikeSubscription?
}
