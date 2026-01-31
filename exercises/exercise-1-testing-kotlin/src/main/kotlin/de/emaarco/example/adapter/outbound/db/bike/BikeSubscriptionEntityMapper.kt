package de.emaarco.example.adapter.outbound.db.bike

import de.emaarco.example.domain.Email
import de.emaarco.example.domain.Name
import de.emaarco.example.domain.bike.BikeId
import de.emaarco.example.domain.bike.BikeSubscription
import de.emaarco.example.domain.bike.BikeSubscriptionId
import org.springframework.stereotype.Component

@Component
class BikeSubscriptionEntityMapper {

    fun toDomain(entity: BikeSubscriptionEntity): BikeSubscription {
        return BikeSubscription(
            id = BikeSubscriptionId(entity.id),
            bikeId = BikeId(entity.bikeId),
            customerEmail = Email(entity.customerEmail),
            customerName = Name(entity.customerName),
            requestDate = entity.requestDate,
            status = entity.status,
            bikeAvailable = entity.bikeAvailable
        )
    }

    fun toEntity(domain: BikeSubscription): BikeSubscriptionEntity {
        return BikeSubscriptionEntity(
            id = domain.id.value,
            bikeId = domain.bikeId.value,
            customerEmail = domain.customerEmail.value,
            customerName = domain.customerName.value,
            requestDate = domain.requestDate,
            status = domain.status,
            bikeAvailable = domain.bikeAvailable
        )
    }
}
