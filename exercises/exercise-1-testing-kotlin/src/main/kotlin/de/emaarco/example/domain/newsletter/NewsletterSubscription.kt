package de.emaarco.example.domain.newsletter

import de.emaarco.example.domain.Email
import de.emaarco.example.domain.Name
import de.emaarco.example.domain.SubscriptionId
import de.emaarco.example.domain.SubscriptionStatus
import java.time.LocalDateTime
import java.util.*

data class NewsletterSubscription(
    val id: SubscriptionId = SubscriptionId(UUID.randomUUID()),
    val name: Name,
    val email: Email,
    val newsletter: NewsletterId,
    val registrationDate: LocalDateTime = LocalDateTime.now(),
    val status: SubscriptionStatus = SubscriptionStatus.PENDING,
) {
    fun confirmRegistration() = this.copy(status = SubscriptionStatus.CONFIRMED)
    fun abortRegistration() = this.copy(status = SubscriptionStatus.ABORTED)
}
